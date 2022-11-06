package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderEntity extends Render {

	public RenderEntity() {
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		renderOffsetAABB(entity.boundingBox, d - entity.lastTickPosX, d1 - entity.lastTickPosY,
				d2 - entity.lastTickPosZ);
		EaglerAdapter.glPopMatrix();
	}

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		return true;
	}
}
