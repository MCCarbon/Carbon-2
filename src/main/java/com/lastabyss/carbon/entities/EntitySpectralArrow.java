package com.lastabyss.carbon.entities;

import com.lastabyss.carbon.staticaccess.ItemList;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;

public class EntitySpectralArrow extends EntityNewArrow {

    private int duration = 200;

    public EntitySpectralArrow(World world) {
        super(world);
    }

    public EntitySpectralArrow(World world, EntityLiving entity) {
        super(world, entity);
    }

    public EntitySpectralArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public void a(NBTTagCompound compound) {
        super.a(compound);
        if (compound.hasKey("Duration")) {
            duration = compound.getInt("Duration");
        }
    }

    @Override
    public void b(NBTTagCompound compound) {
        super.b(compound);
        compound.setInt("Duration", duration);
    }

    @Override
    public void onEntityHit(EntityLiving living) {
        super.onEntityHit(living);
        //TODO: glowing effect
        /*MobEffect var2 = new MobEffect(MobEffectList.WATER_BREATHING.getId(), duration, 0);
        living.addEffect(var2);*/
    }

    protected ItemStack getItemStack() {
        return new ItemStack(ItemList.SPECTRAL_ARROW);
    }

}
