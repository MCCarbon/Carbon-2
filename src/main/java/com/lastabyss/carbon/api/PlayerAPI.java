package com.lastabyss.carbon.api;

import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lastabyss.carbon.network.CarbonPlayerConnection;

public class PlayerAPI {

    public static ItemStack getItemInOffHand(Player player) {
        PlayerConnection connection = getPlayerConnection(player);
        if (connection instanceof CarbonPlayerConnection) {
            net.minecraft.server.v1_8_R3.ItemStack offhanditem = ((CarbonPlayerConnection) connection).getOffHandItem();
            return offhanditem != null ? CraftItemStack.asCraftMirror(offhanditem) : null;
        }
        return null;
    }

    public static boolean setItemInOffHand(Player player, ItemStack stack) {
        PlayerConnection connection = getPlayerConnection(player);
        if (connection instanceof CarbonPlayerConnection) {
            ((CarbonPlayerConnection) connection).setOffHandItem(CraftItemStack.asNMSCopy(stack));
            return true;
        }
        return false;
    }

    private static PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }

}
