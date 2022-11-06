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

public class CommandRenewPassword extends EaglerCommand {

	public CommandRenewPassword() {
		super("renew-password");
		setAliases(Arrays.asList("renew-pass", "password-renew", "pass-renew"));
		setNeedsOp(false);
		setTooltip("Renews a password for a username, or your own username, for Eaglercraft connections");
		setUsage("\"/renew-password\" or \"/renew-password [time]\" or, for ops, \"/renew-password [username]\" or \"/renew-password [username] [time]\"");
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if(!EaglercraftServer.config.enablePasswordLogin()) {
			sender.sendMessage(ChatColor.RED + "Error: password login is disabled");
			return;
		}
		
		boolean isPlayer = sender instanceof Player;
		boolean isWsPlayer = isPlayer && ((CraftPlayer)sender).getHandle().a.b instanceof EaglercraftWebsocketNetworkManager;
		boolean isSelfPlayer = isPlayer && args.length >= 1 && args[0].equalsIgnoreCase(((Player)sender).getName());
		int is1stTime = args.length >= 1 ? tryParseTime(args[0]) : -1;
		int is2stTime = args.length >= 2 ? tryParseTime(args[1]) : -1;
		
		int mpe = EaglercraftServer.config.maximumPasswordExpireTime();
		if(is1stTime > mpe || is2stTime > mpe) {
			sender.sendMessage(ChatColor.RED + "Error: the maximum time before a password expires can be at most " + CommandListPasswords.expiresAfter(mpe));
			return;
		}
		
		if(!EaglercraftServer.config.allowPasswordsWithoutExpire() && (is1stTime == -2 || is2stTime == -2)) {
			sender.sendMessage(ChatColor.RED + "Error: passwords that do not expire are not enabled on this server!");
			return;
		}
		
		if(!EaglercraftServer.hasPasswordDB()) {
			sender.sendMessage(ChatColor.RED + "Error: the password database is not initialized, it probably won't save your changes");
		}
		
		if(args.length == 0 || (args.length == 1 && isSelfPlayer)) {
			if(isPlayer && (isWsPlayer || sender.isOp())) {
				if(!sender.isOp() && !EaglercraftServer.config.allowSelfRenewPassword()) {
					sender.sendMessage(ChatColor.RED + "Error: you cannot renew your password on this server");
					return;
				}
				int n = PasswordManager.changeExpires(((Player)sender).getName(), -1);
				if(n > 0) {
					sender.sendMessage("Your password will expire in " + CommandListPasswords.expiresAfter(n) + ".");
				}else {
					if(n == -2) {
						sender.sendMessage("Your password is not going to expire.");
					}else {
						sender.sendMessage(ChatColor.RED + "Error: you do not have a password to renew");
					}
				}
			}else {
				sender.sendMessage(ChatColor.RED + "Error: you need to be logged in via websocket to use this command");
			}
		}else if(args.length == 1 && is1stTime != -1) {
			if(isPlayer && (isWsPlayer || sender.isOp())) {
				if(!sender.isOp() && !EaglercraftServer.config.allowSelfRenewPassword()) {
					sender.sendMessage(ChatColor.RED + "Error: you cannot renew your password on this server");
					return;
				}
				System.out.println(EaglercraftServer.config.allowSelfRenewPasswordWithTime());
				if(!sender.isOp() && !EaglercraftServer.config.allowSelfRenewPasswordWithTime()) {
					sender.sendMessage(ChatColor.RED + "Error: you cannot manually set the time until your password expires on this server");
					return;
				}
				PasswordEntry pe = PasswordManager.load(((Player)sender).getName());
				if(pe.expiresAfter == -1) {
					sender.sendMessage("Your password is not going to expire.");
				}else {
					if(!sender.isOp() && is1stTime == -2) {
						sender.sendMessage(ChatColor.RED + "Error: you cannot renew your password not to expire");
						return;
					}else {
						int n = PasswordManager.changeExpires(((Player)sender).getName(), is1stTime);
						if(n > 0) {
							sender.sendMessage("Your password will expire in " + CommandListPasswords.expiresAfter(n) + ".");
						}else {
							if(n == -2) {
								sender.sendMessage("Your password is not going to expire.");
							}else {
								sender.sendMessage(ChatColor.RED + "Error: you do not have a password to renew");
							}
						}
					}
				}
			}else {
				sender.sendMessage(ChatColor.RED + "Error: you need to be logged in via websocket to use this command");
			}
		}else if(args.length == 2 && isSelfPlayer && is2stTime != -1) {
			if(isPlayer && (isWsPlayer || sender.isOp())) {
				if(!sender.isOp() && !EaglercraftServer.config.allowSelfRenewPassword()) {
					sender.sendMessage(ChatColor.RED + "Error: you cannot renew your password on this server");
					return;
				}
				if(!sender.isOp() && !EaglercraftServer.config.allowSelfRenewPasswordWithTime()) {
					sender.sendMessage(ChatColor.RED + "Error: you cannot manually set the time until your password expires on this server");
					return;
				}
				PasswordEntry pe = PasswordManager.load(((Player)sender).getName());
				if(pe.expiresAfter == -1) {
					sender.sendMessage("Your password is not going to expire.");
				}else {
					if(!sender.isOp() && is2stTime == -2) {
						sender.sendMessage(ChatColor.RED + "Error: you cannot renew your password not to expire");
						return;
					}else {
						int n = PasswordManager.changeExpires(((Player)sender).getName(), is2stTime);
						if(n > 0) {
							sender.sendMessage("Your password will expire in " + CommandListPasswords.expiresAfter(n) + ".");
						}else {
							if(n == -2) {
								sender.sendMessage("Your password is not going to expire.");
							}else {
								sender.sendMessage(ChatColor.RED + "Error: you do not have a password to renew");
							}
						}
					}
				}
			}else {
				sender.sendMessage(ChatColor.RED + "Error: you need to be logged in via websocket to use this command");
			}
		}else if(args.length == 2 && is2stTime != -1) {
			if(sender.isOp()) {
				int n = PasswordManager.changeExpires(args[0], is2stTime);
				if(n > 0) {
					sender.sendMessage("The password for '" + args[0] + "' will expire in " + CommandListPasswords.expiresAfter(n) + ".");
				}else {
					if(n == -2) {
						sender.sendMessage("The password for '" + args[0] + "' will not expire.");
					}else {
						sender.sendMessage(ChatColor.RED + "Error: this player '" + args[0] + "' does not have a password to renew");
					}
				}
			}else {
				sender.sendMessage(ChatColor.RED + "Error: you need /op to use this command!");
			}
		}else {
			throw new IncorrectUsageException("Illegal argument combination");
		}
	}
	
	public static int tryParseTime(String str) {
		int expires = -1;
		if(str.length() >= 2) {
			if(str.equalsIgnoreCase("never") || str.equalsIgnoreCase("infinite") || str.equalsIgnoreCase("infinity")) {
				return -2;
			}
			int mul = 60 * 60 * 24;
			String exp = str.toLowerCase();
			if(exp.endsWith("s")) {
				mul = 1;
				exp = exp.substring(0, exp.length() - 1);
			}else if(exp.endsWith("m")) {
				mul = 60;
				exp = exp.substring(0, exp.length() - 1);
			}else if(exp.endsWith("h")) {
				mul = 60 * 60;
				exp = exp.substring(0, exp.length() - 1);
			}else if(exp.endsWith("d")) {
				exp = exp.substring(0, exp.length() - 1);
			}else if(exp.endsWith("w")) {
				mul = 60 * 60 * 24 * 7;
				exp = exp.substring(0, exp.length() - 1);
			}
			try {
				expires = Integer.parseInt(exp) * mul;
			}catch(NumberFormatException ex) {
			}
		}
		return expires < 0 ? -1 : expires;
	}
}
