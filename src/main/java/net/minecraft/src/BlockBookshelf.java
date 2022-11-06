package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class BlockBookshelf extends Block {

	public BlockBookshelf(int i, int j) {
		super(i, j, Material.wood);
	}

	public int getBlockTextureFromSide(int i) {
		if (i <= 1) {
			return 4;
		} else {
			return blockIndexInTexture;
		}
	}

	public int quantityDropped(EaglercraftRandom random) {
		return 0;
	}
}
