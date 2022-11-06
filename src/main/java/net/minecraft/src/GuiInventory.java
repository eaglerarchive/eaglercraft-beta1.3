package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class GuiInventory extends GuiContainer {
	
	private static final TextureLocation containerTexture = new TextureLocation("/gui/inventory.png");

	public GuiInventory(EntityPlayer entityplayer) {
		super(entityplayer.inventorySlots);
		field_948_f = true;
	}

	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("Crafting", 86, 16, 0x404040);
	}

	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		xSize_lo = i;
		ySize_lo = j;
	}

	protected void drawGuiContainerBackgroundLayer(float f) {
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		containerTexture.bindTexture();
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
		EaglerAdapter.glEnable(2903 /* GL_COLOR_MATERIAL */);
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef(j + 51, k + 75, 50F);
		float f1 = 30F;
		EaglerAdapter.glScalef(-f1, f1, f1);
		EaglerAdapter.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		float f2 = mc.thePlayer.renderYawOffset;
		float f3 = mc.thePlayer.rotationYaw;
		float f4 = mc.thePlayer.rotationPitch;
		float f5 = (float) (j + 51) - xSize_lo;
		float f6 = (float) ((k + 75) - 50) - ySize_lo;
		EaglerAdapter.glRotatef(135F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		EaglerAdapter.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
		EaglerAdapter.glRotatef(-(float) Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
		mc.thePlayer.renderYawOffset = (float) Math.atan(f5 / 40F) * 20F;
		mc.thePlayer.rotationYaw = (float) Math.atan(f5 / 40F) * 40F;
		mc.thePlayer.rotationPitch = -(float) Math.atan(f6 / 40F) * 20F;
		EaglerAdapter.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
		RenderManager.instance.renderEntityWithPosYaw(mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		mc.thePlayer.renderYawOffset = f2;
		mc.thePlayer.rotationYaw = f3;
		mc.thePlayer.rotationPitch = f4;
		EaglerAdapter.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		EaglerAdapter.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
	}

	private float xSize_lo;
	private float ySize_lo;
}
