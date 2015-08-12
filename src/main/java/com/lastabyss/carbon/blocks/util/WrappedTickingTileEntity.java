package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;

public abstract class WrappedTickingTileEntity extends WrappedTileEntity implements IUpdatePlayerListBox {

    @Override
    public void c() {
        tick();
    }

    public abstract void tick();

}
