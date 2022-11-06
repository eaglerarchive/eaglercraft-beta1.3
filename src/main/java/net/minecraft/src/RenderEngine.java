package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglerImage;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.beta.SpriteSheetTexture;

public class RenderEngine {

	public RenderEngine(TexturePackList texturepacklist, GameSettings gamesettings) {
		textureMap = new HashMap();
		textureNameToImageMap = new HashMap();
		singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
		imageDataB1 = GLAllocation.createDirectByteBuffer(0x100000);
		imageDataB2 = GLAllocation.createDirectByteBuffer(0x100000);
		//imageDataInt = GLAllocation.createDirectIntBuffer(0x40000);
		textureList = new ArrayList();
		textureSpriteList = new ArrayList();
		clampTexture = false;
		blurTexture = false;
		field_6527_k = texturepacklist;
		options = gamesettings;
	}

	public int getTexture(String s) {
		TexturePackBase texturepackbase = field_6527_k.selectedTexturePack;
		Integer integer = (Integer) textureMap.get(s);
		if (integer != null) {
			return integer.intValue();
		}
		try {
			singleIntBuffer.clear();
			GLAllocation.generateTextureNames(singleIntBuffer);
			int i = singleIntBuffer.get(0);
			//if (s.startsWith("##")) {
			//	setupTexture(unwrapImageByColumns(readTextureImage(texturepackbase.func_6481_a(s.substring(2)))), i);
			//} else 
			if (s.startsWith("%clamp%")) {
				clampTexture = true;
				setupTexture(readTextureImage(texturepackbase.func_6481_a(s.substring(7))), i);
				clampTexture = false;
			} else if (s.startsWith("%blur%")) {
				blurTexture = true;
				setupTexture(readTextureImage(texturepackbase.func_6481_a(s.substring(6))), i);
				blurTexture = false;
			} else {
				if(s.equals("/terrain.png")) {
					useMipmaps = true;
				}
				setupTexture(readTextureImage(texturepackbase.func_6481_a(s)), i);
				useMipmaps = false;
			}
			textureMap.put(s, Integer.valueOf(i));
			return i;
		} catch (IOException ioexception) {
			throw new RuntimeException("!!");
		}
	}
	
	public int allocateAndSetupTexture(EaglerImage bufferedimage) {
		singleIntBuffer.clear();
		GLAllocation.generateTextureNames(singleIntBuffer);
		int i = singleIntBuffer.get(0);
		setupTexture(bufferedimage, i);
		textureNameToImageMap.put(Integer.valueOf(i), bufferedimage);
		return i;
	}
	
