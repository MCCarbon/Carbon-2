package com.lastabyss.carbon.network;

import java.lang.reflect.Field;

import org.spigotmc.SneakyThrow;

import com.lastabyss.carbon.network.pipeline.OldClientPacketDataRemapper;
import com.lastabyss.carbon.network.pipeline.StatusResponseTransformer;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketHandshakingInListener;
import net.minecraft.server.v1_8_R3.PacketHandshakingInSetProtocol;

public class InjectingHandshakePacket extends PacketHandshakingInSetProtocol {

    private boolean injected = false;

    @Override
    public void a(PacketHandshakingInListener listener) {
        try {
            NetworkManager manager = findNetworkManager(listener);
            if (b() == NetworkInjector.PROTOCOL_VERSION_ID) {
                manager.channel.attr(NetworkInjector.IS_SNAPSHOT).set(Boolean.TRUE);
                if (a() == EnumProtocol.STATUS) {
                    manager.channel.pipeline().addAfter("prepender", "carbon-status-transformer", new StatusResponseTransformer());
                }
                injected = true;
            } else {
                manager.channel.attr(NetworkInjector.IS_SNAPSHOT).set(Boolean.FALSE);
                manager.channel.pipeline().addAfter("packet_handler", "carbon-oldclients-packetdata-remapper", new OldClientPacketDataRemapper());
            }
        } catch (Throwable t) {
            SneakyThrow.sneaky(t);
        }
        super.a(listener);
    }

    @Override
    public int b() {
        int version = super.b();
        if (version == NetworkInjector.PROTOCOL_VERSION_ID && injected) {
            return 47;
        }
        return version;
    }

    static NetworkManager findNetworkManager(Object listener) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = listener.getClass();
        do {
            for (Field field : clazz.getDeclaredFields()) {
                if (NetworkManager.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    return (NetworkManager) field.get(listener);
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        throw new RuntimeException("[Carbon2] Unable to find networkmanager field in "+listener.getClass().getName());
    }

}
