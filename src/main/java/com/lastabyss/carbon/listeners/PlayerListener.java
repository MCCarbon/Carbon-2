package com.lastabyss.carbon.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.lastabyss.carbon.Carbon;

public class PlayerListener implements Listener {
	
	private Carbon plugin;
	
	public PlayerListener(Carbon plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerRightClickGrass(PlayerInteractEvent event) {
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if(event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.GRASS) {
			return;
		}
		if(event.getPlayer().getItemInHand() == null) {
			return;
		}
		Material material = event.getPlayer().getItemInHand().getType();
		if(material != Material.WOOD_SPADE && material != Material.IRON_SPADE && material != Material.GOLD_SPADE && material != Material.DIAMOND_SPADE) {
			return;
		}
		short durability = (short) (event.getPlayer().getItemInHand().getDurability()+1);
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if(durability == event.getPlayer().getItemInHand().getType().getMaxDurability()) {
				event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
			} else {
				event.getPlayer().getInventory().getItemInHand().setDurability(durability);
			}
		}
		event.getClickedBlock().setType(Material.valueOf("GRASS_PATH"));
	}

}
