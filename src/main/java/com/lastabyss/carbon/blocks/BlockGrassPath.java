package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.blocks.util.WrappedBlock;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDirt;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class BlockGrassPath extends WrappedBlock {

    public BlockGrassPath() {
        super(Material.EARTH);
        setSizes(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        setLightOpacity(255);
    }

    @Override
    public AxisAlignedBB getBoundingBox(World var1, BlockPosition var2, IBlockData var3) {
        return new AxisAlignedBB(var2.getX(), var2.getY(), var2.getZ(), var2.getX() + 1, var2.getY() + 1, var2.getZ() + 1);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public Item getDropType(IBlockData blockdata, Random var2, int var3) {
        return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT), var2, var3);
    }

    @Override
    public void doPhysics(World world, BlockPosition position, IBlockData blockdata, Block block) {
        super.doPhysics(world, position, blockdata, block);
        if (world.getType(position.up()).getBlock().getMaterial().isBuildable()) {
            world.setTypeUpdate(position, Blocks.DIRT.getBlockData());
        }
    }

}
