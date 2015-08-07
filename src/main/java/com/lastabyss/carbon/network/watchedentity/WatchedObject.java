package com.lastabyss.carbon.network.watchedentity;

import java.util.Arrays;

public class WatchedObject extends WatchedEntity {

    private static final SpecificType[] specificTypes = new SpecificType[256];
    static {
        Arrays.fill(specificTypes, SpecificType.NONE);
        specificTypes[2] = SpecificType.ITEM;
        specificTypes[71] = SpecificType.ITEM_FRAME;
    }

    private SpecificType stype;

    public WatchedObject(int id, int type) {
        super(id);
        stype = specificTypes[type];
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean isLiving() {
        return false;
    }

    @Override
    public boolean isAgeable() {
        return false;
    }

    @Override
    public boolean isTameable() {
        return false;
    }

    @Override
    public SpecificType getType() {
        return stype;
    }

}
