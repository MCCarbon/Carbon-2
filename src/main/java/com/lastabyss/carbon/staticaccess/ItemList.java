package com.lastabyss.carbon.staticaccess;

import com.lastabyss.carbon.Injector;

import net.minecraft.server.v1_8_R3.Item;

public class ItemList {

    public static final Item CHORUS_FRUIT = Item.getById(432);
    public static final Item CHORUS_FRUIT_POPPED = Item.getById(433);
    public static final Item BEETROOT = Item.getById(434);
    public static final Item BEETROOT_SEEDS = Item.getById(435);
    public static final Item BEETROOT_SOUP = Item.getById(436);
    public static final Item SPLASH_POTION = Item.getById(438);
    public static final Item SPECTRAL_ARROW = Item.getById(439);
    public static final Item TIPPED_ARROW = Item.getById(440);

    static {
        if (!Injector.isFinished()) {
            throw new IllegalAccessError("Access before Injector finished");
        } 
    }

}
