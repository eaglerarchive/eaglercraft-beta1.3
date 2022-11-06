package net.lax1dude.eaglercraft.anvil;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.ArrayList;
import java.util.zip.*;

public class RegionFile {

	public RegionFile(File file) {
		field_22214_h = 0L;
		field_22212_b = file;
		func_22204_b((new StringBuilder()).append("REGION LOAD ").append(field_22212_b).toString());
		field_22215_g = 0;
		try {
			if (file.exists()) {
				field_22214_h = file.lastModified();
			}
			field_22219_c = new RandomAccessFile(file, "rw");
			if (field_22219_c.length() < 4096L) {
				for (int i = 0; i < 1024; i++) {
					field_22219_c.writeInt(0);
				}

				for (int j = 0; j < 1024; j++) {
					field_22219_c.writeInt(0);
				}

				field_22215_g += 8192;
			}
			if ((field_22219_c.length() & 4095L) != 0L) {
				for (int k = 0; (long) k < (field_22219_c.length() & 4095L); k++) {
					field_22219_c.write(0);
				}

			}
			int l = (int) field_22219_c.length() / 4096;
			field_22216_f = new ArrayList(l);
			for (int i1 = 0; i1 < l; i1++) {
				field_22216_f.add(Boolean.valueOf(true));
			}

			field_22216_f.set(0, Boolean.valueOf(false));
			field_22216_f.set(1, Boolean.valueOf(false));
			field_22219_c.seek(0L);
			for (int j1 = 0; j1 < 1024; j1++) {
				int l1 = field_22219_c.readInt();
				field_22218_d[j1] = l1;
				if (l1 == 0 || (l1 >> 8) + (l1 & 0xff) > field_22216_f.size()) {
					continue;
				}
				for (int j2 = 0; j2 < (l1 & 0xff); j2++) {
					field_22216_f.set((l1 >> 8) + j2, Boolean.valueOf(false));
				}

			}

			for (int k1 = 0; k1 < 1024; k1++) {
				int i2 = field_22219_c.readInt();
				field_22217_e[k1] = i2;
			}

		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

	public synchronized int func_22209_a() {
		int i = field_22215_g;
		field_22215_g = 0;
		return i;
	}

	private void func_22211_a(String s) {
	}

	private void func_22204_b(String s) {
		func_22211_a((new StringBuilder()).append(s).append("\n").toString());
	}

	private void func_22199_a(String s, int i, int j, String s1) {
		func_22211_a((new StringBuilder()).append("REGION ").append(s).append(" ").append(field_22212_b.getName())
				.append("[").append(i).append(",").append(j).append("] = ").append(s1).toString());
	}

	private void func_22197_a(String s, int i, int j, int k, String s1) {
		func_22211_a((new StringBuilder()).append("REGION ").append(s).append(" ").append(field_22212_b.getName())
				.append("[").append(i).append(",").append(j).append("] ").append(k).append("B = ").append(s1)
				.toString());
	}

	private void func_22201_b(String s, int i, int j, String s1) {
		func_22199_a(s, i, j, (new StringBuilder()).append(s1).append("\n").toString());
	}

	public synchronized DataInputStream func_22210_a(int i, int j) {
		if (func_22206_d(i, j)) {
			func_22201_b("READ", i, j, "out of bounds");
			return null;
		}
		try {
			int k = func_22207_e(i, j);
			if (k == 0) {
				return null;
			}
			int l = k >> 8;
			int i1 = k & 0xff;
			if (l + i1 > field_22216_f.size()) {
				func_22201_b("READ", i, j, "invalid sector");
				return null;
			}
			field_22219_c.seek(l * 4096);
			int j1 = field_22219_c.readInt();
			if (j1 > 4096 * i1) {
				func_22201_b("READ", i, j, (new StringBuilder()).append("invalid length: ").append(j1)
						.append(" > 4096 * ").append(i1).toString());
				return null;
			}
			byte byte0 = field_22219_c.readByte();
			if (byte0 == 1) {
				byte abyte0[] = new byte[j1 - 1];
				field_22219_c.read(abyte0);
				DataInputStream datainputstream = new DataInputStream(
						new GZIPInputStream(new ByteArrayInputStream(abyte0)));
				return datainputstream;
			}
			if (byte0 == 2) {
				byte abyte1[] = new byte[j1 - 1];
				field_22219_c.read(abyte1);
				DataInputStream datainputstream1 = new DataInputStream(
						new InflaterInputStream(new ByteArrayInputStream(abyte1)));
				return datainputstream1;
			} else {
				func_22201_b("READ", i, j, (new StringBuilder()).append("unknown version ").append(byte0).toString());
				return null;
			}
		} catch (IOException ioexception) {
			func_22201_b("READ", i, j, "exception");
		}
		return null;
	}

	public DataOutputStream func_22205_b(int i, int j) {
		if (func_22206_d(i, j)) {
			return null;
		} else {
			return new DataOutputStream(new DeflaterOutputStream(new RegionFileChunkBuffer(this, i, j)));
		}
	}

	protected synchronized void func_22203_a(int i, int j, byte abyte0[], int k) {
		try {
			int l = func_22207_e(i, j);
			int i1 = l >> 8;
			int l1 = l & 0xff;
			int i2 = (k + 5) / 4096 + 1;
			if (i2 >= 256) {
				return;
			}
			if (i1 != 0 && l1 == i2) {
				func_22197_a("SAVE", i, j, k, "rewrite");
				func_22200_a(i1, abyte0, k);
			} else {
				for (int j2 = 0; j2 < l1; j2++) {
					field_22216_f.set(i1 + j2, Boolean.valueOf(true));
				}

				int k2 = field_22216_f.indexOf(Boolean.valueOf(true));
				int l2 = 0;
				if (k2 != -1) {
					int i3 = k2;
					do {
						if (i3 >= field_22216_f.size()) {
							break;
						}
						if (l2 != 0) {
							if (((Boolean) field_22216_f.get(i3)).booleanValue()) {
								l2++;
							} else {
								l2 = 0;
							}
						} else if (((Boolean) field_22216_f.get(i3)).booleanValue()) {
							k2 = i3;
							l2 = 1;
						}
						if (l2 >= i2) {
							break;
						}
						i3++;
					} while (true);
				}
				if (l2 >= i2) {
					func_22197_a("SAVE", i, j, k, "reuse");
					int j1 = k2;
					func_22198_a(i, j, j1 << 8 | i2);
					for (int j3 = 0; j3 < i2; j3++) {
						field_22216_f.set(j1 + j3, Boolean.valueOf(false));
					}

					func_22200_a(j1, abyte0, k);
				} else {
					func_22197_a("SAVE", i, j, k, "grow");
					field_22219_c.seek(field_22219_c.length());
					int k1 = field_22216_f.size();
					for (int k3 = 0; k3 < i2; k3++) {
						field_22219_c.write(field_22213_a);
						field_22216_f.add(Boolean.valueOf(false));
					}

					field_22215_g += 4096 * i2;
					func_22200_a(k1, abyte0, k);
					func_22198_a(i, j, k1 << 8 | i2);
				}
			}
			func_22208_b(i, j, (int) (System.currentTimeMillis() / 1000L));
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

	private void func_22200_a(int i, byte abyte0[], int j) throws IOException {
		func_22204_b((new StringBuilder()).append(" ").append(i).toString());
		field_22219_c.seek(i * 4096);
		field_22219_c.writeInt(j + 1);
		field_22219_c.writeByte(2);
		field_22219_c.write(abyte0, 0, j);
	}

	private boolean func_22206_d(int i, int j) {
		return i < 0 || i >= 32 || j < 0 || j >= 32;
	}

	private int func_22207_e(int i, int j) {
		return field_22218_d[i + j * 32];
	}

	public boolean func_22202_c(int i, int j) {
		return func_22207_e(i, j) != 0;
	}

	private void func_22198_a(int i, int j, int k) throws IOException {
		field_22218_d[i + j * 32] = k;
		field_22219_c.seek((i + j * 32) * 4);
		field_22219_c.writeInt(k);
	}

	private void func_22208_b(int i, int j, int k) throws IOException {
		field_22217_e[i + j * 32] = k;
		field_22219_c.seek(4096 + (i + j * 32) * 4);
		field_22219_c.writeInt(k);
	}

	public void func_22196_b() throws IOException {
		field_22219_c.close();
	}

	private static final byte field_22213_a[] = new byte[4096];
	private final File field_22212_b;
	private RandomAccessFile field_22219_c;
	private final int field_22218_d[] = new int[1024];
	private final int field_22217_e[] = new int[1024];
	private ArrayList field_22216_f;
	private int field_22215_g;
	private long field_22214_h;

}
