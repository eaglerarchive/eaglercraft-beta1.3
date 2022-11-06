package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EaglercraftVanillaNetworkManager extends NetworkManager {
	public static final Object a = new Object();
	public static int b;
	public static int c;
	private Object e = new Object();
	private Socket f;
	private final SocketAddress g;
	private DataInputStream h;
	private DataOutputStream i;
	private boolean j = true;
	private List k = Collections.synchronizedList(new ArrayList());
	private List l = Collections.synchronizedList(new ArrayList());
	private List m = Collections.synchronizedList(new ArrayList());
	private NetHandler n;
	private boolean o = false;
	private Thread p;
	private Thread q;
	private boolean r = false;
	private String s = "";
	private Object[] t;
	private int u = 0;
	private int v = 0;
	public int d = 0;
	private int w = 50;

	public EaglercraftVanillaNetworkManager(Socket var1, String var2, NetHandler var3) throws IOException {
		this.f = var1;
		this.g = var1.getRemoteSocketAddress();
		this.n = var3;
		var1.setTrafficClass(24);
		this.h = new DataInputStream(var1.getInputStream());
		this.i = new DataOutputStream(var1.getOutputStream());
		this.q = new NetworkReaderThread(this, var2 + " read thread");
		this.p = new NetworkWriterThread(this, var2 + " write thread");
		this.q.start();
		this.p.start();
	}

	public void a(NetHandler var1) {
		this.n = var1;
	}

	public void a(Packet var1) {
		if (!this.o) {
			Object var2 = this.e;
			synchronized (this.e) {
				this.v += var1.a() + 1;
				if (var1.k) {
					this.m.add(var1);
				} else {
					this.l.add(var1);
				}

			}
		}
	}

	private void e() {
		try {
			boolean var1 = true;
			Object var2;
			Packet var3;
			if (!this.l.isEmpty()
					&& (this.d == 0 || System.currentTimeMillis() - ((Packet) this.l.get(0)).j >= (long) this.d)) {
				var1 = false;
				var2 = this.e;
				synchronized (this.e) {
					var3 = (Packet) this.l.remove(0);
					this.v -= var3.a() + 1;
				}

				Packet.a(var3, this.i);
			}

			if ((var1 || this.w-- <= 0) && !this.m.isEmpty()
					&& (this.d == 0 || System.currentTimeMillis() - ((Packet) this.m.get(0)).j >= (long) this.d)) {
				var1 = false;
				var2 = this.e;
				synchronized (this.e) {
					var3 = (Packet) this.m.remove(0);
					this.v -= var3.a() + 1;
				}

				Packet.a(var3, this.i);
				this.w = 50;
			}

			if (var1) {
				Thread.sleep(10L);
			}
		} catch (InterruptedException var8) {
			;
		} catch (Exception var9) {
			if (!this.r) {
				this.a(var9);
			}
		}

	}

	private void f() {
		try {
			Packet var1 = Packet.b(this.h);
			if (var1 != null) {
				this.k.add(var1);
			} else {
				this.a("disconnect.endOfStream");
			}
		} catch (Exception var2) {
			if (!this.r) {
				this.a(var2);
			}
		}

	}

	private void a(Exception var1) {
		var1.printStackTrace();
		this.a("disconnect.genericReason", "Internal exception: " + var1.toString());
	}

	public void a(String var1, Object... var2) {
		if (this.j) {
			this.r = true;
			this.s = var1;
			this.t = var2;
			(new NetworkMasterThread(this)).start();
			this.j = false;

			try {
				this.h.close();
				this.h = null;
			} catch (Throwable var6) {
				;
			}

			try {
				this.i.close();
				this.i = null;
			} catch (Throwable var5) {
				;
			}

			try {
				this.f.close();
				this.f = null;
			} catch (Throwable var4) {
				;
			}

		}
	}

	public void a() {
		if (this.v > 1048576) {
			this.a("disconnect.overflow");
		}

		if (this.k.isEmpty()) {
			if (this.u++ == 1200) {
				this.a("disconnect.timeout");
			}
		} else {
			this.u = 0;
		}

		int var1 = 100;

		while (!this.k.isEmpty() && var1-- >= 0) {
			Packet var2 = (Packet) this.k.remove(0);
			var2.a(this.n);
		}

		if (this.r && this.k.isEmpty()) {
			this.n.a(this.s, this.t);
		}

	}

	public SocketAddress b() {
		return this.g;
	}

	public void c() {
		this.o = true;
		this.q.interrupt();
		(new ThreadMonitorConnection(this)).start();
	}

	public int d() {
		return this.m.size();
	}

	@Override
	public boolean isDead() {
		return this.f.isClosed();
	}
}