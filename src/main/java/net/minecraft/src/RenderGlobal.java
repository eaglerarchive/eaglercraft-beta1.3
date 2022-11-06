package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.nio.IntBuffer;
import java.util.*;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.EaglercraftRandom;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;
import net.minecraft.client.Minecraft;

public class RenderGlobal implements IWorldAccess {

	public RenderGlobal(Minecraft minecraft, RenderEngine renderengine) {
		tileEntities = new ArrayList();
		worldRenderersToUpdate = new ArrayList();
		field_1436_w = true;
		field_1435_x = 0;
		renderDistance = -1;
		field_1424_I = 2;
		field_1457_b = new int[50000];
		field_1456_c = GLAllocation.createDirectIntBuffer(64);
		field_1415_R = new ArrayList();
		field_1455_d = 0;
		field_1454_e = GLAllocation.generateDisplayLists(1);
		field_1453_f = -9999D;
		field_1452_g = -9999D;
		field_1451_h = -9999D;
		field_1449_j = 0;
		mc = minecraft;
		renderEngine = renderengine;
		byte byte0 = 64;
		renderListBase = GLAllocation.generateDisplayLists(byte0 * byte0 * byte0 * 2);
		this.glOcclusionQuery = new int[byte0 * byte0 * byte0]; 
		for(int i = 0; i < glOcclusionQuery.length; ++i) {
			this.glOcclusionQuery[i] = -1;
		}
		this.occlusionQueryAvailable = new boolean[glOcclusionQuery.length];
		this.occlusionQueryStalled = new long[occlusionQueryAvailable.length];
		field_1434_y = GLAllocation.generateDisplayLists(3);
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glNewList(field_1434_y, 4864 /* GL_COMPILE */);
		func_950_f();
		EaglerAdapter.glEndList();
		EaglerAdapter.glPopMatrix();
		Tessellator tessellator = Tessellator.instance;
		field_1433_z = field_1434_y + 1;
		EaglerAdapter.glNewList(field_1433_z, 4864 /* GL_COMPILE */);
		byte byte1 = 64;
		int i = 256 / byte1 + 2;
		float f = 16F;
		for (int j = -byte1 * i; j <= byte1 * i; j += byte1) {
			for (int l = -byte1 * i; l <= byte1 * i; l += byte1) {
				tessellator.startDrawingQuads();
				tessellator.addVertex(j + 0, f, l + 0);
				tessellator.addVertex(j + byte1, f, l + 0);
				tessellator.addVertex(j + byte1, f, l + byte1);
				tessellator.addVertex(j + 0, f, l + byte1);
				tessellator.draw();
			}

		}

		EaglerAdapter.glEndList();
		field_1432_A = field_1434_y + 2;
		EaglerAdapter.glNewList(field_1432_A, 4864 /* GL_COMPILE */);
		f = -16F;
		tessellator.startDrawingQuads();
		for (int k = -byte1 * i; k <= byte1 * i; k += byte1) {
			for (int i1 = -byte1 * i; i1 <= byte1 * i; i1 += byte1) {
				tessellator.addVertex(k + byte1, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + byte1);
				tessellator.addVertex(k + byte1, f, i1 + byte1);
			}

		}

		tessellator.draw();
		EaglerAdapter.glEndList();
	}

	private void func_950_f() {
		EaglercraftRandom random = new EaglercraftRandom(10842L);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		for (int i = 0; i < 1500; i++) {
			double d = random.nextFloat() * 2.0F - 1.0F;
			double d1 = random.nextFloat() * 2.0F - 1.0F;
			double d2 = random.nextFloat() * 2.0F - 1.0F;
			double d3 = 0.25F + random.nextFloat() * 0.25F;
			double d4 = d * d + d1 * d1 + d2 * d2;
			if (d4 >= 1.0D || d4 <= 0.01D) {
				continue;
			}
			d4 = 1.0D / Math.sqrt(d4);
			d *= d4;
			d1 *= d4;
			d2 *= d4;
			double d5 = d * 100D;
			double d6 = d1 * 100D;
			double d7 = d2 * 100D;
			double d8 = Math.atan2(d, d2);
			double d9 = Math.sin(d8);
			double d10 = Math.cos(d8);
			double d11 = Math.atan2(Math.sqrt(d * d + d2 * d2), d1);
			double d12 = Math.sin(d11);
			double d13 = Math.cos(d11);
			double d14 = random.nextDouble() * 3.1415926535897931D * 2D;
			double d15 = Math.sin(d14);
			double d16 = Math.cos(d14);
			for (int j = 0; j < 4; j++) {
				double d17 = 0.0D;
				double d18 = (double) ((j & 2) - 1) * d3;
				double d19 = (double) ((j + 1 & 2) - 1) * d3;
				double d20 = d17;
				double d21 = d18 * d16 - d19 * d15;
				double d22 = d19 * d16 + d18 * d15;
				double d23 = d22;
				double d24 = d21 * d12 + d20 * d13;
				double d25 = d20 * d12 - d21 * d13;
				double d26 = d25 * d9 - d23 * d10;
				double d27 = d24;
				double d28 = d23 * d9 + d25 * d10;
				tessellator.addVertex(d5 + d26, d6 + d27, d7 + d28);
			}

		}

		tessellator.draw();
	}

	public void func_946_a(World world) {
		if (worldObj != null) {
			worldObj.removeWorldAccess(this);
		}
		field_1453_f = -9999D;
		field_1452_g = -9999D;
		field_1451_h = -9999D;
		RenderManager.instance.func_852_a(world);
		worldObj = world;
		field_1438_u = new RenderBlocks(world);
		if (world != null) {
			world.addWorldAccess(this);
			loadRenderers();
		}
	}

	public void loadRenderers() {
		Block.leaves.setGraphicsLevel(mc.gameSettings.fancyGraphics);
		renderDistance = mc.gameSettings.renderDistance;
		if (worldRenderers != null) {
			for (int i = 0; i < worldRenderers.length; i++) {
				worldRenderers[i].func_1204_c();
			}

		}
		int j = 64 << 3 - renderDistance;
		if (j > 400) {
			j = 400;
		}
		renderChunksWide = j / 16 + 1;
		renderChunksTall = 8;
		renderChunksDeep = j / 16 + 1;
		worldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
		sortedWorldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
		int k = 0;
		int l = 0;
		field_1431_B = 0;
		field_1430_C = 0;
		field_1429_D = 0;
		field_1428_E = renderChunksWide;
		field_1427_F = renderChunksTall;
		field_1426_G = renderChunksDeep;
		for (int i1 = 0; i1 < worldRenderersToUpdate.size(); i1++) {
			((WorldRenderer) worldRenderersToUpdate.get(i1)).needsUpdate = false;
		}

		worldRenderersToUpdate.clear();
		tileEntities.clear();
		for (int j1 = 0; j1 < renderChunksWide; j1++) {
			for (int k1 = 0; k1 < renderChunksTall; k1++) {
				for (int l1 = 0; l1 < renderChunksDeep; l1++) {
					int idx = (l1 * renderChunksTall + k1) * renderChunksWide + j1;
					worldRenderers[idx] = new WorldRenderer(worldObj,
							tileEntities, j1 * 16, k1 * 16, l1 * 16, 16, renderListBase + k);
					worldRenderers[idx].isWaitingOnOcclusionQuery = false;
					worldRenderers[idx].isVisible = 100;
					worldRenderers[idx].isNowVisible = true;
					worldRenderers[idx].isInFrustum = true;
					worldRenderers[idx].chunkIndex = l++;
					worldRenderers[idx].markDirty();
					sortedWorldRenderers[idx] = worldRenderers[idx];
					worldRenderersToUpdate.add(worldRenderers[idx]);
					k += 2;
				}

			}

		}

		forceMarkForNewPosition();
		field_1424_I = 2;
	}
	
