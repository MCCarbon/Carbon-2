package com.lastabyss.carbon.entities;

import java.util.List;
import java.util.Set;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;

import com.google.common.collect.Sets;
import com.lastabyss.carbon.entities.util.PotionEffectUtil;

public class EntityTippedArrow extends EntityNewArrow {

    private final Set<MobEffect> effects = Sets.newHashSet();
    private ItemStack item;

    public EntityTippedArrow(World world) {
        super(world);
    }

    public EntityTippedArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityTippedArrow(World world, double x, double y, double z, ItemStack itemstack) {
        this(world, x, y, z);
        this.setItem(itemstack);
    }

    public EntityTippedArrow(World world, EntityLiving entity, ItemStack itemstack) {
        super(world, entity);
        this.setItem(itemstack);
    }

    private void setItem(ItemStack itemstack) {
        List<MobEffect> effectlist = PotionEffectUtil.getEffects(itemstack);
        for (MobEffect effect : effectlist) {
            this.effects.add(new MobEffect(effect));
        }
        this.item = itemstack.cloneItemStack();
        this.item.count = 1;
    }

    @Override
    protected void h() {
        super.h();
    }

    @Override
    public void a(NBTTagCompound compound) {
        super.a(compound);
        if (compound.hasKeyOfType("Item", 10)) {
            this.item = ItemStack.createStack(compound.getCompound("Item"));
        }
    }

    @Override
    public void b(NBTTagCompound compound) {
        super.b(compound);
        if (this.item != null) {
            compound.set("Item", this.item.save(new NBTTagCompound()));
        }
    }

    @Override
    public void onEntityHit(EntityLiving entity) {
        super.onEntityHit(entity);
        for (MobEffect effect : effects) {
            entity.addEffect(effect);
        }
    }

    @Override
    protected ItemStack getItemStack() {
        return this.effects.isEmpty() ? new ItemStack(Items.ARROW) : this.item;
    }

}
