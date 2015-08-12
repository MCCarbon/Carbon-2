package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumDirection.EnumAxis;

public class EnumDirectionUtil {

    public static EnumDirection getById(int id) {
        return EnumDirection.fromType1(id);
    }

    public static EnumDirection getByHorizontalId(int id) {
        return EnumDirection.fromType2(id);
    }

    public static int getId(EnumDirection direction) {
        return direction.a();
    }

    public static int getHorizontalId(EnumDirection direction) {
        return direction.b();
    }

    public static EnumAxis getAxis(EnumDirection direction) {
        return direction.k();
    }

    public static EnumDirection rotateY(EnumDirection direction) {
        return direction.e();
    }

    public static EnumDirection rotateYCCW(EnumDirection direction) {
        return direction.f();
    }

}
