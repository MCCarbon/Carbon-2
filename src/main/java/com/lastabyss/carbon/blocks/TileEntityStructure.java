package com.lastabyss.carbon.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_8_R3.TileEntity;

/**
 * Will fix this later as well when we implement entities.
 *
 * @author Navid
 */
public class TileEntityStructure extends TileEntity {
//
//    private String name = "";
//    private String author = "";
//    private String metadata = "";
//    private BlockPosition pos = new BlockPosition(1, 1, 1);
//    private BlockPosition size = new BlockPosition(0, 0, 0);
//    private Block.class_a_in_class_agj mirror;
//    private Block.EnumRotation rotation;
//    private TileEntityStructure.EnumMode mode;
//    private boolean ignoreEntities;
//
//    public TileEntityStructure() {
//        mirror = Block.class_a_in_class_agj.NONE;
//        rotation = Block.EnumRotation.NONE;
//        mode = TileEntityStructure.EnumMode.DATA;
//    }
//
//    @Override
//    public void write(NBTTagCompound var1) {
//        super.write(var1);
//        var1.put("name", name);
//        var1.put("author", author);
//        var1.put("metadata", metadata);
//        var1.put("posX", pos.getX());
//        var1.put("posY", pos.getY());
//        var1.put("posZ", pos.getZ());
//        var1.put("sizeX", size.getX());
//        var1.put("sizeY", size.getY());
//        var1.put("sizeZ", size.getZ());
//        var1.put("rotation", rotation.toString());
//        var1.put("mirror", mirror.toString());
//        var1.put("mode", mode.toString());
//        var1.put("ignoreEntities", ignoreEntities);
//    }
//
//    @Override
//    public void read(NBTTagCompound var1) {
//        super.read(var1);
//        name = var1.getString("name");
//        author = var1.getString("author");
//        metadata = var1.getString("metadata");
//        pos = new BlockPosition(var1.getInt("posX"), var1.getInt("posY"), var1.getInt("posZ"));
//        size = new BlockPosition(var1.getInt("sizeX"), var1.getInt("sizeY"), var1.getInt("sizeZ"));
//
//        try {
//            rotation = Block.EnumRotation.valueOf(var1.getString("rotation"));
//        } catch (IllegalArgumentException var5) {
//            rotation = Block.EnumRotation.NONE;
//        }
//
//        try {
//            mirror = Block.class_a_in_class_agj.valueOf(var1.getString("mirror"));
//        } catch (IllegalArgumentException var4) {
//            mirror = Block.class_a_in_class_agj.NONE;
//        }
//
//        try {
//            mode = TileEntityStructure.EnumMode.valueOf(var1.getString("mode"));
//        } catch (IllegalArgumentException var3) {
//            mode = TileEntityStructure.EnumMode.DATA;
//        }
//
//        ignoreEntities = var1.getBoolean("ignoreEntities");
//    }
//
//    @Override
//    public Packet<? extends PacketListener> getUpdatePacket() {
//        NBTTagCompound var1 = new NBTTagCompound();
//        write(var1);
//        return new PacketPlayOutTileEntityData(position, 7, var1);
//    }
//
//    public void a(String var1) {
//        name = var1;
//    }
//
//    public void b(BlockPosition var1) {
//        pos = var1;
//    }
//
//    public void c(BlockPosition var1) {
//        size = var1;
//    }
//
//    public void a(Block.class_a_in_class_agj var1) {
//        mirror = var1;
//    }
//
//    public void a(Block.EnumRotation var1) {
//        rotation = var1;
//    }
//
//    public void b(String var1) {
//        metadata = var1;
//    }
//
//    public void a(TileEntityStructure.EnumMode var1) {
//        mode = var1;
//        IBlockData var2 = world.getType(getPosition());
//        if (var2.getBlock() == Blocks.STRUCTURE_BLOCK) {
//            world.setTypeAndData(getPosition(), var2.set(BlockStructureBlock.MODE, var1), 2);
//        }
//
//    }
//
//    public void a(boolean var1) {
//        ignoreEntities = var1;
//    }
//
//    public boolean l() {
//        if (mode != TileEntityStructure.EnumMode.SAVE) {
//            return false;
//        } else {
//            BlockPosition var1 = getPosition();
//            BlockPosition var3 = new BlockPosition(var1.getX() - 128, 0, var1.getZ() - 128);
//            BlockPosition var4 = new BlockPosition(var1.getX() + 128, 255, var1.getZ() + 128);
//            List<TileEntityStructure> var5 = this.a(var3, var4);
//            List<TileEntityStructure> var6 = this.a(var5);
//            if (var6.size() < 1) {
//                return false;
//            } else {
//                class_arw var7 = this.a(var1, var6);
//                if (((var7.d - var7.a) > 1) && ((var7.e - var7.b) > 1) && ((var7.f - var7.c) > 1)) {
//                    pos = new BlockPosition((var7.a - var1.getX()) + 1, (var7.b - var1.getY()) + 1, (var7.c - var1.getZ()) + 1);
//                    size = new BlockPosition(var7.d - var7.a - 1, var7.e - var7.b - 1, var7.f - var7.c - 1);
//                    update();
//                    world.notify(var1);
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        }
//    }
//
//    private List<TileEntityStructure> a(List<TileEntityStructure> var1) {
//        Iterable<TileEntityStructure> var2 = Iterables.filter(var1, (new Predicate<TileEntityStructure>() {
//            @Override
//            public boolean apply(TileEntityStructure var1) {
//                return (var1.mode == TileEntityStructure.EnumMode.CORNER) && name.equals(var1.name);
//            }
//        }));
//        return Lists.newArrayList(var2);
//    }
//
//    private List<TileEntityStructure> a(BlockPosition var1, BlockPosition var2) {
//        ArrayList<TileEntityStructure> var3 = Lists.newArrayList();
//        Iterator<?> var4 = BlockPosition.allInCubeM(var1, var2).iterator();
//
//        while (var4.hasNext()) {
//            BlockPosition.MutableBlockPosition var5 = (BlockPosition.MutableBlockPosition) var4.next();
//            IBlockData var6 = world.getType(var5);
//            if (var6.getBlock() == Blocks.STRUCTURE_BLOCK) {
//                TileEntity var7 = world.getTileEntity(var5);
//                if ((var7 != null) && (var7 instanceof TileEntityStructure)) {
//                    var3.add((TileEntityStructure) var7);
//                }
//            }
//        }
//
//        return var3;
//    }
//
//    private class_arw a(BlockPosition var1, List<TileEntityStructure> var2) {
//        class_arw var3;
//        if (var2.size() > 1) {
//            BlockPosition var4 = var2.get(0).getPosition();
//            var3 = new class_arw(var4, var4);
//        } else {
//            var3 = new class_arw(var1, var1);
//        }
//
//        Iterator<TileEntityStructure> var7 = var2.iterator();
//
//        while (var7.hasNext()) {
//            TileEntityStructure var5 = var7.next();
//            BlockPosition var6 = var5.getPosition();
//            if (var6.getX() < var3.a) {
//                var3.a = var6.getX();
//            } else if (var6.getX() > var3.d) {
//                var3.d = var6.getX();
//            }
//
//            if (var6.getY() < var3.b) {
//                var3.b = var6.getY();
//            } else if (var6.getY() > var3.e) {
//                var3.e = var6.getY();
//            }
//
//            if (var6.getZ() < var3.c) {
//                var3.c = var6.getZ();
//            } else if (var6.getZ() > var3.f) {
//                var3.f = var6.getZ();
//            }
//        }
//
//        return var3;
//    }
//
//    public boolean m() {
//        if (mode != TileEntityStructure.EnumMode.SAVE) {
//            return false;
//        } else {
//            BlockPosition var1 = getPosition().add(pos);
//            class_ast var2 = ((BlockStructureBlock) getBlock()).l();
//            class_asv var3 = var2.a(new MinecraftKey(name));
//            var3.a(world, var1, size, !ignoreEntities);
//            var3.a(author);
//            var2.c(new MinecraftKey(name));
//            return true;
//        }
//    }
//
//    public boolean n() {
//        if (mode != TileEntityStructure.EnumMode.LOAD) {
//            return false;
//        } else {
//            BlockPosition var1 = getPosition().add(pos);
//            class_ast var2 = ((BlockStructureBlock) getBlock()).l();
//            class_asv var3 = var2.a(new MinecraftKey(name));
//            if (!class_nz.b(var3.b())) {
//                author = var3.b();
//            }
//
//            if (!size.equals(var3.a())) {
//                size = var3.a();
//                return false;
//            } else {
//                BlockPosition var4 = var3.a(rotation);
//                Iterator<?> var5 = world.b((Entity) null, (new AxisAlignedBB(var1, var4.add(var1).add(-1, -1, -1)))).iterator();
//
//                while (var5.hasNext()) {
//                    Entity var6 = (Entity) var5.next();
//                    world.f(var6);
//                }
//
//                class_asu var7 = (new class_asu()).a(mirror).a(rotation).a(ignoreEntities).a((class_aeh) null).b(false).c(false);
//                var3.a(world, var1, var7);
//                return true;
//            }
//        }
//    }
//
//    public static enum EnumMode implements INamable {
//
//        SAVE("save", 0), LOAD("load", 1), CORNER("corner", 2), DATA("data", 3);
//
//        private static final TileEntityStructure.EnumMode[] BY_ID;
//        private final String name;
//        private final int id;
//
//        private EnumMode(String name, int id) {
//            this.name = name;
//            this.id = id;
//        }
//
//        @Override
//        public String getName() {
//            return name;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public static TileEntityStructure.EnumMode getById(int var0) {
//            if ((var0 < 0) || (var0 >= BY_ID.length)) {
//                var0 = 0;
//            }
//
//            return BY_ID[var0];
//        }
//
//        static {
//            BY_ID = new TileEntityStructure.EnumMode[values().length];
//            TileEntityStructure.EnumMode[] var0 = values();
//            int var1 = var0.length;
//
//            for (int var2 = 0; var2 < var1; ++var2) {
//                TileEntityStructure.EnumMode var3 = var0[var2];
//                BY_ID[var3.getId()] = var3;
//            }
//
//        }
//
//    }
}
