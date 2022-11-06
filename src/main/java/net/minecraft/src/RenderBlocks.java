package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class RenderBlocks {

	public RenderBlocks(IBlockAccess iblockaccess) {
		overrideBlockTexture = -1;
		flipTexture = false;
		renderAllFaces = false;
		field_22352_G = 1;
		blockAccess = iblockaccess;
	}

	public RenderBlocks() {
		overrideBlockTexture = -1;
		flipTexture = false;
		renderAllFaces = false;
		field_22352_G = 1;
	}

	public void renderBlockUsingTexture(Block block, int i, int j, int k, int l) {
		overrideBlockTexture = l;
		renderBlockByRenderType(block, i, j, k);
		overrideBlockTexture = -1;
	}

	public boolean renderBlockByRenderType(Block block, int i, int j, int k) {
		int l = block.getRenderType();
		block.setBlockBoundsBasedOnState(blockAccess, i, j, k);
		if (l == 0) {
			return renderStandardBlock(block, i, j, k);
		}
		if (l == 4) {
			return renderBlockFluids(block, i, j, k);
		}
		if (l == 13) {
			return renderBlockCactus(block, i, j, k);
		}
		if (l == 1) {
			return renderBlockReed(block, i, j, k);
		}
		if (l == 6) {
			return renderBlockCrops(block, i, j, k);
		}
		if (l == 2) {
			return renderBlockTorch(block, i, j, k);
		}
		if (l == 3) {
			return renderBlockFire(block, i, j, k);
		}
		if (l == 5) {
			return renderBlockRedstoneWire(block, i, j, k);
		}
		if (l == 8) {
			return renderBlockLadder(block, i, j, k);
		}
		if (l == 7) {
			return renderBlockDoor(block, i, j, k);
		}
		if (l == 9) {
			return renderBlockMinecartTrack(block, i, j, k);
		}
		if (l == 10) {
			return renderBlockStairs(block, i, j, k);
		}
		if (l == 11) {
			return renderBlockFence(block, i, j, k);
		}
		if (l == 12) {
			return renderBlockLever(block, i, j, k);
		}
		if (l == 14) {
			return renderBlockBed(block, i, j, k);
		}
		if (l == 15) {
			return renderBlockRepeater(block, i, j, k);
		} else {
			return false;
		}
	}

	private boolean renderBlockBed(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		int l = blockAccess.getBlockMetadata(i, j, k);
		int i1 = BlockBed.func_22030_c(l);
		boolean flag = BlockBed.func_22032_d(l);
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		float f4 = f1;
		float f5 = f1;
		float f6 = f1;
		float f7 = f;
		float f8 = f2;
		float f9 = f3;
		float f10 = f;
		float f11 = f2;
		float f12 = f3;
		float f13 = f;
		float f14 = f2;
		float f15 = f3;
		float f16 = block.getBlockBrightness(blockAccess, i, j, k);
		tessellator.setColorOpaque_F(f7 * f16, f10 * f16, f13 * f16);
		int f17 = block.getBlockTexture(blockAccess, i, j, k, 0);
		int j1 = (f17 & 0xf) << 4;
		int k1 = f17 & 0xf0;
		double d = (float) j1 / 256F;
		double d2 = ((double) (j1 + 16) - 0.01D) / 256D;
		double d4 = (float) k1 / 256F;
		double d6 = ((double) (k1 + 16) - 0.01D) / 256D;
		double d8 = (double) i + block.minX;
		double d10 = (double) i + block.maxX;
		double d12 = (double) j + block.minY + 0.1875D;
		double d14 = (double) k + block.minZ;
		double d16 = (double) k + block.maxZ;
		tessellator.addVertexWithUV(d8, d12, d16, d, d6);
		tessellator.addVertexWithUV(d8, d12, d14, d, d4);
		tessellator.addVertexWithUV(d10, d12, d14, d2, d4);
		tessellator.addVertexWithUV(d10, d12, d16, d2, d6);
		float ff17 = block.getBlockBrightness(blockAccess, i, j + 1, k);
		tessellator.setColorOpaque_F(f4 * ff17, f5 * ff17, f6 * ff17);
		j1 = block.getBlockTexture(blockAccess, i, j, k, 1);
		k1 = (j1 & 0xf) << 4;
		d = j1 & 0xf0;
		double d1 = (float) k1 / 256F;
		double d3 = ((double) (k1 + 16) - 0.01D) / 256D;
		double d5 = (float) d / 256F;
		double d7 = ((double) (d + 16) - 0.01D) / 256D;
		double d9 = d1;
		double d11 = d3;
		double d13 = d5;
		double d15 = d5;
		double d17 = d1;
		double d18 = d3;
		double d19 = d7;
		double d20 = d7;
		if (i1 == 0) {
			d11 = d1;
			d13 = d7;
			d17 = d3;
			d20 = d5;
		} else if (i1 == 2) {
			d9 = d3;
			d15 = d7;
			d18 = d1;
			d19 = d5;
		} else if (i1 == 3) {
			d9 = d3;
			d15 = d7;
			d18 = d1;
			d19 = d5;
			d11 = d1;
			d13 = d7;
			d17 = d3;
			d20 = d5;
		}
		double d21 = (double) i + block.minX;
		double d22 = (double) i + block.maxX;
		double d23 = (double) j + block.maxY;
		double d24 = (double) k + block.minZ;
		double d25 = (double) k + block.maxZ;
		tessellator.addVertexWithUV(d22, d23, d25, d17, d19);
		tessellator.addVertexWithUV(d22, d23, d24, d9, d13);
		tessellator.addVertexWithUV(d21, d23, d24, d11, d15);
		tessellator.addVertexWithUV(d21, d23, d25, d18, d20);
		f17 = ModelBed.field_22280_a[i1];
		if (flag) {
			f17 = ModelBed.field_22280_a[ModelBed.field_22279_b[i1]];
		}
		j1 = 4;
		switch (i1) {
		case 0: // '\0'
			j1 = 5;
			break;

		case 3: // '\003'
			j1 = 2;
			break;

		case 1: // '\001'
			j1 = 3;
			break;
		}
		if (f17 != 2 && (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2))) {
			float f18 = block.getBlockBrightness(blockAccess, i, j, k - 1);
			if (block.minZ > 0.0D) {
				f18 = f16;
			}
			tessellator.setColorOpaque_F(f8 * f18, f11 * f18, f14 * f18);
			flipTexture = j1 == 2;
			renderEastFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 2));
		}
		if (f17 != 3 && (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3))) {
			float f19 = block.getBlockBrightness(blockAccess, i, j, k + 1);
			if (block.maxZ < 1.0D) {
				f19 = f16;
			}
			tessellator.setColorOpaque_F(f8 * f19, f11 * f19, f14 * f19);
			flipTexture = j1 == 3;
			renderWestFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 3));
		}
		if (f17 != 4 && (renderAllFaces || block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4))) {
			float f20 = block.getBlockBrightness(blockAccess, i - 1, j, k);
			if (block.minX > 0.0D) {
				f20 = f16;
			}
			tessellator.setColorOpaque_F(f9 * f20, f12 * f20, f15 * f20);
			flipTexture = j1 == 4;
			renderNorthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 4));
		}
		if (f17 != 5 && (renderAllFaces || block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5))) {
			float f21 = block.getBlockBrightness(blockAccess, i + 1, j, k);
			if (block.maxX < 1.0D) {
				f21 = f16;
			}
			tessellator.setColorOpaque_F(f9 * f21, f12 * f21, f15 * f21);
			flipTexture = j1 == 5;
			renderSouthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 5));
		}
		flipTexture = false;
		return true;
	}

	public boolean renderBlockTorch(Block block, int i, int j, int k) {
		int l = blockAccess.getBlockMetadata(i, j, k);
		Tessellator tessellator = Tessellator.instance;
		float f = block.getBlockBrightness(blockAccess, i, j, k);
		if (Block.lightValue[block.blockID] > 0) {
			f = 1.0F;
		}
		tessellator.setColorOpaque_F(f, f, f);
		double d = 0.40000000596046448D;
		double d1 = 0.5D - d;
		double d2 = 0.20000000298023224D;
		if (l == 1) {
			renderTorchAtAngle(block, (double) i - d1, (double) j + d2, k, -d, 0.0D);
		} else if (l == 2) {
			renderTorchAtAngle(block, (double) i + d1, (double) j + d2, k, d, 0.0D);
		} else if (l == 3) {
			renderTorchAtAngle(block, i, (double) j + d2, (double) k - d1, 0.0D, -d);
		} else if (l == 4) {
			renderTorchAtAngle(block, i, (double) j + d2, (double) k + d1, 0.0D, d);
		} else {
			renderTorchAtAngle(block, i, j, k, 0.0D, 0.0D);
		}
		return true;
	}

	private boolean renderBlockRepeater(Block block, int i, int j, int k) {
		int l = blockAccess.getBlockMetadata(i, j, k);
		int i1 = l & 3;
		int j1 = (l & 0xc) >> 2;
		renderStandardBlock(block, i, j, k);
		Tessellator tessellator = Tessellator.instance;
		float f = block.getBlockBrightness(blockAccess, i, j, k);
		if (Block.lightValue[block.blockID] > 0) {
			f = (f + 1.0F) * 0.5F;
		}
		tessellator.setColorOpaque_F(f, f, f);
		double d = -0.1875D;
		double d1 = 0.0D;
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		switch (i1) {
		case 0: // '\0'
			d4 = -0.3125D;
			d2 = BlockRedstoneRepeater.field_22024_a[j1];
			break;

		case 2: // '\002'
			d4 = 0.3125D;
			d2 = -BlockRedstoneRepeater.field_22024_a[j1];
			break;

		case 3: // '\003'
			d3 = -0.3125D;
			d1 = BlockRedstoneRepeater.field_22024_a[j1];
			break;

		case 1: // '\001'
			d3 = 0.3125D;
			d1 = -BlockRedstoneRepeater.field_22024_a[j1];
			break;
		}
		renderTorchAtAngle(block, (double) i + d1, (double) j + d, (double) k + d2, 0.0D, 0.0D);
		renderTorchAtAngle(block, (double) i + d3, (double) j + d, (double) k + d4, 0.0D, 0.0D);
		int k1 = block.getBlockTextureFromSide(1);
		int l1 = (k1 & 0xf) << 4;
		int i2 = k1 & 0xf0;
		double d5 = (float) l1 / 256F;
		double d6 = ((float) l1 + 15.99F) / 256F;
		double d7 = (float) i2 / 256F;
		double d8 = ((float) i2 + 15.99F) / 256F;
		float f1 = 0.125F;
		float f2 = i + 1;
		float f3 = i + 1;
		float f4 = i + 0;
		float f5 = i + 0;
		float f6 = k + 0;
		float f7 = k + 1;
		float f8 = k + 1;
		float f9 = k + 0;
		float f10 = (float) j + f1;
		if (i1 == 2) {
			f2 = f3 = i + 0;
			f4 = f5 = i + 1;
			f6 = f9 = k + 1;
			f7 = f8 = k + 0;
		} else if (i1 == 3) {
			f2 = f5 = i + 0;
			f3 = f4 = i + 1;
			f6 = f7 = k + 0;
			f8 = f9 = k + 1;
		} else if (i1 == 1) {
			f2 = f5 = i + 1;
			f3 = f4 = i + 0;
			f6 = f7 = k + 1;
			f8 = f9 = k + 0;
		}
		tessellator.addVertexWithUV(f5, f10, f9, d5, d7);
		tessellator.addVertexWithUV(f4, f10, f8, d5, d8);
		tessellator.addVertexWithUV(f3, f10, f7, d6, d8);
		tessellator.addVertexWithUV(f2, f10, f6, d6, d7);
		return true;
	}

	public boolean renderBlockLever(Block block, int i, int j, int k) {
		int l = blockAccess.getBlockMetadata(i, j, k);
		int i1 = l & 7;
		boolean flag = (l & 8) > 0;
		Tessellator tessellator = Tessellator.instance;
		boolean flag1 = overrideBlockTexture >= 0;
		if (!flag1) {
			overrideBlockTexture = Block.cobblestone.blockIndexInTexture;
		}
		float f = 0.25F;
		float f1 = 0.1875F;
		float f2 = 0.1875F;
		if (i1 == 5) {
			block.setBlockBounds(0.5F - f1, 0.0F, 0.5F - f, 0.5F + f1, f2, 0.5F + f);
		} else if (i1 == 6) {
			block.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, f2, 0.5F + f1);
		} else if (i1 == 4) {
			block.setBlockBounds(0.5F - f1, 0.5F - f, 1.0F - f2, 0.5F + f1, 0.5F + f, 1.0F);
		} else if (i1 == 3) {
			block.setBlockBounds(0.5F - f1, 0.5F - f, 0.0F, 0.5F + f1, 0.5F + f, f2);
		} else if (i1 == 2) {
			block.setBlockBounds(1.0F - f2, 0.5F - f, 0.5F - f1, 1.0F, 0.5F + f, 0.5F + f1);
		} else if (i1 == 1) {
			block.setBlockBounds(0.0F, 0.5F - f, 0.5F - f1, f2, 0.5F + f, 0.5F + f1);
		}
		renderStandardBlock(block, i, j, k);
		if (!flag1) {
			overrideBlockTexture = -1;
		}
		float f3 = block.getBlockBrightness(blockAccess, i, j, k);
		if (Block.lightValue[block.blockID] > 0) {
			f3 = 1.0F;
		}
		tessellator.setColorOpaque_F(f3, f3, f3);
		int j1 = block.getBlockTextureFromSide(0);
		if (overrideBlockTexture >= 0) {
			j1 = overrideBlockTexture;
		}
		int k1 = (j1 & 0xf) << 4;
		int l1 = j1 & 0xf0;
		float f4 = (float) k1 / 256F;
		float f5 = ((float) k1 + 15.99F) / 256F;
		float f6 = (float) l1 / 256F;
		float f7 = ((float) l1 + 15.99F) / 256F;
		Vec3D avec3d[] = new Vec3D[8];
		float f8 = 0.0625F;
		float f9 = 0.0625F;
		float f10 = 0.625F;
		avec3d[0] = Vec3D.createVector(-f8, 0.0D, -f9);
		avec3d[1] = Vec3D.createVector(f8, 0.0D, -f9);
		avec3d[2] = Vec3D.createVector(f8, 0.0D, f9);
		avec3d[3] = Vec3D.createVector(-f8, 0.0D, f9);
		avec3d[4] = Vec3D.createVector(-f8, f10, -f9);
		avec3d[5] = Vec3D.createVector(f8, f10, -f9);
		avec3d[6] = Vec3D.createVector(f8, f10, f9);
		avec3d[7] = Vec3D.createVector(-f8, f10, f9);
		for (int i2 = 0; i2 < 8; i2++) {
			if (flag) {
				avec3d[i2].zCoord -= 0.0625D;
				avec3d[i2].rotateAroundX(0.6981317F);
			} else {
				avec3d[i2].zCoord += 0.0625D;
				avec3d[i2].rotateAroundX(-0.6981317F);
			}
			if (i1 == 6) {
				avec3d[i2].rotateAroundY(1.570796F);
			}
			if (i1 < 5) {
				avec3d[i2].yCoord -= 0.375D;
				avec3d[i2].rotateAroundX(1.570796F);
				if (i1 == 4) {
					avec3d[i2].rotateAroundY(0.0F);
				}
				if (i1 == 3) {
					avec3d[i2].rotateAroundY(3.141593F);
				}
				if (i1 == 2) {
					avec3d[i2].rotateAroundY(1.570796F);
				}
				if (i1 == 1) {
					avec3d[i2].rotateAroundY(-1.570796F);
				}
				avec3d[i2].xCoord += (double) i + 0.5D;
				avec3d[i2].yCoord += (float) j + 0.5F;
				avec3d[i2].zCoord += (double) k + 0.5D;
			} else {
				avec3d[i2].xCoord += (double) i + 0.5D;
				avec3d[i2].yCoord += (float) j + 0.125F;
				avec3d[i2].zCoord += (double) k + 0.5D;
			}
		}

		Vec3D vec3d = null;
		Vec3D vec3d1 = null;
		Vec3D vec3d2 = null;
		Vec3D vec3d3 = null;
		for (int j2 = 0; j2 < 6; j2++) {
			if (j2 == 0) {
				f4 = (float) (k1 + 7) / 256F;
				f5 = ((float) (k1 + 9) - 0.01F) / 256F;
				f6 = (float) (l1 + 6) / 256F;
				f7 = ((float) (l1 + 8) - 0.01F) / 256F;
			} else if (j2 == 2) {
				f4 = (float) (k1 + 7) / 256F;
				f5 = ((float) (k1 + 9) - 0.01F) / 256F;
				f6 = (float) (l1 + 6) / 256F;
				f7 = ((float) (l1 + 16) - 0.01F) / 256F;
			}
			if (j2 == 0) {
				vec3d = avec3d[0];
				vec3d1 = avec3d[1];
				vec3d2 = avec3d[2];
				vec3d3 = avec3d[3];
			} else if (j2 == 1) {
				vec3d = avec3d[7];
				vec3d1 = avec3d[6];
				vec3d2 = avec3d[5];
				vec3d3 = avec3d[4];
			} else if (j2 == 2) {
				vec3d = avec3d[1];
				vec3d1 = avec3d[0];
				vec3d2 = avec3d[4];
				vec3d3 = avec3d[5];
			} else if (j2 == 3) {
				vec3d = avec3d[2];
				vec3d1 = avec3d[1];
				vec3d2 = avec3d[5];
				vec3d3 = avec3d[6];
			} else if (j2 == 4) {
				vec3d = avec3d[3];
				vec3d1 = avec3d[2];
				vec3d2 = avec3d[6];
				vec3d3 = avec3d[7];
			} else if (j2 == 5) {
				vec3d = avec3d[0];
				vec3d1 = avec3d[3];
				vec3d2 = avec3d[7];
				vec3d3 = avec3d[4];
			}
			tessellator.addVertexWithUV(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, f4, f7);
			tessellator.addVertexWithUV(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, f5, f7);
			tessellator.addVertexWithUV(vec3d2.xCoord, vec3d2.yCoord, vec3d2.zCoord, f5, f6);
			tessellator.addVertexWithUV(vec3d3.xCoord, vec3d3.yCoord, vec3d3.zCoord, f4, f6);
		}

		return true;
	}

	public boolean renderBlockFire(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		int l = block.getBlockTextureFromSide(0);
		if (overrideBlockTexture >= 0) {
			l = overrideBlockTexture;
		}
		float f = block.getBlockBrightness(blockAccess, i, j, k);
		tessellator.setColorOpaque_F(f, f, f);
		int i1 = (l & 0xf) << 4;
		int j1 = l & 0xf0;
		double d = (float) i1 / 256F;
		double d2 = ((float) i1 + 15.99F) / 256F;
		double d4 = (float) j1 / 256F;
		double d6 = ((float) j1 + 15.99F) / 256F;
		float f1 = 1.4F;
		if (blockAccess.isBlockOpaqueCube(i, j - 1, k) || Block.fire.canBlockCatchFire(blockAccess, i, j - 1, k)) {
			double d8 = (double) i + 0.5D + 0.20000000000000001D;
			double d9 = ((double) i + 0.5D) - 0.20000000000000001D;
			double d12 = (double) k + 0.5D + 0.20000000000000001D;
			double d14 = ((double) k + 0.5D) - 0.20000000000000001D;
			double d16 = ((double) i + 0.5D) - 0.29999999999999999D;
			double d18 = (double) i + 0.5D + 0.29999999999999999D;
			double d20 = ((double) k + 0.5D) - 0.29999999999999999D;
			double d22 = (double) k + 0.5D + 0.29999999999999999D;
			tessellator.addVertexWithUV(d16, (float) j + f1, k + 1, d2, d4);
			tessellator.addVertexWithUV(d8, j + 0, k + 1, d2, d6);
			tessellator.addVertexWithUV(d8, j + 0, k + 0, d, d6);
			tessellator.addVertexWithUV(d16, (float) j + f1, k + 0, d, d4);
			tessellator.addVertexWithUV(d18, (float) j + f1, k + 0, d2, d4);
			tessellator.addVertexWithUV(d9, j + 0, k + 0, d2, d6);
			tessellator.addVertexWithUV(d9, j + 0, k + 1, d, d6);
			tessellator.addVertexWithUV(d18, (float) j + f1, k + 1, d, d4);
			d = (float) i1 / 256F;
			d2 = ((float) i1 + 15.99F) / 256F;
			d4 = (float) (j1 + 16) / 256F;
			d6 = ((float) j1 + 15.99F + 16F) / 256F;
			tessellator.addVertexWithUV(i + 1, (float) j + f1, d22, d2, d4);
			tessellator.addVertexWithUV(i + 1, j + 0, d14, d2, d6);
			tessellator.addVertexWithUV(i + 0, j + 0, d14, d, d6);
			tessellator.addVertexWithUV(i + 0, (float) j + f1, d22, d, d4);
			tessellator.addVertexWithUV(i + 0, (float) j + f1, d20, d2, d4);
			tessellator.addVertexWithUV(i + 0, j + 0, d12, d2, d6);
			tessellator.addVertexWithUV(i + 1, j + 0, d12, d, d6);
			tessellator.addVertexWithUV(i + 1, (float) j + f1, d20, d, d4);
			d8 = ((double) i + 0.5D) - 0.5D;
			d9 = (double) i + 0.5D + 0.5D;
			d12 = ((double) k + 0.5D) - 0.5D;
			d14 = (double) k + 0.5D + 0.5D;
			d16 = ((double) i + 0.5D) - 0.40000000000000002D;
			d18 = (double) i + 0.5D + 0.40000000000000002D;
			d20 = ((double) k + 0.5D) - 0.40000000000000002D;
			d22 = (double) k + 0.5D + 0.40000000000000002D;
			tessellator.addVertexWithUV(d16, (float) j + f1, k + 0, d, d4);
			tessellator.addVertexWithUV(d8, j + 0, k + 0, d, d6);
			tessellator.addVertexWithUV(d8, j + 0, k + 1, d2, d6);
			tessellator.addVertexWithUV(d16, (float) j + f1, k + 1, d2, d4);
			tessellator.addVertexWithUV(d18, (float) j + f1, k + 1, d, d4);
			tessellator.addVertexWithUV(d9, j + 0, k + 1, d, d6);
			tessellator.addVertexWithUV(d9, j + 0, k + 0, d2, d6);
			tessellator.addVertexWithUV(d18, (float) j + f1, k + 0, d2, d4);
			d = (float) i1 / 256F;
			d2 = ((float) i1 + 15.99F) / 256F;
			d4 = (float) j1 / 256F;
			d6 = ((float) j1 + 15.99F) / 256F;
			tessellator.addVertexWithUV(i + 0, (float) j + f1, d22, d, d4);
			tessellator.addVertexWithUV(i + 0, j + 0, d14, d, d6);
			tessellator.addVertexWithUV(i + 1, j + 0, d14, d2, d6);
			tessellator.addVertexWithUV(i + 1, (float) j + f1, d22, d2, d4);
			tessellator.addVertexWithUV(i + 1, (float) j + f1, d20, d, d4);
			tessellator.addVertexWithUV(i + 1, j + 0, d12, d, d6);
			tessellator.addVertexWithUV(i + 0, j + 0, d12, d2, d6);
			tessellator.addVertexWithUV(i + 0, (float) j + f1, d20, d2, d4);
		} else {
			float f3 = 0.2F;
			float f4 = 0.0625F;
			if ((i + j + k & 1) == 1) {
				d = (float) i1 / 256F;
				d2 = ((float) i1 + 15.99F) / 256F;
				d4 = (float) (j1 + 16) / 256F;
				d6 = ((float) j1 + 15.99F + 16F) / 256F;
			}
			if ((i / 2 + j / 2 + k / 2 & 1) == 1) {
				double d10 = d2;
				d2 = d;
				d = d10;
			}
			if (Block.fire.canBlockCatchFire(blockAccess, i - 1, j, k)) {
				tessellator.addVertexWithUV((float) i + f3, (float) j + f1 + f4, k + 1, d2, d4);
				tessellator.addVertexWithUV(i + 0, (float) (j + 0) + f4, k + 1, d2, d6);
				tessellator.addVertexWithUV(i + 0, (float) (j + 0) + f4, k + 0, d, d6);
				tessellator.addVertexWithUV((float) i + f3, (float) j + f1 + f4, k + 0, d, d4);
				tessellator.addVertexWithUV((float) i + f3, (float) j + f1 + f4, k + 0, d, d4);
				tessellator.addVertexWithUV(i + 0, (float) (j + 0) + f4, k + 0, d, d6);
				tessellator.addVertexWithUV(i + 0, (float) (j + 0) + f4, k + 1, d2, d6);
				tessellator.addVertexWithUV((float) i + f3, (float) j + f1 + f4, k + 1, d2, d4);
			}
			if (Block.fire.canBlockCatchFire(blockAccess, i + 1, j, k)) {
				tessellator.addVertexWithUV((float) (i + 1) - f3, (float) j + f1 + f4, k + 0, d, d4);
				tessellator.addVertexWithUV((i + 1) - 0, (float) (j + 0) + f4, k + 0, d, d6);
				tessellator.addVertexWithUV((i + 1) - 0, (float) (j + 0) + f4, k + 1, d2, d6);
				tessellator.addVertexWithUV((float) (i + 1) - f3, (float) j + f1 + f4, k + 1, d2, d4);
				tessellator.addVertexWithUV((float) (i + 1) - f3, (float) j + f1 + f4, k + 1, d2, d4);
				tessellator.addVertexWithUV((i + 1) - 0, (float) (j + 0) + f4, k + 1, d2, d6);
				tessellator.addVertexWithUV((i + 1) - 0, (float) (j + 0) + f4, k + 0, d, d6);
				tessellator.addVertexWithUV((float) (i + 1) - f3, (float) j + f1 + f4, k + 0, d, d4);
			}
			if (Block.fire.canBlockCatchFire(blockAccess, i, j, k - 1)) {
				tessellator.addVertexWithUV(i + 0, (float) j + f1 + f4, (float) k + f3, d2, d4);
				tessellator.addVertexWithUV(i + 0, (float) (j + 0) + f4, k + 0, d2, d6);
				tessellator.addVertexWithUV(i + 1, (float) (j + 0) + f4, k + 0, d, d6);
				tessellator.addVertexWithUV(i + 1, (float) j + f1 + f4, (float) k + f3, d, d4);
				tessellator.addVertexWithUV(i + 1, (float) j + f1 + f4, (float) k + f3, d, d4);
				tessellator.addVertexWithUV(i + 1, (float) (j + 0) + f4, k + 0, d, d6);
				tessellator.addVertexWithUV(i + 0, (float) (j + 0) + f4, k + 0, d2, d6);
				tessellator.addVertexWithUV(i + 0, (float) j + f1 + f4, (float) k + f3, d2, d4);
			}
			if (Block.fire.canBlockCatchFire(blockAccess, i, j, k + 1)) {
				tessellator.addVertexWithUV(i + 1, (float) j + f1 + f4, (float) (k + 1) - f3, d, d4);
				tessellator.addVertexWithUV(i + 1, (float) (j + 0) + f4, (k + 1) - 0, d, d6);
				tessellator.addVertexWithUV(i + 0, (float) (j + 0) + f4, (k + 1) - 0, d2, d6);
				tessellator.addVertexWithUV(i + 0, (float) j + f1 + f4, (float) (k + 1) - f3, d2, d4);
				tessellator.addVertexWithUV(i + 0, (float) j + f1 + f4, (float) (k + 1) - f3, d2, d4);
				tessellator.addVertexWithUV(i + 0, (float) (j + 0) + f4, (k + 1) - 0, d2, d6);
				tessellator.addVertexWithUV(i + 1, (float) (j + 0) + f4, (k + 1) - 0, d, d6);
				tessellator.addVertexWithUV(i + 1, (float) j + f1 + f4, (float) (k + 1) - f3, d, d4);
			}
			if (Block.fire.canBlockCatchFire(blockAccess, i, j + 1, k)) {
				double d11 = (double) i + 0.5D + 0.5D;
				double d13 = ((double) i + 0.5D) - 0.5D;
				double d15 = (double) k + 0.5D + 0.5D;
				double d17 = ((double) k + 0.5D) - 0.5D;
				double d19 = ((double) i + 0.5D) - 0.5D;
				double d21 = (double) i + 0.5D + 0.5D;
				double d23 = ((double) k + 0.5D) - 0.5D;
				double d24 = (double) k + 0.5D + 0.5D;
				double d1 = (float) i1 / 256F;
				double d3 = ((float) i1 + 15.99F) / 256F;
				double d5 = (float) j1 / 256F;
				double d7 = ((float) j1 + 15.99F) / 256F;
				j++;
				float f2 = -0.2F;
				if ((i + j + k & 1) == 0) {
					tessellator.addVertexWithUV(d19, (float) j + f2, k + 0, d3, d5);
					tessellator.addVertexWithUV(d11, j + 0, k + 0, d3, d7);
					tessellator.addVertexWithUV(d11, j + 0, k + 1, d1, d7);
					tessellator.addVertexWithUV(d19, (float) j + f2, k + 1, d1, d5);
					d1 = (float) i1 / 256F;
					d3 = ((float) i1 + 15.99F) / 256F;
					d5 = (float) (j1 + 16) / 256F;
					d7 = ((float) j1 + 15.99F + 16F) / 256F;
					tessellator.addVertexWithUV(d21, (float) j + f2, k + 1, d3, d5);
					tessellator.addVertexWithUV(d13, j + 0, k + 1, d3, d7);
					tessellator.addVertexWithUV(d13, j + 0, k + 0, d1, d7);
					tessellator.addVertexWithUV(d21, (float) j + f2, k + 0, d1, d5);
				} else {
					tessellator.addVertexWithUV(i + 0, (float) j + f2, d24, d3, d5);
					tessellator.addVertexWithUV(i + 0, j + 0, d17, d3, d7);
					tessellator.addVertexWithUV(i + 1, j + 0, d17, d1, d7);
					tessellator.addVertexWithUV(i + 1, (float) j + f2, d24, d1, d5);
					d1 = (float) i1 / 256F;
					d3 = ((float) i1 + 15.99F) / 256F;
					d5 = (float) (j1 + 16) / 256F;
					d7 = ((float) j1 + 15.99F + 16F) / 256F;
					tessellator.addVertexWithUV(i + 1, (float) j + f2, d23, d3, d5);
					tessellator.addVertexWithUV(i + 1, j + 0, d15, d3, d7);
					tessellator.addVertexWithUV(i + 0, j + 0, d15, d1, d7);
					tessellator.addVertexWithUV(i + 0, (float) j + f2, d23, d1, d5);
				}
			}
		}
		return true;
	}

	public boolean renderBlockRedstoneWire(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		int l = blockAccess.getBlockMetadata(i, j, k);
		int i1 = block.getBlockTextureFromSideAndMetadata(1, l);
		if (overrideBlockTexture >= 0) {
			i1 = overrideBlockTexture;
		}
		float f = block.getBlockBrightness(blockAccess, i, j, k);
		float f1 = (float) l / 15F;
		float f2 = f1 * 0.6F + 0.4F;
		if (l == 0) {
			f2 = 0.0F;
		}
		float f3 = f1 * f1 * 0.7F - 0.5F;
		float f4 = f1 * f1 * 0.6F - 0.7F;
		if (f3 < 0.0F) {
			f3 = 0.0F;
		}
		if (f4 < 0.0F) {
			f4 = 0.0F;
		}
		tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
		int j1 = (i1 & 0xf) << 4;
		int k1 = i1 & 0xf0;
		double d = (float) j1 / 256F;
		double d1 = ((float) j1 + 15.99F) / 256F;
		double d2 = (float) k1 / 256F;
		double d3 = ((float) k1 + 15.99F) / 256F;
		float f5 = 0.0F;
		float f6 = 0.03125F;
		boolean flag = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i - 1, j, k)
				|| !blockAccess.isBlockOpaqueCube(i - 1, j, k)
						&& BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i - 1, j - 1, k);
		boolean flag1 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i + 1, j, k)
				|| !blockAccess.isBlockOpaqueCube(i + 1, j, k)
						&& BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i + 1, j - 1, k);
		boolean flag2 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j, k - 1)
				|| !blockAccess.isBlockOpaqueCube(i, j, k - 1)
						&& BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j - 1, k - 1);
		boolean flag3 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j, k + 1)
				|| !blockAccess.isBlockOpaqueCube(i, j, k + 1)
						&& BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j - 1, k + 1);
		if (!blockAccess.isBlockOpaqueCube(i, j + 1, k)) {
			if (blockAccess.isBlockOpaqueCube(i - 1, j, k)
					&& BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i - 1, j + 1, k)) {
				flag = true;
			}
			if (blockAccess.isBlockOpaqueCube(i + 1, j, k)
					&& BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i + 1, j + 1, k)) {
				flag1 = true;
			}
			if (blockAccess.isBlockOpaqueCube(i, j, k - 1)
					&& BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j + 1, k - 1)) {
				flag2 = true;
			}
			if (blockAccess.isBlockOpaqueCube(i, j, k + 1)
					&& BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j + 1, k + 1)) {
				flag3 = true;
			}
		}
		float f7 = 0.3125F;
		float f8 = i + 0;
		float f9 = i + 1;
		float f10 = k + 0;
		float f11 = k + 1;
		byte byte0 = 0;
		if ((flag || flag1) && !flag2 && !flag3) {
			byte0 = 1;
		}
		if ((flag2 || flag3) && !flag1 && !flag) {
			byte0 = 2;
		}
		if (byte0 != 0) {
			d = (float) (j1 + 16) / 256F;
			d1 = ((float) (j1 + 16) + 15.99F) / 256F;
			d2 = (float) k1 / 256F;
			d3 = ((float) k1 + 15.99F) / 256F;
		}
		if (byte0 == 0) {
			if (flag1 || flag2 || flag3 || flag) {
				if (!flag) {
					f8 += f7;
				}
				if (!flag) {
					d += f7 / 16F;
				}
				if (!flag1) {
					f9 -= f7;
				}
				if (!flag1) {
					d1 -= f7 / 16F;
				}
				if (!flag2) {
					f10 += f7;
				}
				if (!flag2) {
					d2 += f7 / 16F;
				}
				if (!flag3) {
					f11 -= f7;
				}
				if (!flag3) {
					d3 -= f7 / 16F;
				}
			}
			tessellator.addVertexWithUV(f9 + f5, (float) j + f6, f11 + f5, d1, d3);
			tessellator.addVertexWithUV(f9 + f5, (float) j + f6, f10 - f5, d1, d2);
			tessellator.addVertexWithUV(f8 - f5, (float) j + f6, f10 - f5, d, d2);
			tessellator.addVertexWithUV(f8 - f5, (float) j + f6, f11 + f5, d, d3);
		}
		if (byte0 == 1) {
			tessellator.addVertexWithUV(f9 + f5, (float) j + f6, f11 + f5, d1, d3);
			tessellator.addVertexWithUV(f9 + f5, (float) j + f6, f10 - f5, d1, d2);
			tessellator.addVertexWithUV(f8 - f5, (float) j + f6, f10 - f5, d, d2);
			tessellator.addVertexWithUV(f8 - f5, (float) j + f6, f11 + f5, d, d3);
		}
		if (byte0 == 2) {
			tessellator.addVertexWithUV(f9 + f5, (float) j + f6, f11 + f5, d1, d3);
			tessellator.addVertexWithUV(f9 + f5, (float) j + f6, f10 - f5, d, d3);
			tessellator.addVertexWithUV(f8 - f5, (float) j + f6, f10 - f5, d, d2);
			tessellator.addVertexWithUV(f8 - f5, (float) j + f6, f11 + f5, d1, d2);
		}
		d = (float) (j1 + 16) / 256F;
		d1 = ((float) (j1 + 16) + 15.99F) / 256F;
		d2 = (float) k1 / 256F;
		d3 = ((float) k1 + 15.99F) / 256F;
		if (!blockAccess.isBlockOpaqueCube(i, j + 1, k)) {
			if (blockAccess.isBlockOpaqueCube(i - 1, j, k)
					&& blockAccess.getBlockId(i - 1, j + 1, k) == Block.redstoneWire.blockID) {
				tessellator.addVertexWithUV((float) i + f6, (float) (j + 1) + f5, (float) (k + 1) + f5, d1, d2);
				tessellator.addVertexWithUV((float) i + f6, (float) (j + 0) - f5, (float) (k + 1) + f5, d, d2);
				tessellator.addVertexWithUV((float) i + f6, (float) (j + 0) - f5, (float) (k + 0) - f5, d, d3);
				tessellator.addVertexWithUV((float) i + f6, (float) (j + 1) + f5, (float) (k + 0) - f5, d1, d3);
			}
			if (blockAccess.isBlockOpaqueCube(i + 1, j, k)
					&& blockAccess.getBlockId(i + 1, j + 1, k) == Block.redstoneWire.blockID) {
				tessellator.addVertexWithUV((float) (i + 1) - f6, (float) (j + 0) - f5, (float) (k + 1) + f5, d, d3);
				tessellator.addVertexWithUV((float) (i + 1) - f6, (float) (j + 1) + f5, (float) (k + 1) + f5, d1, d3);
				tessellator.addVertexWithUV((float) (i + 1) - f6, (float) (j + 1) + f5, (float) (k + 0) - f5, d1, d2);
				tessellator.addVertexWithUV((float) (i + 1) - f6, (float) (j + 0) - f5, (float) (k + 0) - f5, d, d2);
			}
			if (blockAccess.isBlockOpaqueCube(i, j, k - 1)
					&& blockAccess.getBlockId(i, j + 1, k - 1) == Block.redstoneWire.blockID) {
				tessellator.addVertexWithUV((float) (i + 1) + f5, (float) (j + 0) - f5, (float) k + f6, d, d3);
				tessellator.addVertexWithUV((float) (i + 1) + f5, (float) (j + 1) + f5, (float) k + f6, d1, d3);
				tessellator.addVertexWithUV((float) (i + 0) - f5, (float) (j + 1) + f5, (float) k + f6, d1, d2);
				tessellator.addVertexWithUV((float) (i + 0) - f5, (float) (j + 0) - f5, (float) k + f6, d, d2);
			}
			if (blockAccess.isBlockOpaqueCube(i, j, k + 1)
					&& blockAccess.getBlockId(i, j + 1, k + 1) == Block.redstoneWire.blockID) {
				tessellator.addVertexWithUV((float) (i + 1) + f5, (float) (j + 1) + f5, (float) (k + 1) - f6, d1, d2);
				tessellator.addVertexWithUV((float) (i + 1) + f5, (float) (j + 0) - f5, (float) (k + 1) - f6, d, d2);
				tessellator.addVertexWithUV((float) (i + 0) - f5, (float) (j + 0) - f5, (float) (k + 1) - f6, d, d3);
				tessellator.addVertexWithUV((float) (i + 0) - f5, (float) (j + 1) + f5, (float) (k + 1) - f6, d1, d3);
			}
		}
		return true;
	}

	public boolean renderBlockMinecartTrack(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		int l = blockAccess.getBlockMetadata(i, j, k);
		int i1 = block.getBlockTextureFromSideAndMetadata(0, l);
		if (overrideBlockTexture >= 0) {
			i1 = overrideBlockTexture;
		}
		float f = block.getBlockBrightness(blockAccess, i, j, k);
		tessellator.setColorOpaque_F(f, f, f);
		int j1 = (i1 & 0xf) << 4;
		int k1 = i1 & 0xf0;
		double d = (float) j1 / 256F;
		double d1 = ((float) j1 + 15.99F) / 256F;
		double d2 = (float) k1 / 256F;
		double d3 = ((float) k1 + 15.99F) / 256F;
		float f1 = 0.0625F;
		float f2 = i + 1;
		float f3 = i + 1;
		float f4 = i + 0;
		float f5 = i + 0;
		float f6 = k + 0;
		float f7 = k + 1;
		float f8 = k + 1;
		float f9 = k + 0;
		float f10 = (float) j + f1;
		float f11 = (float) j + f1;
		float f12 = (float) j + f1;
		float f13 = (float) j + f1;
		if (l == 1 || l == 2 || l == 3 || l == 7) {
			f2 = f5 = i + 1;
			f3 = f4 = i + 0;
			f6 = f7 = k + 1;
			f8 = f9 = k + 0;
		} else if (l == 8) {
			f2 = f3 = i + 0;
			f4 = f5 = i + 1;
			f6 = f9 = k + 1;
			f7 = f8 = k + 0;
		} else if (l == 9) {
			f2 = f5 = i + 0;
			f3 = f4 = i + 1;
			f6 = f7 = k + 0;
			f8 = f9 = k + 1;
		}
		if (l == 2 || l == 4) {
			f10++;
			f13++;
		} else if (l == 3 || l == 5) {
			f11++;
			f12++;
		}
		tessellator.addVertexWithUV(f2, f10, f6, d1, d2);
		tessellator.addVertexWithUV(f3, f11, f7, d1, d3);
		tessellator.addVertexWithUV(f4, f12, f8, d, d3);
		tessellator.addVertexWithUV(f5, f13, f9, d, d2);
		tessellator.addVertexWithUV(f5, f13, f9, d, d2);
		tessellator.addVertexWithUV(f4, f12, f8, d, d3);
		tessellator.addVertexWithUV(f3, f11, f7, d1, d3);
		tessellator.addVertexWithUV(f2, f10, f6, d1, d2);
		return true;
	}

	public boolean renderBlockLadder(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		int l = block.getBlockTextureFromSide(0);
		if (overrideBlockTexture >= 0) {
			l = overrideBlockTexture;
		}
		float f = block.getBlockBrightness(blockAccess, i, j, k);
		tessellator.setColorOpaque_F(f, f, f);
		int i1 = (l & 0xf) << 4;
		int j1 = l & 0xf0;
		double d = (float) i1 / 256F;
		double d1 = ((float) i1 + 15.99F) / 256F;
		double d2 = (float) j1 / 256F;
		double d3 = ((float) j1 + 15.99F) / 256F;
		int k1 = blockAccess.getBlockMetadata(i, j, k);
		float f1 = 0.0F;
		float f2 = 0.05F;
		if (k1 == 5) {
			tessellator.addVertexWithUV((float) i + f2, (float) (j + 1) + f1, (float) (k + 1) + f1, d, d2);
			tessellator.addVertexWithUV((float) i + f2, (float) (j + 0) - f1, (float) (k + 1) + f1, d, d3);
			tessellator.addVertexWithUV((float) i + f2, (float) (j + 0) - f1, (float) (k + 0) - f1, d1, d3);
			tessellator.addVertexWithUV((float) i + f2, (float) (j + 1) + f1, (float) (k + 0) - f1, d1, d2);
		}
		if (k1 == 4) {
			tessellator.addVertexWithUV((float) (i + 1) - f2, (float) (j + 0) - f1, (float) (k + 1) + f1, d1, d3);
			tessellator.addVertexWithUV((float) (i + 1) - f2, (float) (j + 1) + f1, (float) (k + 1) + f1, d1, d2);
			tessellator.addVertexWithUV((float) (i + 1) - f2, (float) (j + 1) + f1, (float) (k + 0) - f1, d, d2);
			tessellator.addVertexWithUV((float) (i + 1) - f2, (float) (j + 0) - f1, (float) (k + 0) - f1, d, d3);
		}
		if (k1 == 3) {
			tessellator.addVertexWithUV((float) (i + 1) + f1, (float) (j + 0) - f1, (float) k + f2, d1, d3);
			tessellator.addVertexWithUV((float) (i + 1) + f1, (float) (j + 1) + f1, (float) k + f2, d1, d2);
			tessellator.addVertexWithUV((float) (i + 0) - f1, (float) (j + 1) + f1, (float) k + f2, d, d2);
			tessellator.addVertexWithUV((float) (i + 0) - f1, (float) (j + 0) - f1, (float) k + f2, d, d3);
		}
		if (k1 == 2) {
			tessellator.addVertexWithUV((float) (i + 1) + f1, (float) (j + 1) + f1, (float) (k + 1) - f2, d, d2);
			tessellator.addVertexWithUV((float) (i + 1) + f1, (float) (j + 0) - f1, (float) (k + 1) - f2, d, d3);
			tessellator.addVertexWithUV((float) (i + 0) - f1, (float) (j + 0) - f1, (float) (k + 1) - f2, d1, d3);
			tessellator.addVertexWithUV((float) (i + 0) - f1, (float) (j + 1) + f1, (float) (k + 1) - f2, d1, d2);
		}
		return true;
	}

	public boolean renderBlockReed(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		float f = block.getBlockBrightness(blockAccess, i, j, k);
		tessellator.setColorOpaque_F(f, f, f);
		func_1239_a(block, blockAccess.getBlockMetadata(i, j, k), i, j, k);
		return true;
	}

	public boolean renderBlockCrops(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		float f = block.getBlockBrightness(blockAccess, i, j, k);
		tessellator.setColorOpaque_F(f, f, f);
		func_1245_b(block, blockAccess.getBlockMetadata(i, j, k), i, (float) j - 0.0625F, k);
		return true;
	}

	public void renderTorchAtAngle(Block block, double d, double d1, double d2, double d3, double d4) {
		Tessellator tessellator = Tessellator.instance;
		int i = block.getBlockTextureFromSide(0);
		if (overrideBlockTexture >= 0) {
			i = overrideBlockTexture;
		}
		int j = (i & 0xf) << 4;
		int k = i & 0xf0;
		float f = (float) j / 256F;
		float f1 = ((float) j + 15.99F) / 256F;
		float f2 = (float) k / 256F;
		float f3 = ((float) k + 15.99F) / 256F;
		double d5 = (double) f + 0.02734375D;
		double d6 = (double) f2 + 0.0234375D;
		double d7 = (double) f + 0.03515625D;
		double d8 = (double) f2 + 0.03125D;
		d += 0.5D;
		d2 += 0.5D;
		double d9 = d - 0.5D;
		double d10 = d + 0.5D;
		double d11 = d2 - 0.5D;
		double d12 = d2 + 0.5D;
		double d13 = 0.0625D;
		double d14 = 0.625D;
		tessellator.addVertexWithUV((d + d3 * (1.0D - d14)) - d13, d1 + d14, (d2 + d4 * (1.0D - d14)) - d13, d5, d6);
		tessellator.addVertexWithUV((d + d3 * (1.0D - d14)) - d13, d1 + d14, d2 + d4 * (1.0D - d14) + d13, d5, d8);
		tessellator.addVertexWithUV(d + d3 * (1.0D - d14) + d13, d1 + d14, d2 + d4 * (1.0D - d14) + d13, d7, d8);
		tessellator.addVertexWithUV(d + d3 * (1.0D - d14) + d13, d1 + d14, (d2 + d4 * (1.0D - d14)) - d13, d7, d6);
		tessellator.addVertexWithUV(d - d13, d1 + 1.0D, d11, f, f2);
		tessellator.addVertexWithUV((d - d13) + d3, d1 + 0.0D, d11 + d4, f, f3);
		tessellator.addVertexWithUV((d - d13) + d3, d1 + 0.0D, d12 + d4, f1, f3);
		tessellator.addVertexWithUV(d - d13, d1 + 1.0D, d12, f1, f2);
		tessellator.addVertexWithUV(d + d13, d1 + 1.0D, d12, f, f2);
		tessellator.addVertexWithUV(d + d3 + d13, d1 + 0.0D, d12 + d4, f, f3);
		tessellator.addVertexWithUV(d + d3 + d13, d1 + 0.0D, d11 + d4, f1, f3);
		tessellator.addVertexWithUV(d + d13, d1 + 1.0D, d11, f1, f2);
		tessellator.addVertexWithUV(d9, d1 + 1.0D, d2 + d13, f, f2);
		tessellator.addVertexWithUV(d9 + d3, d1 + 0.0D, d2 + d13 + d4, f, f3);
		tessellator.addVertexWithUV(d10 + d3, d1 + 0.0D, d2 + d13 + d4, f1, f3);
		tessellator.addVertexWithUV(d10, d1 + 1.0D, d2 + d13, f1, f2);
		tessellator.addVertexWithUV(d10, d1 + 1.0D, d2 - d13, f, f2);
		tessellator.addVertexWithUV(d10 + d3, d1 + 0.0D, (d2 - d13) + d4, f, f3);
		tessellator.addVertexWithUV(d9 + d3, d1 + 0.0D, (d2 - d13) + d4, f1, f3);
		tessellator.addVertexWithUV(d9, d1 + 1.0D, d2 - d13, f1, f2);
	}

	public void func_1239_a(Block block, int i, double d, double d1, double d2) {
		Tessellator tessellator = Tessellator.instance;
		int j = block.getBlockTextureFromSideAndMetadata(0, i);
		if (overrideBlockTexture >= 0) {
			j = overrideBlockTexture;
		}
		int k = (j & 0xf) << 4;
		int l = j & 0xf0;
		double d3 = (float) k / 256F;
		double d4 = ((float) k + 15.99F) / 256F;
		double d5 = (float) l / 256F;
		double d6 = ((float) l + 15.99F) / 256F;
		double d7 = (d + 0.5D) - 0.44999998807907104D;
		double d8 = d + 0.5D + 0.44999998807907104D;
		double d9 = (d2 + 0.5D) - 0.44999998807907104D;
		double d10 = d2 + 0.5D + 0.44999998807907104D;
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d3, d5);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d3, d6);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d4, d6);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d4, d5);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d3, d5);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d3, d6);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d4, d6);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d4, d5);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d3, d5);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d3, d6);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d4, d6);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d4, d5);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d3, d5);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d3, d6);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d4, d6);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d4, d5);
	}

	public void func_1245_b(Block block, int i, double d, double d1, double d2) {
		Tessellator tessellator = Tessellator.instance;
		int j = block.getBlockTextureFromSideAndMetadata(0, i);
		if (overrideBlockTexture >= 0) {
			j = overrideBlockTexture;
		}
		int k = (j & 0xf) << 4;
		int l = j & 0xf0;
		double d3 = (float) k / 256F;
		double d4 = ((float) k + 15.99F) / 256F;
		double d5 = (float) l / 256F;
		double d6 = ((float) l + 15.99F) / 256F;
		double d7 = (d + 0.5D) - 0.25D;
		double d8 = d + 0.5D + 0.25D;
		double d9 = (d2 + 0.5D) - 0.5D;
		double d10 = d2 + 0.5D + 0.5D;
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d3, d5);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d3, d6);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d4, d6);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d4, d5);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d3, d5);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d3, d6);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d4, d6);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d4, d5);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d3, d5);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d3, d6);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d4, d6);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d4, d5);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d3, d5);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d3, d6);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d4, d6);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d4, d5);
		d7 = (d + 0.5D) - 0.5D;
		d8 = d + 0.5D + 0.5D;
		d9 = (d2 + 0.5D) - 0.25D;
		d10 = d2 + 0.5D + 0.25D;
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d3, d5);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d3, d6);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d4, d6);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d4, d5);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d3, d5);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d3, d6);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d4, d6);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d4, d5);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d3, d5);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d3, d6);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d4, d6);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d4, d5);
		tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d3, d5);
		tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d3, d6);
		tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d4, d6);
		tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d4, d5);
	}

	public boolean renderBlockFluids(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		boolean flag = block.shouldSideBeRendered(blockAccess, i, j + 1, k, 1);
		boolean flag1 = block.shouldSideBeRendered(blockAccess, i, j - 1, k, 0);
		boolean aflag[] = new boolean[4];
		aflag[0] = block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2);
		aflag[1] = block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3);
		aflag[2] = block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4);
		aflag[3] = block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5);
		if (!flag && !flag1 && !aflag[0] && !aflag[1] && !aflag[2] && !aflag[3]) {
			return false;
		}
		boolean flag2 = false;
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		double d = 0.0D;
		double d1 = 1.0D;
		Material material = block.blockMaterial;
		int l = blockAccess.getBlockMetadata(i, j, k);
		float f4 = func_1224_a(i, j, k, material);
		float f5 = func_1224_a(i, j, k + 1, material);
		float f6 = func_1224_a(i + 1, j, k + 1, material);
		float f7 = func_1224_a(i + 1, j, k, material);
		if (renderAllFaces || flag) {
			flag2 = true;
			int i1 = block.getBlockTextureFromSideAndMetadata(1, l);
			float f9 = (float) BlockFluids.func_293_a(blockAccess, i, j, k, material);
			if (f9 > -999F) {
				i1 = block.getBlockTextureFromSideAndMetadata(2, l);
			}
			int l1 = (i1 & 0xf) << 4;
			int j2 = i1 & 0xf0;
			double d2 = ((double) l1 + 8D) / 256D;
			double d3 = ((double) j2 + 8D) / 256D;
			if (f9 < -999F) {
				f9 = 0.0F;
			} else {
				d2 = (float) (l1 + 16) / 256F;
				d3 = (float) (j2 + 16) / 256F;
			}
			float f11 = (MathHelper.sin(f9) * 8F) / 256F;
			float f13 = (MathHelper.cos(f9) * 8F) / 256F;
			float f15 = block.getBlockBrightness(blockAccess, i, j, k);
			tessellator.setColorOpaque_F(f1 * f15, f1 * f15, f1 * f15);
			tessellator.addVertexWithUV(i + 0, (float) j + f4, k + 0, d2 - (double) f13 - (double) f11,
					(d3 - (double) f13) + (double) f11);
			tessellator.addVertexWithUV(i + 0, (float) j + f5, k + 1, (d2 - (double) f13) + (double) f11,
					d3 + (double) f13 + (double) f11);
			tessellator.addVertexWithUV(i + 1, (float) j + f6, k + 1, d2 + (double) f13 + (double) f11,
					(d3 + (double) f13) - (double) f11);
			tessellator.addVertexWithUV(i + 1, (float) j + f7, k + 0, (d2 + (double) f13) - (double) f11,
					d3 - (double) f13 - (double) f11);
		}
		if (renderAllFaces || flag1) {
			float f8 = block.getBlockBrightness(blockAccess, i, j - 1, k);
			tessellator.setColorOpaque_F(f * f8, f * f8, f * f8);
			renderBottomFace(block, i, j, k, block.getBlockTextureFromSide(0));
			flag2 = true;
		}
		for (int j1 = 0; j1 < 4; j1++) {
			int k1 = i;
			int i2 = j;
			int k2 = k;
			if (j1 == 0) {
				k2--;
			}
			if (j1 == 1) {
				k2++;
			}
			if (j1 == 2) {
				k1--;
			}
			if (j1 == 3) {
				k1++;
			}
			int l2 = block.getBlockTextureFromSideAndMetadata(j1 + 2, l);
			int i3 = (l2 & 0xf) << 4;
			int j3 = l2 & 0xf0;
			if (!renderAllFaces && !aflag[j1]) {
				continue;
			}
			float f10;
			float f12;
			float f14;
			float f16;
			float f17;
			float f18;
			if (j1 == 0) {
				f10 = f4;
				f12 = f7;
				f14 = i;
				f17 = i + 1;
				f16 = k;
				f18 = k;
			} else if (j1 == 1) {
				f10 = f6;
				f12 = f5;
				f14 = i + 1;
				f17 = i;
				f16 = k + 1;
				f18 = k + 1;
			} else if (j1 == 2) {
				f10 = f5;
				f12 = f4;
				f14 = i;
				f17 = i;
				f16 = k + 1;
				f18 = k;
			} else {
				f10 = f7;
				f12 = f6;
				f14 = i + 1;
				f17 = i + 1;
				f16 = k;
				f18 = k + 1;
			}
			flag2 = true;
			double d4 = (float) (i3 + 0) / 256F;
			double d5 = ((double) (i3 + 16) - 0.01D) / 256D;
			double d6 = ((float) j3 + (1.0F - f10) * 16F) / 256F;
			double d7 = ((float) j3 + (1.0F - f12) * 16F) / 256F;
			double d8 = ((double) (j3 + 16) - 0.01D) / 256D;
			float f19 = block.getBlockBrightness(blockAccess, k1, i2, k2);
			if (j1 < 2) {
				f19 *= f2;
			} else {
				f19 *= f3;
			}
			tessellator.setColorOpaque_F(f1 * f19, f1 * f19, f1 * f19);
			tessellator.addVertexWithUV(f14, (float) j + f10, f16, d4, d6);
			tessellator.addVertexWithUV(f17, (float) j + f12, f18, d5, d7);
			tessellator.addVertexWithUV(f17, j + 0, f18, d5, d8);
			tessellator.addVertexWithUV(f14, j + 0, f16, d4, d8);
		}

		block.minY = d;
		block.maxY = d1;
		return flag2;
	}

	private float func_1224_a(int i, int j, int k, Material material) {
		int l = 0;
		float f = 0.0F;
		for (int i1 = 0; i1 < 4; i1++) {
			int j1 = i - (i1 & 1);
			int k1 = j;
			int l1 = k - (i1 >> 1 & 1);
			if (blockAccess.getBlockMaterial(j1, k1 + 1, l1) == material) {
				return 1.0F;
			}
			Material material1 = blockAccess.getBlockMaterial(j1, k1, l1);
			if (material1 == material) {
				int i2 = blockAccess.getBlockMetadata(j1, k1, l1);
				if (i2 >= 8 || i2 == 0) {
					f += BlockFluids.setFluidHeight(i2) * 10F;
					l += 10;
				}
				f += BlockFluids.setFluidHeight(i2);
				l++;
				continue;
			}
			if (!material1.isSolid()) {
				f++;
				l++;
			}
		}

		return 1.0F - f / (float) l;
	}

	public void renderBlockFallingSand(Block block, World world, int i, int j, int k) {
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		float f4 = block.getBlockBrightness(world, i, j, k);
		float f5 = block.getBlockBrightness(world, i, j - 1, k);
		if (f5 < f4) {
			f5 = f4;
		}
		tessellator.setColorOpaque_F(f * f5, f * f5, f * f5);
		renderBottomFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(0));
		f5 = block.getBlockBrightness(world, i, j + 1, k);
		if (f5 < f4) {
			f5 = f4;
		}
		tessellator.setColorOpaque_F(f1 * f5, f1 * f5, f1 * f5);
		renderTopFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(1));
		f5 = block.getBlockBrightness(world, i, j, k - 1);
		if (f5 < f4) {
			f5 = f4;
		}
		tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
		renderEastFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(2));
		f5 = block.getBlockBrightness(world, i, j, k + 1);
		if (f5 < f4) {
			f5 = f4;
		}
		tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
		renderWestFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(3));
		f5 = block.getBlockBrightness(world, i - 1, j, k);
		if (f5 < f4) {
			f5 = f4;
		}
		tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
		renderNorthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(4));
		f5 = block.getBlockBrightness(world, i + 1, j, k);
		if (f5 < f4) {
			f5 = f4;
		}
		tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
		renderSouthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(5));
		tessellator.draw();
	}

	public boolean renderStandardBlock(Block block, int i, int j, int k) {
		int l = block.colorMultiplier(blockAccess, i, j, k);
		float f = (float) (l >> 16 & 0xff) / 255F;
		float f1 = (float) (l >> 8 & 0xff) / 255F;
		float f2 = (float) (l & 0xff) / 255F;
		if (Minecraft.func_22005_v()) {
			return func_22330_a(block, i, j, k, f, f1, f2);
		} else {
			return renderStandardBlockWithColorMultiplier(block, i, j, k, f, f1, f2);
		}
	}

	public boolean func_22330_a(Block block, int i, int j, int k, float f, float f1, float f2) {
		field_22385_e = true;
		boolean flag = false;
		float f3 = field_22384_f;
		float f10 = field_22384_f;
		float f17 = field_22384_f;
		float f24 = field_22384_f;
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		boolean flag4 = true;
		boolean flag5 = true;
		boolean flag6 = true;
		field_22384_f = block.getBlockBrightness(blockAccess, i, j, k);
		field_22383_g = block.getBlockBrightness(blockAccess, i - 1, j, k);
		field_22382_h = block.getBlockBrightness(blockAccess, i, j - 1, k);
		field_22381_i = block.getBlockBrightness(blockAccess, i, j, k - 1);
		field_22380_j = block.getBlockBrightness(blockAccess, i + 1, j, k);
		field_22379_k = block.getBlockBrightness(blockAccess, i, j + 1, k);
		field_22378_l = block.getBlockBrightness(blockAccess, i, j, k + 1);
		field_22338_U = Block.field_340_s[blockAccess.getBlockId(i + 1, j + 1, k)];
		field_22359_ac = Block.field_340_s[blockAccess.getBlockId(i + 1, j - 1, k)];
		field_22334_Y = Block.field_340_s[blockAccess.getBlockId(i + 1, j, k + 1)];
		field_22363_aa = Block.field_340_s[blockAccess.getBlockId(i + 1, j, k - 1)];
		field_22337_V = Block.field_340_s[blockAccess.getBlockId(i - 1, j + 1, k)];
		field_22357_ad = Block.field_340_s[blockAccess.getBlockId(i - 1, j - 1, k)];
		field_22335_X = Block.field_340_s[blockAccess.getBlockId(i - 1, j, k - 1)];
		field_22333_Z = Block.field_340_s[blockAccess.getBlockId(i - 1, j, k + 1)];
		field_22336_W = Block.field_340_s[blockAccess.getBlockId(i, j + 1, k + 1)];
		field_22339_T = Block.field_340_s[blockAccess.getBlockId(i, j + 1, k - 1)];
		field_22355_ae = Block.field_340_s[blockAccess.getBlockId(i, j - 1, k + 1)];
		field_22361_ab = Block.field_340_s[blockAccess.getBlockId(i, j - 1, k - 1)];
		if (block.blockIndexInTexture == 3) {
			flag1 = flag3 = flag4 = flag5 = flag6 = false;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j - 1, k, 0)) {
			float f4;
			float f11;
			float f18;
			float f25;
			if (field_22352_G > 0) {
				j--;
				field_22376_n = block.getBlockBrightness(blockAccess, i - 1, j, k);
				field_22374_p = block.getBlockBrightness(blockAccess, i, j, k - 1);
				field_22373_q = block.getBlockBrightness(blockAccess, i, j, k + 1);
				field_22371_s = block.getBlockBrightness(blockAccess, i + 1, j, k);
				if (field_22361_ab || field_22357_ad) {
					field_22377_m = block.getBlockBrightness(blockAccess, i - 1, j, k - 1);
				} else {
					field_22377_m = field_22376_n;
				}
				if (field_22355_ae || field_22357_ad) {
					field_22375_o = block.getBlockBrightness(blockAccess, i - 1, j, k + 1);
				} else {
					field_22375_o = field_22376_n;
				}
				if (field_22361_ab || field_22359_ac) {
					field_22372_r = block.getBlockBrightness(blockAccess, i + 1, j, k - 1);
				} else {
					field_22372_r = field_22371_s;
				}
				if (field_22355_ae || field_22359_ac) {
					field_22370_t = block.getBlockBrightness(blockAccess, i + 1, j, k + 1);
				} else {
					field_22370_t = field_22371_s;
				}
				j++;
				f4 = (field_22375_o + field_22376_n + field_22373_q + field_22382_h) / 4F;
				f25 = (field_22373_q + field_22382_h + field_22370_t + field_22371_s) / 4F;
				f18 = (field_22382_h + field_22374_p + field_22371_s + field_22372_r) / 4F;
				f11 = (field_22376_n + field_22377_m + field_22382_h + field_22374_p) / 4F;
			} else {
				f4 = f11 = f18 = f25 = field_22382_h;
			}
			field_22351_H = field_22350_I = field_22349_J = field_22348_K = (flag1 ? f : 1.0F) * 0.5F;
			field_22347_L = field_22346_M = field_22345_N = field_22344_O = (flag1 ? f1 : 1.0F) * 0.5F;
			field_22343_P = field_22342_Q = field_22341_R = field_22340_S = (flag1 ? f2 : 1.0F) * 0.5F;
			field_22351_H *= f4;
			field_22347_L *= f4;
			field_22343_P *= f4;
			field_22350_I *= f11;
			field_22346_M *= f11;
			field_22342_Q *= f11;
			field_22349_J *= f18;
			field_22345_N *= f18;
			field_22341_R *= f18;
			field_22348_K *= f25;
			field_22344_O *= f25;
			field_22340_S *= f25;
			renderBottomFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 0));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j + 1, k, 1)) {
			float f5;
			float f12;
			float f19;
			float f26;
			if (field_22352_G > 0) {
				j++;
				field_22368_v = block.getBlockBrightness(blockAccess, i - 1, j, k);
				field_22364_z = block.getBlockBrightness(blockAccess, i + 1, j, k);
				field_22366_x = block.getBlockBrightness(blockAccess, i, j, k - 1);
				field_22362_A = block.getBlockBrightness(blockAccess, i, j, k + 1);
				if (field_22339_T || field_22337_V) {
					field_22369_u = block.getBlockBrightness(blockAccess, i - 1, j, k - 1);
				} else {
					field_22369_u = field_22368_v;
				}
				if (field_22339_T || field_22338_U) {
					field_22365_y = block.getBlockBrightness(blockAccess, i + 1, j, k - 1);
				} else {
					field_22365_y = field_22364_z;
				}
				if (field_22336_W || field_22337_V) {
					field_22367_w = block.getBlockBrightness(blockAccess, i - 1, j, k + 1);
				} else {
					field_22367_w = field_22368_v;
				}
				if (field_22336_W || field_22338_U) {
					field_22360_B = block.getBlockBrightness(blockAccess, i + 1, j, k + 1);
				} else {
					field_22360_B = field_22364_z;
				}
				j--;
				f26 = (field_22367_w + field_22368_v + field_22362_A + field_22379_k) / 4F;
				f5 = (field_22362_A + field_22379_k + field_22360_B + field_22364_z) / 4F;
				f12 = (field_22379_k + field_22366_x + field_22364_z + field_22365_y) / 4F;
				f19 = (field_22368_v + field_22369_u + field_22379_k + field_22366_x) / 4F;
			} else {
				f5 = f12 = f19 = f26 = field_22379_k;
			}
			field_22351_H = field_22350_I = field_22349_J = field_22348_K = flag2 ? f : 1.0F;
			field_22347_L = field_22346_M = field_22345_N = field_22344_O = flag2 ? f1 : 1.0F;
			field_22343_P = field_22342_Q = field_22341_R = field_22340_S = flag2 ? f2 : 1.0F;
			field_22351_H *= f5;
			field_22347_L *= f5;
			field_22343_P *= f5;
			field_22350_I *= f12;
			field_22346_M *= f12;
			field_22342_Q *= f12;
			field_22349_J *= f19;
			field_22345_N *= f19;
			field_22341_R *= f19;
			field_22348_K *= f26;
			field_22344_O *= f26;
			field_22340_S *= f26;
			renderTopFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 1));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2)) {
			float f6;
			float f13;
			float f20;
			float f27;
			if (field_22352_G > 0) {
				k--;
				field_22358_C = block.getBlockBrightness(blockAccess, i - 1, j, k);
				field_22374_p = block.getBlockBrightness(blockAccess, i, j - 1, k);
				field_22366_x = block.getBlockBrightness(blockAccess, i, j + 1, k);
				field_22356_D = block.getBlockBrightness(blockAccess, i + 1, j, k);
				if (field_22335_X || field_22361_ab) {
					field_22377_m = block.getBlockBrightness(blockAccess, i - 1, j - 1, k);
				} else {
					field_22377_m = field_22358_C;
				}
				if (field_22335_X || field_22339_T) {
					field_22369_u = block.getBlockBrightness(blockAccess, i - 1, j + 1, k);
				} else {
					field_22369_u = field_22358_C;
				}
				if (field_22363_aa || field_22361_ab) {
					field_22372_r = block.getBlockBrightness(blockAccess, i + 1, j - 1, k);
				} else {
					field_22372_r = field_22356_D;
				}
				if (field_22363_aa || field_22339_T) {
					field_22365_y = block.getBlockBrightness(blockAccess, i + 1, j + 1, k);
				} else {
					field_22365_y = field_22356_D;
				}
				k++;
				f6 = (field_22358_C + field_22369_u + field_22381_i + field_22366_x) / 4F;
				f13 = (field_22381_i + field_22366_x + field_22356_D + field_22365_y) / 4F;
				f20 = (field_22374_p + field_22381_i + field_22372_r + field_22356_D) / 4F;
				f27 = (field_22377_m + field_22358_C + field_22374_p + field_22381_i) / 4F;
			} else {
				f6 = f13 = f20 = f27 = field_22381_i;
			}
			field_22351_H = field_22350_I = field_22349_J = field_22348_K = (flag3 ? f : 1.0F) * 0.8F;
			field_22347_L = field_22346_M = field_22345_N = field_22344_O = (flag3 ? f1 : 1.0F) * 0.8F;
			field_22343_P = field_22342_Q = field_22341_R = field_22340_S = (flag3 ? f2 : 1.0F) * 0.8F;
			field_22351_H *= f6;
			field_22347_L *= f6;
			field_22343_P *= f6;
			field_22350_I *= f13;
			field_22346_M *= f13;
			field_22342_Q *= f13;
			field_22349_J *= f20;
			field_22345_N *= f20;
			field_22341_R *= f20;
			field_22348_K *= f27;
			field_22344_O *= f27;
			field_22340_S *= f27;
			renderEastFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 2));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3)) {
			float f7;
			float f14;
			float f21;
			float f28;
			if (field_22352_G > 0) {
				k++;
				field_22354_E = block.getBlockBrightness(blockAccess, i - 1, j, k);
				field_22353_F = block.getBlockBrightness(blockAccess, i + 1, j, k);
				field_22373_q = block.getBlockBrightness(blockAccess, i, j - 1, k);
				field_22362_A = block.getBlockBrightness(blockAccess, i, j + 1, k);
				if (field_22333_Z || field_22355_ae) {
					field_22375_o = block.getBlockBrightness(blockAccess, i - 1, j - 1, k);
				} else {
					field_22375_o = field_22354_E;
				}
				if (field_22333_Z || field_22336_W) {
					field_22367_w = block.getBlockBrightness(blockAccess, i - 1, j + 1, k);
				} else {
					field_22367_w = field_22354_E;
				}
				if (field_22334_Y || field_22355_ae) {
					field_22370_t = block.getBlockBrightness(blockAccess, i + 1, j - 1, k);
				} else {
					field_22370_t = field_22353_F;
				}
				if (field_22334_Y || field_22336_W) {
					field_22360_B = block.getBlockBrightness(blockAccess, i + 1, j + 1, k);
				} else {
					field_22360_B = field_22353_F;
				}
				k--;
				f7 = (field_22354_E + field_22367_w + field_22378_l + field_22362_A) / 4F;
				f28 = (field_22378_l + field_22362_A + field_22353_F + field_22360_B) / 4F;
				f21 = (field_22373_q + field_22378_l + field_22370_t + field_22353_F) / 4F;
				f14 = (field_22375_o + field_22354_E + field_22373_q + field_22378_l) / 4F;
			} else {
				f7 = f14 = f21 = f28 = field_22378_l;
			}
			field_22351_H = field_22350_I = field_22349_J = field_22348_K = (flag4 ? f : 1.0F) * 0.8F;
			field_22347_L = field_22346_M = field_22345_N = field_22344_O = (flag4 ? f1 : 1.0F) * 0.8F;
			field_22343_P = field_22342_Q = field_22341_R = field_22340_S = (flag4 ? f2 : 1.0F) * 0.8F;
			field_22351_H *= f7;
			field_22347_L *= f7;
			field_22343_P *= f7;
			field_22350_I *= f14;
			field_22346_M *= f14;
			field_22342_Q *= f14;
			field_22349_J *= f21;
			field_22345_N *= f21;
			field_22341_R *= f21;
			field_22348_K *= f28;
			field_22344_O *= f28;
			field_22340_S *= f28;
			renderWestFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 3));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4)) {
			float f8;
			float f15;
			float f22;
			float f29;
			if (field_22352_G > 0) {
				i--;
				field_22376_n = block.getBlockBrightness(blockAccess, i, j - 1, k);
				field_22358_C = block.getBlockBrightness(blockAccess, i, j, k - 1);
				field_22354_E = block.getBlockBrightness(blockAccess, i, j, k + 1);
				field_22368_v = block.getBlockBrightness(blockAccess, i, j + 1, k);
				if (field_22335_X || field_22357_ad) {
					field_22377_m = block.getBlockBrightness(blockAccess, i, j - 1, k - 1);
				} else {
					field_22377_m = field_22358_C;
				}
				if (field_22333_Z || field_22357_ad) {
					field_22375_o = block.getBlockBrightness(blockAccess, i, j - 1, k + 1);
				} else {
					field_22375_o = field_22354_E;
				}
				if (field_22335_X || field_22337_V) {
					field_22369_u = block.getBlockBrightness(blockAccess, i, j + 1, k - 1);
				} else {
					field_22369_u = field_22358_C;
				}
				if (field_22333_Z || field_22337_V) {
					field_22367_w = block.getBlockBrightness(blockAccess, i, j + 1, k + 1);
				} else {
					field_22367_w = field_22354_E;
				}
				i++;
				f29 = (field_22376_n + field_22375_o + field_22383_g + field_22354_E) / 4F;
				f8 = (field_22383_g + field_22354_E + field_22368_v + field_22367_w) / 4F;
				f15 = (field_22358_C + field_22383_g + field_22369_u + field_22368_v) / 4F;
				f22 = (field_22377_m + field_22376_n + field_22358_C + field_22383_g) / 4F;
			} else {
				f8 = f15 = f22 = f29 = field_22383_g;
			}
			field_22351_H = field_22350_I = field_22349_J = field_22348_K = (flag5 ? f : 1.0F) * 0.6F;
			field_22347_L = field_22346_M = field_22345_N = field_22344_O = (flag5 ? f1 : 1.0F) * 0.6F;
			field_22343_P = field_22342_Q = field_22341_R = field_22340_S = (flag5 ? f2 : 1.0F) * 0.6F;
			field_22351_H *= f8;
			field_22347_L *= f8;
			field_22343_P *= f8;
			field_22350_I *= f15;
			field_22346_M *= f15;
			field_22342_Q *= f15;
			field_22349_J *= f22;
			field_22345_N *= f22;
			field_22341_R *= f22;
			field_22348_K *= f29;
			field_22344_O *= f29;
			field_22340_S *= f29;
			renderNorthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 4));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5)) {
			float f9;
			float f16;
			float f23;
			float f30;
			if (field_22352_G > 0) {
				i++;
				field_22371_s = block.getBlockBrightness(blockAccess, i, j - 1, k);
				field_22356_D = block.getBlockBrightness(blockAccess, i, j, k - 1);
				field_22353_F = block.getBlockBrightness(blockAccess, i, j, k + 1);
				field_22364_z = block.getBlockBrightness(blockAccess, i, j + 1, k);
				if (field_22359_ac || field_22363_aa) {
					field_22372_r = block.getBlockBrightness(blockAccess, i, j - 1, k - 1);
				} else {
					field_22372_r = field_22356_D;
				}
				if (field_22359_ac || field_22334_Y) {
					field_22370_t = block.getBlockBrightness(blockAccess, i, j - 1, k + 1);
				} else {
					field_22370_t = field_22353_F;
				}
				if (field_22338_U || field_22363_aa) {
					field_22365_y = block.getBlockBrightness(blockAccess, i, j + 1, k - 1);
				} else {
					field_22365_y = field_22356_D;
				}
				if (field_22338_U || field_22334_Y) {
					field_22360_B = block.getBlockBrightness(blockAccess, i, j + 1, k + 1);
				} else {
					field_22360_B = field_22353_F;
				}
				i--;
				f9 = (field_22371_s + field_22370_t + field_22380_j + field_22353_F) / 4F;
				f30 = (field_22380_j + field_22353_F + field_22364_z + field_22360_B) / 4F;
				f23 = (field_22356_D + field_22380_j + field_22365_y + field_22364_z) / 4F;
				f16 = (field_22372_r + field_22371_s + field_22356_D + field_22380_j) / 4F;
			} else {
				f9 = f16 = f23 = f30 = field_22380_j;
			}
			field_22351_H = field_22350_I = field_22349_J = field_22348_K = (flag6 ? f : 1.0F) * 0.6F;
			field_22347_L = field_22346_M = field_22345_N = field_22344_O = (flag6 ? f1 : 1.0F) * 0.6F;
			field_22343_P = field_22342_Q = field_22341_R = field_22340_S = (flag6 ? f2 : 1.0F) * 0.6F;
			field_22351_H *= f9;
			field_22347_L *= f9;
			field_22343_P *= f9;
			field_22350_I *= f16;
			field_22346_M *= f16;
			field_22342_Q *= f16;
			field_22349_J *= f23;
			field_22345_N *= f23;
			field_22341_R *= f23;
			field_22348_K *= f30;
			field_22344_O *= f30;
			field_22340_S *= f30;
			renderSouthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 5));
			flag = true;
		}
		field_22385_e = false;
		return flag;
	}

	public boolean renderStandardBlockWithColorMultiplier(Block block, int i, int j, int k, float f, float f1,
			float f2) {
		field_22385_e = false;
		Tessellator tessellator = Tessellator.instance;
		boolean flag = false;
		float f3 = 0.5F;
		float f4 = 1.0F;
		float f5 = 0.8F;
		float f6 = 0.6F;
		float f7 = f4 * f;
		float f8 = f4 * f1;
		float f9 = f4 * f2;
		if (block == Block.grass) {
			f = f1 = f2 = 1.0F;
		}
		float f10 = f3 * f;
		float f11 = f5 * f;
		float f12 = f6 * f;
		float f13 = f3 * f1;
		float f14 = f5 * f1;
		float f15 = f6 * f1;
		float f16 = f3 * f2;
		float f17 = f5 * f2;
		float f18 = f6 * f2;
		float f19 = block.getBlockBrightness(blockAccess, i, j, k);
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j - 1, k, 0)) {
			float f20 = block.getBlockBrightness(blockAccess, i, j - 1, k);
			tessellator.setColorOpaque_F(f10 * f20, f13 * f20, f16 * f20);
			renderBottomFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 0));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j + 1, k, 1)) {
			float f21 = block.getBlockBrightness(blockAccess, i, j + 1, k);
			if (block.maxY != 1.0D && !block.blockMaterial.getIsLiquid()) {
				f21 = f19;
			}
			tessellator.setColorOpaque_F(f7 * f21, f8 * f21, f9 * f21);
			renderTopFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 1));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2)) {
			float f22 = block.getBlockBrightness(blockAccess, i, j, k - 1);
			if (block.minZ > 0.0D) {
				f22 = f19;
			}
			tessellator.setColorOpaque_F(f11 * f22, f14 * f22, f17 * f22);
			renderEastFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 2));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3)) {
			float f23 = block.getBlockBrightness(blockAccess, i, j, k + 1);
			if (block.maxZ < 1.0D) {
				f23 = f19;
			}
			tessellator.setColorOpaque_F(f11 * f23, f14 * f23, f17 * f23);
			renderWestFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 3));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4)) {
			float f24 = block.getBlockBrightness(blockAccess, i - 1, j, k);
			if (block.minX > 0.0D) {
				f24 = f19;
			}
			tessellator.setColorOpaque_F(f12 * f24, f15 * f24, f18 * f24);
			renderNorthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 4));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5)) {
			float f25 = block.getBlockBrightness(blockAccess, i + 1, j, k);
			if (block.maxX < 1.0D) {
				f25 = f19;
			}
			tessellator.setColorOpaque_F(f12 * f25, f15 * f25, f18 * f25);
			renderSouthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 5));
			flag = true;
		}
		return flag;
	}

	public boolean renderBlockCactus(Block block, int i, int j, int k) {
		int l = block.colorMultiplier(blockAccess, i, j, k);
		float f = (float) (l >> 16 & 0xff) / 255F;
		float f1 = (float) (l >> 8 & 0xff) / 255F;
		float f2 = (float) (l & 0xff) / 255F;
		return func_1230_b(block, i, j, k, f, f1, f2);
	}

	public boolean func_1230_b(Block block, int i, int j, int k, float f, float f1, float f2) {
		Tessellator tessellator = Tessellator.instance;
		boolean flag = false;
		float f3 = 0.5F;
		float f4 = 1.0F;
		float f5 = 0.8F;
		float f6 = 0.6F;
		float f7 = f3 * f;
		float f8 = f4 * f;
		float f9 = f5 * f;
		float f10 = f6 * f;
		float f11 = f3 * f1;
		float f12 = f4 * f1;
		float f13 = f5 * f1;
		float f14 = f6 * f1;
		float f15 = f3 * f2;
		float f16 = f4 * f2;
		float f17 = f5 * f2;
		float f18 = f6 * f2;
		float f19 = 0.0625F;
		float f20 = block.getBlockBrightness(blockAccess, i, j, k);
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j - 1, k, 0)) {
			float f21 = block.getBlockBrightness(blockAccess, i, j - 1, k);
			tessellator.setColorOpaque_F(f7 * f21, f11 * f21, f15 * f21);
			renderBottomFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 0));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j + 1, k, 1)) {
			float f22 = block.getBlockBrightness(blockAccess, i, j + 1, k);
			if (block.maxY != 1.0D && !block.blockMaterial.getIsLiquid()) {
				f22 = f20;
			}
			tessellator.setColorOpaque_F(f8 * f22, f12 * f22, f16 * f22);
			renderTopFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 1));
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2)) {
			float f23 = block.getBlockBrightness(blockAccess, i, j, k - 1);
			if (block.minZ > 0.0D) {
				f23 = f20;
			}
			tessellator.setColorOpaque_F(f9 * f23, f13 * f23, f17 * f23);
			tessellator.setTranslationF(0.0F, 0.0F, f19);
			renderEastFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 2));
			tessellator.setTranslationF(0.0F, 0.0F, -f19);
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3)) {
			float f24 = block.getBlockBrightness(blockAccess, i, j, k + 1);
			if (block.maxZ < 1.0D) {
				f24 = f20;
			}
			tessellator.setColorOpaque_F(f9 * f24, f13 * f24, f17 * f24);
			tessellator.setTranslationF(0.0F, 0.0F, -f19);
			renderWestFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 3));
			tessellator.setTranslationF(0.0F, 0.0F, f19);
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4)) {
			float f25 = block.getBlockBrightness(blockAccess, i - 1, j, k);
			if (block.minX > 0.0D) {
				f25 = f20;
			}
			tessellator.setColorOpaque_F(f10 * f25, f14 * f25, f18 * f25);
			tessellator.setTranslationF(f19, 0.0F, 0.0F);
			renderNorthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 4));
			tessellator.setTranslationF(-f19, 0.0F, 0.0F);
			flag = true;
		}
		if (renderAllFaces || block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5)) {
			float f26 = block.getBlockBrightness(blockAccess, i + 1, j, k);
			if (block.maxX < 1.0D) {
				f26 = f20;
			}
			tessellator.setColorOpaque_F(f10 * f26, f14 * f26, f18 * f26);
			tessellator.setTranslationF(-f19, 0.0F, 0.0F);
			renderSouthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 5));
			tessellator.setTranslationF(f19, 0.0F, 0.0F);
			flag = true;
		}
		return flag;
	}

	public boolean renderBlockFence(Block block, int i, int j, int k) {
		boolean flag = false;
		float f = 0.375F;
		float f1 = 0.625F;
		block.setBlockBounds(f, 0.0F, f, f1, 1.0F, f1);
		renderStandardBlock(block, i, j, k);
		boolean flag1 = false;
		boolean flag2 = false;
		if (blockAccess.getBlockId(i - 1, j, k) == block.blockID
				|| blockAccess.getBlockId(i + 1, j, k) == block.blockID) {
			flag1 = true;
		}
		if (blockAccess.getBlockId(i, j, k - 1) == block.blockID
				|| blockAccess.getBlockId(i, j, k + 1) == block.blockID) {
			flag2 = true;
		}
		boolean flag3 = blockAccess.getBlockId(i - 1, j, k) == block.blockID;
		boolean flag4 = blockAccess.getBlockId(i + 1, j, k) == block.blockID;
		boolean flag5 = blockAccess.getBlockId(i, j, k - 1) == block.blockID;
		boolean flag6 = blockAccess.getBlockId(i, j, k + 1) == block.blockID;
		if (!flag1 && !flag2) {
			flag1 = true;
		}
		f = 0.4375F;
		f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = flag3 ? 0.0F : f;
		float f5 = flag4 ? 1.0F : f1;
		float f6 = flag5 ? 0.0F : f;
		float f7 = flag6 ? 1.0F : f1;
		if (flag1) {
			block.setBlockBounds(f4, f2, f, f5, f3, f1);
			renderStandardBlock(block, i, j, k);
		}
		if (flag2) {
			block.setBlockBounds(f, f2, f6, f1, f3, f7);
			renderStandardBlock(block, i, j, k);
		}
		f2 = 0.375F;
		f3 = 0.5625F;
		if (flag1) {
			block.setBlockBounds(f4, f2, f, f5, f3, f1);
			renderStandardBlock(block, i, j, k);
		}
		if (flag2) {
			block.setBlockBounds(f, f2, f6, f1, f3, f7);
			renderStandardBlock(block, i, j, k);
		}
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return flag;
	}

	public boolean renderBlockStairs(Block block, int i, int j, int k) {
		boolean flag = false;
		int l = blockAccess.getBlockMetadata(i, j, k);
		if (l == 0) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			renderStandardBlock(block, i, j, k);
			block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderStandardBlock(block, i, j, k);
		} else if (l == 1) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			renderStandardBlock(block, i, j, k);
			block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			renderStandardBlock(block, i, j, k);
		} else if (l == 2) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			renderStandardBlock(block, i, j, k);
			block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			renderStandardBlock(block, i, j, k);
		} else if (l == 3) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			renderStandardBlock(block, i, j, k);
			block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			renderStandardBlock(block, i, j, k);
		}
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return flag;
	}

	public boolean renderBlockDoor(Block block, int i, int j, int k) {
		Tessellator tessellator = Tessellator.instance;
		BlockDoor blockdoor = (BlockDoor) block;
		boolean flag = false;
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		float f4 = block.getBlockBrightness(blockAccess, i, j, k);
		float f5 = block.getBlockBrightness(blockAccess, i, j - 1, k);
		if (blockdoor.minY > 0.0D) {
			f5 = f4;
		}
		if (Block.lightValue[block.blockID] > 0) {
			f5 = 1.0F;
		}
		tessellator.setColorOpaque_F(f * f5, f * f5, f * f5);
		renderBottomFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 0));
		flag = true;
		f5 = block.getBlockBrightness(blockAccess, i, j + 1, k);
		if (blockdoor.maxY < 1.0D) {
			f5 = f4;
		}
		if (Block.lightValue[block.blockID] > 0) {
			f5 = 1.0F;
		}
		tessellator.setColorOpaque_F(f1 * f5, f1 * f5, f1 * f5);
		renderTopFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 1));
		flag = true;
		f5 = block.getBlockBrightness(blockAccess, i, j, k - 1);
		if (blockdoor.minZ > 0.0D) {
			f5 = f4;
		}
		if (Block.lightValue[block.blockID] > 0) {
			f5 = 1.0F;
		}
		tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
		int l = block.getBlockTexture(blockAccess, i, j, k, 2);
		if (l < 0) {
			flipTexture = true;
			l = -l;
		}
		renderEastFace(block, i, j, k, l);
		flag = true;
		flipTexture = false;
		f5 = block.getBlockBrightness(blockAccess, i, j, k + 1);
		if (blockdoor.maxZ < 1.0D) {
			f5 = f4;
		}
		if (Block.lightValue[block.blockID] > 0) {
			f5 = 1.0F;
		}
		tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
		l = block.getBlockTexture(blockAccess, i, j, k, 3);
		if (l < 0) {
			flipTexture = true;
			l = -l;
		}
		renderWestFace(block, i, j, k, l);
		flag = true;
		flipTexture = false;
		f5 = block.getBlockBrightness(blockAccess, i - 1, j, k);
		if (blockdoor.minX > 0.0D) {
			f5 = f4;
		}
		if (Block.lightValue[block.blockID] > 0) {
			f5 = 1.0F;
		}
		tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
		l = block.getBlockTexture(blockAccess, i, j, k, 4);
		if (l < 0) {
			flipTexture = true;
			l = -l;
		}
		renderNorthFace(block, i, j, k, l);
		flag = true;
		flipTexture = false;
		f5 = block.getBlockBrightness(blockAccess, i + 1, j, k);
		if (blockdoor.maxX < 1.0D) {
			f5 = f4;
		}
		if (Block.lightValue[block.blockID] > 0) {
			f5 = 1.0F;
		}
		tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
		l = block.getBlockTexture(blockAccess, i, j, k, 5);
		if (l < 0) {
			flipTexture = true;
			l = -l;
		}
		renderSouthFace(block, i, j, k, l);
		flag = true;
		flipTexture = false;
		return flag;
	}

	public void renderBottomFace(Block block, double d, double d1, double d2, int i) {
		Tessellator tessellator = Tessellator.instance;
		if (overrideBlockTexture >= 0) {
			i = overrideBlockTexture;
		}
		int j = (i & 0xf) << 4;
		int k = i & 0xf0;
		double d3 = ((double) j + block.minX * 16D) / 256D;
		double d4 = (((double) j + block.maxX * 16D) - 0.01D) / 256D;
		double d5 = ((double) k + block.minZ * 16D) / 256D;
		double d6 = (((double) k + block.maxZ * 16D) - 0.01D) / 256D;
		if (block.minX < 0.0D || block.maxX > 1.0D) {
			d3 = ((float) j + 0.0F) / 256F;
			d4 = ((float) j + 15.99F) / 256F;
		}
		if (block.minZ < 0.0D || block.maxZ > 1.0D) {
			d5 = ((float) k + 0.0F) / 256F;
			d6 = ((float) k + 15.99F) / 256F;
		}
		double d7 = d + block.minX;
		double d8 = d + block.maxX;
		double d9 = d1 + block.minY;
		double d10 = d2 + block.minZ;
		double d11 = d2 + block.maxZ;
		if (field_22385_e) {
			tessellator.setColorOpaque_F(field_22351_H, field_22347_L, field_22343_P);
			tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
			tessellator.setColorOpaque_F(field_22350_I, field_22346_M, field_22342_Q);
			tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
			tessellator.setColorOpaque_F(field_22349_J, field_22345_N, field_22341_R);
			tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
			tessellator.setColorOpaque_F(field_22348_K, field_22344_O, field_22340_S);
			tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
		} else {
			tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
			tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
			tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
			tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
		}
	}

	public void renderTopFace(Block block, double d, double d1, double d2, int i) {
		Tessellator tessellator = Tessellator.instance;
		if (overrideBlockTexture >= 0) {
			i = overrideBlockTexture;
		}
		int j = (i & 0xf) << 4;
		int k = i & 0xf0;
		double d3 = ((double) j + block.minX * 16D) / 256D;
		double d4 = (((double) j + block.maxX * 16D) - 0.01D) / 256D;
		double d5 = ((double) k + block.minZ * 16D) / 256D;
		double d6 = (((double) k + block.maxZ * 16D) - 0.01D) / 256D;
		if (block.minX < 0.0D || block.maxX > 1.0D) {
			d3 = ((float) j + 0.0F) / 256F;
			d4 = ((float) j + 15.99F) / 256F;
		}
		if (block.minZ < 0.0D || block.maxZ > 1.0D) {
			d5 = ((float) k + 0.0F) / 256F;
			d6 = ((float) k + 15.99F) / 256F;
		}
		double d7 = d + block.minX;
		double d8 = d + block.maxX;
		double d9 = d1 + block.maxY;
		double d10 = d2 + block.minZ;
		double d11 = d2 + block.maxZ;
		if (field_22385_e) {
			tessellator.setColorOpaque_F(field_22351_H, field_22347_L, field_22343_P);
			tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
			tessellator.setColorOpaque_F(field_22350_I, field_22346_M, field_22342_Q);
			tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
			tessellator.setColorOpaque_F(field_22349_J, field_22345_N, field_22341_R);
			tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
			tessellator.setColorOpaque_F(field_22348_K, field_22344_O, field_22340_S);
			tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
		} else {
			tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
			tessellator.addVertexWithUV(d8, d9, d10, d4, d5);
			tessellator.addVertexWithUV(d7, d9, d10, d3, d5);
			tessellator.addVertexWithUV(d7, d9, d11, d3, d6);
		}
	}

	public void renderEastFace(Block block, double d, double d1, double d2, int i) {
		Tessellator tessellator = Tessellator.instance;
		if (overrideBlockTexture >= 0) {
			i = overrideBlockTexture;
		}
		int j = (i & 0xf) << 4;
		int k = i & 0xf0;
		double d3 = ((double) j + block.minX * 16D) / 256D;
		double d4 = (((double) j + block.maxX * 16D) - 0.01D) / 256D;
		double d5 = ((double) k + block.minY * 16D) / 256D;
		double d6 = (((double) k + block.maxY * 16D) - 0.01D) / 256D;
		if (flipTexture) {
			double d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		if (block.minX < 0.0D || block.maxX > 1.0D) {
			d3 = ((float) j + 0.0F) / 256F;
			d4 = ((float) j + 15.99F) / 256F;
		}
		if (block.minY < 0.0D || block.maxY > 1.0D) {
			d5 = ((float) k + 0.0F) / 256F;
			d6 = ((float) k + 15.99F) / 256F;
		}
		double d8 = d + block.minX;
		double d9 = d + block.maxX;
		double d10 = d1 + block.minY;
		double d11 = d1 + block.maxY;
		double d12 = d2 + block.minZ;
		if (field_22385_e) {
			tessellator.setColorOpaque_F(field_22351_H, field_22347_L, field_22343_P);
			tessellator.addVertexWithUV(d8, d11, d12, d4, d5);
			tessellator.setColorOpaque_F(field_22350_I, field_22346_M, field_22342_Q);
			tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
			tessellator.setColorOpaque_F(field_22349_J, field_22345_N, field_22341_R);
			tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
			tessellator.setColorOpaque_F(field_22348_K, field_22344_O, field_22340_S);
			tessellator.addVertexWithUV(d8, d10, d12, d4, d6);
		} else {
			tessellator.addVertexWithUV(d8, d11, d12, d4, d5);
			tessellator.addVertexWithUV(d9, d11, d12, d3, d5);
			tessellator.addVertexWithUV(d9, d10, d12, d3, d6);
			tessellator.addVertexWithUV(d8, d10, d12, d4, d6);
		}
	}

	public void renderWestFace(Block block, double d, double d1, double d2, int i) {
		Tessellator tessellator = Tessellator.instance;
		if (overrideBlockTexture >= 0) {
			i = overrideBlockTexture;
		}
		int j = (i & 0xf) << 4;
		int k = i & 0xf0;
		double d3 = ((double) j + block.minX * 16D) / 256D;
		double d4 = (((double) j + block.maxX * 16D) - 0.01D) / 256D;
		double d5 = ((double) k + block.minY * 16D) / 256D;
		double d6 = (((double) k + block.maxY * 16D) - 0.01D) / 256D;
		if (flipTexture) {
			double d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		if (block.minX < 0.0D || block.maxX > 1.0D) {
			d3 = ((float) j + 0.0F) / 256F;
			d4 = ((float) j + 15.99F) / 256F;
		}
		if (block.minY < 0.0D || block.maxY > 1.0D) {
			d5 = ((float) k + 0.0F) / 256F;
			d6 = ((float) k + 15.99F) / 256F;
		}
		double d8 = d + block.minX;
		double d9 = d + block.maxX;
		double d10 = d1 + block.minY;
		double d11 = d1 + block.maxY;
		double d12 = d2 + block.maxZ;
		if (field_22385_e) {
			tessellator.setColorOpaque_F(field_22351_H, field_22347_L, field_22343_P);
			tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
			tessellator.setColorOpaque_F(field_22350_I, field_22346_M, field_22342_Q);
			tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
			tessellator.setColorOpaque_F(field_22349_J, field_22345_N, field_22341_R);
			tessellator.addVertexWithUV(d9, d10, d12, d4, d6);
			tessellator.setColorOpaque_F(field_22348_K, field_22344_O, field_22340_S);
			tessellator.addVertexWithUV(d9, d11, d12, d4, d5);
		} else {
			tessellator.addVertexWithUV(d8, d11, d12, d3, d5);
			tessellator.addVertexWithUV(d8, d10, d12, d3, d6);
			tessellator.addVertexWithUV(d9, d10, d12, d4, d6);
			tessellator.addVertexWithUV(d9, d11, d12, d4, d5);
		}
	}

	public void renderNorthFace(Block block, double d, double d1, double d2, int i) {
		Tessellator tessellator = Tessellator.instance;
		if (overrideBlockTexture >= 0) {
			i = overrideBlockTexture;
		}
		int j = (i & 0xf) << 4;
		int k = i & 0xf0;
		double d3 = ((double) j + block.minZ * 16D) / 256D;
		double d4 = (((double) j + block.maxZ * 16D) - 0.01D) / 256D;
		double d5 = ((double) k + block.minY * 16D) / 256D;
		double d6 = (((double) k + block.maxY * 16D) - 0.01D) / 256D;
		if (flipTexture) {
			double d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		if (block.minZ < 0.0D || block.maxZ > 1.0D) {
			d3 = ((float) j + 0.0F) / 256F;
			d4 = ((float) j + 15.99F) / 256F;
		}
		if (block.minY < 0.0D || block.maxY > 1.0D) {
			d5 = ((float) k + 0.0F) / 256F;
			d6 = ((float) k + 15.99F) / 256F;
		}
		double d8 = d + block.minX;
		double d9 = d1 + block.minY;
		double d10 = d1 + block.maxY;
		double d11 = d2 + block.minZ;
		double d12 = d2 + block.maxZ;
		if (field_22385_e) {
			tessellator.setColorOpaque_F(field_22351_H, field_22347_L, field_22343_P);
			tessellator.addVertexWithUV(d8, d10, d12, d4, d5);
			tessellator.setColorOpaque_F(field_22350_I, field_22346_M, field_22342_Q);
			tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
			tessellator.setColorOpaque_F(field_22349_J, field_22345_N, field_22341_R);
			tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
			tessellator.setColorOpaque_F(field_22348_K, field_22344_O, field_22340_S);
			tessellator.addVertexWithUV(d8, d9, d12, d4, d6);
		} else {
			tessellator.addVertexWithUV(d8, d10, d12, d4, d5);
			tessellator.addVertexWithUV(d8, d10, d11, d3, d5);
			tessellator.addVertexWithUV(d8, d9, d11, d3, d6);
			tessellator.addVertexWithUV(d8, d9, d12, d4, d6);
		}
	}

	public void renderSouthFace(Block block, double d, double d1, double d2, int i) {
		Tessellator tessellator = Tessellator.instance;
		if (overrideBlockTexture >= 0) {
			i = overrideBlockTexture;
		}
		int j = (i & 0xf) << 4;
		int k = i & 0xf0;
		double d3 = ((double) j + block.minZ * 16D) / 256D;
		double d4 = (((double) j + block.maxZ * 16D) - 0.01D) / 256D;
		double d5 = ((double) k + block.minY * 16D) / 256D;
		double d6 = (((double) k + block.maxY * 16D) - 0.01D) / 256D;
		if (flipTexture) {
			double d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		if (block.minZ < 0.0D || block.maxZ > 1.0D) {
			d3 = ((float) j + 0.0F) / 256F;
			d4 = ((float) j + 15.99F) / 256F;
		}
		if (block.minY < 0.0D || block.maxY > 1.0D) {
			d5 = ((float) k + 0.0F) / 256F;
			d6 = ((float) k + 15.99F) / 256F;
		}
		double d8 = d + block.maxX;
		double d9 = d1 + block.minY;
		double d10 = d1 + block.maxY;
		double d11 = d2 + block.minZ;
		double d12 = d2 + block.maxZ;
		if (field_22385_e) {
			tessellator.setColorOpaque_F(field_22351_H, field_22347_L, field_22343_P);
			tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
			tessellator.setColorOpaque_F(field_22350_I, field_22346_M, field_22342_Q);
			tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
			tessellator.setColorOpaque_F(field_22349_J, field_22345_N, field_22341_R);
			tessellator.addVertexWithUV(d8, d10, d11, d4, d5);
			tessellator.setColorOpaque_F(field_22348_K, field_22344_O, field_22340_S);
			tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
		} else {
			tessellator.addVertexWithUV(d8, d9, d12, d3, d6);
			tessellator.addVertexWithUV(d8, d9, d11, d4, d6);
			tessellator.addVertexWithUV(d8, d10, d11, d4, d5);
			tessellator.addVertexWithUV(d8, d10, d12, d3, d5);
		}
	}

	public void func_1238_a(Block block, float f) {
		int i = block.getRenderType();
		Tessellator tessellator = Tessellator.instance;
		if (i == 0) {
			block.func_237_e();
			tessellator.setTranslationF(-0.5F, -0.5F, -0.5F);
			float f1 = 0.5F;
			float f2 = 1.0F;
			float f3 = 0.8F;
			float f4 = 0.6F;
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(f2, f2, f2, f);
			renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.setColorRGBA_F(f1, f1, f1, f);
			renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
			tessellator.setColorRGBA_F(f3, f3, f3, f);
			renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
			renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
			tessellator.setColorRGBA_F(f4, f4, f4, f);
			renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
			renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
			tessellator.draw();
			tessellator.setTranslationF(0.5F, 0.5F, 0.5F);
		}
	}

	public void func_1227_a(Block block, int i) {
		Tessellator tessellator = Tessellator.instance;
		int j = block.getRenderType();
		if (j == 0) {
			//EaglerAdapter.glPushMatrix();
			//EaglerAdapter.glScalef(-1.0f, 1.0f, -1.0f);
			if(block instanceof BlockPumpkin) {
				BlockPumpkin.flipFix = true;
			}
			block.func_237_e();
			tessellator.setTranslationF(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, i));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, i));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1F);
			renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, i));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, i));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1F, 0.0F, 0.0F);
			renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, i));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, i));
			tessellator.draw();
			tessellator.setTranslationF(0.5F, 0.5F, 0.5F);
			//EaglerAdapter.glPopMatrix();
			if(block instanceof BlockPumpkin) {
				BlockPumpkin.flipFix = false;
			}
		} else if (j == 1) {
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			func_1239_a(block, i, -0.5D, -0.5D, -0.5D);
			tessellator.draw();
		} else if (j == 13) {
			block.func_237_e();
			tessellator.setTranslationF(-0.5F, -0.5F, -0.5F);
			float f = 0.0625F;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1F);
			tessellator.setTranslationF(0.0F, 0.0F, f);
			renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
			tessellator.setTranslationF(0.0F, 0.0F, -f);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			tessellator.setTranslationF(0.0F, 0.0F, -f);
			renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
			tessellator.setTranslationF(0.0F, 0.0F, f);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1F, 0.0F, 0.0F);
			tessellator.setTranslationF(f, 0.0F, 0.0F);
			renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
			tessellator.setTranslationF(-f, 0.0F, 0.0F);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			tessellator.setTranslationF(-f, 0.0F, 0.0F);
			renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
			tessellator.setTranslationF(f, 0.0F, 0.0F);
			tessellator.draw();
			tessellator.setTranslationF(0.5F, 0.5F, 0.5F);
		} else if (j == 6) {
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			func_1245_b(block, i, -0.5D, -0.5D, -0.5D);
			tessellator.draw();
		} else if (j == 2) {
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			renderTorchAtAngle(block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D);
			tessellator.draw();
		} else if (j == 10) {
			for (int k = 0; k < 2; k++) {
				if (k == 0) {
					block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
				}
				if (k == 1) {
					block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
				}
				tessellator.setTranslationF(-0.5F, -0.5F, -0.5F);
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1F, 0.0F);
				renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1F);
				renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1F, 0.0F, 0.0F);
				renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
				tessellator.draw();
				tessellator.setTranslationF(0.5F, 0.5F, 0.5F);
			}

		} else if (j == 11) {
			for (int l = 0; l < 4; l++) {
				float f1 = 0.125F;
				if (l == 0) {
					block.setBlockBounds(0.5F - f1, 0.0F, 0.0F, 0.5F + f1, 1.0F, f1 * 2.0F);
				}
				if (l == 1) {
					block.setBlockBounds(0.5F - f1, 0.0F, 1.0F - f1 * 2.0F, 0.5F + f1, 1.0F, 1.0F);
				}
				f1 = 0.0625F;
				if (l == 2) {
					block.setBlockBounds(0.5F - f1, 1.0F - f1 * 3F, -f1 * 2.0F, 0.5F + f1, 1.0F - f1, 1.0F + f1 * 2.0F);
				}
				if (l == 3) {
					block.setBlockBounds(0.5F - f1, 0.5F - f1 * 3F, -f1 * 2.0F, 0.5F + f1, 0.5F - f1, 1.0F + f1 * 2.0F);
				}
				tessellator.setTranslationF(-0.5F, -0.5F, -0.5F);
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1F, 0.0F);
				renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1F);
				renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1F, 0.0F, 0.0F);
				renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
				tessellator.draw();
				tessellator.setTranslationF(0.5F, 0.5F, 0.5F);
			}

			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	public static boolean func_1219_a(int i) {
		if (i == 0) {
			return true;
		}
		if (i == 13) {
			return true;
		}
		if (i == 10) {
			return true;
		}
		return i == 11;
	}

	private IBlockAccess blockAccess;
	private int overrideBlockTexture;
	private boolean flipTexture;
	private boolean renderAllFaces;
	private boolean field_22385_e;
	private float field_22384_f;
	private float field_22383_g;
	private float field_22382_h;
	private float field_22381_i;
	private float field_22380_j;
	private float field_22379_k;
	private float field_22378_l;
	private float field_22377_m;
	private float field_22376_n;
	private float field_22375_o;
	private float field_22374_p;
	private float field_22373_q;
	private float field_22372_r;
	private float field_22371_s;
	private float field_22370_t;
	private float field_22369_u;
	private float field_22368_v;
	private float field_22367_w;
	private float field_22366_x;
	private float field_22365_y;
	private float field_22364_z;
	private float field_22362_A;
	private float field_22360_B;
	private float field_22358_C;
	private float field_22356_D;
	private float field_22354_E;
	private float field_22353_F;
	private int field_22352_G;
	private float field_22351_H;
	private float field_22350_I;
	private float field_22349_J;
	private float field_22348_K;
	private float field_22347_L;
	private float field_22346_M;
	private float field_22345_N;
	private float field_22344_O;
	private float field_22343_P;
	private float field_22342_Q;
	private float field_22341_R;
	private float field_22340_S;
	private boolean field_22339_T;
	private boolean field_22338_U;
	private boolean field_22337_V;
	private boolean field_22336_W;
	private boolean field_22335_X;
	private boolean field_22334_Y;
	private boolean field_22333_Z;
	private boolean field_22363_aa;
	private boolean field_22361_ab;
	private boolean field_22359_ac;
	private boolean field_22357_ad;
	private boolean field_22355_ae;
}
