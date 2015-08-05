package com.lastabyss.carbon.network.pipeline;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;

import com.lastabyss.carbon.network.CarbonPlayerConnection;
import com.lastabyss.carbon.network.DataWatcherTransformer;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayOutBossBar;
import com.lastabyss.carbon.network.watchedentity.WatchedEntity;
import com.lastabyss.carbon.network.watchedentity.WatchedLiving;
import com.lastabyss.carbon.network.watchedentity.WatchedObject;
import com.lastabyss.carbon.network.watchedentity.WatchedPlayer;
import com.lastabyss.carbon.network.watchedentity.WatchedEntity.SpecificType;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;
import com.lastabyss.carbon.utils.Utils;

import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CarbonOutTransformer extends MessageToByteEncoder<ByteBuf> {

    private CarbonPlayerConnection connection;
    public CarbonOutTransformer(CarbonPlayerConnection connection) {
        this.connection = connection;
    }

    private final TIntObjectHashMap<WatchedEntity> entities = new TIntObjectHashMap<WatchedEntity>();
    private WatchedPlayer player;

    public void setPlayerId(int playerId) {
        player = new WatchedPlayer(playerId);
        entities.put(playerId, player);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf messagebuf, ByteBuf out) throws Exception {
        PacketDataSerializer outdata = new PacketDataSerializer(out);
        PacketDataSerializer message = new PacketDataSerializer(messagebuf);
        int packetId = PacketDataSerializerHelper.readVarInt(message);
        switch (packetId) {
            case 0x04: { //EntitEquip - fix slot id (left hand now has id == 1, and other parts 2-5, main hand id remain untouched)
                PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                PacketDataSerializerHelper.writeVarInt(outdata, PacketDataSerializerHelper.readVarInt(message));
                int slot = message.readShort();
                if (slot > 0) {
                    slot++;
                }
                PacketDataSerializerHelper.writeVarInt(outdata, slot);
                outdata.writeBytes(message);
                break;
            }
            case 0x0C: { //PlayerSpawn - add random uuid and transform entity metadata
                PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                int entityId = PacketDataSerializerHelper.readVarInt(message);
                entities.put(entityId, new WatchedPlayer(entityId));
                PacketDataSerializerHelper.writeVarInt(outdata, entityId);
                PacketDataSerializerHelper.writeUUID(outdata, PacketDataSerializerHelper.readUUID(message));
                outdata.writeInt(message.readInt());
                outdata.writeInt(message.readInt());
                outdata.writeInt(message.readInt());
                outdata.writeByte(message.readByte());
                outdata.writeByte(message.readByte());
                message.readShort();
                outdata.writeBytes(DataWatcherTransformer.transform(entities.get(entityId), Utils.toArray(message)));
                break;
            }
            case 0x0E: { //SpawnObject - add random uuid
                PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                int entityId = PacketDataSerializerHelper.readVarInt(message);
                int type = message.readByte();
                entities.put(entityId, new WatchedObject(entityId, type));
                PacketDataSerializerHelper.writeVarInt(outdata, entityId);
                PacketDataSerializerHelper.writeUUID(outdata, UUID.randomUUID());
                outdata.writeByte(type);
                outdata.writeInt(message.readInt());
                outdata.writeInt(message.readInt());
                outdata.writeInt(message.readInt());
                outdata.writeByte(message.readByte());
                outdata.writeByte(message.readByte());
                int data = message.readInt();
                outdata.writeInt(data);
                if (data != 0) {
                    outdata.writeShort(message.readShort());
                    outdata.writeShort(message.readShort());
                    outdata.writeShort(message.readShort());
                } else {
                    outdata.writeShort(0);
                    outdata.writeShort(0);
                    outdata.writeShort(0);
                }
                
                break;
            }
            case 0x0F: { //SpawnMob - add random uuid
                PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                int entityId = PacketDataSerializerHelper.readVarInt(message);
                PacketDataSerializerHelper.writeVarInt(outdata, entityId);
                PacketDataSerializerHelper.writeUUID(outdata, UUID.randomUUID());
                int type = message.readByte();
                entities.put(entityId, new WatchedLiving(entityId, type));
                outdata.writeByte(type);
                outdata.writeInt(message.readInt());
                outdata.writeInt(message.readInt());
                outdata.writeInt(message.readInt());
                outdata.writeByte(message.readByte());
                outdata.writeByte(message.readByte());
                outdata.writeByte(message.readByte());
                outdata.writeShort(message.readShort());
                outdata.writeShort(message.readShort());
                outdata.writeShort(message.readShort());
                outdata.writeBytes(DataWatcherTransformer.transform(entities.get(entityId), Utils.toArray(message)));
                break;
            }
            case 0x1C: { //EntityMetadata - transform entity metadata
                PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                int entityId = PacketDataSerializerHelper.readVarInt(message);
                PacketDataSerializerHelper.writeVarInt(outdata, entityId);
                outdata.writeBytes(DataWatcherTransformer.transform(entities.get(entityId), Utils.toArray(message)));
                break;
            }
            case 0x07: { //Respawn - reset watched entity map, and send original packet
                entities.clear();
                entities.put(player.getId(), player);
                messagebuf.resetReaderIndex();
                out.writeBytes(messagebuf);
                return;
            }
            case 0x13: { //DestroyEntities - remove entries from watched entity map and send original packet
                int count = PacketDataSerializerHelper.readVarInt(message);
                for (int i = 0; i < count; i++) {
                    entities.remove(PacketDataSerializerHelper.readVarInt(message));
                }
                messagebuf.resetReaderIndex();
                out.writeBytes(messagebuf);
                return;
            }
            case 0x30: { //WindowItems - add offhand slot to the end
                PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                outdata.writeByte(message.readByte());
                int count = message.readShort();
                outdata.writeShort(count + 1);
                ArrayList<ItemStack> items = new ArrayList<ItemStack>(count * 2);
                for (int i = 0; i < count; i++) {
                    items.add(PacketDataSerializerHelper.readItemStack(message));
                }
                items.add(connection.getOffHandItem());
                for (ItemStack itemstack : items) {
                    PacketDataSerializerHelper.writeItemStack(outdata, itemstack);
                }
                break;
            }
            case 0x49: { //UpdateEntityNBT - was removed in 1.9, just skip
                return;
            }
            case CarbonPacketPlayOutBossBar.FAKE_ID: { //BossBar - Write real packet id
                PacketDataSerializerHelper.writeVarInt(outdata, CarbonPacketPlayOutBossBar.REAL_ID);
                out.writeBytes(messagebuf);
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
