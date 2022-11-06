package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class LoadingScreenRenderer implements IProgressUpdate {

	public LoadingScreenRenderer(Minecraft minecraft) {
		field_1004_a = "";
		field_1007_c = "";
		field_1006_d = System.currentTimeMillis();
		field_1005_e = false;
		mc = minecraft;
	}

	public void printText(String s) {
		field_1005_e = false;
		func_597_c(s);
	}

	public void func_594_b(String s) {
		field_1005_e = true;
		func_597_c(field_1007_c);
	}

	public void func_597_c(String s) {
		if (!mc.running) {
			if (field_1005_e) {
				return;
			} else {
				throw new MinecraftError();
			}
		} else {
			field_1007_c = s;
			ScaledResolution scaledresolution = new ScaledResolution(mc.displayWidth, mc.displayHeight);
			int i = scaledresolution.getScaledWidth();
			int j = scaledresolution.getScaledHeight();
			EaglerAdapter.glClear(256);
			EaglerAdapter.glMatrixMode(5889 /* GL_PROJECTION */);
			EaglerAdapter.glLoadIdentity();
			EaglerAdapter.glOrtho(0.0F, i, j, 0.0F, 100F, 300F);
			EaglerAdapter.glMatrixMode(5888 /* GL_MODELVIEW0_ARB */);
			EaglerAdapter.glLoadIdentity();
			EaglerAdapter.glTranslatef(0.0F, 0.0F, -200F);
			return;
		}
	}

	public void displayLoadingString(String s) {
		if (!mc.running) {
			if (field_1005_e) {
				return;
			} else {
				throw new MinecraftError();
			}
		} else {
			field_1006_d = 0L;
			field_1004_a = s;
			setLoadingProgress(-1);
			field_1006_d = 0L;
			return;
		}
	}
	
	public void displayLoadingString(String s, String s1) {
		if (!mc.running) {
			if (field_1005_e) {
				return;
			} else {
				throw new MinecraftError();
			}
		} else {
			field_1006_d = 0L;
			field_1004_a = s1;
			field_1007_c = s;
			setLoadingProgress(-1);
			field_1006_d = 0L;
			return;
		}
	}

	public void setLoadingProgress(int i) {
		if (!mc.running) {
			if (field_1005_e) {
				return;
			} else {
				throw new MinecraftError();
			}
		}
		long l = System.currentTimeMillis();
		if (l - field_1006_d < 20L) {
			return;
		}
		field_1006_d = l;
		ScaledResolution scaledresolution = new ScaledResolution(mc.displayWidth, mc.displayHeight);
		int j = scaledresolution.getScaledWidth();
		int k = scaledresolution.getScaledHeight();
		EaglerAdapter.glClear(256);
		EaglerAdapter.glMatrixMode(5889 /* GL_PROJECTION */);
		EaglerAdapter.glLoadIdentity();
		EaglerAdapter.glOrtho(0.0F, j, k, 0.0F, 100F, 300F);
		EaglerAdapter.glMatrixMode(5888 /* GL_MODELVIEW0_ARB */);
		EaglerAdapter.glLoadIdentity();
		EaglerAdapter.glTranslatef(0.0F, 0.0F, -200F);
		EaglerAdapter.glClear(16640);
		Tessellator tessellator = Tessellator.instance;
		int i1 = mc.renderEngine.getTexture("/gui/background.png");
		EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, i1);
		float f = 32F;
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(0x404040);
		tessellator.addVertexWithUV(0.0D, k, 0.0D, 0.0D, (float) k / f);
		tessellator.addVertexWithUV(j, k, 0.0D, (float) j / f, (float) k / f);
		tessellator.addVertexWithUV(j, 0.0D, 0.0D, (float) j / f, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		if (i >= 0) {
			byte byte0 = 100;
			byte byte1 = 2;
			int j1 = j / 2 - byte0 / 2;
			int k1 = k / 2 + 16;
			EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_I(0x808080);
			tessellator.addVertex(j1, k1, 0.0D);
			tessellator.addVertex(j1, k1 + byte1, 0.0D);
			tessellator.addVertex(j1 + byte0, k1 + byte1, 0.0D);
			tessellator.addVertex(j1 + byte0, k1, 0.0D);
			tessellator.setColorOpaque_I(0x80ff80);
			tessellator.addVertex(j1, k1, 0.0D);
			tessellator.addVertex(j1, k1 + byte1, 0.0D);
			tessellator.addVertex(j1 + i, k1 + byte1, 0.0D);
			tessellator.addVertex(j1 + i, k1, 0.0D);
			tessellator.draw();
			EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		}
		mc.fontRenderer.drawStringWithShadow(field_1007_c, (j - mc.fontRenderer.getStringWidth(field_1007_c)) / 2,
				k / 2 - 4 - 16, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(field_1004_a, (j - mc.fontRenderer.getStringWidth(field_1004_a)) / 2,
				(k / 2 - 4) + 8, 0xffffff);
		EaglerAdapter.updateDisplay();
		try {
			Thread.yield();
		} catch (Exception exception) {
		}
	}

	private String field_1004_a;
	private Minecraft mc;
	private String field_1007_c;
	private long field_1006_d;
	private boolean field_1005_e;
}
