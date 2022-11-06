package net.minecraft.server;

import java.net.SocketAddress;

public abstract class NetworkManager {
	
	/**
	 * Set the NetHandler
	 */
	abstract void a(NetHandler var1);
	
	/**
	 * addToSendQueue
	 */
	public abstract void a(Packet var1);
	
	/**
	 * disconnect
	 */
	abstract void a(String var1, Object... var2);
	
	/**
	 * processReadPackets
	 */
	abstract void a();
	
	/**
	 * gets the remote address
	 */
	abstract SocketAddress b();
	
	/**
	 * shuts connection down
	 */
	public abstract void c();
	
	/**
	 * gets a number of delayed packets
	 */
	abstract int d();
	
	/**
	 * gets if the connection is closed
	 */
	abstract boolean isDead();
}
