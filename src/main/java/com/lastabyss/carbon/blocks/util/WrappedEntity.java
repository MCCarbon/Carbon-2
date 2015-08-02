package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;

/**
 *
 * @author Navid
 */
public abstract class WrappedEntity extends Entity {

    public WrappedEntity(World world) {
        super(world);
    }

}
