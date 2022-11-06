package net.lax1dude.eaglercraft.beta.server.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.lax1dude.eaglercraft.beta.server.EaglercraftServer;
import net.lax1dude.eaglercraft.beta.server.PasswordManager;
import net.minecraft.server.EaglercraftWebsocketNetworkManager;

public class CommandRegister extends EaglerCommand {

	public CommandRegister() {
		super("register-password");
		setAliases(Arrays.asList("register-pass", "password-register", "pass-register", "eagler-register", "eag-register"));
		setNeedsOp(false);
		setTooltip("Register a password for your username");
		setUsage("/register-password <password> [expires after][s|m|h|d|w]");
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
		if(sender instanceof Player && ((CraftPlayer)sender).getHandle().a.b instanceof EaglercraftWebsocketNetworkManager) {
			if(!EaglercraftServer.config.allowSelfRegistration()) {
				sender.sendMessage(ChatColor.RED + "Error: password registration is disabled");
				return;
			}
			if(PasswordManager.load(((Player)sender).getName()) != null) {
				sender.sendMessage(ChatColor.RED + "Error: you are already registered on this server, use /change-password to edit it");
			}else {
				if(args.length != 1 && args.length != 2) {
					throw new IncorrectUsageException("this command takes 1 or 2 arguments!");
				}
				if(args[0].length() < 3) {
					throw new IncorrectUsageException("A password must be at least 3 characters!");
				}
				int expires = EaglercraftServer.config.defaultPasswordExpireTime();
				if(args.length == 2) {
					expires = CommandRenewPassword.tryParseTime(args[1]);
					if(expires == -1) {
						throw new IncorrectUsageException("Expires time is invalid!");
					}
				}
				if(expires == -2 && !(EaglercraftServer.config.allowSelfRegistrationWithoutExpire() || !EaglercraftServer.config.allowPasswordsWithoutExpire())) {
					sender.sendMessage(ChatColor.RED + "Error: you cannot register a password");
					return;
				}
				if(expires > EaglercraftServer.config.maximumPasswordExpireTime()) {
					sender.sendMessage(ChatColor.RED + "Error: the maximum time before your password expires can be at most " + CommandListPasswords.expiresAfter(expires));
					return;
				}
				PasswordManager.create(((Player)sender).getName(), args[0], expires == -2 ? -1 : expires);
				sender.sendMessage("Your password was registered." + (expires == -2 ? "" : " It will expire in " + CommandListPasswords.expiresAfter(expires)));
			}
		}else {
			sender.sendMessage(ChatColor.RED + "Error: only players connected via websocket can use this command");
		}
	}

}
