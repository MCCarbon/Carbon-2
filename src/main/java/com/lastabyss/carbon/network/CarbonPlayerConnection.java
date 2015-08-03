package com.lastabyss.carbon.network;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerConnectionUtils;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntitySign;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.NumberConversions;

import com.lastabyss.carbon.network.packets.CarbonPacketPlayInBlockPlace;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInUpdateSign;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayInUseItem;
import com.lastabyss.carbon.types.EnumUsedHand;

public class CarbonPlayerConnection extends PlayerConnection {

    private MinecraftServer minecraftServer;
    
    public CarbonPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
        minecraftServer = minecraftserver;
    }

    private ItemStack offhandItem;

    @Override
    public void a(PacketPlayInBlockDig packet) {
        PlayerConnectionUtils.ensureMainThread(packet, this, player.u());
        if (packet.c() != NetworkInjector.SWAP_HELD_ITEMS) {
            super.a(packet);
            return;
        }
        ItemStack temp = offhandItem;
        offhandItem = player.inventory.getItemInHand();
        player.inventory.setItem(player.inventory.itemInHandIndex, temp);
        sendPacket(new PacketPlayOutSetSlot(player.activeContainer.windowId, player.activeContainer.c.size(), offhandItem));
        sendPacket(new PacketPlayOutSetSlot(player.activeContainer.windowId, player.activeContainer.getSlot(player.inventory, player.inventory.itemInHandIndex).rawSlotIndex, player.inventory.getItemInHand()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void a(PacketPlayInWindowClick packet) {
        PlayerConnectionUtils.ensureMainThread(packet, this, player.u());
        int containersize = player.activeContainer.c.size();
        if (packet.b() != containersize) {
            super.a(packet);
            return;
        }
        if (player.dead) {
            return;
        }
        player.resetIdleTimer();
        if (player.activeContainer.windowId == packet.a() && player.activeContainer.c(player)) {
            int mode = packet.f();
            int button = packet.c();
            switch (mode) {
                case 0: { //Left, Right click
                    ItemStack carried = player.inventory.getCarried();
                    if ((offhandItem == null || offhandItem.count <= 0) && (carried == null || carried.count <= 0)) {
                        break;
                    }
                    if (button == 0) {
                        if (offhandItem == null) {
                            offhandItem = carried;
                            player.inventory.setCarried(null);
                        } else if (carried == null) {
                            player.inventory.setCarried(offhandItem);
                            offhandItem = null;
                        } else if (offhandItem.getItem() != carried.getItem()) {
                            player.inventory.setCarried(offhandItem);
                            offhandItem = carried;
                        } else {
                            int addamount = Math.min(carried.count, offhandItem.getMaxStackSize() - offhandItem.count);
                            offhandItem.count += addamount;
                            carried.count -= addamount;
                        }
                    } else if (button == 1) {
                        if (offhandItem == null) {
                            offhandItem = carried.cloneItemStack();
                            offhandItem.count = 1;
                            carried.count -= 1;
                        } else if (carried == null) {
                            carried = offhandItem.cloneItemStack();
                            offhandItem.count /= 2;
                            carried.count -= offhandItem.count;
                            player.inventory.setCarried(carried);
                        } else if (offhandItem.getItem() != carried.getItem()) {
                            player.inventory.setCarried(offhandItem);
                            offhandItem = carried;
                        } else {
                            int addamount = Math.min(1, offhandItem.getMaxStackSize() - offhandItem.count);
                            offhandItem.count += addamount;
                            carried.count -= addamount;
                        }
                    }
                    if (carried != null && carried.count == 0) {
                        player.inventory.setCarried(null);
                    }
                    break;
                }
                case 1: { //Shift left/right click
                    if (offhandItem == null || offhandItem.count <= 0) {
                        break;
                    }
                    int emptySlot = -1;
                    for (int i = 0; i < 9; i++) {
                        ItemStack item = player.inventory.items[i];
                        if (item == null) {
                            if (emptySlot == -1) {
                                emptySlot = i;
                            }
                            continue;
                        }
                        if (item.getItem() == offhandItem.getItem()) {
                            int addamount = Math.min(offhandItem.count, item.getMaxStackSize() - item.count);
                            item.count += addamount;
                            offhandItem.count -= addamount;
                        }
                        if (offhandItem.count == 0) {
                            break;
                        }
                    }
                    if (offhandItem.count > 0 && emptySlot != -1) {
                        player.inventory.items[emptySlot] = offhandItem;
                        offhandItem = null;
                    }
                    break;
                }
                default: {
                    break;
                }
            }
            if (offhandItem != null && offhandItem.count == 0) {
                offhandItem = null;
            }
            player.getBukkitEntity().updateInventory();
        }
    }

    @Override
    public void a(PacketPlayInBlockPlace packet) {
        PlayerConnectionUtils.ensureMainThread(packet, this, player.u());
        if (!(packet instanceof CarbonPacketPlayInBlockPlace)) {
            super.a(packet);
            return;
        }
        CarbonPacketPlayInBlockPlace carbonpacket = (CarbonPacketPlayInBlockPlace) packet;
        final WorldServer worldserver = minecraftServer.getWorldServer(player.dimension);
        if (player.dead) {
            return;
        }
        ItemStack itemstack = carbonpacket.getHand() == EnumUsedHand.MAIN_HAND ? player.inventory.getItemInHand() : offhandItem;
        boolean flag = false;
        final BlockPosition blockposition = carbonpacket.a();
        final EnumDirection enumdirection = EnumDirection.fromType1(carbonpacket.getFace());
        player.resetIdleTimer();
        if (carbonpacket.getFace() == 255) {
            if (itemstack == null) {
                return;
            }
            final float f1 = player.pitch;
            final float f2 = player.yaw;
            final double d0 = player.locX;
            final double d = player.locY + player.getHeadHeight();
            final double d2 = player.locZ;
            final Vec3D vec3d = new Vec3D(d0, d, d2);
            final float f3 = MathHelper.cos((-f2 * 0.017453292f) - 3.1415927f);
            final float f4 = MathHelper.sin((-f2 * 0.017453292f) - 3.1415927f);
            final float f5 = -MathHelper.cos(-f1 * 0.017453292f);
            final float f6 = MathHelper.sin(-f1 * 0.017453292f);
            final float f7 = f4 * f5;
            final float f8 = f3 * f5;
            final double d3 = (player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE) ? 5.0 : 4.5;
            final Vec3D vec3d2 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
            final MovingObjectPosition movingobjectposition = player.world.rayTrace(vec3d, vec3d2, false);
            boolean cancelled = false;
            if ((movingobjectposition == null) || (movingobjectposition.type != MovingObjectPosition.EnumMovingObjectType.BLOCK)) {
                final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, itemstack);
                cancelled = (event.useItemInHand() == PlayerInteractEvent.Result.DENY);
            } else if (player.playerInteractManager.firedInteract) {
                player.playerInteractManager.firedInteract = false;
                cancelled = player.playerInteractManager.interactResult;
            } else {
                final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, movingobjectposition.a(), movingobjectposition.direction, itemstack, true);
                cancelled = (event.useItemInHand() == PlayerInteractEvent.Result.DENY);
            }
            if (!cancelled) {
                useItem(itemstack, carbonpacket.getHand());
            }
        } else if ((blockposition.getY() >= (minecraftServer.getMaxBuildHeight() - 1)) && ((enumdirection == EnumDirection.UP) || (blockposition.getY() >= minecraftServer.getMaxBuildHeight()))) {
            final ChatMessage chatmessage = new ChatMessage("build.tooHigh", new Object[] { minecraftServer.getMaxBuildHeight() });
            chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
            player.playerConnection.sendPacket(new PacketPlayOutChat(chatmessage));
            flag = true;
        } else {
            final Location eyeLoc = getPlayer().getEyeLocation();
            final double reachDistance = NumberConversions.square(eyeLoc.getX() - blockposition.getX()) + NumberConversions.square(eyeLoc.getY() - blockposition.getY()) + NumberConversions.square(eyeLoc.getZ() - blockposition.getZ());
            if (reachDistance > ((getPlayer().getGameMode() == GameMode.CREATIVE) ? 49 : 36)) {
                return;
            }
            if (!worldserver.getWorldBorder().a(blockposition)) {
                return;
            }
            if ((player.e(blockposition.getX() + 0.5, blockposition.getY() + 0.5, blockposition.getZ() + 0.5) < 64.0) && !minecraftServer.a(worldserver, blockposition, player) && worldserver.getWorldBorder().a(blockposition)) {
                player.playerInteractManager.interact(player, worldserver, itemstack, blockposition, enumdirection, carbonpacket.d(), carbonpacket.e(), carbonpacket.f());
            }
            flag = true;
        }
        if (flag) {
            player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
            player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition.shift(enumdirection)));
        }
        validateHandItems();
    }

    public void handle(CarbonPacketPlayInUseItem packet) {
        PlayerConnectionUtils.ensureMainThread(packet, this, player.u());
        ItemStack itemstack = packet.getHand() == EnumUsedHand.MAIN_HAND ? player.inventory.getItemInHand() : offhandItem;
        if (player.dead) {
            return;
        }
        player.resetIdleTimer();
        if (itemstack == null) {
            return;
        }
        final float pitch = player.pitch;
        final float yaw = player.yaw;
        final double x = player.locX;
        final double y = player.locY + player.getHeadHeight();
        final double z = player.locZ;
        final Vec3D posVec = new Vec3D(x, y, z);
        final float f3 = MathHelper.cos((-yaw * 0.017453292f) - 3.1415927f);
        final float f4 = MathHelper.sin((-yaw * 0.017453292f) - 3.1415927f);
        final float f5 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f6 = MathHelper.sin(-pitch * 0.017453292f);
        final float f7 = f4 * f5;
        final float f8 = f3 * f5;
        final double d3 = (player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE) ? 5.0 : 4.5;
        final Vec3D vec3d2 = posVec.add(f7 * d3, f6 * d3, f8 * d3);
        final MovingObjectPosition movingobjectposition = player.world.rayTrace(posVec, vec3d2, false);
        boolean cancelled = false;
        if ((movingobjectposition == null) || (movingobjectposition.type != MovingObjectPosition.EnumMovingObjectType.BLOCK)) {
            final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, itemstack);
            cancelled = (event.useItemInHand() == PlayerInteractEvent.Result.DENY);
        } else if (player.playerInteractManager.firedInteract) {
            player.playerInteractManager.firedInteract = false;
            cancelled = player.playerInteractManager.interactResult;
        } else {
            final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, movingobjectposition.a(), movingobjectposition.direction, itemstack, true);
            cancelled = (event.useItemInHand() == PlayerInteractEvent.Result.DENY);
        }
        if (!cancelled) {
            useItem(itemstack, packet.getHand());
        }
        validateHandItems();
    }

    private boolean useItem(ItemStack itemstack, EnumUsedHand hand) {
        if (player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.SPECTATOR) {
            return false;
        }
        final int count = itemstack.count;
        final int data = itemstack.getData();
        final ItemStack afterUse = itemstack.a(player.world, player);
        if (afterUse == itemstack && (afterUse == null || (afterUse.count == count && afterUse.l() <= 0 && afterUse.getData() == data))) {
            return false;
        }
        if (hand == EnumUsedHand.MAIN_HAND) {
            player.inventory.items[player.inventory.itemInHandIndex] = afterUse;
        } else {
            offhandItem = afterUse;
        }
        if (player.playerInteractManager.isCreative()) {
            afterUse.count = count;
            if (afterUse.e()) {
                afterUse.setData(data);
            }
        }
        if (!player.bS()) {
            player.updateInventory(player.defaultContainer);
        }
        return true;
    }

    private void validateHandItems() {
        ItemStack mainhandItem = player.inventory.getItemInHand();
        if ((mainhandItem != null) && (mainhandItem.count == 0)) {
            player.inventory.items[player.inventory.itemInHandIndex] = null;
            mainhandItem = null;
        }
        if ((offhandItem != null) && (offhandItem.count == 0)) {
            offhandItem = null;
        }
    }

    public ItemStack getOffHandItem() {
        return offhandItem;
    }

	public void handle(CarbonPacketPlayInUpdateSign carbonPacketPlayInUpdateSign) {
		//class_fh.a(carbonPacketPlayInUpdateSign, this, player.u());
		player.resetIdleTimer();
		WorldServer worldServer = minecraftServer.getWorldServer(player.dimension);
		BlockPosition blockPosition = carbonPacketPlayInUpdateSign.getPosition();
		if (worldServer.isLoaded(blockPosition)) {
			TileEntity tileEntity = worldServer.getTileEntity(blockPosition);
			if (!(tileEntity instanceof TileEntitySign)) {
				return;
			}

			TileEntitySign signTileEntity = (TileEntitySign) tileEntity;
			if (!signTileEntity.b() || (signTileEntity.c() != player)) {
				minecraftServer.info("Player " + player.getName() + " just tried to change non-editable sign");
				return;
			}

			IChatBaseComponent[] lines = carbonPacketPlayInUpdateSign.getLines();

			for (int i = 0; i < lines.length; ++i) {
				signTileEntity.lines[i] = new ChatComponentText(ChatColor.stripColor(lines[i].c()));
			}

			signTileEntity.update();
			worldServer.notify(blockPosition);
		}
		
	}

}
