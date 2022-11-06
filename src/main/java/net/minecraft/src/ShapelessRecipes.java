package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

public class ShapelessRecipes implements IRecipe {

	public ShapelessRecipes(ItemStack itemstack, List list) {
		field_21144_a = itemstack;
		field_21143_b = list;
	}

	public boolean func_21135_a(InventoryCrafting inventorycrafting) {
		ArrayList arraylist = new ArrayList(field_21143_b);
		int i = 0;
		do {
			if (i >= 3) {
				break;
			}
			for (int j = 0; j < 3; j++) {
				ItemStack itemstack = inventorycrafting.func_21103_b(j, i);
				if (itemstack == null) {
					continue;
				}
				boolean flag = false;
				Iterator iterator = arraylist.iterator();
				do {
					if (!iterator.hasNext()) {
						break;
					}
					ItemStack itemstack1 = (ItemStack) iterator.next();
					if (itemstack.itemID != itemstack1.itemID || itemstack1.getItemDamage() != -1
							&& itemstack.getItemDamage() != itemstack1.getItemDamage()) {
						continue;
					}
					flag = true;
					arraylist.remove(itemstack1);
					break;
				} while (true);
				if (!flag) {
					return false;
				}
			}

			i++;
		} while (true);
		return arraylist.isEmpty();
	}

	public ItemStack func_21136_b(InventoryCrafting inventorycrafting) {
		return field_21144_a.copy();
	}

	public int getRecipeSize() {
		return field_21143_b.size();
	}

	private final ItemStack field_21144_a;
	private final List field_21143_b;
}
