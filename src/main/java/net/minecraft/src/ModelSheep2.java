package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ModelSheep2 extends ModelQuadraped {

	public ModelSheep2() {
		super(12, 0.0F);
		head = new ModelRenderer(0, 0);
		head.addBox(-3F, -4F, -6F, 6, 6, 8, 0.0F);
		head.setPosition(0.0F, 6F, -8F);
		body = new ModelRenderer(28, 8);
		body.addBox(-4F, -10F, -7F, 8, 16, 6, 0.0F);
		body.setPosition(0.0F, 5F, 2.0F);
	}
}
