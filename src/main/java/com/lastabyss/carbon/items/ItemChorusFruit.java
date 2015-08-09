package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.ItemFood;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Location;
import org.bukkit.event.entity.EntityTeleportEvent;

public class ItemChorusFruit extends ItemFood {

    public ItemChorusFruit() {
        super(4, 0.3F, false);
    }

    @Override
    public ItemStack b(ItemStack itemstack, World world, EntityHuman player) {
        ItemStack returnitem = super.b(itemstack, world, player);
        for (int i = 0; i < 16; ++i) {
            double x = player.locX + ((player.bc().nextDouble() - 0.5D) * 64.0D);
            double y = player.locY + (player.bc().nextInt(64) - 32);
            double z = player.locZ + ((player.bc().nextDouble() - 0.5D) * 64.0D);
            if (attemptEnderTeleport(player, x, y, z)) {
                break;
            }
        }
        return returnitem;
    }

    private boolean attemptEnderTeleport(EntityHuman entity, final double x, final double y, final double z) {
        final double prevX = entity.locX;
        final double prevY = entity.locY;
        final double prevZ = entity.locZ;
        entity.locX = x;
        entity.locY = y;
        entity.locZ = z;
        boolean success = false;
        BlockPosition blockposition = new BlockPosition(entity.locX, entity.locY, entity.locZ);
        if (entity.world.isLoaded(blockposition)) {
            boolean foundPos = false;
            while (!foundPos && (blockposition.getY() > 0)) {
                final BlockPosition blockposition2 = blockposition.down();
                final Block block = entity.world.getType(blockposition2).getBlock();
                if (block.getMaterial().isSolid()) {
                    foundPos = true;
                } else {
                    --entity.locY;
                    blockposition = blockposition2;
                }
            }
            if (foundPos) {
                final EntityTeleportEvent teleport = new EntityTeleportEvent(entity.getBukkitEntity(), new Location(entity.world.getWorld(), prevX, prevY, prevZ), new Location(entity.world.getWorld(), entity.locX, entity.locY, entity.locZ));
                entity.world.getServer().getPluginManager().callEvent(teleport);
                if (teleport.isCancelled()) {
                    return false;
                }
                final Location to = teleport.getTo();
                entity.enderTeleportTo(to.getX(), to.getY(), to.getZ());
                if (entity.world.getCubes(entity, entity.getBoundingBox()).isEmpty() && !entity.world.containsLiquid(entity.getBoundingBox())) {
                    success = true;
                }
            }
        }
        if (!success) {
            entity.setPosition(prevX, prevY, prevZ);
            return false;
        }
        final short count = 128;
        for (int i = 0; i < count; ++i) {
            final double d6 = i / (count - 1.0);
            final float spreadX = (entity.bc().nextFloat() - 0.5f) * 0.2f;
            final float spreadY = (entity.bc().nextFloat() - 0.5f) * 0.2f;
            final float spreadZ = (entity.bc().nextFloat() - 0.5f) * 0.2f;
            final double pX = prevX + ((entity.locX - prevX) * d6) + ((entity.bc().nextDouble() - 0.5) * entity.width * 2.0);
            final double pY = prevY + ((entity.locY - prevY) * d6) + (entity.bc().nextDouble() * entity.length);
            final double pZ = prevZ + ((entity.locZ - prevZ) * d6) + ((entity.bc().nextDouble() - 0.5) * entity.width * 2.0);
            entity.world.addParticle(EnumParticle.PORTAL, pX, pY, pZ, spreadX, spreadY, spreadZ, new int[0]);
        }
        entity.world.makeSound(prevX, prevY, prevZ, "mob.endermen.portal", 1.0f, 1.0f);
        entity.makeSound("mob.endermen.portal", 1.0f, 1.0f);
        return true;
    }

}
