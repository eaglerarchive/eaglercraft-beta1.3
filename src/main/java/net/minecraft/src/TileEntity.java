package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TileEntity {

	public TileEntity() {
	}

	private static void addMapping(Class class1, Supplier<TileEntity> construct, String s) {
		if (classToNameMap.containsKey(s)) {
			throw new IllegalArgumentException((new StringBuilder()).append("Duplicate id: ").append(s).toString());
		} else {
			nameToClassMap.put(s, construct);
			classToNameMap.put(class1, s);
			return;
		}
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		xCoord = nbttagcompound.getInteger("x");
		yCoord = nbttagcompound.getInteger("y");
		zCoord = nbttagcompound.getInteger("z");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		String s = (String) classToNameMap.get(getClass());
		if (s == null) {
			throw new RuntimeException((new StringBuilder()).append(getClass())
					.append(" is missing a mapping! This is a bug!").toString());
		} else {
			nbttagcompound.setString("id", s);
			nbttagcompound.setInteger("x", xCoord);
			nbttagcompound.setInteger("y", yCoord);
			nbttagcompound.setInteger("z", zCoord);
			return;
		}
	}

	public void updateEntity() {
	}

	public static TileEntity createAndLoadEntity(NBTTagCompound nbttagcompound) {
		TileEntity tileentity = null;
		try {
			Supplier<TileEntity> class1 = (Supplier<TileEntity>) nameToClassMap.get(nbttagcompound.getString("id"));
			if (class1 != null) {
				tileentity = class1.get();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if (tileentity != null) {
			tileentity.readFromNBT(nbttagcompound);
		} else {
			System.out.println((new StringBuilder()).append("Skipping TileEntity with id ")
					.append(nbttagcompound.getString("id")).toString());
		}
		return tileentity;
	}

	public int getBlockMetadata() {
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	public void onInventoryChanged() {
		if (worldObj != null) {
			worldObj.func_698_b(xCoord, yCoord, zCoord, this);
		}
	}

	public double getDistanceFrom(double d, double d1, double d2) {
		double d3 = ((double) xCoord + 0.5D) - d;
		double d4 = ((double) yCoord + 0.5D) - d1;
		double d5 = ((double) zCoord + 0.5D) - d2;
		return d3 * d3 + d4 * d4 + d5 * d5;
	}

	public Block getBlockType() {
		return Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)];
	}

	static Class _mthclass$(String s) {
		try {
			return Class.forName(s);
		} catch (ClassNotFoundException classnotfoundexception) {
			throw new NoClassDefFoundError(classnotfoundexception.getMessage());
		}
	}

	private static Map nameToClassMap = new HashMap();
	private static Map classToNameMap = new HashMap();
	public World worldObj;
	public int xCoord;
	public int yCoord;
	public int zCoord;

	static {
		addMapping(TileEntityFurnace.class, () -> new TileEntityFurnace(), "Furnace");
		addMapping(TileEntityChest.class, () -> new TileEntityChest(), "Chest");
		addMapping(TileEntityDispenser.class, () -> new TileEntityDispenser(), "Trap");
		addMapping(TileEntitySign.class, () -> new TileEntitySign(), "Sign");
		addMapping(TileEntityMobSpawner.class, () -> new TileEntityMobSpawner(), "MobSpawner");
		addMapping(TileEntityNote.class, () -> new TileEntityNote(), "Music");
	}
}
