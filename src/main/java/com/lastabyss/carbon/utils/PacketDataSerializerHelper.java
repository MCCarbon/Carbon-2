package com.lastabyss.carbon.utils;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;

public class PacketDataSerializerHelper {

    public static int readVarInt(PacketDataSerializer serializer) {
        return serializer.e();
    }

    public static void writeVarInt(PacketDataSerializer serializer, int varInt) {
        serializer.b(varInt);
    }

    public static String readString(PacketDataSerializer serializer, int limit) {
        return serializer.c(limit);
    }

    public static void writeString(PacketDataSerializer serializer, String string) {
        serializer.a(string);
    }

    public static ItemStack readItemStack(PacketDataSerializer serializer) throws IOException {
        return serializer.i();
    }

    public static void writeItemStack(PacketDataSerializer serializer, ItemStack itemstack) {
        serializer.a(itemstack);
    }
    
	public static BlockPosition readBlockPosition(PacketDataSerializer serializer) {
		return serializer.c();
	}

	public static void writeBlockPosition(PacketDataSerializer serializer, BlockPosition blockposition) {
		serializer.a(blockposition);
	}

    public static byte[] readArray(PacketDataSerializer serializer) {
        return serializer.a();
    }

    public static void writeArray(PacketDataSerializer serializer, byte[] array) {
        serializer.a(array);
    }

    public static UUID readUUID(PacketDataSerializer serializer) {
        return serializer.g();
    }

    public static void writeUUID(PacketDataSerializer serializer, UUID uuid) {
        serializer.a(uuid);
    }

}
