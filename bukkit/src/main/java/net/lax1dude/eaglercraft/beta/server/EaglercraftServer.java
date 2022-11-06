package net.lax1dude.eaglercraft.beta.server;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

import net.lax1dude.eaglercraft.beta.server.PasswordManager.PasswordEntry;
import net.lax1dude.eaglercraft.beta.server.commands.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PacketRegister;

public class EaglercraftServer extends PlayerListener {
	
	private static final File dataFolder = new File(".");
	private static CraftServer server = null;
	private static CommandMap commands = null;
	private static PluginManager pluginManager = null;
	private static boolean passwordDBWorking = false;
	
	private static EaglercraftServer instance = null;
	
	public static final EaglercraftConfig config = new EaglercraftConfig();
	
	private static final Plugin dummyPlugin = new Plugin() {
		@Override public File getDataFolder() { return dataFolder; }
		@Override public PluginDescriptionFile getDescription() { return new PluginDescriptionFile("EaglercraftServer", "0.0.0", ""); }
		@Override public Configuration getConfiguration() { return null; }
		@Override public PluginLoader getPluginLoader() { return new PluginLoader() {
			@Override public Plugin loadPlugin(File var1) throws InvalidPluginException, InvalidDescriptionException {
				return dummyPlugin;
			}
			@Override public Pattern[] getPluginFileFilters() {
				return new Pattern[0];
			}
			@Override public EventExecutor createExecutor(Type var1, Listener var2) {
				if(var1 == Type.PLAYER_JOIN) {
					return new EventExecutor() { @Override public void execute(Listener var1, Event var2) {
						((PlayerListener)var1).onPlayerJoin((PlayerEvent)var2);
					}};
				}else {
					return null;
				}
			}
			@Override public void enablePlugin(Plugin var1) { }
			@Override public void disablePlugin(Plugin var1) { }
		}; }
		@Override public Server getServer() { return server; }
		@Override public boolean isEnabled() { return true; }
		@Override public void onDisable() { }
		@Override public void onEnable() { }
		@Override
		public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
			return false;
		}
	};
	
	static {
		PacketRegister.register(69, Packet69EaglercraftData.class);
	}

	public static void installHooks(CraftServer craftServer, CommandMap commandMap) {
		server = craftServer;
		commands = commandMap;
		pluginManager = craftServer.getPluginManager();
		instance = new EaglercraftServer();
		
		PasswordManager.log.info("Loading Eaglercraft Bukkit...");
		
		config.reload();
		
		if(config.enablePasswordLogin()) {
			try {
				PasswordManager.loadPasswordDB();
				passwordDBWorking = true;
			} catch (IOException e) {
				PasswordManager.log.severe("ERROR: could not create or load password database!");
				PasswordManager.log.severe("Eaglercraft will not function correctly, unless you disable password login");
				e.printStackTrace();
				passwordDBWorking = false;
			}
		}
		
		pluginManager.registerEvent(Type.PLAYER_JOIN, instance, Priority.Normal, dummyPlugin);

		registerCommand(new CommandSetPassword());
		registerCommand(new CommandChangePassword());
		registerCommand(new CommandClearPassword());
		registerCommand(new CommandListPasswords());
		registerCommand(new CommandPasswordExpires());
		registerCommand(new CommandRenewPassword());
		registerCommand(new CommandRegister());
		
	}
	
	public static boolean hasPasswordDB() {
		return passwordDBWorking;
	}

	private static void registerCommand(Command command) {
		commands.register(command.getName(), "eagler", command);
		for(String s : command.getAliases()) {
			commands.register(s, "eagler", command);
		}
	}
	
	@Override
	public void onPlayerJoin(PlayerEvent event) {
		if(config.enablePasswordLogin()) {
			PasswordEntry et = PasswordManager.load(event.getPlayer().getName());
			if(et != null && et.secondsRemaining() != Integer.MAX_VALUE) {
				event.getPlayer().sendMessage("Your password will expire after " + CommandListPasswords.expiresAfter(et.secondsRemaining()));
				if(config.allowSelfRenewPassword()) {
					event.getPlayer().sendMessage("Use /renew-password to extend");
				}
			}
		}
	}
	
	public static CraftServer getMinecraftServer() {
		return server;
	}
	
	public static PluginManager getPluginManager() {
		return pluginManager;
	}

}
