package com.lastabyss.carbon.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

import com.lastabyss.carbon.blocks.util.WrappedBlock;

public abstract class BlockStepAbstract extends WrappedBlock {

    public static final BlockStateEnum<BlockStepAbstract.EnumSlabHalf> HALF = BlockStateEnum.of("half", BlockStepAbstract.EnumSlabHalf.class);

    public BlockStepAbstract(Material material) {
        super(material);
        if (this.l()) {
            r = true;
        } else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }

        e(255);
    }

    @Override
    public boolean I() {
        return false;
    }

    @Override
    public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
        if (this.l()) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            IBlockData iblockdata = iblockaccess.getType(blockposition);

            if (iblockdata.getBlock() == this) {
                if (iblockdata.get(BlockStepAbstract.HALF) == BlockStepAbstract.EnumSlabHalf.TOP) {
                    this.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                } else {
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                }
            }

        }
    }

    @Override
    public void j() {
        if (this.l()) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }

    }

    @Override
    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
        updateShape(world, blockposition);
        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
    }

    @Override
    public boolean c() {
        return this.l();
    }

    @Override
    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        IBlockData iblockdata = super.getPlacedState(world, blockposition, enumdirection, f, f1, f2, i, entityliving).set(BlockStepAbstract.HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);

        return this.l() ? iblockdata : ((enumdirection != EnumDirection.DOWN) && ((enumdirection == EnumDirection.UP) || (f1 <= 0.5D)) ? iblockdata : iblockdata.set(BlockStepAbstract.HALF, BlockStepAbstract.EnumSlabHalf.TOP));
    }

    @Override
    public int a(Random random) {
        return this.l() ? 2 : 1;
    }

    @Override
    public boolean d() {
        return this.l();
    }

    public abstract String b(int i);

    @Override
    public int getDropData(World world, BlockPosition blockposition) {
        return super.getDropData(world, blockposition) & 7;
    }

    public abstract boolean l();

    public abstract IBlockState<?> n();

    public abstract Object a(ItemStack itemstack);

    public static enum EnumSlabHalf implements INamable {

        TOP("top"), BOTTOM("bottom");

        private final String c;

        private EnumSlabHalf(String s) {
            c = s;
        }

        @Override
        public String toString() {
            return c;
        }

        @Override
        public String getName() {
            return c;
        }
    }

}
