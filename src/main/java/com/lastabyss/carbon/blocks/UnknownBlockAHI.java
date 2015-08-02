package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.blocks.util.WrappedBlock;

import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.Material;

public class UnknownBlockAHI extends WrappedBlock {

    public static final BlockStateDirection FACING = BlockStateDirection.of("facing");

    protected UnknownBlockAHI(Material material) {
        super(material);
    }

}
