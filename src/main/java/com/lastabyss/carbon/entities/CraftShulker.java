package com.lastabyss.carbon.entities;

import net.minecraft.server.v1_8_R3.EntityGolem;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftGolem;

/**
 * @author Navid
 */
public class CraftShulker extends CraftGolem {

    public CraftShulker(CraftServer server, EntityGolem entity) {
        super(server, entity);
    }



    @Override
    public EntityShulker getHandle() {
        return (EntityShulker)entity;
    }
}
