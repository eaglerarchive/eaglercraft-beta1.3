package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet8 extends Packet {

	public Packet8() {
	}

	public void readPacketData(DataInputStream datainputstream) throws IOException {
		healthMP = datainputstream.readShort();
	}

	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeShort(healthMP);
	}

	public void processPacket(NetHandler nethandler) {
		nethandler.handleHealth(this);
	}

	public int getPacketSize() {
		return 2;
	}

	public int healthMP;
}
