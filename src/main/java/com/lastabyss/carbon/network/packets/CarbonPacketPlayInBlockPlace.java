package com.lastabyss.carbon.network.packets;

import io.netty.buffer.Unpooled;

import java.io.IOException;

import com.lastabyss.carbon.types.EnumUsedHand;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;

public class CarbonPacketPlayInBlockPlace extends PacketPlayInBlockPlace {

    private EnumUsedHand hand;

    @Override
    public void a(PacketDataSerializer serializer) throws IOException {
        //remember reader index so we can reset it later
        int readerIndex = serializer.readerIndex();
        //try to read packet and find out if we have new field or not
        PacketDataSerializerHelper helper = new PacketDataSerializerHelper(serializer);
        PacketDataSerializerHelper newdata = new PacketDataSerializerHelper(Unpooled.buffer());
        newdata.a(helper.c());
        newdata.writeByte(helper.readVarInt()); //block face is limited for 1-6, so it's 1 byte size anyway right now
        if (helper.readableBytes() == 4) {
            hand = helper.a(EnumUsedHand.class);
        } else {
            newdata.writeItemStack(helper.readItemStack());
        }
        newdata.writeByte(helper.readByte());
        newdata.writeByte(helper.readByte());
        newdata.writeByte(helper.readByte());
        //reset reader index and call read packet read
        serializer.readerIndex(readerIndex);
        super.a(newdata);
    }

    public EnumUsedHand getHand() {
        return hand;
    }

}
