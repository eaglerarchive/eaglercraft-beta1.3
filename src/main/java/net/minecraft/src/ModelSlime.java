package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelSlime extends ModelBase {

	public ModelSlime(int i) {
		field_1258_a = new ModelRenderer(0, i);
		field_1258_a.addBox(-4F, 16F, -4F, 8, 8, 8);
		if (i > 0) {
			field_1258_a = new ModelRenderer(0, i);
			field_1258_a.addBox(-3F, 17F, -3F, 6, 6, 6);
			field_1257_b = new ModelRenderer(32, 0);
			field_1257_b.addBox(-3.25F, 18F, -3.5F, 2, 2, 2);
			field_1260_c = new ModelRenderer(32, 4);
			field_1260_c.addBox(1.25F, 18F, -3.5F, 2, 2, 2);
			field_1259_d = new ModelRenderer(32, 8);
			field_1259_d.addBox(0.0F, 21F, -3.5F, 1, 1, 1);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
	}

	public void render(float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5);
		field_1258_a.render(f5);
		if (field_1257_b != null) {
			field_1257_b.render(f5);
			field_1260_c.render(f5);
			field_1259_d.render(f5);
		}
	}

	ModelRenderer field_1258_a;
	ModelRenderer field_1257_b;
	ModelRenderer field_1260_c;
	ModelRenderer field_1259_d;
}
