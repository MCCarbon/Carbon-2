package com.lastabyss.carbon.generators;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public abstract class WrappedWorldGenerator extends WorldGenerator {

    protected void setTypeAndData(final World world, final BlockPosition blockPosition, final IBlockData blockData) {
        a(world, blockPosition, blockData);
    }

}
