package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class EntitySlime extends EntityLiving implements IMobs {

	public EntitySlime(World world) {
		super(world);
		field_769_d = 0;
		slimeSize = 1;
		//texture = "/mob/slime.png";
		slimeSize = 1 << rand.nextInt(3);
		yOffset = 0.0F;
		field_769_d = rand.nextInt(20) + 10;
		setSlimeSize(slimeSize);
	}

	public void setSlimeSize(int i) {
		slimeSize = i;
		setSize(0.6F * (float) i, 0.6F * (float) i);
		health = i * i;
		setPosition(posX, posY, posZ);
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setInteger("Size", slimeSize - 1);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		slimeSize = nbttagcompound.getInteger("Size") + 1;
	}

	public void onUpdate() {
		field_767_b = field_768_a;
		boolean flag = onGround;
		super.onUpdate();
		if (onGround && !flag) {
			for (int i = 0; i < slimeSize * 8; i++) {
				float f = rand.nextFloat() * 3.141593F * 2.0F;
				float f1 = rand.nextFloat() * 0.5F + 0.5F;
				float f2 = MathHelper.sin(f) * (float) slimeSize * 0.5F * f1;
				float f3 = MathHelper.cos(f) * (float) slimeSize * 0.5F * f1;
				worldObj.spawnParticle("slime", posX + (double) f2, boundingBox.minY, posZ + (double) f3, 0.0D, 0.0D,
						0.0D);
			}

			if (slimeSize > 2) {
				worldObj.playSoundAtEntity(this, "mob.slime", getSoundVolume(),
						((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			}
			field_768_a = -0.5F;
		}
		field_768_a = field_768_a * 0.6F;
	}

	protected void updatePlayerActionState() {
		EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 16D);
		if (entityplayer != null) {
			faceEntity(entityplayer, 10F);
		}
		if (onGround && field_769_d-- <= 0) {
			field_769_d = rand.nextInt(20) + 10;
			if (entityplayer != null) {
				field_769_d /= 3;
			}
			isJumping = true;
			if (slimeSize > 1) {
				worldObj.playSoundAtEntity(this, "mob.slime", getSoundVolume(),
						((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}
			field_768_a = 1.0F;
			moveStrafing = 1.0F - rand.nextFloat() * 2.0F;
			moveForward = 1 * slimeSize;
		} else {
			isJumping = false;
			if (onGround) {
				moveStrafing = moveForward = 0.0F;
			}
		}
	}

	public void setEntityDead() {
		if (slimeSize > 1 && health == 0) {
			for (int i = 0; i < 4; i++) {
				float f = (((float) (i % 2) - 0.5F) * (float) slimeSize) / 4F;
				float f1 = (((float) (i / 2) - 0.5F) * (float) slimeSize) / 4F;
				EntitySlime entityslime = new EntitySlime(worldObj);
				entityslime.setSlimeSize(slimeSize / 2);
				entityslime.setLocationAndAngles(posX + (double) f, posY + 0.5D, posZ + (double) f1,
						rand.nextFloat() * 360F, 0.0F);
				worldObj.entityJoinedWorld(entityslime);
			}

		}
		super.setEntityDead();
	}

	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		if (slimeSize > 1 && canEntityBeSeen(entityplayer)
				&& (double) getDistanceToEntity(entityplayer) < 0.59999999999999998D * (double) slimeSize
				&& entityplayer.attackEntityFrom(this, slimeSize)) {
			worldObj.playSoundAtEntity(this, "mob.slimeattack", 1.0F,
					(rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
		}
	}

	protected String getHurtSound() {
		return "mob.slime";
	}

	protected String getDeathSound() {
		return "mob.slime";
	}

	protected int getDropItemId() {
		if (slimeSize == 1) {
			return Item.slimeBall.shiftedIndex;
		} else {
			return 0;
		}
	}

	public boolean getCanSpawnHere() {
		Chunk chunk = worldObj.getChunkFromBlockCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
		return (slimeSize == 1 || worldObj.difficultySetting > 0) && rand.nextInt(10) == 0
				&& chunk.func_997_a(0x3ad8025fL).nextInt(10) == 0 && posY < 16D;
	}

	protected float getSoundVolume() {
		return 0.6F;
	}

	public float field_768_a;
	public float field_767_b;
	private int field_769_d;
	public int slimeSize;
}
