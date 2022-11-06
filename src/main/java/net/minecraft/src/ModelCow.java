package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelCow extends ModelQuadraped {

	public ModelCow() {
		super(12, 0.0F);
		head = new ModelRenderer(0, 0);
		head.addBox(-4F, -4F, -6F, 8, 8, 6, 0.0F);
		head.setPosition(0.0F, 4F, -8F);
		horn1 = new ModelRenderer(22, 0);
		horn1.addBox(-4F, -5F, -4F, 1, 3, 1, 0.0F);
		horn1.setPosition(0.0F, 3F, -7F);
		horn2 = new ModelRenderer(22, 0);
		horn2.addBox(3F, -5F, -4F, 1, 3, 1, 0.0F);
		horn2.setPosition(0.0F, 3F, -7F);
		udders = new ModelRenderer(52, 0);
		udders.addBox(-2F, -3F, 0.0F, 4, 6, 2, 0.0F);
		udders.setPosition(0.0F, 14F, 6F);
		udders.rotateAngleX = 1.570796F;
		body = new ModelRenderer(18, 4);
		body.addBox(-6F, -10F, -7F, 12, 18, 10, 0.0F);
		body.setPosition(0.0F, 5F, 2.0F);
		leg1.offsetX--;
		leg2.offsetX++;
		leg1.offsetZ += 0.0F;
		leg2.offsetZ += 0.0F;
		leg3.offsetX--;
		leg4.offsetX++;
		leg3.offsetZ--;
		leg4.offsetZ--;
	}

	public void render(float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(f, f1, f2, f3, f4, f5);
		horn1.render(f5);
		horn2.render(f5);
		udders.render(f5);
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5);
		horn1.rotateAngleY = head.rotateAngleY;
		horn1.rotateAngleX = head.rotateAngleX;
		horn2.rotateAngleY = head.rotateAngleY;
		horn2.rotateAngleX = head.rotateAngleX;
	}

	ModelRenderer udders;
	ModelRenderer horn1;
	ModelRenderer horn2;
}
