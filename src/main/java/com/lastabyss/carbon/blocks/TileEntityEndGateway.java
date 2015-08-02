package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.blocks.util.WrappedTileEntity;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Random;
//import net.minecraft.server.v1_8_R3.AxisAlignedBB;
//import net.minecraft.server.v1_8_R3.BlockPosition;
//import net.minecraft.server.v1_8_R3.Blocks;
//import net.minecraft.server.v1_8_R3.Chunk;
//import net.minecraft.server.v1_8_R3.Entity;
//import net.minecraft.server.v1_8_R3.EnumDirection;
//import net.minecraft.server.v1_8_R3.IBlockData;
//import net.minecraft.server.v1_8_R3.MathHelper;
//import net.minecraft.server.v1_8_R3.NBTTagCompound;
//import net.minecraft.server.v1_8_R3.Packet;
//import net.minecraft.server.v1_8_R3.PacketListener;
//import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
//import net.minecraft.server.v1_8_R3.TileEntity;
//import net.minecraft.server.v1_8_R3.Vec3D;
//import net.minecraft.server.v1_8_R3.World;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

/**
 * I will fix the rest of this class later, going to sleep
 *
 * @author Navid
 */
public class TileEntityEndGateway extends WrappedTileEntity {
//
//    private static final Logger looger = LogManager.getLogger();
//    private long life = 0L;
//    private int g = 0;
//    private BlockPosition exitPos;
//
//    @Override
//    public void write(NBTTagCompound var1) {
//        super.write(var1);
//        var1.setLong("Life", life);
//        if (exitPos != null) {
////            var1.put("ExitPortal", class_dy.a(exitPos)); //TODO: Cannot change this until class_dy is found out
//        }
//    }
//
//    @Override
//    public void read(NBTTagCompound var1) {
//        super.read(var1);
//        life = var1.getLong("Life");
//        if (var1.hasKeyOfType("ExitPortal", 10)) {
////            exitPos = class_dy.c(var1.getCompound("ExitPortal")); //TODO: Cannot change this until class_dy is found out
//        }
//
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        boolean var1 = this.isAlive();
//        boolean var2 = d();
//        ++life;
//        if (var2) {
//            --g;
//        } else if (!world.isClientSide) {
//            List<Entity> list = world.getEntities(Entity.class, new AxisAlignedBB(getPosition(), getPosition().a(1, 1, 1)));
//            if (!list.isEmpty()) {
//                this.a((Entity) list.get(0));
//            }
//        }
//
//        if ((var1 != this.isAlive()) || (var2 != d())) {
//            update();
//        }
//
//    }
//
//    public boolean isAlive() {
//        return life < 200L;
//    }
//
//    public boolean d() {
//        return g > 0;
//    }
//
//    @Override
//    public Packet<? extends PacketListener> getUpdatePacket() {
//        NBTTagCompound data = new NBTTagCompound();
//        write(data);
//        return new PacketPlayOutTileEntityData(position, 8, data);
//    }
//
//    public void h() {
//        if (!world.isClientSide) {
//            g = 20;
//            world.a(getPosition(), getBlock(), 1, 0);
//            update();
//        }
//    }
//
//    @Override
//    public boolean handleClientInput(int var1, int var2) {
//        if (var1 == 1) {
//            g = 20;
//            return true;
//        } else {
//            return super.handleClientInput(var1, var2);
//        }
//    }
//
//    public void a(Entity var1) {
//        if (!world.isClientSide && !d()) {
//            g = 100;
////            if ((exitPos == null) && (world.worldProvider instanceof class_apd)) { //TODO: Fix class_apd
////                j();
////            }
//
//            if (exitPos != null) {
//                BlockPosition var2 = i();
//                var1.a(var2.getX() + 0.5D, var2.getY() + 1.5D, var2.getZ() + 0.5D);
//            }
//
//            h();
//        }
//    }
//
//    private BlockPosition i() {
//        BlockPosition var1 = a(world, exitPos, 5, false);
//        looger.debug("Best exit position for portal at " + exitPos + " is " + var1);
//        return var1.up();
//    }
//
//    private void j() {
//        Vec3D var1 = (new Vec3D(getPosition().getX(), 0.0D, getPosition().getZ())).normalize();
//        Vec3D var2 = var1.multiply(1024.0D);
//
//        int var3;
//        for (var3 = 16; (a(world, var2).g() > 0) && (var3-- > 0); var2 = var2.add(var1.multiply(-16.0D))) {
//            looger.debug("Skipping backwards past nonempty chunk at " + var2);
//        }
//
//        for (var3 = 16; (a(world, var2).g() == 0) && (var3-- > 0); var2 = var2.add(var1.multiply(16.0D))) {
//            looger.debug("Skipping forward past empty chunk at " + var2);
//        }
//
//        looger.debug("Found chunk at " + var2);
//        Chunk var4 = a(world, var2);
//        exitPos = a(var4);
//        if (exitPos == null) {
//            exitPos = new BlockPosition(var2.x + 0.5D, 75.0D, var2.z + 0.5D);
//            looger.debug("Failed to find suitable block, settling on " + exitPos);
//            (new class_aqj()).b(world, new Random(exitPos.asLong()), exitPos);
//        } else {
//            looger.debug("Found block at " + exitPos);
//        }
//
//        exitPos = a(world, exitPos, 16, true);
//        looger.debug("Creating portal at " + exitPos);
//        exitPos = exitPos.up(10);
//        this.b(exitPos);
//        update();
//    }
//
//    private static BlockPosition a(World var0, BlockPosition var1, int var2, boolean var3) {
//        BlockPosition var4 = null;
//
//        for (int var5 = -var2; var5 <= var2; ++var5) {
//            for (int var6 = -var2; var6 <= var2; ++var6) {
//                if ((var5 != 0) || (var6 != 0) || var3) {
//                    for (int var7 = 255; var7 > (var4 == null ? 0 : var4.getY()); --var7) {
//                        BlockPosition var8 = new BlockPosition(var1.getX() + var5, var7, var1.getZ() + var6);
//                        IBlockData var9 = var0.getType(var8);
//                        if (var9.getBlock().isSoildFullCube() && (var3 || (var9.getBlock() != Blocks.BEDROCK))) {
//                            var4 = var8;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//
//        return var4 == null ? var1 : var4;
//    }
//
//    private static Chunk a(World var0, Vec3D var1) {
//        return var0.a(MathHelper.floor(var1.x / 16.0D), MathHelper.floor(var1.z / 16.0D));
//    }
//
//    private static BlockPosition a(Chunk var0) {
//        BlockPosition var1 = new BlockPosition(var0.a * 16, 30, var0.b * 16);
//        int var2 = (var0.g() + 16) - 1;
//        BlockPosition var3 = new BlockPosition(((var0.a * 16) + 16) - 1, var2, ((var0.b * 16) + 16) - 1);
//        BlockPosition var4 = null;
//        double var5 = 0.0D;
//        Iterator<?> var7 = BlockPosition.allInCube(var1, var3).iterator();
//
//        while (true) {
//            BlockPosition var8;
//            double var10;
//            do {
//                do {
//                    IBlockData var9;
//                    do {
//                        do {
//                            if (!var7.hasNext()) {
//                                return var4;
//                            }
//
//                            var8 = (BlockPosition) var7.next();
//                            var9 = var0.g(var8);
//                        } while (var9.getBlock() != Blocks.END_STONE);
//                    } while (var0.a(var8.up(1)).isSoildFullCube());
//                } while (var0.a(var8.up(2)).isSoildFullCube());
//
//                var10 = var8.distanceSquaredFromCenter(0.0D, 0.0D, 0.0D);
//            } while ((var4 != null) && (var10 >= var5));
//
//            var4 = var8;
//            var5 = var10;
//        }
//    }
//
//    private void b(BlockPosition var1) {
//        (new class_aqi()).b(world, new Random(), var1);
//        TileEntity var2 = world.getTileEntity(var1);
//        if (var2 instanceof TileEntityEndGateway) {
//            TileEntityEndGateway var3 = (TileEntityEndGateway) var2;
//            var3.exitPos = new BlockPosition(getPosition());
//            var3.update();
//        } else {
//            looger.warn("Couldn\'t save exit portal at " + var1);
//        }
//    }

}
