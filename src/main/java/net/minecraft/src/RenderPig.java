package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

import net.lax1dude.eaglercraft.TextureLocation;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class RenderPig extends RenderLiving {

	private static final TextureLocation saddleTexture = new TextureLocation("/mob/saddle.png");
	private static final TextureLocation pigTexture = new TextureLocation("/mob/pig.png");

	public RenderPig(ModelBase modelbase, ModelBase modelbase1, float f) {
		super(modelbase, f);
		setRenderPassModel(modelbase1);
	}

	protected boolean renderSaddledPig(EntityPig entitypig, int i, float f) {
		saddleTexture.bindTexture();
		return i == 0 && entitypig.func_21068_q();
	}

	protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f) {
		return renderSaddledPig((EntityPig) entityliving, i, f);
	}

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		pigTexture.bindTexture();
		return true;
	}
}
