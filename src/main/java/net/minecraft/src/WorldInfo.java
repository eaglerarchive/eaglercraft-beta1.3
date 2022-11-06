package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class WorldInfo {

	public WorldInfo(NBTTagCompound nbttagcompound) {
		randomSeed = nbttagcompound.getLong("RandomSeed");
		spawnX = nbttagcompound.getInteger("SpawnX");
		spawnY = nbttagcompound.getInteger("SpawnY");
		spawnZ = nbttagcompound.getInteger("SpawnZ");
		worldTime = nbttagcompound.getLong("Time");
		field_22315_f = nbttagcompound.getLong("LastPlayed");
		sizeOnDisk = nbttagcompound.getLong("SizeOnDisk");
		levelName = nbttagcompound.getString("LevelName");
		saveVersion = nbttagcompound.getInteger("version");
		if (nbttagcompound.hasKey("Player")) {
			field_22313_h = nbttagcompound.getCompoundTag("Player");
			field_22312_i = field_22313_h.getInteger("Dimension");
		}
	}

	public WorldInfo(long l, String s) {
		randomSeed = l;
		levelName = s;
	}

	public WorldInfo(WorldInfo worldinfo) {
		randomSeed = worldinfo.randomSeed;
		spawnX = worldinfo.spawnX;
		spawnY = worldinfo.spawnY;
		spawnZ = worldinfo.spawnZ;
		worldTime = worldinfo.worldTime;
		field_22315_f = worldinfo.field_22315_f;
		sizeOnDisk = worldinfo.sizeOnDisk;
		field_22313_h = worldinfo.field_22313_h;
		field_22312_i = worldinfo.field_22312_i;
		levelName = worldinfo.levelName;
		saveVersion = worldinfo.saveVersion;
	}

	public NBTTagCompound func_22299_a() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		func_22291_a(nbttagcompound, field_22313_h);
		return nbttagcompound;
	}

	public NBTTagCompound func_22305_a(List list) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		EntityPlayer entityplayer = null;
		NBTTagCompound nbttagcompound1 = null;
		if (list.size() > 0) {
			entityplayer = (EntityPlayer) list.get(0);
		}
		if (entityplayer != null) {
			nbttagcompound1 = new NBTTagCompound();
			entityplayer.writeToNBT(nbttagcompound1);
		}
		func_22291_a(nbttagcompound, nbttagcompound1);
		return nbttagcompound;
	}

	private void func_22291_a(NBTTagCompound nbttagcompound, NBTTagCompound nbttagcompound1) {
		nbttagcompound.setLong("RandomSeed", randomSeed);
		nbttagcompound.setInteger("SpawnX", spawnX);
		nbttagcompound.setInteger("SpawnY", spawnY);
		nbttagcompound.setInteger("SpawnZ", spawnZ);
		nbttagcompound.setLong("Time", worldTime);
		nbttagcompound.setLong("SizeOnDisk", sizeOnDisk);
		nbttagcompound.setLong("LastPlayed", System.currentTimeMillis());
		nbttagcompound.setString("LevelName", levelName);
		nbttagcompound.setInteger("version", saveVersion);
		if (nbttagcompound1 != null) {
			nbttagcompound.setCompoundTag("Player", nbttagcompound1);
		}
	}

	public long getRandomSeed() {
		return randomSeed;
	}

	public int func_22293_c() {
		return spawnX;
	}

	public int func_22295_d() {
		return spawnY;
	}

	public int func_22300_e() {
		return spawnZ;
	}

	public long getWorldTime() {
		return worldTime;
	}

	public long func_22306_g() {
		return sizeOnDisk;
	}

	public NBTTagCompound func_22303_h() {
		return field_22313_h;
	}

	public int func_22290_i() {
		return field_22312_i;
	}

	public void func_22294_a(int i) {
		spawnX = i;
	}

	public void func_22308_b(int i) {
		spawnY = i;
	}

	public void func_22298_c(int i) {
		spawnZ = i;
	}

	public void setWorldTime(long l) {
		worldTime = l;
	}

	public void func_22297_b(long l) {
		sizeOnDisk = l;
	}

	public void func_22309_a(NBTTagCompound nbttagcompound) {
		field_22313_h = nbttagcompound;
	}

	public void func_22292_a(int i, int j, int k) {
		spawnX = i;
		spawnY = j;
		spawnZ = k;
	}

	public String getWorldName() {
		return levelName;
	}

	public void func_22287_a(String s) {
		levelName = s;
	}

	public int func_22296_k() {
		return saveVersion;
	}

	public void func_22289_d(int i) {
		saveVersion = i;
	}

	public long func_22301_l() {
		return field_22315_f;
	}

	private long randomSeed;
	private int spawnX;
	private int spawnY;
	private int spawnZ;
	private long worldTime;
	private long field_22315_f;
	private long sizeOnDisk;
	private NBTTagCompound field_22313_h;
	private int field_22312_i;
	private String levelName;
	private int saveVersion;
}
