package com.lastabyss.carbon;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemFood;
import net.minecraft.server.v1_8_R3.ItemSeeds;
import net.minecraft.server.v1_8_R3.ItemSoup;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.PotionBrewer;
import net.minecraft.server.v1_8_R3.TileEntity;

import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import com.lastabyss.carbon.blocks.BlockBeetroots;
import com.lastabyss.carbon.blocks.BlockChorusFlower;
import com.lastabyss.carbon.blocks.BlockChorusPlant;
import com.lastabyss.carbon.blocks.BlockEndGateway;
import com.lastabyss.carbon.blocks.BlockEndRod;
import com.lastabyss.carbon.blocks.BlockGrassPath;
import com.lastabyss.carbon.blocks.BlockPurpurSlabAbstract;
import com.lastabyss.carbon.blocks.BlockRotatable;
import com.lastabyss.carbon.blocks.BlockStairs;
import com.lastabyss.carbon.blocks.BlockStepAbstract;
import com.lastabyss.carbon.blocks.BlockStructureBlock;
import com.lastabyss.carbon.blocks.TileEntityEndGateway;
import com.lastabyss.carbon.blocks.TileEntityStructure;
import com.lastabyss.carbon.blocks.util.SoundUtil;
import com.lastabyss.carbon.blocks.util.WrappedBlock;
import com.lastabyss.carbon.items.ItemArrow;
import com.lastabyss.carbon.items.ItemBow;
import com.lastabyss.carbon.items.ItemChorusFruit;
import com.lastabyss.carbon.items.ItemSpectralArrow;
import com.lastabyss.carbon.items.ItemSplashPotion;
import com.lastabyss.carbon.items.ItemStep;
import com.lastabyss.carbon.items.ItemTippedArrow;
import com.lastabyss.carbon.network.NetworkInjector;
import com.lastabyss.carbon.utils.FixedChatSerializer;
import com.lastabyss.carbon.utils.ReflectionUtils;
import com.lastabyss.carbon.utils.Utils;

/**
 * The injector class is the driver behind Carbon.
 *
 * @author Navid
 */
public class Injector {

    public static final org.bukkit.Material END_ROD_BLOCK = Utils.addMaterial("END_ROD_BLOCK", 198);
    public static final org.bukkit.Material CHORUS_PLANT_BLOCK = Utils.addMaterial("CHORUS_PLANT_BLOCK", 199);
    public static final org.bukkit.Material CHORUS_FLOWER_MATERIAL = Utils.addMaterial("CHORUS_FLOWER_MATERIAL", 200);
    public static final org.bukkit.Material PURPUR_BLOCK = Utils.addMaterial("PURPUR_BLOCK", 201);
    public static final org.bukkit.Material PURPUR_PILLAR = Utils.addMaterial("PURPUR_PILLAR", 202);
    public static final org.bukkit.Material PURPUR_STAIRS = Utils.addMaterial("PURPUR_STAIRS", 203);
    public static final org.bukkit.Material PURPUR_DOUBLE_SLAB = Utils.addMaterial("PURPUR_DOUBLE_SLAB", 204);
    public static final org.bukkit.Material PURPUR_SLAB = Utils.addMaterial("PURPUR_SLAB", 205);
    public static final org.bukkit.Material END_BRICKS = Utils.addMaterial("END_BRICKS", 206);
    public static final org.bukkit.Material BEETROOTS = Utils.addMaterial("BEETROOTS", 207);
    public static final org.bukkit.Material GRASS_PATH = Utils.addMaterial("GRASS_PATH", 208);
    public static final org.bukkit.Material END_GATEWAY = Utils.addMaterial("END_GATEWAY", 209);
    public static final org.bukkit.Material STRUCTURE_BLOCK = Utils.addMaterial("STRUCTURE_BLOCK", 255);

