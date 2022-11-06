package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class GuiCrafting extends GuiContainer {
	
	private static final TextureLocation containerTexture = new TextureLocation("/gui/crafting.png");

	public GuiCrafting(InventoryPlayer inventoryplayer, World world, int i, int j, int k) {
		super(new CraftingInventoryWorkbenchCB(inventoryplayer, world, i, j, k));
	}

	public void onGuiClosed() {
		super.onGuiClosed();
		inventorySlots.onCraftGuiClosed(mc.thePlayer);
	}

	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("Crafting", 28, 6, 0x404040);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f) {
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		containerTexture.bindTexture();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
	}
}
