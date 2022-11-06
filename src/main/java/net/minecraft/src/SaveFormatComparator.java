package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class SaveFormatComparator implements Comparable {

	public SaveFormatComparator(String s, String s1, long l, long l1, boolean flag) {
		field_22166_a = s;
		field_22165_b = s1;
		field_22169_c = l;
		field_22168_d = l1;
		field_22167_e = flag;
	}

	public String func_22164_a() {
		return field_22166_a;
	}

	public String func_22162_b() {
		return field_22165_b;
	}

	public long func_22159_c() {
		return field_22168_d;
	}

	public boolean func_22161_d() {
		return field_22167_e;
	}

	public long func_22163_e() {
		return field_22169_c;
	}

	public int func_22160_a(SaveFormatComparator saveformatcomparator) {
		if (field_22169_c < saveformatcomparator.field_22169_c) {
			return 1;
		}
		if (field_22169_c > saveformatcomparator.field_22169_c) {
			return -1;
		} else {
			return field_22166_a.compareTo(saveformatcomparator.field_22166_a);
		}
	}

	public int compareTo(Object obj) {
		return func_22160_a((SaveFormatComparator) obj);
	}

	private final String field_22166_a;
	private final String field_22165_b;
	private final long field_22169_c;
	private final long field_22168_d;
	private final boolean field_22167_e;
}
