package com.lastabyss.carbon.network.watchedentity;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.EntityType;

@SuppressWarnings("deprecation")
public enum SpecificType {

    // TODO: more types
    NONE(EType.NONE, -1),
    VILLAGER(EType.MOB, EntityType.VILLAGER, new RemappingEntry(16, 12)), //profession
    HORSE(EType.MOB, EntityType.HORSE, RemappingEntry.of(16, 12, "<>", 19, 13, "<>", 20, 14, "<>", 21, 15, "<>", 22, 16)), //info flags, type, color/variant, owner, armor
    BAT(EType.MOB, EntityType.BAT, new RemappingEntry(16, 11)), //hanging
    OCELOT(EType.MOB, EntityType.OCELOT, new RemappingEntry(18, 14)), //type
    WOLF(EType.MOB, EntityType.WOLF, RemappingEntry.of(18, 14, "<>", 19, 15, "<>", 18, 14)), //health, begging, collar color
    PIG(EType.MOB, EntityType.PIG, new RemappingEntry(16, 12)), //has saddle
    RABBIT(EType.MOB, EntityType.RABBIT, new RemappingEntry(18, 12)), //type
    SHEEP(EType.MOB, EntityType.SHEEP, new RemappingEntry(16, 12)), //info flags (color + sheared)
    ENDERMAN(EType.MOB, EntityType.ENDERMAN, RemappingEntry.of(16, 11, "<>", 17, 12, "<>", 18, 13)), //carried block id, carried data id, screaming
    ZOMBIE(EType.MOB, EntityType.ZOMBIE, RemappingEntry.of(12, 11, "<>", 13, 12, "<>", 14, 13)), //is baby, is villager, is converting
    ZOMBIE_PIGMAN(EType.MOB, EntityType.PIG_ZOMBIE, RemappingEntry.of(12, 11, "<>", 13, 12, "<>", 14, 13)), //is baby, is villager, is converting
    BLAZE(EType.MOB, EntityType.BLAZE, new RemappingEntry(16, 11)), //on fire
    SPIDER(EType.MOB, EntityType.SPIDER, new RemappingEntry(16, 11)), //is climbing
    CAVE_SPIDER(EType.MOB, EntityType.CAVE_SPIDER, new RemappingEntry(16, 11)), //is climbing
    CREEPER(EType.MOB, EntityType.CREEPER, RemappingEntry.of(16, 11, "<>", 17, 12, "<>", 18, 13)), //state, is powered, ignited
    ITEM(EType.OBJECT, 2, new RemappingEntry(10, 5)), //item
    ITEM_FRAME(EType.OBJECT, 71, new RemappingEntry(8, 5), new RemappingEntry(9, 6)); //item, rotation

    private static final SpecificType[] OBJECT_BY_TYPE_ID = new SpecificType[256];
    private static final SpecificType[] MOB_BY_TYPE_ID = new SpecificType[256];

    static {
        Arrays.fill(OBJECT_BY_TYPE_ID, SpecificType.NONE);
        Arrays.fill(MOB_BY_TYPE_ID, SpecificType.NONE);
        for (SpecificType type : values()) {
            switch (type.etype) {
                case OBJECT: {
                    OBJECT_BY_TYPE_ID[type.typeId] = type;
                    break;
                }
                case MOB: {
                    MOB_BY_TYPE_ID[type.typeId] = type;
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    protected static SpecificType getObjectByTypeId(int objectTypeId) {
        return OBJECT_BY_TYPE_ID[objectTypeId];
    }

    protected static SpecificType getMobByTypeId(int mobTypeId) {
        return MOB_BY_TYPE_ID[mobTypeId];
    }

    private EType etype;
    private int typeId;
    private RemappingEntry[] entries;

    SpecificType(EType etype, EntityType type, RemappingEntry... entries) {
        this(etype, type.getTypeId(), entries);
    }

    SpecificType(EType etype, int typeId, RemappingEntry... entries) {
        this.etype = etype;
        this.typeId = typeId;
        this.entries = entries;
    }


    public RemappingEntry[] getRemaps() {
        return entries;
    }

    public static class RemappingEntry {
        private int from;
        private int to;
        private RemappingEntry(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        private static RemappingEntry[] of(Object... pairsWithSeparator) {
            ArrayList<RemappingEntry> entries = new ArrayList<RemappingEntry>();
            for (int i = 0; i < pairsWithSeparator.length; i += 3) {
                entries.add(new RemappingEntry((int) pairsWithSeparator[i], (int) pairsWithSeparator[i + 1]));
            }
            return entries.toArray(new RemappingEntry[entries.size()]);
        }

    }

    private static enum EType {
        NONE, OBJECT, MOB;
    }

}