package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

import com.lastabyss.carbon.entities.EntityNewArrow;
import com.lastabyss.carbon.entities.EntitySpectralArrow;

public class ItemSpectralArrow extends ItemNewArrow {

    public ItemSpectralArrow() {
        c("spectral_arrow");
    }

    @Override
    protected EntityNewArrow createArrowEntity(World world, ItemStack itemstack, EntityLiving entity) {
        return new EntitySpectralArrow(world, entity);
    }

}
