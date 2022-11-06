package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;
import net.minecraft.client.Minecraft;

public abstract class GuiSlot {
	
	private static final TextureLocation backgroundTexture = new TextureLocation("/gui/background.png");

	public GuiSlot(Minecraft minecraft, int i, int j, int k, int l, int i1) {
		field_22254_k = -2F;
		field_22251_n = -1;
		field_22250_o = 0L;
		field_22264_a = minecraft;
		field_22263_b = i;
		field_22262_c = j;
		field_22261_d = k;
		field_22260_e = l;
		field_22257_h = i1;
		field_22259_f = i;
	}

	protected abstract int func_22249_a();

	protected abstract void func_22247_a(int i, boolean flag);

	protected abstract boolean func_22246_a(int i);

	protected abstract int func_22245_b();

	protected abstract void func_22248_c();

	protected abstract void func_22242_a(int i, int j, int k, int l, Tessellator tessellator);

	public void func_22240_a(List list, int i, int j) {
		field_22256_i = i;
		field_22255_j = j;
	}

	private void func_22244_d() {
		int i = func_22245_b() - (field_22260_e - field_22261_d - 4);
		if (i < 0) {
			i /= 2;
		}
		if (field_22252_m < 0.0F) {
			field_22252_m = 0.0F;
		}
		if (field_22252_m > (float) i) {
			field_22252_m = i;
		}
	}

