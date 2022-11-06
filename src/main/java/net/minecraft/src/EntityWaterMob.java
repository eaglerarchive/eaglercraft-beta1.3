package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityWaterMob extends EntityCreature {

	public EntityWaterMob(World world) {
		super(world);
	}

	public boolean canBreatheUnderwater() {
		return true;
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
	}

	public boolean getCanSpawnHere() {
		return worldObj.checkIfAABBIsClear(boundingBox);
	}

	public int func_421_b() {
		return 120;
	}
}
