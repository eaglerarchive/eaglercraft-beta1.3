package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public interface IRecipe {

	public abstract boolean func_21135_a(InventoryCrafting inventorycrafting);

	public abstract ItemStack func_21136_b(InventoryCrafting inventorycrafting);

	public abstract int getRecipeSize();
}
