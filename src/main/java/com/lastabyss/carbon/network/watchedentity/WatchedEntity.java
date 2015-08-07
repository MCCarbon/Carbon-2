package com.lastabyss.carbon.network.watchedentity;

public abstract class WatchedEntity {

    private int id;

    public WatchedEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract SpecificType getType();

}
