package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.LocalStorageManager;
import net.minecraft.client.Minecraft;

public class GameSettings {

	public GameSettings(Minecraft mcc) {
		musicVolume = 1.0F;
		soundVolume = 1.0F;
		mouseSensitivity = 0.5F;
		invertMouse = false;
		renderDistance = 2;
		viewBobbing = true;
		anaglyph = false;
		limitFramerate = false;
		fancyGraphics = false;
		field_22278_j = false;
		antialiasing = 1;
		lastPasswordHash = "null";
		lastPasswordLength = -1;
		keyBindForward = new KeyBinding("key.forward", 17);
		keyBindLeft = new KeyBinding("key.left", 30);
		keyBindBack = new KeyBinding("key.back", 31);
		keyBindRight = new KeyBinding("key.right", 32);
		keyBindJump = new KeyBinding("key.jump", 57);
		keyBindInventory = new KeyBinding("key.inventory", 18);
		keyBindDrop = new KeyBinding("key.drop", 16);
		keyBindChat = new KeyBinding("key.chat", 20);
		keyBindSneak = new KeyBinding("key.sneak", 42);
		keyBindFunction = new KeyBinding("key.function", 33);
		keyBindings = (new KeyBinding[] { keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump,
				keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindFunction });
		difficulty = 1;
		field_22277_y = false;
		thirdPersonView = false;
		showDebugInfo = false;
		lastServer = "";
		texturePack = "Default";
		field_22275_C = false;
		field_22274_D = false;
		field_22273_E = false;
		field_22272_F = 1.0F;
		field_22271_G = 1.0F;
		mc = mcc;
		loadOptions();
	}

