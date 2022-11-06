package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;
import java.util.HashMap;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglercraftRandom;

public class SoundManager {

	private GameSettings options;
	private HashMap<String,Integer> sounddefinitions;
	private EaglercraftRandom soundrandom;

	public SoundManager() {
		this.sounddefinitions = null;
		this.soundrandom = new EaglercraftRandom();
	}

	public void loadSoundSettings(GameSettings gamesettings) {
		this.options = gamesettings;
		if(this.sounddefinitions == null) {
			this.sounddefinitions = new HashMap();
			try {
				NBTTagCompound file = CompressedStreamTools.func_1138_a(EaglerAdapter.loadResource("/sounds/sounds.dat"));
				EaglerAdapter.setPlaybackOffsetDelay(file.hasKey("playbackOffset") ? file.getFloat("playbackOffset") : 0.03f);
				NBTTagList l = file.getTagList("sounds");
				int c = l.tagCount();
				for(int i = 0; i < c; i++) {
					NBTTagCompound cc = (NBTTagCompound)l.tagAt(i);
					this.sounddefinitions.put(cc.getString("e"), (int)cc.getByte("c") & 0xFF);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void tryToSetLibraryAndCodecs() {
		/*
		 * try { float f = options.soundVolume; float f1 = options.musicVolume;
		 * options.soundVolume = 0.0F; options.musicVolume = 0.0F;
		 * options.saveOptions();
		 * SoundSystemConfig.addLibrary(paulscode.sound.libraries.LibraryLWJGLOpenAL.
		 * class); SoundSystemConfig.setCodec("ogg",
		 * paulscode.sound.codecs.CodecJOrbis.class); SoundSystemConfig.setCodec("mus",
		 * CodecMus.class); SoundSystemConfig.setCodec("wav",
		 * paulscode.sound.codecs.CodecWav.class); sndSystem = new SoundSystem();
		 * options.soundVolume = f; options.musicVolume = f1; options.saveOptions(); }
		 * catch(Throwable throwable) { throwable.printStackTrace();
		 * System.err.println("error linking with the LibraryJavaSound plug-in"); }
		 */
	}

	public void onSoundOptionsChanged() {
		
	}

	public void playRandomMusicIfReady() {
		
	}

	public void func_338_a(EntityLiving par1EntityLiving, float f) {
		if(par1EntityLiving == null) {
			EaglerAdapter.setListenerPos(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
		}else {
			double x = par1EntityLiving.prevPosX + (par1EntityLiving.posX - par1EntityLiving.prevPosX) * f;
			double y = par1EntityLiving.prevPosY + (par1EntityLiving.posY - par1EntityLiving.prevPosY) * f;
			double z = par1EntityLiving.prevPosZ + (par1EntityLiving.posZ - par1EntityLiving.prevPosZ) * f;
			double pitch = par1EntityLiving.prevRotationPitch + (par1EntityLiving.rotationPitch - par1EntityLiving.prevRotationPitch) * f;
			double yaw = par1EntityLiving.prevRotationYaw + (par1EntityLiving.rotationYaw - par1EntityLiving.prevRotationYaw) * f;
			
			try {
				EaglerAdapter.setListenerPos((float)x, (float)y, (float)z, (float)par1EntityLiving.motionX, (float)par1EntityLiving.motionY, (float)par1EntityLiving.motionZ, (float)pitch, (float)yaw);
			}catch(Throwable t) {
				System.err.println("AudioListener f***ed up again");
			}
		}
	}

	public void func_331_a(String s, float f, float f1, float f2, float f3, float f4) {
		playSound(s, f, f1, f2, f3, f4);
	}

	public void playSound(String s, float par2, float par3, float par4, float par5, float par6) {
		float v = par5 * this.options.soundVolume;
		if(v > 0.0F) {
			Integer ct = this.sounddefinitions.get(s);
			if(ct != null) {
				int c = ct.intValue();
				String path;
				if(c <= 1) {
					path = "/sounds/"+s.replace('.', '/')+".mp3";
				}else {
					int r = soundrandom.nextInt(c) + 1;
					path = "/sounds/"+s.replace('.', '/')+r+".mp3";
				}
				EaglerAdapter.beginPlayback(path, par2, par3, par4, v, par6);
			}else {
				System.err.println("unregistered sound effect: "+s);
			}
		}
	}

	public void func_337_a(String par1Str, float par2, float par3) {
		float v = par3 * this.options.soundVolume;
		if(v > 0.0F) {
			Integer ct = this.sounddefinitions.get(par1Str);
			if(ct != null) {
				int c = ct.intValue();
				String path;
				if(c <= 1) {
					path = "/sounds/"+par1Str.replace('.', '/')+".mp3";
				}else {
					int r = soundrandom.nextInt(c) + 1;
					path = "/sounds/"+par1Str.replace('.', '/')+r+".mp3";
				}
				EaglerAdapter.beginPlaybackStatic(path, v, par3);
			}else {
				System.err.println("unregistered sound effect: "+par1Str);
			}
		}
	}
}
