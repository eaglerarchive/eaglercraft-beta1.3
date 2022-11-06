package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class EntityMobs extends EntityCreature implements IMobs {

	public EntityMobs(World world) {
		super(world);
		attackStrength = 2;
		health = 20;
	}

	public void onLivingUpdate() {
		float f = getEntityBrightness(1.0F);
		if (f > 0.5F) {
			field_9344_ag += 2;
		}
		super.onLivingUpdate();
	}

	public void onUpdate() {
		super.onUpdate();
		if (worldObj.difficultySetting == 0) {
			setEntityDead();
		}
	}

	protected Entity findPlayerToAttack() {
		EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 16D);
		if (entityplayer != null && canEntityBeSeen(entityplayer)) {
			return entityplayer;
		} else {
			return null;
		}
	}

	public boolean attackEntityFrom(Entity entity, int i) {
		if (super.attackEntityFrom(entity, i)) {
			if (riddenByEntity == entity || ridingEntity == entity) {
				return true;
			}
			if (entity != this) {
				playerToAttack = entity;
			}
			return true;
		} else {
			return false;
		}
	}

	protected void attackEntity(Entity entity, float f) {
		if ((double) f < 1.5D && entity.boundingBox.maxY > boundingBox.minY
				&& entity.boundingBox.minY < boundingBox.maxY) {
			attackTime = 20;
			entity.attackEntityFrom(this, attackStrength);
		}
	}

	protected float getBlockPathWeight(int i, int j, int k) {
		return 0.5F - worldObj.getLightBrightness(i, j, k);
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
	}

	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		if (worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > rand.nextInt(32)) {
			return false;
		} else {
			int l = worldObj.getBlockLightValue(i, j, k);
			return l <= rand.nextInt(8) && super.getCanSpawnHere();
		}
	}

	protected int attackStrength;
}
