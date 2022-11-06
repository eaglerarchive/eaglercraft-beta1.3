package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class Slot {

	public Slot(IInventory iinventory, int i, int j, int k) {
		inventory = iinventory;
		slotIndex = i;
		xDisplayPosition = j;
		yDisplayPosition = k;
	}

	public void onPickupFromSlot() {
		onSlotChanged();
	}

	public boolean isItemValid(ItemStack itemstack) {
		return true;
	}

	public ItemStack getStack() {
		return inventory.getStackInSlot(slotIndex);
	}

	public boolean func_20005_c() {
		return getStack() != null;
	}

	public void putStack(ItemStack itemstack) {
		inventory.setInventorySlotContents(slotIndex, itemstack);
		onSlotChanged();
	}

	public void onSlotChanged() {
		inventory.onInventoryChanged();
	}

	public int getSlotStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	public int func_775_c() {
		return -1;
	}

	public ItemStack decrStackSize(int i) {
		return inventory.decrStackSize(slotIndex, i);
	}

	private final int slotIndex;
	private final IInventory inventory;
	public int field_20007_a;
	public int xDisplayPosition;
	public int yDisplayPosition;
}
