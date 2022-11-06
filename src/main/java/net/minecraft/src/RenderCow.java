package net.minecraft.src;

import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class RenderCow extends RenderLiving {

	public RenderCow(ModelBase modelbase, float f) {
		super(modelbase, f);
	}

	public void func_177_a(EntityCow entitycow, double d, double d1, double d2, float f, float f1) {
		super.doRenderLiving(entitycow, d, d1, d2, f, f1);
	}

	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		func_177_a((EntityCow) entityliving, d, d1, d2, f, f1);
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_177_a((EntityCow) entity, d, d1, d2, f, f1);
	}
	
	private static final TextureLocation texture = new TextureLocation("/mob/cow.png");

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		texture.bindTexture();
		return true;
	}
}
