package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class BlockObsidian extends BlockStone {

	public BlockObsidian(int i, int j) {
		super(i, j);
	}

	public int quantityDropped(EaglercraftRandom random) {
		return 1;
	}

	public int idDropped(int i, EaglercraftRandom random) {
		return Block.obsidian.blockID;
	}
}
