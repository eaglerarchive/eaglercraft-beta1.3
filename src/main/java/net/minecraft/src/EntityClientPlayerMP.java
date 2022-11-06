package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP {

	public EntityClientPlayerMP(Minecraft minecraft, World world, Session session, NetClientHandler netclienthandler) {
		super(minecraft, world, session, 0);
		field_9380_bx = 0;
		field_21093_bH = false;
		field_9382_bF = false;
		field_9381_bG = false;
		field_12242_bI = 0;
		sendQueue = netclienthandler;
	}

	public boolean attackEntityFrom(Entity entity, int i) {
		return false;
	}

	public void heal(int i) {
	}

	public void onUpdate() {
		if (!worldObj.blockExists(MathHelper.floor_double(posX), 64, MathHelper.floor_double(posZ))) {
			return;
		} else {
			super.onUpdate();
			func_4056_N();
			return;
		}
	}

	public void func_4056_N() {
		if (field_9380_bx++ == 20) {
			sendInventoryChanged();
			field_9380_bx = 0;
		}
		boolean flag = isSneaking();
		if (flag != field_9381_bG) {
			if (flag) {
				sendQueue.addToSendQueue(new Packet19(this, 1));
			} else {
				sendQueue.addToSendQueue(new Packet19(this, 2));
			}
			field_9381_bG = flag;
		}
		double d = posX - field_9379_by;
		double d1 = boundingBox.minY - field_9378_bz;
		double d2 = posY - field_9377_bA;
		double d3 = posZ - field_9376_bB;
		double d4 = rotationYaw - field_9385_bC;
		double d5 = rotationPitch - field_9384_bD;
		boolean flag1 = d1 != 0.0D || d2 != 0.0D || d != 0.0D || d3 != 0.0D;
		boolean flag2 = d4 != 0.0D || d5 != 0.0D;
		if (ridingEntity != null) {
			if (flag2) {
				sendQueue.addToSendQueue(new Packet11PlayerPosition(motionX, -999D, -999D, motionZ, onGround));
			} else {
				sendQueue.addToSendQueue(new Packet13PlayerLookMove(motionX, -999D, -999D, motionZ, rotationYaw,
						rotationPitch, onGround));
			}
			flag1 = false;
		} else if (flag1 && flag2) {
			sendQueue.addToSendQueue(new Packet13PlayerLookMove(posX, boundingBox.minY, posY, posZ, rotationYaw,
					rotationPitch, onGround));
			field_12242_bI = 0;
		} else if (flag1) {
			sendQueue.addToSendQueue(new Packet11PlayerPosition(posX, boundingBox.minY, posY, posZ, onGround));
			field_12242_bI = 0;
		} else if (flag2) {
			sendQueue.addToSendQueue(new Packet12PlayerLook(rotationYaw, rotationPitch, onGround));
			field_12242_bI = 0;
		} else {
			sendQueue.addToSendQueue(new Packet10Flying(onGround));
			if (field_9382_bF != onGround || field_12242_bI > 200) {
				field_12242_bI = 0;
			} else {
				field_12242_bI++;
			}
		}
		field_9382_bF = onGround;
		if (flag1) {
			field_9379_by = posX;
			field_9378_bz = boundingBox.minY;
			field_9377_bA = posY;
			field_9376_bB = posZ;
		}
		if (flag2) {
			field_9385_bC = rotationYaw;
			field_9384_bD = rotationPitch;
		}
	}

	public void dropCurrentItem() {
		sendQueue.addToSendQueue(new Packet14BlockDig(4, 0, 0, 0, 0));
	}

	private void sendInventoryChanged() {
	}

	protected void joinEntityItemWithWorld(EntityItem entityitem) {
	}

	public void sendChatMessage(String s) {
		sendQueue.addToSendQueue(new Packet3Chat(s));
	}

	public void swingItem() {
		super.swingItem();
		sendQueue.addToSendQueue(new Packet18ArmAnimation(this, 1));
	}

	public void respawnPlayer() {
		sendInventoryChanged();
		sendQueue.addToSendQueue(new Packet9());
	}

	protected void damageEntity(int i) {
		health -= i;
	}

	public void func_20059_m() {
		sendQueue.addToSendQueue(new Packet101(craftingInventory.windowId));
		inventory.setItemStack(null);
		super.func_20059_m();
	}

	public void setHealth(int i) {
		if (field_21093_bH) {
			super.setHealth(i);
		} else {
			health = i;
			field_21093_bH = true;
		}
	}

	public NetClientHandler sendQueue;
	private int field_9380_bx;
	private boolean field_21093_bH;
	private double field_9379_by;
	private double field_9378_bz;
	private double field_9377_bA;
	private double field_9376_bB;
	private float field_9385_bC;
	private float field_9384_bD;
	private boolean field_9382_bF;
	private boolean field_9381_bG;
	private int field_12242_bI;
}
