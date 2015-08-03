package com.lastabyss.carbon.network.pipeline;

import java.util.List;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;

import com.lastabyss.carbon.network.packets.CarbonPacketPlayInUseItem;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class CarbonInTransformer extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf message, List<Object> list) throws Exception {
        if (!message.isReadable()) { //Skip empty buffers
            return;
        }
        PacketDataSerializer serializer = new PacketDataSerializer(message);
        int packetId = PacketDataSerializerHelper.readVarInt(serializer);
        if (packetId == 0x08) {
            packetId = CarbonPacketPlayInUseItem.ID + 1;
        }
        if (packetId > 0x08) {
            packetId--;
        }
        ByteBuf buffer = Unpooled.buffer();
        PacketDataSerializer packetdataserializer = new PacketDataSerializer(buffer);
        PacketDataSerializerHelper.writeVarInt(packetdataserializer, packetId);
        packetdataserializer.writeBytes(message);
        list.add(buffer);
    }

}
