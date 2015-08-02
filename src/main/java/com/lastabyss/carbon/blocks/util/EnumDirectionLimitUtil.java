package com.lastabyss.carbon.blocks.util;

import java.util.Random;

import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumDirection.EnumDirectionLimit;

public class EnumDirectionLimitUtil {

    public static EnumDirection getRandomDirection(EnumDirectionLimit limit, Random rnd) {
        return limit.a(rnd);
    }

}
