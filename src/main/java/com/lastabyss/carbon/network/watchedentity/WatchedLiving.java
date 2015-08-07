package com.lastabyss.carbon.network.watchedentity;

public class WatchedLiving extends WatchedEntity {

    private SpecificType stype;

    public WatchedLiving(int id, int type) {
        super(id);
        stype = SpecificType.getMobByTypeId(type);
    }

    @Override
    public SpecificType getType() {
        return stype;
    }

}
