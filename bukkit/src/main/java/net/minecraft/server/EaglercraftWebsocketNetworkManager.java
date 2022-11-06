package net.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import org.java_websocket.WebSocket;

public class EaglercraftWebsocketNetworkManager extends NetworkManager {

	public static final int PACKET_LIMIT = 300;
	public static final int PACKET_PER_SECOND_QUOTA = 1000 / 35;
	public static final int PACKET_MAX_SIZE = 9600;
	
	final WebSocket websocket;
	NetHandler netHandler;
	
	private volatile int packetCounter = 0;
	private long packetDecrement;
	private int timeoutCounter = 0;

	private final List<Packet> readPackets = new LinkedList();
	protected final List<Packet> writePackets = new LinkedList();
	
	public boolean disconnected = false;
	
	private final Thread writeThread;	
	protected final Object writeThreadLock = new Object();
	
	public EaglercraftWebsocketNetworkManager(WebSocket sock, NetHandler netHandler) {
		this.websocket = sock;
		this.netHandler = netHandler;
		this.packetDecrement = System.currentTimeMillis();
		this.writeThread = new Thread(new WebsocketNetworkManagerWriteThread(this), "PacketWriteThread: [" + sock.getRemoteSocketAddress() + "]");
		this.writeThread.setDaemon(true);
		this.writeThread.start();
	}
	
	void packetRecieved(ByteBuffer buf) throws IOException {
		if(buf.remaining() > PACKET_MAX_SIZE) {
			a("disconnect.overflow");
			return;
		}
		++packetCounter;
		if(packetCounter > PACKET_LIMIT) {
			a("disconnect.overflow");
			return;
		}
		Packet var1 = Packet.b(new DataInputStream(new ByteBufferInputStream(buf)));
		if (var1 != null) {
			synchronized(readPackets) {
				readPackets.add(var1);
			}
		} else {
			this.a("disconnect.closed", "Invalid packet recieved");
		}
	}
	
	protected static class WebsocketNetworkManagerWriteThread implements Runnable {
		
		private EaglercraftWebsocketNetworkManager manager;
		
		private final ByteArrayOutputStream packetOut = new ByteArrayOutputStream();
		private final DataOutputStream packetDataOut = new DataOutputStream(packetOut);
		
		protected WebsocketNetworkManagerWriteThread(EaglercraftWebsocketNetworkManager mgr) {
			manager = mgr;
		}
		
		@Override
		public void run() {
			main_loop: while(manager.websocket.isOpen()) {
				synchronized(manager.writeThreadLock) {
					try {
						manager.writeThreadLock.wait(5000l);
					} catch (InterruptedException e) {
					}
				}
				if(!manager.websocket.isOpen()) {
					break main_loop;
				}
				try {
					while(true) {
						Packet p = null;
						synchronized(manager.writePackets) {
							if(manager.writePackets.isEmpty()) {
								break;
							}
							p = manager.writePackets.remove(0);
						}
						if(p != null) {
							Packet.a(p, packetDataOut);
							byte[] pktBytes = packetOut.toByteArray();
							packetOut.reset();
							manager.websocket.send(ByteBuffer.wrap(pktBytes));
						}
					}
					if(manager.disconnected) {
						manager.websocket.close();
						break main_loop;
					}
				}catch(Throwable t) {
					t.printStackTrace();
					manager.a("disconnect.closed", "Packet write fault");
					break main_loop;
				}
			}
		}
		
	}
	
	/**
	 * Set the NetHandler
	 */
	@Override
	public void a(NetHandler var1) {
		netHandler = var1;
	}

	/**
	 * addToSendQueue
	 */
	@Override
	public void a(Packet var1) {
		synchronized(writePackets) {
			writePackets.add(var1);
		}
		synchronized(writeThreadLock) {
			writeThreadLock.notify();
		}
	}

	/**
	 * disconnect
	 */
	@Override
	public void a(String var1, Object... var2) {
		netHandler.a(var1, var2);
		disconnected = true;
	}

	/**
	 * processReadPackets
	 */
	@Override
	public void a() {
		if(websocket.isClosed()) {
			synchronized(readPackets) {
				readPackets.clear();
			}
			if(netHandler instanceof NetLoginHandler) {
				((NetLoginHandler)netHandler).c = true;
			}else if(netHandler instanceof NetServerHandler) {
				((NetServerHandler)netHandler).c = true;
			}
			return;
		}
		
		long t = System.currentTimeMillis();
		int decr = (int) ((t - packetDecrement) / PACKET_PER_SECOND_QUOTA);
		packetCounter -= decr;
		packetDecrement += decr * PACKET_PER_SECOND_QUOTA;
		
		if(packetCounter < 0) {
			packetCounter = 0;
		}
		
		if(++timeoutCounter > 1200) {
			a("disconnect.timeout");
			return;
		}
		
		int var1 = 100;
		while(--var1 > 0) {
			Packet p = null;
			
			synchronized(readPackets) {
				if(!readPackets.isEmpty()) {
					p = readPackets.remove(0);
				}
			}
			
			if(p == null) {
				break;
			}else {
				timeoutCounter = 0;
				p.a(netHandler);
			}
		}
	}

	/**
	 * gets the remote address
	 */
	@Override
	public SocketAddress b() {
		return websocket.getRemoteSocketAddress();
	}

	/**
	 * shuts connection down
	 */
	@Override
	public void c() {
		a("disconnect.closed", "Connection closed by server");
	}

	/**
	 * gets a number of delayed packets
	 */
	@Override
	public int d() {
		return 0;
	}

	@Override
	public boolean isDead() {
		return websocket.isClosed();
	}

}
