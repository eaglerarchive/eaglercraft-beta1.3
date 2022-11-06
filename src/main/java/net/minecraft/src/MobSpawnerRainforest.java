package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class MobSpawnerRainforest extends MobSpawnerBase {

	public MobSpawnerRainforest() {
	}

	public WorldGenerator getRandomWorldGenForTrees(EaglercraftRandom random) {
		if (random.nextInt(3) == 0) {
			return new WorldGenBigTree();
		} else {
			return new WorldGenTrees();
		}
	}
}
