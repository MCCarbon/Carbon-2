package com.lastabyss.carbon.staticaccess;

import com.lastabyss.carbon.Injector;
import com.lastabyss.carbon.utils.DynamicEnumType;
import com.lastabyss.carbon.utils.Utils;
import net.minecraft.server.v1_8_R3.EnumParticle;

/**
 * @author Navid
 */
public class ParticleList {

    public static final EnumParticle DRAGON_BREATH = Utils.addParticle("dragonbreath", 42, false);
    public static final EnumParticle END_ROD = Utils.addParticle("endRod", 43, false);
}
