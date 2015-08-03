package com.lastabyss.carbon.network;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.lastabyss.carbon.network.packets.CarbonPacketPlayInUse;
import com.lastabyss.carbon.types.EnumUsedHand;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerConnectionUtils;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.WorldSettings;

public class CarbonPlayerConnection extends PlayerConnection {

    private ItemStack offhandItem;

    public CarbonPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }

    public void handle(CarbonPacketPlayInUse usepacket) {
        PlayerConnectionUtils.ensureMainThread(usepacket, this, this.player.u());
        ItemStack itemstack = usepacket.getHand() == EnumUsedHand.MAIN_HAND ? this.player.inventory.getItemInHand() : offhandItem;
        if (this.player.dead) {
            return;
        }
        final float pitch = this.player.pitch;
        final float yaw = this.player.yaw;
        final double x = this.player.locX;
        final double y = this.player.locY + this.player.getHeadHeight();
        final double z = this.player.locZ;
        final Vec3D posVec = new Vec3D(x, y, z);
        final float f3 = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f4 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f5 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f6 = MathHelper.sin(-pitch * 0.017453292f);
        final float f7 = f4 * f5;
        final float f8 = f3 * f5;
        final double d3 = (this.player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE) ? 5.0 : 4.5;
        final Vec3D vec3d2 = posVec.add(f7 * d3, f6 * d3, f8 * d3);
        final MovingObjectPosition movingobjectposition = this.player.world.rayTrace(posVec, vec3d2, false);
        boolean cancelled = false;
        if (movingobjectposition == null || movingobjectposition.type != MovingObjectPosition.EnumMovingObjectType.BLOCK) {
            final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_AIR, itemstack);
            cancelled = (event.useItemInHand() == PlayerInteractEvent.Result.DENY);
        } else if (this.player.playerInteractManager.firedInteract) {
            this.player.playerInteractManager.firedInteract = false;
            cancelled = this.player.playerInteractManager.interactResult;
        } else {
            final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_BLOCK, movingobjectposition.a(), movingobjectposition.direction, itemstack, true);
            cancelled = (event.useItemInHand() == PlayerInteractEvent.Result.DENY);
        }
        if (!cancelled) {
            this.player.playerInteractManager.useItem(this.player, this.player.world, itemstack);
        }
        validateOffHand();
    }

    private void validateOffHand() {
        if (offhandItem != null && offhandItem.count <= 0) {
            offhandItem = null;
        }
    }

    public ItemStack getOffHandItem() {
        return offhandItem;
    }

}
