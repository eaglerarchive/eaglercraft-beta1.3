package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;

import net.lax1dude.eaglercraft.EaglercraftRandom;

public class Block {

	protected Block(int i, Material material) {
		stepSound = soundPowderFootstep;
		blockParticleGravity = 1.0F;
		slipperiness = 0.6F;
		if (blocksList[i] != null) {
			throw new IllegalArgumentException(
					(new StringBuilder()).append("Slot ").append(i).append(" is already occupied by ")
							.append(blocksList[i]).append(" when adding ").append(this).toString());
		} else {
			blockMaterial = material;
			blocksList[i] = this;
			blockID = i;
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			opaqueCubeLookup[i] = isOpaqueCube();
			lightOpacity[i] = isOpaqueCube() ? 255 : 0;
			field_340_s[i] = !material.getCanBlockGrass();
			isBlockContainer[i] = false;
			return;
		}
	}

	protected Block(int i, int j, Material material) {
		this(i, material);
		blockIndexInTexture = j;
	}

	protected Block setStepSound(StepSound stepsound) {
		stepSound = stepsound;
		return this;
	}

	protected Block setLightOpacity(int i) {
		lightOpacity[blockID] = i;
		return this;
	}

	protected Block setLightValue(float f) {
		lightValue[blockID] = (int) (15F * f);
		return this;
	}

	protected Block setResistance(float f) {
		blockResistance = f * 3F;
		return this;
	}

	public boolean renderAsNormalBlock() {
		return true;
	}

	public int getRenderType() {
		return 0;
	}

	protected Block setHardness(float f) {
		blockHardness = f;
		if (blockResistance < f * 5F) {
			blockResistance = f * 5F;
		}
		return this;
	}

	protected void setTickOnLoad(boolean flag) {
		tickOnLoad[blockID] = flag;
	}

	public void setBlockBounds(float f, float f1, float f2, float f3, float f4, float f5) {
		minX = f;
		minY = f1;
		minZ = f2;
		maxX = f3;
		maxY = f4;
		maxZ = f5;
	}

