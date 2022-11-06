package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class BlockStationary extends BlockFluids {

	protected BlockStationary(int i, Material material) {
		super(i, material);
		setTickOnLoad(false);
		if (material == Material.lava) {
			setTickOnLoad(true);
		}
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		if (world.getBlockId(i, j, k) == blockID) {
			func_22035_j(world, i, j, k);
		}
	}

	private void func_22035_j(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		world.field_1043_h = true;
		world.setBlockAndMetadata(i, j, k, blockID - 1, l);
		world.markBlocksDirty(i, j, k, i, j, k);
		world.scheduleBlockUpdate(i, j, k, blockID - 1, tickRate());
		world.field_1043_h = false;
	}

	public void updateTick(World world, int i, int j, int k, EaglercraftRandom random) {
		if (blockMaterial == Material.lava) {
			int l = random.nextInt(3);
			for (int i1 = 0; i1 < l; i1++) {
				i += random.nextInt(3) - 1;
				j++;
				k += random.nextInt(3) - 1;
				int j1 = world.getBlockId(i, j, k);
				if (j1 == 0) {
					if (func_301_k(world, i - 1, j, k) || func_301_k(world, i + 1, j, k)
							|| func_301_k(world, i, j, k - 1) || func_301_k(world, i, j, k + 1)
							|| func_301_k(world, i, j - 1, k) || func_301_k(world, i, j + 1, k)) {
						world.setBlockWithNotify(i, j, k, Block.fire.blockID);
						return;
					}
					continue;
				}
				if (Block.blocksList[j1].blockMaterial.getIsSolid()) {
					return;
				}
			}

		}
	}

	private boolean func_301_k(World world, int i, int j, int k) {
		return world.getBlockMaterial(i, j, k).getBurning();
	}
}
