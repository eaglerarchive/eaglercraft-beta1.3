package net.lax1dude.eaglercraft;

import java.io.File;

import javax.swing.JOptionPane;

import net.lax1dude.eaglercraft.anvil.SaveConverterMcRegion;
import net.minecraft.client.Minecraft;

public class MinecraftMain {
	
	private static class MinecraftImpl extends Minecraft {
		
		@Override
		public void displayCrashScreen(Throwable t) {
			System.err.println("GAME CRASHED! Crash screen was requested");
			t.printStackTrace();
			EaglerAdapter.destroyContext();
			EaglerAdapter.exit();
		}
		
	}

	public static void main(String[] par0ArrayOfStr) {
		JOptionPane.showMessageDialog(null, "launch renderdoc (optionally) and press ok to continue", "eaglercraft",
				JOptionPane.PLAIN_MESSAGE);
		
		EaglerAdapter.initializeContext();
		LocalStorageManager.loadStorage();

		for(int i = 0; i < par0ArrayOfStr.length; ++i) {
			String arg = par0ArrayOfStr[i];
			if("--anvilSaveFormat".equalsIgnoreCase(arg)) {
				EaglerAdapter.configureSaveFormat(new SaveConverterMcRegion(new File("lwjgl_saves")));
			}
		}
		
		Minecraft mc = new MinecraftImpl();
		mc.run();

	}
}
