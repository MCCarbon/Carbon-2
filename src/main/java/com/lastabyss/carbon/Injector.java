package com.lastabyss.carbon;

import gnu.trove.map.TObjectIntMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EntityTypes.MonsterEggInfo;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.PotionBrewer;
import net.minecraft.server.v1_8_R3.TileEntity;

import org.bukkit.Bukkit;
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
import com.lastabyss.carbon.blocks.BlockStructureBlock;
import com.lastabyss.carbon.blocks.TileEntityEndGateway;
import com.lastabyss.carbon.blocks.TileEntityStructure;
import com.lastabyss.carbon.blocks.util.SoundUtil;
import com.lastabyss.carbon.blocks.util.WrappedBlock;
import com.lastabyss.carbon.items.ItemBeetrootSeeds;
import com.lastabyss.carbon.items.ItemBeetrootSoup;
import com.lastabyss.carbon.items.ItemBeetroot;
import com.lastabyss.carbon.items.ItemChorusFruit;
import com.lastabyss.carbon.items.ItemPoppedChorusFruit;
import com.lastabyss.carbon.network.NetworkInjector;
import com.lastabyss.carbon.utils.Utils;

/**
 * The injector class is the driver behind Carbon.
 *
 * @author Navid
 */
public class Injector {

    public Injector(Carbon plugin) {
    }

    public void registerAll() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvocationTargetException, NoSuchMethodException {
        //Inject network
        NetworkInjector.inject();
        //Add new blocks
        Block beetroots = new BlockBeetroots().setName("beetroots");
        
        Utils.addMaterial("END_ROD_BLOCK", 198);
        registerBlock(198, "end_rod", new BlockEndRod().setStrength(0.0F).setLightLevel(0.9375F).setStepSound(SoundUtil.WOOD).setName("endRod"));

        Utils.addMaterial("CHORUS_PLANT_BLOCK", 199);
        registerBlock(199, "chorus_plant", new BlockChorusPlant().setStrength(0.4F).setStepSound(SoundUtil.WOOD).setName("chorusPlant"));

        Utils.addMaterial("CHORUS_FLOWER_MATERIAL", 200);
        registerBlock(200, "chorus_flower", new BlockChorusFlower().setStrength(0.4F).setStepSound(SoundUtil.WOOD).setName("chorusFlower"));

        Utils.addMaterial("PURPUR_BLOCK", 201);
        Block purpur = new WrappedBlock(Material.STONE).setStrength(1.5F).setExplosionResist(10.0F).setStepSound(SoundUtil.STONE2).setName("purpurBlock");
        registerBlock(201, "purpur_block", purpur);

        Utils.addMaterial("PURPUR_PILLAR", 202);
        registerBlock(202, "purpur_pillar", new BlockRotatable(Material.STONE).setStrength(1.5F).setExplosionResist(10.0F).setStepSound(SoundUtil.STONE2).setName("purpurPillar"));

        Utils.addMaterial("PURPUR_STAIRS", 203);
        registerBlock(203, "purpur_stairs", new BlockStairs(purpur.getBlockData()).setName("stairsPurpur"));

        Utils.addMaterial("PURPUR_DOUBLE_SLAB", 204);
        registerBlock(204, "purpur_double_slab", new BlockPurpurSlabAbstract.BlockPurpurDoubleSlab().setStrength(2.0F).setExplosionResist(10.0F).setStepSound(SoundUtil.STONE2).setName("purpurSlab"));

        Utils.addMaterial("PURPUR_SLAB", 205);
        registerBlock(205, "purpur_slab", new BlockPurpurSlabAbstract.BlockPuprpurSlab().setStrength(2.0F).setExplosionResist(10.0F).setStepSound(SoundUtil.STONE2).setName("purpurSlab"));

        Utils.addMaterial("END_BRICKS", 206);
        registerBlock(206, "end_bricks", new WrappedBlock(Material.STONE).setStepSound(SoundUtil.STONE2).setStrength(0.8F).setName("endBricks"));

        Utils.addMaterial("BEETROOTS", 207);
        registerBlock(207, "beetroots", beetroots);
        
        Utils.addMaterial("GRASS_PATH", 208);
        registerBlock(208, "grass_path", new BlockGrassPath().setStrength(0.65F).setStepSound(SoundUtil.GRASS).setName("grassPath").setUnbreakable());

        Utils.addMaterial("END_GATEWAY", 209);
        registerBlock(209, "end_gateway", new BlockEndGateway(Material.PORTAL).setStrength(-1.0F).setExplosionResist(6000000.0F));

        Utils.addMaterial("STRUCTURE_BLOCK", 255);
        registerBlock(255, "structure_block", new BlockStructureBlock().setUnbreakable().setExplosionResist(6000000.0F).setName("structureBlock").setLightLevel(1.0F));

        Utils.addMaterial("CHORUS_FRUIT", 432);
        registerItem(432, "chorus_fruit", new ItemChorusFruit());
        
        Utils.addMaterial("CHORUS_FRUIT_POPPED", 433);
        registerItem(433, "chorus_fruit_popped", new ItemPoppedChorusFruit());
        
        Utils.addMaterial("BEETROOT_SEEDS", 434);
        registerItem(434, "beetroot_seeds", new ItemBeetrootSeeds(beetroots));
        
        Utils.addMaterial("CHORUS_FRUIT", 435);
        registerItem(435, "chorus_fruit", new ItemChorusFruit());
        
        Utils.addMaterial("BEETROOT_SOUP", 436);
        registerItem(436, "beetroot_soup", new ItemBeetrootSoup());
        
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
        addRecipe(new ShapedRecipe(new ItemStack(436)).shape(new String[] {"rrr", "rrr", " b "}).setIngredient('r', org.bukkit.Material.valueOf("BEETROOT")).setIngredient('b', org.bukkit.Material.BOWL));
    }

