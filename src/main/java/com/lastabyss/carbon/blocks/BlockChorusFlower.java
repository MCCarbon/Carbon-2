package com.lastabyss.carbon.blocks;

import java.util.Iterator;
import java.util.Random;

import com.lastabyss.carbon.blocks.util.AddedBlockList;
import com.lastabyss.carbon.blocks.util.EnumDirectionLimitUtil;
import com.lastabyss.carbon.blocks.util.WrappedBlock;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.EnumDirection.EnumDirectionLimit;

public class BlockChorusFlower extends WrappedBlock {

    public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 5);

    public BlockChorusFlower() {
        super(Material.PLANT);
        setBlockData(blockStateList.getBlockData().set(AGE, 0));
        setTicking(true);
    }

    @Override
    public Item getDropType(IBlockData var1, Random var2, int var3) {
        return null;
    }

    @Override
    public void tick(World world, BlockPosition position, IBlockData blockdata, Random random) {
        if (!isValid(world, position)) {
            world.setAir(position, true);
        } else if (world.isEmpty(position.up())) {
            int agev = blockdata.get(AGE);
            if ((agev < 5) && (random.nextInt(1) == 0)) {
                boolean var6 = false;
                boolean var7 = false;
                Block var8 = world.getType(position.down()).getBlock();
                int var9;
                if (var8 == Blocks.END_STONE) {
                    var6 = true;
                } else if (var8 != AddedBlockList.CHORUS_PLANT) {
                    if (var8 == Blocks.AIR) {
                        var6 = true;
                    }
                } else {
                    var9 = 1;

                    int var10;
                    for (var10 = 0; var10 < 4; ++var10) {
                        Block var11 = world.getType(position.down(var9 + 1)).getBlock();
                        if (var11 != AddedBlockList.CHORUS_PLANT) {
                            if (var11 == Blocks.END_STONE) {
                                var7 = true;
                            }
                            break;
                        }

                        ++var9;
                    }

                    var10 = 4;
                    if (var7) {
                        ++var10;
                    }

                    if ((var9 < 2) || (random.nextInt(var10) >= var9)) {
                        var6 = true;
                    }
                }

                if (var6 && a(world, position.up(), (EnumDirection) null) && world.isEmpty(position.up(2))) {
                    world.setTypeAndData(position, AddedBlockList.CHORUS_PLANT.getBlockData(), 2);
                    world.setTypeAndData(position.up(), getBlockData().set(AGE, agev), 2);
                } else if (agev < 4) {
                    var9 = random.nextInt(4);
                    boolean var15 = false;
                    if (var7) {
                        ++var9;
                    }

                    for (int var14 = 0; var14 < var9; ++var14) {
                        EnumDirection var12 = EnumDirectionLimitUtil.getRandomDirection(EnumDirectionLimit.HORIZONTAL, random);
                        BlockPosition var13 = position.shift(var12);
                        if (world.isEmpty(var13) && world.isEmpty(var13.down()) && a(world, var13, var12.opposite())) {
                            world.setTypeAndData(var13, getBlockData().set(AGE, agev + 1), 2);
                            var15 = true;
                        }
                    }

                    if (var15) {
                        world.setTypeAndData(position, AddedBlockList.CHORUS_PLANT.getBlockData(), 2);
                    } else {
                        world.setTypeAndData(position, blockdata.set(AGE, 5), 2);
                    }
                } else if (agev == 4) {
                    world.setTypeAndData(position, blockdata.set(AGE, 5), 2);
                }
            }
        }

    }

    private static boolean a(World world, BlockPosition position, EnumDirection direction) {
        Iterator<EnumDirection> var3 = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

        EnumDirection var4;
        do {
            if (!var3.hasNext()) {
                return true;
            }

            var4 = var3.next();
        } while ((var4 == direction) || world.isEmpty(position.shift(var4)));

        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canPlace(World world, BlockPosition position) {
        return super.canPlace(world, position) && isValid(world, position);
    }

    @Override
    public void doPhysics(World world, BlockPosition position, IBlockData blockdata, Block block) {
        if (!isValid(world, position)) {
            world.a(position, this, 1);
        }
    }

    public boolean isValid(World world, BlockPosition position) {
        Block var3 = world.getType(position.down()).getBlock();
        if ((var3 != AddedBlockList.CHORUS_PLANT) && (var3 != Blocks.END_STONE)) {
            if (var3 == Blocks.AIR) {
                int var4 = 0;

                for (EnumDirection var6 : EnumDirectionLimit.HORIZONTAL) {
                    Block var7 = world.getType(position.shift(var6)).getBlock();
                    if (var7 == AddedBlockList.CHORUS_PLANT) {
                        ++var4;
                    } else if (var7 != Blocks.AIR) {
                        return false;
                    }
                }

                return var4 == 1;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void breakBlockNaturally(World world, EntityHuman player, BlockPosition pos, IBlockData blockdata, TileEntity tile, ItemStack itemstack) {
        super.breakBlockNaturally(world, player, pos, blockdata, tile, itemstack);
        dropItem(world, pos, (new ItemStack(Item.getItemOf(this))));
    }

    @Override
    protected ItemStack createItemStack(IBlockData blockdata) {
        return null;
    }

    @Override
    public IBlockData fromLegacyData(int meta) {
        return getBlockData().set(AGE, meta);
    }

    @Override
    public int toLegacyData(IBlockData blockdata) {
        return blockdata.get(AGE);
    }

    @Override
    protected BlockStateList getStateList() {
        return new BlockStateList(this, AGE);
    }

    @Override
    public void onPlace(World world, BlockPosition var2, IBlockData var3) {
        super.onPlace(world, var2, var3);
    }

    public static void a(World world, BlockPosition position, Random rnd, int var3) {
        world.setTypeAndData(position, AddedBlockList.CHORUS_PLANT.getBlockData(), 2);
        a(world, position, rnd, position, var3, 0);
    }

    private static void a(World var0, BlockPosition var1, Random var2, BlockPosition var3, int var4, int var5) {
        int var6 = var2.nextInt(4) + 1;
        if (var5 == 0) {
            ++var6;
        }

        for (int var7 = 0; var7 < var6; ++var7) {
            BlockPosition var8 = var1.up(var7 + 1);
            if (!a(var0, var8, (EnumDirection) null)) {
                return;
            }

            var0.setTypeAndData(var8, AddedBlockList.CHORUS_PLANT.getBlockData(), 2);
        }

        boolean var12 = false;
        if (var5 < 4) {
            int var13 = var2.nextInt(4);
            if (var5 == 0) {
                ++var13;
            }

            for (int var9 = 0; var9 < var13; ++var9) {
                EnumDirection var10 = EnumDirectionLimitUtil.getRandomDirection(EnumDirectionLimit.HORIZONTAL, var2);
                BlockPosition var11 = var1.up(var6).shift(var10);
                if ((Math.abs(var11.getX() - var3.getX()) < var4) && (Math.abs(var11.getZ() - var3.getZ()) < var4) && var0.isEmpty(var11) && var0.isEmpty(var11.down()) && a(var0, var11, var10.opposite())) {
                    var12 = true;
                    var0.setTypeAndData(var11, AddedBlockList.CHORUS_PLANT.getBlockData(), 2);
                    a(var0, var11, var2, var3, var4, var5 + 1);
                }
            }
        }

        if (!var12) {
            var0.setTypeAndData(var1.up(var6), AddedBlockList.CHORUS_FLOWER.getBlockData().set(AGE, 5), 2);
        }
    }

}
