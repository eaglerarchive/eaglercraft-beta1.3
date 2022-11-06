package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class BlockLightStone extends Block {

	public BlockLightStone(int i, int j, Material material) {
		super(i, j, material);
	}

	public int idDropped(int i, EaglercraftRandom random) {
		return Item.lightStoneDust.shiftedIndex;
	}
}
