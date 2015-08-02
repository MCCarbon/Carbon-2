package com.lastabyss.carbon.network.pipeline;

import java.util.List;

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
        PacketDataSerializerHelper serializer = new PacketDataSerializerHelper(message);
        int packetId = serializer.readVarInt();
        if (packetId > 0x08) {
            packetId--;
        }
        if (packetId == 0x08) {
            packetId = 99;
        }
        PacketDataSerializerHelper packetdataserializer = new PacketDataSerializerHelper(Unpooled.buffer());
        packetdataserializer.writeVarInt(packetId);
        packetdataserializer.writeBytes(message);
        list.add(packetdataserializer.getInternalByteBuf());
    }

}
