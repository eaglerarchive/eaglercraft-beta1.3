package net.lax1dude.eaglercraft;

import java.nio.charset.StandardCharsets;

import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;

public class GuiMultiplayer extends GuiScreen {
	
	private final GuiScreen parent;
	private GuiTextField address = null;
	private GuiPassword password = null;
	private String currentString = "";
	public static String currentPassword = null;
	
	public GuiMultiplayer(GuiScreen guiMainMenu) {
		parent = guiMainMenu;
		currentString = guiMainMenu.getMinecraft().gameSettings.lastServer.replaceAll("_", ":");
	}
	
	public GuiMultiplayer(GuiMainMenu guiMainMenu, String srv) {
		parent = guiMainMenu;
		currentString = srv;
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		EaglerAdapter.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 30, stringtranslate.translateKey("multiplayer.connect")));
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 30, stringtranslate.translateKey("gui.cancel")));
		((GuiButton) controlList.get(0)).enabled = currentString.length() > 0;
		address = new GuiTextField(fontRenderer, width / 2 - 100, (height / 4 - 6) + 30 + 8, 200, 20, currentString);
		if(currentPassword == null) {
			if(GameSettings.currentSessionPasswordPlaintext == null) {
				if(mc.gameSettings.lastPasswordLength > 0) {
					password = new GuiPassword(fontRenderer, width / 2 - 100, (height / 4 - 16) + 30 + 69, 200, 20, mc.gameSettings.lastPasswordLength);
				}else {
					currentPassword = "";
					password = new GuiPassword(fontRenderer, width / 2 - 100, (height / 4 - 16) + 30 + 69, 200, 20, "");
				}
			}else {
				currentPassword = GameSettings.currentSessionPasswordPlaintext;
				password = new GuiPassword(fontRenderer, width / 2 - 100, (height / 4 - 16) + 30 + 69, 200, 20, currentPassword);
			}
		}else {
			password = new GuiPassword(fontRenderer, width / 2 - 100, (height / 4 - 16) + 30 + 69, 200, 20, currentPassword);
		}
	}

	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("multiplayer.title"), width / 2, (height / 4 - 66) + 25, 0xffffff);
		drawString(fontRenderer, stringtranslate.translateKey("multiplayer.info1"), width / 2 - 140, (height / 4 - 66) + 48 + 0, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("multiplayer.info2"), width / 2 - 140, (height / 4 - 66) + 48 + 9, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("multiplayer.ipinfo"), width / 2 - 140, (height / 4 - 66) + 50 + 30, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("multiplayer.passinfo1"), width / 2 - 140, height / 4 + 65, 0xa0a0a0);
		
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glScalef(0.75f, 0.75f, 0.75f);
		drawString(fontRenderer, stringtranslate.translateKey("multiplayer.passinfo2"), (width / 2 - 120) * 4 / 3, (height / 4 + 76 + 34) * 4 / 3, 0x707070);
		EaglerAdapter.glPopMatrix();
		
		address.drawTextBox();
		password.drawTextBox();
		super.drawScreen(i, j, f);
	}
	
	protected void mouseClicked(int i, int j, int k) {
		address.handleMouseInput(i, j, k);
		password.handleMouseInput(i, j, k);
		super.mouseClicked(i, j, k);
	}
	
	protected void keyTyped(char c, int i) {
		address.handleKeyboardInput(c, i);
		password.handleKeyboardInput(c, i);
		currentString = address.getTextBoxText();
		((GuiButton) controlList.get(0)).enabled = currentString.length() > 0;
		currentPassword = password.isShowingPlaceholder() ? null : password.getTextBoxText();
		super.keyTyped(c, i);
	}
	
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 0) {
			if(guibutton.enabled) {
				if(currentPassword != null && !password.isShowingPlaceholder()) {
					if(currentPassword.length() > 0) {
						GameSettings.currentSessionPasswordPlaintext = currentPassword;
						mc.gameSettings.lastPasswordHash = makeHash(currentPassword);
						mc.gameSettings.lastPasswordLength = currentPassword.length();
					}else {
						GameSettings.currentSessionPasswordPlaintext = "";
						mc.gameSettings.lastPasswordHash = "null";
						mc.gameSettings.lastPasswordLength = -1;
					}
				}
				mc.gameSettings.lastServer = currentString;
				mc.gameSettings.saveOptions();
				mc.loadingScreen.displayLoadingString("Connecting to the server...", "");
				mc.displayGuiScreen(new GuiConnecting(mc, currentString));
			}
		}else if(guibutton.id == 1) {
			mc.displayGuiScreen(parent);
		}
	}
	
	private static final byte[] eaglerSalt = {
			(byte)84,(byte)97,(byte)107,(byte)101,(byte)32,(byte)104,(byte)105,(byte)115,
			(byte)32,(byte)106,(byte)117,(byte)107,(byte)101,(byte)98,(byte)111,(byte)120,
			(byte)44,(byte)32,(byte)102,(byte)117,(byte)99,(byte)107,(byte)32,(byte)116,
			(byte)104,(byte)105,(byte)115,(byte)32,(byte)107,(byte)105,(byte)100,(byte)46
	};
	
	public static String makeHash(String pass) {
		SHA1Digest hashGen = new SHA1Digest();
		hashGen.update(eaglerSalt, 0, eaglerSalt.length);
		byte[] b = pass.getBytes(StandardCharsets.UTF_8);
		hashGen.update(b, 0, b.length);
		byte[] o = new byte[20];
		hashGen.doFinal(o, 0);
		return Base64.encodeBase64String(o).replace('+', '-').replace('/', '_');
	}
	
	public static String makeLoginHash(String configHashedPassword, String serverSalt) {
		byte[] decodedServerSalt = Base64.decodeBase64(serverSalt.replace('-', '+').replace('_', '/'));
		if(decodedServerSalt == null || decodedServerSalt.length != 18) {
			return null;
		}
		SHA1Digest hashGen = new SHA1Digest();
		hashGen.update(eaglerSalt, 0, eaglerSalt.length);
		byte[] decodedPassword = Base64.decodeBase64(configHashedPassword.replace('-', '+').replace('_', '/'));
		hashGen.update(decodedPassword, 0, decodedPassword.length);
		hashGen.update(decodedServerSalt, 0, 9);
		byte[] o = new byte[20];
		hashGen.doFinal(o, 0);
		hashGen.update(eaglerSalt, 0, eaglerSalt.length);
		hashGen.update(decodedServerSalt, 9, 9);
		hashGen.update(o, 0, o.length);
		hashGen.doFinal(o, 0);
		return Base64.encodeBase64String(o).replace('+', '-').replace('/', '_');
	}
	
}
