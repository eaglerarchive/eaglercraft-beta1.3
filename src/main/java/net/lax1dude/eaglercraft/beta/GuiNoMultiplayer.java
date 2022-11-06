package net.lax1dude.eaglercraft.beta;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StringTranslate;

public class GuiNoMultiplayer extends GuiScreen {
	
	private final GuiScreen parent;
	
	public GuiNoMultiplayer(GuiScreen parent) {
		this.parent = parent;
	}

	public void initGui() {
		controlList.add(new GuiButton(0, (width - 200) / 2, height / 4 + 85, StringTranslate.getInstance().translateKey("gui.toMenu")));
	}
	
	public void actionPerformed(GuiButton bnt) {
		if(bnt.id == 0) {
			mc.displayGuiScreen(parent);
		}
	}
	
	public void drawScreen(int mx, int my, float f) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Multiplayer is not supported yet", width / 2, height / 4 - 10, 0xFFFFFF);
		drawCenteredString(fontRenderer, "Minecraft Beta public servers are VERY INSECURE!", width / 2, height / 4 + 15, 0xFFCCCC);
		drawCenteredString(fontRenderer, "Just watch some of Team Avolition's old videos", width / 2, height / 4 + 26, 0xFFCCCC);
		drawCenteredString(fontRenderer, "I will add it soon, just shut up and be patient", width / 2, height / 4 + 52, 0x888888);
		super.drawScreen(mx, my, f);
	}
	
}