    public static final org.bukkit.Material CHORUS_FRUIT = Utils.addMaterial("CHORUS_FRUIT", 432);
    public static final org.bukkit.Material CHORUS_FRUIT_POPPED = Utils.addMaterial("CHORUS_FRUIT_POPPED", 433);
    public static final org.bukkit.Material BEETROOT = Utils.addMaterial("BEETROOT", 434);
    public static final org.bukkit.Material BEETROOT_SEEDS = Utils.addMaterial("BEETROOT_SEEDS", 435);
    public static final org.bukkit.Material BEETROOT_SOUP = Utils.addMaterial("BEETROOT_SOUP", 436);
    public static final org.bukkit.Material SPLASH_POTION = Utils.addMaterial("SPLASH_POTION", 438);
    public static final org.bukkit.Material SPECTRAL_ARROW = Utils.addMaterial("SPECTRAL_ARROW", 439);
    public static final org.bukkit.Material TIPPED_ARROW = Utils.addMaterial("TIPPED_ARROW", 440);

    public Injector(Carbon plugin) {
    }

    public void registerAll() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvocationTargetException, NoSuchMethodException {
        //Inject new json serializer
        ReflectionUtils.setFinalField(IChatBaseComponent.ChatSerializer.class.getDeclaredField("a"), null, FixedChatSerializer.gson);

        //Inject network
        NetworkInjector.inject();

        //Add new blocks
        Block endrod = new BlockEndRod().setStrength(0.0F).setLightLevel(0.9375F).setStepSound(SoundUtil.WOOD).setName("endRod");
        Block chorusPlant = new BlockChorusPlant().setStrength(0.4F).setStepSound(SoundUtil.WOOD).setName("chorusPlant");
        Block chorusFlower = new BlockChorusFlower().setStrength(0.4F).setStepSound(SoundUtil.WOOD).setName("chorusFlower");
        Block purpur = new WrappedBlock(Material.STONE).setStrength(1.5F).setExplosionResist(10.0F).setStepSound(SoundUtil.STONE2).setName("purpurBlock");
        Block purpurPillar = new BlockRotatable(Material.STONE).setStrength(1.5F).setExplosionResist(10.0F).setStepSound(SoundUtil.STONE2).setName("purpurPillar");
        Block purpurStairs = new BlockStairs(purpur.getBlockData()).setName("stairsPurpur");
        BlockStepAbstract purpurDoubleSlab = (BlockStepAbstract) new BlockPurpurSlabAbstract.BlockPurpurDoubleSlab().setStrength(2.0F).setExplosionResist(10.0F).setStepSound(SoundUtil.STONE2).setName("purpurSlab");
        BlockStepAbstract purpurSlab = (BlockStepAbstract) new BlockPurpurSlabAbstract.BlockPurpurSlab().setStrength(2.0F).setExplosionResist(10.0F).setStepSound(SoundUtil.STONE2).setName("purpurSlab");
        Block endBricks = new WrappedBlock(Material.STONE).setStepSound(SoundUtil.STONE2).setStrength(0.8F).setName("endBricks");
        Block beetroots = new BlockBeetroots().setName("beetroots");
        Block grassPath = new BlockGrassPath().setStrength(0.65F).setStepSound(SoundUtil.GRASS).setName("grassPath").setUnbreakable();
        Block endGateway = new BlockEndGateway(Material.PORTAL).setStrength(-1.0F).setExplosionResist(6000000.0F);
        Block structureBlock = new BlockStructureBlock().setUnbreakable().setExplosionResist(6000000.0F).setName("structureBlock").setLightLevel(1.0F);

        registerBlock(198, "end_rod", endrod, new ItemBlock(endrod));
        registerBlock(199, "chorus_plant", chorusPlant, new ItemBlock(chorusPlant));
        registerBlock(200, "chorus_flower", chorusFlower, new ItemBlock(chorusFlower));
        registerBlock(201, "purpur_block", purpur, new ItemBlock(purpur));
        registerBlock(202, "purpur_pillar", purpurPillar, new ItemBlock(purpurPillar));
        registerBlock(203, "purpur_stairs", purpurStairs, new ItemBlock(purpurStairs));
        registerBlock(204, "purpur_double_slab", purpurDoubleSlab);
        registerBlock(205, "purpur_slab", purpurSlab, new ItemStep(purpurSlab, purpurSlab, purpurDoubleSlab).b("purpurSlab"));
        registerBlock(206, "end_bricks", endBricks, new ItemBlock(endBricks));
        registerBlock(207, "beetroots", beetroots, new ItemBlock(beetroots));
        registerBlock(208, "grass_path", grassPath, new ItemBlock(grassPath));
        registerBlock(209, "end_gateway", endGateway, new ItemBlock(endGateway));
        registerBlock(255, "structure_block", structureBlock, new ItemBlock(structureBlock));

        //Add new items
        registerItem(261, "bow", new ItemBow().c("bow"));
        registerItem(262, "arrow", new ItemArrow().c("arrow"));
        registerItem(432, "chorus_fruit", new ItemChorusFruit());
        registerItem(433, "chorus_fruit_popped", (new Item()).c("chorusFruitPopped"));
        registerItem(434, "beetroot", (new ItemFood(1, 0.6F, false)).c("beetroot"));
        registerItem(435, "beetroot_seeds", new ItemSeeds(beetroots, Blocks.FARMLAND));
        registerItem(436, "beetroot_soup", new ItemSoup(6).c("beetroot_soup"));
        //Skip 437 because there is nothing there apparently... spooky
        registerItem(438, "splash_potion", new ItemSplashPotion().c("splash_potion"));
        registerItem(439, "spectral_arrow", new ItemSpectralArrow().c("spectral_arrow"));
        registerItem(440, "tipped_arrow", new ItemTippedArrow().c("tipped_arrow"));

        //Add new tile entities
        registerTileEntity(TileEntityEndGateway.class, "EndGateway");
        registerTileEntity(TileEntityStructure.class, "Structure");

        registerRecipes();

        //Fix block references and items, replacing the ones in Minecraft with our new ones
        fixBlocksRefs();
        fixItemsRefs();
    }

     
    public void registerRecipes() {
        Bukkit.resetRecipes();
        addRecipe(new ShapedRecipe(new ItemStack(BEETROOT_SOUP)).shape(new String[] {"rrr", "rrr", " b "}).setIngredient('r', BEETROOT).setIngredient('b', org.bukkit.Material.BOWL));
        addRecipe(new ShapedRecipe(new ItemStack(END_BRICKS)).shape(new String[] {"ee", "ee"}).setIngredient('e', org.bukkit.Material.ENDER_STONE));
        
        //Purpur block recipes
        addRecipe(new FurnaceRecipe(new ItemStack(CHORUS_FRUIT_POPPED), CHORUS_FRUIT));
        addRecipe(new ShapedRecipe(new ItemStack(PURPUR_BLOCK, 4)).shape(new String[] {"pp", "pp"}).setIngredient('p', CHORUS_FRUIT_POPPED));
        addRecipe(new ShapedRecipe(new ItemStack(PURPUR_STAIRS, 4)).shape(new String[] {"p  ", "pp ", "ppp"}).setIngredient('p', PURPUR_BLOCK));
        addRecipe(new ShapedRecipe(new ItemStack(PURPUR_STAIRS, 4)).shape(new String[] {"  p", " pp", "ppp"}).setIngredient('p', PURPUR_BLOCK));
        addRecipe(new ShapedRecipe(new ItemStack(PURPUR_SLAB, 6)).shape(new String[] {"ppp"}).setIngredient('p', PURPUR_BLOCK));
        addRecipe(new ShapedRecipe(new ItemStack(PURPUR_PILLAR)).shape(new String[] {"s", "s"}).setIngredient('s', PURPUR_SLAB));
        
        //Arrows
        addRecipe(new ShapedRecipe(new ItemStack(SPECTRAL_ARROW, 2)).shape(new String[] {" d ", "dad", " d "}).setIngredient('d', org.bukkit.Material.GLOWSTONE_DUST).setIngredient('a', org.bukkit.Material.ARROW));
    }
     

