package net.lax1dude.eaglercraft.anvil;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.*;

import net.lax1dude.eaglercraft.beta.EaglercraftChunkLoader;
import net.minecraft.src.Chunk;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;

public class ChunkLoader implements IChunkLoader {

	public ChunkLoader(File file, boolean flag) {
		saveDir = file;
		createIfNecessary = flag;
	}

	private File chunkFileForXZ(int i, int j) {
		String s = (new StringBuilder()).append("c.").append(Integer.toString(i, 36)).append(".")
				.append(Integer.toString(j, 36)).append(".dat").toString();
		String s1 = Integer.toString(i & 0x3f, 36);
		String s2 = Integer.toString(j & 0x3f, 36);
		File file = new File(saveDir, s1);
		if (!file.exists()) {
			if (createIfNecessary) {
				file.mkdir();
			} else {
				return null;
			}
		}
		file = new File(file, s2);
		if (!file.exists()) {
			if (createIfNecessary) {
				file.mkdir();
			} else {
				return null;
			}
		}
		file = new File(file, s);
		if (!file.exists() && !createIfNecessary) {
			return null;
		} else {
			return file;
		}
	}

	public Chunk loadChunk(World world, int i, int j) {
		File file = chunkFileForXZ(i, j);
		if (file != null && file.exists()) {
			try {
				FileInputStream fileinputstream = new FileInputStream(file);
				NBTTagCompound nbttagcompound = CompressedStreamTools.func_1138_a(fileinputstream);
				if (!nbttagcompound.hasKey("Level")) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j)
							.append(" is missing level data, skipping").toString());
					return null;
				}
				if (!nbttagcompound.getCompoundTag("Level").hasKey("Blocks")) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j)
							.append(" is missing block data, skipping").toString());
					return null;
				}
				Chunk chunk = EaglercraftChunkLoader.loadChunkIntoWorldFromCompound(world, nbttagcompound.getCompoundTag("Level"));
				if (!chunk.isAtLocation(i, j)) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j)
							.append(" is in the wrong location; relocating. (Expected ").append(i).append(", ")
							.append(j).append(", got ").append(chunk.xPosition).append(", ").append(chunk.zPosition)
							.append(")").toString());
					nbttagcompound.setInteger("xPos", i);
					nbttagcompound.setInteger("zPos", j);
					chunk = EaglercraftChunkLoader.loadChunkIntoWorldFromCompound(world, nbttagcompound.getCompoundTag("Level"));
				}
				return chunk;
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	public void saveChunk(World world, Chunk chunk) {
		world.checkSessionLock();
		File file = chunkFileForXZ(chunk.xPosition, chunk.zPosition);
		if (file.exists()) {
			WorldInfo worldinfo = world.func_22144_v();
			worldinfo.func_22297_b(worldinfo.func_22306_g() - file.length());
		}
		try {
			File file1 = new File(saveDir, "tmp_chunk.dat");
			FileOutputStream fileoutputstream = new FileOutputStream(file1);
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound.setTag("Level", nbttagcompound1);
			EaglercraftChunkLoader.storeChunkInCompound(chunk, world, nbttagcompound1);
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound, fileoutputstream);
			fileoutputstream.close();
			if (file.exists()) {
				file.delete();
			}
			file1.renameTo(file);
			WorldInfo worldinfo1 = world.func_22144_v();
			worldinfo1.func_22297_b(worldinfo1.func_22306_g() + file.length());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void func_814_a() {
	}

	public void saveExtraData() {
	}

	public void saveExtraChunkData(World world, Chunk chunk) {
	}

	private File saveDir;
	private boolean createIfNecessary;
}
