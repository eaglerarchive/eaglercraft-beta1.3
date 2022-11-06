package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet54 extends Packet {

	public Packet54() {
	}

	public void readPacketData(DataInputStream datainputstream) throws IOException {
		xLocation = datainputstream.readInt();
		yLocation = datainputstream.readShort();
		zLocation = datainputstream.readInt();
		instrumentType = datainputstream.read();
		pitch = datainputstream.read();
	}

	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(xLocation);
		dataoutputstream.writeShort(yLocation);
		dataoutputstream.writeInt(zLocation);
		dataoutputstream.write(instrumentType);
		dataoutputstream.write(pitch);
	}

	public void processPacket(NetHandler nethandler) {
		nethandler.func_21145_a(this);
	}

	public int getPacketSize() {
		return 12;
	}

	public int xLocation;
	public int yLocation;
	public int zLocation;
	public int instrumentType;
	public int pitch;
}
