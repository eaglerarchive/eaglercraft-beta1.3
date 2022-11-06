package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public abstract class RenderLiving extends Render {

	public RenderLiving(ModelBase modelbase, float f) {
		mainModel = modelbase;
		shadowSize = f;
	}

	public void setRenderPassModel(ModelBase modelbase) {
		renderPassModel = modelbase;
	}

	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glDisable(2884 /* GL_CULL_FACE */);
		mainModel.onGround = func_167_c(entityliving, f1);
		mainModel.isRiding = entityliving.func_21063_V();
		if (renderPassModel != null) {
			renderPassModel.isRiding = mainModel.isRiding;
		}
		try {
			float f2 = entityliving.prevRenderYawOffset
					+ (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * f1;
			float f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
			float f4 = entityliving.prevRotationPitch
					+ (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
			func_22012_b(entityliving, d, d1, d2);
			float f5 = func_170_d(entityliving, f1);
			func_21004_a(entityliving, f5, f2, f1);
			float f6 = 0.0625F;
			EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
			EaglerAdapter.glScalef(-1F, -1F, 1.0F);
			preRenderCallback(entityliving, f1);
			EaglerAdapter.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
			float f7 = entityliving.field_705_Q + (entityliving.field_704_R - entityliving.field_705_Q) * f1;
			float f8 = entityliving.field_703_S - entityliving.field_704_R * (1.0F - f1);
			if (f7 > 1.0F) {
				f7 = 1.0F;
			}
			loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getEntityTexture());
			EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
			mainModel.render(f8, f7, f5, f3 - f2, f4, f6);
			for (int i = 0; i < 4; i++) {
				if (shouldRenderPass(entityliving, i, f1)) {
					renderPassModel.render(f8, f7, f5, f3 - f2, f4, f6);
					EaglerAdapter.glDisable(3042 /* GL_BLEND */);
					EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
				}
			}

			renderEquippedItems(entityliving, f1);
			float f9 = entityliving.getEntityBrightness(f1);
			int j = getColorMultiplier(entityliving, f9, f1);
			if ((j >> 24 & 0xff) > 0 || entityliving.hurtTime > 0 || entityliving.deathTime > 0) {
				EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
				EaglerAdapter.glDisable(3008 /* GL_ALPHA_TEST */);
				EaglerAdapter.glEnable(3042 /* GL_BLEND */);
				EaglerAdapter.glBlendFunc(770, 771);
				EaglerAdapter.glDepthFunc(514);
				if (entityliving.hurtTime > 0 || entityliving.deathTime > 0) {
					EaglerAdapter.glColor4f(f9, 0.0F, 0.0F, 0.4F);
					mainModel.render(f8, f7, f5, f3 - f2, f4, f6);
					for (int k = 0; k < 4; k++) {
						if (shouldRenderPass(entityliving, k, f1)) {
							EaglerAdapter.glColor4f(f9, 0.0F, 0.0F, 0.4F);
							renderPassModel.render(f8, f7, f5, f3 - f2, f4, f6);
						}
					}

				}
				if ((j >> 24 & 0xff) > 0) {
					float f10 = (float) (j >> 16 & 0xff) / 255F;
					float f11 = (float) (j >> 8 & 0xff) / 255F;
					float f12 = (float) (j & 0xff) / 255F;
					float f13 = (float) (j >> 24 & 0xff) / 255F;
					EaglerAdapter.glColor4f(f10, f11, f12, f13);
					mainModel.render(f8, f7, f5, f3 - f2, f4, f6);
					for (int l = 0; l < 4; l++) {
						if (shouldRenderPass(entityliving, l, f1)) {
							EaglerAdapter.glColor4f(f10, f11, f12, f13);
							renderPassModel.render(f8, f7, f5, f3 - f2, f4, f6);
						}
					}

				}
				EaglerAdapter.glDepthFunc(515);
				EaglerAdapter.glDisable(3042 /* GL_BLEND */);
				EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
				EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
			}
			EaglerAdapter.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		EaglerAdapter.glEnable(2884 /* GL_CULL_FACE */);
		EaglerAdapter.glPopMatrix();
		func_22014_a(entityliving, d, d1, d2);
	}

	protected void func_22012_b(EntityLiving entityliving, double d, double d1, double d2) {
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
	}

	protected void func_21004_a(EntityLiving entityliving, float f, float f1, float f2) {
		EaglerAdapter.glRotatef(180F - f1, 0.0F, 1.0F, 0.0F);
		if (entityliving.deathTime > 0) {
			float f3 = ((((float) entityliving.deathTime + f2) - 1.0F) / 20F) * 1.6F;
			f3 = MathHelper.sqrt_float(f3);
			if (f3 > 1.0F) {
				f3 = 1.0F;
			}
			EaglerAdapter.glRotatef(f3 * func_172_a(entityliving), 0.0F, 0.0F, 1.0F);
		}
	}

	protected float func_167_c(EntityLiving entityliving, float f) {
		return entityliving.getSwingProgress(f);
	}

	protected float func_170_d(EntityLiving entityliving, float f) {
		return (float) entityliving.ticksExisted + f;
	}

	protected void renderEquippedItems(EntityLiving entityliving, float f) {
	}

	protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f) {
		return false;
	}

	protected float func_172_a(EntityLiving entityliving) {
		return 90F;
	}

	protected int getColorMultiplier(EntityLiving entityliving, float f, float f1) {
		return 0;
	}

	protected void preRenderCallback(EntityLiving entityliving, float f) {
	}

	protected void func_22014_a(EntityLiving entityliving, double d, double d1, double d2) {
		if (Minecraft.func_22007_w()) {
			func_22013_a(entityliving, Integer.toString(entityliving.entityId), d, d1, d2, 64);
		}
	}

	protected void func_22013_a(EntityLiving entityliving, String s, double d, double d1, double d2, int i) {
		float f = entityliving.getDistanceToEntity(renderManager.field_22188_h);
		if (f > (float) i) {
			return;
		}
		FontRenderer fontrenderer = getFontRendererFromRenderManager();
		float f1 = 1.6F;
		float f2 = 0.01666667F * f1;
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d + 0.0F, (float) d1 + 2.3F, (float) d2);
		EaglerAdapter.glNormal3f(0.0F, 1.0F, 0.0F);
		EaglerAdapter.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		EaglerAdapter.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		EaglerAdapter.glScalef(-f2, -f2, f2);
		EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
		EaglerAdapter.glDepthMask(false);
		EaglerAdapter.glDisable(2929 /* GL_DEPTH_TEST */);
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glBlendFunc(770, 771);
		Tessellator tessellator = Tessellator.instance;
		byte byte0 = 0;
		if (s.equals("deadmau5")) {
			byte0 = -10;
		}
		EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
		tessellator.startDrawingQuads();
		int j = fontrenderer.getStringWidth(s) / 2;
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tessellator.addVertex(-j - 1, -1 + byte0, 0.0D);
		tessellator.addVertex(-j - 1, 8 + byte0, 0.0D);
		tessellator.addVertex(j + 1, 8 + byte0, 0.0D);
		tessellator.addVertex(j + 1, -1 + byte0, 0.0D);
		tessellator.draw();
		EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
		EaglerAdapter.glEnable(2929 /* GL_DEPTH_TEST */);
		EaglerAdapter.glDepthMask(true);
		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
		EaglerAdapter.glEnable(2896 /* GL_LIGHTING */);
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EaglerAdapter.glPopMatrix();
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		doRenderLiving((EntityLiving) entity, d, d1, d2, f, f1);
	}

	protected ModelBase mainModel;
	protected ModelBase renderPassModel;
}
