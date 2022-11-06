package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderFish extends Render {
	
	private static final TextureLocation particlesTexture = new TextureLocation("/particles.png");

	public RenderFish() {
	}

	public void func_4011_a(EntityFish entityfish, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
		EaglerAdapter.glScalef(0.5F, 0.5F, 0.5F);
		int i = 1;
		byte byte0 = 2;
		particlesTexture.bindTexture();
		Tessellator tessellator = Tessellator.instance;
		float f2 = (float) (i * 8 + 0) / 128F;
		float f3 = (float) (i * 8 + 8) / 128F;
		float f4 = (float) (byte0 * 8 + 0) / 128F;
		float f5 = (float) (byte0 * 8 + 8) / 128F;
		float f6 = 1.0F;
		float f7 = 0.5F;
		float f8 = 0.5F;
		EaglerAdapter.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		EaglerAdapter.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
		tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
		tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
		tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
		tessellator.draw();
		EaglerAdapter.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		EaglerAdapter.glPopMatrix();
		if (entityfish.angler != null) {
			float f9 = ((entityfish.angler.prevRotationYaw
					+ (entityfish.angler.rotationYaw - entityfish.angler.prevRotationYaw) * f1) * 3.141593F) / 180F;
			float f11 = ((entityfish.angler.prevRotationPitch
					+ (entityfish.angler.rotationPitch - entityfish.angler.prevRotationPitch) * f1) * 3.141593F) / 180F;
			double d3 = MathHelper.sin(f9);
			double d5 = MathHelper.cos(f9);
			double d7 = MathHelper.sin(f11);
			double d8 = MathHelper.cos(f11);
			double d9 = (entityfish.angler.prevPosX
					+ (entityfish.angler.posX - entityfish.angler.prevPosX) * (double) f1) - d5 * 0.69999999999999996D
					- d3 * 0.5D * d8;
			double d10 = (entityfish.angler.prevPosY
					+ (entityfish.angler.posY - entityfish.angler.prevPosY) * (double) f1) - d7 * 0.5D;
			double d11 = ((entityfish.angler.prevPosZ
					+ (entityfish.angler.posZ - entityfish.angler.prevPosZ) * (double) f1) - d3 * 0.69999999999999996D)
					+ d5 * 0.5D * d8;
			if (renderManager.options.thirdPersonView) {
				float f10 = ((entityfish.angler.prevRenderYawOffset
						+ (entityfish.angler.renderYawOffset - entityfish.angler.prevRenderYawOffset) * f1) * 3.141593F)
						/ 180F;
				double d4 = MathHelper.sin(f10);
				double d6 = MathHelper.cos(f10);
				d9 = (entityfish.angler.prevPosX + (entityfish.angler.posX - entityfish.angler.prevPosX) * (double) f1)
						- d6 * 0.34999999999999998D - d4 * 0.84999999999999998D;
				d10 = (entityfish.angler.prevPosY + (entityfish.angler.posY - entityfish.angler.prevPosY) * (double) f1)
						- 0.45000000000000001D;
				d11 = ((entityfish.angler.prevPosZ
						+ (entityfish.angler.posZ - entityfish.angler.prevPosZ) * (double) f1)
						- d4 * 0.34999999999999998D) + d6 * 0.84999999999999998D;
			}
			double d12 = entityfish.prevPosX + (entityfish.posX - entityfish.prevPosX) * (double) f1;
			double d13 = entityfish.prevPosY + (entityfish.posY - entityfish.prevPosY) * (double) f1 + 0.25D;
			double d14 = entityfish.prevPosZ + (entityfish.posZ - entityfish.prevPosZ) * (double) f1;
			double d15 = (float) (d9 - d12);
			double d16 = (float) (d10 - d13);
			double d17 = (float) (d11 - d14);
			EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
			EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
			tessellator.startDrawing(3);
			tessellator.setColorOpaque_I(0);
			int j = 16;
			for (int k = 0; k <= j; k++) {
				float f12 = (float) k / (float) j;
				tessellator.addVertex(d + d15 * (double) f12, d1 + d16 * (double) (f12 * f12 + f12) * 0.5D + 0.25D,
						d2 + d17 * (double) f12);
			}

			tessellator.draw();
			EaglerAdapter.glEnable(2896 /* GL_LIGHTING */);
			EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		}
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_4011_a((EntityFish) entity, d, d1, d2, f, f1);
	}

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		return true;
	}
}
