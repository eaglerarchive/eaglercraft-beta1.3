package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderTNTPrimed extends Render {
	
	private static final TextureLocation terrainTexture = new TextureLocation("/terrain.png");

	public RenderTNTPrimed() {
		field_196_d = new RenderBlocks();
		shadowSize = 0.5F;
	}

	public void func_153_a(EntityTNTPrimed entitytntprimed, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		if (((float) entitytntprimed.fuse - f1) + 1.0F < 10F) {
			float f2 = 1.0F - (((float) entitytntprimed.fuse - f1) + 1.0F) / 10F;
			if (f2 < 0.0F) {
				f2 = 0.0F;
			}
			if (f2 > 1.0F) {
				f2 = 1.0F;
			}
			f2 *= f2;
			f2 *= f2;
			float f4 = 1.0F + f2 * 0.3F;
			EaglerAdapter.glScalef(f4, f4, f4);
		}
		float f3 = (1.0F - (((float) entitytntprimed.fuse - f1) + 1.0F) / 100F) * 0.8F;
		terrainTexture.bindTexture();
		field_196_d.func_1227_a(Block.tnt, 0);
		if ((entitytntprimed.fuse / 5) % 2 == 0) {
			EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
			EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
			EaglerAdapter.glEnable(3042 /* GL_BLEND */);
			EaglerAdapter.glBlendFunc(770, 772);
			EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, f3);
			field_196_d.func_1227_a(Block.tnt, 0);
			EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			EaglerAdapter.glDisable(3042 /* GL_BLEND */);
			EaglerAdapter.glEnable(2896 /* GL_LIGHTING */);
			EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		}
		EaglerAdapter.glPopMatrix();
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_153_a((EntityTNTPrimed) entity, d, d1, d2, f, f1);
	}

	private RenderBlocks field_196_d;

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		return true;
	}
}
