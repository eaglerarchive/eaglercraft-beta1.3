package net.lax1dude.eaglercraft.beta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.beta.EPKDecompiler.FileEntry;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;

public class ImportExport {
	
	private static IProgressUpdate prog = null;
	private static String progressTitle = null;
	private static long lastProgressUpdate = 0l;
	
	private static String formatFloat(float f) {
		String ret = Float.toString(f);
		int idx = ret.indexOf('.');
		if(ret.length() >= (idx + 3)) {
			ret = ret.substring(0, idx + 3);
		}
		return ret;
	}
	
	private static void progress(int p) {
		long t = System.currentTimeMillis();
		if(t - lastProgressUpdate < 100l) {
			return;
		}
		lastProgressUpdate = t;
		String s;
		if(p < 1000) {
			s = "" + p + " B";
		}else if(p < 1000000) {
			s = "" + formatFloat(p / 1000f) + " kB";
		}else {
			s = "" + formatFloat(p / 1000000f) + " MB";
		}
		prog.displayLoadingString(progressTitle, s);
	}
	
	public static String importWorld(IProgressUpdate loadingScreen) {
		progressTitle = "Importing World";
		prog = loadingScreen;
		loadingScreen.displayLoadingString("Importing World", "(please wait)");
		EaglerAdapter.openFileChooser("epk", "application/epk");
		
		byte[] loaded;
		while((loaded = EaglerAdapter.getFileChooserResult()) == null) {
			long t = System.currentTimeMillis();
			if(t - lastProgressUpdate < 100l) {
				continue;
			}
			lastProgressUpdate = t;
			loadingScreen.displayLoadingString("Importing World", "(please wait)");
		}
		if(loaded.length == 0) {
			return "$cancelled$";
		}
		
		String name = EaglerAdapter.getFileChooserResultName();
		name = name.replaceAll("[^A-Za-z0-9\\-_]", "_").trim();
		
		while(EaglerAdapter.pathExists("saves/" + name)) {
			name = "_" + name;
		}
		
		loadingScreen.displayLoadingString("Importing World", "Extracting EPK");
		
		try {
			EPKDecompiler loader = new EPKDecompiler(loaded);
			int counter = 0;
			FileEntry f;
			while((f = loader.readFile()) != null) {
				EaglerAdapter.writeFile("saves/" + name + "/" + f.name, f.data);
				counter += f.data.length;
				progress(counter);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			NBTBase b = NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(EaglerAdapter.readFile("saves/" + name + "/lvl"))));
			if(!(b instanceof NBTTagCompound)) {
				throw new IOException("NBT in saves/" + name + "/lvl is corrupt!");
			}
		}catch(IOException e) {
			e.printStackTrace();
			System.err.println("The folder 'saves/" + name + "/' will be deleted");
			FilesystemUtils.recursiveDeleteDirectory("saves/" + name);
		}
		
		return name;
	}
	
	public static void renameImportedWorld(String name, String displayName) {
		byte[] lvl = EaglerAdapter.readFile("saves/" + name + "/lvl");
		if(lvl != null) {
			try {
				NBTBase nbt = NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(lvl)));
				if(nbt instanceof NBTTagCompound) {
					NBTTagCompound w = (NBTTagCompound)nbt;
					w.setString("LevelName", displayName);
					ByteArrayOutputStream out = new ByteArrayOutputStream(lvl.length + 16 + displayName.length() * 2); // should be large enough
					NBTBase.writeTag(w, new DataOutputStream(out));
					EaglerAdapter.writeFile("saves/" + name + "/lvl", out.toByteArray());
				}else {
					throw new IOException("file 'saves/" + name + "/lvl' does not contain an NBTTagCompound");
				}
			}catch(IOException e) {
				System.err.println("Failed to modify world data for 'saves/" + name + "/lvl'");
				System.err.println("It will be kept for future recovery");
				e.printStackTrace();
			}
		}
	}
	
	public static boolean exportWorld(IProgressUpdate loadingScreen, String name, String downloadName) {
		progressTitle = "Exporting World";
		prog = loadingScreen;
		loadingScreen.displayLoadingString("Exporting World", "(please wait)");
		
		if(!EaglerAdapter.fileExists("saves/" + name + "/lvl")) {
			return false;
		}
		
		int size = 0;
		String dir = "saves/" + name;
		
		try {
			EPKCompiler comp = new EPKCompiler(dir, 409600000);
			Collection<EaglerAdapter.FileEntry> lst = EaglerAdapter.listFilesRecursive(dir);
			Iterator<EaglerAdapter.FileEntry> itr = lst.iterator();
			while(itr.hasNext()) {
				EaglerAdapter.FileEntry t = itr.next();
				if(t.path.startsWith(dir + "/")) {
					byte[] dat = EaglerAdapter.readFile(t.path);
					if(dat != null) {
						String fn = t.path.substring(dir.length() + 1);
						comp.append(fn, dat);
						size += dat.length;
						progress(size);
					}
				}
			}
			loadingScreen.displayLoadingString("Exporting World", "finishing...");
			EaglerAdapter.downloadFile(downloadName, comp.complete());
			return true;
		}catch(Throwable t) {
			System.err.println("Export of '" + name + "' failed!");
			t.printStackTrace();
			return false;
		}
		
	}

}
