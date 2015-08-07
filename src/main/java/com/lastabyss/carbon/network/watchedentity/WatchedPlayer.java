package com.lastabyss.carbon.network.watchedentity;

public class WatchedPlayer extends WatchedEntity {

    public WatchedPlayer(int id) {
        super(id);
    }

    @Override
    public SpecificType getType() {
        return SpecificType.PLAYER;
    }

}
