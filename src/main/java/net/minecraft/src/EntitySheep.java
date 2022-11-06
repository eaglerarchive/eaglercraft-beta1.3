package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntitySheep extends EntityAnimals {

	public EntitySheep(World world) {
		super(world);
		//texture = "/mob/sheep.png";
		setSize(0.9F, 1.3F);
	}

	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
	}

	public boolean attackEntityFrom(Entity entity, int i) {
		if (!worldObj.multiplayerWorld && !func_21072_p() && (entity instanceof EntityLiving)) {
			setSheared(true);
			int j = 1 + rand.nextInt(3);
			for (int k = 0; k < j; k++) {
				EntityItem entityitem = entityDropItem(new ItemStack(Block.cloth.blockID, 1, getFleeceColor()), 1.0F);
				entityitem.motionY += rand.nextFloat() * 0.05F;
				entityitem.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				entityitem.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
			}

		}
		return super.attackEntityFrom(entity, i);
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Sheared", func_21072_p());
		nbttagcompound.setByte("Color", (byte) getFleeceColor());
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		setSheared(nbttagcompound.getBoolean("Sheared"));
		setFleeceColor(nbttagcompound.getByte("Color"));
	}

	protected String getLivingSound() {
		return "mob.sheep";
	}

	protected String getHurtSound() {
		return "mob.sheep";
	}

	protected String getDeathSound() {
		return "mob.sheep";
	}

	public int getFleeceColor() {
		return dataWatcher.getWatchableObjectByte(16) & 0xf;
	}

	public void setFleeceColor(int i) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & 0xf0 | i & 0xf)));
	}

	public boolean func_21072_p() {
		return (dataWatcher.getWatchableObjectByte(16) & 0x10) != 0;
	}

	public void setSheared(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 | 0x10)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & 0xffffffef)));
		}
	}

	public static int func_21070_a(EaglercraftRandom random) {
		int i = random.nextInt(100);
		if (i < 5) {
			return 15;
		}
		if (i < 10) {
			return 7;
		}
		return i >= 15 ? 0 : 8;
	}

	public static final float field_21075_a[][] = { { 1.0F, 1.0F, 1.0F }, { 0.95F, 0.7F, 0.2F }, { 0.9F, 0.5F, 0.85F },
			{ 0.6F, 0.7F, 0.95F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.7F, 0.8F },
			{ 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.6F, 0.7F }, { 0.7F, 0.4F, 0.9F },
			{ 0.2F, 0.4F, 0.8F }, { 0.5F, 0.4F, 0.3F }, { 0.4F, 0.5F, 0.2F }, { 0.8F, 0.3F, 0.3F },
			{ 0.1F, 0.1F, 0.1F } };

}
