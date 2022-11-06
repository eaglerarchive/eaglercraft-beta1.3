package net.lax1dude.eaglercraft.anvil;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;

public class SaveHandler implements ISaveHandler {

	public SaveHandler(File file, String s, boolean flag) {
		field_22155_b = new File(file, s);
		field_22155_b.mkdirs();
		field_22158_c = new File(field_22155_b, "players");
		if (flag) {
			field_22158_c.mkdirs();
		}
		func_22154_d();
	}

	private void func_22154_d() {
		try {
			File file = new File(field_22155_b, "session.lock");
			DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));
			try {
				dataoutputstream.writeLong(field_22157_d);
			} finally {
				dataoutputstream.close();
			}
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
			throw new RuntimeException("Failed to check session lock, aborting");
		}
	}

	protected File func_22153_a() {
		return field_22155_b;
	}

	public void checkSessionLock() {
		try {
			File file = new File(field_22155_b, "session.lock");
			DataInputStream datainputstream = new DataInputStream(new FileInputStream(file));
			try {
				if (datainputstream.readLong() != field_22157_d) {
					throw new MinecraftException("The save is being accessed from another location, aborting");
				}
			} finally {
				datainputstream.close();
			}
		} catch (IOException ioexception) {
			throw new MinecraftException("Failed to check session lock, aborting");
		}
	}

	public IChunkLoader getChunkLoader(WorldProvider worldprovider) {
		if (worldprovider instanceof WorldProviderHell) {
			File file = new File(field_22155_b, "DIM-1");
			file.mkdirs();
			return new ChunkLoader(file, true);
		} else {
			return new ChunkLoader(field_22155_b, true);
		}
	}

	public WorldInfo getWorldInfo() {
		File file = new File(field_22155_b, "level.dat");
		if (file.exists()) {
			try {
				NBTTagCompound nbttagcompound = CompressedStreamTools.func_1138_a(new FileInputStream(file));
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
				return new WorldInfo(nbttagcompound1);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	public void saveWorldAndPlayer(WorldInfo worldinfo, List list) {
		NBTTagCompound nbttagcompound = worldinfo.func_22305_a(list);
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setTag("Data", nbttagcompound);
		try {
			File file = new File(field_22155_b, "level.dat_new");
			File file1 = new File(field_22155_b, "level.dat_old");
			File file2 = new File(field_22155_b, "level.dat");
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound1, new FileOutputStream(file));
			if (file1.exists()) {
				file1.delete();
			}
			file2.renameTo(file1);
			if (file2.exists()) {
				file2.delete();
			}
			file.renameTo(file2);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void saveWorldInfo(WorldInfo worldinfo) {
		NBTTagCompound nbttagcompound = worldinfo.func_22299_a();
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setTag("Data", nbttagcompound);
		try {
			File file = new File(field_22155_b, "level.dat_new");
			File file1 = new File(field_22155_b, "level.dat_old");
			File file2 = new File(field_22155_b, "level.dat");
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound1, new FileOutputStream(file));
			if (file1.exists()) {
				file1.delete();
			}
			file2.renameTo(file1);
			if (file2.exists()) {
				file2.delete();
			}
			file.renameTo(file2);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private static final Logger field_22156_a = Logger.getLogger("Minecraft");
	private final File field_22155_b;
	private final File field_22158_c;
	private final long field_22157_d = System.currentTimeMillis();

}
