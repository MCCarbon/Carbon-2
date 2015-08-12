package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockPosition.MutableBlockPosition;

public class BlockPositionUtil {

    public static Iterable<BlockPosition> allInCube(BlockPosition start, BlockPosition end) {
        return BlockPosition.a(start, end);
    }

    public static Iterable<MutableBlockPosition> allInCubeM(BlockPosition start, BlockPosition end) {
        return BlockPosition.b(start, end);
    }

    public static BlockPosition add(BlockPosition position, int x, int y, int z) {
        return position.a(x, y, z);
    }

    public static double distanceSquaredFromCenter(BlockPosition position, double x, double y, double z) {
        return position.d(x, y, z);
    }

    public static BlockPosition add(BlockPosition pos, BlockPosition other) {
        return pos.a(other);
    }

    public static BlockPosition substract(BlockPosition pos, BlockPosition other) {
        return pos.b(other);
    }

}
