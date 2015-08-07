package com.lastabyss.carbon.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.ChatComponentText;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lastabyss.carbon.api.BossBar.BossBarColor;
import com.lastabyss.carbon.api.BossBar.BossBarDivider;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayOutBossBar;

public class BossBarAPI implements Listener {

    private static final Map<Player, List<BossBar>> bars = new HashMap<>();

    /**
     * Sets the message above the BossBar
     * for all players with one.
     * @param message
     */
    public static void setMessage(String message, int index) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setMessage(player, message, index);
        }
    }

    /**
     * Sets the message for a BossBar for a specific player
     * where the index is the location on the screen starting
     * at 0. BossBars order from top to bottom.
     * @param player
     * @param message
     * @param index
     */
    public static void setMessage(Player player, String message, int index) {
        if (has(player)) {
            setMessage(player, message, getBars(player).get(index));
            return;
        }
        setMessage(player, message, create(player, message));
    }

    /**
     * Creates a new BossBar with a message.
     * @param player
     * @param message
     * @return
     */
    public static BossBar create(Player player, String message) {
        BossBar bar = new BossBar(UUID.randomUUID(), new ChatComponentText(message), BossBarColor.RED, BossBarDivider.PROGRESS, new CarbonPacketPlayOutBossBar(), false, false);
        if (has(player)) {
            getBars(player).add(bar);
            return bar;
        }
        bars.put(player, new ArrayList<>(Arrays.asList(bar)));
        return bar;
    }

    /**
     * Sets a message for a specific BossBar.
     * @param player
     * @param message
     * @param bar
     */
    public static void setMessage(Player player, String message, BossBar bar) {
        bar.setMessage(message);
        bar.sendPacket(player);
    }

    /**
     * Sets the health for a BossBar to a specific player where
     * the index is the location of the BossBar on the screen. BossBars
     * order from top to bottom.
     * @param player
     * @param health
     * @param index
     */
    public static void setHealth(Player player, float health, int index) {
        if (has(player)) {
            setHealth(player, health, getBars(player).get(index));
            return;
        }
        setHealth(player, health, create(player, ""));
    }

    /**
     * Sets the health of a BossBar shown to a specific player.
     * @param player
     * @param health
     * @param bar
     */
    public static void setHealth(Player player, float health, BossBar bar) {
        bar.setHealth(health);
        bar.sendPacket(player);
    }

    /**
     * Sets the color for a BossBar to a specific player where
     * the index is the location of the BossBar on the screen. BossBars
     * order from top to bottom.
     * @param player
     * @param color
     * @param index
     */
    public static void setColor(Player player, BossBarColor color, int index) {
        if (has(player)) {
            setColor(player, color, getBars(player).get(index));
            return;
        }
        setColor(player, color, create(player, ""));
    }

    /**
     * Sets the color for a BossBar shown to a specific player.
     * @param player
     * @param color
     * @param bar
     */
    public static void setColor(Player player, BossBarColor color, BossBar bar) {
        bar.setColor(color);
        bar.sendPacket(player);
    }

    /**
     * Sets the divider of a BossBar shown to a specific player
     * where the index is the location of the BossBar on the screen.
     * BossBars order from top to bottom.
     * @param player
     * @param divider
     * @param index
     */
    public static void setDivider(Player player, BossBarDivider divider, int index) {
        if (has(player)) {
            setDivider(player, divider, getBars(player).get(index));
            return;
        }
        setDivider(player, divider, create(player, ""));
    }

    /**
     * Changes the divider of a BossBar shown to a specific player.
     * @param player
     * @param divider
     * @param bar
     */
    public static void setDivider(Player player, BossBarDivider divider, BossBar bar) {
        bar.setDivider(divider);
        bar.sendPacket(player);
    }

    /**
     * Removes all BossBars shown to a player.
     * @param player
     */
    public static void removeBars(Player player) {
        if (getBars(player) == null) {
            return;
        }
        for (BossBar bar : getBars(player)) {
            remove(player, bar);
        }
        bars.remove(player);
    }

    /**
     * Removes a boss bar from a player
     * where the index is the location on the screen.
     * BossBars are positioned top to bottom.
     * @param player
     * @param index
     */
    public static void remove(Player player, int index) {
        if (has(player)) {
            BossBar bar = getBars(player).get(index);
            bar.removeBar(player);
            bars.get(player).remove(bar);
        }
    }

    /**
     * Removes a boss bar from a player.
     * @param player
     * @param bar
     */
    public static void remove(Player player, BossBar bar) {
        bar.removeBar(player);
        bars.get(player).remove(bar);
    }

    /**
     * Returns true if the player has any
     * BossBars displaying.
     * @param player
     * @return
     */
    public static boolean has(Player player) {
        return getBars(player) != null;
    }

    /**
     * Returns a direct list of all BossBars a player
     * currently has.
     * @param player
     * @return
     */
    public static List<BossBar> getBars(Player player) {
        return bars.get(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        removeBars(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        removeBars(event.getPlayer());
    }

}
