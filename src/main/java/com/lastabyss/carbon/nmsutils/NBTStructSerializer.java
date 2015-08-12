package com.lastabyss.carbon.nmsutils;

import java.util.UUID;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public final class NBTStructSerializer {

	public static NBTTagCompound toNBT(UUID uuid) {
		NBTTagCompound var1 = new NBTTagCompound();
		var1.setLong("M", uuid.getMostSignificantBits());
		var1.setLong("L", uuid.getLeastSignificantBits());
		return var1;
	}

	public static UUID getUUID(NBTTagCompound var0) {
		return new UUID(var0.getLong("M"), var0.getLong("L"));
	}

	public static BlockPosition getBlockPosition(NBTTagCompound compound) {
		return new BlockPosition(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z"));
	}

	public static NBTTagCompound toNBT(BlockPosition position) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInt("X", position.getX());
		tag.setInt("Y", position.getY());
		tag.setInt("Z", position.getZ());
		return tag;
	}

}
