// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.client;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerProfile;
import net.lax1dude.eaglercraft.GuiMultiplayer;
import net.lax1dude.eaglercraft.GuiScreenEditProfile;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;
import net.lax1dude.eaglercraft.beta.EaglercraftSaveManager;
import net.lax1dude.eaglercraft.beta.SingleplayerCommands;
import net.lax1dude.eaglercraft.beta.TextureNewClockFX;
import net.lax1dude.eaglercraft.beta.TextureNewCompassFX;
import net.minecraft.src.*;

public abstract class Minecraft implements Runnable {

	public Minecraft() {
		instance = this;
		fullscreen = false;
		timer = new Timer(20F);
		session = new Session("fuck", "shit");
		hideQuitButton = true;
		isWorldLoaded = false;
		currentScreen = null;
		displayWidth = EaglerAdapter.getCanvasWidth();
		displayHeight = EaglerAdapter.getCanvasHeight();
		loadingScreen = new LoadingScreenRenderer(this);
		entityRenderer = new EntityRenderer(this);
		mouseHelper = new MouseHelper();
		ticksRan = 0;
		field_6282_S = 0;
		field_6307_v = false;
		field_9242_w = new ModelBiped(0.0F);
		objectMouseOver = null;
		sndManager = new SoundManager();
		running = true;
		debug = "";
		isTakingScreenshot = false;
		prevFrameTime = -1L;
		field_6302_aa = 0;
		isRaining = false;
		systemTime = System.currentTimeMillis();
		field_6300_ab = 0;
		hideQuitButton = false;
		awaitPointerLock = false;
		field_21900_a = this;
	}
	
	public abstract void displayCrashScreen(Throwable t);

	public void startGame() {
		RenderManager.instance.itemRenderer = new ItemRenderer(this);
		
		field_22008_V = EaglerAdapter.getConfiguredSaveFormat();
		if(field_22008_V == null) {
			field_22008_V = new EaglercraftSaveManager("saves");
		}
		
		gameSettings = new GameSettings(this);
		texturePackList = new TexturePackList(this);
		renderEngine = new RenderEngine(texturePackList, gameSettings);
		fontRenderer = new FontRenderer(gameSettings, "/font/default.png", renderEngine);
		checkGLError("Pre startup");
		EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		EaglerAdapter.glShadeModel(7425 /* GL_SMOOTH */);
		EaglerAdapter.glClearDepth(1.0F);
		EaglerAdapter.glEnable(2929 /* GL_DEPTH_TEST */);
		EaglerAdapter.glDepthFunc(515);
		EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
		EaglerAdapter.glAlphaFunc(516, 0.1F);
		EaglerAdapter.glCullFace(1029 /* GL_BACK */);
		EaglerAdapter.glMatrixMode(5889 /* GL_PROJECTION */);
		EaglerAdapter.glLoadIdentity();
		EaglerAdapter.glMatrixMode(5888 /* GL_MODELVIEW0_ARB */);
		this.loadScreen();
		checkGLError("Startup");
		glCapabilities = new OpenGlCapsChecker();
		sndManager.loadSoundSettings(gameSettings);
		renderEngine.registerTextureFX(new TextureNewCompassFX());
		renderEngine.registerTextureFX(new TextureNewClockFX());
		renderEngine.registerSpriteSheet("portal", Block.portal.blockIndexInTexture, 1);
		renderEngine.registerSpriteSheet("water", Block.waterStill.blockIndexInTexture, 1);
		renderEngine.registerSpriteSheet("water_flow", Block.waterMoving.blockIndexInTexture + 1, 2);
		renderEngine.registerSpriteSheet("lava", Block.lavaStill.blockIndexInTexture, 1);
		renderEngine.registerSpriteSheet("lava_flow", Block.lavaMoving.blockIndexInTexture + 1, 2);
		renderEngine.registerSpriteSheet("fire_0", Block.fire.blockIndexInTexture, 1);
		renderEngine.registerSpriteSheet("fire_1", Block.fire.blockIndexInTexture + 16, 1);
		renderGlobal = new RenderGlobal(this, renderEngine);
		EaglerAdapter.glViewport(0, 0, displayWidth, displayHeight);
		effectRenderer = new EffectRenderer(theWorld, renderEngine);
		checkGLError("Post startup");
		
		EaglerProfile.loadFromStorage();
		session = new Session(EaglerProfile.username, "-");
		
		while(EaglerAdapter.keysNext());
		while(EaglerAdapter.mouseNext());
		ingameGUI = new GuiIngame(this);
		String srv = EaglerAdapter.getServerToJoinOnLaunch();
		if (srv != null && srv.length() > 0) {
			displayGuiScreen(new GuiScreenEditProfile(new GuiMultiplayer(new GuiMainMenu(), srv)));
		} else {
			displayGuiScreen(new GuiScreenEditProfile(new GuiMainMenu()));
		}
	}

