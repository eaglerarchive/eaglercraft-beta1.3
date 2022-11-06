package net.minecraft.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jline.ConsoleReader;
import joptsimple.OptionSet;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.LoggerOutputStream;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.craftbukkit.scheduler.CraftScheduler;
import org.bukkit.event.Event.Type;
import org.bukkit.event.world.WorldEvent;

public class MinecraftServer implements Runnable, ICommandListener {
	public static Logger a = Logger.getLogger("Minecraft");
	public static HashMap b = new HashMap();
	public NetworkListenThread c;
	public EaglercraftWebsocketListenerThread wsNetManager;
	public PropertyManager d;
	public ServerConfigurationManager f;
	private ConsoleCommandHandler o;
	private boolean p = true;
	public boolean g = false;
	int h = 0;
	public String i;
	public int j;
	private List q = new ArrayList();
	private List r = Collections.synchronizedList(new ArrayList());
	public EntityTracker k;
	public boolean l;
	public boolean m;
	public boolean n;
	public int spawnProtection;
	public List<WorldServer> worlds = new ArrayList();
	public CraftServer server;
	public OptionSet options;
	public ColouredConsoleSender console;
	public ConsoleReader reader;
	public static long watchDog = 0l;

	public MinecraftServer(OptionSet options) {
		new ThreadSleepForever(this);
		this.options = options;

		try {
			this.reader = new ConsoleReader();
		} catch (IOException var3) {
			Logger.getLogger(MinecraftServer.class.getName()).log(Level.SEVERE, (String) null, var3);
		}

	}

	private boolean d() throws UnknownHostException {
		this.o = new ConsoleCommandHandler(this);
		ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);
		threadcommandreader.setDaemon(true);
		threadcommandreader.start();
		ConsoleLogManager.a(this);
		System.setOut(new PrintStream(new LoggerOutputStream(a, Level.INFO), true));
		System.setErr(new PrintStream(new LoggerOutputStream(a, Level.SEVERE), true));
		a.info("Starting minecraft server version Beta 1.3");
		if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
			a.warning("**** NOT ENOUGH RAM!");
			a.warning(
					"To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
		}

		a.info("Loading properties");
		this.d = new PropertyManager(this.options);
		String s = this.d.a("websocket-address", "0.0.0.0:25565");
		String s2 = this.d.a("vanilla-address", "127.0.0.1:25566");
		this.l = this.d.a("online-mode", false);
		this.m = this.d.a("spawn-animals", true);
		this.n = this.d.a("pvp", true);
		this.spawnProtection = this.d.a("spawn-protection", 16);
		
		InetSocketAddress inetWebsocketAddress = null;
		if (s.length() > 0 && !s.equalsIgnoreCase("null")) {
			String addr = s;
			int port = 25565;
			int cp = s.lastIndexOf(':');
			if(cp != -1) {
				addr = s.substring(0, cp);
				port = Integer.parseInt(s.substring(cp + 1));
			}
			try {
				inetWebsocketAddress = new InetSocketAddress(InetAddress.getByName(addr), port);
			}catch(UnknownHostException ex) {
				a.severe("ERROR: websocket-address '" + s + "' is invalid: " + ex.toString());
			}
		}
		
		InetSocketAddress inetVanillaAddress = null;
		if (s2.length() > 0 && !s2.equalsIgnoreCase("null")) {
			String addr = s2;
			int port = 25565;
			int cp = s2.lastIndexOf(':');
			if(cp != -1) {
				addr = s2.substring(0, cp);
				port = Integer.parseInt(s2.substring(cp + 1));
			}
			try {
				inetVanillaAddress = new InetSocketAddress(InetAddress.getByName(addr), port);
			}catch(UnknownHostException ex) {
				a.severe("ERROR: vanilla-address '" + s2 + "' is invalid: " + ex.toString());
			}
		}
		
		if(inetWebsocketAddress == null && inetVanillaAddress == null) {
			a.severe("No vanilla or websocket addresses are defined!");
			a.severe("Server will not start");
			return false;
		}
		