	public String getKeyBindingDescription(int i) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		return stringtranslate.translateKey(keyBindings[i].keyDescription);
	}

	public String getOptionDisplayString(int i) {
		return EaglerAdapter.getKeyName(keyBindings[i].keyCode);
	}

	public void setKeyBinding(int i, int j) {
		keyBindings[i].keyCode = j;
		saveOptions();
	}

	public void setOptionFloatValue(EnumOptions enumoptions, float f) {
		if (enumoptions == EnumOptions.MUSIC) {
			musicVolume = f;
			mc.sndManager.onSoundOptionsChanged();
		}
		if (enumoptions == EnumOptions.SOUND) {
			soundVolume = f;
			mc.sndManager.onSoundOptionsChanged();
		}
		if (enumoptions == EnumOptions.SENSITIVITY) {
			mouseSensitivity = f;
		}
	}

	public void setOptionValue(EnumOptions enumoptions, int i) {
		if (enumoptions == EnumOptions.INVERT_MOUSE) {
			invertMouse = !invertMouse;
		}
		if (enumoptions == EnumOptions.RENDER_DISTANCE) {
			renderDistance = renderDistance + i & 3;
		}
		if (enumoptions == EnumOptions.VIEW_BOBBING) {
			viewBobbing = !viewBobbing;
		}
		if (enumoptions == EnumOptions.ANAGLYPH) {
			anaglyph = !anaglyph;
			mc.renderEngine.refreshTextures();
		}
		if (enumoptions == EnumOptions.LIMIT_FRAMERATE) {
			limitFramerate = !limitFramerate;
		}
		if (enumoptions == EnumOptions.DIFFICULTY) {
			difficulty = difficulty + i & 3;
		}
		if (enumoptions == EnumOptions.GRAPHICS) {
			fancyGraphics = !fancyGraphics;
			if(mc.renderGlobal != null) {
				mc.renderGlobal.loadRenderers();
			}
		}
		if (enumoptions == EnumOptions.AMBIENT_OCCLUSION) {
			field_22278_j = !field_22278_j;
			if(mc.renderGlobal != null) {
				mc.renderGlobal.loadRenderers();
			}
		}
		if (enumoptions == EnumOptions.ANTIALIASING) {
			antialiasing = (antialiasing + i) % 3;
		}
		saveOptions();
	}

	public float getOptionFloatValue(EnumOptions enumoptions) {
		if (enumoptions == EnumOptions.MUSIC) {
			return musicVolume;
		}
		if (enumoptions == EnumOptions.SOUND) {
			return soundVolume;
		}
		if (enumoptions == EnumOptions.SENSITIVITY) {
			return mouseSensitivity;
		} else {
			return 0.0F;
		}
	}

	public boolean getOptionOrdinalValue(EnumOptions enumoptions) {
		switch (EnumOptionsMappingHelper.enumOptionsMappingHelperArray[enumoptions.ordinal()]) {
		case 1: // '\001'
			return invertMouse;

		case 2: // '\002'
			return viewBobbing;

		case 3: // '\003'
			return anaglyph;

		case 4: // '\004'
			return limitFramerate;

		case 5: // '\005'
			return field_22278_j;
		}
		return false;
	}

	public String getKeyBinding(EnumOptions enumoptions) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		String s = (new StringBuilder()).append(stringtranslate.translateKey(enumoptions.getEnumString())).append(": ")
				.toString();
		if (enumoptions.getEnumFloat()) {
			float f = getOptionFloatValue(enumoptions);
			if (enumoptions == EnumOptions.SENSITIVITY) {
				if (f == 0.0F) {
					return (new StringBuilder()).append(s)
							.append(stringtranslate.translateKey("options.sensitivity.min")).toString();
				}
				if (f == 1.0F) {
					return (new StringBuilder()).append(s)
							.append(stringtranslate.translateKey("options.sensitivity.max")).toString();
				} else {
					return (new StringBuilder()).append(s).append((int) (f * 200F)).append("%").toString();
				}
			}
			if (f == 0.0F) {
				return (new StringBuilder()).append(s).append(stringtranslate.translateKey("options.off")).toString();
			} else {
				return (new StringBuilder()).append(s).append((int) (f * 100F)).append("%").toString();
			}
		}
		if (enumoptions.getEnumBoolean()) {
			boolean flag = getOptionOrdinalValue(enumoptions);
			if (flag) {
				return (new StringBuilder()).append(s).append(stringtranslate.translateKey("options.on")).toString();
			} else {
				return (new StringBuilder()).append(s).append(stringtranslate.translateKey("options.off")).toString();
			}
		}
		if (enumoptions == EnumOptions.RENDER_DISTANCE) {
			return (new StringBuilder()).append(s)
					.append(stringtranslate.translateKey(RENDER_DISTANCES[renderDistance])).toString();
		}
		if (enumoptions == EnumOptions.DIFFICULTY) {
			return (new StringBuilder()).append(s).append(stringtranslate.translateKey(DIFFICULTIES[difficulty]))
					.toString();
		}
		if (enumoptions == EnumOptions.ANTIALIASING) {
			return (new StringBuilder()).append(s).append(stringtranslate.translateKey(ANTIALIASING_MODES[antialiasing]))
					.toString();
		}
		if (enumoptions == EnumOptions.GRAPHICS) {
			if (fancyGraphics) {
				return (new StringBuilder()).append(s).append(stringtranslate.translateKey("options.graphics.fancy"))
						.toString();
			} else {
				return (new StringBuilder()).append(s).append(stringtranslate.translateKey("options.graphics.fast"))
						.toString();
			}
		} else {
			return s;
		}
	}

	public void loadOptions() {
		NBTTagCompound yee = LocalStorageManager.gameSettingsStorage;
		if(!yee.hasNoTags()) {
			if(yee.hasKey("musicVolume")) musicVolume = yee.getFloat("musicVolume");
			if(yee.hasKey("soundVolume")) soundVolume = yee.getFloat("soundVolume");
			if(yee.hasKey("mouseSensitivity")) mouseSensitivity = yee.getFloat("mouseSensitivity");
			if(yee.hasKey("invertMouse")) invertMouse = yee.getBoolean("invertMouse");
			if(yee.hasKey("renderDistance")) renderDistance = (int)yee.getByte("renderDistance") & 0xFF;
			if(yee.hasKey("viewBobbing")) viewBobbing = yee.getBoolean("viewBobbing");
			if(yee.hasKey("anaglyph")) anaglyph = yee.getBoolean("anaglyph");
			if(yee.hasKey("limitFramerate")) limitFramerate = yee.getBoolean("limitFramerate");
			if(yee.hasKey("difficulty")) difficulty = (int)yee.getByte("difficulty") & 0xFF;
			if(yee.hasKey("fancyGraphics")) fancyGraphics = yee.getBoolean("fancyGraphics");
			if(yee.hasKey("ao")) field_22278_j = yee.getBoolean("ao");
			if(yee.hasKey("antialiasing")) antialiasing = (int)yee.getByte("antialiasing") & 0xFF;
			if(yee.hasKey("lastServer")) lastServer = yee.getString("lastServer");
			if(yee.hasKey("lastPasswordHashed")) lastPasswordHash = yee.getString("lastPasswordHashed");
			if(yee.hasKey("lastPasswordLength")) lastPasswordLength = yee.getInteger("lastPasswordLength");
			for(int i = 0; i < keyBindings.length; ++i) {
				String k = "key_" + keyBindings[i].keyDescription;
				if(yee.hasKey(k)) keyBindings[i].keyCode = (int)yee.getShort(k) & 0xFFFF;
			}
		}
	}

	public void saveOptions() {
		NBTTagCompound yee = LocalStorageManager.gameSettingsStorage;
		yee.setFloat("musicVolume", musicVolume);
		yee.setFloat("soundVolume", soundVolume);
		yee.setFloat("mouseSensitivity", mouseSensitivity);
		yee.setBoolean("invertMouse", invertMouse);
		yee.setByte("renderDistance", (byte)renderDistance);
		yee.setBoolean("viewBobbing", viewBobbing);
		yee.setBoolean("anaglyph", anaglyph);
		yee.setBoolean("limitFramerate", limitFramerate);
		yee.setByte("difficulty", (byte)difficulty);
		yee.setBoolean("fancyGraphics", fancyGraphics);
		yee.setBoolean("ao", field_22278_j);
		yee.setByte("antialiasing", (byte)antialiasing);
		yee.setString("lastServer", lastServer);
		yee.setString("texturePack", texturePack);
		yee.setString("lastPasswordHashed", lastPasswordHash);
		yee.setInteger("lastPasswordLength", lastPasswordLength);
		for(int i = 0; i < keyBindings.length; ++i) {
			String k = "key_" + keyBindings[i].keyDescription;
			yee.setShort(k, (short)keyBindings[i].keyCode);
		}
		LocalStorageManager.saveStorageG();
	}

	private static final String RENDER_DISTANCES[] = { "options.renderDistance.far", "options.renderDistance.normal",
			"options.renderDistance.short", "options.renderDistance.tiny" };
	private static final String DIFFICULTIES[] = { "options.difficulty.peaceful", "options.difficulty.easy",
			"options.difficulty.normal", "options.difficulty.hard" };
	private static final String ANTIALIASING_MODES[] = { "options.framebufferAntialias.none", "options.framebufferAntialias.auto",
			"options.framebufferAntialias.fxaa" };
	public float musicVolume;
	public float soundVolume;
	public float mouseSensitivity;
	public boolean invertMouse;
	public int renderDistance;
	public boolean viewBobbing;
	public boolean anaglyph;
	public boolean limitFramerate;
	public boolean fancyGraphics;
	public boolean field_22278_j;
	public int antialiasing;
	public KeyBinding keyBindForward;
	public KeyBinding keyBindLeft;
	public KeyBinding keyBindBack;
	public KeyBinding keyBindRight;
	public KeyBinding keyBindJump;
	public KeyBinding keyBindInventory;
	public KeyBinding keyBindDrop;
	public KeyBinding keyBindChat;
	public KeyBinding keyBindSneak;
	public KeyBinding keyBindFunction;
	public KeyBinding keyBindings[];
	protected Minecraft mc;
	public int difficulty;
	public boolean field_22277_y;
	public boolean thirdPersonView;
	public boolean showDebugInfo;
	public String lastServer;
	public String texturePack;
	public boolean field_22275_C;
	public boolean field_22274_D;
	public boolean field_22273_E;
	public float field_22272_F;
	public float field_22271_G;
	public String lastPasswordHash;
	public int lastPasswordLength;

	public static String currentSessionPasswordPlaintext = null;

}
