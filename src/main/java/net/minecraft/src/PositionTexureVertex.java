package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class PositionTexureVertex {

	public PositionTexureVertex(float f, float f1, float f2, float f3, float f4) {
		this(Vec3D.createVectorHelper(f, f1, f2), f3, f4);
	}

	public PositionTexureVertex setTexturePosition(float f, float f1) {
		return new PositionTexureVertex(this, f, f1);
	}

	public PositionTexureVertex(PositionTexureVertex positiontexurevertex, float f, float f1) {
		vector3D = positiontexurevertex.vector3D;
		texturePositionX = f;
		texturePositionY = f1;
	}

	public PositionTexureVertex(Vec3D vec3d, float f, float f1) {
		vector3D = vec3d;
		texturePositionX = f;
		texturePositionY = f1;
	}

	public Vec3D vector3D;
	public float texturePositionX;
	public float texturePositionY;
}
