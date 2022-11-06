package net.lax1dude.eaglercraft.beta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;

public class EaglercraftSaveHandler implements ISaveHandler {
	
	public final String directory;
	
	public EaglercraftSaveHandler(String dir) {
		this.directory = dir;
	}

	@Override
	public WorldInfo getWorldInfo() {
		byte[] file = EaglerAdapter.readFile(directory + "/lvl");
		if(file != null) {
			try {
				NBTBase nbt = NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(file)));
				if(nbt instanceof NBTTagCompound) {
					return new WorldInfo((NBTTagCompound)nbt);
				}
			} catch (IOException e) {
				System.err.println("Failed to load world data for '" + directory + "/lvl'");
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void checkSessionLock() {
	}

	@Override
	public IChunkLoader getChunkLoader(WorldProvider worldprovider) {
		if(worldprovider instanceof WorldProviderHell) {
			return new EaglercraftChunkLoader(directory + "/c1");
		}else {
			return new EaglercraftChunkLoader(directory + "/c0");
		}
	}

	@Override
	public void saveWorldAndPlayer(WorldInfo worldinfo, List list) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(8192);
		DataOutputStream ds = new DataOutputStream(out);
		try {
			NBTBase.writeTag(worldinfo.func_22305_a(list), ds);
		} catch (IOException e) {
			System.err.println("Failed to serialize world data for '" + directory + "/lvl'");
			e.printStackTrace();
			return;
		}
		EaglerAdapter.writeFile(directory + "/lvl", out.toByteArray());
	}

	@Override
	public void saveWorldInfo(WorldInfo worldinfo) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(8192);
		DataOutputStream ds = new DataOutputStream(out);
		try {
			NBTBase.writeTag(worldinfo.func_22299_a(), ds);
		} catch (IOException e) {
			System.err.println("Failed to serialize world data for '" + directory + "/lvl'");
			e.printStackTrace();
			return;
		}
		EaglerAdapter.writeFile(directory + "/lvl", out.toByteArray());
		
	}

}
