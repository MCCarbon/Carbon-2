package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;

/**
 *
 * @author Navid
 */
public class CreativeTabUtil {

    public static final CreativeModeTab BUILDING_BLOCKS = new CreativeModeTab(0, "buildingBlocks") {
    };
    public static final CreativeModeTab DECORATIONS = new CreativeModeTab(1, "decorations") {
    };
    public static final CreativeModeTab REDSTONE = new CreativeModeTab(2, "redstone") {
    };
    public static final CreativeModeTab TRANSPORATION = new CreativeModeTab(3, "transportation") {
    };
    public static final CreativeModeTab MISC = (new CreativeModeTab(4, "misc") {
    }).a(new EnchantmentSlotType[]{EnchantmentSlotType.ALL});
    public static final CreativeModeTab SEARCH = (new CreativeModeTab(5, "search") {
    }).a("item_search.png");
    public static final CreativeModeTab FOOD = new CreativeModeTab(6, "food") {
    };
    public static final CreativeModeTab TOOLS = (new CreativeModeTab(7, "tools") {
    }).a(new EnchantmentSlotType[]{EnchantmentSlotType.DIGGER, EnchantmentSlotType.FISHING_ROD, EnchantmentSlotType.BREAKABLE});
    public static final CreativeModeTab COMBAT = (new CreativeModeTab(8, "combat") {
    }).a(new EnchantmentSlotType[]{EnchantmentSlotType.ARMOR, EnchantmentSlotType.ARMOR_FEET, EnchantmentSlotType.ARMOR_HEAD, EnchantmentSlotType.ARMOR_LEGS, EnchantmentSlotType.ARMOR_TORSO, EnchantmentSlotType.BOW, EnchantmentSlotType.WEAPON});
    public static final CreativeModeTab BREWING = new CreativeModeTab(9, "brewing") {
    };
    public static final CreativeModeTab MATERIALS = new CreativeModeTab(10, "materials") {
    };
    public static final CreativeModeTab INVENTORY = (new CreativeModeTab(11, "inventory") {
    }).a("inventory.png").k().i();
}
