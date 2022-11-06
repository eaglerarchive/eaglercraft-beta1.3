package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class EntityChicken extends EntityAnimals {

	public EntityChicken(World world) {
		super(world);
		field_753_a = false;
		field_752_b = 0.0F;
		field_758_c = 0.0F;
		field_755_h = 1.0F;
		//texture = "/mob/chicken.png";
		setSize(0.3F, 0.4F);
		health = 4;
		timeUntilNextEgg = rand.nextInt(6000) + 6000;
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		field_756_e = field_752_b;
		field_757_d = field_758_c;
		field_758_c += (double) (onGround ? -1 : 4) * 0.29999999999999999D;
		if (field_758_c < 0.0F) {
			field_758_c = 0.0F;
		}
		if (field_758_c > 1.0F) {
			field_758_c = 1.0F;
		}
		if (!onGround && field_755_h < 1.0F) {
			field_755_h = 1.0F;
		}
		field_755_h *= 0.90000000000000002D;
		if (!onGround && motionY < 0.0D) {
			motionY *= 0.59999999999999998D;
		}
		field_752_b += field_755_h * 2.0F;
		if (!worldObj.multiplayerWorld && --timeUntilNextEgg <= 0) {
			worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F,
					(rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			dropItem(Item.egg.shiftedIndex, 1);
			timeUntilNextEgg = rand.nextInt(6000) + 6000;
		}
	}

	protected void fall(float f) {
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
	}

	protected String getLivingSound() {
		return "mob.chicken";
	}

	protected String getHurtSound() {
		return "mob.chickenhurt";
	}

	protected String getDeathSound() {
		return "mob.chickenhurt";
	}

	protected int getDropItemId() {
		return Item.feather.shiftedIndex;
	}

	public boolean field_753_a;
	public float field_752_b;
	public float field_758_c;
	public float field_757_d;
	public float field_756_e;
	public float field_755_h;
	public int timeUntilNextEgg;
}
