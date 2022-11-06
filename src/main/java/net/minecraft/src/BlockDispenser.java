package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class BlockDispenser extends BlockContainer {

	protected BlockDispenser(int i) {
		super(i, Material.rock);
		blockIndexInTexture = 45;
	}

	public int tickRate() {
		return 4;
	}

	public int idDropped(int i, EaglercraftRandom random) {
		return Block.dispenser.blockID;
	}

	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		setDispenserDefaultDirection(world, i, j, k);
	}

	private void setDispenserDefaultDirection(World world, int i, int j, int k) {
		int l = world.getBlockId(i, j, k - 1);
		int i1 = world.getBlockId(i, j, k + 1);
		int j1 = world.getBlockId(i - 1, j, k);
		int k1 = world.getBlockId(i + 1, j, k);
		byte byte0 = 3;
		if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1]) {
			byte0 = 3;
		}
		if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l]) {
			byte0 = 2;
		}
		if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1]) {
			byte0 = 5;
		}
		if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1]) {
			byte0 = 4;
		}
		world.setBlockMetadataWithNotify(i, j, k, byte0);
	}

	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 1) {
			return blockIndexInTexture + 17;
		}
		if (l == 0) {
			return blockIndexInTexture + 17;
		}
		int i1 = iblockaccess.getBlockMetadata(i, j, k);
		if (l != i1) {
			return blockIndexInTexture;
		} else {
			return blockIndexInTexture + 1;
		}
	}

	public int getBlockTextureFromSide(int i) {
		if (i == 1) {
			return blockIndexInTexture + 17;
		}
		if (i == 0) {
			return blockIndexInTexture + 17;
		}
		if (i == 3) {
			return blockIndexInTexture + 1;
		} else {
			return blockIndexInTexture;
		}
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if (world.multiplayerWorld) {
			return true;
		} else {
			TileEntityDispenser tileentitydispenser = (TileEntityDispenser) world.getBlockTileEntity(i, j, k);
			entityplayer.displayGUIDispenser(tileentitydispenser);
			return true;
		}
	}

	private void dispenseItem(World world, int i, int j, int k, EaglercraftRandom random) {
		int l = world.getBlockMetadata(i, j, k);
		float f = 0.0F;
		float f1 = 0.0F;
		if (l == 3) {
			f1 = 1.0F;
		} else if (l == 2) {
			f1 = -1F;
		} else if (l == 5) {
			f = 1.0F;
		} else {
			f = -1F;
		}
		TileEntityDispenser tileentitydispenser = (TileEntityDispenser) world.getBlockTileEntity(i, j, k);
		ItemStack itemstack = tileentitydispenser.getRandomStackFromInventory();
		double d = (double) i + (double) f * 0.5D + 0.5D;
		double d1 = (double) j + 0.5D;
		double d2 = (double) k + (double) f1 * 0.5D + 0.5D;
		if (itemstack == null) {
			world.playSoundEffect(i, j, k, "random.click", 1.0F, 1.2F);
		} else {
			if (itemstack.itemID == Item.arrow.shiftedIndex) {
				EntityArrow entityarrow = new EntityArrow(world, d, d1, d2);
				entityarrow.setArrowHeading(f, 0.10000000149011612D, f1, 1.1F, 6F);
				world.entityJoinedWorld(entityarrow);
				world.playSoundEffect(i, j, k, "random.bow", 1.0F, 1.2F);
			} else if (itemstack.itemID == Item.egg.shiftedIndex) {
				EntityEgg entityegg = new EntityEgg(world, d, d1, d2);
				entityegg.func_20048_a(f, 0.10000000149011612D, f1, 1.1F, 6F);
				world.entityJoinedWorld(entityegg);
				world.playSoundEffect(i, j, k, "random.bow", 1.0F, 1.2F);
			} else if (itemstack.itemID == Item.snowball.shiftedIndex) {
				EntitySnowball entitysnowball = new EntitySnowball(world, d, d1, d2);
				entitysnowball.func_467_a(f, 0.10000000149011612D, f1, 1.1F, 6F);
				world.entityJoinedWorld(entitysnowball);
				world.playSoundEffect(i, j, k, "random.bow", 1.0F, 1.2F);
			} else {
				EntityItem entityitem = new EntityItem(world, d, d1 - 0.29999999999999999D, d2, itemstack);
				double d3 = random.nextDouble() * 0.10000000000000001D + 0.20000000000000001D;
				entityitem.motionX = (double) f * d3;
				entityitem.motionY = 0.20000000298023224D;
				entityitem.motionZ = (double) f1 * d3;
				entityitem.motionX += random.nextGaussian() * 0.0074999998323619366D * 6D;
				entityitem.motionY += random.nextGaussian() * 0.0074999998323619366D * 6D;
				entityitem.motionZ += random.nextGaussian() * 0.0074999998323619366D * 6D;
				world.entityJoinedWorld(entityitem);
				world.playSoundEffect(i, j, k, "random.click", 1.0F, 1.0F);
			}
			for (int i1 = 0; i1 < 10; i1++) {
				double d4 = random.nextDouble() * 0.20000000000000001D + 0.01D;
				double d5 = d + (double) f * 0.01D + (random.nextDouble() - 0.5D) * (double) f1 * 0.5D;
				double d6 = d1 + (random.nextDouble() - 0.5D) * 0.5D;
				double d7 = d2 + (double) f1 * 0.01D + (random.nextDouble() - 0.5D) * (double) f * 0.5D;
				double d8 = (double) f * d4 + random.nextGaussian() * 0.01D;
				double d9 = -0.029999999999999999D + random.nextGaussian() * 0.01D;
				double d10 = (double) f1 * d4 + random.nextGaussian() * 0.01D;
				world.spawnParticle("smoke", d5, d6, d7, d8, d9, d10);
			}

		}
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if (l > 0 && Block.blocksList[l].canProvidePower()) {
			boolean flag = world.isBlockIndirectlyGettingPowered(i, j, k)
					|| world.isBlockIndirectlyGettingPowered(i, j + 1, k);
			if (flag) {
				world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
			}
		}
	}

	public void updateTick(World world, int i, int j, int k, EaglercraftRandom random) {
		if (world.isBlockIndirectlyGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j + 1, k)) {
			dispenseItem(world, i, j, k, random);
		}
	}

	protected TileEntity getBlockEntity() {
		return new TileEntityDispenser();
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		int l = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if (l == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 2);
		}
		if (l == 1) {
			world.setBlockMetadataWithNotify(i, j, k, 5);
		}
		if (l == 2) {
			world.setBlockMetadataWithNotify(i, j, k, 3);
		}
		if (l == 3) {
			world.setBlockMetadataWithNotify(i, j, k, 4);
		}
	}
}
