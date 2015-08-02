package com.lastabyss.carbon.network.packets;

import java.io.IOException;

import com.lastabyss.carbon.types.EnumUsedHand;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class CarbonPacketPlayInUse implements Packet<PacketListenerPlayIn> {

    protected EnumUsedHand hand;

    @Override
    public void a(PacketDataSerializer serializer) {
        hand = serializer.a(EnumUsedHand.class);
    }

    @Override
    public void a(PacketListenerPlayIn listener) {
        //nothing for now
    }

    @Override
    public void b(PacketDataSerializer arg0) throws IOException {
    }

}
