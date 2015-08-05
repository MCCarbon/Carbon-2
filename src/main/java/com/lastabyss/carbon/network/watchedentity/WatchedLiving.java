package com.lastabyss.carbon.network.watchedentity;

public class WatchedLiving extends WatchedEntity {

    private boolean isLiving; //Yes, we do have a flag for living because some people tend to spawn objects using spawn mob packet (hologramm plugins most of the time)
    private boolean isAgeable;
    private boolean isTameable;
    private SpecificType stype;

    public WatchedLiving(int id, int type) {
        super(id);
        stype = SpecificType.NONE;
        //TODO: cleanup this shit somehow
        switch (type) {
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68: {
                isLiving = true;
                break;
            }
            case 90:
            case 91:
            case 92:
            case 93: {
                isAgeable = true;
                isLiving = true;
                break;
            }
            case 94: {
                isLiving = true;
                break;
            }
            case 95: {
                isTameable = true;
                isAgeable = true;
                isLiving = true;
                break;
            }
            case 96: {
                isAgeable = true;
                isLiving = true;
                break;
            }
            case 97: {
                isLiving = true;
                break;
            }
            case 98: {
                isTameable = true;
                isAgeable = true;
                isLiving = true;
                break;
            }
            case 99: {
                isLiving = true;
                break;
            }
            case 100: {
                stype = SpecificType.HORSE;
                isAgeable = true;
                isLiving = true;
                break;
            }
            case 101: {
                isAgeable = true;
                isLiving = true;
                break;
            }
            case 120: {
                stype = SpecificType.VILLAGER;
                isAgeable = true;
                isLiving = true;
                break;
            }
        }
    }

    @Override
    public boolean isLiving() {
        return isLiving;
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
