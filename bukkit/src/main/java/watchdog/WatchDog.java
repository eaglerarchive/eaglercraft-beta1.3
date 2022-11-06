package watchdog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.craftbukkit.Main;

import net.minecraft.server.MinecraftServer;

public class WatchDog implements Runnable {
	
	private static int timeout = 30;
	
	public static void main(String[] args) {
		List<String> lst = new ArrayList();
		for(int i = 0; i < args.length; ++i) {
			if(args[i].equalsIgnoreCase("--crashTimeout") && i < args.length - 1) {
				try {
					timeout = Integer.parseInt(args[++i]);
				}catch(NumberFormatException ex) {
					System.err.println("Warning: crashTimeout seconds '" + args[i] + "' is invalid");
				}
				break;
			}else {
				lst.add(args[i]);
			}
		}
		Thread watchDog = new Thread(new WatchDog(), "CrashLoopDetector");
		watchDog.setDaemon(true);
		watchDog.start();
		Main.main(lst.toArray(new String[0]));
		
	}

	public void run() {
		main_loop: while(true) {
			while(MinecraftServer.watchDog == 0l) {
				try {
					Thread.sleep(100l);
				}catch(InterruptedException ex) {
				}
			}
			if(MinecraftServer.watchDog == -1l) {
				break main_loop;
			}
			System.out.println("Crash Loop Detector is Running! (" + timeout + "sec)");
			if(timeout == 30) {
				System.out.println("To increase, add \"--crashTimeout [sec]\" to the program's arguments");
			}
			while(true) {
				if(MinecraftServer.watchDog == -1l) {
					break main_loop;
				}else {
					long millis = System.currentTimeMillis();
					if(millis - MinecraftServer.watchDog > 1000l * timeout) {
						System.err.println("Server Crash Detected!");
						boolean b = false;
						System.err.println("-----------------------");
						for(Entry<Thread,StackTraceElement[]> el : Thread.getAllStackTraces().entrySet()) {
							System.err.println(" " + el.getKey().getName() + ":");
							if(el.getValue().length > 0) {
								for(StackTraceElement ell : el.getValue()) {
									System.err.println("     at " + ell.toString());
								}
							}else {
								System.err.println("     [No Stack Trace]");
							}
							System.err.println("-----------------------");
						}
						System.err.println("Program will exit.");
						Runtime.getRuntime().halt(-1);
						break main_loop;
					}
					try {
						Thread.sleep(5000l);
					}catch(InterruptedException ex) {
					}
				}
			}
		}
	}

}
