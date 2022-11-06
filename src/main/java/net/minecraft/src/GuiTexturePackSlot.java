package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.Tessellator;

class GuiTexturePackSlot extends GuiSlot {

	public GuiTexturePackSlot(GuiTexturePacks guitexturepacks) {
		super(GuiTexturePacks.func_22124_a(guitexturepacks), guitexturepacks.width, guitexturepacks.height, 32,
				(guitexturepacks.height - 55) + 4, 36);
		field_22265_a = guitexturepacks;
	}

	protected int func_22249_a() {
		List list = GuiTexturePacks.func_22126_b(field_22265_a).texturePackList.availableTexturePacks();
		return list.size();
	}

	protected void func_22247_a(int i, boolean flag) {
		List list = GuiTexturePacks.func_22119_c(field_22265_a).texturePackList.availableTexturePacks();
		GuiTexturePacks.func_22122_d(field_22265_a).texturePackList.setTexturePack((TexturePackBase) list.get(i));
		GuiTexturePacks.func_22117_e(field_22265_a).renderEngine.refreshTextures();
	}

	protected boolean func_22246_a(int i) {
		List list = GuiTexturePacks.func_22118_f(field_22265_a).texturePackList.availableTexturePacks();
		return GuiTexturePacks.func_22116_g(field_22265_a).texturePackList.selectedTexturePack == list.get(i);
	}

	protected int func_22245_b() {
		return func_22249_a() * 36;
	}

	protected void func_22248_c() {
		field_22265_a.drawDefaultBackground();
	}

	protected void func_22242_a(int i, int j, int k, int l, Tessellator tessellator) {
		TexturePackBase texturepackbase = (TexturePackBase) GuiTexturePacks.func_22121_h(field_22265_a).texturePackList
				.availableTexturePacks().get(i);
		texturepackbase.func_6483_c(GuiTexturePacks.func_22123_i(field_22265_a));
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(0xffffff);
		tessellator.addVertexWithUV(j, k + l, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(j + 32, k + l, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(j + 32, k, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(j, k, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		field_22265_a.drawString(GuiTexturePacks.func_22127_j(field_22265_a), texturepackbase.texturePackFileName,
				j + 32 + 2, k + 1, 0xffffff);
		field_22265_a.drawString(GuiTexturePacks.func_22120_k(field_22265_a), texturepackbase.firstDescriptionLine,
				j + 32 + 2, k + 12, 0x808080);
		field_22265_a.drawString(GuiTexturePacks.func_22125_l(field_22265_a), texturepackbase.secondDescriptionLine,
				j + 32 + 2, k + 12 + 10, 0x808080);
	}

	final GuiTexturePacks field_22265_a; /* synthetic field */
}
