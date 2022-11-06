package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderSpider extends RenderLiving {
	
	private static final TextureLocation spiderTexture = new TextureLocation("/mob/spider.png");
	private static final TextureLocation spiderEyesTexture = new TextureLocation("/mob/spider_eyes.png");

	public RenderSpider() {
		super(new ModelSpider(), 1.0F);
		setRenderPassModel(new ModelSpider());
	}

	protected float func_191_a(EntitySpider entityspider) {
		return 180F;
	}

	protected boolean func_190_a(EntitySpider entityspider, int i, float f) {
		if (i != 0) {
			return false;
		}
		if (i != 0) {
			return false;
		} else {
			spiderEyesTexture.bindTexture();
			float f1 = (1.0F - entityspider.getEntityBrightness(1.0F)) * 0.5F;
			EaglerAdapter.glEnable(3042 /* GL_BLEND */);
			EaglerAdapter.glDisable(3008 /* GL_ALPHA_TEST */);
			EaglerAdapter.glBlendFunc(770, 771);
			EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, f1);
			return true;
		}
	}

	protected float func_172_a(EntityLiving entityliving) {
		return func_191_a((EntitySpider) entityliving);
	}

	protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f) {
		return func_190_a((EntitySpider) entityliving, i, f);
	}

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		spiderTexture.bindTexture();
		return true;
	}
}
