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

import com.lastabyss.carbon.network.packets.CarbonPacketPlayOutBossBar;
import com.lastabyss.carbon.types.Bossbar;
import com.lastabyss.carbon.types.Bossbar.BossBarColor;
import com.lastabyss.carbon.types.Bossbar.BossBarDivider;
import org.bukkit.plugin.PluginManager;

/**
 * BossBarAPI sends the appropriate packets to players in order
 * to display BossBars.
 */
public class BossBarAPI implements Listener {

	private static Map<Player, List<Bossbar>> bars;

    /**
     * Static initializer block instantiates when the plugin is loaded.
     */
    static {
       Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("Carbon2"), new Runnable() {
           @Override
           public void run() {
               new BossBarAPI();
           }
       });
    }

	protected BossBarAPI() {
		BossBarAPI.bars = new HashMap<>();
        PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, pm.getPlugin("Carbon2"));
	}

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
		if (hasBossBar(player)) {
			setMessage(player, message, getBossBars(player).get(index));
			return;
		}
		setMessage(player, message, createBossBar(player, message));
	}

    /**
     * Creates a new BossBar with a message.
     * @param player
     * @param message
     * @return
     */
	public static Bossbar createBossBar(Player player, String message) {
		Bossbar bar = new Bossbar(UUID.randomUUID(), new ChatComponentText(message), BossBarColor.RED, BossBarDivider.PROGRESS, new CarbonPacketPlayOutBossBar(), false, false);
		if (hasBossBar(player)) {
			getBossBars(player).add(bar);
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
	public static void setMessage(Player player, String message, Bossbar bar) {
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
		if (hasBossBar(player)) {
			setHealth(player, health, getBossBars(player).get(index));
			return;
		}
		setHealth(player, health, createBossBar(player, ""));
	}

    /**
     * Sets the health of a BossBar shown to a specific player.
     * @param player
     * @param health
     * @param bar
     */
	public static void setHealth(Player player, float health, Bossbar bar) {
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
		if (hasBossBar(player)) {
			setColor(player, color, getBossBars(player).get(index));
			return;
		}
		setColor(player, color, createBossBar(player, ""));
	}

    /**
     * Sets the color for a BossBar shown to a specific player.
     * @param player
     * @param color
     * @param bar
     */
	public static void setColor(Player player, BossBarColor color, Bossbar bar) {
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
		if (hasBossBar(player)) {
			setDivider(player, divider, getBossBars(player).get(index));
			return;
		}
		setDivider(player, divider, createBossBar(player, ""));
	}

    /**
     * Changes the divider of a BossBar shown to a specific player.
     * @param player
     * @param divider
     * @param bar
     */
	public static void setDivider(Player player, BossBarDivider divider, Bossbar bar) {
		bar.setDivider(divider);
		bar.sendPacket(player);
	}

    /**
     * Removes all BossBars shown to a player.
     * @param player
     */
	public static void removeBossBars(Player player) {
		if (hasBossBar(player)) return;
		for (Bossbar bar : getBossBars(player)) {
			removeBar(player, bar);
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
    public static void removeBar(Player player, int index) {
        if (hasBossBar(player)) {
            Bossbar bar = getBossBars(player).get(index);
            bar.removeBar(player);
            bar.destroy();
            bars.get(player).remove(bar);
        }
    }

    /**
     * Removes a boss bar from a player.
     * @param player
     * @param bar
     */
	public static void removeBar(Player player, Bossbar bar) {
		bar.removeBar(player);
		bar.destroy();
		bars.get(player).remove(bar);
	}

    /**
     * Returns true if the player has any
     * BossBars displaying.
     * @param player
     * @return
     */
	public static boolean hasBossBar(Player player) {
		return getBossBars(player) != null;
	}

    /**
     * Returns a direct list of all BossBars a player
     * currently has.
     * @param player
     * @return
     */
	public static List<Bossbar> getBossBars(Player player) {
		return bars.get(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		removeBossBars(event.getPlayer());
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		removeBossBars(event.getPlayer());
	}

}
