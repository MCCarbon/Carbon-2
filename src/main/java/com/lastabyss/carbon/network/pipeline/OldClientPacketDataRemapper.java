package com.lastabyss.carbon.network.pipeline;

import java.util.List;

import org.bukkit.Material;

import com.lastabyss.carbon.Injector;
import com.lastabyss.carbon.utils.DataWatcherSerializer;
import com.lastabyss.carbon.utils.DataWatcherSerializer.DataWatcherObject;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;
import com.lastabyss.carbon.utils.Utils;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk.ChunkMap;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@SuppressWarnings("deprecation")
public class OldClientPacketDataRemapper extends MessageToMessageEncoder<Packet<?>> {

    private static int[] blockIdRemaps = new int[4096];
    private static int[] itemIdRemaps = new int[4096];

    //TODO: better remaps
    static {
        for (int i = 0; i < blockIdRemaps.length; i++) {
            blockIdRemaps[i] = i;
        }
        blockIdRemaps[Injector.END_ROD_BLOCK.getId()] = Material.STONE.getId();
        blockIdRemaps[Injector.CHORUS_PLANT_BLOCK.getId()] = Material.CACTUS.getId();
        blockIdRemaps[Injector.CHORUS_FLOWER_MATERIAL.getId()] = Material.RED_ROSE.getId();
        blockIdRemaps[Injector.PURPUR_BLOCK.getId()] = Material.ENDER_STONE.getId();
        blockIdRemaps[Injector.PURPUR_PILLAR.getId()] = Material.ENDER_STONE.getId();
        blockIdRemaps[Injector.PURPUR_STAIRS.getId()] = Material.BRICK_STAIRS.getId();
        blockIdRemaps[Injector.PURPUR_SLAB.getId()] = Material.STONE_SLAB2.getId();
        blockIdRemaps[Injector.PURPUR_DOUBLE_SLAB.getId()] = Material.DOUBLE_STONE_SLAB2.getId();
        blockIdRemaps[Injector.END_BRICKS.getId()] = Material.ENDER_STONE.getId();
        blockIdRemaps[Injector.BEETROOTS.getId()] = Material.CROPS.getId();
        blockIdRemaps[Injector.GRASS_PATH.getId()] = Material.GRASS.getId();
        blockIdRemaps[Injector.END_GATEWAY.getId()] = Material.ENDER_PORTAL.getId();
        blockIdRemaps[Injector.STRUCTURE_BLOCK.getId()] = Material.BEDROCK.getId();
        for (int i = 0; i < itemIdRemaps.length; i++) {
            itemIdRemaps[i] = blockIdRemaps[i];
        }
        itemIdRemaps[Injector.CHORUS_FRUIT.getId()] = Material.STONE.getId();
        itemIdRemaps[Injector.CHORUS_FRUIT_POPPED.getId()] = Material.STONE.getId();
        itemIdRemaps[Injector.BEETROOT.getId()] = Material.BREAD.getId();
        itemIdRemaps[Injector.BEETROOT_SEEDS.getId()] = Material.SEEDS.getId();
        itemIdRemaps[Injector.BEETROOT_SOUP.getId()] = Material.MUSHROOM_SOUP.getId();
        itemIdRemaps[Injector.SPLASH_POTION.getId()] = Material.POTION.getId();
        itemIdRemaps[Injector.SPECTRAL_ARROW.getId()] = Material.ARROW.getId();
        itemIdRemaps[Injector.TIPPED_ARROW.getId()] = Material.ARROW.getId();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet<?> packet, List<Object> list) throws Exception {
        if (ctx.channel().attr(NetworkManager.c).get() == EnumProtocol.PLAY) {
            switch (EnumProtocol.PLAY.a(EnumProtocolDirection.CLIENTBOUND, packet)) {
                case 0x04: { //Entity Equipment
                    PacketDataSerializer originaldata = new PacketDataSerializer(Unpooled.buffer());
                    packet.b(originaldata);
                    PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer());
                    PacketDataSerializerHelper.writeVarInt(newdata, PacketDataSerializerHelper.readVarInt(originaldata));
                    newdata.writeShort(originaldata.readShort());
                    PacketDataSerializerHelper.writeItemStack(newdata, remapItem(PacketDataSerializerHelper.readItemStack(originaldata)));
                    packet.a(newdata);
                    break;
                }
                case 0x1C: { //Entity Metadata
                    PacketDataSerializer originaldata = new PacketDataSerializer(Unpooled.buffer());
                    packet.b(originaldata);
                    PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer());
                    PacketDataSerializerHelper.writeVarInt(newdata, PacketDataSerializerHelper.readVarInt(originaldata));
                    TIntObjectMap<DataWatcherObject> objects = DataWatcherSerializer.decodeData(Utils.toArray(originaldata));
                    TIntObjectIterator<DataWatcherObject> iterator = objects.iterator();
                    while (iterator.hasNext()) {
                        iterator.advance();
                        Object wvalue = iterator.value().value;
                        if (wvalue instanceof ItemStack) {
                            iterator.value().value = remapItem((ItemStack) wvalue);
                        }
                    }
                    newdata.writeBytes(DataWatcherSerializer.encodeData(objects));
                    packet.a(newdata);
                    break;
                }
                case 0x2F: { //Set slot
                    PacketDataSerializer originaldata = new PacketDataSerializer(Unpooled.buffer());
                    packet.b(originaldata);
                    PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer());
                    newdata.writeByte(originaldata.readByte());
                    newdata.writeShort(originaldata.readShort());
                    PacketDataSerializerHelper.writeItemStack(newdata, remapItem(PacketDataSerializerHelper.readItemStack(originaldata)));
                    packet.a(newdata);
                    break;
                }
                case 0x30: { //Window items
                    PacketDataSerializer originaldata = new PacketDataSerializer(Unpooled.buffer());
                    packet.b(originaldata);
                    PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer());
                    newdata.writeByte(originaldata.readByte());
                    int count = originaldata.readShort();
                    newdata.writeShort(count);
                    for (int i = 0; i < count; i++) {
                        PacketDataSerializerHelper.writeItemStack(newdata, remapItem(PacketDataSerializerHelper.readItemStack(originaldata))); 
                    }
                    packet.a(newdata);
                    break;
                }
                case 0x21: { //Chunk data
                    ChunkMap chunk = Utils.getFieldValue(PacketPlayOutMapChunk.class, "c", packet);
                    remapChunk(chunk);
                    break;
                }
                case 0x26: { //Bulk chunk data
                    ChunkMap[] chunks = Utils.getFieldValue(PacketPlayOutMapChunkBulk.class, "c", packet);
                    for (ChunkMap chunk : chunks) {
                        remapChunk(chunk);
                    }
                    break;
                }
                case 0x23: { //Block change
                    PacketDataSerializer originaldata = new PacketDataSerializer(Unpooled.buffer());
                    packet.b(originaldata);
                    PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer());
                    PacketDataSerializerHelper.writeBlockPosition(newdata, PacketDataSerializerHelper.readBlockPosition(originaldata));
                    PacketDataSerializerHelper.writeVarInt(newdata, remapBlock(PacketDataSerializerHelper.readVarInt(originaldata)));
                    packet.a(newdata);
                    break;
                }
                case 0x22: { //MultiBlockChange
                    PacketDataSerializer originaldata = new PacketDataSerializer(Unpooled.buffer());
                    packet.b(originaldata);
                    PacketDataSerializer newdata = new PacketDataSerializer(Unpooled.buffer());
                    newdata.writeInt(originaldata.readInt());
                    newdata.writeInt(originaldata.readInt());
                    int count = PacketDataSerializerHelper.readVarInt(originaldata);
                    PacketDataSerializerHelper.writeVarInt(newdata, count);
                    for (int i = 0; i < count; i++) {
                        newdata.writeByte(originaldata.readByte());
                        newdata.writeByte(originaldata.readByte());
                        PacketDataSerializerHelper.writeVarInt(newdata, remapBlock(PacketDataSerializerHelper.readVarInt(originaldata)));
                    }
                    packet.a(newdata);
                    break;
                }
                default: {
                    break;
                }
            }
        }
        list.add(packet);
    }

    private static ItemStack remapItem(ItemStack item) {
        if (item == null) {
            return null;
        }
        item.setItem(Item.getById(itemIdRemaps[Item.getId(item.getItem())]));
        return item;
    }

    private static void remapChunk(ChunkMap chunk) {
        byte[] data = chunk.a;
        for (int i = 0; i < 8192 * Integer.bitCount(chunk.b); i += 2) {
            int id = (data[i] & 0xFF) | ((data[i + 1] & 0xFF) << 8);
            int combinedId = remapBlock(id);
            data[i] = (byte) (combinedId);
            data[i + 1] = (byte) (combinedId >> 8);
        }
    }

    private static int remapBlock(int combinedId) {
        int meta = combinedId & 0xF;
        return ((blockIdRemaps[combinedId >> 4]) << 4) | meta;
    }

}
