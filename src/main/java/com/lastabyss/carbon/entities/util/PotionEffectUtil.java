package com.lastabyss.carbon.entities.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

import com.google.common.collect.Lists;

public class PotionEffectUtil {

    public static List<MobEffect> getEffects(ItemStack itemstack) {
        ArrayList<MobEffect> effects = Lists.newArrayList();
        //effects.addAll(i(itemstack).b()); //TODO: create potion registry
        if (itemstack.hasTag() && itemstack.getTag().hasKeyOfType("CustomPotionEffects", 9)) {
            NBTTagList potioneffectstag = itemstack.getTag().getList("CustomPotionEffects", 10);
            for (int i = 0; i < potioneffectstag.size(); ++i) {
                NBTTagCompound effectcompound = potioneffectstag.get(i);
                MobEffect effect = MobEffect.b(effectcompound);
                if (effect != null) {
                    effects.add(effect);
                }
            }
        }

        return effects;
    }

}
