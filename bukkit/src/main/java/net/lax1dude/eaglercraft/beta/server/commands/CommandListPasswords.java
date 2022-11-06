package net.lax1dude.eaglercraft.beta.server.commands;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.lax1dude.eaglercraft.beta.server.EaglercraftServer;
import net.lax1dude.eaglercraft.beta.server.PasswordManager;
import net.lax1dude.eaglercraft.beta.server.PasswordManager.PasswordEntry;

public class CommandListPasswords extends EaglerCommand {

	public CommandListPasswords() {
		super("list-passwords");
		setAliases(Arrays.asList("list-pass", "pass-list", "list-password", "passwords-list", "password-list"));
		setUsage("/list-passwords");
		setTooltip("List all usernames in the password list");
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if(!EaglercraftServer.config.enablePasswordLogin()) {
			sender.sendMessage(ChatColor.RED + "Error: password login is disabled");
			return;
		}
		if(args.length != 0) {
			throw new IncorrectUsageException("This command does not take any arguments");
		}
		if(!EaglercraftServer.hasPasswordDB()) {
			sender.sendMessage(ChatColor.RED + "Error: the password database is not loaded");
		}
		Collection<PasswordEntry> cc = PasswordManager.getPasswordList();
		if(cc.size() <= 0) {
			sender.sendMessage("No passwords are set on this server");
		}else {
			sender.sendMessage("Usernames with passwords:");
			int characterWidth = (sender instanceof Player) ? 60 : 40;
			String row = "";
			for(PasswordEntry s : cc) {
				String rowAdd = s.username + (s.expiresAfter <= 0 ? " (*)" : " (" + expiresAfter(s.secondsRemaining()) + ")");
				if(row.length() + rowAdd.length() + 2 > characterWidth) {
					sender.sendMessage(" " + row);
					row = "";
				}
				if(row.length() > 0) {
					row = row + ", " + rowAdd;
				}else {
					row = rowAdd;
				}
			}
			if(row.length() > 0) {
				sender.sendMessage(" " + row);
			}
		}
	}
	
	public static String expiresAfter(int remaining) {
		if(remaining < 0) {
			return "never";
		}
		if(remaining < 60) {
			return remaining + "s";
		}else if(remaining < 60 * 60) {
			return (remaining / 60) + "m";
		}else if(remaining < 60 * 60 * 24) {
			return (remaining / 3600) + "h";
		}else if(remaining < 60 * 60 * 24 * 7) {
			return (remaining / 3600 / 24) + "d";
		}else if(remaining < 60 * 60 * 24 * 7) {
			return (remaining / 3600 / 24) + "d";
		}else {
			return (remaining / 3600 / 24 / 7) + "w";
		}
	}

}
