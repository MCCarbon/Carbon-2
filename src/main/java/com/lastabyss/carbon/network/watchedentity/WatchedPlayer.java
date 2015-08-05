package com.lastabyss.carbon.network.watchedentity;

public class WatchedPlayer extends WatchedEntity {

    public WatchedPlayer(int id) {
        super(id);
    }

    @Override
    public boolean isLiving() {
        return true;
    }

    @Override
    public boolean isAgeable() {
        return false;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean isTameable() {
        return false;
    }

    @Override
    public SpecificType getType() {
        return SpecificType.NONE;
    }

}