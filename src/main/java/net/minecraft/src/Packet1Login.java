package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet1Login extends Packet {

	public Packet1Login() {
	}

	public Packet1Login(String s, String s1, int i) {
		username = s;
		password = s1;
		playerId = i;
	}

	public void readPacketData(DataInputStream datainputstream) throws IOException {
		playerId = datainputstream.readInt();
		username = datainputstream.readUTF();
		password = datainputstream.readUTF();
		mapSeed = datainputstream.readLong();
		dimension = datainputstream.readByte();
	}

	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeInt(playerId);
		dataoutputstream.writeUTF(username);
		dataoutputstream.writeUTF(password);
		dataoutputstream.writeLong(mapSeed);
		dataoutputstream.writeByte(dimension);
	}

	public void processPacket(NetHandler nethandler) {
		nethandler.handleLogin(this);
	}

	public int getPacketSize() {
		return 4 + username.length() + password.length() + 4 + 5;
	}

	public int playerId;
	public String username;
	public String password;
	public long mapSeed;
	public byte dimension;
}
