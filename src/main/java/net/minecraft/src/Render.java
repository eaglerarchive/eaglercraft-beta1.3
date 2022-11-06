package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public abstract class Render {

	private static final TextureLocation terrainTexture = new TextureLocation("/terrain.png");
	private static final TextureLocation shadowTexture = new TextureLocation("%clamp%/misc/shadow.png");

	public Render() {
		field_195_d = new ModelBiped();
		field_203_d = new RenderBlocks();
		shadowSize = 0.0F;
		field_194_c = 1.0F;
	}

	public abstract void doRender(Entity entity, double d, double d1, double d2, float f, float f1);

	@Deprecated
	protected void loadTexture(String s) {
		RenderEngine renderengine = renderManager.renderEngine;
		renderengine.bindTexture(renderengine.getTexture(s));
	}
	
	protected abstract boolean loadDownloadableImageTexture(String s, String s1);

	/*
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		RenderEngine renderengine = renderManager.renderEngine;
		int i = renderengine.getTextureForDownloadableImage(s, s1);
		if (i >= 0) {
			renderengine.bindTexture(i);
			return true;
		} else {
			return false;
		}
	}
	 */

	private void renderEntityOnFire(Entity entity, double d, double d1, double d2, float f) {
		EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
		int i = Block.fire.blockIndexInTexture;
		int j = (i & 0xf) << 4;
		int k = i & 0xf0;
		float f1 = (float) j / 256F;
		float f2 = ((float) j + 15.99F) / 256F;
		float f3 = (float) k / 256F;
		float f4 = ((float) k + 15.99F) / 256F;
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		float f5 = entity.width * 1.4F;
		EaglerAdapter.glScalef(f5, f5, f5);
		terrainTexture.bindTexture();
		Tessellator tessellator = Tessellator.instance;
		float f6 = 1.0F;
		float f7 = 0.5F;
		float f8 = 0.0F;
		float f9 = entity.height / entity.width;
		EaglerAdapter.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		EaglerAdapter.glTranslatef(0.0F, 0.0F, -0.4F + (float) (int) f9 * 0.02F);
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		while (f9 > 0.0F) {
			tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f2, f4);
			tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f1, f4);
			tessellator.addVertexWithUV(0.0F - f7, 1.4F - f8, 0.0D, f1, f3);
			tessellator.addVertexWithUV(f6 - f7, 1.4F - f8, 0.0D, f2, f3);
			f9--;
			f8--;
			f6 *= 0.9F;
			EaglerAdapter.glTranslatef(0.0F, 0.0F, -0.04F);
		}
		tessellator.draw();
		EaglerAdapter.glPopMatrix();
		EaglerAdapter.glEnable(2896 /* GL_LIGHTING */);
	}

	private void renderShadow(Entity entity, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glBlendFunc(770, 771);
		RenderEngine renderengine = renderManager.renderEngine;
		shadowTexture.bindTexture();
		World world = getWorldFromRenderManager();
		EaglerAdapter.glDepthMask(false);
		float f2 = shadowSize;
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) f1;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) f1
				+ (double) entity.getShadowSize();
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) f1;
		int i = MathHelper.floor_double(d3 - (double) f2);
		int j = MathHelper.floor_double(d3 + (double) f2);
		int k = MathHelper.floor_double(d4 - (double) f2);
		int l = MathHelper.floor_double(d4);
		int i1 = MathHelper.floor_double(d5 - (double) f2);
		int j1 = MathHelper.floor_double(d5 + (double) f2);
		double d6 = d - d3;
		double d7 = d1 - d4;
		double d8 = d2 - d5;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		for (int k1 = i; k1 <= j; k1++) {
			for (int l1 = k; l1 <= l; l1++) {
				for (int i2 = i1; i2 <= j1; i2++) {
					int j2 = world.getBlockId(k1, l1 - 1, i2);
					if (j2 > 0 && world.getBlockLightValue(k1, l1, i2) > 3) {
						renderShadowOnBlock(Block.blocksList[j2], d, d1 + (double) entity.getShadowSize(), d2, k1, l1,
								i2, f, f2, d6, d7 + (double) entity.getShadowSize(), d8);
					}
				}

			}

		}

		tessellator.draw();
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
		EaglerAdapter.glDepthMask(true);
	}

	private World getWorldFromRenderManager() {
		return renderManager.worldObj;
	}

	private void renderShadowOnBlock(Block block, double d, double d1, double d2, int i, int j, int k, float f,
			float f1, double d3, double d4, double d5) {
		Tessellator tessellator = Tessellator.instance;
		if (!block.renderAsNormalBlock()) {
			return;
		}
		double d6 = ((double) f - (d1 - ((double) j + d4)) / 2D) * 0.5D
				* (double) getWorldFromRenderManager().getLightBrightness(i, j, k);
		if (d6 < 0.0D) {
			return;
		}
		if (d6 > 1.0D) {
			d6 = 1.0D;
		}
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float) d6);
		double d7 = (double) i + block.minX + d3;
		double d8 = (double) i + block.maxX + d3;
		double d9 = (double) j + block.minY + d4 + 0.015625D;
		double d10 = (double) k + block.minZ + d5;
		double d11 = (double) k + block.maxZ + d5;
		float f2 = (float) ((d - d7) / 2D / (double) f1 + 0.5D);
		float f3 = (float) ((d - d8) / 2D / (double) f1 + 0.5D);
		float f4 = (float) ((d2 - d10) / 2D / (double) f1 + 0.5D);
		float f5 = (float) ((d2 - d11) / 2D / (double) f1 + 0.5D);
		tessellator.addVertexWithUV(d7, d9, d10, f2, f4);
		tessellator.addVertexWithUV(d7, d9, d11, f2, f5);
		tessellator.addVertexWithUV(d8, d9, d11, f3, f5);
		tessellator.addVertexWithUV(d8, d9, d10, f3, f4);
	}

	public static void renderOffsetAABB(AxisAlignedBB axisalignedbb, double d, double d1, double d2) {
		EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
		Tessellator tessellator = Tessellator.instance;
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setTranslationD(d, d1, d2);
		tessellator.setNormal(0.0F, 0.0F, -1F);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.setNormal(0.0F, -1F, 0.0F);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.setTranslationD(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
	}

	public static void renderAABB(AxisAlignedBB axisalignedbb) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.draw();
	}

	public void setRenderManager(RenderManager rendermanager) {
		renderManager = rendermanager;
	}

	public void doRenderShadowAndFire(Entity entity, double d, double d1, double d2, float f, float f1) {
		if (renderManager.options.fancyGraphics && shadowSize > 0.0F) {
			double d3 = renderManager.func_851_a(entity.posX, entity.posY, entity.posZ);
			float f2 = (float) ((1.0D - d3 / 256D) * (double) field_194_c);
			if (f2 > 0.0F) {
				renderShadow(entity, d, d1, d2, f2, f1);
			}
		}
		if (entity.func_21062_U()) {
			renderEntityOnFire(entity, d, d1, d2, f1);
		}
	}

	public FontRenderer getFontRendererFromRenderManager() {
		return renderManager.getFontRenderer();
	}

	protected RenderManager renderManager;
	private ModelBase field_195_d;
	private RenderBlocks field_203_d;
	protected float shadowSize;
	protected float field_194_c;
}
