package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class CraftingInventoryChestCB extends CraftingInventoryCB {

	public CraftingInventoryChestCB(IInventory iinventory, IInventory iinventory1) {
		field_20125_a = iinventory1;
		int i = iinventory1.getSizeInventory() / 9;
		int j = (i - 4) * 18;
		for (int k = 0; k < i; k++) {
			for (int j1 = 0; j1 < 9; j1++) {
				func_20117_a(new Slot(iinventory1, j1 + k * 9, 8 + j1 * 18, 18 + k * 18));
			}

		}

		for (int l = 0; l < 3; l++) {
			for (int k1 = 0; k1 < 9; k1++) {
				func_20117_a(new Slot(iinventory, k1 + l * 9 + 9, 8 + k1 * 18, 103 + l * 18 + j));
			}

		}

		for (int i1 = 0; i1 < 9; i1++) {
			func_20117_a(new Slot(iinventory, i1, 8 + i1 * 18, 161 + j));
		}

	}

	public boolean func_20120_b(EntityPlayer entityplayer) {
		return field_20125_a.canInteractWith(entityplayer);
	}

	private IInventory field_20125_a;
}
