package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

class PacketCounter {

	private PacketCounter() {
	}

	public void func_22236_a(int i) {
		field_22238_a++;
		field_22237_b += i;
	}
	
	public PacketCounter(Object wtf) {
	}

	private int field_22238_a;
	private long field_22237_b;
}