	public int allocateAndSetupTexture(byte[] data, int w, int h) {
		int i = EaglerAdapter.glGenTextures();
		bindTexture(i);
		EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10241 /* GL_TEXTURE_MIN_FILTER */, 9729 /* GL_LINEAR */);
		EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10240 /* GL_TEXTURE_MAG_FILTER */, 9728 /* GL_NEAREST */);
		EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10242 /* GL_TEXTURE_WRAP_S */, 10497 /* GL_REPEAT */);
		EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10243 /* GL_TEXTURE_WRAP_T */, 10497 /* GL_REPEAT */);
		imageDataB1.clear();
		imageDataB1.put(data);
		imageDataB1.position(0).limit(data.length);
		EaglerAdapter.glTexImage2D(3553 /* GL_TEXTURE_2D */, 0, 6408 /* GL_RGBA */, w, h, 0, 6408 /* GL_RGBA */,
						5121 /* GL_UNSIGNED_BYTE */, imageDataB1);
		return i;
	}

	public void setupTexture(EaglerImage bufferedimage, int i) {
		bindTexture(i);
		if (useMipmaps) {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10241 /* GL_TEXTURE_MIN_FILTER */, EaglerAdapter.GL_NEAREST_MIPMAP_LINEAR);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10240 /* GL_TEXTURE_MAG_FILTER */, EaglerAdapter.GL_NEAREST /* GL_LINEAR */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, EaglerAdapter.GL_TEXTURE_MAX_LEVEL, 4);
		} else {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10241 /* GL_TEXTURE_MIN_FILTER */, 9728 /* GL_NEAREST */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10240 /* GL_TEXTURE_MAG_FILTER */, 9728 /* GL_NEAREST */);
		}
		if (blurTexture) {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10241 /* GL_TEXTURE_MIN_FILTER */, 9729 /* GL_LINEAR */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10240 /* GL_TEXTURE_MAG_FILTER */, 9729 /* GL_LINEAR */);
		}
		if (clampTexture) {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10242 /* GL_TEXTURE_WRAP_S */, 10496 /* GL_CLAMP */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10243 /* GL_TEXTURE_WRAP_T */, 10496 /* GL_CLAMP */);
		} else {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10242 /* GL_TEXTURE_WRAP_S */, 10497 /* GL_REPEAT */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10243 /* GL_TEXTURE_WRAP_T */, 10497 /* GL_REPEAT */);
		}
		int j = bufferedimage.w;
		int k = bufferedimage.h;
		int ai[] = bufferedimage.data;
		byte abyte0[] = new byte[j * k * 4];
		for (int l = 0; l < ai.length; l++) {
			int j1 = ai[l] >> 24 & 0xff;
			int l1 = ai[l] >> 16 & 0xff;
			int j2 = ai[l] >> 8 & 0xff;
			int l2 = ai[l] >> 0 & 0xff;
			if (options != null && options.anaglyph) {
				int j3 = (l1 * 30 + j2 * 59 + l2 * 11) / 100;
				int l3 = (l1 * 30 + j2 * 70) / 100;
				int j4 = (l1 * 30 + l2 * 70) / 100;
				l1 = j3;
				j2 = l3;
				l2 = j4;
			}
			abyte0[l * 4 + 0] = (byte) l1;
			abyte0[l * 4 + 1] = (byte) j2;
			abyte0[l * 4 + 2] = (byte) l2;
			abyte0[l * 4 + 3] = (byte) j1;
		}
		imageDataB1.clear();
		imageDataB1.put(abyte0);
		imageDataB1.position(0).limit(abyte0.length);
		EaglerAdapter.glTexImage2D(3553 /* GL_TEXTURE_2D */, 0, 6408 /* GL_RGBA */, j, k, 0, 6408 /* GL_RGBA */,
				5121 /* GL_UNSIGNED_BYTE */, imageDataB1);
		if (useMipmaps) {
			for (int i1 = 1; i1 <= 4; i1++) {
				int k1 = j >> i1 - 1;
				int i2 = j >> i1;
				int k2 = k >> i1;
				imageDataB2.clear();
				for (int i3 = 0; i3 < i2; i3++) {
					for (int k3 = 0; k3 < k2; k3++) {
						int i4 = imageDataB1.getInt((i3 * 2 + 0 + (k3 * 2 + 0) * k1) * 4);
						int k4 = imageDataB1.getInt((i3 * 2 + 1 + (k3 * 2 + 0) * k1) * 4);
						int l4 = imageDataB1.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * k1) * 4);
						int i5 = imageDataB1.getInt((i3 * 2 + 0 + (k3 * 2 + 1) * k1) * 4);
						int j5 = averageColor(averageColor(i4, k4), averageColor(l4, i5));
						imageDataB2.putInt((i3 + k3 * i2) * 4, j5);
					}

				}
				
				EaglerAdapter.glTexImage2D(3553 /* GL_TEXTURE_2D */, i1, 6408 /* GL_RGBA */, i2, k2, 0, 6408 /* GL_RGBA */,
						5121 /* GL_UNSIGNED_BYTE */, imageDataB2);
				ByteBuffer tmp = imageDataB1;
				imageDataB1 = imageDataB2;
				imageDataB2 = tmp;
			}

		}
	}

	public void deleteTexture(int i) {
		EaglerAdapter.glDeleteTextures(i);
	}

	public int getTextureForDownloadableImage(String s, String s1) {
		return getTexture("/mob/char.png");
	}
	
	public void registerTextureFX(TextureFX texturefx) {
		textureList.add(texturefx);
		texturefx.onTick();
	}

