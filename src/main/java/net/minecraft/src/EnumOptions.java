package net.minecraft.src;

import net.lax1dude.eaglercraft.compat.CompatEnum;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public final class EnumOptions extends CompatEnum {

	public static EnumOptions[] values() {
		return (EnumOptions[]) field_20141_n.clone();
	}

	public static EnumOptions valueOf(String s) {
		return (EnumOptions) CompatEnum.valueOf(EnumOptions.class, s);
	}

	public static EnumOptions func_20137_a(int i) {
		EnumOptions aenumoptions[] = values();
		int j = aenumoptions.length;
		for (int k = 0; k < j; k++) {
			EnumOptions enumoptions = aenumoptions[k];
			if (enumoptions.returnEnumOrdinal() == i) {
				return enumoptions;
			}
		}

		return null;
	}

	private EnumOptions(String s, int i, String s1, boolean flag, boolean flag1) {
		super(s, i);
		enumString = s1;
		enumFloat = flag;
		enumBoolean = flag1;
	}

	public boolean getEnumFloat() {
		return enumFloat;
	}

	public boolean getEnumBoolean() {
		return enumBoolean;
	}

	public int returnEnumOrdinal() {
		return ordinal();
	}

	public String getEnumString() {
		return enumString;
	}

	public static final EnumOptions MUSIC;
	public static final EnumOptions SOUND;
	public static final EnumOptions INVERT_MOUSE;
	public static final EnumOptions SENSITIVITY;
	public static final EnumOptions RENDER_DISTANCE;
	public static final EnumOptions VIEW_BOBBING;
	public static final EnumOptions ANAGLYPH;
	public static final EnumOptions LIMIT_FRAMERATE;
	public static final EnumOptions DIFFICULTY;
	public static final EnumOptions GRAPHICS;
	public static final EnumOptions AMBIENT_OCCLUSION;
	public static final EnumOptions ANTIALIASING;
	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;
	private static final EnumOptions field_20141_n[]; /* synthetic field */

	static {
		MUSIC = new EnumOptions("MUSIC", 0, "options.music", true, false);
		SOUND = new EnumOptions("SOUND", 1, "options.sound", true, false);
		INVERT_MOUSE = new EnumOptions("INVERT_MOUSE", 2, "options.invertMouse", false, true);
		SENSITIVITY = new EnumOptions("SENSITIVITY", 3, "options.sensitivity", true, false);
		RENDER_DISTANCE = new EnumOptions("RENDER_DISTANCE", 4, "options.renderDistance", false, false);
		VIEW_BOBBING = new EnumOptions("VIEW_BOBBING", 5, "options.viewBobbing", false, true);
		ANAGLYPH = new EnumOptions("ANAGLYPH", 6, "options.anaglyph", false, true);
		LIMIT_FRAMERATE = new EnumOptions("LIMIT_FRAMERATE", 7, "options.limitFramerate", false, true);
		DIFFICULTY = new EnumOptions("DIFFICULTY", 8, "options.difficulty", false, false);
		GRAPHICS = new EnumOptions("GRAPHICS", 9, "options.graphics", false, false);
		AMBIENT_OCCLUSION = new EnumOptions("AMBIENT_OCCLUSION", 10, "options.ao", false, true);
		ANTIALIASING = new EnumOptions("ANTIALIASING", 11, "options.framebufferAntialias", false, false);
		field_20141_n = (new EnumOptions[] { MUSIC, SOUND, INVERT_MOUSE, SENSITIVITY, RENDER_DISTANCE, VIEW_BOBBING,
				ANAGLYPH, LIMIT_FRAMERATE, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, ANTIALIASING });
	}
}
