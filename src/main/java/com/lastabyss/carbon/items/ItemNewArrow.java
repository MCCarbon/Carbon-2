package com.lastabyss.carbon.items;

import com.lastabyss.carbon.entities.EntityNewArrow;

import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

public class ItemNewArrow extends Item {

    public EntityArrow createArrowEntity(World world, ItemStack itemstack, EntityLiving entity, float f) {
        EntityNewArrow newarrow = createArrowEntity0(world, itemstack, entity);
        newarrow.startShoot(entity.pitch, entity.yaw, 0.0F, f * 3.0F, 0.0F);
        return newarrow;
    }

    protected EntityNewArrow createArrowEntity0(World world, ItemStack itemstack, EntityLiving entity) {
        return new EntityNewArrow(world, entity);
    }

}
