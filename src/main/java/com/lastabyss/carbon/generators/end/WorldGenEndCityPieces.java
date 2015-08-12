package com.lastabyss.carbon.generators.end;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.StructureBoundingBox;
import net.minecraft.server.v1_8_R3.StructurePiece;
import net.minecraft.server.v1_8_R3.Tuple;
import net.minecraft.server.v1_8_R3.World;

import com.google.common.collect.Lists;
import com.lastabyss.carbon.generators.end.unknown.class_asp;
import com.lastabyss.carbon.generators.end.unknown.class_asu;
import com.lastabyss.carbon.generators.schematics.StructureSchmeatic;
import com.lastabyss.carbon.generators.schematics.StructureSchematicNBTLoader;
import com.lastabyss.carbon.types.EnumRotation;
import com.lastabyss.carbon.utils.ReflectionUtils;

public class WorldGenEndCityPieces {

    public static final StructureSchematicNBTLoader LOADER = new StructureSchematicNBTLoader();

    private static final class_asu b = new class_asu().a(true);
    private static final class_asu c = new class_asu().a(true).b(true);
    // private static final List d;
    private static final List<Item> e = Lists.newArrayList((new Item[] { Items.DIAMOND_SWORD, Items.DIAMOND_BOOTS, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_HELMET, Items.DIAMOND_PICKAXE, Items.DIAMOND_SHOVEL, Items.IRON_SWORD, Items.IRON_BOOTS, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_HELMET, Items.IRON_PICKAXE, Items.IRON_SHOVEL }));
    private static final List<Tuple<EnumRotation, BlockPosition>> g = new ArrayList<Tuple<EnumRotation, BlockPosition>>();
    private static final List<Tuple<EnumRotation, BlockPosition>> j = new ArrayList<Tuple<EnumRotation, BlockPosition>>();
    private static final WorldGenEndCityPieces.PieceGenerator base1 = new WorldGenEndCityPieces.PieceGenerator() {
        @Override
        public void prepare() {
        }
        @Override
        public boolean generate(int var1, CityPiece var2, BlockPosition var3, List<StructurePiece> var4, Random var5) {
            if (var1 > 8) {
                return false;
            } else {
                EnumRotation var6 = var2.b.c();
                CityPiece var7;
                var4.add(var7 = WorldGenEndCityPieces.b(var2, var3, "base_floor", var6, true));
                int var8 = var5.nextInt(3);
                if (var8 == 0) {
                    var4.add(WorldGenEndCityPieces.b(var7, new BlockPosition(-1, 4, -1), "base_roof", var6, true));
                } else if (var8 == 1) {
                    var4.add(var7 = WorldGenEndCityPieces.b(var7, new BlockPosition(-1, 0, -1), "second_floor_2", var6, false));
                    var4.add(var7 = WorldGenEndCityPieces.b(var7, new BlockPosition(-1, 8, -1), "second_roof", var6, false));
                    WorldGenEndCityPieces.b(WorldGenEndCityPieces.base2, var1 + 1, var7, (BlockPosition) null, var4, var5);
                } else if (var8 == 2) {
                    var4.add(var7 = WorldGenEndCityPieces.b(var7, new BlockPosition(-1, 0, -1), "second_floor_2", var6, false));
                    var4.add(var7 = WorldGenEndCityPieces.b(var7, new BlockPosition(-1, 4, -1), "third_floor_c", var6, false));
                    var4.add(var7 = WorldGenEndCityPieces.b(var7, new BlockPosition(-1, 8, -1), "third_roof", var6, true));
                    WorldGenEndCityPieces.b(WorldGenEndCityPieces.base2, var1 + 1, var7, (BlockPosition) null, var4, var5);
                }

                return true;
            }
        }
    };
    private static final WorldGenEndCityPieces.PieceGenerator base2 = new WorldGenEndCityPieces.PieceGenerator() {
        @Override
        public void prepare() {
        }
        @Override
        public boolean generate(int var1, CityPiece var2, BlockPosition var3, List<StructurePiece> var4, Random var5) {
            EnumRotation var6 = var2.b.c();
            CityPiece var7;
            var4.add(var7 = WorldGenEndCityPieces.b(var2, new BlockPosition(3 + var5.nextInt(2), -3, 3 + var5.nextInt(2)), "tower_base", var6, true));
            var4.add(var7 = WorldGenEndCityPieces.b(var7, new BlockPosition(0, 7, 0), "tower_piece", var6, true));
            CityPiece var8 = var5.nextInt(3) == 0 ? var7 : null;
            int var9 = 1 + var5.nextInt(3);

            for (int var10 = 0; var10 < var9; ++var10) {
                var4.add(var7 = WorldGenEndCityPieces.b(var7, new BlockPosition(0, 4, 0), "tower_piece", var6, true));
                if ((var10 < (var9 - 1)) && var5.nextBoolean()) {
                    var8 = var7;
                }
            }

            if (var8 != null) {
                Iterator<Tuple<EnumRotation, BlockPosition>> var13 = WorldGenEndCityPieces.g.iterator();

                while (var13.hasNext()) {
                    Tuple<EnumRotation, BlockPosition> var11 = var13.next();
                    if (var5.nextBoolean()) {
                        CityPiece var12;
                        var4.add(var12 = WorldGenEndCityPieces.b(var8, var11.b(), "bridge_end", var6.a(var11.a()), true));
                        WorldGenEndCityPieces.b(WorldGenEndCityPieces.bridge, var1 + 1, var12, (BlockPosition) null, var4, var5);
                    }
                }

                var4.add(WorldGenEndCityPieces.b(var7, new BlockPosition(-1, 4, -1), "tower_top", var6, true));
            } else {
                if (var1 != 7) {
                    return WorldGenEndCityPieces.b(WorldGenEndCityPieces.fatBase, var1 + 1, var7, (BlockPosition) null, var4, var5);
                }

                var4.add(WorldGenEndCityPieces.b(var7, new BlockPosition(-1, 4, -1), "tower_top", var6, true));
            }

            return true;
        }
    };
    private static final WorldGenEndCityPieces.PieceGenerator bridge = new WorldGenEndCityPieces.PieceGenerator() {
        public boolean a;
        @Override
        public void prepare() {
            this.a = false;
        }
        @Override
        public boolean generate(int var1, CityPiece var2, BlockPosition var3, List<StructurePiece> var4, Random var5) {
            EnumRotation var7 = var2.b.c();
            int var8 = var5.nextInt(4) + 1;
            byte var9 = 0;
            CityPiece var6;
            var4.add(var6 = WorldGenEndCityPieces.b(var2, new BlockPosition(0, 0, -4), "bridge_piece", var7, true));
            setSturcturePieceNValue(var6, -1);

            for (int var10 = 0; var10 < var8; ++var10) {
                if (var5.nextBoolean()) {
                    var4.add(var6 = WorldGenEndCityPieces.b(var6, new BlockPosition(0, var9, -4), "bridge_piece", var7, true));
                    var9 = 0;
                } else {
                    if (var5.nextBoolean()) {
                        var4.add(var6 = WorldGenEndCityPieces.b(var6, new BlockPosition(0, var9, -4), "bridge_steep_stairs", var7, true));
                    } else {
                        var4.add(var6 = WorldGenEndCityPieces.b(var6, new BlockPosition(0, var9, -8), "bridge_gentle_stairs", var7, true));
                    }

                    var9 = 4;
                }
            }

            if (!this.a && (var5.nextInt(10 - var1) == 0)) {
                var4.add(WorldGenEndCityPieces.b(var6, new BlockPosition(-8 + var5.nextInt(8), var9, -70 + var5.nextInt(10)), "ship", var7, true));
                this.a = true;
            } else if (!WorldGenEndCityPieces.b(WorldGenEndCityPieces.base1, var1 + 1, var6, new BlockPosition(-3, var9 + 1, -11), var4, var5)) {
                return false;
            }

            var4.add(var6 = WorldGenEndCityPieces.b(var6, new BlockPosition(4, var9, 0), "bridge_end", var7.a(EnumRotation.CLOCKWISE_180), true));
            setSturcturePieceNValue(var6, -1);
            return true;
        }
    };
    private static final WorldGenEndCityPieces.PieceGenerator fatBase = new WorldGenEndCityPieces.PieceGenerator() {
        @Override
        public void prepare() {
        }
        @Override
        public boolean generate(int var1, CityPiece var2, BlockPosition var3, List<StructurePiece> var4, Random var5) {
            EnumRotation var7 = var2.b.c();
            CityPiece var6;
            var4.add(var6 = WorldGenEndCityPieces.b(var2, new BlockPosition(-3, 4, -3), "fat_tower_base", var7, true));
            var4.add(var6 = WorldGenEndCityPieces.b(var6, new BlockPosition(0, 4, 0), "fat_tower_middle", var7, true));

            for (int var8 = 0; (var8 < 2) && (var5.nextInt(3) != 0); ++var8) {
                var4.add(var6 = WorldGenEndCityPieces.b(var6, new BlockPosition(0, 8, 0), "fat_tower_middle", var7, true));
                Iterator<Tuple<EnumRotation, BlockPosition>> var9 = WorldGenEndCityPieces.j.iterator();

                while (var9.hasNext()) {
                    Tuple<EnumRotation, BlockPosition> var10 = var9.next();
                    if (var5.nextBoolean()) {
                        CityPiece var11 = WorldGenEndCityPieces.b(var6, var10.b(), "bridge_end", var7.a(var10.a()), true);
                        var4.add(var11);
                        WorldGenEndCityPieces.b(WorldGenEndCityPieces.bridge, var1 + 1, var11, (BlockPosition) null, var4, var5);
                    }
                }
            }

            var4.add(WorldGenEndCityPieces.b(var6, new BlockPosition(-2, 8, -2), "fat_tower_top", var7, true));
            return true;
        }
    };

