package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.TextureLocation;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderBiped extends RenderLiving {
	
	protected TextureLocation texture;

	public RenderBiped(ModelBiped modelbiped, float f, String tex) {
		super(modelbiped, f);
		modelBipedMain = modelbiped;
		texture = new TextureLocation(tex);
	}

	protected void renderEquippedItems(EntityLiving entityliving, float f) {
		ItemStack itemstack = entityliving.getHeldItem();
		if (itemstack != null) {
			EaglerAdapter.glPushMatrix();
			modelBipedMain.bipedRightArm.func_926_b(0.0625F);
			EaglerAdapter.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			if (itemstack.itemID < 256
					&& RenderBlocks.func_1219_a(Block.blocksList[itemstack.itemID].getRenderType())) {
				float f1 = 0.5F;
				EaglerAdapter.glTranslatef(0.0F, 0.1875F, -0.3125F);
				f1 *= 0.75F;
				EaglerAdapter.glRotatef(20F, 1.0F, 0.0F, 0.0F);
				EaglerAdapter.glRotatef(45F, 0.0F, 1.0F, 0.0F);
				EaglerAdapter.glScalef(f1, -f1, f1);
			} else if (Item.itemsList[itemstack.itemID].isFull3D()) {
				float f2 = 0.625F;
				EaglerAdapter.glTranslatef(0.0F, 0.1875F, 0.0F);
				EaglerAdapter.glScalef(f2, -f2, f2);
				EaglerAdapter.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
				EaglerAdapter.glRotatef(45F, 0.0F, 1.0F, 0.0F);
			} else {
				float f3 = 0.375F;
				EaglerAdapter.glTranslatef(0.25F, 0.1875F, -0.1875F);
				EaglerAdapter.glScalef(f3, f3, f3);
				EaglerAdapter.glRotatef(60F, 0.0F, 0.0F, 1.0F);
				EaglerAdapter.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
				EaglerAdapter.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			}
			renderManager.itemRenderer.renderItem(itemstack);
			EaglerAdapter.glPopMatrix();
		}
	}

	protected ModelBiped modelBipedMain;

	@Override
	protected boolean loadDownloadableImageTexture(String s, String s1) {
		texture.bindTexture();
		return true;
	}
}
