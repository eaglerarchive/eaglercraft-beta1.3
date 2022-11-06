package net.lax1dude.eaglercraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.RenderEngine;

public class EaglerProfile {
	
	public static class EaglerProfileSkin {
		public String name;
		public byte[] data;
		public boolean slim;
		public int glTex;
		public EaglerProfileSkin(String name, byte[] data, boolean slim, int glTex) {
			this.name = name;
			this.data = data;
			this.slim = slim;
			this.glTex = glTex;
		}
	}

	public static String username;
	public static int presetSkinId;
	public static int customSkinId;
	
	public static String myChannel;
	
	public static final int SKIN_DATA_SIZE = 64*32*4;
	public static ArrayList<EaglerProfileSkin> skins = new ArrayList();
	
	public static final EaglercraftRandom rand;
	
	public static byte[] getSelfSkinPacket() {
		if(presetSkinId == -1) {
			byte[] d = skins.get(customSkinId).data;
			byte[] d2 = new byte[1 + d.length];
			d2[0] = (byte) 1;
			System.arraycopy(d, 0, d2, 1, d.length);
			return d2;
		}else {
			return new byte[] { (byte)0, (byte)presetSkinId };
		}
	}
	
	public static String[] concatArrays(String[] a, String[] b) {
		String[] r = new String[a.length + b.length];
		System.arraycopy(a, 0, r, 0, a.length);
		System.arraycopy(b, 0, r, a.length, b.length);
		return r;
	}
	
	public static int addSkin(String name, byte[] data, boolean slim) {
		int i = -1;
		for(int j = 0, l = skins.size(); j < l; ++j) {
			if(skins.get(j).name.equalsIgnoreCase(name)) {
				i = j;
				break;
			}
		}
		
		if(data.length != SKIN_DATA_SIZE) {
			return -1;
		}
		
		int im = Minecraft.getMinecraft().renderEngine.allocateAndSetupTexture(data, 64, 32);
		if(i == -1) {
			i = skins.size();
			skins.add(new EaglerProfileSkin(name, data, slim, im));
		}else {
			skins.get(i).glTex = im;
			skins.get(i).data = data;
			skins.get(i).slim = slim;
		}
		return i;
		
	}
	
	private static class CachedSkin {
		
		protected final String username;
		protected UserSkin skin;
		protected long age;
		
		protected CachedSkin(String username, UserSkin skin) {
			this.username = username;
			this.skin = skin;
			this.age = System.currentTimeMillis();
		}
		
	}
	
	public static enum EnumSkinType {
		PRESET, CUSTOM_LEGACY
	}
	
	public static interface UserSkin {
		
		abstract EnumSkinType getSkinType();
		abstract int getSkin();
		abstract int getTexture();
		abstract void free();
		
	}

	private static class UserPresetSkin implements UserSkin {
		
		protected final int skinType;
		
		protected UserPresetSkin(int skin) {
			this.skinType = skin;
		}

		@Override
		public EnumSkinType getSkinType() {
			return EnumSkinType.PRESET;
		}

		@Override
		public int getSkin() {
			return skinType;
		}

		@Override
		public int getTexture() {
			return (skinType >= defaultOptionsTextures.length || skinType < 0) ? -1 : defaultOptionsTextures[skinType].getTexturePointer();
		}

		@Override
		public void free() {
		}
		
	}
	
	private static class UserCustomSkin implements UserSkin {

		protected final byte[] data;
		protected int glTexture;

		protected UserCustomSkin(byte[] data) {
			this.data = data;
			this.glTexture = -1; 
		}
		
		@Override
		public EnumSkinType getSkinType() {
			return EnumSkinType.CUSTOM_LEGACY;
		}

		@Override
		public int getSkin() {
			return -1;
		}

		@Override
		public int getTexture() {
			RenderEngine r = Minecraft.getMinecraft().renderEngine;
			if(glTexture == -1) {
				glTexture = r.allocateAndSetupTexture(data, 64, 32);
			}
			return glTexture;
		}

