package org.bukkit.craftbukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jline.ConsoleReader;
import net.lax1dude.eaglercraft.beta.server.EaglercraftServer;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.ConvertProgressUpdater;
import net.minecraft.server.Convertable;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PropertyManager;
import net.minecraft.server.ServerConfigurationManager;
import net.minecraft.server.ServerNBTManager;
import net.minecraft.server.WorldLoaderServer;
import net.minecraft.server.WorldManager;
import net.minecraft.server.WorldServer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.scheduler.CraftScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitScheduler;

public final class CraftServer implements Server {
	private final String serverName = "Craftbukkit";
	private final String serverVersion;
	private final String protocolVersion = "1.3";
	private final PluginManager pluginManager = new SimplePluginManager(this);
	private final BukkitScheduler scheduler = new CraftScheduler(this);
	private final CommandMap commandMap = new SimpleCommandMap(this);
	protected final MinecraftServer console;
	protected final ServerConfigurationManager server;
	private final Map<String, World> worlds = new LinkedHashMap();

	public CraftServer(MinecraftServer console, ServerConfigurationManager server) {
		this.console = console;
		this.server = server;
		this.serverVersion = CraftServer.class.getPackage().getImplementationVersion();
		this.pluginManager.RegisterInterface(JavaPluginLoader.class);
		Logger.getLogger("Minecraft").log(Level.INFO,
				"This server is running " + this.getName() + " version " + this.getVersion());
	}

