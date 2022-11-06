package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public interface IInventory {

	public abstract int getSizeInventory();

	public abstract ItemStack getStackInSlot(int i);

	public abstract ItemStack decrStackSize(int i, int j);

	public abstract void setInventorySlotContents(int i, ItemStack itemstack);

	public abstract String getInvName();

	public abstract int getInventoryStackLimit();

	public abstract void onInventoryChanged();

	public abstract boolean canInteractWith(EntityPlayer entityplayer);
}