		@Override
		public void free() {
			RenderEngine r = Minecraft.getMinecraft().renderEngine;
			r.deleteTexture(glTexture);
			glTexture = -1;
		}
		
	}
	
	private static class WaitingSkin {
		
		protected final int cookie;
		protected final String username;
		protected final long requestStartTime;
		
		protected WaitingSkin(int cookie, String username) {
			this.cookie = cookie;
			this.username = username;
			this.requestStartTime = System.currentTimeMillis();
		}
		
	}

	private static final Map<Integer,WaitingSkin> multiplayerWaitingSkinCache = new HashMap();
	private static final Map<String,CachedSkin> multiplayerSkinCache = new HashMap();
	private static final long maxSkinAge = 1000l * 60l * 5l;
	
	private static final UserSkin defaultSkin = new UserPresetSkin(0);
	
	private static int skinRequestId = 0;
	
	public static int beginSkinRequest(String un) {
		int ret = skinRequestId++;
		if(skinRequestId >= 65536) {
			skinRequestId = 0;
		}
		multiplayerWaitingSkinCache.put(ret, new WaitingSkin(ret, un));
		return ret;
	}
	
	public static boolean skinRequestPending(String un) {
		return multiplayerWaitingSkinCache.containsKey(un);
	}
	
	public static UserSkin getUserSkin(String un) {
		CachedSkin cs = multiplayerSkinCache.get(un);
		if(cs == null) {
			return null;
		}else {
			cs.age = System.currentTimeMillis();
			return cs.skin;
		}
	}
	
	public static void processSkinResponse(byte[] dat) {
		if(dat.length >= 3) {
			int cookie = (((int)dat[0] & 0xFF) << 8) | ((int)dat[1] & 0xFF);
			WaitingSkin st = multiplayerWaitingSkinCache.remove(cookie);
			if(st != null) {
				int t = (int)dat[2] & 0xFF;
				if(t == 0) {
					if(dat.length == 4) {
						multiplayerSkinCache.put(st.username, new CachedSkin(st.username, new UserPresetSkin((int)dat[3] & 0xFF)));
					}else {
						System.out.println("Recieved a PRESET skin of the wrong size (" + (dat.length - 3) + ") for player " + st + ".");
					}
				}else if(t == 1) {
					if(dat.length == 3 + SKIN_DATA_SIZE) {
						byte[] datt = new byte[SKIN_DATA_SIZE];
						System.arraycopy(dat, 3, datt, 0, SKIN_DATA_SIZE);
						multiplayerSkinCache.put(st.username, new CachedSkin(st.username, new UserCustomSkin(datt)));
					}else {
						System.out.println("Recieved a CUSTOM_LEGACY skin of the wrong size (" + (dat.length - 3) + ") for player " + st + ".");
					}
				}else {
					System.out.println("Unsupported skin type '" + t + "' was recieved from server for player " + st + ".");
				}
			}
		}
	}
	
	public static void freeSkins() {
		long millis = System.currentTimeMillis();
		Iterator<CachedSkin> skns = multiplayerSkinCache.values().iterator();
		while(skns.hasNext()) {
			CachedSkin cs = skns.next();
			if(millis - cs.age > maxSkinAge) {
				cs.skin.free();
				skns.remove();
			}
		}
		Iterator<WaitingSkin> skns2 = multiplayerWaitingSkinCache.values().iterator();
		while(skns2.hasNext()) {
			WaitingSkin cs = skns2.next();
			if(millis - cs.requestStartTime > 10000l) {
				skns2.remove();
			}
		}
	}
	
	public static void freeUserSkin(String un) {
		CachedSkin cs = multiplayerSkinCache.remove(un);
		if(cs != null) {
			cs.skin.free();
		}
	}
	
