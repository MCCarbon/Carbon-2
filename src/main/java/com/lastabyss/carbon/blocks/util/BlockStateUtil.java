package com.lastabyss.carbon.blocks.util;

import java.util.Collection;

import net.minecraft.server.v1_8_R3.BlockState;

public class BlockStateUtil {

    public static <T extends Comparable<T>> Collection<T> getValues(BlockState<T> blockstate) {
        return blockstate.c();
    }

}
