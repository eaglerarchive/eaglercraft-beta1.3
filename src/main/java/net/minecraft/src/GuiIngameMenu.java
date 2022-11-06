package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiIngameMenu extends GuiScreen {

	public GuiIngameMenu() {
		updateCounter2 = 0;
		updateCounter = 0;
	}

	public void initGui() {
		updateCounter2 = 0;
		controlList.clear();
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 48, "Save and quit to title"));
		if (mc.isMultiplayerWorld()) {
			((GuiButton) controlList.get(0)).displayString = "Disconnect";
		}
		controlList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24, "Back to game"));
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96, "Options..."));
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (guibutton.id == 0) {
			mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
		}
		if (guibutton.id == 1) {
			if (mc.isMultiplayerWorld()) {
				mc.theWorld.sendQuittingDisconnectingPacket();
			}
			mc.changeWorld1(null);
			mc.displayGuiScreen(new GuiMainMenu());
		}
		if (guibutton.id == 4) {
			mc.displayGuiScreen(null);
		}
	}

	public void updateScreen() {
		super.updateScreen();
		updateCounter++;
	}

	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		boolean flag = !mc.theWorld.func_650_a(updateCounter2++);
		if (flag || updateCounter < 20) {
			float f1 = ((float) (updateCounter % 10) + f) / 10F;
			f1 = MathHelper.sin(f1 * 3.141593F * 2.0F) * 0.2F + 0.8F;
			int k = (int) (255F * f1);
			drawString(fontRenderer, "Saving level..", 8, height - 16, k << 16 | k << 8 | k);
		}
		drawCenteredString(fontRenderer, "Game menu", width / 2, 40, 0xffffff);
		super.drawScreen(i, j, f);
	}

	private int updateCounter2;
	private int updateCounter;
}
