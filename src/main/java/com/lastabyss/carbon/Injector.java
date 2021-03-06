package com.lastabyss.carbon;

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
import com.lastabyss.carbon.effects.NewMobEffectType;
import com.lastabyss.carbon.entities.EntityShulker;
import com.lastabyss.carbon.entities.EntityShulkerBullet;
import com.lastabyss.carbon.entities.EntitySpectralArrow;
import com.lastabyss.carbon.entities.EntityTippedArrow;
import com.lastabyss.carbon.generators.end.WorldGenEndCity.WorldGenEndCityStart;
import com.lastabyss.carbon.generators.end.WorldGenEndCityPieces.CityPiece;
import com.lastabyss.carbon.items.ItemChorusFruit;
import com.lastabyss.carbon.items.ItemNewArrow;
import com.lastabyss.carbon.items.ItemNewBow;
import com.lastabyss.carbon.items.ItemSpectralArrow;
import com.lastabyss.carbon.items.ItemSplashPotion;
import com.lastabyss.carbon.items.ItemStep;
import com.lastabyss.carbon.items.ItemTippedArrow;
import com.lastabyss.carbon.network.NetworkInjector;
import com.lastabyss.carbon.staticaccess.EffectList;
import com.lastabyss.carbon.staticaccess.MaterialList;
import com.lastabyss.carbon.staticaccess.ParticleList;
import com.lastabyss.carbon.utils.FixedChatSerializer;
import com.lastabyss.carbon.utils.ReflectionUtils;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
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
import net.minecraft.server.v1_8_R3.WorldGenFactory;
import net.minecraft.server.v1_8_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * The injector class is the driver behind Carbon.
 *
 * @author Navid
 */
public class Injector {

    private Carbon plugin;
    public Injector(Carbon plugin) {
        this.plugin = plugin;
    }

    private static boolean injectionFinished;

    public static boolean isFinished() {
        return injectionFinished;
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
        registerItem(261, "bow", new ItemNewBow().c("bow"));
        registerItem(262, "arrow", new ItemNewArrow().c("arrow"));
        registerItem(432, "chorus_fruit", new ItemChorusFruit().h().c("chorusFruit"));
        registerItem(433, "chorus_fruit_popped", (new Item()).c("chorusFruitPopped"));
        registerItem(434, "beetroot", (new ItemFood(1, 0.6F, false)).c("beetroot"));
        registerItem(435, "beetroot_seeds", new ItemSeeds(beetroots, Blocks.FARMLAND).c("beetroot_seeds"));
        registerItem(436, "beetroot_soup", new ItemSoup(6).c("beetroot_soup"));
        //Skip 437 because there is nothing there apparently... spooky
        registerItem(438, "splash_potion", new ItemSplashPotion().c("splash_potion"));
        registerItem(439, "spectral_arrow", new ItemSpectralArrow().c("spectral_arrow"));
        registerItem(440, "tipped_arrow", new ItemTippedArrow().c("tipped_arrow"));

        //Add new tile entities
        registerTileEntity(TileEntityEndGateway.class, "EndGateway");
        registerTileEntity(TileEntityStructure.class, "Structure");

        //Add new entities
        registerEntity(EntityTippedArrow.class, "TippedArrow", 23);
        registerEntity(EntitySpectralArrow.class, "SpectralArrow", 24);
        registerEntity(EntityShulkerBullet.class, "ShulkerBullet", 25);
        registerEntity(EntityShulker.class, "Shulker", 69, 9725844, 5060690);

        //Add new effects
        prepareMobEffectRegistration(2);
        new NewMobEffectType(24, new MinecraftKey("glowing"), false, 9740385).c("effect.glowing");
        new NewMobEffectType(25, new MinecraftKey("levitation"), true, 13565951).c("potion.levitation");
        PotionEffectType.stopAcceptingRegistrations();

        //Add new recipes
        registerRecipes();

        //Add worldgen factory entry
        registerWorldGenFactoryAddition(false, CityPiece.class, "ECP");
        registerWorldGenFactoryAddition(true, WorldGenEndCityStart.class, "EndCity");

        //Make sure that enums are injected at start
        MaterialList.init();
        ParticleList.init();

        //Fix block references and items, replacing the ones in Minecraft with our new ones
        fixBlocksRefs();
        fixItemsRefs();

        injectionFinished = true;
    }

     
    public void registerRecipes() {
        Bukkit.resetRecipes();
        addRecipe(new ShapedRecipe(new ItemStack(MaterialList.BEETROOT_SOUP)).shape(new String[] {"rrr", "rrr", " b "}).setIngredient('r', MaterialList.BEETROOT).setIngredient('b', org.bukkit.Material.BOWL));
        addRecipe(new ShapedRecipe(new ItemStack(MaterialList.END_BRICKS)).shape(new String[] {"ee", "ee"}).setIngredient('e', org.bukkit.Material.ENDER_STONE));
        
        //Purpur block recipes
        addRecipe(new FurnaceRecipe(new ItemStack(MaterialList.CHORUS_FRUIT_POPPED), MaterialList.CHORUS_FRUIT));
        addRecipe(new ShapedRecipe(new ItemStack(MaterialList.PURPUR_BLOCK, 4)).shape(new String[] {"pp", "pp"}).setIngredient('p', MaterialList.CHORUS_FRUIT_POPPED));
        addRecipe(new ShapedRecipe(new ItemStack(MaterialList.PURPUR_STAIRS, 4)).shape(new String[] {"p  ", "pp ", "ppp"}).setIngredient('p', MaterialList.PURPUR_BLOCK));
        addRecipe(new ShapedRecipe(new ItemStack(MaterialList.PURPUR_STAIRS, 4)).shape(new String[] {"  p", " pp", "ppp"}).setIngredient('p', MaterialList.PURPUR_BLOCK));
        addRecipe(new ShapedRecipe(new ItemStack(MaterialList.PURPUR_SLAB, 6)).shape(new String[] {"ppp"}).setIngredient('p', MaterialList.PURPUR_BLOCK));
        addRecipe(new ShapedRecipe(new ItemStack(MaterialList.PURPUR_PILLAR)).shape(new String[] {"s", "s"}).setIngredient('s', MaterialList.PURPUR_SLAB));
        
        //Arrows
        addRecipe(new ShapedRecipe(new ItemStack(MaterialList.SPECTRAL_ARROW, 2)).shape(new String[] {" d ", "dad", " d "}).setIngredient('d', org.bukkit.Material.GLOWSTONE_DUST).setIngredient('a', org.bukkit.Material.ARROW));
    }
     

