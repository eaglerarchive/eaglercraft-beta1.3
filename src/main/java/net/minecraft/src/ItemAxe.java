package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ItemAxe extends ItemTool {

	protected ItemAxe(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 3, enumtoolmaterial, blocksEffectiveAgainst);
	}

	private static Block blocksEffectiveAgainst[];

	static {
		blocksEffectiveAgainst = (new Block[] { Block.planks, Block.bookShelf, Block.wood, Block.crate });
	}
}
