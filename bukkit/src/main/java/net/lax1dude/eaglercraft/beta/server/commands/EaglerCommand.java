package net.lax1dude.eaglercraft.beta.server.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class EaglerCommand extends Command {
	
	protected static class IncorrectUsageException extends RuntimeException {
		public IncorrectUsageException(String message, Throwable cause) {
			super(message, cause);
		}
		public IncorrectUsageException(String message) {
			super(message);
		}
	}
	
	private boolean needsOp = true;
	
	public EaglerCommand(String name) {
		super(name);
	}
	
	public void setNeedsOp(boolean b) {
		needsOp = b;
	}
	
	public boolean getNeedsOp() {
		return needsOp;
	}

	protected abstract void execute(CommandSender sender, String[] args);
	
	public boolean execute(CommandSender var1, String var2, String[] var3) {
		if(needsOp && !var1.isOp()) {
			var1.sendMessage(ChatColor.RED + "Error: you need /op to use this command!");
			return false;
		}else {
			try {
				execute(var1, var3);
			}catch(IncorrectUsageException ex) {
				var1.sendMessage(ChatColor.RED + "Error: " + ex.getMessage());
				var1.sendMessage(ChatColor.RED + "Usage: " + getUsage());
				Throwable t = ex.getCause();
				if(t != null) {
					var1.sendMessage(ChatColor.RED + "(caused by: " + t.getClass().getSimpleName() + ": " + t.getLocalizedMessage() + ")");
					System.out.println("Exception while executing '" + getName() + "':");
					t.printStackTrace();
				}
			}catch(Throwable t) {
				var1.sendMessage(ChatColor.RED + "Exception while executing '" + getName() + "'! " + t.toString());
				System.out.println("Exception while executing '" + getName() + "'!");
				t.printStackTrace();
			}
			return true;
		}
	}

}