    private static CityPiece b(CityPiece var0, BlockPosition var1, String var2, EnumRotation var3, boolean var4) {
        CityPiece var5 = new CityPiece(var2, var0.c, var3, var4);
        BlockPosition var6 = var0.a.a(var0.b, var1, var5.a, var5.b, BlockPosition.ZERO);
        var5.a(var6.getX(), var6.getY(), var6.getZ());
        return var5;
    }

    public static void a(BlockPosition var0, EnumRotation var1, List<StructurePiece> var2, Random var3) {
        fatBase.prepare();
        base1.prepare();
        bridge.prepare();
        base2.prepare();
        CityPiece var4 = new CityPiece("base_floor", var0, var1, true);
        var2.add(var4);
        byte var5 = 0;
        CityPiece var6;
        var2.add(var6 = b(var4, new BlockPosition(-1, 0, -1), "second_floor", var1, false));
        var2.add(var6 = b(var6, new BlockPosition(-1, 4, -1), "third_floor", var1, false));
        var2.add(var6 = b(var6, new BlockPosition(-1, 8, -1), "third_roof", var1, true));
        b(base2, var5 + 1, var6, (BlockPosition) null, var2, var3);
    }

    static void setSturcturePieceNValue(StructurePiece of, int value) {
        ReflectionUtils.setFieldValue(StructurePiece.class, "n", of, value);
    }

