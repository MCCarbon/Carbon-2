package com.lastabyss.carbon.blocks;

import java.util.Random;

import com.lastabyss.carbon.blocks.util.MapColorUtil;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class BlockStructureBlock extends BlockContainer {

    public BlockStructureBlock() {
        super(Material.ORE, MapColorUtil.COLOR23);
        setBlockData(blockStateList.getBlockData());
    }

    @Override
    public TileEntity a(World world, int i) {
        return new TileEntityStructure();
    }

    @Override
    public boolean interact(World world, BlockPosition pos, IBlockData blockdata, EntityHuman player, EnumDirection direction, float f1, float f2, float f3) {
        return false;
    }

    @Override
    public void postPlace(World var1, BlockPosition var2, IBlockData var3, EntityLiving var4, ItemStack var5) {
    }

    @Override
    public int getDropCount(Random var1) {
        return 0;
    }

    @Override
    public int b() {
        return 3;
    }

}
