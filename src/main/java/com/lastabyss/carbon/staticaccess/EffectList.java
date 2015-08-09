package com.lastabyss.carbon.staticaccess;

import com.lastabyss.carbon.Injector;

import net.minecraft.server.v1_8_R3.MobEffectList;

public class EffectList {

    public static final MobEffectList GLOWING = MobEffectList.b("glowing");
    public static final MobEffectList LEVITATION = MobEffectList.b("levitation");

    static {
        if (!Injector.isFinished()) {
            throw new IllegalAccessError("Access before Injector finished");
        } 
    }

}
