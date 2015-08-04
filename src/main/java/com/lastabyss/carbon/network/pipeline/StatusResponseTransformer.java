package com.lastabyss.carbon.network.pipeline;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketStatusOutServerInfo;
import net.minecraft.server.v1_8_R3.ServerPing;
import net.minecraft.server.v1_8_R3.ServerPing.ServerData;

import com.google.gson.Gson;
import com.lastabyss.carbon.network.NetworkInjector;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;
import com.lastabyss.carbon.utils.Utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class StatusResponseTransformer extends MessageToByteEncoder<ByteBuf> {

    private static final Gson pingGson = Utils.getFieldValue(PacketStatusOutServerInfo.class, "a", null);

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf messagebuf, ByteBuf out) throws Exception {
        PacketDataSerializer outdata = new PacketDataSerializer(out);
        PacketDataSerializer message = new PacketDataSerializer(messagebuf);
        int packetId = PacketDataSerializerHelper.readVarInt(message);
        switch (packetId) {
            case 0: { //Status response
                ServerPing serverping = pingGson.fromJson(PacketDataSerializerHelper.readString(message, 32767), ServerPing.class);
                serverping.setServerInfo(new ServerData(serverping.c().a(), NetworkInjector.PROTOCOL_VERSION_ID));
                PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                PacketDataSerializerHelper.writeString(outdata, pingGson.toJson(serverping));
                break;
            }
            default: { //Any other - just send original packet
                messagebuf.resetReaderIndex();
                out.writeBytes(messagebuf);
                return;
            }
        }
    }

}
