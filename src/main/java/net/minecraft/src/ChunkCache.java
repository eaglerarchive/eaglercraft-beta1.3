package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ChunkCache implements IBlockAccess {

	public ChunkCache(World world, int i, int j, int k, int l, int i1, int j1) {
		worldObj = world;
		chunkX = i >> 4;
		chunkZ = k >> 4;
		int k1 = l >> 4;
		int l1 = j1 >> 4;
		chunkArray = new Chunk[(k1 - chunkX) + 1][(l1 - chunkZ) + 1];
		for (int i2 = chunkX; i2 <= k1; i2++) {
			for (int j2 = chunkZ; j2 <= l1; j2++) {
				chunkArray[i2 - chunkX][j2 - chunkZ] = world.getChunkFromChunkCoords(i2, j2);
			}

		}

	}

	public int getBlockId(int i, int j, int k) {
		if (j < 0) {
			return 0;
		}
		if (j >= 128) {
			return 0;
		}
		int l = (i >> 4) - chunkX;
		int i1 = (k >> 4) - chunkZ;
		if (l < 0 || l >= chunkArray.length || i1 < 0 || i1 >= chunkArray[l].length) {
			return 0;
		}
		Chunk chunk = chunkArray[l][i1];
		if (chunk == null) {
			return 0;
		} else {
			return chunk.getBlockID(i & 0xf, j, k & 0xf);
		}
	}

	public TileEntity getBlockTileEntity(int i, int j, int k) {
		int l = (i >> 4) - chunkX;
		int i1 = (k >> 4) - chunkZ;
		return chunkArray[l][i1].getChunkBlockTileEntity(i & 0xf, j, k & 0xf);
	}

	public float getLightBrightness(int i, int j, int k) {
		return worldObj.worldProvider.lightBrightnessTable[func_4086_d(i, j, k)];
	}

	public int func_4086_d(int i, int j, int k) {
		return func_716_a(i, j, k, true);
	}

	public int func_716_a(int i, int j, int k, boolean flag) {
		if (i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800) {
			return 15;
		}
		if (flag) {
			int l = getBlockId(i, j, k);
			if (l == Block.stairSingle.blockID || l == Block.tilledField.blockID) {
				int k1 = func_716_a(i, j + 1, k, false);
				int i2 = func_716_a(i + 1, j, k, false);
				int j2 = func_716_a(i - 1, j, k, false);
				int k2 = func_716_a(i, j, k + 1, false);
				int l2 = func_716_a(i, j, k - 1, false);
				if (i2 > k1) {
					k1 = i2;
				}
				if (j2 > k1) {
					k1 = j2;
				}
				if (k2 > k1) {
					k1 = k2;
				}
				if (l2 > k1) {
					k1 = l2;
				}
				return k1;
			}
		}
		if (j < 0) {
			return 0;
		}
		if (j >= 128) {
			int i1 = 15 - worldObj.skylightSubtracted;
			if (i1 < 0) {
				i1 = 0;
			}
			return i1;
		} else {
			int j1 = (i >> 4) - chunkX;
			int l1 = (k >> 4) - chunkZ;
			return chunkArray[j1][l1].getBlockLightValue(i & 0xf, j, k & 0xf, worldObj.skylightSubtracted);
		}
	}

	public int getBlockMetadata(int i, int j, int k) {
		if (j < 0) {
			return 0;
		}
		if (j >= 128) {
			return 0;
		} else {
			int l = (i >> 4) - chunkX;
			int i1 = (k >> 4) - chunkZ;
			return chunkArray[l][i1].getBlockMetadata(i & 0xf, j, k & 0xf);
		}
	}

	public Material getBlockMaterial(int i, int j, int k) {
		int l = getBlockId(i, j, k);
		if (l == 0) {
			return Material.air;
		} else {
			return Block.blocksList[l].blockMaterial;
		}
	}

	public boolean isBlockOpaqueCube(int i, int j, int k) {
		Block block = Block.blocksList[getBlockId(i, j, k)];
		if (block == null) {
			return false;
		} else {
			return block.isOpaqueCube();
		}
	}

	public WorldChunkManager getWorldChunkManager() {
		return worldObj.getWorldChunkManager();
	}

	private int chunkX;
	private int chunkZ;
	private Chunk chunkArray[][];
	private World worldObj;
}
