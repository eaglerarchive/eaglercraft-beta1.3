package net.minecraft.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

import org.java_websocket.WebSocket;

import net.lax1dude.eaglercraft.beta.server.Base64;
import net.lax1dude.eaglercraft.beta.server.EaglercraftServer;
import net.lax1dude.eaglercraft.beta.server.NetEaglerServerHandler;
import net.lax1dude.eaglercraft.beta.server.Packet69EaglercraftData;
import net.lax1dude.eaglercraft.beta.server.PasswordManager;
import net.lax1dude.eaglercraft.beta.server.PasswordManager.PasswordEntry;
import net.lax1dude.eaglercraft.beta.server.SHA1Digest;

public class NetLoginHandler extends NetHandler {
	public static Logger a = Logger.getLogger("Minecraft");
	private static Random d = new Random();
	public NetworkManager b;
	public boolean c = false;
	private MinecraftServer e;
	private int f = 0;
	private int hf = 0;
	private String g = null;
	private Packet1Login h = null;
	private String i = "";
	private String wsUsername = null;
	private PasswordEntry passEntry = null;
	private byte[] sentSalt = null;
	private byte[] skinData = null;
	
	private boolean isWebsocket() {
		return b instanceof EaglercraftWebsocketNetworkManager;
	}

	public NetLoginHandler(MinecraftServer minecraftserver, Socket socket, String s) throws IOException {
		this.e = minecraftserver;
		EaglercraftVanillaNetworkManager mgr = new EaglercraftVanillaNetworkManager(socket, s, this);
		mgr.d = 0;
		this.b = mgr;
	}

	public NetLoginHandler(MinecraftServer minecraftserver, WebSocket socket) {
		this.e = minecraftserver;
		this.b = new EaglercraftWebsocketNetworkManager(socket, this);
	}

	public void a() {
		++this.f;
		if (this.h != null) {
			if(this.f - this.hf > 20) {
				this.b(this.h);
				this.h = null;
			}
		}

		if (this.h == null && this.hf == 0 && this.f >= (isWebsocket() ? 60 : 600)) {
			this.a("Took too long to log in");
		} else {
			this.b.a();
		}

	}

	public void a(String s) {
		try {
			a.info("Disconnecting " + this.b() + ": " + s);
			this.b.a(new Packet255KickDisconnect(s));
			this.b.c();
			this.c = true;
		} catch (Exception var3) {
			var3.printStackTrace();
		}

	}

	public void a(Packet2Handshake packet2handshake) {
		if(isWebsocket()) {
			wsUsername = packet2handshake.a;
			if(!validateUsername(wsUsername)) {
				this.a("Invalid username!");
				return;
			}
			if(EaglercraftServer.config.enablePasswordLogin()) {
				passEntry = PasswordManager.load(wsUsername);
				if(passEntry == null) {
					if(EaglercraftServer.config.requirePasswordLogin()) {
						this.a("You're not registered on this server!");
						return;
					}else {
						sentSalt = new byte[0];
						this.b.a(new Packet2Handshake("NULL"));
					}
				}else {
					sentSalt = new byte[9];
					synchronized(PasswordManager.rand) {
						PasswordManager.rand.nextBytes(sentSalt);
					}
					byte[] sendHash = new byte[18];
					System.arraycopy(passEntry.salt, 0, sendHash, 0, 9);
					System.arraycopy(sentSalt, 0, sendHash, 9, 9);
					this.b.a(new Packet2Handshake(Base64.encodeBase64String(sendHash)));
				}
			}else {
				sentSalt = new byte[0];
				this.b.a(new Packet2Handshake("NULL"));
			}
		}else {
			if (this.e.l) {
				this.i = Long.toHexString(d.nextLong());
				this.b.a(new Packet2Handshake(this.i));
			} else {
				this.b.a(new Packet2Handshake("-"));
			}
		}
	}

	public static boolean validateUsername(String wsUsername2) {
		if (wsUsername2.length() < 3 || wsUsername2.length() > 16) {
			return false;
		}else if(!wsUsername2.equals(wsUsername2.replaceAll("[^A-Za-z0-9\\-_]", "_").trim())) {
			return false;
		}else {
			return true;
		}
	}

