package net.minecraft.src;

// Decompiled with: CFR 0.152
// Class Version: 5
public class GuiTextField extends Gui {
	protected final FontRenderer field_22080_c;
	protected final int field_22079_d;
	protected final int field_22078_e;
	protected final int field_22077_f;
	protected final int field_22076_g;
	protected String field_22075_h;
	protected int maxLength;
	protected int ticks;
	public boolean field_22082_a = false;
	public boolean field_22081_b = true;

	public GuiTextField(FontRenderer fontRenderer, int n, int n2, int n3, int n4, String string) {
		this.field_22080_c = fontRenderer;
		this.field_22079_d = n;
		this.field_22078_e = n2;
		this.field_22077_f = n3;
		this.field_22076_g = n4;
		this.setTextBoxText(string);
	}

	public void setTextBoxText(String string) {
		this.field_22075_h = string;
	}

	public String getTextBoxText() {
		return this.field_22075_h;
	}

	public void onUpdate() {
		++this.ticks;
	}

	public void handleKeyboardInput(char c, int n) {
		if (!this.field_22081_b || !this.field_22082_a) {
			return;
		}
		
		if ((int)c == 16 || (GuiScreen.isCtrlKeyDown() && n == 47)) {
			int n2;
			String string = GuiScreen.getClipboardString();
			if (string == null) {
				string = "";
			}
			if ((n2 = 32 - this.field_22075_h.length()) > string.length()) {
				n2 = string.length();
			}
			if (n2 > 0) {
				this.field_22075_h = this.field_22075_h + string.substring(0, n2);
			}
		}
		if (n == 14 && this.field_22075_h.length() > 0) {
			this.field_22075_h = this.field_22075_h.substring(0, this.field_22075_h.length() - 1);
		}
		if (FontAllowedCharacters.isAllowed(c) >= 0
				&& (this.field_22075_h.length() < this.maxLength || this.maxLength == 0)) {
			this.field_22075_h = this.field_22075_h + c;
		}
	}

	public void handleMouseInput(int n, int n2, int n3) {
		boolean bl;
		boolean bl2 = bl = this.field_22081_b && n >= this.field_22079_d && n < this.field_22079_d + this.field_22077_f
				&& n2 >= this.field_22078_e && n2 < this.field_22078_e + this.field_22076_g;
		if (bl && !this.field_22082_a) {
			this.ticks = 0;
		}
		this.field_22082_a = bl;
	}

	public void drawTextBox() {
		this.drawRect(this.field_22079_d - 1, this.field_22078_e - 1, this.field_22079_d + this.field_22077_f + 1,
				this.field_22078_e + this.field_22076_g + 1, -6250336);
		this.drawRect(this.field_22079_d, this.field_22078_e, this.field_22079_d + this.field_22077_f,
				this.field_22078_e + this.field_22076_g, -16777216);
		if (this.field_22081_b) {
			boolean bl = this.field_22082_a && this.ticks / 6 % 2 == 0;
			this.drawString(this.field_22080_c, this.field_22075_h + (bl ? "_" : ""), this.field_22079_d + 4,
					this.field_22078_e + (this.field_22076_g - 8) / 2, 0xE0E0E0);
		} else {
			this.drawString(this.field_22080_c, this.field_22075_h, this.field_22079_d + 4,
					this.field_22078_e + (this.field_22076_g - 8) / 2, 0x707070);
		}
	}

	public void setMaxLength(int n) {
		this.maxLength = n;
	}
}
