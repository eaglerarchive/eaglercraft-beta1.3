package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;

public interface IChunkLoader {

	public abstract Chunk loadChunk(World world, int i, int j);

	public abstract void saveChunk(World world, Chunk chunk);

	public abstract void saveExtraChunkData(World world, Chunk chunk);

	public abstract void func_814_a();

	public abstract void saveExtraData();
}
