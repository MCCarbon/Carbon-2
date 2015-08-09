package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBow;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ItemNewBow extends ItemBow {

    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman player) {
        if (player.abilities.canInstantlyBuild || findArrowSlot(player.inventory) != -1) {
            player.a(itemstack, this.d(itemstack));
        }
        return itemstack;
    }

    @Override
    public void a(ItemStack itemstack, World world, EntityHuman player, int usedFor) {
        final boolean flag = player.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, itemstack) > 0;
        int arrowSlot = this.findArrowSlot(player.inventory);
        if (flag || arrowSlot != -1) {
            ItemStack arrowItemStack = arrowSlot != -1 ? player.inventory.getItem(arrowSlot) : new ItemStack(Items.ARROW);
            final int j = this.d(itemstack) - usedFor;
            float ready = j / 20.0f;
            ready = (ready * ready + ready * 2.0f) / 3.0f;
            if (ready < 0.1) {
                return;
            }
            if (ready > 1.0f) {
                ready = 1.0f;
            }
            EntityArrow entityarrow = null;
            if (arrowItemStack.getItem() instanceof ItemNewArrow) {
                entityarrow = ((ItemNewArrow) arrowItemStack.getItem()).createArrowEntity(world, arrowItemStack, player, ready);
            } else {
                entityarrow = new EntityArrow(world, player, ready * 2.0f);
            }
            if (ready == 1.0f) {
                entityarrow.setCritical(true);
            }
            final int k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, itemstack);
            if (k > 0) {
                entityarrow.b(entityarrow.j() + k * 0.5 + 0.5);
            }
            final int l = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, itemstack);
            if (l > 0) {
                entityarrow.setKnockbackStrength(l);
            }
            if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, itemstack) > 0) {
                final EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
                entityarrow.world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    entityarrow.setOnFire(event.getDuration());
                }
            }
            final EntityShootBowEvent event2 = CraftEventFactory.callEntityShootBowEvent(player, itemstack, entityarrow, ready);
            if (event2.isCancelled()) {
                event2.getProjectile().remove();
                return;
            }
            if (event2.getProjectile() == entityarrow.getBukkitEntity()) {
                world.addEntity((net.minecraft.server.v1_8_R3.Entity) entityarrow);
            }
            itemstack.damage(1, player);
            world.makeSound((net.minecraft.server.v1_8_R3.Entity) player, "random.bow", 1.0f, 1.0f / (ItemNewBow.g.nextFloat() * 0.4f + 1.2f) + ready * 0.5f);
            if (flag) {
                entityarrow.fromPlayer = 2;
            } else {
                player.inventory.a(Items.ARROW);
            }
            player.b(StatisticList.USE_ITEM_COUNT[Item.getId((Item) this)]);
        }
    }

    private int findArrowSlot(PlayerInventory inventory) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if (this.isArrow(itemstack)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isArrow(ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        return (itemstack.getItem() instanceof ItemNewArrow) || (itemstack.getItem() == Items.ARROW);
    }

}
