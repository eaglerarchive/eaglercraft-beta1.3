package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class BlockStone extends Block {

	public BlockStone(int i, int j) {
		super(i, j, Material.rock);
	}

	public int idDropped(int i, EaglercraftRandom random) {
		return Block.cobblestone.blockID;
	}
}
