package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import net.lax1dude.eaglercraft.adapter.Tessellator;

class GuiWorldSlot extends GuiSlot {

	public GuiWorldSlot(GuiSelectWorld guiselectworld) {
		super(guiselectworld.mc, guiselectworld.width, guiselectworld.height, 32, guiselectworld.height - 64, 36);
		field_22266_a = guiselectworld;
	}

	protected int func_22249_a() {
		return GuiSelectWorld.func_22090_a(field_22266_a).size();
	}

	protected void func_22247_a(int i, boolean flag) {
		GuiSelectWorld.func_22089_a(field_22266_a, i);
		boolean flag1 = GuiSelectWorld.func_22086_b(field_22266_a) >= 0
				&& GuiSelectWorld.func_22086_b(field_22266_a) < func_22249_a();
		GuiSelectWorld.func_22083_c(field_22266_a).enabled = flag1;
		GuiSelectWorld.func_22085_d(field_22266_a).enabled = flag1;
		GuiSelectWorld.func_22092_e(field_22266_a).enabled = flag1;
		GuiSelectWorld.getExportButton(field_22266_a).enabled = flag1;
		if (flag && flag1) {
			field_22266_a.selectWorld(i);
		}
	}

	protected boolean func_22246_a(int i) {
		return i == GuiSelectWorld.func_22086_b(field_22266_a);
	}

	protected int func_22245_b() {
		return GuiSelectWorld.func_22090_a(field_22266_a).size() * 36;
	}

	protected void func_22248_c() {
		field_22266_a.drawDefaultBackground();
	}

	protected void func_22242_a(int i, int j, int k, int l, Tessellator tessellator) {
		SaveFormatComparator saveformatcomparator = (SaveFormatComparator) GuiSelectWorld.func_22090_a(field_22266_a)
				.get(i);
		String s = saveformatcomparator.func_22162_b();
		if (s == null || MathHelper.func_22282_a(s)) {
			s = (new StringBuilder()).append(GuiSelectWorld.func_22087_f(field_22266_a)).append(" ").append(i + 1)
					.toString();
		}
		String s1 = saveformatcomparator.func_22164_a();
		s1 = (new StringBuilder()).append(s1).append(" (").append(
				GuiSelectWorld.func_22093_g(field_22266_a).format(new Date(saveformatcomparator.func_22163_e())))
				.toString();
		long l1 = saveformatcomparator.func_22159_c();
		s1 = (new StringBuilder()).append(s1).append(")").toString();
		String s2 = "";
		if (saveformatcomparator.func_22161_d()) {
			s2 = (new StringBuilder()).append(GuiSelectWorld.func_22088_h(field_22266_a)).append(" ").append(s2)
					.toString();
		}
		field_22266_a.drawString(field_22266_a.fontRenderer, s, j + 2, k + 1, 0xffffff);
		field_22266_a.drawString(field_22266_a.fontRenderer, s1, j + 2, k + 12, 0x808080);
		field_22266_a.drawString(field_22266_a.fontRenderer, s2, j + 2, k + 12 + 10, 0x808080);
	}

	final GuiSelectWorld field_22266_a; /* synthetic field */
}
