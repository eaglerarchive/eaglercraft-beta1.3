package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class SlotCrafting extends Slot {

	public SlotCrafting(IInventory iinventory, IInventory iinventory1, int i, int j, int k) {
		super(iinventory1, i, j, k);
		craftMatrix = iinventory;
	}

	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}

	public void onPickupFromSlot() {
		for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
			ItemStack itemstack = craftMatrix.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			craftMatrix.decrStackSize(i, 1);
			if (itemstack.getItem().func_21014_i()) {
				craftMatrix.setInventorySlotContents(i, new ItemStack(itemstack.getItem().getContainerItem()));
			}
		}

	}

	private final IInventory craftMatrix;
}
