package com.lastabyss.carbon.types;

import com.lastabyss.carbon.blocks.util.EnumDirectionUtil;

import net.minecraft.server.v1_8_R3.EnumDirection;

public enum EnumRotation {
    NONE, CLOCKWISE_90, CLOCKWISE_180, COUNTERCLOCKWISE_90;

    public EnumRotation a(EnumRotation rotation) {
        switch (SwitchHelper.ORDINAL_TO_SWITCH_ID[rotation.ordinal()]) {
            case 3:
                switch (SwitchHelper.ORDINAL_TO_SWITCH_ID[ordinal()]) {
                    case 1:
                        return CLOCKWISE_180;
                    case 2:
                        return COUNTERCLOCKWISE_90;
                    case 3:
                        return NONE;
                    case 4:
                        return CLOCKWISE_90;
                }
            case 4:
                switch (SwitchHelper.ORDINAL_TO_SWITCH_ID[ordinal()]) {
                    case 1:
                        return COUNTERCLOCKWISE_90;
                    case 2:
                        return NONE;
                    case 3:
                        return CLOCKWISE_90;
                    case 4:
                        return CLOCKWISE_180;
                }
            case 2:
                switch (SwitchHelper.ORDINAL_TO_SWITCH_ID[ordinal()]) {
                    case 1:
                        return CLOCKWISE_90;
                    case 2:
                        return CLOCKWISE_180;
                    case 3:
                        return COUNTERCLOCKWISE_90;
                    case 4:
                        return NONE;
                }
            default:
                return this;
        }
    }

    public EnumDirection a(EnumDirection direction) {
        if (EnumDirectionUtil.getAxis(direction) == EnumDirection.EnumAxis.Y) {
            return direction;
        } else {
            switch (SwitchHelper.ORDINAL_TO_SWITCH_ID[ordinal()]) {
                case 2:
                    return EnumDirectionUtil.rotateY(direction);
                case 3:
                    return direction.opposite();
                case 4:
                    return EnumDirectionUtil.rotateYCCW(direction);
                default:
                    return direction;
            }
        }
    }

    public int a(int var1, int var2) {
        switch (SwitchHelper.ORDINAL_TO_SWITCH_ID[ordinal()]) {
            case 2:
                return (var1 + (var2 / 4)) % var2;
            case 3:
                return (var1 + (var2 / 2)) % var2;
            case 4:
                return (var1 + ((var2 * 3) / 4)) % var2;
            default:
                return var1;
        }
    }

    // $FF: synthetic class
    static class SwitchHelper {
        // $FF: synthetic field
        static final int[] ORDINAL_TO_SWITCH_ID = new int[EnumRotation.values().length];

        static {
            ORDINAL_TO_SWITCH_ID[EnumRotation.NONE.ordinal()] = 1;
            ORDINAL_TO_SWITCH_ID[EnumRotation.CLOCKWISE_90.ordinal()] = 2;
            ORDINAL_TO_SWITCH_ID[EnumRotation.CLOCKWISE_180.ordinal()] = 3;
            ORDINAL_TO_SWITCH_ID[EnumRotation.COUNTERCLOCKWISE_90.ordinal()] = 4;
        }
    }

}
