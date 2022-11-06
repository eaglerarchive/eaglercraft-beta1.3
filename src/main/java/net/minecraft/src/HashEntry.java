package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

class HashEntry {

	HashEntry(int i, int j, Object obj, HashEntry hashentry) {
		valueEntry = obj;
		nextEntry = hashentry;
		hashEntry = j;
		slotHash = i;
	}

	public final int getHash() {
		return hashEntry;
	}

	public final Object getValue() {
		return valueEntry;
	}

	public final boolean equals(Object obj) {
		if (!(obj instanceof HashEntry)) {
			return false;
		}
		HashEntry hashentry = (HashEntry) obj;
		Integer integer = Integer.valueOf(getHash());
		Integer integer1 = Integer.valueOf(hashentry.getHash());
		if (integer == integer1 || integer != null && integer.equals(integer1)) {
			Object obj1 = getValue();
			Object obj2 = hashentry.getValue();
			if (obj1 == obj2 || obj1 != null && obj1.equals(obj2)) {
				return true;
			}
		}
		return false;
	}

	public final int hashCode() {
		return MCHashTable.getHash(hashEntry);
	}

	public final String toString() {
		return (new StringBuilder()).append(getHash()).append("=").append(getValue()).toString();
	}

	final int hashEntry;
	Object valueEntry;
	HashEntry nextEntry;
	final int slotHash;
}
