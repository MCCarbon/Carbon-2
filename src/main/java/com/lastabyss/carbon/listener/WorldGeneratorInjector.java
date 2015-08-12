package com.lastabyss.carbon.listener;

import net.minecraft.server.v1_8_R3.IChunkProvider;
import net.minecraft.server.v1_8_R3.WorldServer;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.generator.NormalChunkGenerator;
import org.bukkit.craftbukkit.v1_8_R3.generator.SkyLandsChunkGenerator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import com.lastabyss.carbon.generators.end.NewChunkProviderTheEnd;
import com.lastabyss.carbon.utils.ReflectionUtils;

public class WorldGeneratorInjector implements Listener {

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        WorldServer nmsworld = ((CraftWorld) event.getWorld()).getHandle();
        IChunkProvider genprovider = nmsworld.chunkProviderServer.chunkProvider;
        if (genprovider instanceof SkyLandsChunkGenerator) {
            ReflectionUtils.setFieldValue(NormalChunkGenerator.class, "provider", genprovider, new NewChunkProviderTheEnd(nmsworld, nmsworld.getSeed()));
        }
    }

}