    private static boolean b(WorldGenEndCityPieces.PieceGenerator var0, int var1, CityPiece var2, BlockPosition var3, List<StructurePiece> var4, Random var5) {
        if (var1 > 8) {
            return false;
        } else {
            ArrayList<StructurePiece> pieces = new ArrayList<StructurePiece>();
            if (var0.generate(var1, var2, var3, pieces, var5)) {
                boolean var7 = false;
                int var8 = var5.nextInt();
                Iterator<StructurePiece> iterator = pieces.iterator();
                while (iterator.hasNext()) {
                    StructurePiece piece = iterator.next();
                    setSturcturePieceNValue(piece, var8);
                    StructurePiece var11 = StructurePiece.a(var4, piece.c());
                    if ((var11 != null) && (var11.d() != var2.d())) {
                        var7 = true;
                        break;
                    }
                }

                if (!var7) {
                    var4.addAll(pieces);
                    return true;
                }
            }

            return false;
        }
    }

    static {
        // d = Lists.newArrayList((Object[]) (new class_od[] { new
        // class_od(Items.DIAMOND, 0, 2, 7, 5), new class_od(Items.IRON_INGOT,
        // 0, 4, 8, 10), new class_od(Items.GOLD_INGOT, 0, 2, 7, 15), new
        // class_od(Items.EMERALD, 0, 2, 6, 2), new
        // class_od(Items.BEETROOT_SEEDS, 0, 1, 10, 5), new
        // class_od(Items.SADDLE, 0, 1, 1, 3), new
        // class_od(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new
        // class_od(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new
        // class_od(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1) }));
        g.add(new Tuple<EnumRotation, BlockPosition>(EnumRotation.NONE, new BlockPosition(1, -1, 0)));
        g.add(new Tuple<EnumRotation, BlockPosition>(EnumRotation.CLOCKWISE_90, new BlockPosition(6, -1, 1)));
        g.add(new Tuple<EnumRotation, BlockPosition>(EnumRotation.COUNTERCLOCKWISE_90, new BlockPosition(0, -1, 5)));
        g.add(new Tuple<EnumRotation, BlockPosition>(EnumRotation.CLOCKWISE_180, new BlockPosition(5, -1, 6)));
        j.add(new Tuple<EnumRotation, BlockPosition>(EnumRotation.NONE, new BlockPosition(4, -1, 0)));
        j.add(new Tuple<EnumRotation, BlockPosition>(EnumRotation.CLOCKWISE_90, new BlockPosition(12, -1, 4)));
        j.add(new Tuple<EnumRotation, BlockPosition>(EnumRotation.COUNTERCLOCKWISE_90, new BlockPosition(0, -1, 8)));
        j.add(new Tuple<EnumRotation, BlockPosition>(EnumRotation.CLOCKWISE_180, new BlockPosition(8, -1, 12)));
    }

