package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

public class ItemArrow extends Item {

    public EntityArrow createArrowEntity(World world, ItemStack itemstack, EntityLiving entity) {
        return new EntityArrow(world, entity.locX, entity.locY, entity.locZ);
    }

}
