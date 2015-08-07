package com.lastabyss.carbon.network.watchedentity;

public class WatchedObject extends WatchedEntity {

    private SpecificType stype;

    public WatchedObject(int id, int type) {
        super(id);
        stype = SpecificType.getObjectByTypeId(type);
    }

    @Override
    public SpecificType getType() {
        return stype;
    }

}
