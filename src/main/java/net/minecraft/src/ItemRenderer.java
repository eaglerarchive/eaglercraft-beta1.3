package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerProfile;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class ItemRenderer {
	
	private static final TextureLocation terrainTexture = new TextureLocation("/terrain.png");
	private static final TextureLocation itemsTextures = new TextureLocation("/gui/items.png");
	private static final TextureLocation waterTexture = new TextureLocation("/misc/water.png");

	public ItemRenderer(Minecraft minecraft) {
		itemToRender = null;
		equippedProgress = 0.0F;
		prevEquippedProgress = 0.0F;
		field_1357_e = new RenderBlocks();
		field_20099_f = -1;
		mc = minecraft;
	}

	public void renderItem(ItemStack itemstack) {
		EaglerAdapter.glPushMatrix();
		if (itemstack.itemID < 256 && RenderBlocks.func_1219_a(Block.blocksList[itemstack.itemID].getRenderType())) {
			terrainTexture.bindTexture();
			field_1357_e.func_1227_a(Block.blocksList[itemstack.itemID], itemstack.getItemDamage());
		} else {
			if (itemstack.itemID < 256) {
				terrainTexture.bindTexture();
			} else {
				itemsTextures.bindTexture();
			}
			Tessellator tessellator = Tessellator.instance;
			float f = ((float) ((itemstack.getIconIndex() % 16) * 16) + 0.0F) / 256F;
			float f1 = ((float) ((itemstack.getIconIndex() % 16) * 16) + 15.99F) / 256F;
			float f2 = ((float) ((itemstack.getIconIndex() / 16) * 16) + 0.0F) / 256F;
			float f3 = ((float) ((itemstack.getIconIndex() / 16) * 16) + 15.99F) / 256F;
			float f4 = 1.0F;
			float f5 = 0.0F;
			float f6 = 0.3F;
			EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
			EaglerAdapter.glTranslatef(-f5, -f6, 0.0F);
			float f7 = 1.5F;
			EaglerAdapter.glScalef(f7, f7, f7);
			EaglerAdapter.glRotatef(50F, 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glRotatef(335F, 0.0F, 0.0F, 1.0F);
			EaglerAdapter.glTranslatef(-0.9375F, -0.0625F, 0.0F);
			float f8 = 0.0625F;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, f1, f3);
			tessellator.addVertexWithUV(f4, 0.0D, 0.0D, f, f3);
			tessellator.addVertexWithUV(f4, 1.0D, 0.0D, f, f2);
			tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, f1, f2);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1F);
			tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - f8, f1, f2);
			tessellator.addVertexWithUV(f4, 1.0D, 0.0F - f8, f, f2);
			tessellator.addVertexWithUV(f4, 0.0D, 0.0F - f8, f, f3);
			tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - f8, f1, f3);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1F, 0.0F, 0.0F);
			for (int i = 0; i < 16; i++) {
				float f9 = (float) i / 16F;
				float f13 = (f1 + (f - f1) * f9) - 0.001953125F;
				float f17 = f4 * f9;
				tessellator.addVertexWithUV(f17, 0.0D, 0.0F - f8, f13, f3);
				tessellator.addVertexWithUV(f17, 0.0D, 0.0D, f13, f3);
				tessellator.addVertexWithUV(f17, 1.0D, 0.0D, f13, f2);
				tessellator.addVertexWithUV(f17, 1.0D, 0.0F - f8, f13, f2);
			}

			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			for (int j = 0; j < 16; j++) {
				float f10 = (float) j / 16F;
				float f14 = (f1 + (f - f1) * f10) - 0.001953125F;
				float f18 = f4 * f10 + 0.0625F;
				tessellator.addVertexWithUV(f18, 1.0D, 0.0F - f8, f14, f2);
				tessellator.addVertexWithUV(f18, 1.0D, 0.0D, f14, f2);
				tessellator.addVertexWithUV(f18, 0.0D, 0.0D, f14, f3);
				tessellator.addVertexWithUV(f18, 0.0D, 0.0F - f8, f14, f3);
			}

			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			for (int k = 0; k < 16; k++) {
				float f11 = (float) k / 16F;
				float f15 = (f3 + (f2 - f3) * f11) - 0.001953125F;
				float f19 = f4 * f11 + 0.0625F;
				tessellator.addVertexWithUV(0.0D, f19, 0.0D, f1, f15);
				tessellator.addVertexWithUV(f4, f19, 0.0D, f, f15);
				tessellator.addVertexWithUV(f4, f19, 0.0F - f8, f, f15);
				tessellator.addVertexWithUV(0.0D, f19, 0.0F - f8, f1, f15);
			}

			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			for (int l = 0; l < 16; l++) {
				float f12 = (float) l / 16F;
				float f16 = (f3 + (f2 - f3) * f12) - 0.001953125F;
				float f20 = f4 * f12;
				tessellator.addVertexWithUV(f4, f20, 0.0D, f, f16);
				tessellator.addVertexWithUV(0.0D, f20, 0.0D, f1, f16);
				tessellator.addVertexWithUV(0.0D, f20, 0.0F - f8, f1, f16);
				tessellator.addVertexWithUV(f4, f20, 0.0F - f8, f, f16);
			}

			tessellator.draw();
			EaglerAdapter.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		}
		EaglerAdapter.glPopMatrix();
	}

	public void renderItemInFirstPerson(float f) {
		float f1 = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * f;
		EntityPlayerSP entityplayersp = mc.thePlayer;
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glRotatef(
				((EntityPlayer) (entityplayersp)).prevRotationPitch + (((EntityPlayer) (entityplayersp)).rotationPitch
						- ((EntityPlayer) (entityplayersp)).prevRotationPitch) * f,
				1.0F, 0.0F, 0.0F);
		EaglerAdapter.glRotatef(((EntityPlayer) (entityplayersp)).prevRotationYaw
				+ (((EntityPlayer) (entityplayersp)).rotationYaw - ((EntityPlayer) (entityplayersp)).prevRotationYaw)
						* f,
				0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		EaglerAdapter.glPopMatrix();
		float f2 = mc.theWorld.getLightBrightness(MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posX),
				MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posY),
				MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posZ));
		EaglerAdapter.glColor4f(f2, f2, f2, 1.0F);
		ItemStack itemstack = itemToRender;
		if (((EntityPlayer) (entityplayersp)).fishEntity != null) {
			itemstack = new ItemStack(Item.stick);
		}
		if (itemstack != null) {
			EaglerAdapter.glPushMatrix();
			float f3 = 0.8F;
			float f5 = entityplayersp.getSwingProgress(f);
			float f7 = MathHelper.sin(f5 * 3.141593F);
			float f9 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F);
			EaglerAdapter.glTranslatef(-f9 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F * 2.0F) * 0.2F,
					-f7 * 0.2F);
			EaglerAdapter.glTranslatef(0.7F * f3, -0.65F * f3 - (1.0F - f1) * 0.6F, -0.9F * f3);
			EaglerAdapter.glRotatef(45F, 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
			f5 = entityplayersp.getSwingProgress(f);
			f7 = MathHelper.sin(f5 * f5 * 3.141593F);
			f9 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F);
			EaglerAdapter.glRotatef(-f7 * 20F, 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glRotatef(-f9 * 20F, 0.0F, 0.0F, 1.0F);
			EaglerAdapter.glRotatef(-f9 * 80F, 1.0F, 0.0F, 0.0F);
			f5 = 0.4F;
			EaglerAdapter.glScalef(f5, f5, f5);
			if (itemstack.getItem().shouldRotateAroundWhenRendering()) {
				EaglerAdapter.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			}
			renderItem(itemstack);
			EaglerAdapter.glPopMatrix();
		} else {
			EaglerAdapter.glPushMatrix();
			float f4 = 0.8F;
			float f6 = entityplayersp.getSwingProgress(f);
			float f8 = MathHelper.sin(f6 * 3.141593F);
			float f10 = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F);
			EaglerAdapter.glTranslatef(-f10 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.4F,
					-f8 * 0.4F);
			EaglerAdapter.glTranslatef(0.8F * f4, -0.75F * f4 - (1.0F - f1) * 0.6F, -0.9F * f4);
			EaglerAdapter.glRotatef(45F, 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
			f6 = entityplayersp.getSwingProgress(f);
			f8 = MathHelper.sin(f6 * f6 * 3.141593F);
			f10 = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F);
			EaglerAdapter.glRotatef(f10 * 70F, 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glRotatef(-f8 * 20F, 0.0F, 0.0F, 1.0F);
//			EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, mc.renderEngine
//					.getTextureForDownloadableImage(mc.thePlayer.skinUrl, mc.thePlayer.getEntityTexture()));
			if(EaglerProfile.presetSkinId < 0) {
				mc.renderEngine.bindTexture(EaglerProfile.skins.get(EaglerProfile.customSkinId).glTex);
			}else {
				EaglerProfile.defaultOptionsTextures[EaglerProfile.presetSkinId].bindTexture();
			}
			EaglerAdapter.glTranslatef(-1F, 3.6F, 3.5F);
			EaglerAdapter.glRotatef(120F, 0.0F, 0.0F, 1.0F);
			EaglerAdapter.glRotatef(200F, 1.0F, 0.0F, 0.0F);
			EaglerAdapter.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glScalef(1.0F, 1.0F, 1.0F);
			EaglerAdapter.glTranslatef(5.6F, 0.0F, 0.0F);
			Render render = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
			RenderPlayer renderplayer = (RenderPlayer) render;
			f10 = 1.0F;
			EaglerAdapter.glScalef(f10, f10, f10);
			renderplayer.drawFirstPersonHand();
			EaglerAdapter.glPopMatrix();
		}
		EaglerAdapter.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		RenderHelper.disableStandardItemLighting();
	}

	public void renderOverlays(float f) {
		EaglerAdapter.glDisable(3008 /* GL_ALPHA_TEST */);
		if (mc.thePlayer.func_21062_U()) {
			terrainTexture.bindTexture();
			renderFireInFirstPerson(f);
		}
		if (mc.thePlayer.func_345_I()) {
			int j = MathHelper.floor_double(mc.thePlayer.posX);
			int l = MathHelper.floor_double(mc.thePlayer.posY);
			int i1 = MathHelper.floor_double(mc.thePlayer.posZ);
			terrainTexture.bindTexture();
			int k1 = mc.theWorld.getBlockId(j, l, i1);
			if (Block.blocksList[k1] != null) {
				renderInsideOfBlock(f, Block.blocksList[k1].getBlockTextureFromSide(2));
			}
		}
		if (mc.thePlayer.isInsideOfMaterial(Material.water)) {
			waterTexture.bindTexture();
			renderWarpedTextureOverlay(f);
		}
		EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
	}

	private void renderInsideOfBlock(float f, int i) {
		Tessellator tessellator = Tessellator.instance;
		float f1 = mc.thePlayer.getEntityBrightness(f);
		f1 = 0.1F;
		EaglerAdapter.glColor4f(f1, f1, f1, 0.5F);
		EaglerAdapter.glPushMatrix();
		float f2 = -1F;
		float f3 = 1.0F;
		float f4 = -1F;
		float f5 = 1.0F;
		float f6 = -0.5F;
		float f7 = 0.0078125F;
		float f8 = (float) (i % 16) / 256F - f7;
		float f9 = ((float) (i % 16) + 15.99F) / 256F + f7;
		float f10 = (float) (i / 16) / 256F - f7;
		float f11 = ((float) (i / 16) + 15.99F) / 256F + f7;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(f2, f4, f6, f9, f11);
		tessellator.addVertexWithUV(f3, f4, f6, f8, f11);
		tessellator.addVertexWithUV(f3, f5, f6, f8, f10);
		tessellator.addVertexWithUV(f2, f5, f6, f9, f10);
		tessellator.draw();
		EaglerAdapter.glPopMatrix();
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void renderWarpedTextureOverlay(float f) {
		Tessellator tessellator = Tessellator.instance;
		float f1 = mc.thePlayer.getEntityBrightness(f);
		EaglerAdapter.glColor4f(f1, f1, f1, 0.5F);
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glBlendFunc(770, 771);
		EaglerAdapter.glPushMatrix();
		float f2 = 4F;
		float f3 = -1F;
		float f4 = 1.0F;
		float f5 = -1F;
		float f6 = 1.0F;
		float f7 = -0.5F;
		float f8 = -mc.thePlayer.rotationYaw / 64F;
		float f9 = mc.thePlayer.rotationPitch / 64F;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(f3, f5, f7, f2 + f8, f2 + f9);
		tessellator.addVertexWithUV(f4, f5, f7, 0.0F + f8, f2 + f9);
		tessellator.addVertexWithUV(f4, f6, f7, 0.0F + f8, 0.0F + f9);
		tessellator.addVertexWithUV(f3, f6, f7, f2 + f8, 0.0F + f9);
		tessellator.draw();
		EaglerAdapter.glPopMatrix();
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
	}

	private void renderFireInFirstPerson(float f) {
		Tessellator tessellator = Tessellator.instance;
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glBlendFunc(770, 771);
		float f1 = 1.0F;
		for (int i = 0; i < 2; i++) {
			EaglerAdapter.glPushMatrix();
			int j = Block.fire.blockIndexInTexture + i * 16;
			int k = (j & 0xf) << 4;
			int l = j & 0xf0;
			float f2 = (float) k / 256F;
			float f3 = ((float) k + 15.99F) / 256F;
			float f4 = (float) l / 256F;
			float f5 = ((float) l + 15.99F) / 256F;
			float f6 = (0.0F - f1) / 2.0F;
			float f7 = f6 + f1;
			float f8 = 0.0F - f1 / 2.0F;
			float f9 = f8 + f1;
			float f10 = -0.5F;
			EaglerAdapter.glTranslatef((float) (-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
			EaglerAdapter.glRotatef((float) (i * 2 - 1) * 10F, 0.0F, 1.0F, 0.0F);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(f6, f8, f10, f3, f5);
			tessellator.addVertexWithUV(f7, f8, f10, f2, f5);
			tessellator.addVertexWithUV(f7, f9, f10, f2, f4);
			tessellator.addVertexWithUV(f6, f9, f10, f3, f4);
			tessellator.draw();
			EaglerAdapter.glPopMatrix();
		}

		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
	}

	public void updateEquippedItem() {
		prevEquippedProgress = equippedProgress;
		EntityPlayerSP entityplayersp = mc.thePlayer;
		ItemStack itemstack = ((EntityPlayer) (entityplayersp)).inventory.getCurrentItem();
		ItemStack itemstack1 = itemstack;
		boolean flag = field_20099_f == ((EntityPlayer) (entityplayersp)).inventory.currentItem
				&& itemstack1 == itemToRender;
		if (itemToRender == null && itemstack1 == null) {
			flag = true;
		}
		if (itemstack1 != null && itemToRender != null && itemstack1 != itemToRender
				&& itemstack1.itemID == itemToRender.itemID) {
			itemToRender = itemstack1;
			flag = true;
		}
		float f = 0.4F;
		float f1 = flag ? 1.0F : 0.0F;
		float f2 = f1 - equippedProgress;
		if (f2 < -f) {
			f2 = -f;
		}
		if (f2 > f) {
			f2 = f;
		}
		equippedProgress += f2;
		if (equippedProgress < 0.1F) {
			itemToRender = itemstack1;
			field_20099_f = ((EntityPlayer) (entityplayersp)).inventory.currentItem;
		}
	}

	public void func_9449_b() {
		equippedProgress = 0.0F;
	}

	public void func_9450_c() {
		equippedProgress = 0.0F;
	}

	private Minecraft mc;
	private ItemStack itemToRender;
	private float equippedProgress;
	private float prevEquippedProgress;
	private RenderBlocks field_1357_e;
	private int field_20099_f;
}
