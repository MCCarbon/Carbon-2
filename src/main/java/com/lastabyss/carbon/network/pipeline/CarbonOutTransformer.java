package com.lastabyss.carbon.network.pipeline;

import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;
import com.lastabyss.carbon.utils.Utils;

public class CarbonOutTransformer extends MessageToByteEncoder<ByteBuf> {

    private CarbonPlayerConnection connection;
    private static int teleportId = 0;
    private static final Map<Integer, Integer> remapPackets = new HashMap<>();
    
    static {
    	remapPackets.put(0x0E, 0x00);
    	remapPackets.put(0x11, 0x01);
    	remapPackets.put(0x2C, 0x02);
    	remapPackets.put(0x0F, 0x03);
    	remapPackets.put(0x10, 0x04);
    	remapPackets.put(0x0C, 0x05);
    	remapPackets.put(0x0B, 0x06);
    	remapPackets.put(0x37, 0x07);
    	remapPackets.put(0x25, 0x08);
    	remapPackets.put(0x35, 0x09);
    	remapPackets.put(0x24, 0x0A);
    	remapPackets.put(0x23, 0x0B);
    	//0x0C is the new BossBar packet
    	remapPackets.put(0x41, 0x0D);
    	remapPackets.put(0x3A, 0x0E);
    	remapPackets.put(0x03, 0x0F);
    	remapPackets.put(0x22, 0x10);
    	remapPackets.put(0x32, 0x11);
    	remapPackets.put(0x2E, 0x12);
    	remapPackets.put(0x2D, 0x13);
    	remapPackets.put(0x30, 0x14);
    	remapPackets.put(0x31, 0x15);
    	remapPackets.put(0x2F, 0x16);
    	//0x17 is the new cooldown packet
    	remapPackets.put(0x3F, 0x18);
    	remapPackets.put(0x29, 0x19);
    	remapPackets.put(0x40, 0x1A);
    	remapPackets.put(0x1A, 0x1B);
    	remapPackets.put(0x27, 0x1C);
    	//0x1D is the new Chunk Unload packet
    	remapPackets.put(0x2B, 0x1E);
    	remapPackets.put(0x00, 0x1F);
    	remapPackets.put(0x21, 0x20);
    	remapPackets.put(0x28, 0x21);
    	remapPackets.put(0x2A, 0x22);
    	remapPackets.put(0x01, 0x23);
    	remapPackets.put(0x34, 0x24);
    	remapPackets.put(0x15, 0x25);
    	remapPackets.put(0x17, 0x26);
    	remapPackets.put(0x16, 0x27);
    	remapPackets.put(0x14, 0x28);
    	//0x29 is the new Vehicle Move packet
    	remapPackets.put(0x36, 0x2A);
    	remapPackets.put(0x39, 0x2B);
    	remapPackets.put(0x42, 0x2C);
    	remapPackets.put(0x38, 0x2D);
    	remapPackets.put(0x08, 0x2E);
    	remapPackets.put(0x0A, 0x2F);
    	remapPackets.put(0x13, 0x30);
    	remapPackets.put(0x1E, 0x31);
    	remapPackets.put(0x48, 0x32);
    	remapPackets.put(0x07, 0x33);
    	remapPackets.put(0x19, 0x34);
    	remapPackets.put(0x44, 0x35);
    	remapPackets.put(0x43, 0x36);
    	remapPackets.put(0x09, 0x37);
    	remapPackets.put(0x3D, 0x38);
    	remapPackets.put(0x1C, 0x39);
    	remapPackets.put(0x1B, 0x3A);
    	remapPackets.put(0x12, 0x3B);
    	remapPackets.put(0x04, 0x3C);
    	remapPackets.put(0x1F, 0x3D);
    	remapPackets.put(0x06, 0x3E);
    	remapPackets.put(0x3B, 0x3F);
    	//0x40 is the new Set Passangers packet
    	remapPackets.put(0x3E, 0x41);
    	remapPackets.put(0x3C, 0x42);
    	remapPackets.put(0x05, 0x43);
    	remapPackets.put(0x03, 0x44);
    	remapPackets.put(0x45, 0x45);
    	remapPackets.put(0x33, 0x46);
    	//0x47 is the new Sound Effect packet
    	remapPackets.put(0x47, 0x48);
    	remapPackets.put(0x0D, 0x49);
    	remapPackets.put(0x18, 0x4A);
    	remapPackets.put(0x20, 0x4B);
    	remapPackets.put(0x1D, 0x4C);
    }
    
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
        if(remapPackets.containsKey(packetId)) {
        	packetId = remapPackets.get(packetId);
        }
        switch (packetId) {
            case 0x00: { //SpawnObject - add random uuid
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
            case 0x03: { //SpawnMob - add random uuid
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
            case 0x05: { //PlayerSpawn - add random uuid and transform entity metadata
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
            case 0x12: { //DestroyEntities - remove entries from watched entity map and send original packet
                int count = PacketDataSerializerHelper.readVarInt(message);
                for (int i = 0; i < count; i++) {
                    entities.remove(PacketDataSerializerHelper.readVarInt(message));
                }
                messagebuf.resetReaderIndex();
                out.writeBytes(messagebuf);
                return;
            }
            case 0x14: { //WindowItems - add offhand slot to the end
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
            case 0x19: { //Name Sound Effect - Packet now requires an enum varint for the sound category
				PacketDataSerializerHelper.writeVarInt(outdata, packetId);
				PacketDataSerializerHelper.writeString(outdata, PacketDataSerializerHelper.readString(message, 32767));
				PacketDataSerializerHelper.writeVarInt(outdata, 0); // TODO: Change this
				outdata.writeInt(message.readInt());
				outdata.writeInt(message.readInt());
				outdata.writeInt(message.readInt());
				outdata.writeFloat(message.readFloat());
				outdata.writeByte(message.readByte());
				return;
            }
            case 0x24: { //Map Data - add tracking position boolean
	        	PacketDataSerializerHelper.writeVarInt(outdata, packetId);
	        	PacketDataSerializerHelper.writeVarInt(outdata, PacketDataSerializerHelper.readVarInt(message));
				outdata.writeByte(message.readByte());
				outdata.writeBoolean(true); // Tracking Position
				
				int length = PacketDataSerializerHelper.readVarInt(message);
				PacketDataSerializerHelper.writeVarInt(outdata, length);
				
		        for(int i = 0; i < length; ++i) {
		    	    outdata.writeByte(message.readByte());
		            outdata.writeByte(message.readByte());
		            outdata.writeByte(message.readByte());
		        }
				
		        byte b = message.readByte();
		        outdata.writeByte(b);
		        
		        if(b > 0){
			        outdata.writeByte(message.readByte());
			        outdata.writeByte(message.readByte());
			        outdata.writeByte(message.readByte());
			        PacketDataSerializerHelper.writeArray(outdata, PacketDataSerializerHelper.readArray(message));
		        }
            	return;
            }
            case 0x20: { //Chunk Packet - change everything, will be a pain in the ass to fix
            	
            	return;
            }
            case 0x2C: { //CombatEvent - change message type from string into chat
            	
            	return;
            }
            case 0x2E: { //Player Position And Look - Add teleport id;
            	
            }
            case 0x33: { //Respawn - reset watched entity map, and send original packet
                entities.clear();
                entities.put(player.getId(), player);
                messagebuf.resetReaderIndex();
                out.writeBytes(messagebuf);
                return;
            }
            case 0x39: { //EntityMetadata - transform entity metadata
                PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                int entityId = PacketDataSerializerHelper.readVarInt(message);
                PacketDataSerializerHelper.writeVarInt(outdata, entityId);
                outdata.writeBytes(DataWatcherTransformer.transform(entities.get(entityId), Utils.toArray(message)));
                break;
            }
            case 0x3C: { //EntitEquip - fix slot id (left hand now has id == 1, and other parts 2-5, main hand id remain untouched)
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
            case 0x41: { //Teams - add the collision rule
            	
            	return;
            }
            case 0x4B: { //Entity Properties - add generic.attackSpeed, attackDamage has a max of 2048.0, movementSpeed and maxHealth has a max of 1024
            	
            	return;
            }
            case 0x26: //Map Chunk Bulk  - was removed in 1.9, just skip
            case 0x46: //Set Compression - was removed in 1.9, just skip
            case 0x49:{ //UpdateEntityNBT - was removed in 1.9, just skip
                return;
            }
            case CarbonPacketPlayOutBossBar.FAKE_ID: { //BossBar - Write real packet id
                PacketDataSerializerHelper.writeVarInt(outdata, CarbonPacketPlayOutBossBar.REAL_ID);
                out.writeBytes(messagebuf);
                break;
            }
            default: { //Any other - just send original packet
            	 PacketDataSerializerHelper.writeVarInt(outdata, packetId);
                 out.writeBytes(messagebuf);
                return;
            }
        }
    }

}
