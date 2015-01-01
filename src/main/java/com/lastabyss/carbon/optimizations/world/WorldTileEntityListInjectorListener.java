package com.lastabyss.carbon.optimizations.world;

import net.minecraft.server.v1_8_R1.WorldServer;

import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import com.lastabyss.carbon.utils.Utilities;

public class WorldTileEntityListInjectorListener implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		WorldServer nmsWorld = ((CraftWorld) event.getWorld()).getHandle();
		try {
			Utilities.setFinalField(nmsWorld.getClass().getField("tileEntityList"), nmsWorld, new OptimizedTileEntityList());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
