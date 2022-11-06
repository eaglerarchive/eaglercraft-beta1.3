package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

import net.lax1dude.eaglercraft.EaglerAdapter;

public class TileEntityRenderer {

	private TileEntityRenderer() {
		specialRendererMap = new HashMap();
		specialRendererMap.put(TileEntitySign.class, new TileEntitySignRenderer());
		specialRendererMap.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
		TileEntitySpecialRenderer tileentityspecialrenderer;
		for (Iterator iterator = specialRendererMap.values().iterator(); iterator.hasNext(); tileentityspecialrenderer
				.setTileEntityRenderer(this)) {
			tileentityspecialrenderer = (TileEntitySpecialRenderer) iterator.next();
		}

	}

	public TileEntitySpecialRenderer getSpecialRendererForClass(Class class1) {
		TileEntitySpecialRenderer tileentityspecialrenderer = (TileEntitySpecialRenderer) specialRendererMap
				.get(class1);
		if (tileentityspecialrenderer == null && class1 != (TileEntity.class)) {
			tileentityspecialrenderer = getSpecialRendererForClass(class1.getSuperclass());
			specialRendererMap.put(class1, tileentityspecialrenderer);
		}
		return tileentityspecialrenderer;
	}

	public boolean hasSpecialRenderer(TileEntity tileentity) {
		return getSpecialRendererForEntity(tileentity) != null;
	}

	public TileEntitySpecialRenderer getSpecialRendererForEntity(TileEntity tileentity) {
		if (tileentity == null) {
			return null;
		} else {
			return getSpecialRendererForClass(tileentity.getClass());
		}
	}

	public void func_22267_a(World world, RenderEngine renderengine, FontRenderer fontrenderer,
			EntityLiving entityliving, float f) {
		worldObj = world;
		renderEngine = renderengine;
		field_22270_g = entityliving;
		fontRenderer = fontrenderer;
		field_22269_h = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f;
		field_22268_i = entityliving.prevRotationPitch
				+ (entityliving.rotationPitch - entityliving.prevRotationPitch) * f;
		playerX = entityliving.lastTickPosX + (entityliving.posX - entityliving.lastTickPosX) * (double) f;
		playerY = entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * (double) f;
		playerZ = entityliving.lastTickPosZ + (entityliving.posZ - entityliving.lastTickPosZ) * (double) f;
	}

	public void renderTileEntity(TileEntity tileentity, float f) {
		if (tileentity.getDistanceFrom(playerX, playerY, playerZ) < 4096D) {
			float f1 = worldObj.getLightBrightness(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
			EaglerAdapter.glColor3f(f1, f1, f1);
			renderTileEntityAt(tileentity, (double) tileentity.xCoord - staticPlayerX,
					(double) tileentity.yCoord - staticPlayerY, (double) tileentity.zCoord - staticPlayerZ, f);
		}
	}

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		TileEntitySpecialRenderer tileentityspecialrenderer = getSpecialRendererForEntity(tileentity);
		if (tileentityspecialrenderer != null) {
			tileentityspecialrenderer.renderTileEntityAt(tileentity, d, d1, d2, f);
		}
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}

	private Map specialRendererMap;
	public static TileEntityRenderer instance = new TileEntityRenderer();
	private FontRenderer fontRenderer;
	public static double staticPlayerX;
	public static double staticPlayerY;
	public static double staticPlayerZ;
	public RenderEngine renderEngine;
	public World worldObj;
	public EntityLiving field_22270_g;
	public float field_22269_h;
	public float field_22268_i;
	public double playerX;
	public double playerY;
	public double playerZ;

}
