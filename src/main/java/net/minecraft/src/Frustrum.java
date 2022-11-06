package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class Frustrum implements ICamera {

	public Frustrum() {
		clippingHelper = ClippingHelperImplementation.getInstance();
	}

	public void setPosition(double d, double d1, double d2) {
		xPosition = d;
		yPosition = d1;
		zPosition = d2;
	}

	public boolean isBoxInFrustum(double d, double d1, double d2, double d3, double d4, double d5) {
		return clippingHelper.isBoxInFrustum(d - xPosition, d1 - yPosition, d2 - zPosition, d3 - xPosition,
				d4 - yPosition, d5 - zPosition);
	}

	public boolean isBoundingBoxInFrustum(AxisAlignedBB axisalignedbb) {
		return isBoxInFrustum(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX,
				axisalignedbb.maxY, axisalignedbb.maxZ);
	}

	private ClippingHelper clippingHelper;
	private double xPosition;
	private double yPosition;
	private double zPosition;
}
