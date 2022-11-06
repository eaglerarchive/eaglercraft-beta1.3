package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class WorldGenHellLava extends WorldGenerator {

	public WorldGenHellLava(int i) {
		field_4158_a = i;
	}

	public boolean generate(World world, EaglercraftRandom random, int i, int j, int k) {
		if (world.getBlockId(i, j + 1, k) != Block.bloodStone.blockID) {
			return false;
		}
		if (world.getBlockId(i, j, k) != 0 && world.getBlockId(i, j, k) != Block.bloodStone.blockID) {
			return false;
		}
		int l = 0;
		if (world.getBlockId(i - 1, j, k) == Block.bloodStone.blockID) {
			l++;
		}
		if (world.getBlockId(i + 1, j, k) == Block.bloodStone.blockID) {
			l++;
		}
		if (world.getBlockId(i, j, k - 1) == Block.bloodStone.blockID) {
			l++;
		}
		if (world.getBlockId(i, j, k + 1) == Block.bloodStone.blockID) {
			l++;
		}
		if (world.getBlockId(i, j - 1, k) == Block.bloodStone.blockID) {
			l++;
		}
		int i1 = 0;
		if (world.isAirBlock(i - 1, j, k)) {
			i1++;
		}
		if (world.isAirBlock(i + 1, j, k)) {
			i1++;
		}
		if (world.isAirBlock(i, j, k - 1)) {
			i1++;
		}
		if (world.isAirBlock(i, j, k + 1)) {
			i1++;
		}
		if (world.isAirBlock(i, j - 1, k)) {
			i1++;
		}
		if (l == 4 && i1 == 1) {
			world.setBlockWithNotify(i, j, k, field_4158_a);
			world.scheduledUpdatesAreImmediate = true;
			Block.blocksList[field_4158_a].updateTick(world, i, j, k, random);
			world.scheduledUpdatesAreImmediate = false;
		}
		return true;
	}

	private int field_4158_a;
}
