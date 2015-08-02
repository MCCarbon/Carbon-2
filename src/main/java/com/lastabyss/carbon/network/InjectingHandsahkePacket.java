package com.lastabyss.carbon.network;

import io.netty.util.AttributeKey;

import java.lang.reflect.Field;

import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketHandshakingInListener;
import net.minecraft.server.v1_8_R3.PacketHandshakingInSetProtocol;

public class InjectingHandsahkePacket extends PacketHandshakingInSetProtocol {

    public static final AttributeKey<Boolean> IS_SNAPSHOT = AttributeKey.valueOf("IS_SNAPSHOT");

    private boolean injected = false;

    @Override
    public void a(PacketHandshakingInListener listener) {
        if (b() == 51) {
            try {
                NetworkManager manager = findNetworkManager(listener);
                manager.channel.attr(IS_SNAPSHOT).set(Boolean.TRUE);
                injected = true;
            } catch (Throwable t) {
                SneakyThrow.sneaky(t);
            }
        }
        super.a(listener);
    }

    @Override
    public int b() {
        int version = super.b();
        if (version == 51 && injected) {
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
