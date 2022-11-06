package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
	public void readPacketData(DataInputStream datainputstream) throws IOException {
		type = datainputstream.readUTF();
		data = new byte[datainputstream.readUnsignedShort()];
		datainputstream.read(data);
	}

	@Override
	public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeUTF(type);
		dataoutputstream.writeShort(data.length);
		dataoutputstream.write(data);
	}

	@Override
	public void processPacket(NetHandler nethandler) {
		nethandler.handleEaglercraftData(this);
	}

	@Override
	public int getPacketSize() {
		return 2 + type.length() + 2 + data.length;
	}

}
