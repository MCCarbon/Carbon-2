package com.lastabyss.carbon.blocks;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IContainer;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

import com.lastabyss.carbon.blocks.util.WrappedBlock;

public abstract class BlockContainer extends WrappedBlock implements IContainer {

    protected BlockContainer(Material var1) {
        this(var1, var1.r());
    }

    protected BlockContainer(Material var1, MaterialMapColor var2) {
        super(var1, var2);
        isTileEntity = true;
    }

    protected boolean a(World var1, BlockPosition var2, EnumDirection var3) {
        return var1.getType(var2.shift(var3)).getBlock().getMaterial() == Material.CACTUS;
    }

    protected boolean e(World var1, BlockPosition var2) {
        return this.a(var1, var2, EnumDirection.NORTH) || this.a(var1, var2, EnumDirection.SOUTH) || this.a(var1, var2, EnumDirection.WEST) || this.a(var1, var2, EnumDirection.EAST);
    }

    @Override
    public int b() {
        return -1;
    }

    @Override
    public void remove(World var1, BlockPosition var2, IBlockData var3) {
        super.remove(var1, var2, var3);
        var1.t(var2);
    }

    @Override
    public boolean a(World var1, BlockPosition var2, IBlockData var3, int var4, int var5) {
        super.a(var1, var2, var3, var4, var5);
        TileEntity var6 = var1.getTileEntity(var2);
        return var6 == null ? false : var6.c(var4, var5);
    }
}
