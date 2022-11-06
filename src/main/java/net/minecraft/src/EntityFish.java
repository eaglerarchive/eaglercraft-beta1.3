package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import java.util.Random;

public class EntityFish extends Entity {

	public EntityFish(World world) {
		super(world);
		tileX = -1;
		tileY = -1;
		tileZ = -1;
		field_4092_g = 0;
		field_4091_h = false;
		field_4098_a = 0;
		field_4089_j = 0;
		field_4088_k = 0;
		field_4096_c = null;
		setSize(0.25F, 0.25F);
	}

	protected void entityInit() {
	}

	public boolean isInRangeToRenderDist(double d) {
		double d1 = boundingBox.getAverageEdgeLength() * 4D;
		d1 *= 64D;
		return d < d1 * d1;
	}

	public EntityFish(World world, double d, double d1, double d2) {
		this(world);
		setPosition(d, d1, d2);
	}

	public EntityFish(World world, EntityPlayer entityplayer) {
		super(world);
		tileX = -1;
		tileY = -1;
		tileZ = -1;
		field_4092_g = 0;
		field_4091_h = false;
		field_4098_a = 0;
		field_4089_j = 0;
		field_4088_k = 0;
		field_4096_c = null;
		angler = entityplayer;
		angler.fishEntity = this;
		setSize(0.25F, 0.25F);
		setLocationAndAngles(entityplayer.posX,
				(entityplayer.posY + 1.6200000000000001D) - (double) entityplayer.yOffset, entityplayer.posZ,
				entityplayer.rotationYaw, entityplayer.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		float f = 0.4F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F)
				* f;
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F)
				* f;
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
		func_4042_a(motionX, motionY, motionZ, 1.5F, 1.0F);
	}

	public void func_4042_a(double d, double d1, double d2, float f, float f1) {
		float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		d += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d1 += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d2 += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d *= f;
		d1 *= f;
		d2 *= f;
		motionX = d;
		motionY = d1;
		motionZ = d2;
		float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
		prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
		prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
		field_4090_i = 0;
	}

	public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i) {
		field_6387_m = d;
		field_6386_n = d1;
		field_6385_o = d2;
		field_6384_p = f;
		field_6383_q = f1;
		field_6388_l = i;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}

	public void setVelocity(double d, double d1, double d2) {
		velocityX = motionX = d;
		velocityY = motionY = d1;
		velocityZ = motionZ = d2;
	}

	public void onUpdate() {
		super.onUpdate();
		if (field_6388_l > 0) {
			double d = posX + (field_6387_m - posX) / (double) field_6388_l;
			double d1 = posY + (field_6386_n - posY) / (double) field_6388_l;
			double d2 = posZ + (field_6385_o - posZ) / (double) field_6388_l;
			double d4;
			for (d4 = field_6384_p - (double) rotationYaw; d4 < -180D; d4 += 360D) {
			}
			for (; d4 >= 180D; d4 -= 360D) {
			}
			rotationYaw += d4 / (double) field_6388_l;
			rotationPitch += (field_6383_q - (double) rotationPitch) / (double) field_6388_l;
			field_6388_l--;
			setPosition(d, d1, d2);
			setRotation(rotationYaw, rotationPitch);
			return;
		}
		if (!worldObj.multiplayerWorld) {
			ItemStack itemstack = angler.getCurrentEquippedItem();
			if (angler.isDead || !angler.isEntityAlive() || itemstack == null || itemstack.getItem() != Item.fishingRod
					|| getDistanceSqToEntity(angler) > 1024D) {
				setEntityDead();
				angler.fishEntity = null;
				return;
			}
			if (field_4096_c != null) {
				if (field_4096_c.isDead) {
					field_4096_c = null;
				} else {
					posX = field_4096_c.posX;
					posY = field_4096_c.boundingBox.minY + (double) field_4096_c.height * 0.80000000000000004D;
					posZ = field_4096_c.posZ;
					return;
				}
			}
		}
		if (field_4098_a > 0) {
			field_4098_a--;
		}
		if (field_4091_h) {
			int i = worldObj.getBlockId(tileX, tileY, tileZ);
			if (i != field_4092_g) {
				field_4091_h = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				field_4090_i = 0;
				field_4089_j = 0;
			} else {
				field_4090_i++;
				if (field_4090_i == 1200) {
					setEntityDead();
				}
				return;
			}
		} else {
			field_4089_j++;
		}
		Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
		Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
		vec3d = Vec3D.createVector(posX, posY, posZ);
		vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
		if (movingobjectposition != null) {
			vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord,
					movingobjectposition.hitVec.zCoord);
		}
		Entity entity = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
				boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		double d3 = 0.0D;
		for (int j = 0; j < list.size(); j++) {
			Entity entity1 = (Entity) list.get(j);
			if (!entity1.canBeCollidedWith() || entity1 == angler && field_4089_j < 5) {
				continue;
			}
			float f2 = 0.3F;
			AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
			MovingObjectPosition movingobjectposition1 = axisalignedbb.func_1169_a(vec3d, vec3d1);
			if (movingobjectposition1 == null) {
				continue;
			}
			double d6 = vec3d.distanceTo(movingobjectposition1.hitVec);
			if (d6 < d3 || d3 == 0.0D) {
				entity = entity1;
				d3 = d6;
			}
		}

		if (entity != null) {
			movingobjectposition = new MovingObjectPosition(entity);
		}
		if (movingobjectposition != null) {
			if (movingobjectposition.entityHit != null) {
				if (movingobjectposition.entityHit.attackEntityFrom(angler, 0)) {
					field_4096_c = movingobjectposition.entityHit;
				}
			} else {
				field_4091_h = true;
			}
		}
		if (field_4091_h) {
			return;
		}
		moveEntity(motionX, motionY, motionZ);
		float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
		for (rotationPitch = (float) ((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch
				- prevRotationPitch < -180F; prevRotationPitch -= 360F) {
		}
		for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {
		}
		for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {
		}
		for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {
		}
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		float f1 = 0.92F;
		if (onGround || isCollidedHorizontally) {
			f1 = 0.5F;
		}
		int k = 5;
		double d5 = 0.0D;
		for (int l = 0; l < k; l++) {
			double d8 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double) (l + 0)) / (double) k)
					- 0.125D) + 0.125D;
			double d9 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double) (l + 1)) / (double) k)
					- 0.125D) + 0.125D;
			AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBoxFromPool(boundingBox.minX, d8, boundingBox.minZ,
					boundingBox.maxX, d9, boundingBox.maxZ);
			if (worldObj.isAABBInMaterial(axisalignedbb1, Material.water)) {
				d5 += 1.0D / (double) k;
			}
		}

		if (d5 > 0.0D) {
			if (field_4088_k > 0) {
				field_4088_k--;
			} else if (rand.nextInt(500) == 0) {
				field_4088_k = rand.nextInt(30) + 10;
				motionY -= 0.20000000298023224D;
				worldObj.playSoundAtEntity(this, "random.splash", 0.25F,
						1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
				float f3 = MathHelper.floor_double(boundingBox.minY);
				for (int i1 = 0; (float) i1 < 1.0F + width * 20F; i1++) {
					float f4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
					float f6 = (rand.nextFloat() * 2.0F - 1.0F) * width;
					worldObj.spawnParticle("bubble", posX + (double) f4, f3 + 1.0F, posZ + (double) f6, motionX,
							motionY - (double) (rand.nextFloat() * 0.2F), motionZ);
				}

				for (int j1 = 0; (float) j1 < 1.0F + width * 20F; j1++) {
					float f5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
					float f7 = (rand.nextFloat() * 2.0F - 1.0F) * width;
					worldObj.spawnParticle("splash", posX + (double) f5, f3 + 1.0F, posZ + (double) f7, motionX,
							motionY, motionZ);
				}

			}
		}
		if (field_4088_k > 0) {
			motionY -= (double) (rand.nextFloat() * rand.nextFloat() * rand.nextFloat()) * 0.20000000000000001D;
		}
		double d7 = d5 * 2D - 1.0D;
		motionY += 0.039999999105930328D * d7;
		if (d5 > 0.0D) {
			f1 = (float) ((double) f1 * 0.90000000000000002D);
			motionY *= 0.80000000000000004D;
		}
		motionX *= f1;
		motionY *= f1;
		motionZ *= f1;
		setPosition(posX, posY, posZ);
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setShort("xTile", (short) tileX);
		nbttagcompound.setShort("yTile", (short) tileY);
		nbttagcompound.setShort("zTile", (short) tileZ);
		nbttagcompound.setByte("inTile", (byte) field_4092_g);
		nbttagcompound.setByte("shake", (byte) field_4098_a);
		nbttagcompound.setByte("inGround", (byte) (field_4091_h ? 1 : 0));
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		tileX = nbttagcompound.getShort("xTile");
		tileY = nbttagcompound.getShort("yTile");
		tileZ = nbttagcompound.getShort("zTile");
		field_4092_g = nbttagcompound.getByte("inTile") & 0xff;
		field_4098_a = nbttagcompound.getByte("shake") & 0xff;
		field_4091_h = nbttagcompound.getByte("inGround") == 1;
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public int catchFish() {
		byte byte0 = 0;
		if (field_4096_c != null) {
			double d = angler.posX - posX;
			double d2 = angler.posY - posY;
			double d4 = angler.posZ - posZ;
			double d6 = MathHelper.sqrt_double(d * d + d2 * d2 + d4 * d4);
			double d8 = 0.10000000000000001D;
			field_4096_c.motionX += d * d8;
			field_4096_c.motionY += d2 * d8 + (double) MathHelper.sqrt_double(d6) * 0.080000000000000002D;
			field_4096_c.motionZ += d4 * d8;
			byte0 = 3;
		} else if (field_4088_k > 0) {
			EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.fishRaw));
			double d1 = angler.posX - posX;
			double d3 = angler.posY - posY;
			double d5 = angler.posZ - posZ;
			double d7 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
			double d9 = 0.10000000000000001D;
			entityitem.motionX = d1 * d9;
			entityitem.motionY = d3 * d9 + (double) MathHelper.sqrt_double(d7) * 0.080000000000000002D;
			entityitem.motionZ = d5 * d9;
			worldObj.entityJoinedWorld(entityitem);
			byte0 = 1;
		}
		if (field_4091_h) {
			byte0 = 2;
		}
		setEntityDead();
		angler.fishEntity = null;
		return byte0;
	}

	private int tileX;
	private int tileY;
	private int tileZ;
	private int field_4092_g;
	private boolean field_4091_h;
	public int field_4098_a;
	public EntityPlayer angler;
	private int field_4090_i;
	private int field_4089_j;
	private int field_4088_k;
	public Entity field_4096_c;
	private int field_6388_l;
	private double field_6387_m;
	private double field_6386_n;
	private double field_6385_o;
	private double field_6384_p;
	private double field_6383_q;
	private double velocityX;
	private double velocityY;
	private double velocityZ;
}
