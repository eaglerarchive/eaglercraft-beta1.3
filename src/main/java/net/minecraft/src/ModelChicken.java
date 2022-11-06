package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelChicken extends ModelBase {

	public ModelChicken() {
		byte byte0 = 16;
		field_1289_a = new ModelRenderer(0, 0);
		field_1289_a.addBox(-2F, -6F, -2F, 4, 6, 3, 0.0F);
		field_1289_a.setPosition(0.0F, -1 + byte0, -4F);
		field_1291_g = new ModelRenderer(14, 0);
		field_1291_g.addBox(-2F, -4F, -4F, 4, 2, 2, 0.0F);
		field_1291_g.setPosition(0.0F, -1 + byte0, -4F);
		field_1290_h = new ModelRenderer(14, 4);
		field_1290_h.addBox(-1F, -2F, -3F, 2, 2, 2, 0.0F);
		field_1290_h.setPosition(0.0F, -1 + byte0, -4F);
		field_1288_b = new ModelRenderer(0, 9);
		field_1288_b.addBox(-3F, -4F, -3F, 6, 8, 6, 0.0F);
		field_1288_b.setPosition(0.0F, 0 + byte0, 0.0F);
		field_1295_c = new ModelRenderer(26, 0);
		field_1295_c.addBox(-1F, 0.0F, -3F, 3, 5, 3);
		field_1295_c.setPosition(-2F, 3 + byte0, 1.0F);
		field_1294_d = new ModelRenderer(26, 0);
		field_1294_d.addBox(-1F, 0.0F, -3F, 3, 5, 3);
		field_1294_d.setPosition(1.0F, 3 + byte0, 1.0F);
		field_1293_e = new ModelRenderer(24, 13);
		field_1293_e.addBox(0.0F, 0.0F, -3F, 1, 4, 6);
		field_1293_e.setPosition(-4F, -3 + byte0, 0.0F);
		field_1292_f = new ModelRenderer(24, 13);
		field_1292_f.addBox(-1F, 0.0F, -3F, 1, 4, 6);
		field_1292_f.setPosition(4F, -3 + byte0, 0.0F);
	}

	public void render(float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5);
		field_1289_a.render(f5);
		field_1291_g.render(f5);
		field_1290_h.render(f5);
		field_1288_b.render(f5);
		field_1295_c.render(f5);
		field_1294_d.render(f5);
		field_1293_e.render(f5);
		field_1292_f.render(f5);
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		field_1289_a.rotateAngleX = -(f4 / 57.29578F);
		field_1289_a.rotateAngleY = f3 / 57.29578F;
		field_1291_g.rotateAngleX = field_1289_a.rotateAngleX;
		field_1291_g.rotateAngleY = field_1289_a.rotateAngleY;
		field_1290_h.rotateAngleX = field_1289_a.rotateAngleX;
		field_1290_h.rotateAngleY = field_1289_a.rotateAngleY;
		field_1288_b.rotateAngleX = 1.570796F;
		field_1295_c.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		field_1294_d.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		field_1293_e.rotateAngleZ = f2;
		field_1292_f.rotateAngleZ = -f2;
	}

	public ModelRenderer field_1289_a;
	public ModelRenderer field_1288_b;
	public ModelRenderer field_1295_c;
	public ModelRenderer field_1294_d;
	public ModelRenderer field_1293_e;
	public ModelRenderer field_1292_f;
	public ModelRenderer field_1291_g;
	public ModelRenderer field_1290_h;
}
