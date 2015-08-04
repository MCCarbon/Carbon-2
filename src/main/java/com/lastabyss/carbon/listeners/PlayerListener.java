package com.lastabyss.carbon.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.Injector;

public class PlayerListener implements Listener {

    public PlayerListener(Carbon plugin) {
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerRightClickGrass(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.GRASS) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        Material material = event.getItem().getType();
        switch (material) {
            case WOOD_SPADE:
            case STONE_SPADE:
            case IRON_SPADE:
            case GOLD_SPADE:
            case DIAMOND_SPADE: {
                break;
            }
            default: {
                return;
            }
        }
        short durability = (short) (event.getItem().getDurability() + 1);
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            //TODO: check if setting more that max durability do break item
            event.getItem().setDurability(durability);
        }
        event.getClickedBlock().setType(Injector.GRASS_PATH);
        event.getPlayer().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.DIG_GRAVEL, 1, 1);
    }

}
