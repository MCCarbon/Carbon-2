package com.lastabyss.carbon.entities;

import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;

public class EntitySpectralArrow extends EntityArrow {

    private int duration = 200;

    public EntitySpectralArrow(World world) {
        super(world);
    }

    public EntitySpectralArrow(World world, EntityLiving entity, float f) {
        super(world, entity, f);
    }

    public EntitySpectralArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    /*@Override //this method doesn't exist in old entity arrow //TODO: create our own arrow
    public void b(EntityLiving living) {
        super.b(living);
        MobEffect var2 = new MobEffect(MobEffectList.WATER_BREATHING.getId(), duration, 0);
        living.addEffect(var2);
    }*/

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

}
