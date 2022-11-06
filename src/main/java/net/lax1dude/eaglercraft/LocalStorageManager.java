package net.lax1dude.eaglercraft;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;

public class LocalStorageManager {

	public static NBTTagCompound gameSettingsStorage = null;
	public static NBTTagCompound profileSettingsStorage = null;
	
	public static void loadStorage() {
		byte[] g = EaglerAdapter.loadLocalStorage("g");
		byte[] p = EaglerAdapter.loadLocalStorage("p");
		
		if(g != null) {
			try {
				NBTBase t = NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(g)));
				if(t != null && t instanceof NBTTagCompound) {
					gameSettingsStorage = (NBTTagCompound)t;
				}
			}catch(IOException e) {
				;
			}
		}
		
		if(p != null) {
			try {
				NBTBase t = NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(p)));
				if(t != null && t instanceof NBTTagCompound) {
					profileSettingsStorage = (NBTTagCompound)t;
				}
			}catch(IOException e) {
				;
			}
		}

		if(gameSettingsStorage == null) gameSettingsStorage = new NBTTagCompound();
		if(profileSettingsStorage == null) profileSettingsStorage = new NBTTagCompound();
		
	}
	
	public static void saveStorageG() {
		try {
			ByteArrayOutputStream s = new ByteArrayOutputStream();
			NBTBase.writeTag(gameSettingsStorage, new DataOutputStream(s));
			EaglerAdapter.saveLocalStorage("g", s.toByteArray());
		} catch (IOException e) {
			;
		}
	}
	
	public static void saveStorageP() {
		try {
			ByteArrayOutputStream s = new ByteArrayOutputStream();
			NBTBase.writeTag(profileSettingsStorage, new DataOutputStream(s));
			EaglerAdapter.saveLocalStorage("p", s.toByteArray());
		} catch (IOException e) {
			;
		}
	}
	
	public static String dumpConfiguration() {
		try {
			ByteArrayOutputStream s = new ByteArrayOutputStream();
			NBTBase.writeTag(gameSettingsStorage, new DataOutputStream(s));
			return Base64.encodeBase64String(s.toByteArray());
		} catch(Throwable e) {
			return "<error>";
		}
	}

}
