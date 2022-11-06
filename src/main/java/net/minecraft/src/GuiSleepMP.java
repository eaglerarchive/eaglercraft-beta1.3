package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.lax1dude.eaglercraft.EaglerAdapter;

public class GuiSleepMP extends GuiChat {

	public GuiSleepMP() {
	}

	public void initGui() {
		EaglerAdapter.enableRepeatEvents(true);
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.add(new GuiButton(1, width / 2 - 100, height - 40,
				stringtranslate.translateKey("multiplayer.stopSleeping")));
	}

	public void onGuiClosed() {
		EaglerAdapter.enableRepeatEvents(false);
	}

	protected void keyTyped(char c, int i) {
		if (i == 1) {
			func_22115_j();
		} else if (i == 28) {
			String s = field_985_a.trim();
			if (s.length() > 0) {
				mc.thePlayer.sendChatMessage(field_985_a.trim());
			}
			field_985_a = "";
		} else {
			super.keyTyped(c, i);
		}
	}

	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (guibutton.id == 1) {
			func_22115_j();
		} else {
			super.actionPerformed(guibutton);
		}
	}

	private void func_22115_j() {
		if (mc.thePlayer instanceof EntityClientPlayerMP) {
			NetClientHandler netclienthandler = ((EntityClientPlayerMP) mc.thePlayer).sendQueue;
			netclienthandler.addToSendQueue(new Packet19(mc.thePlayer, 3));
		}
	}
}
