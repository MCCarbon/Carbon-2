package com.lastabyss.carbon.network.packets;

import java.io.IOException;

import com.lastabyss.carbon.network.CarbonPlayerConnection;
import com.lastabyss.carbon.types.EnumUsedHand;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;

public class CarbonPacketPlayInUseItem implements Packet<CarbonPlayerConnection> {

    public static final int REAL_ID = 8;
    public static final int FAKE_ID = 99;

    private EnumUsedHand hand;

    @Override
    public void a(PacketDataSerializer serializer) {
        hand = serializer.a(EnumUsedHand.class);
    }

    @Override
    public void a(CarbonPlayerConnection listener) {
        listener.handle(this);
    }

    @Override
    public void b(PacketDataSerializer arg0) throws IOException {
    }

    public EnumUsedHand getHand() {
        return hand;
    }

}
