package net.lax1dude.eaglercraft.beta.server.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.lax1dude.eaglercraft.beta.server.EaglercraftServer;
import net.lax1dude.eaglercraft.beta.server.PasswordManager;

public class CommandSetPassword extends EaglerCommand {

	public CommandSetPassword() {
		super("set-password");
		setNeedsOp(true);
		setAliases(Arrays.asList("set-pass"));
		setTooltip("Sets a password on a username for Eaglercraft connections");
		setUsage("/set-password <username> <password> [expires][s|m|h|d|w]");
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
		if(args.length != 2 && args.length != 3) {
			throw new IncorrectUsageException("this command takes 2 or 3 arguments!");
		}
		if(args[0].length() > 16) {
			throw new IncorrectUsageException("the maximum length for a username is 16 characters!");
		}
		if(args[1].length() < 3) {
			throw new IncorrectUsageException("A password must be at least 3 characters!");
		}
		int expires = EaglercraftServer.config.defaultPasswordExpireTime();
		if(args.length == 3) {
			expires = CommandRenewPassword.tryParseTime(args[2]);
			if(expires == -1) {
				throw new IncorrectUsageException("Expires time is invalid!");
			}
			if(expires == -2) {
				expires = -1;
			}
		}
		if(expires > EaglercraftServer.config.maximumPasswordExpireTime()) {
			sender.sendMessage(ChatColor.RED + "Error: the maximum time before a password expires can be at most " + CommandListPasswords.expiresAfter(EaglercraftServer.config.maximumPasswordExpireTime()));
			return;
		}
		if(expires == -1 && !EaglercraftServer.config.allowPasswordsWithoutExpire()) {
			sender.sendMessage(ChatColor.RED + "Error: passwords that do not expire are disabled!");
			return;
		}
		PasswordManager.create(args[0], args[1], expires);
		sender.sendMessage("Password for '" + args[0] + "' was changed");
	}

}
