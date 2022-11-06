package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public interface ISaveFormat {

	public abstract String formatName();

	public abstract ISaveHandler loadWorldHandler(String s, boolean flag);

	public abstract List getWorldList(IProgressUpdate progress);

	public abstract void flushCache();

	public abstract WorldInfo getWorldInfoForWorld(String s);

	public abstract void deleteWorldByDirectory(String s, IProgressUpdate progress);

	public abstract void renameWorldData(String s, String s1);

	public abstract boolean worldNeedsConvert_maybe(String s);

	public abstract boolean convertSave(String s, IProgressUpdate iprogressupdate);
}
