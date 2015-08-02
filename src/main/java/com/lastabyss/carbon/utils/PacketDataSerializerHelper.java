package com.lastabyss.carbon.utils;

import java.io.IOException;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;

public class PacketDataSerializerHelper extends PacketDataSerializer {

    private ByteBuf bytebuf;
    public PacketDataSerializerHelper(ByteBuf bytebuf) {
        super(bytebuf);
        this.bytebuf = bytebuf;
    }

    public int readVarInt() {
        return e();
    }

    public void writeVarInt(int varInt) {
        b(varInt);
    }

    public String readString(int limit) {
        return c(limit);
    }

    public void writeString(String string) {
        a(string);
    }

    public ItemStack readItemStack() throws IOException {
        return i();
    }

    public void writeItemStack(ItemStack itemstack) {
        a(itemstack);
    }

    public byte[] readArray() {
        return a();
    }

    public void writeArray(byte[] array) {
        a(array);
    }

    public UUID readUUID() {
        return g();
    }

    public void writeUUID(UUID uuid) {
        a(uuid);
    }

    public ByteBuf getInternalByteBuf() {
        return bytebuf;
    }

}
