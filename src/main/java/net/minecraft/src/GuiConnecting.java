package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

import java.io.IOException;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerProfile;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen {

	private NetClientHandler clientHandler;
	
	private boolean cancelled;
	private String uri;
	private int timer = 0;
	
	public GuiConnecting(Minecraft minecraft, String s) {
		cancelled = false;
		uri = s;
		minecraft.changeWorld1(null);
	}

	public void updateScreen() {
		if (timer > 2 && this.clientHandler == null) {
			try {
				String uria = null;
				if(uri.startsWith("ws://")) {
					uria = uri.substring(5);
				}else if(uri.startsWith("wss://")){
					uria = uri.substring(6);
				}else if(!uri.contains("://")){
					uria = uri;
					uri = "ws://" + uri;
				}else {
					this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[] { "invalid uri websocket protocol" }));
					return;
				}
				
				int i = uria.lastIndexOf(':');
				int port = -1;
				
				if(i > 0 && uria.startsWith("[") && uria.charAt(i - 1) != ']') {
					i = -1;
				}
				
				if(i == -1) port = uri.startsWith("wss") ? 443 : 80;
				if(uria.endsWith("/")) uria = uria.substring(0, uria.length() - 1);
				
				if(port == -1) {
					try {
						int i2 = uria.indexOf('/');
						port = Integer.parseInt(uria.substring(i + 1, i2 == -1 ? uria.length() : i2 - 1));
					}catch(Throwable t) {
						this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[] { "invalid port number" }));
					}
				}
				
				this.clientHandler = new NetClientHandler(mc, uri, 0);
				this.clientHandler.addToSendQueue(new Packet2Handshake(mc.session.username));
				this.clientHandler.addToSendQueue(new Packet69EaglercraftData("EAG|MySkin", EaglerProfile.getSelfSkinPacket()));
			} catch (IOException e) {
				try {
					this.clientHandler.disconnect();
				}catch(Throwable t) {
				}
				e.printStackTrace();
				this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", new Object[] { e.toString() }));
			}
		}
		if (clientHandler != null) {
			clientHandler.processReadPackets();
		}
		if(timer >= 1) {
			++timer;
		}
		if(timer > 5) {
			if(!EaglerAdapter.connectionOpen() && this.mc.currentScreen == this) {
				this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.timeout", null));
			}
		}
	}

	protected void keyTyped(char c, int i) {
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (guibutton.id == 0) {
			cancelled = true;
			if (clientHandler != null) {
				clientHandler.disconnect();
			}
			mc.displayGuiScreen(new GuiMainMenu());
		}
	}

	public void drawScreen(int i, int j, float f) {
		if(timer == 0) {
			timer = 1;
		}
		drawDefaultBackground();
		StringTranslate stringtranslate = StringTranslate.getInstance();
		if (clientHandler == null) {
			drawCenteredString(fontRenderer, stringtranslate.translateKey("connect.connecting"), width / 2,
					height / 2 - 50, 0xffffff);
			drawCenteredString(fontRenderer, "", width / 2, height / 2 - 10, 0xffffff);
		} else {
			drawCenteredString(fontRenderer, stringtranslate.translateKey("connect.authorizing"), width / 2,
					height / 2 - 50, 0xffffff);
			drawCenteredString(fontRenderer, clientHandler.field_1209_a, width / 2, height / 2 - 10, 0xffffff);
		}
		super.drawScreen(i, j, f);
	}

	static NetClientHandler setNetClientHandler(GuiConnecting guiconnecting, NetClientHandler netclienthandler) {
		return guiconnecting.clientHandler = netclienthandler;
	}

	static boolean isCancelled(GuiConnecting guiconnecting) {
		return guiconnecting.cancelled;
	}

	static NetClientHandler getNetClientHandler(GuiConnecting guiconnecting) {
		return guiconnecting.clientHandler;
	}
	
}
