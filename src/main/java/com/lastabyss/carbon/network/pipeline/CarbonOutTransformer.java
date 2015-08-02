package com.lastabyss.carbon.network.pipeline;

import java.util.UUID;

import com.lastabyss.carbon.network.DataWatcherTransformer;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;
import com.lastabyss.carbon.utils.Utils;
import com.lastabyss.carbon.utils.watchedentity.WatchedEntity;
import com.lastabyss.carbon.utils.watchedentity.WatchedPlayer;

import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CarbonOutTransformer extends MessageToByteEncoder<ByteBuf> {

    private final TIntObjectHashMap<WatchedEntity> entities = new TIntObjectHashMap<WatchedEntity>();
    private WatchedPlayer player;

    public void setPlayerId(int playerId) {
        player = new WatchedPlayer(playerId);
        entities.put(playerId, player);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf message, ByteBuf out) throws Exception {
        PacketDataSerializerHelper packetdataserializer = new PacketDataSerializerHelper(Unpooled.buffer());
        PacketDataSerializerHelper messageserializer = new PacketDataSerializerHelper(message);
        int packetId = messageserializer.readVarInt();
        packetdataserializer.writeVarInt(packetId);
        switch (packetId) {
            case 0x04: { //EntitEquip - fix slot id (left hand now has id == 1, and other parts 2-5, main hand id remain untouched)
                packetdataserializer.writeVarInt(messageserializer.readVarInt());
                int slot = messageserializer.readShort();
                if (slot > 0) {
                    slot++;
                }
                packetdataserializer.writeVarInt(slot);
                packetdataserializer.writeBytes(messageserializer);
                break;
            }
            case 0x0C: { //PlayerSpawn - add random uuid and transform entity metadata
                int entityId = messageserializer.readVarInt();
                entities.put(entityId, new WatchedPlayer(entityId));
                packetdataserializer.writeVarInt(entityId);
                packetdataserializer.writeUUID(messageserializer.readUUID());
                packetdataserializer.writeInt(messageserializer.readInt());
                packetdataserializer.writeInt(messageserializer.readInt());
                packetdataserializer.writeInt(messageserializer.readInt());
                packetdataserializer.writeByte(messageserializer.readByte());
                packetdataserializer.writeByte(messageserializer.readByte());
                messageserializer.readShort();
                packetdataserializer.writeBytes(DataWatcherTransformer.transform(entities.get(entityId), Utils.toArray(messageserializer)));
                break;
            }
            case 0x0E: { //SpawnObject - add random uuid
                packetdataserializer.writeVarInt(messageserializer.readVarInt());
                packetdataserializer.writeUUID(UUID.randomUUID()); //TODO: check if it is safe
                packetdataserializer.writeByte(messageserializer.readByte());
                packetdataserializer.writeInt(messageserializer.readInt());
                packetdataserializer.writeInt(messageserializer.readInt());
                packetdataserializer.writeInt(messageserializer.readInt());
                packetdataserializer.writeByte(messageserializer.readByte());
                packetdataserializer.writeByte(messageserializer.readByte());
                int data = messageserializer.readInt();
                packetdataserializer.writeInt(data);
                if (data != 0) {
                    packetdataserializer.writeShort(messageserializer.readShort());
                    packetdataserializer.writeShort(messageserializer.readShort());
                    packetdataserializer.writeShort(messageserializer.readShort());
                } else {
                    packetdataserializer.writeShort(0);
                    packetdataserializer.writeShort(0);
                    packetdataserializer.writeShort(0);
                }
                break;
            }
            case 0x0F: { //SpawnMob - add random uuid
                packetdataserializer.writeVarInt(messageserializer.readVarInt());
                packetdataserializer.writeUUID(UUID.randomUUID()); //TODO: check if it is safe
                packetdataserializer.writeBytes(messageserializer);
                break;
            }
            case 0x1C: { //EntityMetadata - transform entity metadata
                int entityId = messageserializer.readVarInt();
                packetdataserializer.writeVarInt(entityId);
                packetdataserializer.writeBytes(DataWatcherTransformer.transform(entities.get(entityId), Utils.toArray(messageserializer)));
                break;
            }
            case 0x07: { //Respawn - reset watched entity map, and send original packet
                entities.clear();
                entities.put(player.getId(), player);
                message.resetReaderIndex();
                out.writeBytes(message);
                return;
            }
            case 0x13: { //DestroyEntities - remove entries from watched entity map and send original packet
                int count = messageserializer.readVarInt();
                for (int i = 0; i < count; i++) {
                    entities.remove(messageserializer.readVarInt());
                }
                message.resetReaderIndex();
                out.writeBytes(message);
                return;
            }
            case 0x49: { //UpdateEntityNBT - was removed in 1.9, just skip
                return;
            }
            default: { //Any other - just send original packet
                message.resetReaderIndex();
                out.writeBytes(message);
                return;
            }
        }
        //add transformed packet
        out.writeBytes(packetdataserializer.getInternalByteBuf());
    }

}
