package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class SignModel {

	public SignModel() {
		field_1346_a = new ModelRenderer(0, 0);
		field_1346_a.addBox(-12F, -14F, -1F, 24, 12, 2, 0.0F);
		field_1345_b = new ModelRenderer(0, 14);
		field_1345_b.addBox(-1F, -2F, -1F, 2, 14, 2, 0.0F);
	}

	public void func_887_a() {
		field_1346_a.render(0.0625F);
		field_1345_b.render(0.0625F);
	}

	public ModelRenderer field_1346_a;
	public ModelRenderer field_1345_b;
}
