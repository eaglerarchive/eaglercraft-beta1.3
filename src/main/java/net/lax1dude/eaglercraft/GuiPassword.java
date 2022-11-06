package net.lax1dude.eaglercraft;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiTextField;

public class GuiPassword extends GuiTextField {
	
	private int placeholderLength;

	public GuiPassword(FontRenderer fontRenderer, int n, int n2, int n3, int n4, String string) {
		super(fontRenderer, n, n2, n3, n4, string);
		placeholderLength = -1;
	}
	
	public GuiPassword(FontRenderer fontRenderer, int n, int n2, int n3, int n4, int placeholderLength) {
		super(fontRenderer, n, n2, n3, n4, null);
		this.placeholderLength = placeholderLength;
	}
	
	public void drawTextBox() {
		this.drawRect(this.field_22079_d - 1, this.field_22078_e - 1, this.field_22079_d + this.field_22077_f + 1, this.field_22078_e + this.field_22076_g + 1, -6250336);
		this.drawRect(this.field_22079_d, this.field_22078_e, this.field_22079_d + this.field_22077_f, this.field_22078_e + this.field_22076_g, -16777216);
		String str = obfuscate(placeholderLength > 0 ? placeholderLength : this.field_22075_h.length());
		if (this.field_22081_b) {
			boolean bl = this.field_22082_a && this.ticks / 6 % 2 == 0;
			this.drawString(this.field_22080_c, str + (bl ? "_" : ""), this.field_22079_d + 4, this.field_22078_e + (this.field_22076_g - 8) / 2, 0xE0E0E0);
		} else {
			this.drawString(this.field_22080_c, str, this.field_22079_d + 4, this.field_22078_e + (this.field_22076_g - 8) / 2, 0x707070);
		}
	}
	
	public void handleKeyboardInput(char c, int n) {
		if (!this.field_22081_b || !this.field_22082_a) {
			return;
		}
		if(placeholderLength > 0) {
			placeholderLength = -1;
			field_22075_h = "";
		}
		super.handleKeyboardInput(c, n);
	}
	
	private static String obfuscate(int len) {
		char[] stars = new char[len];
		for(int i = 0; i < stars.length; ++i) {
			stars[i] = '*';
		}
		return new String(stars);
	}
	
	public boolean isShowingPlaceholder() {
		return placeholderLength != -1;
	}

}
