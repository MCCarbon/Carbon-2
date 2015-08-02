package com.lastabyss.carbon.blocks;

import java.util.Random;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.World;

public class BlockBeetroots extends BlockCrops {

    public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 3);

    // getSeedType
    @Override
    protected Item l() {
        return Items.WHEAT_SEEDS; // Wheat seeds for now until we implement the
                                  // correct items
    }

    // getPlantItem
    @Override
    protected Item n() {
        return Items.WHEAT_SEEDS; // Wheat seeds for now until we implement the
                                  // correct items
    }

    @Override
    public void tick(World var1, BlockPosition var2, IBlockData var3, Random var4) {
        if (var4.nextInt(3) == 0) {
            this.e(var1, var2, var3);
        } else {
            super.tick(var1, var2, var3, var4);
        }
    }

    // No clue what this does
    // @Override
    // protected int b(World var1) {
    // return super.b(var1) / 3;
    // }
    @Override
    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { AGE });
    }

}
