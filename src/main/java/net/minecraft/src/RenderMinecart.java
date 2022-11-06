package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderMinecart extends Render {
	
	private static final TextureLocation terrainTexture = new TextureLocation("/terrain.png");
	private static final TextureLocation minecartTexture = new TextureLocation("/item/cart.png");

	public RenderMinecart() {
		shadowSize = 0.5F;
		modelMinecart = new ModelMinecart();
	}

	public void func_152_a(EntityMinecart entityminecart, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		double d3 = entityminecart.lastTickPosX + (entityminecart.posX - entityminecart.lastTickPosX) * (double) f1;
		double d4 = entityminecart.lastTickPosY + (entityminecart.posY - entityminecart.lastTickPosY) * (double) f1;
		double d5 = entityminecart.lastTickPosZ + (entityminecart.posZ - entityminecart.lastTickPosZ) * (double) f1;
		double d6 = 0.30000001192092896D;
		Vec3D vec3d = entityminecart.func_514_g(d3, d4, d5);
		float f2 = entityminecart.prevRotationPitch
				+ (entityminecart.rotationPitch - entityminecart.prevRotationPitch) * f1;
		if (vec3d != null) {
			Vec3D vec3d1 = entityminecart.func_515_a(d3, d4, d5, d6);
			Vec3D vec3d2 = entityminecart.func_515_a(d3, d4, d5, -d6);
			if (vec3d1 == null) {
				vec3d1 = vec3d;
			}
			if (vec3d2 == null) {
				vec3d2 = vec3d;
			}
			d += vec3d.xCoord - d3;
			d1 += (vec3d1.yCoord + vec3d2.yCoord) / 2D - d4;
			d2 += vec3d.zCoord - d5;
			Vec3D vec3d3 = vec3d2.addVector(-vec3d1.xCoord, -vec3d1.yCoord, -vec3d1.zCoord);
			if (vec3d3.lengthVector() != 0.0D) {
				vec3d3 = vec3d3.normalize();
				f = (float) ((Math.atan2(vec3d3.zCoord, vec3d3.xCoord) * 180D) / 3.1415926535897931D);
				f2 = (float) (Math.atan(vec3d3.yCoord) * 73D);
			}
		}
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		EaglerAdapter.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
		EaglerAdapter.glRotatef(-f2, 0.0F, 0.0F, 1.0F);
		float f3 = (float) entityminecart.field_20911_b - f1;
		float f4 = (float) entityminecart.field_20910_a - f1;
		if (f4 < 0.0F) {
			f4 = 0.0F;
		}
		if (f3 > 0.0F) {
			EaglerAdapter.glRotatef(((MathHelper.sin(f3) * f3 * f4) / 10F) * (float) entityminecart.field_20912_c, 1.0F, 0.0F,
					0.0F);
		}
		if (entityminecart.minecartType != 0) {
			terrainTexture.bindTexture();
			float f5 = 0.75F;
			EaglerAdapter.glScalef(f5, f5, f5);
			EaglerAdapter.glTranslatef(0.0F, 0.3125F, 0.0F);
			EaglerAdapter.glRotatef(90F, 0.0F, 1.0F, 0.0F);
			if (entityminecart.minecartType == 1) {
				(new RenderBlocks()).func_1227_a(Block.crate, 0);
			} else if (entityminecart.minecartType == 2) {
				(new RenderBlocks()).func_1227_a(Block.stoneOvenIdle, 0);
			}
			EaglerAdapter.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glTranslatef(0.0F, -0.3125F, 0.0F);
			EaglerAdapter.glScalef(1.0F / f5, 1.0F / f5, 1.0F / f5);
		}
		minecartTexture.bindTexture();
		EaglerAdapter.glScalef(-1F, -1F, 1.0F);
		modelMinecart.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		EaglerAdapter.glPopMatrix();
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_152_a((EntityMinecart) entity, d, d1, d2, f, f1);
	}

	protected ModelBase modelMinecart;

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		return true;
	}
}
