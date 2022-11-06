package net.lax1dude.eaglercraft.beta.server.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.lax1dude.eaglercraft.beta.server.EaglercraftServer;
import net.lax1dude.eaglercraft.beta.server.PasswordManager;
import net.lax1dude.eaglercraft.beta.server.PasswordManager.PasswordEntry;
import net.minecraft.server.EaglercraftWebsocketNetworkManager;

public class CommandPasswordExpires extends EaglerCommand {

	public CommandPasswordExpires() {
		super("password-expires");
		setNeedsOp(false);
		setAliases(Arrays.asList("pass-expires", "password-expire", "pass-expire"));
		setTooltip("Checks how long until the password for a username, or your own username, expires and gets deleted");
		setUsage("/password-expires [username]");
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if(!EaglercraftServer.config.enablePasswordLogin()) {
			sender.sendMessage(ChatColor.RED + "Error: password login is disabled");
			return;
		}
		if(!EaglercraftServer.hasPasswordDB()) {
			sender.sendMessage(ChatColor.RED + "Error: the password database is not initialized, it probably won't save your changes");
		}
		boolean other = true;
		if(sender instanceof Player) {
			if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase(((Player)sender).getName()))) {
				if(((CraftPlayer)sender).getHandle().a.b instanceof EaglercraftWebsocketNetworkManager) {
					PasswordEntry et = PasswordManager.load(((Player)sender).getName());
					if(et != null) {
						sender.sendMessage("Your password will expire after " + CommandListPasswords.expiresAfter(et.secondsRemaining()) + ".");
					}else {
						sender.sendMessage(ChatColor.RED + "You do not have a password!");
					}
				}else {
					sender.sendMessage(ChatColor.RED + "You are not connected via websocket, so this command is unavailable");
				}
				other = false;
			}
		}
		if(other) {
			if(sender.isOp()) {
				PasswordEntry et = PasswordManager.load(args[0]);
				if(et != null) {
					sender.sendMessage("The password for username '" + args[0] + "' will expire after " + CommandListPasswords.expiresAfter(et.secondsRemaining()) + ".");
				}else {
					sender.sendMessage(ChatColor.RED + "That username does not have a password");
				}
			}else {
				sender.sendMessage(ChatColor.RED + "Error: you need /op to use this command");
			}
		}
	}

}
