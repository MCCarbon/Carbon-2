package com.lastabyss.carbon.staticaccess;

import com.lastabyss.carbon.Injector;

import net.minecraft.server.v1_8_R3.Block;

public class BlockList {

    public static final Block END_ROD = Block.getByName("end_rod");
    public static final Block CHORUS_PLANT = Block.getByName("chorus_plant");
    public static final Block CHORUS_FLOWER = Block.getByName("chorus_flower");
    public static final Block PURPUR_BLOCK = Block.getByName("purpur_block"); 
    public static final Block PURPUR_PILLAR = Block.getByName("purpur_pillar");
    public static final Block PURPUR_STAIRS = Block.getByName("purpur_stairs");
    public static final Block PURPUR_DOUBLE_SLAB = Block.getByName("purpur_double_slab");
    public static final Block PURPUR_SLAB = Block.getByName("purpur_slab");
    public static final Block END_BRICKS = Block.getByName("end_bricks");
    public static final Block BEETROOTS = Block.getByName("beetroots");
    public static final Block GRASS_PATH = Block.getByName("grass_path");
    public static final Block END_GATEWAY = Block.getByName("end_gateway");
    public static final Block STRUCTURE_BLOCK = Block.getByName("structure_block");

    static {
        if (!Injector.isFinished()) {
            throw new IllegalAccessError("Access before Injector finished");
        } 
    }

}