	public float getBlockBrightness(IBlockAccess iblockaccess, int i, int j, int k) {
		return iblockaccess.getLightBrightness(i, j, k);
	}

	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 0 && minY > 0.0D) {
			return true;
		}
		if (l == 1 && maxY < 1.0D) {
			return true;
		}
		if (l == 2 && minZ > 0.0D) {
			return true;
		}
		if (l == 3 && maxZ < 1.0D) {
			return true;
		}
		if (l == 4 && minX > 0.0D) {
			return true;
		}
		if (l == 5 && maxX < 1.0D) {
			return true;
		} else {
			return !iblockaccess.isBlockOpaqueCube(i, j, k);
		}
	}

	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return getBlockTextureFromSideAndMetadata(l, iblockaccess.getBlockMetadata(i, j, k));
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		return getBlockTextureFromSide(i);
	}

	public int getBlockTextureFromSide(int i) {
		return blockIndexInTexture;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBoxFromPool((double) i + minX, (double) j + minY, (double) k + minZ,
				(double) i + maxX, (double) j + maxY, (double) k + maxZ);
	}

	public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB axisalignedbb,
			ArrayList arraylist) {
		AxisAlignedBB axisalignedbb1 = getCollisionBoundingBoxFromPool(world, i, j, k);
		if (axisalignedbb1 != null && axisalignedbb.intersectsWith(axisalignedbb1)) {
			arraylist.add(axisalignedbb1);
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBoxFromPool((double) i + minX, (double) j + minY, (double) k + minZ,
				(double) i + maxX, (double) j + maxY, (double) k + maxZ);
	}

	public boolean isOpaqueCube() {
		return true;
	}

	public boolean canCollideCheck(int i, boolean flag) {
		return isCollidable();
	}

	public boolean isCollidable() {
		return true;
	}

	public void updateTick(World world, int i, int j, int k, EaglercraftRandom random) {
	}

	public void randomDisplayTick(World world, int i, int j, int k, EaglercraftRandom random) {
	}

	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l) {
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
	}

	public int tickRate() {
		return 10;
	}

	public void onBlockAdded(World world, int i, int j, int k) {
	}

	public void onBlockRemoval(World world, int i, int j, int k) {
	}

	public int quantityDropped(EaglercraftRandom random) {
		return 1;
	}

	public int idDropped(int i, EaglercraftRandom random) {
		return blockID;
	}

	public float blockStrength(EntityPlayer entityplayer) {
		if (blockHardness < 0.0F) {
			return 0.0F;
		}
		if (!entityplayer.canHarvestBlock(this)) {
			return 1.0F / blockHardness / 100F;
		} else {
			return entityplayer.getCurrentPlayerStrVsBlock(this) / blockHardness / 30F;
		}
	}

	public void dropBlockAsItem(World world, int i, int j, int k, int l) {
		dropBlockAsItemWithChance(world, i, j, k, l, 1.0F);
	}

	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f) {
		if (world.multiplayerWorld) {
			return;
		}
		int i1 = quantityDropped(world.rand);
		for (int j1 = 0; j1 < i1; j1++) {
			if (world.rand.nextFloat() > f) {
				continue;
			}
			int k1 = idDropped(l, world.rand);
			if (k1 > 0) {
				float f1 = 0.7F;
				double d = (double) (world.rand.nextFloat() * f1) + (double) (1.0F - f1) * 0.5D;
				double d1 = (double) (world.rand.nextFloat() * f1) + (double) (1.0F - f1) * 0.5D;
				double d2 = (double) (world.rand.nextFloat() * f1) + (double) (1.0F - f1) * 0.5D;
				EntityItem entityitem = new EntityItem(world, (double) i + d, (double) j + d1, (double) k + d2,
						new ItemStack(k1, 1, damageDropped(l)));
				entityitem.delayBeforeCanPickup = 10;
				world.entityJoinedWorld(entityitem);
			}
		}

	}

	protected int damageDropped(int i) {
		return 0;
	}

	public float getExplosionResistance(Entity entity) {
		return blockResistance / 5F;
	}

	public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
		setBlockBoundsBasedOnState(world, i, j, k);
		vec3d = vec3d.addVector(-i, -j, -k);
		vec3d1 = vec3d1.addVector(-i, -j, -k);
		Vec3D vec3d2 = vec3d.getIntermediateWithXValue(vec3d1, minX);
		Vec3D vec3d3 = vec3d.getIntermediateWithXValue(vec3d1, maxX);
		Vec3D vec3d4 = vec3d.getIntermediateWithYValue(vec3d1, minY);
		Vec3D vec3d5 = vec3d.getIntermediateWithYValue(vec3d1, maxY);
		Vec3D vec3d6 = vec3d.getIntermediateWithZValue(vec3d1, minZ);
		Vec3D vec3d7 = vec3d.getIntermediateWithZValue(vec3d1, maxZ);
		if (!isVecInsideYZBounds(vec3d2)) {
			vec3d2 = null;
		}
		if (!isVecInsideYZBounds(vec3d3)) {
			vec3d3 = null;
		}
		if (!isVecInsideXZBounds(vec3d4)) {
			vec3d4 = null;
		}
		if (!isVecInsideXZBounds(vec3d5)) {
			vec3d5 = null;
		}
		if (!isVecInsideXYBounds(vec3d6)) {
			vec3d6 = null;
		}
		if (!isVecInsideXYBounds(vec3d7)) {
			vec3d7 = null;
		}
		Vec3D vec3d8 = null;
		if (vec3d2 != null && (vec3d8 == null || vec3d.distanceTo(vec3d2) < vec3d.distanceTo(vec3d8))) {
			vec3d8 = vec3d2;
		}
		if (vec3d3 != null && (vec3d8 == null || vec3d.distanceTo(vec3d3) < vec3d.distanceTo(vec3d8))) {
			vec3d8 = vec3d3;
		}
		if (vec3d4 != null && (vec3d8 == null || vec3d.distanceTo(vec3d4) < vec3d.distanceTo(vec3d8))) {
			vec3d8 = vec3d4;
		}
		if (vec3d5 != null && (vec3d8 == null || vec3d.distanceTo(vec3d5) < vec3d.distanceTo(vec3d8))) {
			vec3d8 = vec3d5;
		}
		if (vec3d6 != null && (vec3d8 == null || vec3d.distanceTo(vec3d6) < vec3d.distanceTo(vec3d8))) {
			vec3d8 = vec3d6;
		}
		if (vec3d7 != null && (vec3d8 == null || vec3d.distanceTo(vec3d7) < vec3d.distanceTo(vec3d8))) {
			vec3d8 = vec3d7;
		}
		if (vec3d8 == null) {
			return null;
		}
		byte byte0 = -1;
		if (vec3d8 == vec3d2) {
			byte0 = 4;
		}
		if (vec3d8 == vec3d3) {
			byte0 = 5;
		}
		if (vec3d8 == vec3d4) {
			byte0 = 0;
		}
		if (vec3d8 == vec3d5) {
			byte0 = 1;
		}
		if (vec3d8 == vec3d6) {
			byte0 = 2;
		}
		if (vec3d8 == vec3d7) {
			byte0 = 3;
		}
		return new MovingObjectPosition(i, j, k, byte0, vec3d8.addVector(i, j, k));
	}

	private boolean isVecInsideYZBounds(Vec3D vec3d) {
		if (vec3d == null) {
			return false;
		} else {
			return vec3d.yCoord >= minY && vec3d.yCoord <= maxY && vec3d.zCoord >= minZ && vec3d.zCoord <= maxZ;
		}
	}

	private boolean isVecInsideXZBounds(Vec3D vec3d) {
		if (vec3d == null) {
			return false;
		} else {
			return vec3d.xCoord >= minX && vec3d.xCoord <= maxX && vec3d.zCoord >= minZ && vec3d.zCoord <= maxZ;
		}
	}

	private boolean isVecInsideXYBounds(Vec3D vec3d) {
		if (vec3d == null) {
			return false;
		} else {
			return vec3d.xCoord >= minX && vec3d.xCoord <= maxX && vec3d.yCoord >= minY && vec3d.yCoord <= maxY;
		}
	}

	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) {
	}

	public int getRenderBlockPass() {
		return 0;
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		int l = world.getBlockId(i, j, k);
		return l == 0 || blocksList[l].blockMaterial.getIsLiquid();
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return false;
	}

	public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
	}

	public void onBlockPlaced(World world, int i, int j, int k, int l) {
	}

	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
	}

	public void velocityToAddToEntity(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
	}

	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
		return 0xffffff;
	}

	public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return false;
	}

	public boolean canProvidePower() {
		return false;
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
	}

	public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
		return false;
	}

	public void func_237_e() {
	}

	public void harvestBlock(World world, int i, int j, int k, int l) {
		dropBlockAsItem(world, i, j, k, l);
	}

	public boolean canBlockStay(World world, int i, int j, int k) {
		return true;
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
	}

	public Block setBlockName(String s) {
		blockName = (new StringBuilder()).append("tile.").append(s).toString();
		return this;
	}

	public String getBlockName() {
		return blockName;
	}

	public void playBlock(World world, int i, int j, int k, int l, int i1) {
	}

	static Class _mthclass$(String s) {
		try {
			return Class.forName(s);
		} catch (ClassNotFoundException classnotfoundexception) {
			throw new NoClassDefFoundError(classnotfoundexception.getMessage());
		}
	}

	public static final StepSound soundPowderFootstep;
	public static final StepSound soundWoodFootstep;
	public static final StepSound soundGravelFootstep;
	public static final StepSound soundGrassFootstep;
	public static final StepSound soundStoneFootstep;
	public static final StepSound soundMetalFootstep;
	public static final StepSound soundGlassFootstep;
	public static final StepSound soundClothFootstep;
	public static final StepSound soundSandFootstep;
	public static final Block blocksList[];
	public static final boolean tickOnLoad[] = new boolean[256];
	public static final boolean opaqueCubeLookup[] = new boolean[256];
	public static final boolean isBlockContainer[] = new boolean[256];
	public static final int lightOpacity[] = new int[256];
	public static final boolean field_340_s[];
	public static final int lightValue[] = new int[256];
	public static final Block stone;
	public static final BlockGrass grass;
	public static final Block dirt;
	public static final Block cobblestone;
	public static final Block planks;
	public static final Block sapling;
	public static final Block bedrock;
	public static final Block waterStill;
	public static final Block waterMoving;
	public static final Block lavaStill;
	public static final Block lavaMoving;
	public static final Block sand;
	public static final Block gravel;
	public static final Block oreGold;
	public static final Block oreIron;
	public static final Block oreCoal;
	public static final Block wood;
	public static final BlockLeaves leaves;
	public static final Block sponge;
	public static final Block glass;
	public static final Block oreLapis;
	public static final Block blockLapis;
	public static final Block dispenser;
	public static final Block sandStone;
	public static final Block musicBlock;
	public static final Block field_9262_S;
	public static final Block field_9261_T = null;
	public static final Block field_9260_U = null;
	public static final Block field_9259_V = null;
	public static final Block field_9258_W = null;
	public static final Block field_9257_X = null;
	public static final Block field_9256_Y = null;
	public static final Block field_9255_Z = null;
	public static final Block field_9269_aa = null;
	public static final Block cloth;
	public static final Block field_9268_ac = null;
	public static final BlockFlower plantYellow;
	public static final BlockFlower plantRed;
	public static final BlockFlower mushroomBrown;
	public static final BlockFlower mushroomRed;
	public static final Block blockGold;
	public static final Block blockSteel;
	public static final Block stairDouble;
	public static final Block stairSingle;
	public static final Block brick;
	public static final Block tnt;
	public static final Block bookShelf;
	public static final Block cobblestoneMossy;
	public static final Block obsidian;
	public static final Block torchWood;
	public static final BlockFire fire;
	public static final Block mobSpawner;
	public static final Block stairCompactPlanks;
	public static final Block crate;
	public static final Block redstoneWire;
	public static final Block oreDiamond;
	public static final Block blockDiamond;
	public static final Block workbench;
	public static final Block crops;
	public static final Block tilledField;
	public static final Block stoneOvenIdle;
	public static final Block stoneOvenActive;
	public static final Block signPost;
	public static final Block doorWood;
	public static final Block ladder;
	public static final Block minecartTrack;
	public static final Block stairCompactCobblestone;
	public static final Block signWall;
	public static final Block lever;
	public static final Block pressurePlateStone;
	public static final Block doorSteel;
	public static final Block pressurePlatePlanks;
	public static final Block oreRedstone;
	public static final Block oreRedstoneGlowing;
	public static final Block torchRedstoneIdle;
	public static final Block torchRedstoneActive;
	public static final Block button;
	public static final Block snow;
	public static final Block ice;
	public static final Block blockSnow;
	public static final Block cactus;
	public static final Block blockClay;
	public static final Block reed;
	public static final Block jukebox;
	public static final Block fence;
	public static final Block pumpkin;
	public static final Block bloodStone;
	public static final Block slowSand;
	public static final Block lightStone;
	public static final BlockPortal portal;
	public static final Block pumpkinLantern;
	public static final Block cake;
	public static final Block field_22021_bh;
	public static final Block field_22020_bi;
	public int blockIndexInTexture;
	public final int blockID;
	protected float blockHardness;
	protected float blockResistance;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	public StepSound stepSound;
	public float blockParticleGravity;
	public final Material blockMaterial;
	public float slipperiness;
	private String blockName;

	static {
		soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
		soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
		soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
		soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
		soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
		soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
		soundGlassFootstep = new StepSoundStone("stone", 1.0F, 1.0F);
		soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
		soundSandFootstep = new StepSoundSand("sand", 1.0F, 1.0F);
		blocksList = new Block[256];
		field_340_s = new boolean[256];
		stone = (new BlockStone(1, 1)).setHardness(1.5F).setResistance(10F).setStepSound(soundStoneFootstep)
				.setBlockName("stone");
		grass = (BlockGrass) (new BlockGrass(2)).setHardness(0.6F).setStepSound(soundGrassFootstep)
				.setBlockName("grass");
		dirt = (new BlockDirt(3, 2)).setHardness(0.5F).setStepSound(soundGravelFootstep).setBlockName("dirt");
		cobblestone = (new Block(4, 16, Material.rock)).setHardness(2.0F).setResistance(10F)
				.setStepSound(soundStoneFootstep).setBlockName("stonebrick");
		planks = (new Block(5, 4, Material.wood)).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep)
				.setBlockName("wood");
		sapling = (new BlockSapling(6, 15)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("sapling");
		bedrock = (new Block(7, 17, Material.rock)).setHardness(-1F).setResistance(6000000F)
				.setStepSound(soundStoneFootstep).setBlockName("bedrock");
		waterStill = (new BlockFlowing(8, Material.water)).setHardness(100F).setLightOpacity(3).setBlockName("water");
		waterMoving = (new BlockStationary(9, Material.water)).setHardness(100F).setLightOpacity(3)
				.setBlockName("water");
		lavaStill = (new BlockFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255)
				.setBlockName("lava");
		lavaMoving = (new BlockStationary(11, Material.lava)).setHardness(100F).setLightValue(1.0F).setLightOpacity(255)
				.setBlockName("lava");
		sand = (new BlockSand(12, 18)).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("sand");
		gravel = (new BlockGravel(13, 19)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("gravel");
		oreGold = (new BlockOre(14, 32)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep)
				.setBlockName("oreGold");
		oreIron = (new BlockOre(15, 33)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep)
				.setBlockName("oreIron");
		oreCoal = (new BlockOre(16, 34)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep)
				.setBlockName("oreCoal");
		wood = (new BlockLog(17)).setHardness(2.0F).setStepSound(soundWoodFootstep).setBlockName("log");
		leaves = (BlockLeaves) (new BlockLeaves(18, 52)).setHardness(0.2F).setLightOpacity(1)
				.setStepSound(soundGrassFootstep).setBlockName("leaves");
		sponge = (new BlockSponge(19)).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("sponge");
		glass = (new BlockGlass(20, 49, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep)
				.setBlockName("glass");
		oreLapis = (new BlockOre(21, 160)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep)
				.setBlockName("oreLapis");
		blockLapis = (new Block(22, 144, Material.rock)).setHardness(3F).setResistance(5F)
				.setStepSound(soundStoneFootstep).setBlockName("blockLapis");
		dispenser = (new BlockDispenser(23)).setHardness(3.5F).setStepSound(soundStoneFootstep)
				.setBlockName("dispenser");
		sandStone = (new BlockSandStone(24)).setStepSound(soundStoneFootstep).setHardness(0.8F)
				.setBlockName("sandStone");
		musicBlock = (new BlockNote(25)).setHardness(0.8F).setBlockName("musicBlock");
		field_9262_S = (new BlockBed(26)).setHardness(0.2F).setBlockName("bed");
		cloth = (new BlockCloth()).setHardness(0.8F).setStepSound(soundClothFootstep).setBlockName("cloth");
		plantYellow = (BlockFlower) (new BlockFlower(37, 13)).setHardness(0.0F).setStepSound(soundGrassFootstep)
				.setBlockName("flower");
		plantRed = (BlockFlower) (new BlockFlower(38, 12)).setHardness(0.0F).setStepSound(soundGrassFootstep)
				.setBlockName("rose");
		mushroomBrown = (BlockFlower) (new BlockMushroom(39, 29)).setHardness(0.0F).setStepSound(soundGrassFootstep)
				.setLightValue(0.125F).setBlockName("mushroom");
		mushroomRed = (BlockFlower) (new BlockMushroom(40, 28)).setHardness(0.0F).setStepSound(soundGrassFootstep)
				.setBlockName("mushroom");
		blockGold = (new BlockOreBlock(41, 23)).setHardness(3F).setResistance(10F).setStepSound(soundMetalFootstep)
				.setBlockName("blockGold");
		blockSteel = (new BlockOreBlock(42, 22)).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep)
				.setBlockName("blockIron");
		stairDouble = (new BlockStep(43, true)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep)
				.setBlockName("stoneSlab");
		stairSingle = (new BlockStep(44, false)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep)
				.setBlockName("stoneSlab");
		brick = (new Block(45, 7, Material.rock)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep)
				.setBlockName("brick");
		tnt = (new BlockTNT(46, 8)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tnt");
		bookShelf = (new BlockBookshelf(47, 35)).setHardness(1.5F).setStepSound(soundWoodFootstep)
				.setBlockName("bookshelf");
		cobblestoneMossy = (new Block(48, 36, Material.rock)).setHardness(2.0F).setResistance(10F)
				.setStepSound(soundStoneFootstep).setBlockName("stoneMoss");
		obsidian = (new BlockObsidian(49, 37)).setHardness(10F).setResistance(2000F).setStepSound(soundStoneFootstep)
				.setBlockName("obsidian");
		torchWood = (new BlockTorch(50, 80)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundWoodFootstep)
				.setBlockName("torch");
		fire = (BlockFire) (new BlockFire(51, 31)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep)
				.setBlockName("fire");
		mobSpawner = (new BlockMobSpawner(52, 65)).setHardness(5F).setStepSound(soundMetalFootstep)
				.setBlockName("mobSpawner");
		stairCompactPlanks = (new BlockStairs(53, planks)).setBlockName("stairsWood");
		crate = (new BlockChest(54)).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("chest");
		redstoneWire = (new BlockRedstoneWire(55, 164)).setHardness(0.0F).setStepSound(soundPowderFootstep)
				.setBlockName("redstoneDust");
		oreDiamond = (new BlockOre(56, 50)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep)
				.setBlockName("oreDiamond");
		blockDiamond = (new BlockOreBlock(57, 24)).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep)
				.setBlockName("blockDiamond");
		workbench = (new BlockWorkbench(58)).setHardness(2.5F).setStepSound(soundWoodFootstep)
				.setBlockName("workbench");
		crops = (new BlockCrops(59, 88)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("crops");
		tilledField = (new BlockSoil(60)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("farmland");
		stoneOvenIdle = (new BlockFurnace(61, false)).setHardness(3.5F).setStepSound(soundStoneFootstep)
				.setBlockName("furnace");
		stoneOvenActive = (new BlockFurnace(62, true)).setHardness(3.5F).setStepSound(soundStoneFootstep)
				.setLightValue(0.875F).setBlockName("furnace");
		signPost = (new BlockSign(63, TileEntitySign.class, true)).setHardness(1.0F).setStepSound(soundWoodFootstep)
				.setBlockName("sign");
		doorWood = (new BlockDoor(64, Material.wood)).setHardness(3F).setStepSound(soundWoodFootstep)
				.setBlockName("doorWood");
		ladder = (new BlockLadder(65, 83)).setHardness(0.4F).setStepSound(soundWoodFootstep).setBlockName("ladder");
		minecartTrack = (new BlockMinecartTrack(66, 128)).setHardness(0.7F).setStepSound(soundMetalFootstep)
				.setBlockName("rail");
		stairCompactCobblestone = (new BlockStairs(67, cobblestone)).setBlockName("stairsStone");
		signWall = (new BlockSign(68, TileEntitySign.class, false)).setHardness(1.0F).setStepSound(soundWoodFootstep)
				.setBlockName("sign");
		lever = (new BlockLever(69, 96)).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("lever");
		pressurePlateStone = (new BlockPressurePlate(70, stone.blockIndexInTexture, EnumMobType.mobs)).setHardness(0.5F)
				.setStepSound(soundStoneFootstep).setBlockName("pressurePlate");
		doorSteel = (new BlockDoor(71, Material.iron)).setHardness(5F).setStepSound(soundMetalFootstep)
				.setBlockName("doorIron");
		pressurePlatePlanks = (new BlockPressurePlate(72, planks.blockIndexInTexture, EnumMobType.everything))
				.setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("pressurePlate");
		oreRedstone = (new BlockRedstoneOre(73, 51, false)).setHardness(3F).setResistance(5F)
				.setStepSound(soundStoneFootstep).setBlockName("oreRedstone");
		oreRedstoneGlowing = (new BlockRedstoneOre(74, 51, true)).setLightValue(0.625F).setHardness(3F)
				.setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone");
		torchRedstoneIdle = (new BlockRedstoneTorch(75, 115, false)).setHardness(0.0F).setStepSound(soundWoodFootstep)
				.setBlockName("notGate");
		torchRedstoneActive = (new BlockRedstoneTorch(76, 99, true)).setHardness(0.0F).setLightValue(0.5F)
				.setStepSound(soundWoodFootstep).setBlockName("notGate");
		button = (new BlockButton(77, stone.blockIndexInTexture)).setHardness(0.5F).setStepSound(soundStoneFootstep)
				.setBlockName("button");
		snow = (new BlockSnow(78, 66)).setHardness(0.1F).setStepSound(soundClothFootstep).setBlockName("snow");
		ice = (new BlockIce(79, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep)
				.setBlockName("ice");
		blockSnow = (new BlockSnowBlock(80, 66)).setHardness(0.2F).setStepSound(soundClothFootstep)
				.setBlockName("snow");
		cactus = (new BlockCactus(81, 70)).setHardness(0.4F).setStepSound(soundClothFootstep).setBlockName("cactus");
		blockClay = (new BlockClay(82, 72)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("clay");
		reed = (new BlockReed(83, 73)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("reeds");
		jukebox = (new BlockJukeBox(84, 74)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep)
				.setBlockName("jukebox");
		fence = (new BlockFence(85, 4)).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep)
				.setBlockName("fence");
		pumpkin = (new BlockPumpkin(86, 102, false)).setHardness(1.0F).setStepSound(soundWoodFootstep)
				.setBlockName("pumpkin");
		bloodStone = (new BlockBloodStone(87, 103)).setHardness(0.4F).setStepSound(soundStoneFootstep)
				.setBlockName("hellrock");
		slowSand = (new BlockSlowSand(88, 104)).setHardness(0.5F).setStepSound(soundSandFootstep)
				.setBlockName("hellsand");
		lightStone = (new BlockLightStone(89, 105, Material.glass)).setHardness(0.3F).setStepSound(soundGlassFootstep)
				.setLightValue(1.0F).setBlockName("lightgem");
		portal = (BlockPortal) (new BlockPortal(90, 14)).setHardness(-1F).setStepSound(soundGlassFootstep)
				.setLightValue(0.75F).setBlockName("portal");
		pumpkinLantern = (new BlockPumpkin(91, 102, true)).setHardness(1.0F).setStepSound(soundWoodFootstep)
				.setLightValue(1.0F).setBlockName("litpumpkin");
		cake = (new BlockCake(92, 121)).setHardness(0.5F).setStepSound(soundClothFootstep).setBlockName("cake");
		field_22021_bh = (new BlockRedstoneRepeater(93, false)).setHardness(0.0F).setStepSound(soundWoodFootstep)
				.setBlockName("diode");
		field_22020_bi = (new BlockRedstoneRepeater(94, true)).setHardness(0.0F).setLightValue(0.625F)
				.setStepSound(soundWoodFootstep).setBlockName("diode");
		Item.itemsList[cloth.blockID] = (new ItemCloth(cloth.blockID - 256)).setItemName("cloth");
		Item.itemsList[wood.blockID] = (new ItemLog(wood.blockID - 256)).setItemName("log");
		Item.itemsList[stairSingle.blockID] = (new ItemSlab(stairSingle.blockID - 256)).setItemName("stoneSlab");
		for (int i = 0; i < 256; i++) {
			if (blocksList[i] != null && Item.itemsList[i] == null) {
				Item.itemsList[i] = new ItemBlock(i - 256);
			}
		}

		field_340_s[0] = true;
	}
}
