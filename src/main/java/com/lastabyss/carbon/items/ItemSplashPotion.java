package com.lastabyss.carbon.items;

import net.minecraft.server.v1_8_R3.*;

/**
 * @author Navid
 */
public class ItemSplashPotion extends ItemPotion {

    @Override
    public ItemStack a(ItemStack i, World world, EntityHuman ent) {
        if(f(i.getData())) {
            if(!ent.abilities.canInstantlyBuild) {
                --i.count;
            }

            world.makeSound(ent, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
            if(!world.isClientSide) {
                world.addEntity(new EntityPotion(world, ent, i));
            }
            ent.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
            return i;
        } else {
            ent.a(i, this.d(i));
            return i;
        }
    }

}