	public void func_22241_a(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id == field_22256_i) {
			field_22252_m -= (field_22257_h * 2) / 3;
			field_22254_k = -2F;
			func_22244_d();
		} else if (guibutton.id == field_22255_j) {
			field_22252_m += (field_22257_h * 2) / 3;
			field_22254_k = -2F;
			func_22244_d();
		}
	}

	public void func_22243_a(int i, int j, float f) {
		func_22248_c();
		int k = func_22249_a();
		int l = field_22263_b / 2 + 124;
		int i1 = l + 6;
		if (EaglerAdapter.mouseIsButtonDown(0)) {
			if (field_22254_k == -1F) {
				if (j >= field_22261_d && j <= field_22260_e) {
					int j1 = field_22263_b / 2 - 110;
					int k1 = field_22263_b / 2 + 110;
					int l1 = (((j - field_22261_d) + (int) field_22252_m) - 2) / field_22257_h;
					if (i >= j1 && i <= k1 && l1 >= 0 && l1 < k) {
						boolean flag = l1 == field_22251_n && System.currentTimeMillis() - field_22250_o < 250L;
						func_22247_a(l1, flag);
						field_22251_n = l1;
						field_22250_o = System.currentTimeMillis();
					}
					if (i >= l && i <= i1) {
						field_22253_l = -1F;
						int j2 = func_22245_b() - (field_22260_e - field_22261_d - 4);
						if (j2 < 1) {
							j2 = 1;
						}
						int i3 = ((field_22260_e - field_22261_d) * (field_22260_e - field_22261_d)) / func_22245_b();
						if (i3 < 32) {
							i3 = 32;
						}
						if (i3 > field_22260_e - field_22261_d - 8) {
							i3 = field_22260_e - field_22261_d - 8;
						}
						field_22253_l /= (float) (field_22260_e - field_22261_d - i3) / (float) j2;
					} else {
						field_22253_l = 1.0F;
					}
					field_22254_k = j;
				} else {
					field_22254_k = -2F;
				}
			} else if (field_22254_k >= 0.0F) {
				field_22252_m -= ((float) j - field_22254_k) * field_22253_l;
				field_22254_k = j;
			}
		} else {
			field_22254_k = -1F;
		}
		func_22244_d();
		EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
		EaglerAdapter.glDisable(2912 /* GL_FOG */);
		Tessellator tessellator = Tessellator.instance;
		backgroundTexture.bindTexture();
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f1 = 32F;
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(0x202020);
		tessellator.addVertexWithUV(field_22258_g, field_22260_e, 0.0D, (float) field_22258_g / f1,
				(float) (field_22260_e + (int) field_22252_m) / f1);
		tessellator.addVertexWithUV(field_22259_f, field_22260_e, 0.0D, (float) field_22259_f / f1,
				(float) (field_22260_e + (int) field_22252_m) / f1);
		tessellator.addVertexWithUV(field_22259_f, field_22261_d, 0.0D, (float) field_22259_f / f1,
				(float) (field_22261_d + (int) field_22252_m) / f1);
		tessellator.addVertexWithUV(field_22258_g, field_22261_d, 0.0D, (float) field_22258_g / f1,
				(float) (field_22261_d + (int) field_22252_m) / f1);
		tessellator.draw();
		for (int i2 = 0; i2 < k; i2++) {
			int k2 = field_22263_b / 2 - 92 - 16;
			int j3 = (field_22261_d + 4 + i2 * field_22257_h) - (int) field_22252_m;
			byte byte1 = 32;
			if (func_22246_a(i2)) {
				int i4 = field_22263_b / 2 - 110;
				int j4 = field_22263_b / 2 + 110;
				EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
				tessellator.startDrawingQuads();
				tessellator.setColorOpaque_I(0x808080);
				tessellator.addVertexWithUV(i4, j3 + byte1 + 2, 0.0D, 0.0D, 1.0D);
				tessellator.addVertexWithUV(j4, j3 + byte1 + 2, 0.0D, 1.0D, 1.0D);
				tessellator.addVertexWithUV(j4, j3 - 2, 0.0D, 1.0D, 0.0D);
				tessellator.addVertexWithUV(i4, j3 - 2, 0.0D, 0.0D, 0.0D);
				tessellator.setColorOpaque_I(0);
				tessellator.addVertexWithUV(i4 + 1, j3 + byte1 + 1, 0.0D, 0.0D, 1.0D);
				tessellator.addVertexWithUV(j4 - 1, j3 + byte1 + 1, 0.0D, 1.0D, 1.0D);
				tessellator.addVertexWithUV(j4 - 1, j3 - 1, 0.0D, 1.0D, 0.0D);
				tessellator.addVertexWithUV(i4 + 1, j3 - 1, 0.0D, 0.0D, 0.0D);
				tessellator.draw();
				EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
			}
			func_22242_a(i2, k2, j3, byte1, tessellator);
		}

		byte byte0 = 4;
		func_22239_a(0, field_22261_d, 255, 255);
		func_22239_a(field_22260_e, field_22262_c, 255, 255);
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glBlendFunc(770, 771);
		EaglerAdapter.glDisable(3008 /* GL_ALPHA_TEST */);
		EaglerAdapter.glShadeModel(7425 /* GL_SMOOTH */);
		EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(0, 0);
		tessellator.addVertexWithUV(field_22258_g, field_22261_d + byte0, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(field_22259_f, field_22261_d + byte0, 0.0D, 1.0D, 1.0D);
		tessellator.setColorRGBA_I(0, 255);
		tessellator.addVertexWithUV(field_22259_f, field_22261_d, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(field_22258_g, field_22261_d, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(0, 255);
		tessellator.addVertexWithUV(field_22258_g, field_22260_e, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(field_22259_f, field_22260_e, 0.0D, 1.0D, 1.0D);
		tessellator.setColorRGBA_I(0, 0);
		tessellator.addVertexWithUV(field_22259_f, field_22260_e - byte0, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(field_22258_g, field_22260_e - byte0, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		int l2 = func_22245_b() - (field_22260_e - field_22261_d - 4);
		if (l2 > 0) {
			int k3 = ((field_22260_e - field_22261_d) * (field_22260_e - field_22261_d)) / func_22245_b();
			if (k3 < 32) {
				k3 = 32;
			}
			if (k3 > field_22260_e - field_22261_d - 8) {
				k3 = field_22260_e - field_22261_d - 8;
			}
			int l3 = ((int) field_22252_m * (field_22260_e - field_22261_d - k3)) / l2 + field_22261_d;
			if (l3 < field_22261_d) {
				l3 = field_22261_d;
			}
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			tessellator.addVertexWithUV(l, field_22260_e, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(i1, field_22260_e, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(i1, field_22261_d, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(l, field_22261_d, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0x808080, 255);
			tessellator.addVertexWithUV(l, l3 + k3, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(i1, l3 + k3, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(i1, l3, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(l, l3, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0xc0c0c0, 255);
			tessellator.addVertexWithUV(l, (l3 + k3) - 1, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(i1 - 1, (l3 + k3) - 1, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(i1 - 1, l3, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(l, l3, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
		}
		EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		EaglerAdapter.glShadeModel(7424 /* GL_FLAT */);
		EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
	}

	private void func_22239_a(int i, int j, int k, int l) {
		Tessellator tessellator = Tessellator.instance;
		backgroundTexture.bindTexture();
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f = 32F;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(0x404040, l);
		tessellator.addVertexWithUV(0.0D, j, 0.0D, 0.0D, (float) j / f);
		tessellator.addVertexWithUV(field_22263_b, j, 0.0D, (float) field_22263_b / f, (float) j / f);
		tessellator.setColorRGBA_I(0x404040, k);
		tessellator.addVertexWithUV(field_22263_b, i, 0.0D, (float) field_22263_b / f, (float) i / f);
		tessellator.addVertexWithUV(0.0D, i, 0.0D, 0.0D, (float) i / f);
		tessellator.draw();
	}

	private final Minecraft field_22264_a;
	private final int field_22263_b;
	private final int field_22262_c;
	private final int field_22261_d;
	private final int field_22260_e;
	private final int field_22259_f;
	private final int field_22258_g = 0;
	private final int field_22257_h;
	private int field_22256_i;
	private int field_22255_j;
	private float field_22254_k;
	private float field_22253_l;
	private float field_22252_m;
	private int field_22251_n;
	private long field_22250_o;
}
