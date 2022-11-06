package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class BlockGlass extends BlockBreakable {

	public BlockGlass(int i, int j, Material material, boolean flag) {
		super(i, j, material, flag);
	}

	public int quantityDropped(EaglercraftRandom random) {
		return 0;
	}
}
