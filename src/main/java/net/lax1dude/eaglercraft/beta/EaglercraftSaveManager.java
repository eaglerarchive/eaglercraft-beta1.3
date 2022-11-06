package net.lax1dude.eaglercraft.beta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.EaglerAdapterImpl2.FileEntry;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.SaveFormatComparator;
import net.minecraft.src.WorldInfo;

public class EaglercraftSaveManager implements ISaveFormat {
	
	public final String directory;
	
	public EaglercraftSaveManager(String dir) {
		this.directory = dir;
	}

	@Override
	public String formatName() {
		return "Eaglercraft VFS";
	}

	@Override
	public ISaveHandler loadWorldHandler(String s, boolean flag) {
		return new EaglercraftSaveHandler(directory + "/" + s);
	}

	@Override
	public List getWorldList(IProgressUpdate progress) {
		progress.displayLoadingString("Loading Worlds...", "just wait a moment");
		ArrayList<SaveFormatComparator> lst = new ArrayList<>();
		EaglerAdapter.listFilesAndDirectories(directory).forEach(new Consumer<FileEntry>() {
			@Override
			public void accept(FileEntry t) {
				if(!t.isDirectory) {
					return;
				}
				String folderName = t.getName();
				String dir = t.path;
				byte[] lvl = EaglerAdapter.readFile(dir + "/lvl");
				if(lvl != null) {
					try {
						NBTBase nbt = NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(lvl)));
						if(nbt instanceof NBTTagCompound) {
							WorldInfo w =  new WorldInfo((NBTTagCompound)nbt);
							String s1 = w.getWorldName();
							if (s1 == null || MathHelper.func_22282_a(s1)) {
								s1 = folderName;
							}
							lst.add(new SaveFormatComparator(folderName, s1, w.func_22301_l(), w.func_22306_g(), false));
						}else {
							throw new IOException("file '" + dir + "/lvl' does not contain an NBTTagCompound");
						}
					}catch(IOException e) {
						System.err.println("Failed to load world data for '" + directory + "/lvl'");
						System.err.println("It will be kept for future recovery");
						e.printStackTrace();
					}
				}
			}
		});
		return lst;
	}

	@Override
	public void flushCache() {
	}

	@Override
	public WorldInfo getWorldInfoForWorld(String s) {
		byte[] lvl = EaglerAdapter.readFile(directory + "/" + s + "/lvl");
		if(lvl != null) {
			try {
				NBTBase nbt = NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(lvl)));
				if(nbt instanceof NBTTagCompound) {
					return new WorldInfo((NBTTagCompound)nbt);
				}else {
					throw new IOException("file '" + directory + "/" + s + "/lvl' does not contain an NBTTagCompound");
				}
			}catch(IOException e) {
				System.err.println("Failed to load world data for '" + directory + "/lvl'");
				System.err.println("It will be kept for future recovery");
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void deleteWorldByDirectory(String s, IProgressUpdate progress) {
		FilesystemUtils.recursiveDeleteDirectoryWithProgress(directory + "/" + s, "Deleting World", "%i chunks", progress);
	}

	@Override
	public boolean worldNeedsConvert_maybe(String s) {
		return false;
	}

	@Override
	public boolean convertSave(String s, IProgressUpdate iprogressupdate) {
		return false;
	}

	@Override
	public void renameWorldData(String s, String s1) {
		byte[] lvl = EaglerAdapter.readFile(directory + "/" + s + "/lvl");
		if(lvl != null) {
			try {
				NBTBase nbt = NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(lvl)));
				if(nbt instanceof NBTTagCompound) {
					NBTTagCompound w = (NBTTagCompound)nbt;
					w.setString("LevelName", s1);
					ByteArrayOutputStream out = new ByteArrayOutputStream(lvl.length + 16 + s1.length() * 2); // should be large enough
					NBTBase.writeTag(w, new DataOutputStream(out));
					EaglerAdapter.writeFile(directory + "/" + s + "/lvl", out.toByteArray());
				}else {
					throw new IOException("file '" + directory + "/" + s + "/lvl' does not contain an NBTTagCompound");
				}
			}catch(IOException e) {
				System.err.println("Failed to load world data for '" + directory + "/lvl'");
				System.err.println("It will be kept for future recovery");
				e.printStackTrace();
			}
		}
	}

}