	private void loadScreen() {
		int xx = displayWidth;
		if(xx > displayHeight) {
			xx = displayHeight;
		}
		EaglerAdapter.glClear(16640);
		EaglerAdapter.glMatrixMode(5889 /* GL_PROJECTION */);
		EaglerAdapter.glLoadIdentity();
		EaglerAdapter.glOrtho(0.0F, displayWidth, displayHeight, 0.0F, 1000F, 3000F);
		EaglerAdapter.glMatrixMode(5888 /* GL_MODELVIEW0_ARB */);
		EaglerAdapter.glLoadIdentity();
		EaglerAdapter.glTranslatef(0.0F, 0.0F, -2000F);
		EaglerAdapter.glViewport(0, 0, displayWidth, displayHeight);
		EaglerAdapter.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Tessellator tessellator = Tessellator.instance;
		EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
		EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		EaglerAdapter.glDisable(2912 /* GL_FOG */);
		EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, renderEngine.getTexture("/title/mojang.png"));
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(0xffffff);
		tessellator.addVertexWithUV(0.0D, displayHeight, 0.0D, 0.0D, 0.0D);
		tessellator.addVertexWithUV(displayWidth, displayHeight, 0.0D, 0.0D, 0.0D);
		tessellator.addVertexWithUV(displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(0xffffff);
		int marginX = (displayWidth - xx) / 2;
		int marginY = (displayHeight - xx) / 2;
		tessellator.addVertexWithUV(marginX, displayHeight - marginY, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(displayWidth - marginX, displayHeight - marginY, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(displayWidth - marginX, marginY, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(marginX, marginY, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
		EaglerAdapter.glDisable(2912 /* GL_FOG */);
		EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
		EaglerAdapter.glAlphaFunc(516, 0.1F);
		EaglerAdapter.updateDisplay();
	}

	public void func_6274_a(int i, int j, int k, int l, int i1, int j1) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(i + 0, j + j1, 0.0D, (float) (k + 0) * f, (float) (l + j1) * f1);
		tessellator.addVertexWithUV(i + i1, j + j1, 0.0D, (float) (k + i1) * f, (float) (l + j1) * f1);
		tessellator.addVertexWithUV(i + i1, j + 0, 0.0D, (float) (k + i1) * f, (float) (l + 0) * f1);
		tessellator.addVertexWithUV(i + 0, j + 0, 0.0D, (float) (k + 0) * f, (float) (l + 0) * f1);
		tessellator.draw();
	}

	public ISaveFormat func_22004_c() {
		return field_22008_V;
	}

	public void displayGuiScreen(GuiScreen guiscreen) {
		if (currentScreen instanceof GuiUnused) {
			return;
		}
		if (currentScreen != null) {
			currentScreen.onGuiClosed();
		}
		if (guiscreen == null && theWorld == null) {
			guiscreen = new GuiMainMenu();
		} else if (guiscreen == null && thePlayer.health <= 0) {
			guiscreen = new GuiGameOver();
		}
		currentScreen = guiscreen;
		if (guiscreen != null) {
			ungrabMouseCursor();
			ScaledResolution scaledresolution = new ScaledResolution(displayWidth, displayHeight);
			int i = scaledresolution.getScaledWidth();
			int j = scaledresolution.getScaledHeight();
			guiscreen.setWorldAndResolution(this, i, j);
			field_6307_v = false;
		} else {
			grabMouseCursor();
		}
	}

	private void checkGLError(String s) {
		int i = EaglerAdapter.glGetError();
		if (i != 0) {
			String s1 = EaglerAdapter.gluErrorString(i);
			System.out.println("########## GL ERROR ##########");
			System.out.println((new StringBuilder()).append("@ ").append(s).toString());
			System.out.println((new StringBuilder()).append(i).append(": ").append(s1).toString());
		}
	}

	public void shutdownMinecraftApplet() {
		System.out.println("Stopping!");
		try {
			changeWorld1(null);
		} catch (Throwable throwable) {
		}
		try {
			GLAllocation.deleteTexturesAndDisplayLists();
		} catch (Throwable throwable1) {
		}
		EaglerAdapter.destroyContext();
		EaglerAdapter.exit();
	}

	public void run() {
		running = true;
		try {
			startGame();
		} catch (Exception exception) {
			displayCrashScreen(exception);
			return;
		}
		try {
			long l = System.currentTimeMillis();
			int i = 0;
			while (running) {
				AxisAlignedBB.clearBoundingBoxPool();
				Vec3D.initialize();
				if (EaglerAdapter.shouldShutdown()) {
					shutdown();
					break;
				}
				if (isWorldLoaded && theWorld != null) {
					float f = timer.renderPartialTicks;
					timer.updateTimer();
					timer.renderPartialTicks = f;
				} else {
					timer.updateTimer();
				}
				long l1 = System.nanoTime();
				for (int j = 0; j < timer.elapsedTicks; j++) {
					ticksRan++;
					runTick();
				}

				long l2 = System.nanoTime() - l1;
				checkGLError("Pre render");
				sndManager.func_338_a(thePlayer, timer.renderPartialTicks);
				EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
				if (theWorld != null && !theWorld.multiplayerWorld) {
					theWorld.func_6465_g();
				}
				if (theWorld != null && theWorld.multiplayerWorld) {
					theWorld.func_6465_g();
				}
				if (gameSettings.limitFramerate) {
					Thread.sleep(5L);
				}
				EaglerAdapter.updateDisplay();
				if (!field_6307_v) {
					if (playerController != null) {
						playerController.setPartialTime(timer.renderPartialTicks);
					}
					entityRenderer.func_4136_b(timer.renderPartialTicks);
				}
				if (!EaglerAdapter.isFocused()) {
					if (fullscreen) {
						toggleFullscreen();
					}
					Thread.sleep(10L);
				}
				if (gameSettings.showDebugInfo) {
					if(!(currentScreen instanceof GuiChat)) {
						displayDebugInfo(l2);
					}
				} else {
					prevFrameTime = System.nanoTime();
				}
				screenshotListener();
				if (EaglerAdapter.getCanvasWidth() != displayWidth || EaglerAdapter.getCanvasHeight() != displayHeight) {
					displayWidth = EaglerAdapter.getCanvasWidth();
					displayHeight = EaglerAdapter.getCanvasHeight();
					if (displayWidth <= 0) {
						displayWidth = 1;
					}
					if (displayHeight <= 0) {
						displayHeight = 1;
					}
					resize(displayWidth, displayHeight);
				}
				checkGLError("Post render");
				i++;
				isWorldLoaded = !isMultiplayerWorld() && currentScreen != null && currentScreen.doesGuiPauseGame();
				while (System.currentTimeMillis() >= l + 1000L) {
					debug = (new StringBuilder()).append(i).append(" fps, ").append(WorldRenderer.chunksUpdated)
							.append(" chunk updates").toString();
					WorldRenderer.chunksUpdated = 0;
					l += 1000L;
					i = 0;
				}
			}
		} catch (Throwable throwable) {
			theWorld = null;
			displayCrashScreen(throwable);
			return;
		}
		
		EaglerAdapter.destroyContext();
		EaglerAdapter.exit();
	}

	private void screenshotListener() {
		if (EaglerAdapter.isFunctionKeyDown(gameSettings.keyBindFunction.keyCode, 60)) {
			if (!isTakingScreenshot) {
				isTakingScreenshot = true;
				EaglerAdapter.saveScreenshot();
			}
		} else {
			isTakingScreenshot = false;
		}
	}

	private void displayDebugInfo(long l) {
		long l1 = 0xfe502aL;
		if (prevFrameTime == -1L) {
			prevFrameTime = System.nanoTime();
		}
		long l2 = System.nanoTime();
		tickTimes[numRecordedFrameTimes & frameTimes.length - 1] = l;
		frameTimes[numRecordedFrameTimes++ & frameTimes.length - 1] = l2 - prevFrameTime;
		prevFrameTime = l2;
		EaglerAdapter.glClear(256);
		EaglerAdapter.glMatrixMode(5889 /* GL_PROJECTION */);
		EaglerAdapter.glLoadIdentity();
		EaglerAdapter.glOrtho(0.0F, displayWidth, displayHeight, 0.0F, 1000F, 3000F);
		EaglerAdapter.glMatrixMode(5888 /* GL_MODELVIEW0_ARB */);
		EaglerAdapter.glLoadIdentity();
		EaglerAdapter.glTranslatef(0.0F, 0.0F, -2000F);
		EaglerAdapter.glLineWidth(1.0F);
		EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(7);
		int i = (int) (l1 / 0x30d40L);
		tessellator.setColorOpaque_I(0x20000000);
		tessellator.addVertex(0.0D, displayHeight - i, 0.0D);
		tessellator.addVertex(0.0D, displayHeight, 0.0D);
		tessellator.addVertex(frameTimes.length, displayHeight, 0.0D);
		tessellator.addVertex(frameTimes.length, displayHeight - i, 0.0D);
		tessellator.setColorOpaque_I(0x20200000);
		tessellator.addVertex(0.0D, displayHeight - i * 2, 0.0D);
		tessellator.addVertex(0.0D, displayHeight - i, 0.0D);
		tessellator.addVertex(frameTimes.length, displayHeight - i, 0.0D);
		tessellator.addVertex(frameTimes.length, displayHeight - i * 2, 0.0D);
		tessellator.draw();
		long l3 = 0L;
		for (int j = 0; j < frameTimes.length; j++) {
			l3 += frameTimes[j];
		}

		int k = (int) (l3 / 0x30d40L / (long) frameTimes.length);
		tessellator.startDrawing(7);
		tessellator.setColorOpaque_I(0x20400000);
		tessellator.addVertex(0.0D, displayHeight - k, 0.0D);
		tessellator.addVertex(0.0D, displayHeight, 0.0D);
		tessellator.addVertex(frameTimes.length, displayHeight, 0.0D);
		tessellator.addVertex(frameTimes.length, displayHeight - k, 0.0D);
		tessellator.draw();
		tessellator.startDrawing(1);
		for (int i1 = 0; i1 < frameTimes.length; i1++) {
			int j1 = ((i1 - numRecordedFrameTimes & frameTimes.length - 1) * 255) / frameTimes.length;
			int k1 = (j1 * j1) / 255;
			k1 = (k1 * k1) / 255;
			int i2 = (k1 * k1) / 255;
			i2 = (i2 * i2) / 255;
			if (frameTimes[i1] > l1) {
				tessellator.setColorOpaque_I(0xff000000 + k1 * 0x10000);
			} else {
				tessellator.setColorOpaque_I(0xff000000 + k1 * 256);
			}
			long l4 = frameTimes[i1] / 0x30d40L;
			long l5 = tickTimes[i1] / 0x30d40L;
			tessellator.addVertex((float) i1 + 0.5F, (float) ((long) displayHeight - l4) + 0.5F, 0.0D);
			tessellator.addVertex((float) i1 + 0.5F, (float) displayHeight + 0.5F, 0.0D);
			tessellator.setColorOpaque_I(0xff000000 + k1 * 0x10000 + k1 * 256 + k1 * 1);
			tessellator.addVertex((float) i1 + 0.5F, (float) ((long) displayHeight - l4) + 0.5F, 0.0D);
			tessellator.addVertex((float) i1 + 0.5F, (float) ((long) displayHeight - (l4 - l5)) + 0.5F, 0.0D);
		}

		tessellator.draw();
		EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
	}

	public void shutdown() {
		running = false;
	}

	public void grabMouseCursor() {
		if (!EaglerAdapter.isFocused()) {
			return;
		}
		awaitPointerLock = true;
		if (EaglerAdapter.isPointerLocked2()) {
			return;
		} else {
			mouseHelper.grabMouse();
			field_6302_aa = ticksRan + 10000;
			return;
		}
	}

	public void ungrabMouseCursor() {
		if (!EaglerAdapter.isPointerLocked2()) {
			return;
		}
		if (thePlayer != null) {
			thePlayer.resetPlayerKeyState();
		}
		mouseHelper.ungrabMouse();
	}

	public void displayIngameMenu() {
		if (currentScreen == null) {
			displayGuiScreen(new GuiIngameMenu());
		}
	}

	private void func_6254_a(int i, boolean flag) {
		if (playerController.field_1064_b) {
			return;
		}
		if (i == 0 && field_6282_S > 0) {
			return;
		}
		if (flag && objectMouseOver != null && objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && i == 0) {
			int j = objectMouseOver.blockX;
			int k = objectMouseOver.blockY;
			int l = objectMouseOver.blockZ;
			playerController.sendBlockRemoving(j, k, l, objectMouseOver.sideHit);
			effectRenderer.addBlockHitEffects(j, k, l, objectMouseOver.sideHit);
		} else {
			playerController.func_6468_a();
		}
	}

	private void clickMouse(int i) {
		if (i == 0 && field_6282_S > 0) {
			return;
		}
		if (i == 0) {
			thePlayer.swingItem();
		}
		boolean flag = true;
		if (objectMouseOver == null) {
			if (i == 0) {
				field_6282_S = 10;
			}
		} else if (objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
			if (i == 0) {
				playerController.func_6472_b(thePlayer, objectMouseOver.entityHit);
			}
			if (i == 1) {
				playerController.func_6475_a(thePlayer, objectMouseOver.entityHit);
			}
		} else if (objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
			int j = objectMouseOver.blockX;
			int k = objectMouseOver.blockY;
			int l = objectMouseOver.blockZ;
			int i1 = objectMouseOver.sideHit;
			Block block = Block.blocksList[theWorld.getBlockId(j, k, l)];
			if (i == 0) {
				theWorld.onBlockHit(j, k, l, objectMouseOver.sideHit);
				if (block != Block.bedrock || thePlayer.field_9371_f >= 100) {
					playerController.clickBlock(j, k, l, objectMouseOver.sideHit);
				}
			} else {
				ItemStack itemstack1 = thePlayer.inventory.getCurrentItem();
				int j1 = itemstack1 == null ? 0 : itemstack1.stackSize;
				if (playerController.sendPlaceBlock(thePlayer, theWorld, itemstack1, j, k, l, i1)) {
					flag = false;
					thePlayer.swingItem();
				}
				if (itemstack1 == null) {
					return;
				}
				if (itemstack1.stackSize == 0) {
					thePlayer.inventory.mainInventory[thePlayer.inventory.currentItem] = null;
				} else if (itemstack1.stackSize != j1) {
					entityRenderer.itemRenderer.func_9449_b();
				}
			}
		}
		if (flag && i == 1) {
			ItemStack itemstack = thePlayer.inventory.getCurrentItem();
			if (itemstack != null && playerController.sendUseItem(thePlayer, theWorld, itemstack)) {
				entityRenderer.itemRenderer.func_9450_c();
			}
		}
	}

	public void toggleFullscreen() {

	}

	private void resize(int i, int j) {
		if (i <= 0) {
			i = 1;
		}
		if (j <= 0) {
			j = 1;
		}
		displayWidth = i;
		displayHeight = j;
		if (currentScreen != null) {
			ScaledResolution scaledresolution = new ScaledResolution(i, j);
			int k = scaledresolution.getScaledWidth();
			int l = scaledresolution.getScaledHeight();
			currentScreen.setWorldAndResolution(this, k, l);
		}
	}

	private void clickMiddleMouseButton() {
		if (objectMouseOver != null) {
			int i = theWorld.getBlockId(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);
			if (i == Block.grass.blockID) {
				i = Block.dirt.blockID;
			}
			if (i == Block.stairDouble.blockID) {
				i = Block.stairSingle.blockID;
			}
			if (i == Block.bedrock.blockID) {
				i = Block.stone.blockID;
			}
			thePlayer.inventory.setCurrentItem(i, false);
		}
	}
	
	private static final TextureLocation terrainTexture = new TextureLocation("/terrain.png");

	public void runTick() {
		ingameGUI.updateTick();
		entityRenderer.getMouseOver(1.0F);
		if (thePlayer != null) {
			IChunkProvider ichunkprovider = theWorld.func_21118_q();
			if (ichunkprovider instanceof ChunkProviderLoadOrGenerate) {
				ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate) ichunkprovider;
				int j = MathHelper.floor_float((int) thePlayer.posX) >> 4;
				int i1 = MathHelper.floor_float((int) thePlayer.posZ) >> 4;
				chunkproviderloadorgenerate.func_21110_c(j, i1);
			}
		}
		if (!isWorldLoaded && theWorld != null) {
			playerController.updateController();
			if(++holdStillTimer == 150) {
				if (thePlayer != null && !holdStillShownThisSession) {
					holdStillShownThisSession = true;
					if(isMultiplayerWorld()) {
						//ingameGUI.addChatMessage("Known Multiplayer Bugs:");
						//ingameGUI.addChatMessage(" - chunks may not show until you move around");
						//ingameGUI.addChatMessage(" - block crack animation is fucked up");
					}else {
						ingameGUI.addChatMessage("Note, the game can lag when chunks are generated");
						ingameGUI.addChatMessage("hold still for a few moments and the lag will stop");
					}
				}
			}else if(holdStillTimer == 10) {
				if(isMultiplayerWorld()) {
					renderGlobal.loadRenderers(); // dammit
				}
			}
		}
		terrainTexture.bindTexture();
		if (!isWorldLoaded) {
			renderEngine.updateTerrainTextures();
		}
		if (currentScreen == null && thePlayer != null) {
			if (thePlayer.health <= 0) {
				displayGuiScreen(null);
			} else if (thePlayer.isPlayerSleeping() && theWorld != null && theWorld.multiplayerWorld) {
				displayGuiScreen(new GuiSleepMP());
			}
		} else if (currentScreen != null && (currentScreen instanceof GuiSleepMP) && !thePlayer.isPlayerSleeping()) {
			displayGuiScreen(null);
		}
		if (currentScreen != null) {
			field_6302_aa = ticksRan + 10000;
		}
		if (currentScreen != null) {
			currentScreen.handleInput();
			if (currentScreen != null) {
				currentScreen.updateScreen();
			}
		}
		if (currentScreen == null || currentScreen.field_948_f) {
			do {
				if (!EaglerAdapter.mouseNext()) {
					break;
				}
				long l = System.currentTimeMillis() - systemTime;
				if (l <= 200L) {
					int k = EaglerAdapter.mouseGetEventDWheel();
					if (k != 0) {
						thePlayer.inventory.changeCurrentItem(k);
						if (gameSettings.field_22275_C) {
							if (k > 0) {
								k = 1;
							}
							if (k < 0) {
								k = -1;
							}
							gameSettings.field_22272_F += (float) k * 0.25F;
						}
					}
					if (currentScreen == null) {
						if (!EaglerAdapter.isPointerLocked() && EaglerAdapter.mouseGetEventButtonState()) {
							grabMouseCursor();
						} else {
							if (EaglerAdapter.mouseGetEventButton() == 0 && EaglerAdapter.mouseGetEventButtonState()) {
								clickMouse(0);
								field_6302_aa = ticksRan;
							}
							if (EaglerAdapter.mouseGetEventButton() == 1 && EaglerAdapter.mouseGetEventButtonState()) {
								clickMouse(1);
								field_6302_aa = ticksRan;
							}
							if (EaglerAdapter.mouseGetEventButton() == 2 && EaglerAdapter.mouseGetEventButtonState()) {
								clickMiddleMouseButton();
							}
						}
					} else if (currentScreen != null) {
						currentScreen.handleMouseInput();
					}
				}
			} while (true);
			if (field_6282_S > 0) {
				field_6282_S--;
			}
			do {
				if (!EaglerAdapter.keysNext()) {
					break;
				}
				thePlayer.handleKeyPress(EaglerAdapter.getEventKey(), EaglerAdapter.getEventKeyState());
				if (EaglerAdapter.getEventKeyState()) {
					if (EaglerAdapter.getEventKey() == 31 && EaglerAdapter.isFunctionKeyHeldDown(gameSettings.keyBindFunction.keyCode, 61)) {
						forceReload();
						continue;
					}
					if (currentScreen != null) {
						currentScreen.handleKeyboardInput();
					} else {
						if (EaglerAdapter.getEventKey() == 1) {
							displayIngameMenu();
							continue;
						}
						if (EaglerAdapter.isFunctionKeyDown(gameSettings.keyBindFunction.keyCode, 59)) {
							gameSettings.field_22277_y = !gameSettings.field_22277_y;
							continue;
						}
						if (EaglerAdapter.isFunctionKeyDown(gameSettings.keyBindFunction.keyCode, 61)) {
							gameSettings.showDebugInfo = !gameSettings.showDebugInfo;
							continue;
						}
						if (EaglerAdapter.isFunctionKeyDown(gameSettings.keyBindFunction.keyCode, 63)) {
							gameSettings.thirdPersonView = !gameSettings.thirdPersonView;
							continue;
						}
						if (EaglerAdapter.isFunctionKeyDown(gameSettings.keyBindFunction.keyCode, 66)) {
							gameSettings.field_22274_D = !gameSettings.field_22274_D;
							continue;
						}
						if (EaglerAdapter.getEventKey() == gameSettings.keyBindInventory.keyCode) {
							displayGuiScreen(new GuiInventory(thePlayer));
							continue;
						}
						if (EaglerAdapter.getEventKey() == gameSettings.keyBindDrop.keyCode) {
							thePlayer.dropCurrentItem();
							continue;
						}
						if (EaglerAdapter.getEventKey() == gameSettings.keyBindChat.keyCode) {
							displayGuiScreen(new GuiChat());
							continue;
						}
					}
					for (int i = 0; i < 9; i++) {
						if (EaglerAdapter.getEventKey() == 2 + i) {
							thePlayer.inventory.currentItem = i;
						}
					}
				}
			} while (true);
			if (currentScreen == null) {
				if(EaglerAdapter.isPointerLocked()) {
					awaitPointerLock = false;
					if (EaglerAdapter.mouseIsButtonDown(0) && (float) (ticksRan - field_6302_aa) >= timer.ticksPerSecond / 4F) {
						clickMouse(0);
						field_6302_aa = ticksRan;
					}
					if (EaglerAdapter.mouseIsButtonDown(1) && (float) (ticksRan - field_6302_aa) >= timer.ticksPerSecond / 4F) {
						clickMouse(1);
						field_6302_aa = ticksRan;
					}
				}else if(!awaitPointerLock) {
					displayIngameMenu();
				}
			}
			func_6254_a(0, currentScreen == null && EaglerAdapter.mouseIsButtonDown(0) && EaglerAdapter.isPointerLocked());
		}
		if (theWorld != null) {
			if (thePlayer != null) {
				field_6300_ab++;
				if (field_6300_ab == 30) {
					field_6300_ab = 0;
					theWorld.joinEntityInSurroundings(thePlayer);
				}
			}
			theWorld.difficultySetting = gameSettings.difficulty;
			if (theWorld.multiplayerWorld) {
				theWorld.difficultySetting = 3;
			}
			if (!isWorldLoaded) {
				EaglerProfile.freeSkins();
				entityRenderer.updateRenderer();
			}
			if (!isWorldLoaded) {
				renderGlobal.func_945_d();
			}
			if (!isWorldLoaded) {
				theWorld.func_633_c();
			}
			if (!isWorldLoaded || isMultiplayerWorld()) {
				theWorld.func_21114_a(gameSettings.difficulty > 0, true);
				theWorld.tick();
			}
			if (!isWorldLoaded && theWorld != null) {
				theWorld.randomDisplayUpdates(MathHelper.floor_double(thePlayer.posX),
						MathHelper.floor_double(thePlayer.posY), MathHelper.floor_double(thePlayer.posZ));
			}
			if (!isWorldLoaded) {
				effectRenderer.updateEffects();
			}
		}
		systemTime = System.currentTimeMillis();
	}

	private void forceReload() {
		System.out.println("FORCING RELOAD!");
		sndManager = new SoundManager();
		sndManager.loadSoundSettings(gameSettings);
		renderEngine.refreshTextures();
		renderGlobal.loadRenderers();
	}

	public boolean isMultiplayerWorld() {
		return theWorld != null && theWorld.multiplayerWorld;
	}
	
	public void displayChat(String s) {
		this.ingameGUI.addChatMessage(s);
	}
	
	public void displayErrorChat(String s) {
		this.ingameGUI.addChatMessage(FontRenderer.formatChar + "c" + s);
	}

	public void startWorld(String s, String s1, long l) {
		changeWorld1(null);
		System.gc();
		if (field_22008_V.worldNeedsConvert_maybe(s)) {
			func_22002_b(s, s1);
		} else {
			ISaveHandler isavehandler = field_22008_V.loadWorldHandler(s, false);
			World world = new World(isavehandler, s1, l);
			if (world.isNewWorld) {
				changeWorld2(world, "Generating level");
			} else {
				changeWorld2(world, "Loading level");
			}
		}
	}

	public void usePortal() {
		if (thePlayer.dimension == -1) {
			thePlayer.dimension = 0;
		} else {
			thePlayer.dimension = -1;
		}
		theWorld.setEntityDead(thePlayer);
		thePlayer.isDead = false;
		double d = thePlayer.posX;
		double d1 = thePlayer.posZ;
		double d2 = 8D;
		if (thePlayer.dimension == -1) {
			d /= d2;
			d1 /= d2;
			thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
			theWorld.updateEntityWithOptionalForce(thePlayer, false);
			World world = new World(theWorld, new WorldProviderHell());
			changeWorld(world, "Entering the Nether", thePlayer);
		} else {
			d *= d2;
			d1 *= d2;
			thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
			theWorld.updateEntityWithOptionalForce(thePlayer, false);
			World world1 = new World(theWorld, new WorldProvider());
			changeWorld(world1, "Leaving the Nether", thePlayer);
		}
		thePlayer.worldObj = theWorld;
		thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
		theWorld.updateEntityWithOptionalForce(thePlayer, false);
		(new Teleporter()).func_4107_a(theWorld, thePlayer);
	}

	public void changeWorld1(World world) {
		changeWorld2(world, "");
	}

	public void changeWorld2(World world, String s) {
		holdStillTimer = 0;
		changeWorld(world, s, null);
	}

	public void changeWorld(World world, String s, EntityPlayer entityplayer) {
		field_22009_h = null;
		loadingScreen.printText(s);
		loadingScreen.displayLoadingString("");
		sndManager.func_331_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		if (theWorld != null) {
			theWorld.func_651_a(loadingScreen);
		}
		theWorld = world;
		if (world != null) {
			playerController.func_717_a(world);
			if (!isMultiplayerWorld()) {
				if (entityplayer == null) {
					thePlayer = (EntityPlayerSP) world.func_4085_a(EntityPlayerSP.class);
				}
			} else if (thePlayer != null) {
				thePlayer.preparePlayerToSpawn();
				if (world != null) {
					world.entityJoinedWorld(thePlayer);
				}
			}
			if (!world.multiplayerWorld) {
				func_6255_d(s);
			}
			if (thePlayer == null) {
				thePlayer = (EntityPlayerSP) playerController.func_4087_b(world);
				thePlayer.preparePlayerToSpawn();
				playerController.flipPlayer(thePlayer);
			}
			thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
			if (renderGlobal != null) {
				renderGlobal.func_946_a(world);
			}
			if (effectRenderer != null) {
				effectRenderer.clearEffects(world);
			}
			playerController.func_6473_b(thePlayer);
			if (entityplayer != null) {
				world.func_6464_c();
			}
			IChunkProvider ichunkprovider = world.func_21118_q();
			if (ichunkprovider instanceof ChunkProviderLoadOrGenerate) {
				ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate) ichunkprovider;
				int i = MathHelper.floor_float((int) thePlayer.posX) >> 4;
				int j = MathHelper.floor_float((int) thePlayer.posZ) >> 4;
				chunkproviderloadorgenerate.func_21110_c(i, j);
			}
			world.spawnPlayerWithLoadedChunks(thePlayer);
			if (world.isNewWorld) {
				world.func_651_a(loadingScreen);
			}
			field_22009_h = thePlayer;
			mouseHelper.grabMouse();
		} else {
			EaglerProfile.freeAllSkins();
			holdStillShownThisSession = false;
			ungrabMouseCursor();
			thePlayer = null;
		}
		System.gc();
		systemTime = 0L;
	}

	private void func_22002_b(String s, String s1) {
		loadingScreen.printText(
				(new StringBuilder()).append("Converting World to ").append(field_22008_V.formatName()).toString());
		loadingScreen.displayLoadingString("This may take a while :)");
		field_22008_V.convertSave(s, loadingScreen);
		startWorld(s, s1, 0L);
	}

	private void func_6255_d(String s) {
		loadingScreen.printText(s);
		loadingScreen.displayLoadingString("Building terrain");
		char c = '\200';
		int i = 0;
		int j = (c * 2) / 16 + 1;
		j *= j;
		IChunkProvider ichunkprovider = theWorld.func_21118_q();
		ChunkCoordinates chunkcoordinates = theWorld.func_22137_s();
		if (thePlayer != null) {
			chunkcoordinates.field_22395_a = (int) thePlayer.posX;
			chunkcoordinates.field_22396_c = (int) thePlayer.posZ;
		}
		if (ichunkprovider instanceof ChunkProviderLoadOrGenerate) {
			ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate) ichunkprovider;
			chunkproviderloadorgenerate.func_21110_c(chunkcoordinates.field_22395_a >> 4,
					chunkcoordinates.field_22396_c >> 4);
		}
		for (int k = -c; k <= c; k += 16) {
			for (int l = -c; l <= c; l += 16) {
				loadingScreen.setLoadingProgress((i++ * 100) / j);
				theWorld.getBlockId(chunkcoordinates.field_22395_a + k, 64, chunkcoordinates.field_22396_c + l);
				while (theWorld.func_6465_g())
					;
			}

		}

		loadingScreen.displayLoadingString("Simulating world for a bit");
		j = 2000;
		theWorld.func_656_j();
	}

