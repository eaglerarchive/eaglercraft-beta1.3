package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class FontAllowedCharacters {
	
	public static int isAllowed(char c) {
		int cc = (int) c;
		for(int i = 0; i < allowedChars.length; ++i) {
			if(cc == allowedChars[i]) {
				return i;
			}
		}
		return -1;
	}
	
	public static final int[] allowedChars = new int[]{
			32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,
			59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,
			86,87,88,89,90,91,92,93,94,95,39,97,98,99,100,101,102,103,104,105,106,107,108,109,
			110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,8962,199,252,
			233,226,228,224,229,231,234,235,232,239,238,236,196,197,201,230,198,244,246,242,
			251,249,255,214,220,248,163,216,215,402,225,237,243,250,241,209,170,186,191,174,
			172,189,188,161,171,187
	};
	
	public static final char field_22286_b[] = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };

}
