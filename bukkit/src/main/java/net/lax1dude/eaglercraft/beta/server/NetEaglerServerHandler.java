package net.lax1dude.eaglercraft.beta.server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet255KickDisconnect;

public class NetEaglerServerHandler extends NetServerHandler {
	
	public final byte[] skinData;
	private final MinecraftServer server;

	public NetEaglerServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer, byte[] skinData) {
		super(minecraftserver, networkmanager, entityplayer);
		this.server = minecraftserver;
		this.skinData = skinData;
	}
	
	public void a(Packet69EaglercraftData pkt) {
		if(pkt.type.equals("EAG|RequestPlayerSkin")) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(pkt.data));
			try {
				int cookie = dis.readUnsignedShort();
				String un = dis.readUTF();
				if(dis.available() > 0) {
					throw new IOException("Packet has " + dis.available() + " extra bytes!");
				}
				EntityPlayer ep = server.f.i(un);
				if(ep != null) {
					if(ep.a instanceof NetEaglerServerHandler) {
						byte[] pk = ((NetEaglerServerHandler)ep.a).skinData;
						if(pk != null) {
							byte[] ret = new byte[pk.length + 2];
							ret[0] = (byte)((cookie >> 8) & 0xFF);
							ret[1] = (byte)(cookie & 0xFF);
							System.arraycopy(pk, 0, ret, 2, pk.length);
							this.b.a(new Packet69EaglercraftData("EAG|PlayerSkin", ret));
						}else {
							this.b.a(new Packet69EaglercraftData("EAG|PlayerSkin", new byte[] { (byte)((cookie >> 8) & 0xFF), (byte)(cookie & 0xFF), (byte)0, (byte)0 }));
						}
					}
				}
			}catch(IOException ex) {
				this.b.a(new Packet255KickDisconnect("Invalid Skin Request"));
				this.b.c();
				this.c = true;
			}
		}
	}

}