    private void addRecipe(Recipe recipe) {
        Bukkit.getServer().addRecipe(recipe);
    }

    public static void registerBlock(int id, String name, Block block) {
        MinecraftKey stringkey = new MinecraftKey(name);
        Block.REGISTRY.a(id, stringkey, block);
        for (IBlockData blockdata : block.P().a()) {
            final int stateId = (id << 4) | block.toLegacyData(blockdata);
            Block.d.a(blockdata, stateId);
        }
    }

    public static void registerBlock(int id, String name, Block block, Item item) {
        MinecraftKey stringkey = new MinecraftKey(name);
        Block.REGISTRY.a(id, stringkey, block);
        for (IBlockData blockdata : block.P().a()) {
            final int stateId = (id << 4) | block.toLegacyData(blockdata);
            Block.d.a(blockdata, stateId);
        }
        Item.REGISTRY.a(id, stringkey, item);
        ReflectionUtils.<Map<Block, Item>>getFieldValue(Item.class, "a", null).put(block, item);
    }

    public static void registerItem(int id, String name, Item item) {
        Item.REGISTRY.a(id, new MinecraftKey(name), item);
    }

    public static void registerTileEntity(Class<? extends TileEntity> entityClass, String name) {
        ReflectionUtils.<Map<String, Class<? extends TileEntity>>>getFieldValue(TileEntity.class, "f", null).put(name, entityClass);
        ReflectionUtils.<Map<Class<? extends TileEntity>, String>>getFieldValue(TileEntity.class, "g", null).put(entityClass, name);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String name, int id) {
        ReflectionUtils.<Map<String, Class<? extends Entity>>>getFieldValue(EntityTypes.class, "c", null).put(name, entityClass);
        ReflectionUtils.<Map<Class<? extends Entity>, String>>getFieldValue(EntityTypes.class, "d", null).put(entityClass, name);
        ReflectionUtils.<Map<Integer, Class<? extends Entity>>>getFieldValue(EntityTypes.class, "e", null).put(id, entityClass);
        ReflectionUtils.<Map<Class<? extends Entity>, Integer>>getFieldValue(EntityTypes.class, "f", null).put(entityClass, id);
        ReflectionUtils.<Map<String, Integer>>getFieldValue(EntityTypes.class, "g", null).put(name, id);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String name, int id, int monsterEgg, int monsterEggData) {
        registerEntity(entityClass, name, id);
        EntityTypes.eggInfo.put(id, new EntityTypes.MonsterEggInfo(id, monsterEgg, monsterEggData));
    }

