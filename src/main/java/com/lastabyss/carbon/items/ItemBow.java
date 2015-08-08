package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ItemBow extends net.minecraft.server.v1_8_R3.ItemBow {

    @Override
    public void a(ItemStack itemstack, World world, EntityHuman entityhuman, int i) {
        int k;
        int l;
        boolean flag = entityhuman.abilities.canInstantlyBuild || (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, itemstack) > 0);
        if (!flag) {
            if (!entityhuman.inventory.b(Items.ARROW)) {
                return;
            }
        }
        int j = this.d(itemstack) - i;
        float f = j / 20.0f;
        if ((f = ((f * f) + (f * 2.0f)) / 3.0f) < 0.1) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        EntityArrow entityarrow = null;
        Item arrowItem = itemstack.getItem();
        if (arrowItem instanceof ItemArrow) {
            entityarrow = ((ItemArrow) arrowItem).createArrowEntity(world, itemstack, entityhuman);
        } else {
            entityarrow = new EntityArrow(world, entityhuman, f * 2.0f);
        }
        if (f == 1.0f) {
            entityarrow.setCritical(true);
        }
        if ((k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, itemstack)) > 0) {
            entityarrow.b(entityarrow.j() + (k * 0.5) + 0.5);
        }
        if ((l = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, itemstack)) > 0) {
            entityarrow.setKnockbackStrength(l);
        }
        if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, itemstack) > 0) {
            EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
            entityarrow.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                entityarrow.setOnFire(event.getDuration());
            }
        }
        EntityShootBowEvent event = null;
        if ((event = CraftEventFactory.callEntityShootBowEvent(entityhuman, itemstack, entityarrow, f)).isCancelled()) {
            event.getProjectile().remove();
            return;
        }
        if (event.getProjectile() == entityarrow.getBukkitEntity()) {
            world.addEntity(entityarrow);
        }
        itemstack.damage(1, entityhuman);
        world.makeSound(entityhuman, "random.bow", 1.0f, (1.0f / ((g.nextFloat() * 0.4f) + 1.2f)) + (f * 0.5f));
        if (flag) {
            entityarrow.fromPlayer = 2;
        } else {
            entityhuman.inventory.a(Items.ARROW);
        }
        entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
    }

}
