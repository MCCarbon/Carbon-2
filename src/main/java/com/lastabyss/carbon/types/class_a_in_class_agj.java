package com.lastabyss.carbon.types;

import com.lastabyss.carbon.blocks.util.EnumDirectionUtil;

import net.minecraft.server.v1_8_R3.EnumDirection;

public enum class_a_in_class_agj {
    NONE, LEFT_RIGHT, FRONT_BACK;

    public int a(int var1, int var2) {
        int var3 = var2 / 2;
        int var4 = var1 > var3 ? var1 - var2 : var1;
        switch (SwitchHelper.ORDINAL_TO_SWITCH_ID[ordinal()]) {
        case 1:
            return (var2 - var4) % var2;
        case 2:
            return ((var3 - var4) + var2) % var2;
        default:
            return var1;
        }
    }

    public EnumRotation a(EnumDirection var1) {
        EnumDirection.EnumAxis var2 = EnumDirectionUtil.getAxis(var1);
        return ((this != LEFT_RIGHT) || (var2 != EnumDirection.EnumAxis.Z)) && ((this != FRONT_BACK) || (var2 != EnumDirection.EnumAxis.X)) ? EnumRotation.NONE : EnumRotation.CLOCKWISE_180;
    }

    public EnumDirection b(EnumDirection var1) {
        switch (SwitchHelper.ORDINAL_TO_SWITCH_ID[ordinal()]) {
        case 1:
            if (var1 == EnumDirection.WEST) {
                return EnumDirection.EAST;
            } else {
                if (var1 == EnumDirection.EAST) {
                    return EnumDirection.WEST;
                }

                return var1;
            }
        case 2:
            if (var1 == EnumDirection.NORTH) {
                return EnumDirection.SOUTH;
            } else {
                if (var1 == EnumDirection.SOUTH) {
                    return EnumDirection.NORTH;
                }

                return var1;
            }
        default:
            return var1;
        }
    }

    // $FF: synthetic class
    static class SwitchHelper {
        // $FF: synthetic field
        static final int[] ORDINAL_TO_SWITCH_ID = new int[class_a_in_class_agj.values().length];

        static {
            ORDINAL_TO_SWITCH_ID[class_a_in_class_agj.LEFT_RIGHT.ordinal()] = 1;
            ORDINAL_TO_SWITCH_ID[class_a_in_class_agj.FRONT_BACK.ordinal()] = 2;
        }
    }

}