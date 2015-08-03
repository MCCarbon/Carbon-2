package com.lastabyss.carbon.network.packets;

import io.netty.buffer.Unpooled;

import java.io.IOException;

import com.lastabyss.carbon.types.EnumUsedHand;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;

import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;

public class CarbonPacketPlayInBlockPlace extends PacketPlayInBlockPlace {

    private EnumUsedHand hand;

    @Override
    public void a(PacketDataSerializer serializer) throws IOException {
        //try to read packet and find out if we have new field or not
        PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer());
        newdata.a(serializer.c());
        if (serializer.readableBytes() == 5) {
            newdata.writeByte(serializer.a(EnumDirection.class).ordinal());
            hand = serializer.a(EnumUsedHand.class);
            PacketDataSerializerHelper.writeItemStack(newdata, null);
        } else {
            newdata.writeByte(serializer.readByte());
            PacketDataSerializerHelper.writeItemStack(newdata, PacketDataSerializerHelper.readItemStack(serializer));
        }
        newdata.writeByte(serializer.readByte());
        newdata.writeByte(serializer.readByte());
        newdata.writeByte(serializer.readByte());
        //reset reader index and call read packet read
        super.a(newdata);
    }

    public EnumUsedHand getHand() {
        return hand;
    }

}
