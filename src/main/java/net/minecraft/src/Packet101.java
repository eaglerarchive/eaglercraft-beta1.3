package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet101 extends Packet {

	public Packet101() {
	}

	public Packet101(int i) {
		windowId = i;
	}

	public void processPacket(NetHandler nethandler) {
		nethandler.func_20092_a(this);
	}

	public void readPacketData(DataInputStream datainputstream) throws IOException {
		windowId = datainputstream.readByte();
	}

	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeByte(windowId);
	}

	public int getPacketSize() {
		return 1;
	}

	public int windowId;
}
