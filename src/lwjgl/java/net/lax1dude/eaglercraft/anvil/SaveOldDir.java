package net.lax1dude.eaglercraft.anvil;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;
import java.util.List;

import net.minecraft.src.IChunkLoader;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;

public class SaveOldDir extends SaveHandler {

	public SaveOldDir(File file, String s, boolean flag) {
		super(file, s, flag);
	}

	public IChunkLoader getChunkLoader(WorldProvider worldprovider) {
		File file = func_22153_a();
		if (worldprovider instanceof WorldProviderHell) {
			File file1 = new File(file, "DIM-1");
			file1.mkdirs();
			return new McRegionChunkLoader(file1);
		} else {
			return new McRegionChunkLoader(file);
		}
	}

	public void saveWorldAndPlayer(WorldInfo worldinfo, List list) {
		worldinfo.func_22289_d(19132);
		super.saveWorldAndPlayer(worldinfo, list);
	}
}
