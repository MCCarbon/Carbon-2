package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ItemSeeds;

public class ItemBeetrootSeeds extends ItemSeeds {
	
	public ItemBeetrootSeeds(Block block) {
		super(block, Blocks.FARMLAND);
		this.c("beetroot_seeds");
	}

}
