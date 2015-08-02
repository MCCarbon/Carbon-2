package com.lastabyss.carbon.network;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lastabyss.carbon.network.packets.CarbonPacketPlayInAnimation;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInBlockPlace;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInSettings;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInUse;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInUseEntity;
import com.lastabyss.carbon.network.pipeline.CarbonInTransformer;
import com.lastabyss.carbon.network.pipeline.CarbonOutTransformer;
import com.lastabyss.carbon.utils.Utils;

public class NetworkInjector implements Listener {

    public static void inject() {
        registerPacket(EnumProtocol.HANDSHAKING, InjectingHandsahkePacket.class, 0, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInUse.class, 99, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInUseEntity.class, 0x02, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInBlockPlace.class, 0x08, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInAnimation.class, 0x0A, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInSettings.class, 0x15, false);
    }

    @SuppressWarnings("unchecked")
    public static void registerPacket(EnumProtocol protocol, Class<? extends Packet<? extends PacketListener>> packetClass, int packetID, boolean isClientbound) {
        try {
            ((Map<Class<? extends Packet<? extends PacketListener>>, EnumProtocol>) Utils.<Field>setAccessible(EnumProtocol.class.getDeclaredField("h")).get(null)).put(packetClass, protocol);
            ((Map<EnumProtocolDirection, Map<Integer, Class<? extends Packet<? extends PacketListener>>>>) Utils.<Field>setAccessible(EnumProtocol.class.getDeclaredField("j")).get(protocol)).get(isClientbound ? EnumProtocolDirection.CLIENTBOUND : EnumProtocolDirection.SERVERBOUND).put(packetID, packetClass);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace(System.out);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        NetworkManager networkManager = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.networkManager;
        if (networkManager.channel.attr(InjectingHandsahkePacket.IS_SNAPSHOT) != null) {
            CarbonOutTransformer outransformer = new CarbonOutTransformer();
            outransformer.setPlayerId(event.getPlayer().getEntityId());
            networkManager.channel.pipeline()
            .addAfter("compress", "carbon-out-transformer", outransformer)
            .addAfter("decompress", "carbon-in-transformer", new CarbonInTransformer());
        }
    }

}
