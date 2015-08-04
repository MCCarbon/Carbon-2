package com.lastabyss.carbon.network.watchedentity;

public class WatchedObject extends WatchedEntity {

    public WatchedObject(int id, int type) {
        super(id);
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
    public SpecificType getType() {
        //TODO
        return SpecificType.NONE;
    }

}
