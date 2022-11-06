package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet130 extends Packet {

	public Packet130() {
		isChunkDataPacket = true;
	}

	public Packet130(int i, int j, int k, String as[]) {
		isChunkDataPacket = true;
		xPosition = i;
		yPosition = j;
		zPosition = k;
		signLines = as;
	}

	public void readPacketData(DataInputStream datainputstream) throws IOException {
		xPosition = datainputstream.readInt();
		yPosition = datainputstream.readShort();
		zPosition = datainputstream.readInt();
		signLines = new String[4];
		for (int i = 0; i < 4; i++) {
			signLines[i] = datainputstream.readUTF();
		}

	}

	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(xPosition);
		dataoutputstream.writeShort(yPosition);
		dataoutputstream.writeInt(zPosition);
		for (int i = 0; i < 4; i++) {
			dataoutputstream.writeUTF(signLines[i]);
		}

	}

	public void processPacket(NetHandler nethandler) {
		nethandler.func_20093_a(this);
	}

	public int getPacketSize() {
		int i = 0;
		for (int j = 0; j < 4; j++) {
			i += signLines[j].length();
		}

		return i;
	}

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public String signLines[];
}
