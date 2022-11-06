package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ScaledResolution {

	public ScaledResolution(int i, int j) {
		scaledWidth = i;
		scaledHeight = j;
		for (scaleFactor = 1; scaledWidth / (scaleFactor + 1) >= 320
				&& scaledHeight / (scaleFactor + 1) >= 240; scaleFactor++) {
		}
		scaledWidth = scaledWidth / scaleFactor;
		scaledHeight = scaledHeight / scaleFactor;
	}

	public int getScaledWidth() {
		return scaledWidth;
	}

	public int getScaledHeight() {
		return scaledHeight;
	}

	private int scaledWidth;
	private int scaledHeight;
	public int scaleFactor;
}
