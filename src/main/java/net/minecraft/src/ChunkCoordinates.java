package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ChunkCoordinates implements Comparable {

	public ChunkCoordinates() {
	}

	public ChunkCoordinates(int i, int j, int k) {
		field_22395_a = i;
		field_22394_b = j;
		field_22396_c = k;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof ChunkCoordinates)) {
			return false;
		} else {
			ChunkCoordinates chunkcoordinates = (ChunkCoordinates) obj;
			return field_22395_a == chunkcoordinates.field_22395_a && field_22394_b == chunkcoordinates.field_22394_b
					&& field_22396_c == chunkcoordinates.field_22396_c;
		}
	}

	public int hashCode() {
		return field_22395_a + field_22396_c << 8 + field_22394_b << 16;
	}

	public int func_22393_a(ChunkCoordinates chunkcoordinates) {
		if (field_22394_b == chunkcoordinates.field_22394_b) {
			if (field_22396_c == chunkcoordinates.field_22396_c) {
				return field_22395_a - chunkcoordinates.field_22395_a;
			} else {
				return field_22396_c - chunkcoordinates.field_22396_c;
			}
		} else {
			return field_22394_b - chunkcoordinates.field_22394_b;
		}
	}

	public int compareTo(Object obj) {
		return func_22393_a((ChunkCoordinates) obj);
	}

	public int field_22395_a;
	public int field_22394_b;
	public int field_22396_c;
}
