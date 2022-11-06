package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelSpider extends ModelBase {

	public ModelSpider() {
		float f = 0.0F;
		int i = 15;
		field_1255_a = new ModelRenderer(32, 4);
		field_1255_a.addBox(-4F, -4F, -8F, 8, 8, 8, f);
		field_1255_a.setPosition(0.0F, 0 + i, -3F);
		field_1254_b = new ModelRenderer(0, 0);
		field_1254_b.addBox(-3F, -3F, -3F, 6, 6, 6, f);
		field_1254_b.setPosition(0.0F, i, 0.0F);
		field_1253_c = new ModelRenderer(0, 12);
		field_1253_c.addBox(-5F, -4F, -6F, 10, 8, 12, f);
		field_1253_c.setPosition(0.0F, 0 + i, 9F);
		field_1252_d = new ModelRenderer(18, 0);
		field_1252_d.addBox(-15F, -1F, -1F, 16, 2, 2, f);
		field_1252_d.setPosition(-4F, 0 + i, 2.0F);
		field_1251_e = new ModelRenderer(18, 0);
		field_1251_e.addBox(-1F, -1F, -1F, 16, 2, 2, f);
		field_1251_e.setPosition(4F, 0 + i, 2.0F);
		field_1250_f = new ModelRenderer(18, 0);
		field_1250_f.addBox(-15F, -1F, -1F, 16, 2, 2, f);
		field_1250_f.setPosition(-4F, 0 + i, 1.0F);
		field_1249_g = new ModelRenderer(18, 0);
		field_1249_g.addBox(-1F, -1F, -1F, 16, 2, 2, f);
		field_1249_g.setPosition(4F, 0 + i, 1.0F);
		field_1248_h = new ModelRenderer(18, 0);
		field_1248_h.addBox(-15F, -1F, -1F, 16, 2, 2, f);
		field_1248_h.setPosition(-4F, 0 + i, 0.0F);
		field_1247_i = new ModelRenderer(18, 0);
		field_1247_i.addBox(-1F, -1F, -1F, 16, 2, 2, f);
		field_1247_i.setPosition(4F, 0 + i, 0.0F);
		field_1246_j = new ModelRenderer(18, 0);
		field_1246_j.addBox(-15F, -1F, -1F, 16, 2, 2, f);
		field_1246_j.setPosition(-4F, 0 + i, -1F);
		field_1245_m = new ModelRenderer(18, 0);
		field_1245_m.addBox(-1F, -1F, -1F, 16, 2, 2, f);
		field_1245_m.setPosition(4F, 0 + i, -1F);
	}

	public void render(float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5);
		field_1255_a.render(f5);
		field_1254_b.render(f5);
		field_1253_c.render(f5);
		field_1252_d.render(f5);
		field_1251_e.render(f5);
		field_1250_f.render(f5);
		field_1249_g.render(f5);
		field_1248_h.render(f5);
		field_1247_i.render(f5);
		field_1246_j.render(f5);
		field_1245_m.render(f5);
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		field_1255_a.rotateAngleY = f3 / 57.29578F;
		field_1255_a.rotateAngleX = f4 / 57.29578F;
		float f6 = 0.7853982F;
		field_1252_d.rotateAngleZ = -f6;
		field_1251_e.rotateAngleZ = f6;
		field_1250_f.rotateAngleZ = -f6 * 0.74F;
		field_1249_g.rotateAngleZ = f6 * 0.74F;
		field_1248_h.rotateAngleZ = -f6 * 0.74F;
		field_1247_i.rotateAngleZ = f6 * 0.74F;
		field_1246_j.rotateAngleZ = -f6;
		field_1245_m.rotateAngleZ = f6;
		float f7 = -0F;
		float f8 = 0.3926991F;
		field_1252_d.rotateAngleY = f8 * 2.0F + f7;
		field_1251_e.rotateAngleY = -f8 * 2.0F - f7;
		field_1250_f.rotateAngleY = f8 * 1.0F + f7;
		field_1249_g.rotateAngleY = -f8 * 1.0F - f7;
		field_1248_h.rotateAngleY = -f8 * 1.0F + f7;
		field_1247_i.rotateAngleY = f8 * 1.0F - f7;
		field_1246_j.rotateAngleY = -f8 * 2.0F + f7;
		field_1245_m.rotateAngleY = f8 * 2.0F - f7;
		float f9 = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
		float f10 = -(MathHelper.cos(f * 0.6662F * 2.0F + 3.141593F) * 0.4F) * f1;
		float f11 = -(MathHelper.cos(f * 0.6662F * 2.0F + 1.570796F) * 0.4F) * f1;
		float f12 = -(MathHelper.cos(f * 0.6662F * 2.0F + 4.712389F) * 0.4F) * f1;
		float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
		float f14 = Math.abs(MathHelper.sin(f * 0.6662F + 3.141593F) * 0.4F) * f1;
		float f15 = Math.abs(MathHelper.sin(f * 0.6662F + 1.570796F) * 0.4F) * f1;
		float f16 = Math.abs(MathHelper.sin(f * 0.6662F + 4.712389F) * 0.4F) * f1;
		field_1252_d.rotateAngleY += f9;
		field_1251_e.rotateAngleY += -f9;
		field_1250_f.rotateAngleY += f10;
		field_1249_g.rotateAngleY += -f10;
		field_1248_h.rotateAngleY += f11;
		field_1247_i.rotateAngleY += -f11;
		field_1246_j.rotateAngleY += f12;
		field_1245_m.rotateAngleY += -f12;
		field_1252_d.rotateAngleZ += f13;
		field_1251_e.rotateAngleZ += -f13;
		field_1250_f.rotateAngleZ += f14;
		field_1249_g.rotateAngleZ += -f14;
		field_1248_h.rotateAngleZ += f15;
		field_1247_i.rotateAngleZ += -f15;
		field_1246_j.rotateAngleZ += f16;
		field_1245_m.rotateAngleZ += -f16;
	}

	public ModelRenderer field_1255_a;
	public ModelRenderer field_1254_b;
	public ModelRenderer field_1253_c;
	public ModelRenderer field_1252_d;
	public ModelRenderer field_1251_e;
	public ModelRenderer field_1250_f;
	public ModelRenderer field_1249_g;
	public ModelRenderer field_1248_h;
	public ModelRenderer field_1247_i;
	public ModelRenderer field_1246_j;
	public ModelRenderer field_1245_m;
}
