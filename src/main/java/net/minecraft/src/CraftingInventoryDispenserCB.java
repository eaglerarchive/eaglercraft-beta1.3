package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class CraftingInventoryDispenserCB extends CraftingInventoryCB {

	public CraftingInventoryDispenserCB(IInventory iinventory, TileEntityDispenser tileentitydispenser) {
		field_21149_a = tileentitydispenser;
		for (int i = 0; i < 3; i++) {
			for (int l = 0; l < 3; l++) {
				func_20117_a(new Slot(tileentitydispenser, l + i * 3, 61 + l * 18, 17 + i * 18));
			}

		}

		for (int j = 0; j < 3; j++) {
			for (int i1 = 0; i1 < 9; i1++) {
				func_20117_a(new Slot(iinventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
			}

		}

		for (int k = 0; k < 9; k++) {
			func_20117_a(new Slot(iinventory, k, 8 + k * 18, 142));
		}

	}

	public boolean func_20120_b(EntityPlayer entityplayer) {
		return field_21149_a.canInteractWith(entityplayer);
	}

	private TileEntityDispenser field_21149_a;
}