		if(inetWebsocketAddress != null) {
			a.info("Starting websocket server on: " + inetWebsocketAddress.toString());
			wsNetManager = new EaglercraftWebsocketListenerThread(this, inetWebsocketAddress);
			synchronized(wsNetManager.startupLock) {
				try {
					wsNetManager.startupLock.wait(5000l);
				} catch (InterruptedException e) {
					;
				}
			}
			if(wsNetManager.startupFailed || !wsNetManager.started) {
				a.severe("ERROR: could not start websocket server on " + inetWebsocketAddress.toString());
				return false;
			}
		}
		
		if(inetVanillaAddress != null) {
			a.info("Starting vanilla server on: " + inetVanillaAddress.toString());
			try {
				this.c = new NetworkListenThread(this, inetVanillaAddress.getAddress(), inetVanillaAddress.getPort());
			} catch (Throwable var11) {
				a.warning("**** FAILED TO BIND TO PORT!");
				a.log(Level.WARNING, "The exception was: " + var11.toString());
				a.warning("Perhaps a server is already running on that port?");
				return false;
			}
		}else {
			this.c = new NetworkListenThread(this);
		}

		this.f = new ServerConfigurationManager(this);
		this.k = new EntityTracker(this);
		long j = System.nanoTime();
		String s1 = this.d.a("level-name", "world");
		a.info("Preparing level \"" + s1 + "\"");
		this.a((Convertable) (new WorldLoaderServer(new File("."))), (String) s1);
		long elapsed = System.nanoTime() - j;
		String time = String.format("%.3fs", (double) elapsed / 1.0E10D);
		a.info("Done (" + time + ")! For help, type \"help\" or \"?\"");
		return true;
	}

	private void a(Convertable convertable, String s) {
		if (convertable.a(s)) {
			a.info("Converting map!");
			convertable.a(s, new ConvertProgressUpdater(this));
		}

		a.info("Preparing start region");
		WorldServer world = new WorldServer(this, new ServerNBTManager(new File("."), s, true), s,
				this.d.a("hellworld", false) ? -1 : 0);
		world.a(new WorldManager(this, world));
		world.j = this.d.a("spawn-monsters", true) ? 1 : 0;
		world.a(this.d.a("spawn-monsters", true), this.m);
		this.f.a(world);
		this.worlds.add(world);
		short short1 = 196;
		long i = System.currentTimeMillis();
		ChunkCoordinates chunkcoordinates = ((WorldServer) this.worlds.get(0)).l();

		for (int j = -short1; j <= short1 && this.p; j += 16) {
			for (int k = -short1; k <= short1 && this.p; k += 16) {
				long l = System.currentTimeMillis();
				if (l < i) {
					i = l;
				}

				if (l > i + 1000L) {
					int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
					int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;
					this.a("Preparing spawn area", j1 * 100 / i1);
					i = l;
				}

				Iterator i$ = this.worlds.iterator();

				while (i$.hasNext()) {
					WorldServer worldserver = (WorldServer) i$.next();
					world.u.d(chunkcoordinates.a + j >> 4, chunkcoordinates.c + k >> 4);

					while (world.e() && this.p) {
						;
					}
				}
			}
		}

		this.e();
	}

	private void a(String s, int i) {
		this.i = s;
		this.j = i;
		a.info(s + ": " + i + "%");
	}

	private void e() {
		this.i = null;
		this.j = 0;
		this.server.loadPlugins();
	}

	void f() {
		a.info("Saving chunks");
		Iterator i$ = this.worlds.iterator();

		while (i$.hasNext()) {
			WorldServer world = (WorldServer) i$.next();
			world.a(true, (IProgressUpdate) null);
			world.r();
			WorldEvent event = new WorldEvent(Type.WORLD_SAVED, world.getWorld());
			this.server.getPluginManager().callEvent(event);
		}

		this.f.d();
	}

	private void g() {
		a.info("Stopping server");
		if (this.server != null) {
			this.server.disablePlugins();
		}

		if (this.f != null) {
			this.f.d();
		}

		if (this.worlds.size() > 0) {
			this.f();
		}

	}

	public void a() {
		this.p = false;
	}

	public void run() {
		try {
			if (this.d()) {
				long i = System.currentTimeMillis();

				for (long j = 0L; this.p; Thread.sleep(1L)) {
					long k = System.currentTimeMillis();
					long l = k - i;
					if (l > 2000L) {
						a.warning("Can't keep up! Did the system time change, or is the server overloaded?");
						l = 2000L;
					}

					if (l < 0L) {
						a.warning("Time ran backwards! Did the system time change?");
						l = 0L;
					}

					j += l;
					i = k;
					if (this.worlds.size() > 0 && ((WorldServer) this.worlds.get(0)).q()) {
						this.h();
						j = 0L;
					} else {
						while (j > 50L) {
							j -= 50L;
							this.h();
						}
					}
				}
			} else {
				watchDog = -1l;
				return;
			}
		} catch (Throwable var58) {
			var58.printStackTrace();
			a.log(Level.SEVERE, "Unexpected exception", var58);
			watchDog = -1l;
			
			while (this.p) {
				this.b();

				try {
					Thread.sleep(10L);
				} catch (InterruptedException var56) {
					var56.printStackTrace();
				}
			}
		} finally {
			watchDog = -1l;
			
			try {
				this.g();
				this.g = true;
			} catch (Throwable var54) {
				var54.printStackTrace();
			} finally {
				System.exit(0);
			}

		}

	}

	private void h() {
		ArrayList arraylist = new ArrayList();
		Iterator iterator = b.keySet().iterator();
		watchDog = System.currentTimeMillis();
		
		int i;
		while (iterator.hasNext()) {
			String s = (String) iterator.next();
			i = (Integer) b.get(s);
			if (i > 0) {
				b.put(s, i - 1);
			} else {
				arraylist.add(s);
			}
		}

		int j;
		for (j = 0; j < arraylist.size(); ++j) {
			b.remove(arraylist.get(j));
		}

		AxisAlignedBB.a();
		Vec3D.a();
		++this.h;
		if (this.h % 20 == 0) {
			for (i = 0; i < this.f.b.size(); ++i) {
				EntityPlayer entityplayer = (EntityPlayer) this.f.b.get(i);
				entityplayer.a.b(new Packet4UpdateTime(entityplayer.world.k()));
			}
		}

		((CraftScheduler) this.server.getScheduler()).mainThreadHeartbeat((long) this.h);
		Iterator i$ = this.worlds.iterator();

		while (i$.hasNext()) {
			WorldServer world = (WorldServer) i$.next();
			world.g();

			while (world.e()) {
				;
			}

			world.d();
		}

		this.c.a();
		this.f.b();
		this.k.a();

		for (j = 0; j < this.q.size(); ++j) {
			((IUpdatePlayerListBox) this.q.get(j)).a();
		}

		try {
			this.b();
		} catch (Exception var6) {
			a.log(Level.WARNING, "Unexpected exception while parsing console command", var6);
		}

	}

	public void a(String s, ICommandListener icommandlistener) {
		this.r.add(new ServerCommand(s, icommandlistener));
	}

	public void b() {
		while (this.r.size() > 0) {
			ServerCommand servercommand = (ServerCommand) this.r.remove(0);
			if (!this.server.dispatchCommand(this.console, servercommand.a)) {
				this.o.a(servercommand);
			}
		}

	}

	public void a(IUpdatePlayerListBox iupdateplayerlistbox) {
		this.q.add(iupdateplayerlistbox);
	}

	public static void main(OptionSet options) {
		try {
			MinecraftServer minecraftserver = new MinecraftServer(options);
			(new ThreadServerApplication("Server thread", minecraftserver)).start();
		} catch (Exception var2) {
			a.log(Level.SEVERE, "Failed to start the minecraft server", var2);
		}

	}

	public File a(String s) {
		return new File(s);
	}

	public void b(String s) {
		a.info(s);
	}

	public String c() {
		return "CONSOLE";
	}

	public static boolean a(MinecraftServer minecraftserver) {
		return minecraftserver.p;
	}
}