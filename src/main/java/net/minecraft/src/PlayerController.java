package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;

public class PlayerController {

	public PlayerController(Minecraft minecraft) {
		field_1064_b = false;
		mc = minecraft;
	}

	public void func_717_a(World world) {
	}

	public void clickBlock(int i, int j, int k, int l) {
		sendBlockRemoved(i, j, k, l);
	}

	public boolean sendBlockRemoved(int i, int j, int k, int l) {
		mc.effectRenderer.addBlockDestroyEffects(i, j, k);
		World world = mc.theWorld;
		Block block = Block.blocksList[world.getBlockId(i, j, k)];
		int i1 = world.getBlockMetadata(i, j, k);
		boolean flag = world.setBlockWithNotify(i, j, k, 0);
		if (block != null && flag) {
			mc.sndManager.playSound(block.stepSound.func_1146_a(), (float) i + 0.5F, (float) j + 0.5F, (float) k + 0.5F,
					(block.stepSound.func_1147_b() + 1.0F) / 2.0F, block.stepSound.func_1144_c() * 0.8F);
			block.onBlockDestroyedByPlayer(world, i, j, k, i1);
		}
		return flag;
	}

	public void sendBlockRemoving(int i, int j, int k, int l) {
	}

	public void func_6468_a() {
	}

	public void setPartialTime(float f) {
	}

	public float getBlockReachDistance() {
		return 5F;
	}

	public boolean sendUseItem(EntityPlayer entityplayer, World world, ItemStack itemstack) {
		int i = itemstack.stackSize;
		ItemStack itemstack1 = itemstack.useItemRightClick(world, entityplayer);
		if (itemstack1 != itemstack || itemstack1 != null && itemstack1.stackSize != i) {
			entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack1;
			if (itemstack1.stackSize == 0) {
				entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
			}
			return true;
		} else {
			return false;
		}
	}

	public void flipPlayer(EntityPlayer entityplayer) {
	}

	public void updateController() {
	}

	public boolean shouldDrawHUD() {
		return true;
	}

	public void func_6473_b(EntityPlayer entityplayer) {
	}

	public boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k,
			int l) {
		int i1 = world.getBlockId(i, j, k);
		if (i1 > 0 && Block.blocksList[i1].blockActivated(world, i, j, k, entityplayer)) {
			return true;
		}
		if (itemstack == null) {
			return false;
		} else {
			return itemstack.useItem(entityplayer, world, i, j, k, l);
		}
	}

	public EntityPlayer func_4087_b(World world) {
		return new EntityPlayerSP(mc, world, mc.session, world.worldProvider.worldType);
	}

	public void func_6475_a(EntityPlayer entityplayer, Entity entity) {
		entityplayer.useCurrentItemOnEntity(entity);
	}

	public void func_6472_b(EntityPlayer entityplayer, Entity entity) {
		entityplayer.attackTargetEntityWithCurrentItem(entity);
	}

	public ItemStack func_20085_a(int i, int j, int k, EntityPlayer entityplayer) {
		return entityplayer.craftingInventory.func_20116_a(j, k, entityplayer);
	}

	public void func_20086_a(int i, EntityPlayer entityplayer) {
		entityplayer.craftingInventory.onCraftGuiClosed(entityplayer);
		entityplayer.craftingInventory = entityplayer.inventorySlots;
	}

	protected final Minecraft mc;
	public boolean field_1064_b;
}
