package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderSheep extends RenderLiving {
	
	private static final TextureLocation sheepTexture = new TextureLocation("/mob/sheep.png");
	private static final TextureLocation sheepFurTexture = new TextureLocation("/mob/sheep_fur.png");

	public RenderSheep(ModelBase modelbase, ModelBase modelbase1, float f) {
		super(modelbase, f);
		setRenderPassModel(modelbase1);
	}

	protected boolean func_176_a(EntitySheep entitysheep, int i, float f) {
		if (i == 0 && !entitysheep.func_21072_p()) {
			sheepFurTexture.bindTexture();
			float f1 = entitysheep.getEntityBrightness(f);
			int j = entitysheep.getFleeceColor();
			EaglerAdapter.glColor3f(f1 * EntitySheep.field_21075_a[j][0], f1 * EntitySheep.field_21075_a[j][1],
					f1 * EntitySheep.field_21075_a[j][2]);
			return true;
		} else {
			return false;
		}
	}

	protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f) {
		return func_176_a((EntitySheep) entityliving, i, f);
	}

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		sheepTexture.bindTexture();
		return true;
	}
}
