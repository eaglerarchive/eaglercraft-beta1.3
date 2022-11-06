package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import net.lax1dude.eaglercraft.beta.GuiCreateOrImport;
import net.lax1dude.eaglercraft.beta.GuiSomethingFailed;
import net.lax1dude.eaglercraft.beta.ImportExport;
import net.minecraft.client.Minecraft;

public class GuiSelectWorld extends GuiScreen {

	public GuiSelectWorld(GuiScreen guiscreen) {
		screenTitle = "Select world";
		selected = false;
		parentScreen = guiscreen;
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		screenTitle = stringtranslate.translateKey("selectWorld.title");
		field_22098_o = stringtranslate.translateKey("selectWorld.world");
		field_22097_p = stringtranslate.translateKey("selectWorld.conversion");
		func_22084_k();
		field_22099_n = new GuiWorldSlot(this);
		field_22099_n.func_22240_a(controlList, 4, 5);
		initGui2();
	}

	private void func_22084_k() {
		ISaveFormat isaveformat = mc.func_22004_c();
		field_22100_m = isaveformat.getWorldList(mc.loadingScreen);
		Collections.sort(field_22100_m);
		field_22101_l = -1;
	}

	protected String func_22091_c(int i) {
		return ((SaveFormatComparator) field_22100_m.get(i)).func_22164_a();
	}

	protected String func_22094_d(int i) {
		String s = ((SaveFormatComparator) field_22100_m.get(i)).func_22162_b();
		if (s == null || MathHelper.func_22282_a(s)) {
			StringTranslate stringtranslate = StringTranslate.getInstance();
			s = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.world")).append(" ")
					.append(i + 1).toString();
		}
		return s;
	}

