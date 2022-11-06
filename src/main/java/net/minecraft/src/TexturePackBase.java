package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.InputStream;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.minecraft.client.Minecraft;

public abstract class TexturePackBase {

	public TexturePackBase() {
	}

	public void func_6482_a() {
	}

	public void closeTexturePackFile() {
	}

	public void func_6485_a(Minecraft minecraft) {
	}

	public void func_6484_b(Minecraft minecraft) {
	}

	public void func_6483_c(Minecraft minecraft) {
	}

	public byte[] func_6481_a(String s) {
		return EaglerAdapter.loadResourceBytes(s);
	}

	public String texturePackFileName;
	public String firstDescriptionLine;
	public String secondDescriptionLine;
	public String field_6488_d;
}
