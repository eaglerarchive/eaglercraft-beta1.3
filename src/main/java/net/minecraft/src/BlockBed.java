package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class BlockBed extends Block {

	public BlockBed(int i) {
		super(i, 134, Material.cloth);
		func_22027_j();
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		int l = world.getBlockMetadata(i, j, k);
		if (!func_22032_d(l)) {
			int i1 = func_22030_c(l);
			i += field_22033_a[i1][0];
			k += field_22033_a[i1][1];
			if (world.getBlockId(i, j, k) != blockID) {
				return true;
			}
			l = world.getBlockMetadata(i, j, k);
		}
		if (func_22029_f(l)) {
			entityplayer.func_22055_b("tile.bed.occupied");
			return true;
		}
		if (entityplayer.func_22053_b(i, j, k)) {
			func_22031_a(world, i, j, k, true);
			return true;
		} else {
			entityplayer.func_22055_b("tile.bed.noSleep");
			return true;
		}
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if (i == 0) {
			return Block.planks.blockIndexInTexture;
		}
		int k = func_22030_c(j);
		int l = ModelBed.field_22281_c[k][i];
		if (func_22032_d(j)) {
			if (l == 2) {
				return blockIndexInTexture + 2 + 16;
			}
			if (l == 5 || l == 4) {
				return blockIndexInTexture + 1 + 16;
			} else {
				return blockIndexInTexture + 1;
			}
		}
		if (l == 3) {
			return (blockIndexInTexture - 1) + 16;
		}
		if (l == 5 || l == 4) {
			return blockIndexInTexture + 16;
		} else {
			return blockIndexInTexture;
		}
	}

	public int getRenderType() {
		return 14;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		func_22027_j();
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		int i1 = world.getBlockMetadata(i, j, k);
		int j1 = func_22030_c(i1);
		if (func_22032_d(i1)) {
			if (world.getBlockId(i - field_22033_a[j1][0], j, k - field_22033_a[j1][1]) != blockID) {
				world.setBlockWithNotify(i, j, k, 0);
			}
		} else if (world.getBlockId(i + field_22033_a[j1][0], j, k + field_22033_a[j1][1]) != blockID) {
			world.setBlockWithNotify(i, j, k, 0);
			if (!world.multiplayerWorld) {
				dropBlockAsItem(world, i, j, k, i1);
			}
		}
	}

	public int idDropped(int i, EaglercraftRandom random) {
		if (func_22032_d(i)) {
			return 0;
		} else {
			return Item.field_22019_aY.shiftedIndex;
		}
	}

	private void func_22027_j() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
	}

	public static int func_22030_c(int i) {
		return i & 3;
	}

	public static boolean func_22032_d(int i) {
		return (i & 8) != 0;
	}

	public static boolean func_22029_f(int i) {
		return (i & 4) != 0;
	}

	public static void func_22031_a(World world, int i, int j, int k, boolean flag) {
		int l = world.getBlockMetadata(i, j, k);
		if (flag) {
			l |= 4;
		} else {
			l &= -5;
		}
		world.setBlockMetadataWithNotify(i, j, k, l);
	}

	public static ChunkCoordinates func_22028_g(World world, int i, int j, int k, int l) {
		int i1 = world.getBlockMetadata(i, j, k);
		int j1 = func_22030_c(i1);
		for (int k1 = 0; k1 <= 1; k1++) {
			int l1 = i - field_22033_a[j1][0] * k1 - 1;
			int i2 = k - field_22033_a[j1][1] * k1 - 1;
			int j2 = l1 + 2;
			int k2 = i2 + 2;
			for (int l2 = l1; l2 <= j2; l2++) {
				for (int i3 = i2; i3 <= k2; i3++) {
					if (!world.isBlockOpaqueCube(l2, j - 1, i3) || !world.isAirBlock(l2, j, i3)
							|| !world.isAirBlock(l2, j + 1, i3)) {
						continue;
					}
					if (l > 0) {
						l--;
					} else {
						return new ChunkCoordinates(l2, j, i3);
					}
				}

			}

		}

		return new ChunkCoordinates(i, j + 1, k);
	}

	public static final int field_22033_a[][] = { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };

}
