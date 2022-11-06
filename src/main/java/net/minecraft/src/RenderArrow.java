package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderArrow extends Render {
	
	private static final TextureLocation arrowsTexture = new TextureLocation("/item/arrows.png");

	public RenderArrow() {
	}

	public void func_154_a(EntityArrow entityarrow, double d, double d1, double d2, float f, float f1) {
		arrowsTexture.bindTexture();
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		EaglerAdapter.glRotatef(
				(entityarrow.prevRotationYaw + (entityarrow.rotationYaw - entityarrow.prevRotationYaw) * f1) - 90F,
				0.0F, 1.0F, 0.0F);
		EaglerAdapter.glRotatef(entityarrow.prevRotationPitch + (entityarrow.rotationPitch - entityarrow.prevRotationPitch) * f1,
				0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		int i = 0;
		float f2 = 0.0F;
		float f3 = 0.5F;
		float f4 = (float) (0 + i * 10) / 32F;
		float f5 = (float) (5 + i * 10) / 32F;
		float f6 = 0.0F;
		float f7 = 0.15625F;
		float f8 = (float) (5 + i * 10) / 32F;
		float f9 = (float) (10 + i * 10) / 32F;
		float f10 = 0.05625F;
		EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
		float f11 = (float) entityarrow.arrowShake - f1;
		if (f11 > 0.0F) {
			float f12 = -MathHelper.sin(f11 * 3F) * f11;
			EaglerAdapter.glRotatef(f12, 0.0F, 0.0F, 1.0F);
		}
		EaglerAdapter.glRotatef(45F, 1.0F, 0.0F, 0.0F);
		EaglerAdapter.glScalef(f10, f10, f10);
		EaglerAdapter.glTranslatef(-4F, 0.0F, 0.0F);
		EaglerAdapter.glNormal3f(f10, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7D, -2D, -2D, f6, f8);
		tessellator.addVertexWithUV(-7D, -2D, 2D, f7, f8);
		tessellator.addVertexWithUV(-7D, 2D, 2D, f7, f9);
		tessellator.addVertexWithUV(-7D, 2D, -2D, f6, f9);
		tessellator.draw();
		EaglerAdapter.glNormal3f(-f10, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7D, 2D, -2D, f6, f8);
		tessellator.addVertexWithUV(-7D, 2D, 2D, f7, f8);
		tessellator.addVertexWithUV(-7D, -2D, 2D, f7, f9);
		tessellator.addVertexWithUV(-7D, -2D, -2D, f6, f9);
		tessellator.draw();
		for (int j = 0; j < 4; j++) {
			EaglerAdapter.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			EaglerAdapter.glNormal3f(0.0F, 0.0F, f10);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-8D, -2D, 0.0D, f2, f4);
			tessellator.addVertexWithUV(8D, -2D, 0.0D, f3, f4);
			tessellator.addVertexWithUV(8D, 2D, 0.0D, f3, f5);
			tessellator.addVertexWithUV(-8D, 2D, 0.0D, f2, f5);
			tessellator.draw();
		}

		EaglerAdapter.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		EaglerAdapter.glPopMatrix();
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_154_a((EntityArrow) entity, d, d1, d2, f, f1);
	}

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		return true;
	}
}
