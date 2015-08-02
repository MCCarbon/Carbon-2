package com.lastabyss.carbon.blocks;

import java.util.Random;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

import com.lastabyss.carbon.blocks.util.MapColorUtil;

public class BlockEndGateway extends BlockContainer {

    public BlockEndGateway(Material material) {
        super(material);
        setLightLevel(1.0F);
    }

    @Override
    public TileEntity a(World world, int var2) {
        return new TileEntityEndGateway();
    }

    @Override
    public AxisAlignedBB getBoundingBox(World world, BlockPosition position, IBlockData blockdata) {
        return null;
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
    public int getDropCount(Random rnd) {
        return 0;
    }

    @Override
    public MaterialMapColor g(IBlockData iblockdata) {
        return MapColorUtil.COLOR30;
    }

}
