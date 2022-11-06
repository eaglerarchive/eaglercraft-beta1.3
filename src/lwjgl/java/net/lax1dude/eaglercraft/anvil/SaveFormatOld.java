package net.lax1dude.eaglercraft.anvil;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.SaveFormatComparator;
import net.minecraft.src.WorldInfo;

public class SaveFormatOld implements ISaveFormat {

	public SaveFormatOld(File file) {
		if (!file.exists()) {
			file.mkdirs();
		}
		field_22180_a = file;
	}

	public String formatName() {
		return "Old Format";
	}

	public List getWorldList(IProgressUpdate progress) {
		ArrayList arraylist = new ArrayList();
		for (int i = 0; i < 5; i++) {
			String s = (new StringBuilder()).append("World").append(i + 1).toString();
			WorldInfo worldinfo = getWorldInfoForWorld(s);
			if (worldinfo != null) {
				arraylist.add(
						new SaveFormatComparator(s, "", worldinfo.func_22301_l(), worldinfo.func_22306_g(), false));
			}
		}

		return arraylist;
	}

	public void flushCache() {
	}

	public WorldInfo getWorldInfoForWorld(String s) {
		File file = new File(field_22180_a, s);
		if (!file.exists()) {
			return null;
		}
		File file1 = new File(file, "level.dat");
		if (file1.exists()) {
			try {
				NBTTagCompound nbttagcompound = CompressedStreamTools.func_1138_a(new FileInputStream(file1));
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
				return new WorldInfo(nbttagcompound1);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	public void renameWorldData(String s, String s1) {
		File file = new File(field_22180_a, s);
		if (!file.exists()) {
			return;
		}
		File file1 = new File(file, "level.dat");
		if (file1.exists()) {
			try {
				NBTTagCompound nbttagcompound = CompressedStreamTools.func_1138_a(new FileInputStream(file1));
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
				nbttagcompound1.setString("LevelName", s1);
				CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound, new FileOutputStream(file1));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public void deleteWorldByDirectory(String s, IProgressUpdate iprogressupdate) {
		File file = new File(field_22180_a, s);
		if (!file.exists()) {
			return;
		} else {
			func_22179_a(file.listFiles());
			file.delete();
			return;
		}
	}

	protected static void func_22179_a(File afile[]) {
		for (int i = 0; i < afile.length; i++) {
			if (afile[i].isDirectory()) {
				func_22179_a(afile[i].listFiles());
			}
			afile[i].delete();
		}

	}

	public ISaveHandler loadWorldHandler(String s, boolean flag) {
		return new SaveHandler(field_22180_a, s, flag);
	}

	public boolean worldNeedsConvert_maybe(String s) {
		return false;
	}

	public boolean convertSave(String s, IProgressUpdate iprogressupdate) {
		return false;
	}

	protected final File field_22180_a;
}
