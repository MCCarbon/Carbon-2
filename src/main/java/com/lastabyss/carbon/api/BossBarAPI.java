package com.lastabyss.carbon.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.ChatComponentText;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayOutBossBar;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayOutBossBar.EnumBossBarAction;
import com.lastabyss.carbon.types.Bossbar;
import com.lastabyss.carbon.types.Bossbar.EnumBossbarColor;
import com.lastabyss.carbon.types.Bossbar.EnumBossbarDivider;
import com.lastabyss.carbon.utils.Utils;

public class BossBarAPI implements Listener {
	
	private static Map<Player, ArrayList<Bossbar>> bars;
	
	public BossBarAPI(Carbon carbon) {
		BossBarAPI.bars = new HashMap<Player, ArrayList<Bossbar>>();
		carbon.getServer().getPluginManager().registerEvents(this, carbon);
	}
	
	public static void setMessage(String message) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			setMessage(player, message);
		}
	}
	
	public static void setMessage(Player player, String message) {
		if(hasBar(player)) {
			setMessage(player, message, getBossBars(player).get(0));
			return;
		}
		setMessage(player, message, getNewBossBar(player, message));
	}
	
	public static Bossbar getNewBossBar(Player player, String message) {
		Bossbar bar = new Bossbar(UUID.randomUUID(), new ChatComponentText(message), EnumBossbarColor.RED, EnumBossbarDivider.PROGRESS, false, false);
		if(hasBar(player)) {
			getBossBars(player).add(bar);
			return bar;
		}
		bars.put(player, new ArrayList<Bossbar>(Arrays.asList(bar)));
		return bar;
	}
	
	public static void setMessage(Player player, String message, Bossbar bar) {
		Utils.sendPacket(player, new CarbonPacketPlayOutBossBar(EnumBossBarAction.UPDATE_TITLE, bar));
	}
	
	public static void setHealth(Player player, float health) {
		if(hasBar(player)) {
			setHealth(player, health, getBossBars(player).get(0));
			return;	
		}
		setHealth(player, health, getNewBossBar(player, ""));
	}
	
	public static void setHealth(Player player, float health, Bossbar bar) {
		bar.setHealth(health);
		Utils.sendPacket(player, new CarbonPacketPlayOutBossBar(EnumBossBarAction.UPDATE_HEALTH, getBossBars(player).get(0)));
	}
	
	public static void setColor(Player player, EnumBossbarColor color) {
		if(hasBar(player)) {
			setColor(player, color, getBossBars(player).get(0));
			return;
		}
		setColor(player, color, getNewBossBar(player, ""));
	}
	
	public static void setColor(Player player, EnumBossbarColor color, Bossbar bar) {
		bar.setColor(color);
		Utils.sendPacket(player, new CarbonPacketPlayOutBossBar(EnumBossBarAction.UPDATE_STYLE, bar));
	}
	
	public static void setDivider(Player player, EnumBossbarDivider divider) {
		if(hasBar(player)) {
			setDivider(player, divider, getBossBars(player).get(0));
			return;
		}
		setDivider(player, divider, getNewBossBar(player, ""));
	}
	
	public static void setDivider(Player player, EnumBossbarDivider divider, Bossbar bar) {
		bar.setDivider(divider);
		Utils.sendPacket(player, new CarbonPacketPlayOutBossBar(EnumBossBarAction.UPDATE_STYLE, bar));
	}
	
	public static void removeBars(Player player) {
		if(getBossBars(player) == null) {
			return;
		}
		for(Bossbar bar : getBossBars(player)) {
			removeBar(player, bar);
		}
		bars.remove(player);
	}
	
	public static void removeBar(Player player, Bossbar bar) {
		Utils.sendPacket(player, new CarbonPacketPlayOutBossBar(EnumBossBarAction.REMOVE, bar));
		bar.destroy();
		bars.get(player).remove(bar);
	}
	
	public static boolean hasBar(Player player) {
		return getBossBars(player) != null;
	}
	
	public static ArrayList<Bossbar> getBossBars(Player player) {
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
