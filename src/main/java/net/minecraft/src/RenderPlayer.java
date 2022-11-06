package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerProfile;
import net.lax1dude.eaglercraft.EaglerProfile.EnumSkinType;
import net.lax1dude.eaglercraft.EaglerProfile.UserSkin;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class RenderPlayer extends RenderLiving {

	private static final String[] armorFilenamePrefix = { "cloth", "chain", "iron", "diamond", "gold" };
	private static final TextureLocation[][] armorTextures = new TextureLocation[armorFilenamePrefix.length][2];
	
	static {
		for(int i = 0; i < armorFilenamePrefix.length; ++i) {
			armorTextures[i][0] = new TextureLocation("/armor/" + armorFilenamePrefix[i] + "_1.png");
			armorTextures[i][1] = new TextureLocation("/armor/" + armorFilenamePrefix[i] + "_2.png");
		}
	}
	
	public RenderPlayer() {
		super(new ModelBiped(0.0F), 0.5F);
		modelBipedMain = (ModelBiped) mainModel;
		modelBipedMain.blockTransparentSkins = true;
		modelArmorChestplate = new ModelBiped(1.0F);
		modelArmor = new ModelBiped(0.5F);
	}

	protected boolean setArmorModel(EntityPlayer entityplayer, int i, float f) {
		ItemStack itemstack = entityplayer.inventory.armorItemInSlot(3 - i);
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof ItemArmor) {
				ItemArmor itemarmor = (ItemArmor) item;
				armorTextures[itemarmor.renderIndex][i != 2 ? 0 : 1].bindTexture();
				ModelBiped modelbiped = i != 2 ? modelArmorChestplate : modelArmor;
				modelbiped.bipedHead.showModel = i == 0;
				modelbiped.bipedHeadwear.showModel = i == 0;
				modelbiped.bipedBody.showModel = i == 1 || i == 2;
				modelbiped.bipedRightArm.showModel = i == 1;
				modelbiped.bipedLeftArm.showModel = i == 1;
				modelbiped.bipedRightLeg.showModel = i == 2 || i == 3;
				modelbiped.bipedLeftLeg.showModel = i == 2 || i == 3;
				setRenderPassModel(modelbiped);
				return true;
			}
		}
		return false;
	}

	public void func_188_a(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		modelArmorChestplate.field_1278_i = modelArmor.field_1278_i = modelBipedMain.field_1278_i = itemstack != null;
		modelArmorChestplate.isSneak = modelArmor.isSneak = modelBipedMain.isSneak = entityplayer.isSneaking();
		double d3 = d1 - (double) entityplayer.yOffset;
		if (entityplayer.isSneaking()) {
			d3 -= 0.125D;
		}
		super.doRenderLiving(entityplayer, d, d3, d2, f, f1);
		modelArmorChestplate.isSneak = modelArmor.isSneak = modelBipedMain.isSneak = false;
		modelArmorChestplate.field_1278_i = modelArmor.field_1278_i = modelBipedMain.field_1278_i = false;
	}

	protected void func_22015_a(EntityPlayer entityplayer, double d, double d1, double d2) {
		if (Minecraft.func_22006_t() && entityplayer != renderManager.field_22188_h) {
			float f = 1.6F;
			float f1 = 0.01666667F * f;
			float f2 = entityplayer.getDistanceToEntity(renderManager.field_22188_h);
			float f3 = entityplayer.isSneaking() ? 32F : 64F;
			if (f2 < f3) {
				String s = entityplayer.username;
				if (!entityplayer.isSneaking()) {
					func_22013_a(entityplayer, s, d, d1, d2, 64);
				} else {
					FontRenderer fontrenderer = getFontRendererFromRenderManager();
					EaglerAdapter.glPushMatrix();
					EaglerAdapter.glTranslatef((float) d + 0.0F, (float) d1 + 2.3F, (float) d2);
					EaglerAdapter.glNormal3f(0.0F, 1.0F, 0.0F);
					EaglerAdapter.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
					EaglerAdapter.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
					EaglerAdapter.glScalef(-f1, -f1, f1);
					EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
					EaglerAdapter.glTranslatef(0.0F, 0.25F / f1, 0.0F);
					EaglerAdapter.glDepthMask(false);
					EaglerAdapter.glEnable(3042 /* GL_BLEND */);
					EaglerAdapter.glBlendFunc(770, 771);
					Tessellator tessellator = Tessellator.instance;
					EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
					tessellator.startDrawingQuads();
					int i = fontrenderer.getStringWidth(s) / 2;
					tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
					tessellator.addVertex(-i - 1, -1D, 0.0D);
					tessellator.addVertex(-i - 1, 8D, 0.0D);
					tessellator.addVertex(i + 1, 8D, 0.0D);
					tessellator.addVertex(i + 1, -1D, 0.0D);
					tessellator.draw();
					EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
					EaglerAdapter.glDepthMask(true);
					fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 0x20ffffff);
					EaglerAdapter.glEnable(2896 /* GL_LIGHTING */);
					EaglerAdapter.glDisable(3042 /* GL_BLEND */);
					EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					EaglerAdapter.glPopMatrix();
				}
			}
		}
	}

	protected void func_4015_a(EntityPlayer entityplayer, float f) {
		ItemStack itemstack = entityplayer.inventory.armorItemInSlot(3);
		if (itemstack != null && itemstack.getItem().shiftedIndex < 256) {
			EaglerAdapter.glPushMatrix();
			modelBipedMain.bipedHead.func_926_b(0.0625F);
			if (RenderBlocks.func_1219_a(Block.blocksList[itemstack.itemID].getRenderType())) {
				float f1 = 0.625F;
				EaglerAdapter.glTranslatef(0.0F, -0.25F, 0.0F);
				EaglerAdapter.glRotatef(180F, 0.0F, 1.0F, 0.0F);
				EaglerAdapter.glScalef(f1, -f1, f1);
			}
			renderManager.itemRenderer.renderItem(itemstack);
			EaglerAdapter.glPopMatrix();
		}
		/*
		if (entityplayer.username.equals("deadmau5") && loadDownloadableImageTexture(entityplayer.skinUrl, null)) {
			for (int i = 0; i < 2; i++) {
				float f2 = (entityplayer.prevRotationYaw
						+ (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f)
						- (entityplayer.prevRenderYawOffset
								+ (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * f);
				float f6 = entityplayer.prevRotationPitch
						+ (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
				EaglerAdapter.glPushMatrix();
				EaglerAdapter.glRotatef(f2, 0.0F, 1.0F, 0.0F);
				EaglerAdapter.glRotatef(f6, 1.0F, 0.0F, 0.0F);
				EaglerAdapter.glTranslatef(0.375F * (float) (i * 2 - 1), 0.0F, 0.0F);
				EaglerAdapter.glTranslatef(0.0F, -0.375F, 0.0F);
				EaglerAdapter.glRotatef(-f6, 1.0F, 0.0F, 0.0F);
				EaglerAdapter.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
				float f7 = 1.333333F;
				EaglerAdapter.glScalef(f7, f7, f7);
				modelBipedMain.renderEars(0.0625F);
				EaglerAdapter.glPopMatrix();
			}

		}
		*/
		/*
		if (loadDownloadableImageTexture(entityplayer.field_20067_q, null)) {
			EaglerAdapter.glPushMatrix();
			EaglerAdapter.glTranslatef(0.0F, 0.0F, 0.125F);
			double d = (entityplayer.field_20066_r
					+ (entityplayer.field_20063_u - entityplayer.field_20066_r) * (double) f)
					- (entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double) f);
			double d1 = (entityplayer.field_20065_s
					+ (entityplayer.field_20062_v - entityplayer.field_20065_s) * (double) f)
					- (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double) f);
			double d2 = (entityplayer.field_20064_t
					+ (entityplayer.field_20061_w - entityplayer.field_20064_t) * (double) f)
					- (entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double) f);
			float f8 = entityplayer.prevRenderYawOffset
					+ (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * f;
			double d3 = MathHelper.sin((f8 * 3.141593F) / 180F);
			double d4 = -MathHelper.cos((f8 * 3.141593F) / 180F);
			float f9 = (float) d1 * 10F;
			if (f9 < -6F) {
				f9 = -6F;
			}
			if (f9 > 32F) {
				f9 = 32F;
			}
			float f10 = (float) (d * d3 + d2 * d4) * 100F;
			float f11 = (float) (d * d4 - d2 * d3) * 100F;
			if (f10 < 0.0F) {
				f10 = 0.0F;
			}
			float f12 = entityplayer.field_775_e + (entityplayer.field_774_f - entityplayer.field_775_e) * f;
			f9 += MathHelper
					.sin((entityplayer.prevDistanceWalkedModified
							+ (entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified) * f) * 6F)
					* 32F * f12;
			EaglerAdapter.glRotatef(6F + f10 / 2.0F + f9, 1.0F, 0.0F, 0.0F);
			EaglerAdapter.glRotatef(f11 / 2.0F, 0.0F, 0.0F, 1.0F);
			EaglerAdapter.glRotatef(-f11 / 2.0F, 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			modelBipedMain.renderCloak(0.0625F);
			EaglerAdapter.glPopMatrix();
		}
		*/
		ItemStack itemstack1 = entityplayer.inventory.getCurrentItem();
		if (itemstack1 != null) {
			EaglerAdapter.glPushMatrix();
			modelBipedMain.bipedRightArm.func_926_b(0.0625F);
			EaglerAdapter.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			if (entityplayer.fishEntity != null) {
				itemstack1 = new ItemStack(Item.stick);
			}
			if (itemstack1.itemID < 256
					&& RenderBlocks.func_1219_a(Block.blocksList[itemstack1.itemID].getRenderType())) {
				float f3 = 0.5F;
				EaglerAdapter.glTranslatef(0.0F, 0.1875F, -0.3125F);
				f3 *= 0.75F;
				EaglerAdapter.glRotatef(20F, 1.0F, 0.0F, 0.0F);
				EaglerAdapter.glRotatef(45F, 0.0F, 1.0F, 0.0F);
				EaglerAdapter.glScalef(f3, -f3, f3);
			} else if (Item.itemsList[itemstack1.itemID].isFull3D()) {
				float f4 = 0.625F;
				if (Item.itemsList[itemstack1.itemID].shouldRotateAroundWhenRendering()) {
					EaglerAdapter.glRotatef(180F, 0.0F, 0.0F, 1.0F);
					EaglerAdapter.glTranslatef(0.0F, -0.125F, 0.0F);
				}
				EaglerAdapter.glTranslatef(0.0F, 0.1875F, 0.0F);
				EaglerAdapter.glScalef(f4, -f4, f4);
				EaglerAdapter.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
				EaglerAdapter.glRotatef(45F, 0.0F, 1.0F, 0.0F);
			} else {
				float f5 = 0.375F;
				EaglerAdapter.glTranslatef(0.25F, 0.1875F, -0.1875F);
				EaglerAdapter.glScalef(f5, f5, f5);
				EaglerAdapter.glRotatef(60F, 0.0F, 0.0F, 1.0F);
				EaglerAdapter.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
				EaglerAdapter.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			}
			renderManager.itemRenderer.renderItem(itemstack1);
			EaglerAdapter.glPopMatrix();
		}
	}

	protected void func_186_b(EntityPlayer entityplayer, float f) {
		float f1 = 0.9375F;
		EaglerAdapter.glScalef(f1, f1, f1);
	}

	public void drawFirstPersonHand() {
		modelBipedMain.onGround = 0.0F;
		modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		modelBipedMain.bipedRightArm.render(0.0625F);
	}

	protected void func_22016_b(EntityPlayer entityplayer, double d, double d1, double d2) {
		if (entityplayer.isEntityAlive() && entityplayer.isPlayerSleeping()) {
			super.func_22012_b(entityplayer, d + (double) entityplayer.field_22063_x,
					d1 + (double) entityplayer.field_22062_y, d2 + (double) entityplayer.field_22061_z);
		} else {
			super.func_22012_b(entityplayer, d, d1, d2);
		}
	}

	protected void func_22017_a(EntityPlayer entityplayer, float f, float f1, float f2) {
		if (entityplayer.isEntityAlive() && entityplayer.isPlayerSleeping()) {
			EaglerAdapter.glRotatef(entityplayer.func_22059_J(), 0.0F, 1.0F, 0.0F);
			EaglerAdapter.glRotatef(func_172_a(entityplayer), 0.0F, 0.0F, 1.0F);
			EaglerAdapter.glRotatef(270F, 0.0F, 1.0F, 0.0F);
		} else {
			super.func_21004_a(entityplayer, f, f1, f2);
		}
	}

	protected void func_22014_a(EntityLiving entityliving, double d, double d1, double d2) {
		func_22015_a((EntityPlayer) entityliving, d, d1, d2);
	}

	protected void preRenderCallback(EntityLiving entityliving, float f) {
		func_186_b((EntityPlayer) entityliving, f);
	}

	protected boolean shouldRenderPass(EntityLiving entityliving, int i, float f) {
		return setArmorModel((EntityPlayer) entityliving, i, f);
	}

	protected void renderEquippedItems(EntityLiving entityliving, float f) {
		func_4015_a((EntityPlayer) entityliving, f);
	}

	protected void func_21004_a(EntityLiving entityliving, float f, float f1, float f2) {
		func_22017_a((EntityPlayer) entityliving, f, f1, f2);
	}

	protected void func_22012_b(EntityLiving entityliving, double d, double d1, double d2) {
		func_22016_b((EntityPlayer) entityliving, d, d1, d2);
	}

	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		func_188_a((EntityPlayer) entityliving, d, d1, d2, f, f1);
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_188_a((EntityPlayer) entity, d, d1, d2, f, f1);
	}

	private ModelBiped modelBipedMain;
	private ModelBiped modelArmorChestplate;
	private ModelBiped modelArmor;
	
	private static final TextureLocation defaultPlayerSkin = new TextureLocation("/mob/char.png");
	
	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		RenderEngine re = Minecraft.getMinecraft().renderEngine;
		if(s == null) {
			defaultPlayerSkin.bindTexture();
		}else if(s.equals("SPSkin")) {
			if(EaglerProfile.presetSkinId < 0) {
				re.bindTexture(EaglerProfile.skins.get(EaglerProfile.customSkinId).glTex);
			}else {
				EaglerProfile.defaultOptionsTextures[EaglerProfile.presetSkinId].bindTexture();
			}
		}else if(s.startsWith("MPSkin")) {
			String un = s.substring(6);
			UserSkin us = EaglerProfile.getUserSkin(un);
			if(us == null) {
				if(!EaglerProfile.skinRequestPending(un)) {
					World w = Minecraft.getMinecraft().theWorld;
					if(w != null && (w instanceof WorldClient)) {
						try {
							ByteArrayOutputStream bao = new ByteArrayOutputStream();
							DataOutputStream dao = new DataOutputStream(bao);
							dao.writeShort(EaglerProfile.beginSkinRequest(un));
							dao.writeUTF(un);
							((WorldClient)w).sendPacket(new Packet69EaglercraftData("EAG|RequestPlayerSkin", bao.toByteArray()));
						}catch(IOException exx) {
							// ?
						}
					}
				}
				defaultPlayerSkin.bindTexture();
			}else {
				EnumSkinType st = us.getSkinType();
				if(st == EnumSkinType.PRESET) {
					EaglerProfile.defaultOptionsTextures[us.getSkin()].bindTexture();
				}else if(st == EnumSkinType.CUSTOM_LEGACY){
					re.bindTexture(us.getTexture());
				}else {
					defaultPlayerSkin.bindTexture();
				}
			}
		}else {
			defaultPlayerSkin.bindTexture();
		}
		return true;
	}

}
