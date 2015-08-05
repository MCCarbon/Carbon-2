package com.lastabyss.carbon.network.watchedentity;

public class WatchedObject extends WatchedEntity {

    int type;

    public WatchedObject(int id, int type) {
        super(id);
        this.type = type;
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
        switch (type) {
            case 71: {
                return SpecificType.ITEM_FRAME;
            }
            case 2: {
                return SpecificType.ITEM;
            }
            default: {
                return SpecificType.NONE;
            }
        }
    }

}
