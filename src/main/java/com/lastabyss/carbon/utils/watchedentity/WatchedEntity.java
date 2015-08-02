package com.lastabyss.carbon.utils.watchedentity;

public abstract class WatchedEntity {

    private int id;

    public WatchedEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract boolean isPlayer();

    @Override
    public String toString() {
        return new StringBuilder(100).append("Id: ").append(getId()).append(", player: ").append(isPlayer()).toString();
    }

}
