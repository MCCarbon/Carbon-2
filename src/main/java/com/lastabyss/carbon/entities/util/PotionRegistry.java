package com.lastabyss.carbon.entities.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;

public class PotionRegistry {

    private static final HashMap<String, MobEffectEntry[]> REGISTRY = new HashMap<String, MobEffectEntry[]>();

    private static final void register(String name, MobEffectEntry...effects) {
        REGISTRY.put("minecraft:"+name, effects);
    }

    static {
        register("night_vision", new MobEffectEntry(MobEffectList.NIGHT_VISION, 3600));
        register("long_night_vision", new MobEffectEntry(MobEffectList.NIGHT_VISION, 9600));
        register("invisibility", new MobEffectEntry(MobEffectList.INVISIBILITY, 3600));
        register("long_invisibility", new MobEffectEntry(MobEffectList.INVISIBILITY, 9600));
        register("leaping", new MobEffectEntry(MobEffectList.JUMP, 3600));
        register("long_leaping", new MobEffectEntry(MobEffectList.JUMP, 9600));
        register("strong_leaping", new MobEffectEntry(MobEffectList.JUMP, 1800, 1));
        register("fire_resistance", new MobEffectEntry(MobEffectList.FIRE_RESISTANCE, 3600));
        register("long_fire_refistance", new MobEffectEntry(MobEffectList.FIRE_RESISTANCE, 9600));
        register("swiftness", new MobEffectEntry(MobEffectList.FASTER_MOVEMENT, 3600));
        register("long_swiftness", new MobEffectEntry(MobEffectList.FASTER_MOVEMENT, 9600));
        register("strong_swiftness", new MobEffectEntry(MobEffectList.FASTER_MOVEMENT, 1800, 1));
        register("slowness", new MobEffectEntry(MobEffectList.SLOWER_MOVEMENT, 1800));
        register("long_slowness", new MobEffectEntry(MobEffectList.SLOWER_MOVEMENT, 4800));
        register("water_breathing", new MobEffectEntry(MobEffectList.WATER_BREATHING, 3600));
        register("long_water_breathing", new MobEffectEntry(MobEffectList.WATER_BREATHING, 9600));
        register("healing", new MobEffectEntry(MobEffectList.HEAL, 1));
        register("strong_healing", new MobEffectEntry(MobEffectList.HEAL, 1, 1));
        register("harming", new MobEffectEntry(MobEffectList.HARM, 1));
        register("strong_harming", new MobEffectEntry(MobEffectList.HARM, 1, 1));
        register("poison", new MobEffectEntry(MobEffectList.POISON, 900));
        register("long_poison", new MobEffectEntry(MobEffectList.POISON, 2400));
        register("strong_poison", new MobEffectEntry(MobEffectList.POISON, 450, 1));
        register("regeneration", new MobEffectEntry(MobEffectList.REGENERATION, 900));
        register("long_regeneration", new MobEffectEntry(MobEffectList.REGENERATION, 2400));
        register("strong_regeneration", new MobEffectEntry(MobEffectList.REGENERATION, 450, 1));
        register("strength", new MobEffectEntry(MobEffectList.INCREASE_DAMAGE, 3600));
        register("long_strength", new MobEffectEntry(MobEffectList.INCREASE_DAMAGE, 9600));
        register("strong_strength", new MobEffectEntry(MobEffectList.INCREASE_DAMAGE, 1800, 1));
        register("weakness", new MobEffectEntry(MobEffectList.WEAKNESS, 1800));
        register("long_weakness", new MobEffectEntry(MobEffectList.WEAKNESS, 4800));
    }

    public static List<MobEffect> getMobEffectsByPotionName(String name) {
        if (name == null) {
            return Collections.emptyList();
        }
        MobEffectEntry[] effects = REGISTRY.get(name);
        if (effects == null) {
            return Collections.emptyList();
        }
        ArrayList<MobEffect> mobeffects = new ArrayList<MobEffect>();
        for (MobEffectEntry entry : effects) {
            mobeffects.add(new MobEffect(entry.type.getId(), entry.duration, entry.amplifier));
        }
        return mobeffects;
    }

    private static final class MobEffectEntry {
        private final MobEffectList type;
        private final int duration;
        private int amplifier;

        public MobEffectEntry(MobEffectList type, int duration) {
            this(type, duration, 0);
        }
        public MobEffectEntry(MobEffectList type, int duration, int amplifier) {
            this.type = type;
            this.duration = duration;
            this.amplifier = amplifier;
        }
    }

}
