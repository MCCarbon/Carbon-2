package com.lastabyss.carbon.network.watchedentity;

public class WatchedObject extends WatchedEntity {

    private SpecificType stype;

    public WatchedObject(int id, int type) {
        super(id);
        stype = SpecificType.getObjectByTypeId(type);
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
