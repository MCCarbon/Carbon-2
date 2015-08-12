package com.lastabyss.carbon.blocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldProviderTheEnd;

import com.lastabyss.carbon.blocks.util.BlockPositionUtil;
import com.lastabyss.carbon.blocks.util.WrappedTickingTileEntity;
import com.lastabyss.carbon.generators.EndGatewayGenerator;
import com.lastabyss.carbon.generators.EndGatewayPlatformGenerator;
import com.lastabyss.carbon.nmsutils.NBTStructSerializer;
import com.lastabyss.carbon.nmsutils.NewAxisAlignedBB;
import com.lastabyss.carbon.nmsutils.WrappedVec3D;

public class TileEntityEndGateway extends WrappedTickingTileEntity {

    private long life = 0L;
    private int teleportCooldown = 0;
    private BlockPosition exitPos;

    @Override
    public void write(NBTTagCompound compound) {
        super.write(compound);
        compound.setLong("Life", life);
        if (exitPos != null) {
            compound.set("ExitPortal", NBTStructSerializer.toNBT(exitPos));
        }
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        life = compound.getLong("Life");
        if (compound.hasKeyOfType("ExitPortal", 10)) {
            exitPos = NBTStructSerializer.getBlockPosition(compound.getCompound("ExitPortal"));
        }
    }

    @Override
    public void tick() {
        boolean justSpawned = this.justSpawned();
        boolean atCooddown = isAtCooldown();
        ++life;
        if (atCooddown) {
            --teleportCooldown;
        } else {
            List<Entity> list = world.a(Entity.class, new NewAxisAlignedBB(getPosition()));
            if (!list.isEmpty()) {
                this.teleport(list.get(0));
            }
        }

        if ((justSpawned != this.justSpawned()) || (atCooddown != isAtCooldown())) {
            this.update();
        }
    }

    public boolean justSpawned() {
        return life < 200L;
    }

    public boolean isAtCooldown() {
        return teleportCooldown > 0;
    }

    @Override
    public Packet<? extends PacketListener> getUpdatePacket() {
        NBTTagCompound data = new NBTTagCompound();
        write(data);
        return new PacketPlayOutTileEntityData(position, 8, data);
    }

    public void afterTeleport() {
        teleportCooldown = 20;
        world.playBlockAction(getPosition(), getBlock(), 1, 0);
        update();
    }

    @Override
    public boolean handleClientInput(int id, int value) {
        if (id == 1) {
            teleportCooldown = 20;
            return true;
        } else {
            return super.handleClientInput(id, value);
        }
    }

    public void teleport(Entity entity) {
        if (!isAtCooldown()) {
            teleportCooldown = 100;
            if ((exitPos == null) && (world.worldProvider instanceof WorldProviderTheEnd)) {
                generateExitPosition();
            }

            if (exitPos != null) {
                BlockPosition position = findExitPosition();
                entity.enderTeleportTo(position.getX() + 0.5D, position.getY() + 1.5D, position.getZ() + 0.5D);
            }

            afterTeleport();
        }
    }

    private BlockPosition findExitPosition() {
        BlockPosition position = findExitPosition(world, exitPos, 5, false);
        return position.up();
    }

    private void generateExitPosition() {
        WrappedVec3D normalized = new WrappedVec3D(getPosition().getX(), 0.0D, getPosition().getZ()).normalize();
        WrappedVec3D mul = normalized.multiply(1024.0D);

        for (int i = 16; (getChunkAtWorldCoords(world, mul).g() > 0) && (i-- > 0); mul = mul.add(normalized.multiply(-16.0D))) {
        }

        for (int i = 16; (getChunkAtWorldCoords(world, mul).g() == 0) && (i-- > 0); mul = mul.add(normalized.multiply(16.0D))) {
        }

        Chunk chunk = getChunkAtWorldCoords(world, mul);
        exitPos = findPlatformPosition(chunk);
        if (exitPos == null) {
            exitPos = new BlockPosition(mul.getX() + 0.5D, 75.0D, mul.getZ() + 0.5D);
            new EndGatewayPlatformGenerator().generate(world, new Random(exitPos.asLong()), exitPos);
        }

        exitPos = findExitPosition(world, exitPos, 16, true);
        exitPos = exitPos.up(10);
        this.generateGateway(exitPos);
        update();
    }

    private static BlockPosition findExitPosition(World world, BlockPosition startPoint, int radius, boolean anyY) {
        BlockPosition result = null;
        for (int x = -radius; x <= radius; ++x) {
            for (int z = -radius; z <= radius; ++z) {
                if ((x != 0) || (z != 0) || anyY) {
                    for (int y = 255; y > (result == null ? 0 : result.getY()); --y) {
                        BlockPosition search = new BlockPosition(startPoint.getX() + x, y, startPoint.getZ() + z);
                        IBlockData blockdata = world.getType(search);
                        if (blockdata.getBlock().u() && (anyY || (blockdata.getBlock() != Blocks.BEDROCK))) {
                            result = search;
                            break;
                        }
                    }
                }
            }
        }

        return result == null ? startPoint : result;
    }

    private static Chunk getChunkAtWorldCoords(World world, WrappedVec3D vec) {
        return world.getChunkAt(MathHelper.floor(vec.getX() / 16.0D), MathHelper.floor(vec.getZ() / 16.0D));
    }

    private static BlockPosition findPlatformPosition(Chunk chunk) {
        BlockPosition chunkStart = new BlockPosition(chunk.locX * 16, 30, chunk.locZ * 16);
        int highestSectY = (chunk.g() + 16) - 1;
        BlockPosition searchEnd = new BlockPosition(((chunk.locX * 16) + 16) - 1, highestSectY, ((chunk.locZ * 16) + 16) - 1);

        BlockPosition platformPosition = null;
        double dist = 0.0D;
        Iterator<BlockPosition> iterator = BlockPositionUtil.allInCube(chunkStart, searchEnd).iterator();

        while (true) {
            BlockPosition nextPosition;
            double sqDist;
            do {
                do {
                    IBlockData blockdata;
                    do {
                        do {
                            if (!iterator.hasNext()) {
                                return platformPosition;
                            }

                            nextPosition = iterator.next();
                            blockdata = chunk.getBlockData(nextPosition);
                        } while (blockdata.getBlock() != Blocks.END_STONE);
                    } while (chunk.getType(nextPosition.up(1)).u());
                } while (chunk.getType(nextPosition.up(2)).u());

                sqDist = BlockPositionUtil.distanceSquaredFromCenter(nextPosition, 0.0D, 0.0D, 0.0D);
            } while ((platformPosition != null) && (sqDist >= dist));

            platformPosition = nextPosition;
            dist = sqDist;
        }
    }

    private void generateGateway(BlockPosition position) {
        new EndGatewayGenerator().generate(world, new Random(), position);
        TileEntity te = world.getTileEntity(position);
        if (te instanceof TileEntityEndGateway) {
            TileEntityEndGateway gateway = (TileEntityEndGateway) te;
            gateway.exitPos = new BlockPosition(getPosition());
            gateway.update();
        }
    }

}
