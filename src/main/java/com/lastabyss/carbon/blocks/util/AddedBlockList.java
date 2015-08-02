package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.Block;

public class AddedBlockList {

    public static Block END_ROD;
    public static Block CHORUS_PLANT;
    public static Block CHORUS_FLOWER;

    public static void init() {
        END_ROD = Block.getByName("end_rod");
        CHORUS_FLOWER = Block.getByName("chorus_plant");
        CHORUS_PLANT = Block.getByName("chorus_flower");
    }

}
