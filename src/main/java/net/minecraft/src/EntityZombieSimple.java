package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityZombieSimple extends EntityMobs {

	public EntityZombieSimple(World world) {
		super(world);
		//texture = "/mob/zombie.png";
		moveSpeed = 0.5F;
		attackStrength = 50;
		health *= 10;
		yOffset *= 6F;
		setSize(width * 6F, height * 6F);
	}

	protected float getBlockPathWeight(int i, int j, int k) {
		return worldObj.getLightBrightness(i, j, k) - 0.5F;
	}
}
