package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;

class MinecartTrackLogic {

	public MinecartTrackLogic(BlockMinecartTrack blockminecarttrack, World world, int i, int j, int k) {
		minecartTrack = blockminecarttrack;
		connectedTracks = new ArrayList();
		worldObj = world;
		trackX = i;
		trackY = j;
		trackZ = k;
		trackMetadata = world.getBlockMetadata(i, j, k);
		calculateConnectedTracks();
	}

	private void calculateConnectedTracks() {
		connectedTracks.clear();
		if (trackMetadata == 0) {
			connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ - 1));
			connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ + 1));
		} else if (trackMetadata == 1) {
			connectedTracks.add(new ChunkPosition(trackX - 1, trackY, trackZ));
			connectedTracks.add(new ChunkPosition(trackX + 1, trackY, trackZ));
		} else if (trackMetadata == 2) {
			connectedTracks.add(new ChunkPosition(trackX - 1, trackY, trackZ));
			connectedTracks.add(new ChunkPosition(trackX + 1, trackY + 1, trackZ));
		} else if (trackMetadata == 3) {
			connectedTracks.add(new ChunkPosition(trackX - 1, trackY + 1, trackZ));
			connectedTracks.add(new ChunkPosition(trackX + 1, trackY, trackZ));
		} else if (trackMetadata == 4) {
			connectedTracks.add(new ChunkPosition(trackX, trackY + 1, trackZ - 1));
			connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ + 1));
		} else if (trackMetadata == 5) {
			connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ - 1));
			connectedTracks.add(new ChunkPosition(trackX, trackY + 1, trackZ + 1));
		} else if (trackMetadata == 6) {
			connectedTracks.add(new ChunkPosition(trackX + 1, trackY, trackZ));
			connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ + 1));
		} else if (trackMetadata == 7) {
			connectedTracks.add(new ChunkPosition(trackX - 1, trackY, trackZ));
			connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ + 1));
		} else if (trackMetadata == 8) {
			connectedTracks.add(new ChunkPosition(trackX - 1, trackY, trackZ));
			connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ - 1));
		} else if (trackMetadata == 9) {
			connectedTracks.add(new ChunkPosition(trackX + 1, trackY, trackZ));
			connectedTracks.add(new ChunkPosition(trackX, trackY, trackZ - 1));
		}
	}

	private void func_785_b() {
		for (int i = 0; i < connectedTracks.size(); i++) {
			MinecartTrackLogic minecarttracklogic = getMinecartTrackLogic((ChunkPosition) connectedTracks.get(i));
			if (minecarttracklogic == null || !minecarttracklogic.isConnectedTo(this)) {
				connectedTracks.remove(i--);
			} else {
				connectedTracks.set(i, new ChunkPosition(minecarttracklogic.trackX, minecarttracklogic.trackY,
						minecarttracklogic.trackZ));
			}
		}

	}

	private boolean isMinecartTrack(int i, int j, int k) {
		if (worldObj.getBlockId(i, j, k) == minecartTrack.blockID) {
			return true;
		}
		if (worldObj.getBlockId(i, j + 1, k) == minecartTrack.blockID) {
			return true;
		}
		return worldObj.getBlockId(i, j - 1, k) == minecartTrack.blockID;
	}

	private MinecartTrackLogic getMinecartTrackLogic(ChunkPosition chunkposition) {
		if (worldObj.getBlockId(chunkposition.x, chunkposition.y, chunkposition.z) == minecartTrack.blockID) {
			return new MinecartTrackLogic(minecartTrack, worldObj, chunkposition.x, chunkposition.y, chunkposition.z);
		}
		if (worldObj.getBlockId(chunkposition.x, chunkposition.y + 1, chunkposition.z) == minecartTrack.blockID) {
			return new MinecartTrackLogic(minecartTrack, worldObj, chunkposition.x, chunkposition.y + 1,
					chunkposition.z);
		}
		if (worldObj.getBlockId(chunkposition.x, chunkposition.y - 1, chunkposition.z) == minecartTrack.blockID) {
			return new MinecartTrackLogic(minecartTrack, worldObj, chunkposition.x, chunkposition.y - 1,
					chunkposition.z);
		} else {
			return null;
		}
	}

	private boolean isConnectedTo(MinecartTrackLogic minecarttracklogic) {
		for (int i = 0; i < connectedTracks.size(); i++) {
			ChunkPosition chunkposition = (ChunkPosition) connectedTracks.get(i);
			if (chunkposition.x == minecarttracklogic.trackX && chunkposition.z == minecarttracklogic.trackZ) {
				return true;
			}
		}

		return false;
	}

	private boolean func_794_b(int i, int j, int k) {
		for (int l = 0; l < connectedTracks.size(); l++) {
			ChunkPosition chunkposition = (ChunkPosition) connectedTracks.get(l);
			if (chunkposition.x == i && chunkposition.z == k) {
				return true;
			}
		}

		return false;
	}

	private int getAdjacentTracks() {
		int i = 0;
		if (isMinecartTrack(trackX, trackY, trackZ - 1)) {
			i++;
		}
		if (isMinecartTrack(trackX, trackY, trackZ + 1)) {
			i++;
		}
		if (isMinecartTrack(trackX - 1, trackY, trackZ)) {
			i++;
		}
		if (isMinecartTrack(trackX + 1, trackY, trackZ)) {
			i++;
		}
		return i;
	}

	private boolean handleKeyPress(MinecartTrackLogic minecarttracklogic) {
		if (isConnectedTo(minecarttracklogic)) {
			return true;
		}
		if (connectedTracks.size() == 2) {
			return false;
		}
		if (connectedTracks.size() == 0) {
			return true;
		}
		ChunkPosition chunkposition = (ChunkPosition) connectedTracks.get(0);
		return minecarttracklogic.trackY != trackY || chunkposition.y != trackY ? true : true;
	}

	private void func_788_d(MinecartTrackLogic minecarttracklogic) {
		connectedTracks.add(
				new ChunkPosition(minecarttracklogic.trackX, minecarttracklogic.trackY, minecarttracklogic.trackZ));
		boolean flag = func_794_b(trackX, trackY, trackZ - 1);
		boolean flag1 = func_794_b(trackX, trackY, trackZ + 1);
		boolean flag2 = func_794_b(trackX - 1, trackY, trackZ);
		boolean flag3 = func_794_b(trackX + 1, trackY, trackZ);
		byte byte0 = -1;
		if (flag || flag1) {
			byte0 = 0;
		}
		if (flag2 || flag3) {
			byte0 = 1;
		}
		if (flag1 && flag3 && !flag && !flag2) {
			byte0 = 6;
		}
		if (flag1 && flag2 && !flag && !flag3) {
			byte0 = 7;
		}
		if (flag && flag2 && !flag1 && !flag3) {
			byte0 = 8;
		}
		if (flag && flag3 && !flag1 && !flag2) {
			byte0 = 9;
		}
		if (byte0 == 0) {
			if (worldObj.getBlockId(trackX, trackY + 1, trackZ - 1) == minecartTrack.blockID) {
				byte0 = 4;
			}
			if (worldObj.getBlockId(trackX, trackY + 1, trackZ + 1) == minecartTrack.blockID) {
				byte0 = 5;
			}
		}
		if (byte0 == 1) {
			if (worldObj.getBlockId(trackX + 1, trackY + 1, trackZ) == minecartTrack.blockID) {
				byte0 = 2;
			}
			if (worldObj.getBlockId(trackX - 1, trackY + 1, trackZ) == minecartTrack.blockID) {
				byte0 = 3;
			}
		}
		if (byte0 < 0) {
			byte0 = 0;
		}
		worldObj.setBlockMetadataWithNotify(trackX, trackY, trackZ, byte0);
	}

	private boolean func_786_c(int i, int j, int k) {
		MinecartTrackLogic minecarttracklogic = getMinecartTrackLogic(new ChunkPosition(i, j, k));
		if (minecarttracklogic == null) {
			return false;
		} else {
			minecarttracklogic.func_785_b();
			return minecarttracklogic.handleKeyPress(this);
		}
	}

	public void func_792_a(boolean flag) {
		boolean flag1 = func_786_c(trackX, trackY, trackZ - 1);
		boolean flag2 = func_786_c(trackX, trackY, trackZ + 1);
		boolean flag3 = func_786_c(trackX - 1, trackY, trackZ);
		boolean flag4 = func_786_c(trackX + 1, trackY, trackZ);
		int i = -1;
		if ((flag1 || flag2) && !flag3 && !flag4) {
			i = 0;
		}
		if ((flag3 || flag4) && !flag1 && !flag2) {
			i = 1;
		}
		if (flag2 && flag4 && !flag1 && !flag3) {
			i = 6;
		}
		if (flag2 && flag3 && !flag1 && !flag4) {
			i = 7;
		}
		if (flag1 && flag3 && !flag2 && !flag4) {
			i = 8;
		}
		if (flag1 && flag4 && !flag2 && !flag3) {
			i = 9;
		}
		if (i == -1) {
			if (flag1 || flag2) {
				i = 0;
			}
			if (flag3 || flag4) {
				i = 1;
			}
			if (flag) {
				if (flag2 && flag4) {
					i = 6;
				}
				if (flag3 && flag2) {
					i = 7;
				}
				if (flag4 && flag1) {
					i = 9;
				}
				if (flag1 && flag3) {
					i = 8;
				}
			} else {
				if (flag1 && flag3) {
					i = 8;
				}
				if (flag4 && flag1) {
					i = 9;
				}
				if (flag3 && flag2) {
					i = 7;
				}
				if (flag2 && flag4) {
					i = 6;
				}
			}
		}
		if (i == 0) {
			if (worldObj.getBlockId(trackX, trackY + 1, trackZ - 1) == minecartTrack.blockID) {
				i = 4;
			}
			if (worldObj.getBlockId(trackX, trackY + 1, trackZ + 1) == minecartTrack.blockID) {
				i = 5;
			}
		}
		if (i == 1) {
			if (worldObj.getBlockId(trackX + 1, trackY + 1, trackZ) == minecartTrack.blockID) {
				i = 2;
			}
			if (worldObj.getBlockId(trackX - 1, trackY + 1, trackZ) == minecartTrack.blockID) {
				i = 3;
			}
		}
		if (i < 0) {
			i = 0;
		}
		trackMetadata = i;
		calculateConnectedTracks();
		worldObj.setBlockMetadataWithNotify(trackX, trackY, trackZ, i);
		for (int j = 0; j < connectedTracks.size(); j++) {
			MinecartTrackLogic minecarttracklogic = getMinecartTrackLogic((ChunkPosition) connectedTracks.get(j));
			if (minecarttracklogic == null) {
				continue;
			}
			minecarttracklogic.func_785_b();
			if (minecarttracklogic.handleKeyPress(this)) {
				minecarttracklogic.func_788_d(this);
			}
		}

	}

	static int getNAdjacentTracks(MinecartTrackLogic minecarttracklogic) {
		return minecarttracklogic.getAdjacentTracks();
	}

	private World worldObj;
	private int trackX;
	private int trackY;
	private int trackZ;
	private int trackMetadata;
	private List connectedTracks;
	final BlockMinecartTrack minecartTrack; /* synthetic field */
}
