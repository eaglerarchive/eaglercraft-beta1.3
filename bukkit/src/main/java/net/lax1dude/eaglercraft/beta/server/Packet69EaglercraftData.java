package net.lax1dude.eaglercraft.beta.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

public class Packet69EaglercraftData extends Packet {
	
	public String type;
	public byte[] data;
	
	public Packet69EaglercraftData() {
	}
	
	public Packet69EaglercraftData(String type, byte[] data) {
		if(data.length > 65535) {
			throw new IllegalArgumentException("Packet69EaglercraftData may at most carry a 65535 byte payload");
		}
		this.type = type;
		this.data = data;
	}

	@Override
	public void a(DataInputStream datainputstream) {
		try {
			type = datainputstream.readUTF();
			data = new byte[datainputstream.readUnsignedShort()];
			datainputstream.read(data);
		}catch(IOException ex) {
			throw new RuntimeException("IOException was thrown while reading Packet69EaglercraftData!", ex);
		}
	}

	@Override
	public void a(DataOutputStream var1) {
		try {
			var1.writeUTF(type);
			var1.writeShort(data.length);
			var1.write(data);
		}catch(IOException ex) {
			throw new RuntimeException("IOException was thrown while writing Packet69EaglercraftData!", ex);
		}
	}

	@Override
	public void a(NetHandler var1) {
		var1.a(this);
	}

	@Override
	public int a() {
		return 2 + type.length() + 2 + data.length;
	}

}