    private void addRecipe(Recipe recipe) {
        Bukkit.getServer().addRecipe(recipe);
    }

    public void registerEntityHandler() {
        //register task that updates glowing effect on entities
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            private final int GLOWING_BIT = 6;
            @Override
            public void run() {
                for (org.bukkit.World world : Bukkit.getWorlds()) {
                    WorldServer nmsworld = ((CraftWorld) world).getHandle();
                    for (Entity entity : nmsworld.entityList) {
                        if (entity instanceof EntityLiving) {
                            EntityLiving living = (EntityLiving) entity;
                            boolean hasGlowingEffect = living.hasEffect(EffectList.GLOWING);
                            final byte b0 = entity.getDataWatcher().getByte(0);
                            boolean hasGlowingFlag = (b0 & 1 << GLOWING_BIT) != 0;
                            if (hasGlowingEffect != hasGlowingFlag) {
                                if (hasGlowingEffect) {
                                    entity.getDataWatcher().watch(0, (byte)(b0 | 1 << GLOWING_BIT));
                                } else {
                                    entity.getDataWatcher().watch(0, (byte)(b0 & ~(1 << GLOWING_BIT)));
                                }
                            }
                        }
                    }
                }
            }
        }, 0, 1);
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

    public static void prepareMobEffectRegistration(int additionalSlots) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.setFieldValue(PotionEffectType.class, "acceptingNew", null, true);
        PotionEffectType[] oldById = ReflectionUtils.getFieldValue(PotionEffectType.class, "byId", null);
        PotionEffectType[] newById = new PotionEffectType[oldById.length + 2];
        System.arraycopy(oldById, 0, newById, 0, oldById.length);
        ReflectionUtils.setStaticFinalField(PotionEffectType.class, "byId", newById);
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

    public static void registerWorldGenFactoryAddition(boolean isStructureStart, Class<?> clazz, String string) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        ReflectionUtils.setAccessible(WorldGenFactory.class.getDeclaredMethod(isStructureStart ? "b" : "a", Class.class, String.class)).invoke(null, clazz, string);
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
