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
        PacketDataSerializerHelper helper = new PacketDataSerializerHelper(serializer);
        PacketDataSerializerHelper newdata = new PacketDataSerializerHelper(Unpooled.buffer());
        newdata.a(helper.c());
        if (helper.readableBytes() == 5) {
            newdata.writeByte(helper.a(EnumDirection.class).ordinal());
            hand = helper.a(EnumUsedHand.class);
            newdata.writeItemStack(null);
        } else {
            newdata.writeByte(helper.readByte());
            newdata.writeItemStack(helper.readItemStack());
        }
        newdata.writeByte(helper.readByte());
        newdata.writeByte(helper.readByte());
        newdata.writeByte(helper.readByte());
        //reset reader index and call read packet read
        super.a(newdata);
    }

    public EnumUsedHand getHand() {
        return hand;
    }

}
