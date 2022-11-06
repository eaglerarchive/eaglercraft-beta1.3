package net.minecraft.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkListenThread {
	public static Logger a = Logger.getLogger("Minecraft");
	private ServerSocket d;
	private Thread e;
	public volatile boolean b = false;
	private int f = 0;
	private ArrayList g = new ArrayList();
	private ArrayList h = new ArrayList();
	public MinecraftServer c;

	public NetworkListenThread(MinecraftServer var1, InetAddress var2, int var3) throws IOException {
		this.c = var1;
		this.d = new ServerSocket(var3, 0, var2);
		this.d.setPerformancePreferences(0, 2, 1);
		this.b = true;
		this.e = new NetworkAcceptThread(this, "Listen thread", var1);
		this.e.start();
	}

	public NetworkListenThread(MinecraftServer minecraftServer) {
		this.c = minecraftServer;
		this.d = null;
		this.b = true;
		this.e = null;
	}

	public void a(NetServerHandler var1) {
		this.h.add(var1);
	}

	public void a(NetLoginHandler var1) {
		if (var1 == null) {
			throw new IllegalArgumentException("Got null pendingconnection!");
		} else {
			this.g.add(var1);
		}
	}

	public void a() {
		int var1;
		for (var1 = 0; var1 < this.g.size(); ++var1) {
			NetLoginHandler var2 = (NetLoginHandler) this.g.get(var1);

			try {
				var2.a();
			} catch (Exception var5) {
				var2.a("Internal server error");
				a.log(Level.WARNING, "Failed to handle packet: " + var5, var5);
			}

			if (var2.c) {
				this.g.remove(var1--);
			}
		}

		for (var1 = 0; var1 < this.h.size(); ++var1) {
			NetServerHandler var6 = (NetServerHandler) this.h.get(var1);

			try {
				var6.a();
			} catch (Exception var4) {
				a.log(Level.WARNING, "Failed to handle packet: " + var4, var4);
				var6.a("Internal server error");
			}

			if (var6.c) {
				this.h.remove(var1--);
			}
		}

	}
	
	public static ServerSocket a(NetworkListenThread th) {
		return th.d;
	}
}