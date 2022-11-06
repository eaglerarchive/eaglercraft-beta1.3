package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;

// Decompiled with: CFR 0.152

public class GuiEditSign extends GuiScreen {
	protected String field_999_a = "Edit sign message:";
	private TileEntitySign field_1002_h;
	private int field_4189_i;
	private int field_1000_j = 0;

	public GuiEditSign(TileEntitySign tileEntitySign) {
		this.field_1002_h = tileEntitySign;
	}

	public void initGui() {
		this.controlList.clear();
		EaglerAdapter.enableRepeatEvents(true);
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done"));
	}

	public void onGuiClosed() {
		EaglerAdapter.enableRepeatEvents(false);
		if (this.mc.theWorld.multiplayerWorld) {
			this.mc.func_20001_q().addToSendQueue(new Packet130(this.field_1002_h.xCoord, this.field_1002_h.yCoord,
					this.field_1002_h.zCoord, this.field_1002_h.signText));
		}
	}

	public void updateScreen() {
		++this.field_4189_i;
	}

	protected void actionPerformed(GuiButton guiButton) {
		if (!guiButton.enabled) {
			return;
		}
		if (guiButton.id == 0) {
			this.field_1002_h.onInventoryChanged();
			this.mc.displayGuiScreen(null);
		}
	}

	protected void keyTyped(char c, int n) {
		if (n == 200) {
			this.field_1000_j = this.field_1000_j - 1 & 3;
		}
		if (n == 208 || n == 28) {
			this.field_1000_j = this.field_1000_j + 1 & 3;
		}
		if (n == 14 && this.field_1002_h.signText[this.field_1000_j].length() > 0) {
			this.field_1002_h.signText[this.field_1000_j] = this.field_1002_h.signText[this.field_1000_j].substring(0,
					this.field_1002_h.signText[this.field_1000_j].length() - 1);
		}
		if (FontAllowedCharacters.isAllowed(c) >= 0 && this.field_1002_h.signText[this.field_1000_j].length() < 15) {
			int n2 = this.field_1000_j;
			this.field_1002_h.signText[n2] = this.field_1002_h.signText[n2] + c;
		}
	}

	public void drawScreen(int n, int n2, float f) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.field_999_a, this.width / 2, 40, 0xFFFFFF);
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef(this.width / 2, this.height / 2, 50.0f);
		float f2 = 93.75f;
		EaglerAdapter.glScalef(-f2, -f2, -f2);
		EaglerAdapter.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
		Block block = this.field_1002_h.getBlockType();
		if (block == Block.signPost) {
			float f3 = (float) (this.field_1002_h.getBlockMetadata() * 360) / 16.0f;
			EaglerAdapter.glRotatef(f3, 0.0f, 1.0f, 0.0f);
			EaglerAdapter.glTranslatef(0.0f, 0.3125f, 0.0f);
		} else {
			int n3 = this.field_1002_h.getBlockMetadata();
			float f4 = 0.0f;
			if (n3 == 2) {
				f4 = 180.0f;
			}
			if (n3 == 4) {
				f4 = 90.0f;
			}
			if (n3 == 5) {
				f4 = -90.0f;
			}
			EaglerAdapter.glRotatef(f4, 0.0f, 1.0f, 0.0f);
			EaglerAdapter.glTranslatef(0.0f, 0.3125f, 0.0f);
		}
		if (this.field_4189_i / 6 % 2 == 0) {
			this.field_1002_h.lineBeingEdited = this.field_1000_j;
		}
		TileEntityRenderer.instance.renderTileEntityAt(this.field_1002_h, -0.5, -0.75, -0.5, 0.0f);
		this.field_1002_h.lineBeingEdited = -1;
		EaglerAdapter.glPopMatrix();
		super.drawScreen(n, n2, f);
	}
}
