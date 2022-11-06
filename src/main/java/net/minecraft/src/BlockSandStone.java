package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class BlockSandStone extends Block {

	public BlockSandStone(int i) {
		super(i, 192, Material.rock);
	}

	public int getBlockTextureFromSide(int i) {
		if (i == 1) {
			return blockIndexInTexture - 16;
		}
		if (i == 0) {
			return blockIndexInTexture + 16;
		} else {
			return blockIndexInTexture;
		}
	}
}
