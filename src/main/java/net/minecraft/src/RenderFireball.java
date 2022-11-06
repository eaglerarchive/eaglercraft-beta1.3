package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderFireball extends Render {
	
	private static final TextureLocation itemsTexture = new TextureLocation("/gui/items.png");

	public RenderFireball() {
	}

	public void func_4012_a(EntityFireball entityfireball, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
		float f2 = 2.0F;
		EaglerAdapter.glScalef(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
		int i = Item.snowball.getIconIndex(null);
		itemsTexture.bindTexture();
		Tessellator tessellator = Tessellator.instance;
		float f3 = (float) ((i % 16) * 16 + 0) / 256F;
		float f4 = (float) ((i % 16) * 16 + 16) / 256F;
		float f5 = (float) ((i / 16) * 16 + 0) / 256F;
		float f6 = (float) ((i / 16) * 16 + 16) / 256F;
		float f7 = 1.0F;
		float f8 = 0.5F;
		float f9 = 0.25F;
		EaglerAdapter.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		EaglerAdapter.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
		tessellator.addVertexWithUV(f7 - f8, 0.0F - f9, 0.0D, f4, f6);
		tessellator.addVertexWithUV(f7 - f8, 1.0F - f9, 0.0D, f4, f5);
		tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
		tessellator.draw();
		EaglerAdapter.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		EaglerAdapter.glPopMatrix();
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_4012_a((EntityFireball) entity, d, d1, d2, f, f1);
	}

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		return true;
	}
}
