package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.Tessellator;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelRenderer {

	public ModelRenderer(int i, int j) {
		compiled = false;
		displayList = 0;
		mirror = false;
		showModel = true;
		field_1402_i = false;
		textureOffsetX = i;
		textureOffsetY = j;
	}

	public void addBox(float f, float f1, float f2, int i, int j, int k) {
		addBox(f, f1, f2, i, j, k, 0.0F);
	}

	public void addBox(float f, float f1, float f2, int i, int j, int k, float f3) {
		corners = new PositionTexureVertex[8];
		faces = new TexturedQuad[6];
		float f4 = f + (float) i;
		float f5 = f1 + (float) j;
		float f6 = f2 + (float) k;
		f -= f3;
		f1 -= f3;
		f2 -= f3;
		f4 += f3;
		f5 += f3;
		f6 += f3;
		if (mirror) {
			float f7 = f4;
			f4 = f;
			f = f7;
		}
		PositionTexureVertex positiontexurevertex = new PositionTexureVertex(f, f1, f2, 0.0F, 0.0F);
		PositionTexureVertex positiontexurevertex1 = new PositionTexureVertex(f4, f1, f2, 0.0F, 8F);
		PositionTexureVertex positiontexurevertex2 = new PositionTexureVertex(f4, f5, f2, 8F, 8F);
		PositionTexureVertex positiontexurevertex3 = new PositionTexureVertex(f, f5, f2, 8F, 0.0F);
		PositionTexureVertex positiontexurevertex4 = new PositionTexureVertex(f, f1, f6, 0.0F, 0.0F);
		PositionTexureVertex positiontexurevertex5 = new PositionTexureVertex(f4, f1, f6, 0.0F, 8F);
		PositionTexureVertex positiontexurevertex6 = new PositionTexureVertex(f4, f5, f6, 8F, 8F);
		PositionTexureVertex positiontexurevertex7 = new PositionTexureVertex(f, f5, f6, 8F, 0.0F);
		corners[0] = positiontexurevertex;
		corners[1] = positiontexurevertex1;
		corners[2] = positiontexurevertex2;
		corners[3] = positiontexurevertex3;
		corners[4] = positiontexurevertex4;
		corners[5] = positiontexurevertex5;
		corners[6] = positiontexurevertex6;
		corners[7] = positiontexurevertex7;
		faces[0] = new TexturedQuad(
				new PositionTexureVertex[] { positiontexurevertex5, positiontexurevertex1, positiontexurevertex2,
						positiontexurevertex6 },
				textureOffsetX + k + i, textureOffsetY + k, textureOffsetX + k + i + k, textureOffsetY + k + j);
		faces[1] = new TexturedQuad(
				new PositionTexureVertex[] { positiontexurevertex, positiontexurevertex4, positiontexurevertex7,
						positiontexurevertex3 },
				textureOffsetX + 0, textureOffsetY + k, textureOffsetX + k, textureOffsetY + k + j);
		faces[2] = new TexturedQuad(
				new PositionTexureVertex[] { positiontexurevertex5, positiontexurevertex4, positiontexurevertex,
						positiontexurevertex1 },
				textureOffsetX + k, textureOffsetY + 0, textureOffsetX + k + i, textureOffsetY + k);
		faces[3] = new TexturedQuad(
				new PositionTexureVertex[] { positiontexurevertex2, positiontexurevertex3, positiontexurevertex7,
						positiontexurevertex6 },
				textureOffsetX + k + i, textureOffsetY + 0, textureOffsetX + k + i + i, textureOffsetY + k);
		faces[4] = new TexturedQuad(
				new PositionTexureVertex[] { positiontexurevertex1, positiontexurevertex, positiontexurevertex3,
						positiontexurevertex2 },
				textureOffsetX + k, textureOffsetY + k, textureOffsetX + k + i, textureOffsetY + k + j);
		faces[5] = new TexturedQuad(
				new PositionTexureVertex[] { positiontexurevertex4, positiontexurevertex5, positiontexurevertex6,
						positiontexurevertex7 },
				textureOffsetX + k + i + k, textureOffsetY + k, textureOffsetX + k + i + k + i, textureOffsetY + k + j);
		if (mirror) {
			for (int l = 0; l < faces.length; l++) {
				faces[l].flipFace();
			}

		}
	}

	public void setPosition(float f, float f1, float f2) {
		offsetX = f;
		offsetY = f1;
		offsetZ = f2;
	}

	public void render(float f) {
		if (field_1402_i) {
			return;
		}
		if (!showModel) {
			return;
		}
		if (!compiled) {
			compileDisplayList(f);
		}
		if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F) {
			EaglerAdapter.glPushMatrix();
			EaglerAdapter.glTranslatef(offsetX * f, offsetY * f, offsetZ * f);
			if (rotateAngleZ != 0.0F) {
				EaglerAdapter.glRotatef(rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleY != 0.0F) {
				EaglerAdapter.glRotatef(rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleX != 0.0F) {
				EaglerAdapter.glRotatef(rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
			}
			EaglerAdapter.glCallList(displayList);
			EaglerAdapter.glPopMatrix();
		} else if (offsetX != 0.0F || offsetY != 0.0F || offsetZ != 0.0F) {
			EaglerAdapter.glTranslatef(offsetX * f, offsetY * f, offsetZ * f);
			EaglerAdapter.glCallList(displayList);
			EaglerAdapter.glTranslatef(-offsetX * f, -offsetY * f, -offsetZ * f);
		} else {
			EaglerAdapter.glCallList(displayList);
		}
	}

	public void func_926_b(float f) {
		if (field_1402_i) {
			return;
		}
		if (!showModel) {
			return;
		}
		if (!compiled) {
			compileDisplayList(f);
		}
		if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F) {
			EaglerAdapter.glTranslatef(offsetX * f, offsetY * f, offsetZ * f);
			if (rotateAngleZ != 0.0F) {
				EaglerAdapter.glRotatef(rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleY != 0.0F) {
				EaglerAdapter.glRotatef(rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleX != 0.0F) {
				EaglerAdapter.glRotatef(rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
			}
		} else if (offsetX != 0.0F || offsetY != 0.0F || offsetZ != 0.0F) {
			EaglerAdapter.glTranslatef(offsetX * f, offsetY * f, offsetZ * f);
		}
	}

	private void compileDisplayList(float f) {
		displayList = GLAllocation.generateDisplayLists(1);
		EaglerAdapter.glNewList(displayList, 4864 /* GL_COMPILE */);
		Tessellator tessellator = Tessellator.instance;
		for (int i = 0; i < faces.length; i++) {
			faces[i].draw(tessellator, f);
		}

		EaglerAdapter.glEndList();
		compiled = true;
	}

	private PositionTexureVertex corners[];
	private TexturedQuad faces[];
	private int textureOffsetX;
	private int textureOffsetY;
	public float offsetX;
	public float offsetY;
	public float offsetZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	private boolean compiled;
	private int displayList;
	public boolean mirror;
	public boolean showModel;
	public boolean field_1402_i;
}