//	public void updateTerrainTextures() {
//		for (int i = 0; i < textureList.size(); i++) {
//			TextureFX texturefx = (TextureFX) textureList.get(i);
//			texturefx.anaglyphEnabled = options.anaglyph;
//			texturefx.onTick();
//			int tileSize = 16 * 16 * 4;
//			imageDataB1.clear();
//			imageDataB1.put(texturefx.imageData);
//			imageDataB1.position(0).limit(tileSize);
//			texturefx.bindImage(this);
//			
//			imageDataB1.position(0).limit(tileSize);
//
//			for (int k = 0; k < texturefx.tileSize; k++) {
//				for (int i1 = 0; i1 < texturefx.tileSize; i1++) {
//					int idx = texturefx.iconIndex + k + i1 * 16;
//					imageDataB1.mark();
//					EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 0, (idx % 16) * 16, (idx / 16) * 16, 16, 16, 6408 /* GL_RGBA */,
//							5121 /* GL_UNSIGNED_BYTE */, imageDataB1);
//					imageDataB1.rewind();
//				}
//			}
//		}
//		TextureFX.terrainTexture.bindTexture();
//		IntBuffer buf = imageDataInt;
//		for (int i = 0; i < textureSpriteList.size(); i++) {
//			Object obj = textureSpriteList.get(i);
//			PXSpriteSheet s;
//			int ticksCount = -1;
//			if(obj instanceof PXSpriteSheet) {
//				s = (PXSpriteSheet)obj;
//				s.onUpdate();
//				ticksCount = s.ticks;
//			}else if(obj instanceof PXSpriteSheet.PXSpriteSheetOffset) {
//				PXSpriteSheet.PXSpriteSheetOffset ss = (PXSpriteSheet.PXSpriteSheetOffset) obj;
//				s = ss.src;
//				ticksCount = (s.ticks + ss.offset) % s.sheetLength;
//			}else {
//				continue;
//			}
//			
//			int idx = s.tileIndex;
//			
//			int tileSize = 16 * s.tileSize * 16 * s.tileSize;
//			int offsetIndex = ticksCount * tileSize;
//			buf.clear();
//			buf.put(s.pixels0, offsetIndex, tileSize);
//			buf.flip();
//			EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 0, (idx % 16) * 16, (idx / 16) * 16, 16 * s.tileSize, 16 * s.tileSize, 6408 /* GL_RGBA */,
//					5121 /* GL_UNSIGNED_BYTE */, buf);
//			
//			tileSize = 8 * s.tileSize * 8 * s.tileSize;
//			offsetIndex = ticksCount * tileSize;
//			buf.clear();
//			buf.put(s.pixels1, offsetIndex, tileSize);
//			buf.flip();
//			EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 1, (idx % 16) * 8, (idx / 16) * 8, 8 * s.tileSize, 8 * s.tileSize, 6408 /* GL_RGBA */,
//					5121 /* GL_UNSIGNED_BYTE */, buf);
//			
//			tileSize = 4 * s.tileSize * 4 * s.tileSize;
//			offsetIndex = ticksCount * tileSize;
//			buf.clear();
//			buf.put(s.pixels2, offsetIndex, tileSize);
//			buf.flip();
//			EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 2, (idx % 16) * 4, (idx / 16) * 4, 4 * s.tileSize, 4 * s.tileSize, 6408 /* GL_RGBA */,
//					5121 /* GL_UNSIGNED_BYTE */, buf);
//			
//			tileSize = 2 * s.tileSize * 2 * s.tileSize;
//			offsetIndex = ticksCount * tileSize;
//			buf.clear();
//			buf.put(s.pixels3, offsetIndex, tileSize);
//			buf.flip();
//			EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 3, (idx % 16) * 2, (idx / 16) * 2, 2 * s.tileSize, 2 * s.tileSize, 6408 /* GL_RGBA */,
//					5121 /* GL_UNSIGNED_BYTE */, buf);
//			
//			tileSize = s.tileSize * s.tileSize;
//			offsetIndex = ticksCount * tileSize;
//			buf.clear();
//			buf.put(s.pixels4, offsetIndex, tileSize);
//			buf.flip();
//			EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 4, (idx % 16), (idx / 16), 1 * s.tileSize, 1 * s.tileSize, 6408 /* GL_RGBA */,
//					5121 /* GL_UNSIGNED_BYTE */, buf);
//		}
//		
//	}

	private int averageColor(int i, int j) {
		int k = (i & 0xff000000) >> 24 & 0xff;
		int l = (j & 0xff000000) >> 24 & 0xff;
		return ((k + l >> 1) << 24) + ((i & 0xfefefe) + (j & 0xfefefe) >> 1);
		
	}
	
	public void refreshTextures() {
		TextureLocation.freeTextures();
		TexturePackBase texturepackbase = field_6527_k.selectedTexturePack;
		int i;
		EaglerImage bufferedimage;
		for (Iterator iterator = textureNameToImageMap.keySet().iterator(); iterator
				.hasNext(); setupTexture(bufferedimage, i)) {
			i = ((Integer) iterator.next()).intValue();
			bufferedimage = (EaglerImage) textureNameToImageMap.get(Integer.valueOf(i));
		}

		//for (Iterator iterator1 = urlToImageDataMap.values().iterator(); iterator1.hasNext();) {
		//	ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData) iterator1.next();
		//	threaddownloadimagedata.textureSetupComplete = false;
		//}

		for (Iterator iterator2 = textureMap.keySet().iterator(); iterator2.hasNext();) {
			String s = (String) iterator2.next();
			try {
				EaglerImage bufferedimage1;
				/*
				if (s.startsWith("##")) {
					bufferedimage1 = unwrapImageByColumns(
							readTextureImage(texturepackbase.func_6481_a(s.substring(2))));
				} else
				*/
				if (s.startsWith("%clamp%")) {
					clampTexture = true;
					bufferedimage1 = readTextureImage(texturepackbase.func_6481_a(s.substring(7)));
				} else if (s.startsWith("%blur%")) {
					blurTexture = true;
					bufferedimage1 = readTextureImage(texturepackbase.func_6481_a(s.substring(6)));
				} else {
					bufferedimage1 = readTextureImage(texturepackbase.func_6481_a(s));
				}
				int j = ((Integer) textureMap.get(s)).intValue();
				setupTexture(bufferedimage1, j);
				blurTexture = false;
				clampTexture = false;
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
			}
		}
		
		for(int j = 0, l = textureSpriteList.size(); j < l; ++j) {
			textureSpriteList.get(j).reloadData();
		}

	}

	private EaglerImage readTextureImage(byte[] inputstream) throws IOException {
		return EaglerAdapter.loadPNG(inputstream);
	}

	public void bindTexture(int i) {
		if (i < 0) {
			return;
		} else {
			EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, i);
			return;
		}
	}

	public static boolean useMipmaps = false;
	private HashMap textureMap;
	private HashMap textureNameToImageMap;
	private IntBuffer singleIntBuffer;
	private ByteBuffer imageDataB1;
	private ByteBuffer imageDataB2;
	private java.util.List<TextureFX> textureList;
	private java.util.List<SpriteSheetTexture> textureSpriteList;
	private GameSettings options;
	private boolean clampTexture;
	private boolean blurTexture;
	private TexturePackList field_6527_k;
	
	public void updateTerrainTextures() {
		
		for (int i = 0; i < textureList.size(); i++) {
			TextureFX texturefx = (TextureFX) textureList.get(i);
			texturefx.anaglyphEnabled = options.anaglyph;
			texturefx.onTick();
			texturefx.bindImage(this);
			int tileSize = 16 * 16 * 4;
			imageDataB1.clear();
			imageDataB1.put(texturefx.imageData);
			imageDataB1.position(0).limit(tileSize);
			EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 0, (texturefx.iconIndex % 16) * 16, (texturefx.iconIndex / 16) * 16, 16, 16,
					6408 /* GL_RGBA */, 5121 /* GL_UNSIGNED_BYTE */, imageDataB1);
		}
		
		TextureFX.terrainTexture.bindTexture();
		for(int i = 0, l = textureSpriteList.size(); i < l; ++i) {
			SpriteSheetTexture sp = textureSpriteList.get(i);
			sp.update();
			int w = 16;
			for(int j = 0; j < 5; ++j) {
				EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, j, (sp.iconIndex % 16) * w, (sp.iconIndex / 16) * w, w * sp.iconTileSize, w * sp.iconTileSize,
						6408 /* GL_RGBA */, 5121 /* GL_UNSIGNED_BYTE */, sp.grabFrame(j));
				w /= 2;
			}
		}
		
	}
	
	public void registerSpriteSheet(String name, int iconIndex, int iconTileSize) {
		textureSpriteList.add(new SpriteSheetTexture(name, iconIndex, iconTileSize));
	}

}