    public static void registerPotionEffect(int effectId, String durations, String amplifier) {
        ReflectionUtils.<Map<Integer, String>>getFieldValue(PotionBrewer.class, "effectDurations", null).put(effectId, durations);
        ReflectionUtils.<Map<Integer, String>>getFieldValue(PotionBrewer.class, "effectAmplifiers", null).put(effectId, amplifier);
    }

    public static void registerEnchantment(Enchantment enhcantment) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.setAccessible(org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew")).set(null, true);
        ArrayList<Enchantment> enchants = new ArrayList<>(Arrays.asList(Enchantment.b));
        enchants.add(enhcantment);
        ReflectionUtils.setFinalField(Enchantment.class.getField("b"), null, enchants.toArray(new Enchantment[enchants.size()]));
        ReflectionUtils.setAccessible(org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew")).set(null, false);
    }

    private static void fixBlocksRefs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        for (Field field : Blocks.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (Block.class.isAssignableFrom(field.getType())) {
                Block block = (Block) field.get(null);
                Block newblock = Block.getById(Block.getId(block));
                if (block != newblock) {
                    ReflectionUtils.setFinalField(field, null, newblock);
                }
            }
        }
    }

    private static void fixItemsRefs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        for (Field field : Items.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (Item.class.isAssignableFrom(field.getType())) {
                Item block = (Item) field.get(null);
                Item newblock = Item.getById(Item.getId(block));
                if (block != newblock) {
                    ReflectionUtils.setFinalField(field, null, newblock);
                }
            }
        }
    }

}
