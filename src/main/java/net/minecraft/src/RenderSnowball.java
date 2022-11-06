package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderSnowball extends Render {
	
	private static final TextureLocation itemTexture = new TextureLocation("/gui/items.png");

	public RenderSnowball(int i) {
		field_20003_a = i;
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
		EaglerAdapter.glScalef(0.5F, 0.5F, 0.5F);
		itemTexture.bindTexture();
		Tessellator tessellator = Tessellator.instance;
		float f2 = (float) ((field_20003_a % 16) * 16 + 0) / 256F;
		float f3 = (float) ((field_20003_a % 16) * 16 + 16) / 256F;
		float f4 = (float) ((field_20003_a / 16) * 16 + 0) / 256F;
		float f5 = (float) ((field_20003_a / 16) * 16 + 16) / 256F;
		float f6 = 1.0F;
		float f7 = 0.5F;
		float f8 = 0.25F;
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
	}

	private int field_20003_a;

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		return true;
	}
}