    interface PieceGenerator {
        void prepare();

        boolean generate(int var1, CityPiece var2, BlockPosition var3, List<StructurePiece> var4, Random var5);
    }

    public static class CityPiece extends class_asp {

        private String template;
        private EnumRotation rotation;
        private boolean ow;

        public CityPiece() {
        }

        public CityPiece(String var1, BlockPosition var2, EnumRotation var3, boolean var4) {
            super(0);
            this.template = var1;
            this.rotation = var3;
            this.ow = var4;
            this.a(var2);
        }

        private void a(BlockPosition position) {
            StructureSchmeatic schematic = WorldGenEndCityPieces.LOADER.getSchematic(new MinecraftKey("endcity/" + this.template));
            class_asu var3 = null;
            if (this.ow) {
                var3 = WorldGenEndCityPieces.b.a().a(this.rotation);
            } else {
                var3 = WorldGenEndCityPieces.c.a().a(this.rotation);
            }

            this.a(schematic, position, var3);
        }

        @Override
        protected void a(NBTTagCompound var1) {
            super.a(var1);
            var1.setString("Template", this.template);
            var1.setString("Rot", this.rotation.name());
            var1.setBoolean("OW", this.ow);
        }

        @Override
        protected void b(NBTTagCompound var1) {
            super.b(var1);
            this.template = var1.getString("Template");
            this.rotation = EnumRotation.valueOf(var1.getString("Rot"));
            this.ow = var1.getBoolean("OW");
            this.a(this.c);
        }

        @Override
        protected void a(String var1, BlockPosition var2, World var3, Random var4, StructureBoundingBox var5) {
           /* if (var1.startsWith("Chest")) {
                BlockPosition var6 = var2.down();
                if (var5.b(var6)) {
                    TileEntity var7 = var3.getTileEntity(var6);
                    if (var7 instanceof TileEntityChest) {
                        ArrayList var8 = Lists.newArrayList(WorldGenEndCityPieces.d);
                        Iterator var9 = WorldGenEndCityPieces.e.iterator();

                        while (var9.hasNext()) {
                            Item var10 = (Item) var9.next();
                            var8.add(new class_od(EnchantmentManager.a(var4, new ItemStack(var10), 20 + var4.nextInt(20)), 1, 1, 3));
                        }

                        class_od.a(var4, var8, ((TileEntityChest) var7), 2 + var4.nextInt(5));
                    }
                }
            } else if (var1.startsWith("Sentry")) {
                EntityShulker var11 = new EntityShulker(var3);
                var11.setPosition((double) var2.getX() + 0.5D, (double) var2.getY() + 0.5D, (double) var2.getZ() + 0.5D);
                var11.f(var2);
                var3.addEntity((Entity) var11);
            }*/

        }
    }

}