	public void forceMarkForNewPosition() {
		if (worldObj != null) {
			EntityLiving entityliving = mc.field_22009_h;
			if (entityliving != null) {
				markRenderersForNewPosition(MathHelper.floor_double(((Entity) (entityliving)).posX),
						MathHelper.floor_double(((Entity) (entityliving)).posY),
						MathHelper.floor_double(((Entity) (entityliving)).posZ));
				Arrays.sort(sortedWorldRenderers, new EntitySorter(entityliving));
			}
		}
	}

	public void func_951_a(Vec3D vec3d, ICamera icamera, float f) {
		if (field_1424_I > 0) {
			field_1424_I--;
			return;
		}
		TileEntityRenderer.instance.func_22267_a(worldObj, renderEngine, mc.fontRenderer, mc.field_22009_h, f);
		RenderManager.instance.func_22187_a(worldObj, renderEngine, mc.fontRenderer, mc.field_22009_h, mc.gameSettings,
				f);
		field_1423_J = 0;
		field_1422_K = 0;
		field_1421_L = 0;
		EntityLiving entityliving = mc.field_22009_h;
		RenderManager.renderPosX = ((Entity) (entityliving)).lastTickPosX
				+ (((Entity) (entityliving)).posX - ((Entity) (entityliving)).lastTickPosX) * (double) f;
		RenderManager.renderPosY = ((Entity) (entityliving)).lastTickPosY
				+ (((Entity) (entityliving)).posY - ((Entity) (entityliving)).lastTickPosY) * (double) f;
		RenderManager.renderPosZ = ((Entity) (entityliving)).lastTickPosZ
				+ (((Entity) (entityliving)).posZ - ((Entity) (entityliving)).lastTickPosZ) * (double) f;
		TileEntityRenderer.staticPlayerX = ((Entity) (entityliving)).lastTickPosX
				+ (((Entity) (entityliving)).posX - ((Entity) (entityliving)).lastTickPosX) * (double) f;
		TileEntityRenderer.staticPlayerY = ((Entity) (entityliving)).lastTickPosY
				+ (((Entity) (entityliving)).posY - ((Entity) (entityliving)).lastTickPosY) * (double) f;
		TileEntityRenderer.staticPlayerZ = ((Entity) (entityliving)).lastTickPosZ
				+ (((Entity) (entityliving)).posZ - ((Entity) (entityliving)).lastTickPosZ) * (double) f;
		List list = worldObj.getLoadedEntityList();
		field_1423_J = list.size();
		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity) list.get(i);
			if (entity.isInRangeToRenderVec3D(vec3d) && icamera.isBoundingBoxInFrustum(entity.boundingBox)
					&& (entity != mc.field_22009_h || mc.gameSettings.thirdPersonView
							|| mc.field_22009_h.isPlayerSleeping())
					&& worldObj.blockExists(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY),
							MathHelper.floor_double(entity.posZ))) {
				field_1422_K++;
				RenderManager.instance.renderEntity(entity, f);
			}
		}

		for (int j = 0; j < tileEntities.size(); j++) {
			TileEntityRenderer.instance.renderTileEntity((TileEntity) tileEntities.get(j), f);
		}

	}

	public String func_953_b() {
		return (new StringBuilder()).append("C: ").append(field_1417_P).append("/").append(field_1420_M).append(". F: ")
				.append(field_1419_N).append(", O: ").append(field_1418_O).append(", E: ").append(field_1416_Q)
				.toString();
	}

	public String func_957_c() {
		return (new StringBuilder()).append("E: ").append(field_1422_K).append("/").append(field_1423_J).append(". B: ")
				.append(field_1421_L).append(", I: ").append(field_1423_J - field_1421_L - field_1422_K).toString();
	}
	
	/**
	 * Goes through all the renderers setting new positions on them and those that
	 * have their position changed are adding to be updated
	 */
	private void markRenderersForNewPosition(int i, int j, int k) {
		i -= 8;
		j -= 8;
		k -= 8;
		field_1431_B = 0x7fffffff;
		field_1430_C = 0x7fffffff;
		field_1429_D = 0x7fffffff;
		field_1428_E = 0x80000000;
		field_1427_F = 0x80000000;
		field_1426_G = 0x80000000;
		int l = renderChunksWide * 16;
		int i1 = l / 2;
		for (int j1 = 0; j1 < renderChunksWide; j1++) {
			int k1 = j1 * 16;
			int l1 = (k1 + i1) - i;
			if (l1 < 0) {
				l1 -= l - 1;
			}
			l1 /= l;
			k1 -= l1 * l;
			if (k1 < field_1431_B) {
				field_1431_B = k1;
			}
			if (k1 > field_1428_E) {
				field_1428_E = k1;
			}
			for (int i2 = 0; i2 < renderChunksDeep; i2++) {
				int j2 = i2 * 16;
				int k2 = (j2 + i1) - k;
				if (k2 < 0) {
					k2 -= l - 1;
				}
				k2 /= l;
				j2 -= k2 * l;
				if (j2 < field_1429_D) {
					field_1429_D = j2;
				}
				if (j2 > field_1426_G) {
					field_1426_G = j2;
				}
				for (int l2 = 0; l2 < renderChunksTall; l2++) {
					int i3 = l2 * 16;
					if (i3 < field_1430_C) {
						field_1430_C = i3;
					}
					if (i3 > field_1427_F) {
						field_1427_F = i3;
					}
					WorldRenderer worldrenderer = worldRenderers[(i2 * renderChunksTall + l2) * renderChunksWide + j1];
					boolean flag = worldrenderer.needsUpdate;
					worldrenderer.setPositionAndBoundingBox(k1, i3, j2);
					if (!flag && worldrenderer.needsUpdate) {
						worldRenderersToUpdate.add(worldrenderer);
					}
				}

			}

		}

	}
	
	private long lastOcclusionQuery = 0l;
	private boolean[] occlusionQueryAvailable;
	private long[] occlusionQueryStalled;

	/**
	 * Sorts all renderers based on the passed in entity. Args: entityLiving,
	 * renderPass, partialTickTime
	 */
	public int sortAndRender(EntityLiving entityliving, int i, double d) {
		for (int j = 0; j < 10; j++) {
			field_21156_R = (field_21156_R + 1) % worldRenderers.length;
			WorldRenderer worldrenderer = worldRenderers[field_21156_R];
			if (worldrenderer.needsUpdate && !worldRenderersToUpdate.contains(worldrenderer)) {
				worldRenderersToUpdate.add(worldrenderer);
			}
		}

		if (mc.gameSettings.renderDistance != renderDistance) {
			loadRenderers();
		}
		if (i == 0) {
			field_1420_M = 0;
			field_1419_N = 0;
			field_1418_O = 0;
			field_1417_P = 0;
			field_1416_Q = 0;
		}
		double d1 = entityliving.lastTickPosX + (entityliving.posX - entityliving.lastTickPosX) * d;
		double d2 = entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * d;
		double d3 = entityliving.lastTickPosZ + (entityliving.posZ - entityliving.lastTickPosZ) * d;
		double d4 = entityliving.posX - field_1453_f;
		double d5 = entityliving.posY - field_1452_g;
		double d6 = entityliving.posZ - field_1451_h;

		int fx = MathHelper.floor_double(d1);
		int fy = MathHelper.floor_double(d2);
		int fz = MathHelper.floor_double(d3);
		
		if (d4 * d4 + d5 * d5 + d6 * d6 > 16D) {
			field_1453_f = entityliving.posX;
			field_1452_g = entityliving.posY;
			field_1451_h = entityliving.posZ;
			markRenderersForNewPosition(fx, fy, fz);
			Arrays.sort(sortedWorldRenderers, new EntitySorter(entityliving));
		}
		
		fx = fx >> 4;
		fy = MathHelper.floor_double(d2 + entityliving.getEyeHeight()) >> 4;
		fz = fz >> 4;

		long queryRate = 50l;
		long stallRateVisible = 50l;
		long stallRate = 500l;
		int cooldownRate = 10;
		
		long ct = System.currentTimeMillis();
		if(i == 0) {
			for (int j = 0; j < this.sortedWorldRenderers.length; ++j) {
				WorldRenderer c = this.sortedWorldRenderers[j];
				int ccx = c.chunkX - fx;
				int ccy = c.chunkY - fy;
				int ccz = c.chunkZ - fz;
				if((ccx < 2 && ccx > -2 && ccy < 2 && ccy > -2 && ccz < 2 && ccz > -2) || glOcclusionQuery[c.chunkIndex] == -1) {
					c.isNowVisible = true;
					c.isVisible = cooldownRate;
				}else if(!c.canRender() && c.isInFrustum) {
					if(occlusionQueryAvailable[c.chunkIndex]) {
						if(EaglerAdapter.glGetQueryResultAvailable(glOcclusionQuery[c.chunkIndex])) {
							if(EaglerAdapter.glGetQueryResult(glOcclusionQuery[c.chunkIndex])) {
								c.isNowVisible = true;
								c.isVisible = cooldownRate;
							}else {
								if(c.isVisible <= 0) {
									c.isNowVisible = false;
								}
							}
							occlusionQueryAvailable[c.chunkIndex] = false;
							occlusionQueryStalled[c.chunkIndex] = 0l;
						}else if(occlusionQueryStalled[c.chunkIndex] != 0l && ct - occlusionQueryStalled[c.chunkIndex] > stallRateVisible) {
							c.isNowVisible = true;
							c.isVisible = cooldownRate;
						}
					}
				}
			}
		}
		
		int k = func_952_a(0, this.sortedWorldRenderers.length, i, d);
		
		d2 -= entityliving.getEyeHeight();
		
		if(i == 0 && ct - lastOcclusionQuery > queryRate) {
			lastOcclusionQuery = ct;
			EaglerAdapter.glEnable(EaglerAdapter.GL_CULL_FACE);
			EaglerAdapter.glDisable(EaglerAdapter.GL_BLEND);
			EaglerAdapter.glColorMask(false, false, false, false);
			EaglerAdapter.glDepthMask(false);
			EaglerAdapter.glBindOcclusionBB();
			for (int j = 0; j < this.sortedWorldRenderers.length; ++j) {
				WorldRenderer c = this.sortedWorldRenderers[j];
				int ccx = c.chunkX - fx;
				int ccy = c.chunkY - fy;
				int ccz = c.chunkZ - fz;
				if(!c.canRender() && c.isInFrustum && !(ccx < 2 && ccx > -2 && ccy < 2 && ccy > -2 && ccz < 2 && ccz > -2)) {
					boolean stalled = false;
					if(occlusionQueryAvailable[c.chunkIndex]) {
						if(occlusionQueryStalled[c.chunkIndex] == 0l) {
							occlusionQueryStalled[c.chunkIndex] = ct;
							stalled = true;
						}else if(ct - occlusionQueryStalled[c.chunkIndex] < stallRate) {
							stalled = true;
						}
					}
					if(!stalled) {
						occlusionQueryAvailable[c.chunkIndex] = true;
						int q = glOcclusionQuery[c.chunkIndex];
						if(q == -1) {
							q = glOcclusionQuery[c.chunkIndex] = EaglerAdapter.glCreateQuery();
						}
						EaglerAdapter.glBeginQuery(q);
						EaglerAdapter.glDrawOcclusionBB((float)(c.posX - d1), (float)(c.posY - d2), (float)(c.posZ - d3), 16, 16, 16);
						EaglerAdapter.glEndQuery();
					}
				}
				if(c.isVisible > 0) {
					--c.isVisible;
				}
			}
			EaglerAdapter.glEndOcclusionBB();
			EaglerAdapter.glColorMask(true, true, true, true);
			EaglerAdapter.glDepthMask(true);
			EaglerAdapter.glEnable(EaglerAdapter.GL_CULL_FACE);
		}
		
		return k;
	}

	private int func_952_a(int i, int j, int k, double d) {
		field_1415_R.clear();
		int l = 0;
		for (int i1 = i; i1 < j; i1++) {
			if (k == 0) {
				field_1420_M++;
				if (sortedWorldRenderers[i1].skipRenderPass[k]) {
					field_1416_Q++;
				} else if (!sortedWorldRenderers[i1].isInFrustum) {
					field_1419_N++;
				} else if (field_1436_w && !sortedWorldRenderers[i1].isNowVisible) {
					field_1418_O++;
				} else {
					field_1417_P++;
				}
			}
			if (sortedWorldRenderers[i1].skipRenderPass[k] || !sortedWorldRenderers[i1].isInFrustum
					|| !sortedWorldRenderers[i1].isNowVisible) {
				continue;
			}
			int j1 = sortedWorldRenderers[i1].getGLCallListForPass(k);
			if (j1 >= 0) {
				field_1415_R.add(sortedWorldRenderers[i1]);
				l++;
			}
		}

		EntityLiving entityliving = mc.field_22009_h;
		double d1 = entityliving.lastTickPosX + (entityliving.posX - entityliving.lastTickPosX) * d;
		double d2 = entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * d;
		double d3 = entityliving.lastTickPosZ + (entityliving.posZ - entityliving.lastTickPosZ) * d;
		
		for (int i2 = 0; i2 < field_1415_R.size(); i2++) {
			WorldRenderer worldrenderer = (WorldRenderer) field_1415_R.get(i2);
			EaglerAdapter.glPushMatrix();
			EaglerAdapter.glTranslatef((float)(worldrenderer.posXMinus - d1), (float)(worldrenderer.posYMinus - d2), (float)(worldrenderer.posZMinus - d3));
			EaglerAdapter.glCallList(worldrenderer.getGLCallListForPass(k));
			EaglerAdapter.glPopMatrix();
		}
		
		return l;
	}

	public void func_945_d() {
		field_1435_x++;
	}
	
	private static final TextureLocation terrainSun = new TextureLocation("/terrain/sun.png");
	private static final TextureLocation terrainMoon = new TextureLocation("/terrain/moon.png");

	public void func_4142_a(float f) {
		if (mc.theWorld.worldProvider.field_4220_c) {
			return;
		}
		EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
		Vec3D vec3d = worldObj.func_4079_a(mc.field_22009_h, f);
		float f1 = (float) vec3d.xCoord;
		float f2 = (float) vec3d.yCoord;
		float f3 = (float) vec3d.zCoord;
		if (mc.gameSettings.anaglyph) {
			float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
			float f5 = (f1 * 30F + f2 * 70F) / 100F;
			float f7 = (f1 * 30F + f3 * 70F) / 100F;
			f1 = f4;
			f2 = f5;
			f3 = f7;
		}
		EaglerAdapter.glColor3f(f1, f2, f3);
		Tessellator tessellator = Tessellator.instance;
		EaglerAdapter.glDepthMask(false);
		EaglerAdapter.glEnable(2912 /* GL_FOG */);
		EaglerAdapter.glColor3f(f1, f2, f3);
		EaglerAdapter.glCallList(field_1433_z);
		EaglerAdapter.glDisable(2912 /* GL_FOG */);
		EaglerAdapter.glDisable(3008 /* GL_ALPHA_TEST */);
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glBlendFunc(770, 771);
		float af[] = worldObj.worldProvider.func_4097_b(worldObj.getCelestialAngle(f), f);
		if (af != null) {
			EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
			EaglerAdapter.glShadeModel(7425 /* GL_SMOOTH */);
			EaglerAdapter.glPushMatrix();
			EaglerAdapter.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			float f8 = worldObj.getCelestialAngle(f);
			EaglerAdapter.glRotatef(f8 <= 0.5F ? 0.0F : 180F, 0.0F, 0.0F, 1.0F);
			tessellator.startDrawing(6);
			tessellator.setColorRGBA_F(af[0], af[1], af[2], af[3]);
			tessellator.addVertex(0.0D, 100D, 0.0D);
			int i = 16;
			tessellator.setColorRGBA_F(af[0], af[1], af[2], 0.0F);
			for (int j = 0; j <= i; j++) {
				float f12 = ((float) j * 3.141593F * 2.0F) / (float) i;
				float f14 = MathHelper.sin(f12);
				float f15 = MathHelper.cos(f12);
				tessellator.addVertex(f14 * 120F, f15 * 120F, -f15 * 40F * af[3]);
			}

			tessellator.draw();
			EaglerAdapter.glPopMatrix();
			EaglerAdapter.glShadeModel(7424 /* GL_FLAT */);
		}
		EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		EaglerAdapter.glBlendFunc(1, 1);
		EaglerAdapter.glPushMatrix();
		float f6 = 0.0F;
		float f9 = 0.0F;
		float f10 = 0.0F;
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EaglerAdapter.glTranslatef(f6, f9, f10);
		EaglerAdapter.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
		EaglerAdapter.glRotatef(worldObj.getCelestialAngle(f) * 360F, 1.0F, 0.0F, 0.0F);
		float f11 = 30F;
		terrainSun.bindTexture();
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f11, 100D, -f11, 0.0D, 0.0D);
		tessellator.addVertexWithUV(f11, 100D, -f11, 1.0D, 0.0D);
		tessellator.addVertexWithUV(f11, 100D, f11, 1.0D, 1.0D);
		tessellator.addVertexWithUV(-f11, 100D, f11, 0.0D, 1.0D);
		tessellator.draw();
		f11 = 20F;
		terrainMoon.bindTexture();
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f11, -100D, f11, 1.0D, 1.0D);
		tessellator.addVertexWithUV(f11, -100D, f11, 0.0D, 1.0D);
		tessellator.addVertexWithUV(f11, -100D, -f11, 0.0D, 0.0D);
		tessellator.addVertexWithUV(-f11, -100D, -f11, 1.0D, 0.0D);
		tessellator.draw();
		EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
		float f13 = worldObj.func_679_f(f);
		if (f13 > 0.0F) {
			EaglerAdapter.glColor4f(f13, f13, f13, f13);
			EaglerAdapter.glCallList(field_1434_y);
		}
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
		EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
		EaglerAdapter.glEnable(2912 /* GL_FOG */);
		EaglerAdapter.glPopMatrix();
		EaglerAdapter.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
		EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
		EaglerAdapter.glCallList(field_1432_A);
		EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
		EaglerAdapter.glDepthMask(true);
	}
	
	private static final TextureLocation cloudsTexture = new TextureLocation("/environment/clouds.png");

	public void func_4141_b(float f) {
		if (mc.theWorld.worldProvider.field_4220_c) {
			return;
		}
		if (mc.gameSettings.fancyGraphics) {
			func_6510_c(f);
			return;
		}
		EaglerAdapter.glDisable(2884 /* GL_CULL_FACE */);
		float f1 = (float) (mc.field_22009_h.lastTickPosY
				+ (mc.field_22009_h.posY - mc.field_22009_h.lastTickPosY) * (double) f);
		byte byte0 = 32;
		int i = 256 / byte0;
		Tessellator tessellator = Tessellator.instance;
		cloudsTexture.bindTexture();
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glBlendFunc(770, 771);
		Vec3D vec3d = worldObj.func_628_d(f);
		float f2 = (float) vec3d.xCoord;
		float f3 = (float) vec3d.yCoord;
		float f4 = (float) vec3d.zCoord;
		if (mc.gameSettings.anaglyph) {
			float f5 = (f2 * 30F + f3 * 59F + f4 * 11F) / 100F;
			float f7 = (f2 * 30F + f3 * 70F) / 100F;
			float f8 = (f2 * 30F + f4 * 70F) / 100F;
			f2 = f5;
			f3 = f7;
			f4 = f8;
		}
		float f6 = 0.0004882813F;
		double d = mc.field_22009_h.prevPosX + (mc.field_22009_h.posX - mc.field_22009_h.prevPosX) * (double) f
				+ (double) (((float) field_1435_x + f) * 0.03F);
		double d1 = mc.field_22009_h.prevPosZ + (mc.field_22009_h.posZ - mc.field_22009_h.prevPosZ) * (double) f;
		int j = MathHelper.floor_double(d / 2048D);
		int k = MathHelper.floor_double(d1 / 2048D);
		d -= j * 2048 /* GL_EXP */;
		d1 -= k * 2048 /* GL_EXP */;
		float f9 = (120F - f1) + 0.33F;
		float f10 = (float) (d * (double) f6);
		float f11 = (float) (d1 * (double) f6);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f2, f3, f4, 0.8F);
		for (int l = -byte0 * i; l < byte0 * i; l += byte0) {
			for (int i1 = -byte0 * i; i1 < byte0 * i; i1 += byte0) {
				tessellator.addVertexWithUV(l + 0, f9, i1 + byte0, (float) (l + 0) * f6 + f10,
						(float) (i1 + byte0) * f6 + f11);
				tessellator.addVertexWithUV(l + byte0, f9, i1 + byte0, (float) (l + byte0) * f6 + f10,
						(float) (i1 + byte0) * f6 + f11);
				tessellator.addVertexWithUV(l + byte0, f9, i1 + 0, (float) (l + byte0) * f6 + f10,
						(float) (i1 + 0) * f6 + f11);
				tessellator.addVertexWithUV(l + 0, f9, i1 + 0, (float) (l + 0) * f6 + f10, (float) (i1 + 0) * f6 + f11);
			}

		}

		tessellator.draw();
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
		EaglerAdapter.glEnable(2884 /* GL_CULL_FACE */);
	}

	public void func_6510_c(float f) {
		EaglerAdapter.glDisable(2884 /* GL_CULL_FACE */);
		float f1 = (float) (mc.field_22009_h.lastTickPosY
				+ (mc.field_22009_h.posY - mc.field_22009_h.lastTickPosY) * (double) f);
		Tessellator tessellator = Tessellator.instance;
		float f2 = 12F;
		float f3 = 4F;
		double d = (mc.field_22009_h.prevPosX + (mc.field_22009_h.posX - mc.field_22009_h.prevPosX) * (double) f
				+ (double) (((float) field_1435_x + f) * 0.03F)) / (double) f2;
		double d1 = (mc.field_22009_h.prevPosZ + (mc.field_22009_h.posZ - mc.field_22009_h.prevPosZ) * (double) f)
				/ (double) f2 + 0.33000001311302185D;
		float f4 = (108F - f1) + 0.33F;
		int i = MathHelper.floor_double(d / 2048D);
		int j = MathHelper.floor_double(d1 / 2048D);
		d -= i * 2048 /* GL_EXP */;
		d1 -= j * 2048 /* GL_EXP */;
		cloudsTexture.bindTexture();
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glBlendFunc(770, 771);
		Vec3D vec3d = worldObj.func_628_d(f);
		float f5 = (float) vec3d.xCoord;
		float f6 = (float) vec3d.yCoord;
		float f7 = (float) vec3d.zCoord;
		if (mc.gameSettings.anaglyph) {
			float f8 = (f5 * 30F + f6 * 59F + f7 * 11F) / 100F;
			float f10 = (f5 * 30F + f6 * 70F) / 100F;
			float f12 = (f5 * 30F + f7 * 70F) / 100F;
			f5 = f8;
			f6 = f10;
			f7 = f12;
		}
		float f9 = (float) (d * 0.0D);
		float f11 = (float) (d1 * 0.0D);
		float f13 = 0.00390625F;
		f9 = (float) MathHelper.floor_double(d) * f13;
		f11 = (float) MathHelper.floor_double(d1) * f13;
		float f14 = (float) (d - (double) MathHelper.floor_double(d));
		float f15 = (float) (d1 - (double) MathHelper.floor_double(d1));
		int k = 8;
		byte byte0 = 3;
		float f16 = 0.0009765625F;
		EaglerAdapter.glScalef(f2, 1.0F, f2);
		for (int l = 0; l < 2; l++) {
			if (l == 0) {
				EaglerAdapter.glColorMask(false, false, false, false);
			} else {
				EaglerAdapter.glColorMask(true, true, true, true);
			}
			for (int i1 = -byte0 + 1; i1 <= byte0; i1++) {
				for (int j1 = -byte0 + 1; j1 <= byte0; j1++) {
					tessellator.startDrawingQuads();
					float f17 = i1 * k;
					float f18 = j1 * k;
					float f19 = f17 - f14;
					float f20 = f18 - f15;
					if (f4 > -f3 - 1.0F) {
						tessellator.setColorRGBA_F(f5 * 0.7F, f6 * 0.7F, f7 * 0.7F, 0.8F);
						tessellator.setNormal(0.0F, -1F, 0.0F);
						tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + (float) k, (f17 + 0.0F) * f13 + f9,
								(f18 + (float) k) * f13 + f11);
						tessellator.addVertexWithUV(f19 + (float) k, f4 + 0.0F, f20 + (float) k,
								(f17 + (float) k) * f13 + f9, (f18 + (float) k) * f13 + f11);
						tessellator.addVertexWithUV(f19 + (float) k, f4 + 0.0F, f20 + 0.0F,
								(f17 + (float) k) * f13 + f9, (f18 + 0.0F) * f13 + f11);
						tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + 0.0F, (f17 + 0.0F) * f13 + f9,
								(f18 + 0.0F) * f13 + f11);
					}
					if (f4 <= f3 + 1.0F) {
						tessellator.setColorRGBA_F(f5, f6, f7, 0.8F);
						tessellator.setNormal(0.0F, 1.0F, 0.0F);
						tessellator.addVertexWithUV(f19 + 0.0F, (f4 + f3) - f16, f20 + (float) k,
								(f17 + 0.0F) * f13 + f9, (f18 + (float) k) * f13 + f11);
						tessellator.addVertexWithUV(f19 + (float) k, (f4 + f3) - f16, f20 + (float) k,
								(f17 + (float) k) * f13 + f9, (f18 + (float) k) * f13 + f11);
						tessellator.addVertexWithUV(f19 + (float) k, (f4 + f3) - f16, f20 + 0.0F,
								(f17 + (float) k) * f13 + f9, (f18 + 0.0F) * f13 + f11);
						tessellator.addVertexWithUV(f19 + 0.0F, (f4 + f3) - f16, f20 + 0.0F, (f17 + 0.0F) * f13 + f9,
								(f18 + 0.0F) * f13 + f11);
					}
					tessellator.setColorRGBA_F(f5 * 0.9F, f6 * 0.9F, f7 * 0.9F, 0.8F);
					if (i1 > -1) {
						tessellator.setNormal(-1F, 0.0F, 0.0F);
						for (int k1 = 0; k1 < k; k1++) {
							tessellator.addVertexWithUV(f19 + (float) k1 + 0.0F, f4 + 0.0F, f20 + (float) k,
									(f17 + (float) k1 + 0.5F) * f13 + f9, (f18 + (float) k) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float) k1 + 0.0F, f4 + f3, f20 + (float) k,
									(f17 + (float) k1 + 0.5F) * f13 + f9, (f18 + (float) k) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float) k1 + 0.0F, f4 + f3, f20 + 0.0F,
									(f17 + (float) k1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float) k1 + 0.0F, f4 + 0.0F, f20 + 0.0F,
									(f17 + (float) k1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
						}

					}
					if (i1 <= 1) {
						tessellator.setNormal(1.0F, 0.0F, 0.0F);
						for (int l1 = 0; l1 < k; l1++) {
							tessellator.addVertexWithUV((f19 + (float) l1 + 1.0F) - f16, f4 + 0.0F, f20 + (float) k,
									(f17 + (float) l1 + 0.5F) * f13 + f9, (f18 + (float) k) * f13 + f11);
							tessellator.addVertexWithUV((f19 + (float) l1 + 1.0F) - f16, f4 + f3, f20 + (float) k,
									(f17 + (float) l1 + 0.5F) * f13 + f9, (f18 + (float) k) * f13 + f11);
							tessellator.addVertexWithUV((f19 + (float) l1 + 1.0F) - f16, f4 + f3, f20 + 0.0F,
									(f17 + (float) l1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
							tessellator.addVertexWithUV((f19 + (float) l1 + 1.0F) - f16, f4 + 0.0F, f20 + 0.0F,
									(f17 + (float) l1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
						}

					}
					tessellator.setColorRGBA_F(f5 * 0.8F, f6 * 0.8F, f7 * 0.8F, 0.8F);
					if (j1 > -1) {
						tessellator.setNormal(0.0F, 0.0F, -1F);
						for (int i2 = 0; i2 < k; i2++) {
							tessellator.addVertexWithUV(f19 + 0.0F, f4 + f3, f20 + (float) i2 + 0.0F,
									(f17 + 0.0F) * f13 + f9, (f18 + (float) i2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float) k, f4 + f3, f20 + (float) i2 + 0.0F,
									(f17 + (float) k) * f13 + f9, (f18 + (float) i2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float) k, f4 + 0.0F, f20 + (float) i2 + 0.0F,
									(f17 + (float) k) * f13 + f9, (f18 + (float) i2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + (float) i2 + 0.0F,
									(f17 + 0.0F) * f13 + f9, (f18 + (float) i2 + 0.5F) * f13 + f11);
						}

					}
					if (j1 <= 1) {
						tessellator.setNormal(0.0F, 0.0F, 1.0F);
						for (int j2 = 0; j2 < k; j2++) {
							tessellator.addVertexWithUV(f19 + 0.0F, f4 + f3, (f20 + (float) j2 + 1.0F) - f16,
									(f17 + 0.0F) * f13 + f9, (f18 + (float) j2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float) k, f4 + f3, (f20 + (float) j2 + 1.0F) - f16,
									(f17 + (float) k) * f13 + f9, (f18 + (float) j2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + (float) k, f4 + 0.0F, (f20 + (float) j2 + 1.0F) - f16,
									(f17 + (float) k) * f13 + f9, (f18 + (float) j2 + 0.5F) * f13 + f11);
							tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, (f20 + (float) j2 + 1.0F) - f16,
									(f17 + 0.0F) * f13 + f9, (f18 + (float) j2 + 0.5F) * f13 + f11);
						}

					}
					tessellator.draw();
				}

			}

		}

		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
		EaglerAdapter.glEnable(2884 /* GL_CULL_FACE */);
	}

	public boolean updateRenderers(EntityLiving entityliving, boolean flag) {
		//boolean flag1 = false;
		//if (flag1) {
			int t = worldRenderersToUpdate.size();
			if(t > 0) {
				Collections.sort(worldRenderersToUpdate, new RenderSorter(entityliving));
				boolean b = false;
				for(int i = t - 1; i >= 0; --i) {
					WorldRenderer worldrenderer = (WorldRenderer) worldRenderersToUpdate.get(i);
					if(worldrenderer.isInFrustum || worldrenderer.distanceToEntity(entityliving) < 1024F) {
						b = true;
						worldrenderer.updateRenderer();
						worldRenderersToUpdate.remove(i);
						break;
					}
				}
				if(!b) {
					((WorldRenderer)worldRenderersToUpdate.remove(t - 1)).updateRenderer();
				}
			}
			return worldRenderersToUpdate.size() == 0;
		//}
		/*
		RenderSorter rendersorter = new RenderSorter(entityliving);
		WorldRenderer aworldrenderer[] = new WorldRenderer[3];
		ArrayList arraylist = null;
		int l = worldRenderersToUpdate.size();
		int i1 = 0;
		for (int j1 = 0; j1 < l; j1++) {
			WorldRenderer worldrenderer1 = (WorldRenderer) worldRenderersToUpdate.get(j1);
			if (!flag) {
				if (worldrenderer1.distanceToEntity(entityliving) > 1024F) {
					int k2;
					for (k2 = 0; k2 < 3 && (aworldrenderer[k2] == null
							|| rendersorter.func_993_a(aworldrenderer[k2], worldrenderer1) <= 0); k2++) {
					}
					if (--k2 <= 0) {
						continue;
					}
					for (int i3 = k2; --i3 != 0;) {
						aworldrenderer[i3 - 1] = aworldrenderer[i3];
					}

					aworldrenderer[k2] = worldrenderer1;
					continue;
				}
			}else if (!worldrenderer1.isInFrustum) {
				continue;
			}
			if (arraylist == null) {
				arraylist = new ArrayList();
			}
			i1++;
			arraylist.add(worldrenderer1);
			worldRenderersToUpdate.set(j1, null);
		}

		if (arraylist != null) {
			if (arraylist.size() > 1) {
				Collections.sort(arraylist, rendersorter);
			}
			for (int k1 = arraylist.size() - 1; k1 >= 0; k1--) {
				WorldRenderer worldrenderer2 = (WorldRenderer) arraylist.get(k1);
				worldrenderer2.updateRenderer();
				worldrenderer2.needsUpdate = false;
			}

		}
		int l1 = 0;
		for (int i2 = 2; i2 >= 0; i2--) {
			WorldRenderer worldrenderer3 = aworldrenderer[i2];
			if (worldrenderer3 == null) {
				continue;
			}
			if (!worldrenderer3.isInFrustum && i2 != 2) {
				aworldrenderer[i2] = null;
				aworldrenderer[0] = null;
				break;
			}
			aworldrenderer[i2].updateRenderer();
			aworldrenderer[i2].needsUpdate = false;
			l1++;
		}

		int j2 = 0;
		int l2 = 0;
		for (int j3 = worldRenderersToUpdate.size(); j2 != j3; j2++) {
			WorldRenderer worldrenderer4 = (WorldRenderer) worldRenderersToUpdate.get(j2);
			if (worldrenderer4 == null || worldrenderer4 == aworldrenderer[0] || worldrenderer4 == aworldrenderer[1]
					|| worldrenderer4 == aworldrenderer[2]) {
				continue;
			}
			if (l2 != j2) {
				worldRenderersToUpdate.set(l2, worldrenderer4);
			}
			l2++;
		}

		while (--j2 >= l2) {
			worldRenderersToUpdate.remove(j2);
		}
		return l == i1 + l1;
		*/
	}
	
	private static final TextureLocation terrainTexture = new TextureLocation("/terrain.png");

	public void func_959_a(EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i,
			ItemStack itemstack, float f) {
		Tessellator tessellator = Tessellator.instance;
		EaglerAdapter.glEnable(3042 /* GL_BLEND */);
		EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
		EaglerAdapter.glBlendFunc(770, 1);
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F,
				(MathHelper.sin((float) System.currentTimeMillis() / 100F) * 0.2F + 0.4F) * 0.5F);
		if (i == 0) {
			if (field_1450_i > 0.0F) {
				EaglerAdapter.glBlendFunc(774, 768);
				terrainTexture.bindTexture();
				EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
				EaglerAdapter.glPushMatrix();
				int k = worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY,
						movingobjectposition.blockZ);
				Block block = k <= 0 ? null : Block.blocksList[k];
				EaglerAdapter.glDisable(3008 /* GL_ALPHA_TEST */);
				EaglerAdapter.glPolygonOffset(3F, 3F);
				EaglerAdapter.glEnable(32823 /* GL_POLYGON_OFFSET_FILL */);
				tessellator.startDrawingQuads();
				double d = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double) f;
				double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double) f;
				double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double) f;
				tessellator.setTranslationD(-d, -d1, -d2);
				tessellator.disableColor();
				if (block == null) {
					block = Block.stone;
				}
				field_1438_u.renderBlockUsingTexture(block, movingobjectposition.blockX, movingobjectposition.blockY,
						movingobjectposition.blockZ, 240 + (int) (field_1450_i * 10F));
				tessellator.draw();
				tessellator.setTranslationD(0.0D, 0.0D, 0.0D);
				EaglerAdapter.glPolygonOffset(0.0F, 0.0F);
				EaglerAdapter.glDisable(32823 /* GL_POLYGON_OFFSET_FILL */);
				EaglerAdapter.glEnable(3008 /* GL_ALPHA_TEST */);
				EaglerAdapter.glDepthMask(true);
				EaglerAdapter.glPopMatrix();
			}
		} else if (itemstack != null) {
			EaglerAdapter.glBlendFunc(770, 771);
			float f1 = MathHelper.sin((float) System.currentTimeMillis() / 100F) * 0.2F + 0.8F;
			EaglerAdapter.glColor4f(f1, f1, f1, MathHelper.sin((float) System.currentTimeMillis() / 200F) * 0.2F + 0.5F);
			terrainTexture.bindTexture();
			int i1 = movingobjectposition.blockX;
			int j1 = movingobjectposition.blockY;
			int k1 = movingobjectposition.blockZ;
			if (movingobjectposition.sideHit == 0) {
				j1--;
			}
			if (movingobjectposition.sideHit == 1) {
				j1++;
			}
			if (movingobjectposition.sideHit == 2) {
				k1--;
			}
			if (movingobjectposition.sideHit == 3) {
				k1++;
			}
			if (movingobjectposition.sideHit == 4) {
				i1--;
			}
			if (movingobjectposition.sideHit == 5) {
				i1++;
			}
		}
		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
		EaglerAdapter.glDisable(3008 /* GL_ALPHA_TEST */);
	}

	public void drawSelectionBox(EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i,
			ItemStack itemstack, float f) {
		if (i == 0 && movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			EaglerAdapter.glEnable(3042 /* GL_BLEND */);
			EaglerAdapter.glBlendFunc(770, 771);
			EaglerAdapter.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
			EaglerAdapter.glLineWidth(2.0F);
			EaglerAdapter.glDisable(3553 /* GL_TEXTURE_2D */);
			EaglerAdapter.glDepthMask(false);
			float f1 = 0.002F;
			int j = worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY,
					movingobjectposition.blockZ);
			if (j > 0) {
				Block.blocksList[j].setBlockBoundsBasedOnState(worldObj, movingobjectposition.blockX,
						movingobjectposition.blockY, movingobjectposition.blockZ);
				double d = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double) f;
				double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double) f;
				double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double) f;
				drawOutlinedBoundingBox(Block.blocksList[j]
						.getSelectedBoundingBoxFromPool(worldObj, movingobjectposition.blockX,
								movingobjectposition.blockY, movingobjectposition.blockZ)
						.expand(f1, f1, f1).getOffsetBoundingBox(-d, -d1, -d2));
			}
			EaglerAdapter.glDepthMask(true);
			EaglerAdapter.glEnable(3553 /* GL_TEXTURE_2D */);
			EaglerAdapter.glDisable(3042 /* GL_BLEND */);
		}
	}

	private void drawOutlinedBoundingBox(AxisAlignedBB axisalignedbb) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(3);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.draw();
		tessellator.startDrawing(3);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.draw();
		tessellator.startDrawing(1);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.draw();
	}

	public void func_949_a(int i, int j, int k, int l, int i1, int j1) {
		int k1 = MathHelper.bucketInt(i, 16);
		int l1 = MathHelper.bucketInt(j, 16);
		int i2 = MathHelper.bucketInt(k, 16);
		int j2 = MathHelper.bucketInt(l, 16);
		int k2 = MathHelper.bucketInt(i1, 16);
		int l2 = MathHelper.bucketInt(j1, 16);
		for (int i3 = k1; i3 <= j2; i3++) {
			int j3 = i3 % renderChunksWide;
			if (j3 < 0) {
				j3 += renderChunksWide;
			}
			for (int k3 = l1; k3 <= k2; k3++) {
				int l3 = k3 % renderChunksTall;
				if (l3 < 0) {
					l3 += renderChunksTall;
				}
				for (int i4 = i2; i4 <= l2; i4++) {
					int j4 = i4 % renderChunksDeep;
					if (j4 < 0) {
						j4 += renderChunksDeep;
					}
					int k4 = (j4 * renderChunksTall + l3) * renderChunksWide + j3;
					WorldRenderer worldrenderer = worldRenderers[k4];
					if (!worldrenderer.needsUpdate) {
						worldRenderersToUpdate.add(worldrenderer);
						worldrenderer.markDirty();
					}
				}

			}

		}

	}

	public void func_934_a(int i, int j, int k) {
		func_949_a(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
	}

	public void markBlockRangeNeedsUpdate(int i, int j, int k, int l, int i1, int j1) {
		func_949_a(i - 1, j - 1, k - 1, l + 1, i1 + 1, j1 + 1);
	}

	public void func_960_a(ICamera icamera, float f) {
		for (int i = 0; i < worldRenderers.length; i++) {
			if (!worldRenderers[i].canRender() && (!worldRenderers[i].isInFrustum || (i + field_1449_j & 0xf) == 0)) {
				worldRenderers[i].updateInFrustrum(icamera);
			}
		}

		field_1449_j++;
	}

	public void playRecord(String s, int i, int j, int k) {
		if (s != null) {
			mc.ingameGUI.setRecordPlayingMessage((new StringBuilder()).append("C418 - ").append(s).toString());
		}
		mc.sndManager.func_331_a(s, i, j, k, 1.0F, 1.0F);
	}

	public void playSound(String s, double d, double d1, double d2, float f, float f1) {
		float f2 = 16F;
		if (f > 1.0F) {
			f2 *= f;
		}
		if (mc.field_22009_h.getDistanceSq(d, d1, d2) < (double) (f2 * f2)) {
			mc.sndManager.playSound(s, (float) d, (float) d1, (float) d2, f, f1);
		}
	}

	public void spawnParticle(String s, double d, double d1, double d2, double d3, double d4, double d5) {
		double d6 = mc.field_22009_h.posX - d;
		double d7 = mc.field_22009_h.posY - d1;
		double d8 = mc.field_22009_h.posZ - d2;
		double d9 = 16D;
		if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9) {
			return;
		}
		if (s == "bubble") {
			mc.effectRenderer.addEffect(new EntityBubbleFX(worldObj, d, d1, d2, d3, d4, d5));
		} else if (s == "smoke") {
			mc.effectRenderer.addEffect(new EntitySmokeFX(worldObj, d, d1, d2, d3, d4, d5));
		} else if (s == "note") {
			mc.effectRenderer.addEffect(new EntityNoteFX(worldObj, d, d1, d2, d3, d4, d5));
		} else if (s == "portal") {
			mc.effectRenderer.addEffect(new EntityPortalFX(worldObj, d, d1, d2, d3, d4, d5));
		} else if (s == "explode") {
			mc.effectRenderer.addEffect(new EntityExplodeFX(worldObj, d, d1, d2, d3, d4, d5));
		} else if (s == "flame") {
			mc.effectRenderer.addEffect(new EntityFlameFX(worldObj, d, d1, d2, d3, d4, d5));
		} else if (s == "lava") {
			mc.effectRenderer.addEffect(new EntityLavaFX(worldObj, d, d1, d2));
		} else if (s == "splash") {
			mc.effectRenderer.addEffect(new EntitySplashFX(worldObj, d, d1, d2, d3, d4, d5));
		} else if (s == "largesmoke") {
			mc.effectRenderer.addEffect(new EntitySmokeFX(worldObj, d, d1, d2, d3, d4, d5, 2.5F));
		} else if (s == "reddust") {
			mc.effectRenderer.addEffect(new EntityReddustFX(worldObj, d, d1, d2, (float) d3, (float) d4, (float) d5));
		} else if (s == "snowballpoof") {
			mc.effectRenderer.addEffect(new EntitySlimeFX(worldObj, d, d1, d2, Item.snowball));
		} else if (s == "slime") {
			mc.effectRenderer.addEffect(new EntitySlimeFX(worldObj, d, d1, d2, Item.slimeBall));
		}
	}

	public void obtainEntitySkin(Entity entity) {
		//entity.updateCloak();
		//if (entity.skinUrl != null) {
		//	renderEngine.obtainImageData(entity.skinUrl, new ImageBufferDownload());
		//}
		//if (entity.cloakUrl != null) {
		//	renderEngine.obtainImageData(entity.cloakUrl, new ImageBufferDownload());
		//}
	}

	public void releaseEntitySkin(Entity entity) {
		//if (entity.skinUrl != null) {
		//	renderEngine.releaseImageData(entity.skinUrl);
		//}
		//if (entity.cloakUrl != null) {
		//	renderEngine.releaseImageData(entity.cloakUrl);
		//}
	}

	public void updateAllRenderers() {
		for (int i = 0; i < worldRenderers.length; i++) {
			if (worldRenderers[i].field_1747_A && !worldRenderers[i].needsUpdate) {
				worldRenderersToUpdate.add(worldRenderers[i]);
				worldRenderers[i].markDirty();
			}
		}

	}

	public void doNothingWithTileEntity(int i, int j, int k, TileEntity tileentity) {
	}

	public List tileEntities;
	private World worldObj;
	private RenderEngine renderEngine;
	private List worldRenderersToUpdate;
	private WorldRenderer sortedWorldRenderers[];
	private WorldRenderer worldRenderers[];
	private int[] glOcclusionQuery;
	private int renderChunksWide;
	private int renderChunksTall;
	private int renderChunksDeep;
	private int renderListBase;
	private Minecraft mc;
	private RenderBlocks field_1438_u;
	private IntBuffer field_1437_v;
	private boolean field_1436_w;
	private int field_1435_x;
	private int field_1434_y;
	private int field_1433_z;
	private int field_1432_A;
	private int field_1431_B;
	private int field_1430_C;
	private int field_1429_D;
	private int field_1428_E;
	private int field_1427_F;
	private int field_1426_G;
	private int renderDistance;
	private int field_1424_I;
	private int field_1423_J;
	private int field_1422_K;
	private int field_1421_L;
	int field_1457_b[];
	IntBuffer field_1456_c;
	private int field_1420_M;
	private int field_1419_N;
	private int field_1418_O;
	private int field_1417_P;
	private int field_1416_Q;
	private int field_21156_R;
	private List field_1415_R;
	int field_1455_d;
	int field_1454_e;
	double field_1453_f;
	double field_1452_g;
	double field_1451_h;
	public float field_1450_i;
	int field_1449_j;
}
