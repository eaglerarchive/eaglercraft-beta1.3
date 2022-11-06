package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class EntityCreature extends EntityLiving {

	public EntityCreature(World world) {
		super(world);
		hasAttacked = false;
	}

	protected void updatePlayerActionState() {
		hasAttacked = false;
		float f = 16F;
		if (playerToAttack == null) {
			playerToAttack = findPlayerToAttack();
			if (playerToAttack != null) {
				pathToEntity = worldObj.getPathToEntity(this, playerToAttack, f);
			}
		} else if (!playerToAttack.isEntityAlive()) {
			playerToAttack = null;
		} else {
			float f1 = playerToAttack.getDistanceToEntity(this);
			if (canEntityBeSeen(playerToAttack)) {
				attackEntity(playerToAttack, f1);
			}
		}
		if (!hasAttacked && playerToAttack != null && (pathToEntity == null || rand.nextInt(20) == 0)) {
			pathToEntity = worldObj.getPathToEntity(this, playerToAttack, f);
		} else if (pathToEntity == null && rand.nextInt(80) == 0 || rand.nextInt(80) == 0) {
			boolean flag = false;
			int j = -1;
			int k = -1;
			int l = -1;
			float f2 = -99999F;
			for (int i1 = 0; i1 < 10; i1++) {
				int j1 = MathHelper.floor_double((posX + (double) rand.nextInt(13)) - 6D);
				int k1 = MathHelper.floor_double((posY + (double) rand.nextInt(7)) - 3D);
				int l1 = MathHelper.floor_double((posZ + (double) rand.nextInt(13)) - 6D);
				float f3 = getBlockPathWeight(j1, k1, l1);
				if (f3 > f2) {
					f2 = f3;
					j = j1;
					k = k1;
					l = l1;
					flag = true;
				}
			}

			if (flag) {
				pathToEntity = worldObj.getEntityPathToXYZ(this, j, k, l, 10F);
			}
		}
		int i = MathHelper.floor_double(boundingBox.minY);
		boolean flag1 = handleWaterMovement();
		boolean flag2 = handleLavaMovement();
		rotationPitch = 0.0F;
		if (pathToEntity == null || rand.nextInt(100) == 0) {
			super.updatePlayerActionState();
			pathToEntity = null;
			return;
		}
		Vec3D vec3d = pathToEntity.getPosition(this);
		for (double d = width * 2.0F; vec3d != null && vec3d.squareDistanceTo(posX, vec3d.yCoord, posZ) < d * d;) {
			pathToEntity.incrementPathIndex();
			if (pathToEntity.isFinished()) {
				vec3d = null;
				pathToEntity = null;
			} else {
				vec3d = pathToEntity.getPosition(this);
			}
		}

		isJumping = false;
		if (vec3d != null) {
			double d1 = vec3d.xCoord - posX;
			double d2 = vec3d.zCoord - posZ;
			double d3 = vec3d.yCoord - (double) i;
			float f4 = (float) ((Math.atan2(d2, d1) * 180D) / 3.1415927410125732D) - 90F;
			float f5 = f4 - rotationYaw;
			moveForward = moveSpeed;
			for (; f5 < -180F; f5 += 360F) {
			}
			for (; f5 >= 180F; f5 -= 360F) {
			}
			if (f5 > 30F) {
				f5 = 30F;
			}
			if (f5 < -30F) {
				f5 = -30F;
			}
			rotationYaw += f5;
			if (hasAttacked && playerToAttack != null) {
				double d4 = playerToAttack.posX - posX;
				double d5 = playerToAttack.posZ - posZ;
				float f7 = rotationYaw;
				rotationYaw = (float) ((Math.atan2(d5, d4) * 180D) / 3.1415927410125732D) - 90F;
				float f6 = (((f7 - rotationYaw) + 90F) * 3.141593F) / 180F;
				moveStrafing = -MathHelper.sin(f6) * moveForward * 1.0F;
				moveForward = MathHelper.cos(f6) * moveForward * 1.0F;
			}
			if (d3 > 0.0D) {
				isJumping = true;
			}
		}
		if (playerToAttack != null) {
			faceEntity(playerToAttack, 30F);
		}
		if (isCollidedHorizontally) {
			isJumping = true;
		}
		if (rand.nextFloat() < 0.8F && (flag1 || flag2)) {
			isJumping = true;
		}
	}

	protected void attackEntity(Entity entity, float f) {
	}

	protected float getBlockPathWeight(int i, int j, int k) {
		return 0.0F;
	}

	protected Entity findPlayerToAttack() {
		return null;
	}

	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		return super.getCanSpawnHere() && getBlockPathWeight(i, j, k) >= 0.0F;
	}

	private PathEntity pathToEntity;
	protected Entity playerToAttack;
	protected boolean hasAttacked;
}
