package com.lastabyss.carbon.network.watchedentity;

public class WatchedLiving extends WatchedEntity {

    private boolean isAgeable;
    private boolean isTameable;
    private SpecificType stype;

    public WatchedLiving(int id, int type) {
        super(id);
        stype = SpecificType.getMobByTypeId(type);
        switch (type) {
            case 90:
            case 91:
            case 92:
            case 93:
            case 96:
            case 100:
            case 101:
            case 120: {
                isAgeable = true;
                break;
            }
            case 95:
            case 98: {
                isTameable = true;
                isAgeable = true;
                break;
            }
        }
    }

    @Override
    public boolean isLiving() {
        return true;
    }

    @Override
    public boolean isAgeable() {
        return isAgeable;
    }

    @Override
    public boolean isTameable() {
        return isTameable;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public SpecificType getType() {
        return stype;
    }

}
