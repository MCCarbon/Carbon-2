package com.lastabyss.carbon.network.watchedentity;

public abstract class WatchedEntity {

    private int id;

    public WatchedEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract boolean isLiving();

    public abstract boolean isAgeable();

    public abstract boolean isTameable();

    public abstract boolean isPlayer();

    public abstract SpecificType getType();

    public static enum SpecificType {
        NONE, VILLAGER, HORSE, BAT, ITEM, ITEM_FRAME;
    }

}
