package com.lastabyss.carbon.utils.watchedentity;

public class WatchedPlayer extends WatchedEntity {

    public WatchedPlayer(int id) {
        super(id);
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

}
