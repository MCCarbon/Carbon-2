package com.lastabyss.carbon.network;

import io.netty.util.AttributeKey;

import java.util.Map;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig.EnumPlayerDigType;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lastabyss.carbon.network.packets.CarbonPacketPlayInAnimation;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInBlockPlace;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInSettings;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInUseEntity;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInUseItem;
import com.lastabyss.carbon.network.pipeline.CarbonInTransformer;
import com.lastabyss.carbon.network.pipeline.CarbonOutTransformer;
import com.lastabyss.carbon.utils.DynamicEnumType;
import com.lastabyss.carbon.utils.Utils;

public class NetworkInjector implements Listener {

    public static final int PROTOCOL_VERSION_ID = 52;
    public static final Enum<EnumPlayerDigType> SWAP_HELD_ITEMS = DynamicEnumType.addEnum(EnumPlayerDigType.class, "SWAP_HELD_ITEMS", new Class<?>[0], new Object[0]);
    public static final AttributeKey<Boolean> IS_SNAPSHOT = AttributeKey.valueOf("IS_SNAPSHOT");

    public static void inject() {
        registerPacket(EnumProtocol.HANDSHAKING, InjectingHandshakePacket.class, 0, false);
        
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInUseItem.class, CarbonPacketPlayInUseItem.ID, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInUseEntity.class, 0x02, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInBlockPlace.class, 0x08, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInAnimation.class, 0x0A, false);
        registerPacket(EnumProtocol.PLAY, CarbonPacketPlayInSettings.class, 0x15, false);
    }

    public static void registerPacket(EnumProtocol protocol, Class<? extends Packet<? extends PacketListener>> packetClass, int packetID, boolean isClientbound) {
        Utils.<Map<Class<? extends Packet<? extends PacketListener>>, EnumProtocol>>getFieldValue(EnumProtocol.class, "h", null).put(packetClass, protocol);
        Utils.<Map<EnumProtocolDirection, Map<Integer, Class<? extends Packet<? extends PacketListener>>>>>getFieldValue(EnumProtocol.class, "j", protocol).get(isClientbound ? EnumProtocolDirection.CLIENTBOUND : EnumProtocolDirection.SERVERBOUND).put(packetID, packetClass);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        EntityPlayer nmsplayer = ((CraftPlayer) event.getPlayer()).getHandle();
        NetworkManager networkManager = nmsplayer.playerConnection.networkManager;
        if (networkManager.channel.attr(NetworkInjector.IS_SNAPSHOT).get()) {
            CarbonOutTransformer outransformer = new CarbonOutTransformer(new CarbonPlayerConnection(MinecraftServer.getServer(), networkManager, nmsplayer));
            outransformer.setPlayerId(event.getPlayer().getEntityId());
            networkManager.channel.pipeline()
            .addAfter("compress", "carbon-out-transformer", outransformer)
            .addAfter("decompress", "carbon-in-transformer", new CarbonInTransformer());
        }
    }

}
