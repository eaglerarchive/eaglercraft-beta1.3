package net.minecraft.server;

public class PacketRegister {

	public static void register(int id, Class<? extends Packet> pkt) {
		Packet.a(id, pkt);
	}
	
}
