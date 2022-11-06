package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerImage;

public class TerrainTextureManager {

	public TerrainTextureManager() {
		field_1181_a = new float[768];
		field_1180_b = new int[5120];
		field_1186_c = new int[5120];
		field_1185_d = new int[5120];
		field_1184_e = new int[5120];
		field_1183_f = new int[34];
		field_1182_g = new int[768];
		int ai[] = EaglerAdapter.loadPNG(EaglerAdapter.loadResourceBytes("/terrain.png")).data;
		for (int j = 0; j < 256; j++) {
			int k = 0;
			int l = 0;
			int i1 = 0;
			int j1 = (j % 16) * 16;
			int k1 = (j / 16) * 16;
			int l1 = 0;
			for (int i2 = 0; i2 < 16; i2++) {
				for (int j2 = 0; j2 < 16; j2++) {
					int k2 = ai[j2 + j1 + (i2 + k1) * 256];
					int l2 = k2 >> 24 & 0xff;
					if (l2 > 128) {
						k += k2 >> 16 & 0xff;
						l += k2 >> 8 & 0xff;
						i1 += k2 & 0xff;
						l1++;
					}
				}

				if (l1 == 0) {
					l1++;
				}
				field_1181_a[j * 3 + 0] = k / l1;
				field_1181_a[j * 3 + 1] = l / l1;
				field_1181_a[j * 3 + 2] = i1 / l1;
			}

		}
		for (int i = 0; i < 256; i++) {
			if (Block.blocksList[i] != null) {
				field_1182_g[i * 3 + 0] = Block.blocksList[i].getBlockTextureFromSide(1);
				field_1182_g[i * 3 + 1] = Block.blocksList[i].getBlockTextureFromSide(2);
				field_1182_g[i * 3 + 2] = Block.blocksList[i].getBlockTextureFromSide(3);
			}
		}

	}

	private void func_800_a() {
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 160; j++) {
				int k = i + j * 32;
				if (field_1186_c[k] == 0) {
					field_1180_b[k] = 0;
				}
				if (field_1185_d[k] <= field_1186_c[k]) {
					continue;
				}
				int l = field_1180_b[k] >> 24 & 0xff;
				field_1180_b[k] = ((field_1180_b[k] & 0xfefefe) >> 1) + field_1184_e[k];
				if (l < 128) {
					field_1180_b[k] = 0x80000000 + field_1184_e[k] * 2;
				} else {
					field_1180_b[k] |= 0xff000000;
				}
			}

		}

	}

	private float field_1181_a[];
	private int field_1180_b[];
	private int field_1186_c[];
	private int field_1185_d[];
	private int field_1184_e[];
	private int field_1183_f[];
	private int field_1182_g[];
}
