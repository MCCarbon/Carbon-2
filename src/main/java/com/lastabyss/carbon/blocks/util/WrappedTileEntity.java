package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;

/**
 *
 * @author Navid
 */
public class WrappedTileEntity extends TileEntity {

    public void write(NBTTagCompound tag) {
        a(tag);
    }

    public void read(NBTTagCompound tag) {
        b(tag);
    }

    public TileEntity fromNBT(NBTTagCompound tag) {
        return c(tag);
    }

    public Block getBlock() {
        return e;
    }

    public void tick() {
        update();
    }

}
