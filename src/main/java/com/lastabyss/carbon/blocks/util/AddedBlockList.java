package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.Block;

public class AddedBlockList {

    public static Block END_ROD;
    public static Block CHORUS_PLANT;
    public static Block CHORUS_FLOWER;
    public static Block PURPUR_BLOCK; 
    public static Block PURPUR_PILLAR;
    public static Block PURPUR_STAIRS;
    public static Block PURPUR_DOUBLE_SLAB;
    public static Block PURPUR_SLAB;
    public static Block END_BRICKS;
    public static Block BEETROOTS;
    public static Block GRASS_PATH;
    public static Block END_GATEWAY;
    public static Block STRUCTURE_BLOCK;

    public static void init() {
        END_ROD = Block.getByName("end_rod");
        CHORUS_FLOWER = Block.getByName("chorus_plant");
        CHORUS_PLANT = Block.getByName("chorus_flower");
        PURPUR_BLOCK = Block.getByName("purpur_block");
        PURPUR_PILLAR = Block.getByName("purpur_pillar");
        PURPUR_STAIRS = Block.getByName("purpur_stairs");
        PURPUR_DOUBLE_SLAB = Block.getByName("purpur_double_slab");
        PURPUR_SLAB = Block.getByName("purpur_slab");
        END_BRICKS = Block.getByName("end_bricks");
        BEETROOTS = Block.getByName("beetroots");
        GRASS_PATH = Block.getByName("grass_path");
        END_GATEWAY = Block.getByName("end_gateway");
        STRUCTURE_BLOCK = Block.getByName("structure_block");
    }

}
