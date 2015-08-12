package com.lastabyss.carbon.nmsutils;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;

public class NewAxisAlignedBB extends AxisAlignedBB {

    public NewAxisAlignedBB(BlockPosition position) {
        super(position.getX(), position.getY(), position.getZ(), position.getX() + 1, position.getY() + 1, position.getZ() + 1);
    }

}
