package com.lastabyss.carbon.network.watchedentity;

import java.util.ArrayList;
import java.util.Arrays;

public enum SpecificType {

    // TODO: more types
    NONE(EntityType.NONE, 0),
    VILLAGER(EntityType.MOB, 120, new RemappingEntry(16, 12)), //profession
    HORSE(EntityType.MOB, 100, RemappingEntry.of(16, 12, "<>", 19, 13, "<>", 20, 14, "<>", 21, 15, "<>", 22, 16)), //info flags, type, color/variant, owner, armor
    BAT(EntityType.MOB, 65, new RemappingEntry(16, 11)), //hanging
    OCELOT(EntityType.MOB, 98, new RemappingEntry(18, 14)), //type
    WOLF(EntityType.MOB, 95, RemappingEntry.of(18, 14, "<>", 19, 15, "<>", 18, 14)), //health, begging, collar color
    ITEM(EntityType.OBJECT, 2, new RemappingEntry(10, 5)), //item
    ITEM_FRAME(EntityType.OBJECT, 71, new RemappingEntry(8, 5), new RemappingEntry(9, 6)); //item, rotation

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

    private EntityType etype;
    private int typeId;
    private RemappingEntry[] entries;
    SpecificType(EntityType etype, int typeId, RemappingEntry... entries) {
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

    private static enum EntityType {
        NONE, OBJECT, MOB;
    }

}