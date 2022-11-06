package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderCreeper extends RenderLiving {

	public RenderCreeper() {
		super(new ModelCreeper(), 0.5F);
	}

	protected void updateCreeperScale(EntityCreeper entitycreeper, float f) {
		EntityCreeper entitycreeper1 = entitycreeper;
		float f1 = entitycreeper1.func_440_b(f);
		float f2 = 1.0F + MathHelper.sin(f1 * 100F) * f1 * 0.01F;
		if (f1 < 0.0F) {
			f1 = 0.0F;
		}
		if (f1 > 1.0F) {
			f1 = 1.0F;
		}
		f1 *= f1;
		f1 *= f1;
		float f3 = (1.0F + f1 * 0.4F) * f2;
		float f4 = (1.0F + f1 * 0.1F) / f2;
		EaglerAdapter.glScalef(f3, f4, f3);
	}

	protected int updateCreeperColorMultiplier(EntityCreeper entitycreeper, float f, float f1) {
		EntityCreeper entitycreeper1 = entitycreeper;
		float f2 = entitycreeper1.func_440_b(f1);
		if ((int) (f2 * 10F) % 2 == 0) {
			return 0;
		}
		int i = (int) (f2 * 0.2F * 255F);
		if (i < 0) {
			i = 0;
		}
		if (i > 255) {
			i = 255;
		}
		char c = '\377';
		char c1 = '\377';
		char c2 = '\377';
		return i << 24 | c << 16 | c1 << 8 | c2;
	}

	protected void preRenderCallback(EntityLiving entityliving, float f) {
		updateCreeperScale((EntityCreeper) entityliving, f);
	}

	protected int getColorMultiplier(EntityLiving entityliving, float f, float f1) {
		return updateCreeperColorMultiplier((EntityCreeper) entityliving, f, f1);
	}
	
	private static final TextureLocation texture = new TextureLocation("/mob/creeper.png");

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		texture.bindTexture();
		return true;
	}
}
