package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class RenderSlime extends RenderLiving {
	
	private static final TextureLocation slimeTexture = new TextureLocation("/mob/slime.png");

	public RenderSlime(ModelBase modelbase, ModelBase modelbase1, float f) {
		super(modelbase, f);
		scaleAmount = modelbase1;
	}

	protected boolean func_179_a(EntitySlime entityslime, int i, float f) {
		if (i == 0) {
			setRenderPassModel(scaleAmount);
			EaglerAdapter.glEnable(2977 /* GL_NORMALIZE */);
			EaglerAdapter.glEnable(3042 /* GL_BLEND */);
			EaglerAdapter.glBlendFunc(770, 771);
			return true;
		}
		if (i == 1) {
			EaglerAdapter.glDisable(3042 /* GL_BLEND */);
			EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		return false;
	}

	protected void func_178_a(EntitySlime entityslime, float f) {
		float f1 = (entityslime.field_767_b + (entityslime.field_768_a - entityslime.field_767_b) * f)
				/ ((float) entityslime.slimeSize * 0.5F + 1.0F);
		float f2 = 1.0F / (f1 + 1.0F);
		float f3 = entityslime.slimeSize;
		EaglerAdapter.glScalef(f2 * f3, (1.0F / f2) * f3, f2 * f3);
	}

	protected void preRenderCallback(EntityLiving entityliving, float f) {
		func_178_a((EntitySlime) entityliving, f);
	}

	protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f) {
		return func_179_a((EntitySlime) entityliving, i, f);
	}

	private ModelBase scaleAmount;

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		slimeTexture.bindTexture();
		return true;
	}
}