	public static void freeAllSkins() {
		Iterator<CachedSkin> skns = multiplayerSkinCache.values().iterator();
		while(skns.hasNext()) {
			skns.next().skin.free();
		}
		multiplayerWaitingSkinCache.clear();
		multiplayerSkinCache.clear();
	}
	
	static {
		String[] usernameDefaultWords = ConfigConstants.profanity ? new String[] {
				"Eagler",
				"Eagler",
				"Bitch",
				"Cock",
				"Milf",
				"Milf",
				"Yeer",
				"Groon",
				"Eag",
				"Deevis",
				"Chode",
				"Deev",
				"Deev",
				"Fucker",
				"Fucking",
				"Dumpster",
				"Dumpster",
				"Cum",
				"Chad",
				"Egg",
				"Fudgler",
				"Fudgli",
				"Yee",
				"Yee",
				"Yee",
				"Yeet",
				"Flumpter",
				"Darvy",
				"Darver",
				"Darver",
				"Fuck",
				"Fuck",
				"Frick",
				"Eagler",
				"Vigg",
				"Vigg",
				"Cunt",
				"Darvig"
		} : new String[] {
				"Yeeish",
				"Yeeish",
				"Yee",
				"Yee",
				"Yeer",
				"Yeeler",
				"Eagler",
				"Eagl",
				"Darver",
				"Darvler",
				"Vool",
				"Vigg",
				"Vigg",
				"Deev",
				"Yigg",
				"Yeeg"
		};
		
		rand = new EaglercraftRandom();
		
		do {
			username = usernameDefaultWords[rand.nextInt(usernameDefaultWords.length)] + usernameDefaultWords[rand.nextInt(usernameDefaultWords.length)] + (10 + rand.nextInt(90));
		}while(username.length() > 16);
		
		presetSkinId = rand.nextInt(GuiScreenEditProfile.defaultOptions.length);
		myChannel = username + "_" + (100 + rand.nextInt(900));
		customSkinId = -1;
	}

	public static void loadFromStorage() {
		if(!LocalStorageManager.profileSettingsStorage.hasNoTags()) {
			presetSkinId = LocalStorageManager.profileSettingsStorage.getInteger("ps");
			customSkinId = LocalStorageManager.profileSettingsStorage.getInteger("cs");
			username = LocalStorageManager.profileSettingsStorage.getString("name");
			myChannel = username + "_" + (100 + rand.nextInt(900));
			NBTTagCompound n = LocalStorageManager.profileSettingsStorage.getCompoundTag("skins");
			for(Object s : NBTTagCompound.getTagMap(n).keySet()) {
				String s2 = (String)s;
				addSkin(s2, n.getByteArray(s2), false);
			}
		}
	}

	public static final TextureLocation[] defaultOptionsTextures = new TextureLocation[] {
			new TextureLocation("/skins/01.default_steve.png"),
			new TextureLocation("/skins/02.tennis_steve.png"),
			new TextureLocation("/skins/03.tuxedo_steve.png"),
			new TextureLocation("/skins/04.athlete_steve.png"),
			new TextureLocation("/skins/05.cyclist_steve.png"),
			new TextureLocation("/skins/06.boxer_steve.png"),
			new TextureLocation("/skins/07.prisoner_steve.png"),
			new TextureLocation("/skins/08.scottish_steve.png"),
			new TextureLocation("/skins/09.dev_steve.png"),
			new TextureLocation("/skins/10.herobrine.png"),
			new TextureLocation("/skins/11.slime.png"),
			new TextureLocation("/skins/12.trump.png"),
			new TextureLocation("/skins/13.notch.png"),
			new TextureLocation("/skins/14.creeper.png"),
			new TextureLocation("/skins/15.zombie.png"),
			new TextureLocation("/skins/16.pig.png"),
			new TextureLocation("/skins/17.squid.png"),
			new TextureLocation("/skins/18.mooshroom.png")
	};
	
}
