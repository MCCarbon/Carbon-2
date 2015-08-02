package com.lastabyss.carbon.network.packets;

import java.io.IOException;

import com.lastabyss.carbon.types.EnumMainHand;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;

public class CarbonPacketPlayInSettings extends PacketPlayInSettings {

    private EnumMainHand hand;

    @Override
    public void a(PacketDataSerializer serializer) throws IOException {
        //call read packet read
        super.a(serializer);
        //read additional field if we have it
        if (serializer.readableBytes() > 0) {
            hand = serializer.a(EnumMainHand.class);
        }
    }

    public EnumMainHand getMainHand() {
        return hand;
    }

}
