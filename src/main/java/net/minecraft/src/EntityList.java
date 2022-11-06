package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EntityList {

	public EntityList() {
	}

	private static void addMapping(Class class1, Function<World, Entity> construct, String s, int i) {
		stringToConstructorMapping.put(s, construct);
		classToStringMapping.put(class1, s);
		IDtoConstructorMapping.put(Integer.valueOf(i), construct);
		classToIDMapping.put(class1, Integer.valueOf(i));
	}

	public static Entity createEntityInWorld(String s, World world) {
		Entity entity = null;
		try {
			Function<World, Entity> class1 = (Function<World, Entity>) stringToConstructorMapping.get(s);
			if (class1 != null) {
				entity = class1.apply(world);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return entity;
	}

	public static Entity createEntityFromNBT(NBTTagCompound nbttagcompound, World world) {
		Entity entity = null;
		try {
			Function<World, Entity> class1 = (Function<World, Entity>) stringToConstructorMapping.get(nbttagcompound.getString("id"));
			if (class1 != null) {
				entity = class1.apply(world);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if (entity != null) {
			entity.readFromNBT(nbttagcompound);
		} else {
			System.out.println((new StringBuilder()).append("Skipping Entity with id ")
					.append(nbttagcompound.getString("id")).toString());
		}
		return entity;
	}

	public static Entity createEntity(int i, World world) {
		Entity entity = null;
		try {
			Function<World, Entity> class1 = (Function<World, Entity>) IDtoConstructorMapping.get(Integer.valueOf(i));
			if (class1 != null) {
				entity = class1.apply(world);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if (entity == null) {
			System.out.println((new StringBuilder()).append("Skipping Entity with id ").append(i).toString());
		}
		return entity;
	}

	public static int getEntityID(Entity entity) {
		return ((Integer) classToIDMapping.get(entity.getClass())).intValue();
	}

	public static String getEntityString(Entity entity) {
		return (String) classToStringMapping.get(entity.getClass());
	}

	private static Map stringToConstructorMapping = new HashMap();
	private static Map classToStringMapping = new HashMap();
	private static Map IDtoConstructorMapping = new HashMap();
	private static Map classToIDMapping = new HashMap();

	static {
		addMapping(EntityArrow.class, (w) -> new EntityArrow(w), "Arrow", 10);
		addMapping(EntitySnowball.class, (w) -> new EntitySnowball(w), "Snowball", 11);
		addMapping(EntityItem.class, (w) -> new EntityItem(w), "Item", 1);
		addMapping(EntityPainting.class, (w) -> new EntityPainting(w), "Painting", 9);
		addMapping(EntityCreeper.class, (w) -> new EntityCreeper(w), "Creeper", 50);
		addMapping(EntitySkeleton.class, (w) -> new EntitySkeleton(w), "Skeleton", 51);
		addMapping(EntitySpider.class, (w) -> new EntitySpider(w), "Spider", 52);
		addMapping(EntityZombieSimple.class, (w) -> new EntityZombieSimple(w), "Giant", 53);
		addMapping(EntityZombie.class, (w) -> new EntityZombie(w), "Zombie", 54);
		addMapping(EntitySlime.class, (w) -> new EntitySlime(w), "Slime", 55);
		addMapping(EntityGhast.class, (w) -> new EntityGhast(w), "Ghast", 56);
		addMapping(EntityPigZombie.class, (w) -> new EntityPigZombie(w), "PigZombie", 57);
		addMapping(EntityPig.class, (w) -> new EntityPig(w), "Pig", 90);
		addMapping(EntitySheep.class, (w) -> new EntitySheep(w), "Sheep", 91);
		addMapping(EntityCow.class, (w) -> new EntityCow(w), "Cow", 92);
		addMapping(EntityChicken.class, (w) -> new EntityChicken(w), "Chicken", 93);
		addMapping(EntitySquid.class, (w) -> new EntitySquid(w), "Squid", 94);
		addMapping(EntityTNTPrimed.class, (w) -> new EntityTNTPrimed(w), "PrimedTnt", 20);
		addMapping(EntityFallingSand.class, (w) -> new EntityFallingSand(w), "FallingSand", 21);
		addMapping(EntityMinecart.class, (w) -> new EntityMinecart(w), "Minecart", 40);
		addMapping(EntityBoat.class, (w) -> new EntityBoat(w), "Boat", 41);
	}
}