    private void addRecipe(Recipe recipe) {
        Bukkit.getServer().addRecipe(recipe);
    }

    public void registerBlock(int id, String name, Block block) {
        MinecraftKey stringkey = new MinecraftKey(name);
        Block.REGISTRY.a(id, stringkey, block);
        Iterator<IBlockData> blockdataiterator = block.P().a().iterator();
        while (blockdataiterator.hasNext()) {
            IBlockData blockdata = blockdataiterator.next();
            final int stateId = (id << 4) | block.toLegacyData(blockdata);
            Block.d.a(blockdata, stateId);
        }
    }

    @SuppressWarnings("unchecked")
    public void registerBlock(int id, String name, Block block, Item item) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        MinecraftKey stringkey = new MinecraftKey(name);
        Block.REGISTRY.a(id, stringkey, block);
        Iterator<IBlockData> blockdataiterator = block.P().a().iterator();
        while (blockdataiterator.hasNext()) {
            IBlockData blockdata = blockdataiterator.next();
            final int stateId = (id << 4) | block.toLegacyData(blockdata);
            Block.d.a(blockdata, stateId);
        }
        Item.REGISTRY.a(id, stringkey, item);
        ((Map<Block, Item>) Utils.<Field>setAccessible(Item.class.getDeclaredField("a")).get(null)).put(block, item);
    }

    public void registerItem(int id, String name, Item item) {
        Item.REGISTRY.a(id, new MinecraftKey(name), item);
    }

    @SuppressWarnings("unchecked")
    public void registerTileEntity(Class<? extends TileEntity> entityClass, String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        ((Map<String, Class<? extends TileEntity>>) Utils.<Field>setAccessible(TileEntity.class.getDeclaredField("f")).get(null)).put(name, entityClass);
        ((Map<Class<? extends TileEntity>, String>) Utils.<Field>setAccessible(TileEntity.class.getDeclaredField("g")).get(null)).put(entityClass, name);
    }

    @SuppressWarnings("unchecked")
    public void registerDataWatcherType(Class<?> type, int id) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field classToIdField = DataWatcher.class.getDeclaredField("classToId");
        classToIdField.setAccessible(true);
        ((TObjectIntMap<Class<?>>) classToIdField.get(null)).put(type, id);
    }

    @SuppressWarnings("unchecked")
    public void registerEntity(Class<? extends Entity> entityClass, String name, int id) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        ((Map<String, Class<? extends Entity>>) Utils.<Field>setAccessible(EntityTypes.class.getDeclaredField("c")).get(null)).put(name, entityClass);
        ((Map<Class<? extends Entity>, String>) Utils.<Field>setAccessible(EntityTypes.class.getDeclaredField("d")).get(null)).put(entityClass, name);
        ((Map<Integer, Class<? extends Entity>>) Utils.<Field>setAccessible(EntityTypes.class.getDeclaredField("e")).get(null)).put(id, entityClass);
        ((Map<Class<? extends Entity>, Integer>) Utils.<Field>setAccessible(EntityTypes.class.getDeclaredField("f")).get(null)).put(entityClass, id);
        ((Map<String, Integer>) Utils.<Field>setAccessible(EntityTypes.class.getDeclaredField("g")).get(null)).put(name, id);
    }

    public void registerEntity(Class<? extends Entity> entityClass, String name, int id, int monsterEgg, int monsterEggData) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        registerEntity(entityClass, name, id);
        EntityTypes.eggInfo.put(id, new MonsterEggInfo(id, monsterEgg, monsterEggData));
    }

    @SuppressWarnings("unchecked")
    public void registerPotionEffect(int effectId, String durations, String amplifier) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        ((Map<Integer, String>) Utils.<Field>setAccessible(PotionBrewer.class.getDeclaredField("effectDurations")).get(null)).put(effectId, durations);
        ((Map<Integer, String>) Utils.<Field>setAccessible(PotionBrewer.class.getDeclaredField("effectAmplifiers")).get(null)).put(effectId, amplifier);
    }

    public void registerEnchantment(Enchantment enhcantment) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Utils.<Field>setAccessible(org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew")).set(null, true);
        ArrayList<Enchantment> enchants = new ArrayList<Enchantment>(Arrays.asList(Enchantment.b));
        enchants.add(enhcantment);
        Utils.setFinalField(Enchantment.class.getField("b"), null, enchants.toArray(new Enchantment[0]));
        Utils.<Field>setAccessible(org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew")).set(null, false);
    }

    private void fixBlocksRefs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        for (Field field : Blocks.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (Block.class.isAssignableFrom(field.getType())) {
                Block block = (Block) field.get(null);
                Block newblock = Block.getById(Block.getId(block));
                if (block != newblock) {
                    Utils.setFinalField(field, null, newblock);
                }
            }
        }
    }

    private void fixItemsRefs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        for (Field field : Items.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (Item.class.isAssignableFrom(field.getType())) {
                Item block = (Item) field.get(null);
                Item newblock = Item.getById(Item.getId(block));
                if (block != newblock) {
                    Utils.setFinalField(field, null, newblock);
                }
            }
        }
    }

}
