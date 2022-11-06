package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;
import net.minecraft.client.Minecraft;

public class GuiScreen extends Gui {
	
	private static final TextureLocation backgroundTexture = new TextureLocation("/gui/background.png");

	public GuiScreen() {
		controlList = new ArrayList();
		field_948_f = false;
		selectedButton = null;
	}

	public void drawScreen(int i, int j, float f) {
		for (int k = 0; k < controlList.size(); k++) {
			GuiButton guibutton = (GuiButton) controlList.get(k);
			guibutton.drawButton(mc, i, j);
		}

	}

	protected void keyTyped(char c, int i) {
		if (i == 1) {
			mc.displayGuiScreen(null);
			mc.grabMouseCursor();
		}
	}

	public static String getClipboardString() {
		try {
			String s = EaglerAdapter.getClipboard();
			return s == null ? "" : s;
		}catch(Throwable t) {
			return "";
		}
	}

	protected void mouseClicked(int i, int j, int k) {
		if (k == 0) {
			for (int l = 0; l < controlList.size(); l++) {
				GuiButton guibutton = (GuiButton) controlList.get(l);
				if (guibutton.mousePressed(mc, i, j)) {
					selectedButton = guibutton;
					mc.sndManager.func_337_a("random.click", 1.0F, 1.0F);
					actionPerformed(guibutton);
				}
			}

		}
	}

	protected void mouseMovedOrUp(int i, int j, int k) {
		if (selectedButton != null && k == 0) {
			selectedButton.mouseReleased(i, j);
			selectedButton = null;
		}
	}

	protected void actionPerformed(GuiButton guibutton) {
	}

	public void setWorldAndResolution(Minecraft minecraft, int i, int j) {
		mc = minecraft;
		fontRenderer = minecraft.fontRenderer;
		width = i;
		height = j;
		controlList.clear();
		initGui();
	}

	public void initGui() {
	}

	public void handleInput() {
		for (; EaglerAdapter.mouseNext(); handleMouseInput()) {
		}
		for (; EaglerAdapter.keysNext(); handleKeyboardInput()) {
		}
	}

	public void handleMouseInput() {
		EaglerAdapter.mouseSetGrabbed(false);
		if (EaglerAdapter.mouseGetEventButtonState()) {
			int i = (EaglerAdapter.mouseGetEventX() * width) / mc.displayWidth;
			int k = height - (EaglerAdapter.mouseGetEventY() * height) / mc.displayHeight - 1;
			mouseClicked(i, k, EaglerAdapter.mouseGetEventButton());
		} else {
			int j = (EaglerAdapter.mouseGetEventX() * width) / mc.displayWidth;
			int l = height - (EaglerAdapter.mouseGetEventY() * height) / mc.displayHeight - 1;
			mouseMovedOrUp(j, l, EaglerAdapter.mouseGetEventButton());
		}
	}

	public void handleKeyboardInput() {
		if (EaglerAdapter.getEventKeyState()) {
			if (EaglerAdapter.getEventKey() == 87) {
				mc.toggleFullscreen();
				return;
			}
			keyTyped(EaglerAdapter.getEventChar(), EaglerAdapter.getEventKey());
		}
	}

	public void updateScreen() {
	}

	public void onGuiClosed() {
	}

	public void drawDefaultBackground() {
		drawWorldBackground(0);
	}

	public void drawWorldBackground(int i) {
		if (mc.theWorld != null) {
			drawGradientRect(0, 0, width, height, 0xc0101010, 0xd0101010);
		} else {
			drawBackground(i);
		}
	}

	public void drawBackground(int i) {
		EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
		EaglerAdapter.glDisable(2912 /* GL_FOG */);
		Tessellator tessellator = Tessellator.instance;
		backgroundTexture.bindTexture();
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f = 32F;
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(0x404040);
		tessellator.addVertexWithUV(0.0D, height, 0.0D, 0.0D, (float) height / f + (float) i);
		tessellator.addVertexWithUV(width, height, 0.0D, (float) width / f, (float) height / f + (float) i);
		tessellator.addVertexWithUV(width, 0.0D, 0.0D, (float) width / f, 0 + i);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0 + i);
		tessellator.draw();
	}

	public boolean doesGuiPauseGame() {
		return true;
	}

	public void confirmClicked(boolean flag, int i) {
	}

	public static boolean isCtrlKeyDown() {
		return EaglerAdapter.isKeyDown(29) || EaglerAdapter.isKeyDown(157);
	}

	public static boolean isShiftKeyDown() {
		return EaglerAdapter.isKeyDown(42) || EaglerAdapter.isKeyDown(54);
	}
	
	public Minecraft getMinecraft() {
		return mc == null ? Minecraft.getMinecraft() : mc;
	}

	protected Minecraft mc;
	public int width;
	public int height;
	protected java.util.List controlList;
	public boolean field_948_f;
	protected FontRenderer fontRenderer;
	private GuiButton selectedButton;
}
