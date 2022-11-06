package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.List;

public class Packet40 extends Packet {

	public Packet40() {
	}

	public void readPacketData(DataInputStream datainputstream) throws IOException {
		entityId = datainputstream.readInt();
		field_21048_b = DataWatcher.readWatchableObjects(datainputstream);
	}

	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(entityId);
		DataWatcher.writeObjectsInListToStream(field_21048_b, dataoutputstream);
	}

	public void processPacket(NetHandler nethandler) {
		nethandler.func_21148_a(this);
	}

	public int getPacketSize() {
		return 5;
	}

	public List func_21047_b() {
		return field_21048_b;
	}

	public int entityId;
	private List field_21048_b;
}
