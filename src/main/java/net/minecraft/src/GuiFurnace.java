package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class GuiFurnace extends GuiContainer {

	private static final TextureLocation containerTexture = new TextureLocation("/gui/furnace.png");
	
	public GuiFurnace(InventoryPlayer inventoryplayer, TileEntityFurnace tileentityfurnace) {
		super(new CraftingInventoryFurnaceCB(inventoryplayer, tileentityfurnace));
		furnaceInventory = tileentityfurnace;
	}

	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("Furnace", 60, 6, 0x404040);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f) {
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		containerTexture.bindTexture();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		if (furnaceInventory.isBurning()) {
			int l = furnaceInventory.getBurnTimeRemainingScaled(12);
			drawTexturedModalRect(j + 56, (k + 36 + 12) - l, 176, 12 - l, 14, l + 2);
		}
		int i1 = furnaceInventory.getCookProgressScaled(24);
		drawTexturedModalRect(j + 79, k + 34, 176, 14, i1 + 1, 16);
	}

	private TileEntityFurnace furnaceInventory;
}
