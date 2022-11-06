// Decompiled with: CFR 0.152
// Class Version: 5
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public final class SpawnerAnimals {
	private static Set<ChunkCoordIntPair> field_6544_a = new HashSet();
	protected static final List<Function<World, EntityLiving>> field_22391_a = new ArrayList();
			
	static {
		field_22391_a.add((w) -> new EntitySpider(w));
		field_22391_a.add((w) -> new EntityZombie(w));
		field_22391_a.add((w) -> new EntitySkeleton(w));
	}

	protected static ChunkPosition func_4153_a(World world, int n, int n2) {
		int n3 = n + world.rand.nextInt(16);
		int n4 = world.rand.nextInt(128);
		int n5 = n2 + world.rand.nextInt(16);
		return new ChunkPosition(n3, n4, n5);
	}

	public static final int performSpawning(World world, boolean bl, boolean bl2) {
		Object object;
		int n;
		if (!bl && !bl2) {
			return 0;
		}
		field_6544_a.clear();
		for (n = 0; n < world.playerEntities.size(); ++n) {
			object = (EntityPlayer) world.playerEntities.get(n);
			int n2 = MathHelper.floor_double(((EntityPlayer) object).posX / 16.0);
			int n3 = MathHelper.floor_double(((EntityPlayer) object).posZ / 16.0);
			int n4 = 8;
			for (int i = -n4; i <= n4; ++i) {
				for (int j = -n4; j <= n4; ++j) {
					field_6544_a.add(new ChunkCoordIntPair(i + n2, j + n3));
				}
			}
		}
		n = 0;
		object = world.func_22137_s();
		for (EnumCreatureType enumCreatureType : EnumCreatureType.values()) {
			if (enumCreatureType.func_21168_d() && !bl2 || !enumCreatureType.func_21168_d() && !bl
					|| world.countEntities(
							enumCreatureType.getCreatureClass()) > enumCreatureType.getMaxNumberOfCreature()
									* field_6544_a.size() / 256)
				continue;
			block6: for (ChunkCoordIntPair chunkCoordIntPair : field_6544_a) {
				MobSpawnerBase mobSpawnerBase = world.getWorldChunkManager().func_4074_a(chunkCoordIntPair);
				List<Function<World, EntityLiving>> classArray = mobSpawnerBase.getEntitiesForType(enumCreatureType);
				if (classArray == null || classArray.size() == 0)
					continue;
				int n5 = world.rand.nextInt(classArray.size());
				ChunkPosition chunkPosition = SpawnerAnimals.func_4153_a(world, chunkCoordIntPair.chunkXPos * 16,
						chunkCoordIntPair.chunkZPos * 16);
				int n6 = chunkPosition.x;
				int n7 = chunkPosition.y;
				int n8 = chunkPosition.z;
				if (world.isBlockOpaqueCube(n6, n7, n8)
						|| world.getBlockMaterial(n6, n7, n8) != enumCreatureType.getCreatureMaterial())
					continue;
				int n9 = 0;
				for (int i = 0; i < 3; ++i) {
					int n10 = n6;
					int n11 = n7;
					int n12 = n8;
					int n13 = 6;
					for (int j = 0; j < 4; ++j) {
						EntityLiving entityLiving;
						float f;
						float f2;
						float f3;
						float f4;
						float f5;
						float f6;
						float f7;
						if (!SpawnerAnimals.func_21203_a(enumCreatureType, world,
								n10 += world.rand.nextInt(n13) - world.rand.nextInt(n13),
								n11 += world.rand.nextInt(1) - world.rand.nextInt(1),
								n12 += world.rand.nextInt(n13) - world.rand.nextInt(n13))
								|| world.getClosestPlayer(f7 = (float) n10 + 0.5f, f6 = (float) n11,
										f5 = (float) n12 + 0.5f, 24.0) != null
								|| (f4 = (f3 = f7 - (float) ((ChunkCoordinates) object).field_22395_a) * f3
										+ (f2 = f6 - (float) ((ChunkCoordinates) object).field_22394_b) * f2
										+ (f = f5 - (float) ((ChunkCoordinates) object).field_22396_c) * f) < 576.0f)
							continue;
						try {
							entityLiving = classArray.get(n5).apply(world);
						} catch (Exception exception) {
							exception.printStackTrace();
							return n;
						}
						entityLiving.setLocationAndAngles(f7, f6, f5, world.rand.nextFloat() * 360.0f, 0.0f);
						if (entityLiving.getCanSpawnHere()) {
							world.entityJoinedWorld(entityLiving);
							SpawnerAnimals.func_21204_a(entityLiving, world, f7, f6, f5);
							if (++n9 >= entityLiving.getMaxSpawnedInChunk())
								continue block6;
						}
						n += n9;
					}
				}
			}
		}
		return n;
	}

	private static boolean func_21203_a(EnumCreatureType enumCreatureType, World world, int n, int n2, int n3) {
		if (enumCreatureType.getCreatureMaterial() == Material.water) {
			return world.getBlockMaterial(n, n2, n3).getIsLiquid() && !world.isBlockOpaqueCube(n, n2 + 1, n3);
		}
		return world.isBlockOpaqueCube(n, n2 - 1, n3) && !world.isBlockOpaqueCube(n, n2, n3)
				&& !world.getBlockMaterial(n, n2, n3).getIsLiquid() && !world.isBlockOpaqueCube(n, n2 + 1, n3);
	}

	private static void func_21204_a(EntityLiving entityLiving, World world, float f, float f2, float f3) {
		if (entityLiving instanceof EntitySpider && world.rand.nextInt(100) == 0) {
			EntitySkeleton entitySkeleton = new EntitySkeleton(world);
			entitySkeleton.setLocationAndAngles(f, f2, f3, entityLiving.rotationYaw, 0.0f);
			world.entityJoinedWorld(entitySkeleton);
			entitySkeleton.mountEntity(entityLiving);
		} else if (entityLiving instanceof EntitySheep) {
			((EntitySheep) ((Object) entityLiving)).setFleeceColor(EntitySheep.func_21070_a(world.rand));
		}
	}

	public static boolean func_22390_a(World world, List list) {
		boolean bl = false;
		Pathfinder pathfinder = new Pathfinder(world);
		for (EntityPlayer entityPlayer : (List<EntityPlayer>) list) {
			List<Function<World, EntityLiving>> classArray = field_22391_a;
			if (classArray == null || classArray.size() == 0)
				continue;
			boolean bl2 = false;
			for (int i = 0; i < 20 && !bl2; ++i) {
				PathEntity pathEntity;
				EntityLiving entityLiving;
				int n;
				int n2 = MathHelper.floor_double(entityPlayer.posX) + world.rand.nextInt(32) - world.rand.nextInt(32);
				int n3 = MathHelper.floor_double(entityPlayer.posY) + world.rand.nextInt(32) - world.rand.nextInt(32);
				int n4 = MathHelper.floor_double(entityPlayer.posZ) + world.rand.nextInt(16) - world.rand.nextInt(16);
				if (n4 < 1) {
					n4 = 1;
				} else if (n4 > 128) {
					n4 = 128;
				}
				int n5 = world.rand.nextInt(classArray.size());
				for (n = n4; n > 2 && !world.isBlockOpaqueCube(n2, n - 1, n3); --n) {
				}
				while (!SpawnerAnimals.func_21203_a(EnumCreatureType.monster, world, n2, n, n3) && n < n4 + 16
						&& n < 128) {
					++n;
				}
				if (n >= n4 + 16 || n >= 128) {
					n = n4;
					continue;
				}
				float f = (float) n2 + 0.5f;
				float f2 = n;
				float f3 = (float) n3 + 0.5f;
				try {
					entityLiving = (EntityLiving) classArray.get(n5).apply(world);
				} catch (Exception exception) {
					exception.printStackTrace();
					return bl;
				}
				entityLiving.setLocationAndAngles(f, f2, f3, world.rand.nextFloat() * 360.0f, 0.0f);
				if (!entityLiving.getCanSpawnHere()
						|| (pathEntity = pathfinder.createEntityPathTo(entityLiving, entityPlayer, 32.0f)) == null
						|| pathEntity.pathLength <= 1)
					continue;
				PathPoint pathPoint = pathEntity.func_22328_c();
				if (!(Math.abs((double) pathPoint.xCoord - entityPlayer.posX) < 1.5)
						|| !(Math.abs((double) pathPoint.yCoord - entityPlayer.posY) < 1.5)
						|| !(Math.abs((double) pathPoint.zCoord - entityPlayer.posZ) < 1.5))
					continue;
				ChunkCoordinates chunkCoordinates = BlockBed.func_22028_g(world,
						MathHelper.floor_double(entityPlayer.posX), MathHelper.floor_double(entityPlayer.posY),
						MathHelper.floor_double(entityPlayer.posZ), 1);
				entityLiving.setLocationAndAngles((float) chunkCoordinates.field_22395_a + 0.5f,
						chunkCoordinates.field_22394_b, (float) chunkCoordinates.field_22396_c + 0.5f, 0.0f, 0.0f);
				world.entityJoinedWorld(entityLiving);
				SpawnerAnimals.func_21204_a(entityLiving, world, (float) chunkCoordinates.field_22395_a + 0.5f,
						chunkCoordinates.field_22394_b, (float) chunkCoordinates.field_22396_c + 0.5f);
				entityPlayer.func_22056_a(true, false);
				entityLiving.func_22050_O();
				bl = true;
				bl2 = true;
			}
		}
		return bl;
	}
}
