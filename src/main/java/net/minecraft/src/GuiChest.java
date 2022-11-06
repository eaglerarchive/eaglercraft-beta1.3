package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class GuiChest extends GuiContainer {
	
	private static final TextureLocation containerTexture = new TextureLocation("/gui/container.png");

	public GuiChest(IInventory iinventory, IInventory iinventory1) {
		super(new CraftingInventoryChestCB(iinventory, iinventory1));
		inventoryRows = 0;
		upperChestInventory = iinventory;
		lowerChestInventory = iinventory1;
		field_948_f = false;
		char c = '\336';
		int i = c - 108;
		inventoryRows = iinventory1.getSizeInventory() / 9;
		ySize = i + inventoryRows * 18;
	}

	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString(lowerChestInventory.getInvName(), 8, 6, 0x404040);
		fontRenderer.drawString(upperChestInventory.getInvName(), 8, (ySize - 96) + 2, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f) {
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		containerTexture.bindTexture();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, inventoryRows * 18 + 17);
		drawTexturedModalRect(j, k + inventoryRows * 18 + 17, 0, 126, xSize, 96);
	}

	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;
	private int inventoryRows;
}
