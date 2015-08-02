package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.blocks.util.WrappedBlock;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumDirection.EnumAxis;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.World;

public class BlockRotatable extends WrappedBlock {

    public static final BlockStateEnum<EnumAxis> AXIS = BlockStateEnum.of("axis", EnumDirection.EnumAxis.class);

    public BlockRotatable(Material material) {
        super(material, material.r());
    }

    public BlockRotatable(Material material, MaterialMapColor mapcolor) {
        super(material, mapcolor);
    }

    @Override
    public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
        return super.a(iblockaccess, blockposition, iblockdata, enumdirection); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IBlockData fromLegacyData(int var1) {
        EnumDirection.EnumAxis var2 = EnumDirection.EnumAxis.Y;
        int var3 = var1 & 12;
        if (var3 == 4) {
            var2 = EnumDirection.EnumAxis.X;
        } else if (var3 == 8) {
            var2 = EnumDirection.EnumAxis.Z;
        }

        return getBlockData().set(AXIS, var2);
    }

    @Override
    public int toLegacyData(IBlockData var1) {
        int var2 = 0;
        EnumDirection.EnumAxis var3 = var1.get(AXIS);
        if (var3 == EnumDirection.EnumAxis.X) {
            var2 |= 4;
        } else if (var3 == EnumDirection.EnumAxis.Z) {
            var2 |= 8;
        }

        return var2;
    }

    @Override
    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[]{AXIS});
    }

    @Override
    protected ItemStack createItemStack(IBlockData var1) {
        return new ItemStack(Item.getItemOf(this));
    }

    @Override
    public IBlockData getPlacedState(World var1, BlockPosition var2, EnumDirection var3, float var4, float var5, float var6, int var7, EntityLiving var8) {
        return super.getPlacedState(var1, var2, var3, var4, var5, var6, var7, var8).set(AXIS, var3.k());
    }
}
