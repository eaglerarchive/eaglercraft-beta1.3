package net.lax1dude.eaglercraft.anvil;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

import net.lax1dude.eaglercraft.beta.EaglercraftChunkLoader;
import net.minecraft.src.Chunk;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;

public class McRegionChunkLoader implements IChunkLoader {

	public McRegionChunkLoader(File file) {
		field_22184_a = file;
	}

	public Chunk loadChunk(World world, int i, int j) {
		java.io.DataInputStream datainputstream = RegionFileCache.func_22194_c(field_22184_a, i, j);
		NBTTagCompound nbttagcompound;
		if (datainputstream != null) {
			try {
				nbttagcompound = CompressedStreamTools.func_1141_a(datainputstream);
			} catch (IOException e) {
				System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j)
						.append(" is corrupt:").append(e.toString()).toString());
				return null;
			}
		} else {
			return null;
		}
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
					.append(" is in the wrong location; relocating. (Expected ").append(i).append(", ").append(j)
					.append(", got ").append(chunk.xPosition).append(", ").append(chunk.zPosition).append(")")
					.toString());
			nbttagcompound.setInteger("xPos", i);
			nbttagcompound.setInteger("zPos", j);
			chunk = EaglercraftChunkLoader.loadChunkIntoWorldFromCompound(world, nbttagcompound.getCompoundTag("Level"));
		}
		return chunk;
	}

	public void saveChunk(World world, Chunk chunk) {
		world.checkSessionLock();
		try {
			DataOutputStream dataoutputstream = RegionFileCache.func_22190_d(field_22184_a, chunk.xPosition,
					chunk.zPosition);
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound.setTag("Level", nbttagcompound1);
			EaglercraftChunkLoader.storeChunkInCompound(chunk, world, nbttagcompound1);
			CompressedStreamTools.func_1139_a(nbttagcompound, dataoutputstream);
			dataoutputstream.close();
			WorldInfo worldinfo = world.func_22144_v();
			worldinfo.func_22297_b(worldinfo.func_22306_g()
					+ (long) RegionFileCache.func_22191_b(field_22184_a, chunk.xPosition, chunk.zPosition));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void saveExtraChunkData(World world, Chunk chunk) {
	}

	public void func_814_a() {
	}

	public void saveExtraData() {
	}

	private final File field_22184_a;
}
