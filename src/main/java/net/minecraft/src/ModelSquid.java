package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelSquid extends ModelBase {

	public ModelSquid() {
		field_21122_b = new ModelRenderer[8];
		byte byte0 = -16;
		field_21123_a = new ModelRenderer(0, 0);
		field_21123_a.addBox(-6F, -8F, -6F, 12, 16, 12);
		field_21123_a.offsetY += 24 + byte0;
		for (int i = 0; i < field_21122_b.length; i++) {
			field_21122_b[i] = new ModelRenderer(48, 0);
			double d = ((double) i * 3.1415926535897931D * 2D) / (double) field_21122_b.length;
			float f = (float) Math.cos(d) * 5F;
			float f1 = (float) Math.sin(d) * 5F;
			field_21122_b[i].addBox(-1F, 0.0F, -1F, 2, 18, 2);
			field_21122_b[i].offsetX = f;
			field_21122_b[i].offsetZ = f1;
			field_21122_b[i].offsetY = 31 + byte0;
			d = ((double) i * 3.1415926535897931D * -2D) / (double) field_21122_b.length + 1.5707963267948966D;
			field_21122_b[i].rotateAngleY = (float) d;
		}

	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		for (int i = 0; i < field_21122_b.length; i++) {
			field_21122_b[i].rotateAngleX = f2;
		}

	}

	public void render(float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5);
		field_21123_a.render(f5);
		for (int i = 0; i < field_21122_b.length; i++) {
			field_21122_b[i].render(f5);
		}

	}

	ModelRenderer field_21123_a;
	ModelRenderer field_21122_b[];
}
