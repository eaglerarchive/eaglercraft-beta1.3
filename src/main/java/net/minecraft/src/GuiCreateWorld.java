package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglercraftRandom;

public class GuiCreateWorld extends GuiScreen {

	public GuiCreateWorld(GuiScreen guiscreen) {
		field_22131_a = guiscreen;
	}

	public void updateScreen() {
		field_22134_h.onUpdate();
		field_22133_i.onUpdate();
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		EaglerAdapter.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12,
				stringtranslate.translateKey("selectWorld.create")));
		controlList.add(
				new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
		field_22134_h = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20,
				stringtranslate.translateKey("selectWorld.newWorld"));
		field_22134_h.field_22082_a = true;
		field_22134_h.setMaxLength(32);
		field_22133_i = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20, "");
		func_22129_j();
	}

	private void func_22129_j() {
		field_22132_k = field_22134_h.getTextBoxText().trim();
		char ac[] = FontAllowedCharacters.field_22286_b;
		int i = ac.length;
		for (int j = 0; j < i; j++) {
			char c = ac[j];
			field_22132_k = field_22132_k.replace(c, '_');
		}

		if (MathHelper.func_22282_a(field_22132_k)) {
			field_22132_k = "World";
		}
		for (ISaveFormat isaveformat = mc.func_22004_c(); isaveformat
				.getWorldInfoForWorld(field_22132_k) != null; field_22132_k = (new StringBuilder()).append(field_22132_k)
						.append("-").toString()) {
		}
	}

	public void onGuiClosed() {
		EaglerAdapter.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id == 1) {
			mc.displayGuiScreen(field_22131_a);
		} else if (guibutton.id == 0) {
			mc.displayGuiScreen(null);
			if (field_22130_l) {
				return;
			}
			field_22130_l = true;
			long l = (new EaglercraftRandom()).nextLong();
			String s = field_22133_i.getTextBoxText();
			if (!MathHelper.func_22282_a(s)) {
				try {
					long l1 = Long.parseLong(s);
					if (l1 != 0L) {
						l = l1;
					}
				} catch (NumberFormatException numberformatexception) {
					l = s.hashCode();
				}
			}
			mc.playerController = new PlayerControllerSP(mc);
			mc.startWorld(field_22132_k, field_22134_h.getTextBoxText(), l);
			mc.displayGuiScreen(null);
		}
	}

	protected void keyTyped(char c, int i) {
		field_22134_h.handleKeyboardInput(c, i);
		field_22133_i.handleKeyboardInput(c, i);
		if (c == '\r') {
			actionPerformed((GuiButton) controlList.get(0));
		}
		((GuiButton) controlList.get(0)).enabled = field_22134_h.getTextBoxText().length() > 0;
		func_22129_j();
	}

	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		field_22134_h.handleMouseInput(i, j, k);
		field_22133_i.handleMouseInput(i, j, k);
	}

	public void drawScreen(int i, int j, float f) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("selectWorld.create"), width / 2,
				(height / 4 - 60) + 20, 0xffffff);
		drawString(fontRenderer, stringtranslate.translateKey("selectWorld.enterName"), width / 2 - 100, 47, 0xa0a0a0);
		drawString(fontRenderer, (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.resultFolder"))
				.append(" ").append(field_22132_k).toString(), width / 2 - 100, 85, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("selectWorld.enterSeed"), width / 2 - 100, 104, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("selectWorld.seedInfo"), width / 2 - 100, 140, 0xa0a0a0);
		field_22134_h.drawTextBox();
		field_22133_i.drawTextBox();
		super.drawScreen(i, j, f);
	}

	private GuiScreen field_22131_a;
	private GuiTextField field_22134_h;
	private GuiTextField field_22133_i;
	private String field_22132_k;
	private boolean field_22130_l;
}
