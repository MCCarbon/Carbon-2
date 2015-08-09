package com.lastabyss.carbon.blocks;

import java.util.Iterator;
import java.util.Random;

import com.lastabyss.carbon.blocks.util.WrappedBlock;
import com.lastabyss.carbon.staticaccess.BlockList;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class BlockChorusPlant extends WrappedBlock {

    public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
    public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
    public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
    public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
    public static final BlockStateBoolean UP = BlockStateBoolean.of("up");
    public static final BlockStateBoolean DOWN = BlockStateBoolean.of("down");

    public BlockChorusPlant() {
        super(Material.PLANT);
        setBlockData(blockStateList.getBlockData().set(NORTH, false).set(EAST, false).set(SOUTH, false).set(WEST, false).set(UP, false).set(DOWN, false));
    }

    @Override
    public IBlockData updateState(IBlockData blockdata, IBlockAccess access, BlockPosition position) {
        Block var4 = access.getType(position.down()).getBlock();
        Block var5 = access.getType(position.up()).getBlock();
        Block var6 = access.getType(position.north()).getBlock();
        Block var7 = access.getType(position.east()).getBlock();
        Block var8 = access.getType(position.south()).getBlock();
        Block var9 = access.getType(position.west()).getBlock();
        return blockdata.set(DOWN, (var4 == this) || (var4 == BlockList.CHORUS_FLOWER) || (var4 == Blocks.END_STONE)).set(UP, (var5 == this) || (var5 == BlockList.CHORUS_FLOWER)).set(NORTH, var6 == this || (var6 == BlockList.CHORUS_FLOWER)).set(EAST, (var7 == this) || (var7 == BlockList.CHORUS_FLOWER)).set(SOUTH, (var8 == this) || (var8 == BlockList.CHORUS_FLOWER)).set(WEST, (var9 == this) || (var9 == BlockList.CHORUS_FLOWER));
    }

    @Override
    public int toLegacyData(IBlockData var1) {
        return 0;
    }

    @Override
    public void tick(World world, BlockPosition position, IBlockData blockdata, Random rnd) {
        if (!isValid(world, position)) {
            world.setAir(position, true);
        }
    }

    @Override
    public Item getDropType(IBlockData var1, Random var2, int var3) {
        return null;
        // TODO: return chorus fruit
        // return Items.cI;
    }

    @Override
    public int getDropCount(Random rnd) {
        return rnd.nextInt(2);
    }

    @Override
    public AxisAlignedBB getBoundingBox(World var1, BlockPosition var2, IBlockData var3) {
        return super.getBoundingBox(var1, var2, var3);
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
    public void doPhysics(World var1, BlockPosition var2, IBlockData var3, Block var4) {
        if (!isValid(var1, var2)) {
            var1.a(var2, this, 1);
        }
    }

    public boolean isValid(World world, BlockPosition position) {
        boolean upEmpty = world.isEmpty(position.up());
        boolean downEmpty = world.isEmpty(position.down());
        Iterator<EnumDirection> directionIt = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

        Block block;
        do {
            BlockPosition searchposition;
            Block searchblock;
            do {
                if (!directionIt.hasNext()) {
                    Block var10 = world.getType(position.down()).getBlock();
                    return !((var10 != this) && (var10 != Blocks.END_STONE));

                }

                EnumDirection direction = directionIt.next();
                searchposition = position.shift(direction);
                searchblock = world.getType(searchposition).getBlock();
            } while (searchblock != this);

            if (!upEmpty && !downEmpty) {
                return false;
            }

            block = world.getType(searchposition.down()).getBlock();
        } while ((block != this) && (block != Blocks.END_STONE));

        return true;
    }

    @Override
    protected BlockStateList getStateList() {
        return new BlockStateList(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

}
