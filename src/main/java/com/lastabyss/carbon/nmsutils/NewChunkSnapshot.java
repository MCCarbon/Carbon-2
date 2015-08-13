package com.lastabyss.carbon.nmsutils;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChunkSnapshot;
import net.minecraft.server.v1_8_R3.IBlockData;

public class NewChunkSnapshot extends ChunkSnapshot {

    private final short[] combinedIds = new short[65536];
    private final IBlockData air = Blocks.AIR.getBlockData();

    @Override
    public IBlockData a(int var1, int var2, int var3) {
        int var4 = var1 << 12 | var3 << 8 | var2;
        return this.a(var4);
    }

    @Override
    public IBlockData a(int var1) {
        if (var1 >= 0 && var1 < this.combinedIds.length) {
            IBlockData var2 = Block.d.a(this.combinedIds[var1]);
            return var2 != null ? var2 : this.air;
        } else {
            throw new IndexOutOfBoundsException("The coordinate is out of range");
        }
    }

    @Override
    public void a(int var1, int var2, int var3, IBlockData var4) {
        int var5 = var1 << 12 | var3 << 8 | var2;
        this.a(var5, var4);
    }

    @Override
    public void a(int var1, IBlockData var2) {
        if (var1 >= 0 && var1 < this.combinedIds.length) {
            this.combinedIds[var1] = (short) Block.d.b(var2);
        } else {
            throw new IndexOutOfBoundsException("The coordinate is out of range");
        }
    }

    public int getHighestYAt(int x, int z) {
        int coordId = (x << 12 | z << 8) + 256 - 1;

        for (int y = 255; y >= 0; --y) {
            IBlockData blockdata = Block.d.a(this.combinedIds[coordId + y]);
            if (blockdata != null && blockdata != this.air) {
                return y;
            }
        }

        return 0;
    }

}