	public OpenGlCapsChecker func_6251_l() {
		return glCapabilities;
	}

	public String func_6241_m() {
		return renderGlobal.func_953_b();
	}

	public String func_6262_n() {
		return renderGlobal.func_957_c();
	}

	public String func_21002_o() {
		return theWorld.func_21119_g();
	}

	public String func_6245_o() {
		return (new StringBuilder()).append("P: ").append(effectRenderer.getStatistics()).append(". T: ")
				.append(theWorld.func_687_d()).toString();
	}

	public void respawn() {
		if (!theWorld.worldProvider.canRespawnHere()) {
			usePortal();
		}
		ChunkCoordinates chunkcoordinates = theWorld.func_22137_s();
		IChunkProvider ichunkprovider = theWorld.func_21118_q();
		if (ichunkprovider instanceof ChunkProviderLoadOrGenerate) {
			ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate) ichunkprovider;
			chunkproviderloadorgenerate.func_21110_c(chunkcoordinates.field_22395_a >> 4,
					chunkcoordinates.field_22396_c >> 4);
		}
		theWorld.setSpawnLocation();
		theWorld.updateEntityList();
		int i = 0;
		if (thePlayer != null) {
			i = thePlayer.entityId;
			theWorld.setEntityDead(thePlayer);
		}
		field_22009_h = null;
		thePlayer = (EntityPlayerSP) playerController.func_4087_b(theWorld);
		field_22009_h = thePlayer;
		thePlayer.preparePlayerToSpawn();
		playerController.flipPlayer(thePlayer);
		theWorld.spawnPlayerWithLoadedChunks(thePlayer);
		holdStillTimer = 0;
		thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
		thePlayer.entityId = i;
		thePlayer.func_6420_o();
		playerController.func_6473_b(thePlayer);
		func_6255_d("Respawning");
		if (currentScreen instanceof GuiGameOver) {
			displayGuiScreen(null);
		}
	}

	public NetClientHandler func_20001_q() {
		if (thePlayer instanceof EntityClientPlayerMP) {
			return ((EntityClientPlayerMP) thePlayer).sendQueue;
		} else {
			return null;
		}
	}

	public static boolean func_22006_t() {
		return field_21900_a == null || !field_21900_a.gameSettings.field_22277_y;
	}

	public static boolean func_22001_u() {
		return field_21900_a != null && field_21900_a.gameSettings.fancyGraphics;
	}

	public static boolean func_22005_v() {
		return field_21900_a != null && field_21900_a.gameSettings.field_22278_j;
	}

	public static boolean func_22007_w() {
		return field_21900_a != null && field_21900_a.gameSettings.showDebugInfo;
	}

	public boolean func_22003_b(String s) {
		if (!this.isMultiplayerWorld() && s.startsWith("/")) {
			SingleplayerCommands.processCommand(this, s.substring(1));
			return true;
		}
		return false;
	}

	private static Minecraft field_21900_a;
	public PlayerController playerController;
	private boolean fullscreen;
	public int displayWidth;
	public int displayHeight;
	private OpenGlCapsChecker glCapabilities;
	private Timer timer;
	public World theWorld;
	public RenderGlobal renderGlobal;
	public EntityPlayerSP thePlayer;
	public EntityLiving field_22009_h;
	public EffectRenderer effectRenderer;
	public Session session;
	public String minecraftUri;
	public boolean hideQuitButton;
	public volatile boolean isWorldLoaded;
	public RenderEngine renderEngine;
	public FontRenderer fontRenderer;
	public GuiScreen currentScreen;
	public LoadingScreenRenderer loadingScreen;
	public EntityRenderer entityRenderer;
	private int ticksRan;
	private int field_6282_S;
	private int field_9236_T;
	private int field_9235_U;
	public GuiIngame ingameGUI;
	public boolean field_6307_v;
	public ModelBiped field_9242_w;
	public MovingObjectPosition objectMouseOver;
	public GameSettings gameSettings;
	public SoundManager sndManager;
	public MouseHelper mouseHelper;
	public TexturePackList texturePackList;
	private ISaveFormat field_22008_V;
	public static long frameTimes[] = new long[512];
	public static long tickTimes[] = new long[512];
	public static int numRecordedFrameTimes = 0;
	public volatile boolean running;
	public String debug;
	boolean isTakingScreenshot;
	long prevFrameTime;
	private int field_6302_aa;
	public boolean isRaining;
	long systemTime;
	private int field_6300_ab;
	private boolean awaitPointerLock;
	public int holdStillTimer = 0;
	public boolean holdStillShownThisSession = false;

	private static Minecraft instance = null;

	public static Minecraft getMinecraft() {
		return instance;
	}

}
