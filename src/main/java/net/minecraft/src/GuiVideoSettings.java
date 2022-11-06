package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiVideoSettings extends GuiScreen {

	public GuiVideoSettings(GuiScreen guiscreen, GameSettings gamesettings) {
		field_22107_a = "Video Settings";
		field_22110_h = guiscreen;
		field_22109_i = gamesettings;
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		field_22107_a = stringtranslate.translateKey("options.videoTitle");
		int i = 0;
		EnumOptions aenumoptions[] = field_22108_k;
		int j = aenumoptions.length;
		for (int k = 0; k < j; k++) {
			EnumOptions enumoptions = aenumoptions[k];
			if (!enumoptions.getEnumFloat()) {
				controlList.add(new GuiSmallButton(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160,
						height / 6 + 24 * (i >> 1), enumoptions, field_22109_i.getKeyBinding(enumoptions)));
			} else {
				controlList.add(new GuiSlider(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160,
						height / 6 + 24 * (i >> 1), enumoptions, field_22109_i.getKeyBinding(enumoptions),
						field_22109_i.getOptionFloatValue(enumoptions)));
			}
			i++;
		}

		controlList
				.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.translateKey("gui.done")));
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id < 100 && (guibutton instanceof GuiSmallButton)) {
			field_22109_i.setOptionValue(((GuiSmallButton) guibutton).returnEnumOptions(), 1);
			guibutton.displayString = field_22109_i.getKeyBinding(EnumOptions.func_20137_a(guibutton.id));
		}
		if (guibutton.id == 200) {
			mc.gameSettings.saveOptions();
			mc.displayGuiScreen(field_22110_h);
		}
	}

	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, field_22107_a, width / 2, 20, 0xffffff);
		super.drawScreen(i, j, f);
	}

	private GuiScreen field_22110_h;
	protected String field_22107_a;
	private GameSettings field_22109_i;
	private static EnumOptions field_22108_k[];

	static {
		field_22108_k = (new EnumOptions[] { EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE,
				EnumOptions.LIMIT_FRAMERATE, EnumOptions.ANAGLYPH, EnumOptions.VIEW_BOBBING,
				EnumOptions.AMBIENT_OCCLUSION, EnumOptions.ANTIALIASING });
	}
}
