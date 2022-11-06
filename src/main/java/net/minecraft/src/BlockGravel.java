package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class BlockGravel extends BlockSand {

	public BlockGravel(int i, int j) {
		super(i, j);
	}

	public int idDropped(int i, EaglercraftRandom random) {
		if (random.nextInt(10) == 0) {
			return Item.flint.shiftedIndex;
		} else {
			return blockID;
		}
	}
}
