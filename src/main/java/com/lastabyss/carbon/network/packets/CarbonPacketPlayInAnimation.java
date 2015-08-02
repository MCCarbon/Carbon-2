package com.lastabyss.carbon.network.packets;

import java.io.IOException;

import com.lastabyss.carbon.types.EnumUsedHand;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;

public class CarbonPacketPlayInAnimation extends PacketPlayInArmAnimation {

    private EnumUsedHand hand;

    @Override
    public void a(PacketDataSerializer serializer) throws IOException {
        //call read packet read
        super.a(serializer);
        //read additional field if we have it
        if (serializer.readableBytes() > 0) {
            hand = serializer.a(EnumUsedHand.class);
        }
    }

    public EnumUsedHand getHand() {
        return hand;
    }

}
