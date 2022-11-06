package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerImage;

public class ColorizerGrass {

	public ColorizerGrass() {
	}

	public static int getGrassColor(double d, double d1) {
		if(grassBuffer == null) {
			grassBuffer = EaglerAdapter.loadPNG(EaglerAdapter.loadResourceBytes("/misc/grasscolor.png")).data;
		}
		d1 *= d;
		int i = (int) ((1.0D - d) * 255D);
		int j = (int) ((1.0D - d1) * 255D);
		return grassBuffer[j << 8 | i];
	}

	private static int grassBuffer[] = null;
}
