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

public class CommandChangePassword extends EaglerCommand {

	public CommandChangePassword() {
		super("change-password");
		setNeedsOp(false);
		setAliases(Arrays.asList("change-pass"));
		setTooltip("Change you current password");
		setUsage("/change-password <password>");
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if(!EaglercraftServer.config.enablePasswordLogin()) {
			sender.sendMessage(ChatColor.RED + "Error: password login is disabled");
			return;
		}
		if(sender instanceof Player) {
			if(!EaglercraftServer.hasPasswordDB()) {
				sender.sendMessage(ChatColor.RED + "Error: the password database is not initialized, it probably won't save your changes");
			}
			if(args.length != 1) {
				throw new IncorrectUsageException("this command only takes 1 argument!");
			}
			if(args[0].length() < 3) {
				throw new IncorrectUsageException("A password must be at least 3 characters!");
			}
			if(((CraftPlayer)sender).getHandle().a.b instanceof EaglercraftWebsocketNetworkManager) {
				if(!EaglercraftServer.config.allowSelfChangePassword()) {
					sender.sendMessage(ChatColor.RED + "Error: you are not allowed to change your password");
					return;
				}
				PasswordEntry et = PasswordManager.load(((Player)sender).getName());
				if(et != null) {
					PasswordManager.create(et.username, args[0], et.expiresAfter);
					sender.sendMessage("Your password for '" + et.username + "' was changed.");
				}else {
					sender.sendMessage(ChatColor.RED + "You do not have a password!");
				}
			}else {
				sender.sendMessage(ChatColor.RED + "You are not connected via websocket, so this command is unavailable");
			}
		}else {
			sender.sendMessage(ChatColor.RED + "Error: only players can run this command");
		}
	}

}