	public void a(Packet1Login packet1login) {
		if (packet1login.a != 9) {
			if (packet1login.a > 9) {
				this.a("Outdated server!");
			} else {
				this.a("Outdated client!");
			}
			return;
		}else {
			if(isWebsocket()) {
				if(wsUsername == null || sentSalt == null || !wsUsername.equals(packet1login.b)) {
					this.a("Invalid login!");
				}else {
					this.g = packet1login.b;
					if(packet1login.c.equalsIgnoreCase("NULL")) {
						if(sentSalt.length == 0) {
							this.h = packet1login;
						}else {
							this.a(EaglercraftServer.config.requirePasswordLogin() ? "A password is required to join this server!" : "This username requires a password to join!");
						}
					}else {
						SHA1Digest dg = new SHA1Digest();
						dg.update(PasswordManager.eaglerSalt, 0, PasswordManager.eaglerSalt.length);
						dg.update(sentSalt, 0, 9);
						dg.update(passEntry.password, 0, passEntry.password.length);
						byte[] o = new byte[20];
						dg.doFinal(o, 0);
						
						byte[] hsh = Base64.decodeBase64(packet1login.c.replace('-', '+').replace('_', '/'));
						if(Arrays.equals(o, hsh)) {
							packet1login.c = "-";
							this.h = packet1login;
							this.hf = f;
						}else {
							this.a("Wrong password!");
						}
					}
				}
			}else {
				if(!validateUsername(packet1login.b)) {
					this.a("Invalid username!");
					return;
				}
				this.g = packet1login.b;
				if (!this.e.l) {
					this.b(packet1login);
				} else {
					(new ThreadLoginVerifier(this, packet1login)).start();
				}
			}
		}
	}
	
	public static final int SKIN_DATA_SIZE = 64*32*4;

	public void a(Packet69EaglercraftData pkt) {
		if(isWebsocket()) {
			if(pkt.type.equals("EAG|MySkin")) {
				String inv = "Invalid skin";
				if(pkt.data.length < 2) {
					this.a(inv);
				}else {
					int type = (int)pkt.data[0] & 0xFF;
					if(type == 0) {
						if(pkt.data.length == 2) {
							skinData = pkt.data;
						}else {
							this.a(inv);
						}
					}else if(type == 1) {
						if(pkt.data.length == SKIN_DATA_SIZE + 1) {
							skinData = pkt.data;
						}else {
							this.a(inv);
						}
					}else {
						this.a(inv);
					}
				}
			}
		}else {
			a((Packet)pkt);
		}
	}
	
	public void b(Packet1Login packet1login) {
		EntityPlayer entityplayer = this.e.f.a(this, packet1login.b, packet1login.c);
		if (entityplayer != null) {
			a.info(this.b() + " logged in with entity id " + entityplayer.id);
			NetServerHandler netserverhandler = isWebsocket() ? new NetEaglerServerHandler(this.e, this.b, entityplayer, skinData) :
				new NetServerHandler(this.e, this.b, entityplayer);
			ChunkCoordinates chunkcoordinates = entityplayer.world.l();
			netserverhandler.b(
					new Packet1Login("", "", entityplayer.id, entityplayer.world.j(), (byte) entityplayer.world.m.g));
			netserverhandler.b(new Packet6SpawnPosition(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c));
			this.e.f.a(new Packet3Chat("§e" + entityplayer.name + " joined the game."));
			this.e.f.a(entityplayer);
			netserverhandler.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw,
					entityplayer.pitch);
			this.e.c.a(netserverhandler);
			netserverhandler.b(new Packet4UpdateTime(entityplayer.world.k()));
			entityplayer.l();
		}

		this.c = true;
	}

	public void a(String s, Object[] aobject) {
		a.info(this.b() + " was kicked while logging in: " + s);
		this.c = true;
	}

	public void a(Packet packet) {
		this.a("Protocol error");
	}

	public String b() {
		if(isWebsocket()) {
			return this.g != null ? this.g + " [websocket]" : "[websocket]";
		}else {
			return this.g != null ? this.g + " [" + this.b.b().toString() + "]" : this.b.b().toString();
		}
	}

	static String a(NetLoginHandler netloginhandler) {
		return netloginhandler.i;
	}

	static Packet1Login a(NetLoginHandler netloginhandler, Packet1Login packet1login) {
		return netloginhandler.h = packet1login;
	}
}