	public void loadPlugins() {
		EaglercraftServer.installHooks(this, this.commandMap);
		File pluginFolder = (File) this.console.options.valueOf("plugins");
		if (pluginFolder.exists()) {
			try {
				Plugin[] plugins = this.pluginManager.loadPlugins(pluginFolder);
				Plugin[] arr$ = plugins;
				int len$ = plugins.length;

				for (int i$ = 0; i$ < len$; ++i$) {
					Plugin plugin = arr$[i$];
					this.loadPlugin(plugin);
				}
			} catch (Throwable var7) {
				Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE,
						var7.getMessage() + " (Is it up to date?)", var7);
			}
		} else {
			pluginFolder.mkdir();
		}

	}

	public void disablePlugins() {
		this.pluginManager.disablePlugins();
	}

	private void loadPlugin(Plugin plugin) {
		List<Command> pluginCommands = PluginCommandYamlParser.parse(plugin);
		if (!pluginCommands.isEmpty()) {
			this.commandMap.registerAll(plugin.getDescription().getName(), pluginCommands);
		}

		this.pluginManager.enablePlugin(plugin);
	}

	public String getName() {
		return "Craftbukkit";
	}

	public String getVersion() {
		return this.serverVersion + " (MC: " + "1.3" + ")";
	}

	public Player[] getOnlinePlayers() {
		List<EntityPlayer> online = this.server.b;
		Player[] players = new Player[online.size()];

		for (int i = 0; i < players.length; ++i) {
			players[i] = ((EntityPlayer) online.get(i)).a.getPlayer();
		}

		return players;
	}

	public Player getPlayer(String name) {
		Player[] players = this.getOnlinePlayers();
		Player found = null;
		String lowerName = name.toLowerCase();
		int delta = Integer.MAX_VALUE;
		Player[] arr$ = players;
		int len$ = players.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Player player = arr$[i$];
			if (player.getName().toLowerCase().startsWith(lowerName)) {
				int curDelta = player.getName().length() - lowerName.length();
				if (curDelta < delta) {
					found = player;
					delta = curDelta;
				}

				if (curDelta == 0) {
					break;
				}
			}
		}

		return found;
	}

	public int broadcastMessage(String message) {
		Player[] players = this.getOnlinePlayers();
		Player[] arr$ = players;
		int len$ = players.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Player player = arr$[i$];
			player.sendMessage(message);
		}

		return players.length;
	}

	public Player getPlayer(EntityPlayer entity) {
		return entity.a.getPlayer();
	}

	public List<Player> matchPlayer(String partialName) {
		List<Player> matchedPlayers = new ArrayList();
		Player[] arr$ = this.getOnlinePlayers();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Player iterPlayer = arr$[i$];
			String iterPlayerName = iterPlayer.getName();
			if (partialName.equalsIgnoreCase(iterPlayerName)) {
				matchedPlayers.clear();
				matchedPlayers.add(iterPlayer);
				break;
			}

			if (iterPlayerName.toLowerCase().indexOf(partialName.toLowerCase()) != -1) {
				matchedPlayers.add(iterPlayer);
			}
		}

		return matchedPlayers;
	}

	public int getMaxPlayers() {
		return this.server.e;
	}

	public PluginManager getPluginManager() {
		return this.pluginManager;
	}

	public BukkitScheduler getScheduler() {
		return this.scheduler;
	}

	public List<World> getWorlds() {
		return new ArrayList(this.worlds.values());
	}

	public ServerConfigurationManager getHandle() {
		return this.server;
	}

	public boolean dispatchCommand(CommandSender sender, String commandLine) {
		return this.commandMap.dispatch(sender, commandLine);
	}

	public void reload() {
		PropertyManager config = new PropertyManager(this.console.options);
		this.console.d = config;
		boolean animals = config.a("spawn-monsters", this.console.m);
		boolean monsters = config.a("spawn-monsters", ((WorldServer) this.console.worlds.get(0)).j > 0);
		this.console.l = config.a("online-mode", this.console.l);
		this.console.m = config.a("spawn-animals", this.console.m);
		this.console.n = config.a("pvp", this.console.n);
		Iterator i$ = this.console.worlds.iterator();

		while (i$.hasNext()) {
			WorldServer world = (WorldServer) i$.next();
			world.j = monsters ? 1 : 0;
			world.a(monsters, animals);
		}

		this.pluginManager.clearPlugins();
		this.commandMap.clearCommands();
		this.loadPlugins();
	}

	public String toString() {
		return "CraftServer{serverName=CraftbukkitserverVersion=" + this.serverVersion + "protocolVersion=" + "1.3"
				+ '}';
	}

	public World createWorld(String name, Environment environment) {
		File folder = new File(name);
		World world = this.getWorld(name);
		if (world != null) {
			return world;
		} else if (folder.exists() && !folder.isDirectory()) {
			throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
		} else {
			Convertable converter = new WorldLoaderServer(folder);
			if (converter.a(name)) {
				this.getLogger().info("Converting world '" + name + "'");
				converter.a(name, new ConvertProgressUpdater(this.console));
			}

			WorldServer internal = new WorldServer(this.console, new ServerNBTManager(new File("."), name, true), name,
					environment == Environment.NETHER ? -1 : 0);
			internal.a(new WorldManager(this.console, internal));
			internal.j = 1;
			internal.a(true, true);
			this.console.f.a(internal);
			this.console.worlds.add(internal);
			short short1 = 196;
			long i = System.currentTimeMillis();

			for (int j = -short1; j <= short1; j += 16) {
				for (int k = -short1; k <= short1; k += 16) {
					long l = System.currentTimeMillis();
					if (l < i) {
						i = l;
					}

					if (l > i + 1000L) {
						int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
						int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;
						System.out.println("Preparing spawn area for " + name + ", " + j1 * 100 / i1 + "%");
						i = l;
					}

					ChunkCoordinates chunkcoordinates = internal.l();
					internal.u.d(chunkcoordinates.a + j >> 4, chunkcoordinates.c + k >> 4);

					while (internal.e()) {
						;
					}
				}
			}

			return new CraftWorld(internal);
		}
	}

	public MinecraftServer getServer() {
		return this.console;
	}

	public World getWorld(String name) {
		return (World) this.worlds.get(name.toLowerCase());
	}

	protected void addWorld(World world) {
		this.worlds.put(world.getName().toLowerCase(), world);
		this.pluginManager.callEvent(new WorldEvent(Type.WORLD_LOADED, world));
	}

	public Logger getLogger() {
		return MinecraftServer.a;
	}

	public ConsoleReader getReader() {
		return this.console.reader;
	}
}