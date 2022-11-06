package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class NetHandler {

	public NetHandler() {
	}

	public void handleMapChunk(Packet51MapChunk packet51mapchunk) {
	}

	public void registerPacket(Packet packet) {
	}

	public void handleErrorMessage(String s, Object aobj[]) {
	}

	public void handleKickDisconnect(Packet255KickDisconnect packet255kickdisconnect) {
		registerPacket(packet255kickdisconnect);
	}

	public void handleLogin(Packet1Login packet1login) {
		registerPacket(packet1login);
	}

	public void handleFlying(Packet10Flying packet10flying) {
		registerPacket(packet10flying);
	}

	public void handleMultiBlockChange(Packet52MultiBlockChange packet52multiblockchange) {
		registerPacket(packet52multiblockchange);
	}

	public void handleBlockDig(Packet14BlockDig packet14blockdig) {
		registerPacket(packet14blockdig);
	}

	public void handleBlockChange(Packet53BlockChange packet53blockchange) {
		registerPacket(packet53blockchange);
	}

	public void handlePreChunk(Packet50PreChunk packet50prechunk) {
		registerPacket(packet50prechunk);
	}

	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20namedentityspawn) {
		registerPacket(packet20namedentityspawn);
	}

	public void handleEntity(Packet30Entity packet30entity) {
		registerPacket(packet30entity);
	}

	public void handleEntityTeleport(Packet34EntityTeleport packet34entityteleport) {
		registerPacket(packet34entityteleport);
	}

	public void handlePlace(Packet15Place packet15place) {
		registerPacket(packet15place);
	}

	public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16blockitemswitch) {
		registerPacket(packet16blockitemswitch);
	}

	public void handleDestroyEntity(Packet29DestroyEntity packet29destroyentity) {
		registerPacket(packet29destroyentity);
	}

	public void handlePickupSpawn(Packet21PickupSpawn packet21pickupspawn) {
		registerPacket(packet21pickupspawn);
	}

	public void handleCollect(Packet22Collect packet22collect) {
		registerPacket(packet22collect);
	}

	public void handleChat(Packet3Chat packet3chat) {
		registerPacket(packet3chat);
	}

	public void handleVehicleSpawn(Packet23VehicleSpawn packet23vehiclespawn) {
		registerPacket(packet23vehiclespawn);
	}

	public void handleArmAnimation(Packet18ArmAnimation packet18armanimation) {
		registerPacket(packet18armanimation);
	}

	public void func_21147_a(Packet19 packet19) {
		registerPacket(packet19);
	}

	public void handleHandshake(Packet2Handshake packet2handshake) {
		registerPacket(packet2handshake);
	}

	public void handleMobSpawn(Packet24MobSpawn packet24mobspawn) {
		registerPacket(packet24mobspawn);
	}

	public void handleUpdateTime(Packet4UpdateTime packet4updatetime) {
		registerPacket(packet4updatetime);
	}

	public void handleSpawnPosition(Packet6SpawnPosition packet6spawnposition) {
		registerPacket(packet6spawnposition);
	}

	public void func_6498_a(Packet28 packet28) {
		registerPacket(packet28);
	}

	public void func_21148_a(Packet40 packet40) {
		registerPacket(packet40);
	}

	public void func_6497_a(Packet39 packet39) {
		registerPacket(packet39);
	}

	public void func_6499_a(Packet7 packet7) {
		registerPacket(packet7);
	}

	public void func_9447_a(Packet38 packet38) {
		registerPacket(packet38);
	}

	public void handleHealth(Packet8 packet8) {
		registerPacket(packet8);
	}

	public void func_9448_a(Packet9 packet9) {
		registerPacket(packet9);
	}

	public void func_12245_a(Packet60 packet60) {
		registerPacket(packet60);
	}

	public void func_20087_a(Packet100 packet100) {
		registerPacket(packet100);
	}

	public void func_20092_a(Packet101 packet101) {
		registerPacket(packet101);
	}

	public void func_20091_a(Packet102 packet102) {
		registerPacket(packet102);
	}

	public void func_20088_a(Packet103 packet103) {
		registerPacket(packet103);
	}

	public void func_20094_a(Packet104 packet104) {
		registerPacket(packet104);
	}

	public void func_20093_a(Packet130 packet130) {
		registerPacket(packet130);
	}

	public void func_20090_a(Packet105 packet105) {
		registerPacket(packet105);
	}

	public void handlePlayerInventory(Packet5PlayerInventory packet5playerinventory) {
		registerPacket(packet5playerinventory);
	}

	public void func_20089_a(Packet106 packet106) {
		registerPacket(packet106);
	}

	public void func_21146_a(Packet25 packet25) {
		registerPacket(packet25);
	}

	public void func_21145_a(Packet54 packet54) {
		registerPacket(packet54);
	}

	public void func_22186_a(Packet17Sleep packet17sleep) {
	}

	public void func_22185_a(Packet27 packet27) {
	}

	public void handleEaglercraftData(Packet69EaglercraftData packet) {
		registerPacket(packet);
	}
}
