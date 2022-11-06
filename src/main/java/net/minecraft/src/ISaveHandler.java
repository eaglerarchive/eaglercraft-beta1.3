package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public interface ISaveHandler {

	public abstract WorldInfo getWorldInfo();

	public abstract void checkSessionLock();

	public abstract IChunkLoader getChunkLoader(WorldProvider worldprovider);

	public abstract void saveWorldAndPlayer(WorldInfo worldinfo, List list);

	public abstract void saveWorldInfo(WorldInfo worldinfo);
}
