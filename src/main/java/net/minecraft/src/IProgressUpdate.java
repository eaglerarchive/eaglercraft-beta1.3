package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public interface IProgressUpdate {

	public abstract void func_594_b(String s);

	public abstract void displayLoadingString(String s);
	
	public abstract void displayLoadingString(String s, String s1);

	public abstract void setLoadingProgress(int i);
}
