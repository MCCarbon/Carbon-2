package com.lastabyss.carbon.items;

import com.lastabyss.carbon.entities.EntityNewArrow;
import com.lastabyss.carbon.entities.EntityTippedArrow;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

/**
 * @author Navid
 */
public class ItemTippedArrow extends ItemNewArrow {

    public ItemTippedArrow() {
        c("tipped_arrow");
    }

    @Override
    public EntityNewArrow createArrowEntity(World world, ItemStack itemstack, EntityLiving entity) {
        return new EntityTippedArrow(world, entity, itemstack);
    }

}