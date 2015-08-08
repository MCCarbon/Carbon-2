package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.*;

/**
 * @author Navid
 */
public class ItemSplashPotion extends ItemPotion {

    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman player) {
        if (f(itemstack.getData())) {
            if (!player.abilities.canInstantlyBuild) {
                --itemstack.count;
            }

            world.makeSound(player, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.addEntity(new EntityPotion(world, player, itemstack));
            }
            player.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
            return itemstack;
        } else {
            player.a(itemstack, this.d(itemstack));
            return itemstack;
        }
    }

}
