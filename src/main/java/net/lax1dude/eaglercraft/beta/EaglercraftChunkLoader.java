package net.lax1dude.eaglercraft.beta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.minecraft.src.Chunk;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NibbleArray;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class EaglercraftChunkLoader implements IChunkLoader {
	
	public final String directory;
	
	public EaglercraftChunkLoader(String dir) {
		this.directory = dir;
	}

	@Override
	public Chunk loadChunk(World world, int x, int z) {
		String name = getChunkPath(x, z);
		String path = directory + "/" + name;
		byte[] dat = EaglerAdapter.readFile(path);
		if(dat != null) {
			try {
				NBTTagCompound nbt = (NBTTagCompound) NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(dat)));
				int xx = nbt.getInteger("xPos");
				int zz = nbt.getInteger("zPos");
				if(x != xx || z != zz) {
					String name2 = getChunkPath(xx, zz);
					System.err.println("The chunk file '" + name + "' was supposed to be at [" + x + ", " + z + "], but the file contained a chunk from [" + xx + ", " + zz +
							"]. It's data will be moved to file '" + name2 + "', and a new empty chunk will be created for file '" + name + "' for [" + x + ", " + z + "]");
					EaglerAdapter.renameFile(path, directory + "/" + name2);
					return null;
				}
				
				return loadChunkIntoWorldFromCompound(world, nbt);
			} catch (IOException e) {
				System.err.println("Corrupt chunk '" + name + "' was found at: [" + x + ", " + z + "]");
				System.err.println("The file will be deleted");
				EaglerAdapter.deleteFile(path);
				e.printStackTrace();
				return null;
			}
		}else {
			return null;
		}
	}

	@Override
	public void saveChunk(World world, Chunk chunk) {
		NBTTagCompound toSave = new NBTTagCompound();
		storeChunkInCompound(chunk, world, toSave);
		ByteArrayOutputStream bao = new ByteArrayOutputStream(131072);
		try {
			NBTBase.writeTag(toSave, new DataOutputStream(bao));
		} catch (IOException e) {
			System.err.println("Failed to serialize chunk at [" + chunk.xPosition + ", " + chunk.zPosition + "] to byte array");
			e.printStackTrace();
			return;
		}
		EaglerAdapter.writeFile(directory + "/" + getChunkPath(chunk.xPosition, chunk.zPosition), bao.toByteArray());
	}

	@Override
	public void saveExtraChunkData(World world, Chunk chunk) {
	}

	@Override
	public void func_814_a() {
	}

	@Override
	public void saveExtraData() {
	}
	
	public static final String CHUNK_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String getChunkPath(int x, int z) {
		int unsignedX = x + 30233088;
		int unsignedZ = z + 30233088;
		int radix = CHUNK_CHARS.length();
		char[] path = new char[10];
		for(int i = 0; i < 5; ++i) {
			path[i * 2] = CHUNK_CHARS.charAt(unsignedX % radix);
			unsignedX /= radix;
			path[i * 2 + 1] = CHUNK_CHARS.charAt(unsignedZ % radix);
			unsignedZ /= radix;
		}
		return new String(path);
	}

	public static Chunk loadChunkIntoWorldFromCompound(World world, NBTTagCompound nbttagcompound) {
		int i = nbttagcompound.getInteger("xPos");
		int j = nbttagcompound.getInteger("zPos");
		Chunk chunk = new Chunk(world, i, j);
		chunk.blocks = nbttagcompound.getByteArray("Blocks");
		chunk.data = new NibbleArray(nbttagcompound.getByteArray("Data"));
		chunk.skylightMap = new NibbleArray(nbttagcompound.getByteArray("SkyLight"));
		chunk.blocklightMap = new NibbleArray(nbttagcompound.getByteArray("BlockLight"));
		chunk.heightMap = nbttagcompound.getByteArray("HeightMap");
		chunk.isTerrainPopulated = nbttagcompound.getBoolean("TerrainPopulated");
		if (!chunk.data.isValid()) {
			chunk.data = new NibbleArray(chunk.blocks.length);
		}
		if (chunk.heightMap == null || !chunk.skylightMap.isValid()) {
			chunk.heightMap = new byte[256];
			chunk.skylightMap = new NibbleArray(chunk.blocks.length);
			chunk.func_1024_c();
		}
		if (!chunk.blocklightMap.isValid()) {
			chunk.blocklightMap = new NibbleArray(chunk.blocks.length);
			chunk.func_1014_a();
		}
		NBTTagList nbttaglist = nbttagcompound.getTagList("Entities");
		if (nbttaglist != null) {
			for (int k = 0; k < nbttaglist.tagCount(); k++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(k);
				Entity entity = EntityList.createEntityFromNBT(nbttagcompound1, world);
				chunk.hasEntities = true;
				if (entity != null) {
					chunk.addEntity(entity);
				}
			}
	
		}
		NBTTagList nbttaglist1 = nbttagcompound.getTagList("TileEntities");
		if (nbttaglist1 != null) {
			for (int l = 0; l < nbttaglist1.tagCount(); l++) {
				NBTTagCompound nbttagcompound2 = (NBTTagCompound) nbttaglist1.tagAt(l);
				TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound2);
				if (tileentity != null) {
					chunk.func_1001_a(tileentity);
				}
			}
	
		}
		return chunk;
	}

	public static void storeChunkInCompound(Chunk chunk, World world, NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("xPos", chunk.xPosition);
		nbttagcompound.setInteger("zPos", chunk.zPosition);
		nbttagcompound.setLong("LastUpdate", world.func_22139_r());
		nbttagcompound.setByteArray("Blocks", chunk.blocks);
		nbttagcompound.setByteArray("Data", chunk.data.data);
		nbttagcompound.setByteArray("SkyLight", chunk.skylightMap.data);
		nbttagcompound.setByteArray("BlockLight", chunk.blocklightMap.data);
		nbttagcompound.setByteArray("HeightMap", chunk.heightMap);
		nbttagcompound.setBoolean("TerrainPopulated", chunk.isTerrainPopulated);
		chunk.hasEntities = false;
		NBTTagList nbttaglist = new NBTTagList();
		label0: for (int i = 0; i < chunk.entities.length; i++) {
			Iterator iterator = chunk.entities[i].iterator();
			do {
				if (!iterator.hasNext()) {
					continue label0;
				}
				Entity entity = (Entity) iterator.next();
				chunk.hasEntities = true;
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				if (entity.addEntityID(nbttagcompound1)) {
					nbttaglist.setTag(nbttagcompound1);
				}
			} while (true);
		}
	
		nbttagcompound.setTag("Entities", nbttaglist);
		NBTTagList nbttaglist1 = new NBTTagList();
		NBTTagCompound nbttagcompound2;
		for (Iterator iterator1 = chunk.chunkTileEntityMap.values().iterator(); iterator1.hasNext(); nbttaglist1
				.setTag(nbttagcompound2)) {
			TileEntity tileentity = (TileEntity) iterator1.next();
			nbttagcompound2 = new NBTTagCompound();
			tileentity.writeToNBT(nbttagcompound2);
		}
	
		nbttagcompound.setTag("TileEntities", nbttaglist1);
	}

}
