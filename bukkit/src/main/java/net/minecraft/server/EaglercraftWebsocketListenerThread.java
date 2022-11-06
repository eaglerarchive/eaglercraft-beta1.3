package net.minecraft.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import net.minecraft.server.NetworkListenThread;

public class EaglercraftWebsocketListenerThread extends WebSocketServer {

	public final MinecraftServer server;
	public final Object startupLock = new Object();
	public volatile boolean startupFailed;
	public volatile boolean started;
	
	public EaglercraftWebsocketListenerThread(MinecraftServer server, InetSocketAddress addr) {
		super(addr);
		this.server = server;
		this.startupFailed = false;
		this.started = false;
		this.setConnectionLostTimeout(15);
		this.setTcpNoDelay(true);
		this.start();
	}

	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		EaglercraftWebsocketNetworkManager mgr = arg0.getAttachment();
		if(mgr != null && !mgr.disconnected) {
			mgr.a("disconnect.close");
		}
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		if(!this.started) {
			this.startupFailed = true;
			synchronized(startupLock) {
				startupLock.notify();
			}
		}
	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		EaglercraftWebsocketNetworkManager mgr = arg0.getAttachment();
		if(mgr != null) {
			mgr.a("disconnect.closed", "Fuck off");
		}else {
			arg0.close();
		}
	}

	@Override
	public void onMessage(WebSocket arg0, ByteBuffer arg1) {
		EaglercraftWebsocketNetworkManager mgr = arg0.getAttachment();
		if(mgr != null) {
			try {
				mgr.packetRecieved(arg1);
			} catch (IOException e) {
				mgr.a("disconnect.closed", "Packet Decoding Failure: " + e.toString());
			}
		}
	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		NetLoginHandler newNetHandler = new NetLoginHandler(server, arg0);
		arg0.setAttachment(newNetHandler.b);
		this.server.c.a(newNetHandler);
	}

	@Override
	public void onStart() {
		this.started = true;
		
		synchronized(startupLock) {
			startupLock.notify();
		}
	}
	
}
