package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;

/**
 *
 * @author Navid
 */
public class WrappedTileEntity extends TileEntity {

    public void read(NBTTagCompound tag) {
        a(tag);
    }

    public void write(NBTTagCompound tag) {
        b(tag);
    }

    public TileEntity fromNBT(NBTTagCompound tag) {
        return c(tag);
    }

    public Block getBlock() {
        return w();
    }

    public int getMetadata() {
        return u();
    }

    @Override
    public boolean c(final int i, final int j) {
        return handleClientInput(i, j);
    }

    public boolean handleClientInput(int id, int val) {
        return super.c(id, val);
    }

}
