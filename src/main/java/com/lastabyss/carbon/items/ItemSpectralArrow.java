package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

import com.lastabyss.carbon.entities.EntitySpectralArrow;

public class ItemSpectralArrow extends ItemArrow {

    public ItemSpectralArrow() {
        c("spectral_arrow");
    }

    @Override
    public EntityArrow createArrowEntity(World world, ItemStack itemstack, EntityLiving entity) {
        return new EntitySpectralArrow(world, entity.locX, entity.locY, entity.locZ);
    }

}
