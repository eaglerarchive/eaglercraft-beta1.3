package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ShapedRecipes implements IRecipe {

	public ShapedRecipes(int i, int j, ItemStack aitemstack[], ItemStack itemstack) {
		field_21139_a = itemstack.itemID;
		field_21138_b = i;
		field_21142_c = j;
		field_21141_d = aitemstack;
		field_21140_e = itemstack;
	}

	public boolean func_21135_a(InventoryCrafting inventorycrafting) {
		for (int i = 0; i <= 3 - field_21138_b; i++) {
			for (int j = 0; j <= 3 - field_21142_c; j++) {
				if (func_21137_a(inventorycrafting, i, j, true)) {
					return true;
				}
				if (func_21137_a(inventorycrafting, i, j, false)) {
					return true;
				}
			}

		}

		return false;
	}

	private boolean func_21137_a(InventoryCrafting inventorycrafting, int i, int j, boolean flag) {
		for (int k = 0; k < 3; k++) {
			for (int l = 0; l < 3; l++) {
				int i1 = k - i;
				int j1 = l - j;
				ItemStack itemstack = null;
				if (i1 >= 0 && j1 >= 0 && i1 < field_21138_b && j1 < field_21142_c) {
					if (flag) {
						itemstack = field_21141_d[(field_21138_b - i1 - 1) + j1 * field_21138_b];
					} else {
						itemstack = field_21141_d[i1 + j1 * field_21138_b];
					}
				}
				ItemStack itemstack1 = inventorycrafting.func_21103_b(k, l);
				if (itemstack1 == null && itemstack == null) {
					continue;
				}
				if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
					return false;
				}
				if (itemstack.itemID != itemstack1.itemID) {
					return false;
				}
				if (itemstack.getItemDamage() != -1 && itemstack.getItemDamage() != itemstack1.getItemDamage()) {
					return false;
				}
			}

		}

		return true;
	}

	public ItemStack func_21136_b(InventoryCrafting inventorycrafting) {
		return new ItemStack(field_21140_e.itemID, field_21140_e.stackSize, field_21140_e.getItemDamage());
	}

	public int getRecipeSize() {
		return field_21138_b * field_21142_c;
	}

	private int field_21138_b;
	private int field_21142_c;
	private ItemStack field_21141_d[];
	private ItemStack field_21140_e;
	public final int field_21139_a;
}
