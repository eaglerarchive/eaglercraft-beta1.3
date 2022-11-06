package net.minecraft.src;

import java.util.ArrayList;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class MobSpawnerHell extends MobSpawnerBase {

	public MobSpawnerHell() {
		biomeMonsters = new ArrayList();
		biomeMonsters.add((w) -> new EntityGhast(w));
		biomeMonsters.add((w) -> new EntityPigZombie(w));
		biomeCreatures = new ArrayList();
	}
}
