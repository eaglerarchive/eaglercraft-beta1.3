package net.lax1dude.eaglercraft.beta.server.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.lax1dude.eaglercraft.beta.server.EaglercraftServer;
import net.lax1dude.eaglercraft.beta.server.PasswordManager;
import net.minecraft.server.EaglercraftWebsocketNetworkManager;

public class CommandClearPassword extends EaglerCommand {

	public CommandClearPassword() {
		super("clear-password");
		setAliases(Arrays.asList("clear-pass", "remove-password", "remove-pass", "delete-password", "delete-pass"));
		setNeedsOp(false);
		setTooltip("Removes a password from a username, or your own username, for Eaglercraft connections");
		setUsage("/clear-password [username]");
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
		if(sender instanceof CraftPlayer && ((CraftPlayer)sender).getHandle().a.b instanceof EaglercraftWebsocketNetworkManager && (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase(((Player)sender).getName())))) {
			if(EaglercraftServer.config.allowSelfDeletePassword() || EaglercraftServer.config.allowSelfRegistration()) {
				if(PasswordManager.delete(((Player)sender).getName())) {
					if(EaglercraftServer.config.requirePasswordLogin()) {
						((Player)sender).kickPlayer("Your password was removed.");
					}else {
						sender.sendMessage("Your password was removed.");
					}
				}else {
					sender.sendMessage(ChatColor.RED + "You do not have a password on this account!");
				}
			}else {
				sender.sendMessage(ChatColor.RED + "You cannot remove the password from your username.");
			}
			return;
		}else if(args.length != 1) {
			throw new IncorrectUsageException("this command only takes 1 argument!");
		}
		if(sender.isOp())  {
			if(PasswordManager.delete(args[0])) {
				sender.sendMessage("Password for '" + args[0] + "' was removed");
			}else {
				sender.sendMessage(ChatColor.RED + "The user '" + args[0] + "' does not have a password!");
			}
		}else {
			sender.sendMessage(ChatColor.RED + "Error: you need /op to use this command!");
		}
		
	}

}
