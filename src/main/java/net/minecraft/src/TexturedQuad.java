package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

import net.lax1dude.eaglercraft.adapter.Tessellator;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class TexturedQuad {

	public TexturedQuad(PositionTexureVertex apositiontexurevertex[]) {
		nVertices = 0;
		invertNormal = false;
		vertexPositions = apositiontexurevertex;
		nVertices = apositiontexurevertex.length;
	}

	public TexturedQuad(PositionTexureVertex apositiontexurevertex[], int i, int j, int k, int l) {
		this(apositiontexurevertex);
		float f = 0.0015625F;
		float f1 = 0.003125F;
		apositiontexurevertex[0] = apositiontexurevertex[0].setTexturePosition((float) k / 64F - f,
				(float) j / 32F + f1);
		apositiontexurevertex[1] = apositiontexurevertex[1].setTexturePosition((float) i / 64F + f,
				(float) j / 32F + f1);
		apositiontexurevertex[2] = apositiontexurevertex[2].setTexturePosition((float) i / 64F + f,
				(float) l / 32F - f1);
		apositiontexurevertex[3] = apositiontexurevertex[3].setTexturePosition((float) k / 64F - f,
				(float) l / 32F - f1);
	}

	public void flipFace() {
		PositionTexureVertex apositiontexurevertex[] = new PositionTexureVertex[vertexPositions.length];
		for (int i = 0; i < vertexPositions.length; i++) {
			apositiontexurevertex[i] = vertexPositions[vertexPositions.length - i - 1];
		}

		vertexPositions = apositiontexurevertex;
	}

	public void draw(Tessellator tessellator, float f) {
		Vec3D vec3d = vertexPositions[1].vector3D.subtract(vertexPositions[0].vector3D);
		Vec3D vec3d1 = vertexPositions[1].vector3D.subtract(vertexPositions[2].vector3D);
		Vec3D vec3d2 = vec3d1.crossProduct(vec3d).normalize();
		tessellator.startDrawingQuads();
		if (invertNormal) {
			tessellator.setNormal(-(float) vec3d2.xCoord, -(float) vec3d2.yCoord, -(float) vec3d2.zCoord);
		} else {
			tessellator.setNormal((float) vec3d2.xCoord, (float) vec3d2.yCoord, (float) vec3d2.zCoord);
		}
		for (int i = 0; i < 4; i++) {
			PositionTexureVertex positiontexurevertex = vertexPositions[i];
			tessellator.addVertexWithUV((float) positiontexurevertex.vector3D.xCoord * f,
					(float) positiontexurevertex.vector3D.yCoord * f, (float) positiontexurevertex.vector3D.zCoord * f,
					positiontexurevertex.texturePositionX, positiontexurevertex.texturePositionY);
		}

		tessellator.draw();
	}

	public PositionTexureVertex vertexPositions[];
	public int nVertices;
	private boolean invertNormal;
}