	public void initGui2() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.add(field_22104_s = new GuiButton(1, width / 2 - 154, height - 52, 150, 20, stringtranslate.translateKey("selectWorld.select")));
		controlList.add(field_22095_r = new GuiButton(6, width / 2 - 154, height - 28, 70, 20, stringtranslate.translateKey("selectWorld.rename")));
		controlList.add(field_22103_t = new GuiButton(2, width / 2 - 74, height - 28, 70, 20, stringtranslate.translateKey("selectWorld.delete")));
		controlList.add(new GuiButton(3, width / 2 + 4, height - 52, 150, 20, stringtranslate.translateKey("selectWorld.create")));
		controlList.add(export = new GuiButton(7, width / 2 + 4, height - 28, 70, 20, stringtranslate.translateKey("selectWorld.export")));
		controlList.add(new GuiButton(0, width / 2 + 4 + 80, height - 28, 70, 20, stringtranslate.translateKey("gui.cancel")));
		field_22104_s.enabled = false;
		field_22095_r.enabled = false;
		field_22103_t.enabled = false;
		export.enabled = false;
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id == 2) {
			String s = func_22094_d(field_22101_l);
			if (s != null) {
				field_22096_q = true;
				StringTranslate stringtranslate = StringTranslate.getInstance();
				String s1 = stringtranslate.translateKey("selectWorld.deleteQuestion");
				String s2 = (new StringBuilder()).append("'").append(s).append("' ")
						.append(stringtranslate.translateKey("selectWorld.deleteWarning")).toString();
				String s3 = stringtranslate.translateKey("selectWorld.deleteButton");
				String s4 = stringtranslate.translateKey("gui.cancel");
				GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, field_22101_l);
				mc.displayGuiScreen(guiyesno);
			}
		} else if (guibutton.id == 1) {
			selectWorld(field_22101_l);
		} else if (guibutton.id == 3) {
			mc.displayGuiScreen(new GuiCreateOrImport(this));
		} else if (guibutton.id == 6) {
			mc.displayGuiScreen(new GuiRenameWorld(this, func_22091_c(field_22101_l)));
		} else if (guibutton.id == 7) {
			String s = func_22094_d(field_22101_l);
			if (s != null) {
				isExporting = true;
				StringTranslate stringtranslate = StringTranslate.getInstance();
				String s1 = stringtranslate.translateKey("selectWorld.exportQuestion1");
				String s2 = stringtranslate.translateKey("selectWorld.exportQuestion2");
				String s3 = stringtranslate.translateKey("selectWorld.export");
				String s4 = stringtranslate.translateKey("gui.cancel");
				GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, field_22101_l);
				mc.displayGuiScreen(guiyesno);
			}
		} else if (guibutton.id == 0) {
			mc.displayGuiScreen(parentScreen);
		} else {
			field_22099_n.func_22241_a(guibutton);
		}
	}

	public void selectWorld(int i) {
		mc.displayGuiScreen(null);
		if (selected) {
			return;
		}
		selected = true;
		mc.playerController = new PlayerControllerSP(mc);
		String s = func_22091_c(i);
		if (s == null) {
			s = (new StringBuilder()).append("World").append(i).toString();
		}
		mc.startWorld(s, func_22094_d(i), 0L);
		mc.displayGuiScreen(null);
	}

	public void confirmClicked(boolean flag, int i) {
		if (field_22096_q) {
			field_22096_q = false;
			if (flag) {
				ISaveFormat isaveformat = mc.func_22004_c();
				isaveformat.flushCache();
				isaveformat.deleteWorldByDirectory(func_22091_c(i), mc.loadingScreen);
				func_22084_k();
			}
			mc.displayGuiScreen(this);
		}else if (isExporting) {
			isExporting = false;
			if (flag) {
				if(!ImportExport.exportWorld(mc.loadingScreen, func_22091_c(i), func_22094_d(i) + ".epk")) {
					mc.displayGuiScreen(new GuiSomethingFailed(this, "Export Failed", "An exception was encountered while exporting '" + func_22091_c(i) + "'", "Check the game's console"));
				}else {
					mc.displayGuiScreen(this);
				}
			}else {
				mc.displayGuiScreen(this);
			}
		}
	}

	public void drawScreen(int i, int j, float f) {
		field_22099_n.func_22243_a(i, j, f);
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
		super.drawScreen(i, j, f);
	}

	static List func_22090_a(GuiSelectWorld guiselectworld) {
		return guiselectworld.field_22100_m;
	}

	static int func_22089_a(GuiSelectWorld guiselectworld, int i) {
		return guiselectworld.field_22101_l = i;
	}

	static int func_22086_b(GuiSelectWorld guiselectworld) {
		return guiselectworld.field_22101_l;
	}

	static GuiButton func_22083_c(GuiSelectWorld guiselectworld) {
		return guiselectworld.field_22104_s;
	}

	static GuiButton func_22085_d(GuiSelectWorld guiselectworld) {
		return guiselectworld.field_22095_r;
	}

	static GuiButton func_22092_e(GuiSelectWorld guiselectworld) {
		return guiselectworld.field_22103_t;
	}

	static GuiButton getExportButton(GuiSelectWorld guiselectworld) {
		return guiselectworld.export;
	}

	static String func_22087_f(GuiSelectWorld guiselectworld) {
		return guiselectworld.field_22098_o;
	}

	static DateFormat func_22093_g(GuiSelectWorld guiselectworld) {
		return guiselectworld.field_22102_i;
	}

	static String func_22088_h(GuiSelectWorld guiselectworld) {
		return guiselectworld.field_22097_p;
	}

	private final DateFormat field_22102_i = new SimpleDateFormat();
	protected GuiScreen parentScreen;
	protected String screenTitle;
	private boolean selected;
	private int field_22101_l;
	private List field_22100_m;
	private GuiWorldSlot field_22099_n;
	private String field_22098_o;
	private String field_22097_p;
	private boolean field_22096_q;
	private boolean isExporting;
	private GuiButton field_22095_r;
	private GuiButton field_22104_s;
	private GuiButton field_22103_t;
	private GuiButton export;
}
