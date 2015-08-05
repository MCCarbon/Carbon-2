package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

public class ItemArrow extends Item {

    public ItemArrow() {
        c("arrow");
    }

    public EntityArrow a(World var1, ItemStack var2, EntityLiving var3) {
        return new EntityArrow(var1, var3.locX, var3.locY, var3.locZ);
    }

}
