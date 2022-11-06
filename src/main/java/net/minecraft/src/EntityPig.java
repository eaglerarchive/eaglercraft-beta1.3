package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityPig extends EntityAnimals {

	public EntityPig(World world) {
		super(world);
		//texture = "/mob/pig.png";
		setSize(0.9F, 0.9F);
	}

	protected void entityInit() {
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Saddle", func_21068_q());
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		func_21069_a(nbttagcompound.getBoolean("Saddle"));
	}

	protected String getLivingSound() {
		return "mob.pig";
	}

	protected String getHurtSound() {
		return "mob.pig";
	}

	protected String getDeathSound() {
		return "mob.pigdeath";
	}

	public boolean interact(EntityPlayer entityplayer) {
		if (func_21068_q() && !worldObj.multiplayerWorld
				&& (riddenByEntity == null || riddenByEntity == entityplayer)) {
			entityplayer.mountEntity(this);
			return true;
		} else {
			return false;
		}
	}

	protected int getDropItemId() {
		return Item.porkRaw.shiftedIndex;
	}

	public boolean func_21068_q() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void func_21069_a(boolean flag) {
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) 1));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) 0));
		}
	}
}
