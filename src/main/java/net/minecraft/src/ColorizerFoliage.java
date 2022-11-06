package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerImage;

public class ColorizerFoliage {

	public ColorizerFoliage() {
	}

	public static int getFoliageColor(double d, double d1) {
		if(foliageBuffer == null) {
			foliageBuffer = EaglerAdapter.loadPNG(EaglerAdapter.loadResourceBytes("/misc/foliagecolor.png")).data;
		}
		d1 *= d;
		int i = (int) ((1.0D - d) * 255D);
		int j = (int) ((1.0D - d1) * 255D);
		return foliageBuffer[j << 8 | i];
	}

	public static int func_21175_a() {
		return 0x619961;
	}

	public static int func_21174_b() {
		return 0x80a755;
	}
	
	private static int foliageBuffer[] = null;

}
