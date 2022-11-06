package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.lax1dude.eaglercraft.AWTColor;
import net.lax1dude.eaglercraft.EaglercraftRandom;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class MobSpawnerBase {

	public MobSpawnerBase() {
		topBlock = (byte) Block.grass.blockID;
		fillerBlock = (byte) Block.dirt.blockID;
		field_6502_q = 0x4ee031;
		biomeMonsters = new ArrayList();
		biomeMonsters.add((w) -> new EntitySpider(w));
		biomeMonsters.add((w) -> new EntityZombie(w));
		biomeMonsters.add((w) -> new EntitySkeleton(w));
		biomeMonsters.add((w) -> new EntityCreeper(w));
		biomeMonsters.add((w) -> new EntitySlime(w));
		biomeCreatures = new ArrayList();
		biomeCreatures.add((w) -> new EntitySheep(w));
		biomeCreatures.add((w) -> new EntityPig(w));
		biomeCreatures.add((w) -> new EntityChicken(w));
		biomeCreatures.add((w) -> new EntityCow(w));
		biomeWaterCreatures = new ArrayList();
		biomeWaterCreatures.add((w) -> new EntitySquid(w));
	}

	public static void generateBiomeLookup() {
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				biomeLookupTable[i + j * 64] = getBiome((float) i / 63F, (float) j / 63F);
			}

		}

		desert.topBlock = desert.fillerBlock = (byte) Block.sand.blockID;
		iceDesert.topBlock = iceDesert.fillerBlock = (byte) Block.sand.blockID;
	}

	public WorldGenerator getRandomWorldGenForTrees(EaglercraftRandom random) {
		if (random.nextInt(10) == 0) {
			return new WorldGenBigTree();
		} else {
			return new WorldGenTrees();
		}
	}

	protected MobSpawnerBase doesNothingForMobSpawnerBase() {
		return this;
	}

	protected MobSpawnerBase setBiomeName(String s) {
		biomeName = s;
		return this;
	}

	protected MobSpawnerBase func_4124_a(int i) {
		field_6502_q = i;
		return this;
	}

	protected MobSpawnerBase setColor(int i) {
		color = i;
		return this;
	}

	public static MobSpawnerBase getBiomeFromLookup(double d, double d1) {
		int i = (int) (d * 63D);
		int j = (int) (d1 * 63D);
		return biomeLookupTable[i + j * 64];
	}

	public static MobSpawnerBase getBiome(float f, float f1) {
		f1 *= f;
		if (f < 0.1F) {
			return tundra;
		}
		if (f1 < 0.2F) {
			if (f < 0.5F) {
				return tundra;
			}
			if (f < 0.95F) {
				return savanna;
			} else {
				return desert;
			}
		}
		if (f1 > 0.5F && f < 0.7F) {
			return swampland;
		}
		if (f < 0.5F) {
			return taiga;
		}
		if (f < 0.97F) {
			if (f1 < 0.35F) {
				return shrubland;
			} else {
				return forest;
			}
		}
		if (f1 < 0.45F) {
			return plains;
		}
		if (f1 < 0.9F) {
			return seasonalForest;
		} else {
			return rainforest;
		}
	}

	public int getSkyColorByTemp(float f) {
		f /= 3F;
		if (f < -1F) {
			f = -1F;
		}
		if (f > 1.0F) {
			f = 1.0F;
		}
		return AWTColor.HSBtoRGB(0.6222222F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
	}

	public List<Function<World, EntityLiving>> getEntitiesForType(EnumCreatureType enumcreaturetype) {
		if (enumcreaturetype == EnumCreatureType.monster) {
			return biomeMonsters;
		}
		if (enumcreaturetype == EnumCreatureType.creature) {
			return biomeCreatures;
		}
		if (enumcreaturetype == EnumCreatureType.waterCreature) {
			return biomeWaterCreatures;
		} else {
			return null;
		}
	}

	public static final MobSpawnerBase rainforest = (new MobSpawnerRainforest()).setColor(0x8fa36)
			.setBiomeName("Rainforest").func_4124_a(0x1ff458);
	public static final MobSpawnerBase swampland = (new MobSpawnerSwamp()).setColor(0x7f9b2).setBiomeName("Swampland")
			.func_4124_a(0x8baf48);
	public static final MobSpawnerBase seasonalForest = (new MobSpawnerBase()).setColor(0x9be023)
			.setBiomeName("Seasonal Forest");
	public static final MobSpawnerBase forest = (new MobSpawnerForest()).setColor(0x56621).setBiomeName("Forest")
			.func_4124_a(0x4eba31);
	public static final MobSpawnerBase savanna = (new MobSpawnerDesert()).setColor(0xd9e023).setBiomeName("Savanna");
	public static final MobSpawnerBase shrubland = (new MobSpawnerBase()).setColor(0xa1ad20).setBiomeName("Shrubland");
	public static final MobSpawnerBase taiga = (new MobSpawnerTaiga()).setColor(0x2eb153).setBiomeName("Taiga")
			.doesNothingForMobSpawnerBase().func_4124_a(0x7bb731);
	public static final MobSpawnerBase desert = (new MobSpawnerDesert()).setColor(0xfa9418).setBiomeName("Desert");
	public static final MobSpawnerBase plains = (new MobSpawnerDesert()).setColor(0xffd910).setBiomeName("Plains");
	public static final MobSpawnerBase iceDesert = (new MobSpawnerDesert()).setColor(0xffed93)
			.setBiomeName("Ice Desert").doesNothingForMobSpawnerBase().func_4124_a(0xc4d339);
	public static final MobSpawnerBase tundra = (new MobSpawnerBase()).setColor(0x57ebf9).setBiomeName("Tundra")
			.doesNothingForMobSpawnerBase().func_4124_a(0xc4d339);
	public static final MobSpawnerBase hell = (new MobSpawnerHell()).setColor(0xff0000).setBiomeName("Hell");
	public String biomeName;
	public int color;
	public byte topBlock;
	public byte fillerBlock;
	public int field_6502_q;
	protected List<Function<World, EntityLiving>> biomeMonsters;
	protected List<Function<World, EntityLiving>> biomeCreatures;
	protected List<Function<World, EntityLiving>> biomeWaterCreatures;
	private static MobSpawnerBase biomeLookupTable[] = new MobSpawnerBase[4096];

	static {
		generateBiomeLookup();
	}
}
