package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.blocks.util.CreativeTabUtil;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;

public abstract class BlockPurpurSlabAbstract extends BlockStepAbstract {

    public static final BlockStateEnum<EnumPurpurSlabType> VARIANT = BlockStateEnum.of("variant", BlockPurpurSlabAbstract.EnumPurpurSlabType.class);

    public BlockPurpurSlabAbstract() {
        super(Material.STONE);
        IBlockData var1 = blockStateList.getBlockData();
        if (!l()) {
            var1 = var1.set(HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
        }

        setBlockData(var1.set(VARIANT, BlockPurpurSlabAbstract.EnumPurpurSlabType.DEFAULT));
        setCreativeTab(CreativeTabUtil.BUILDING_BLOCKS);
    }

    @Override
    public IBlockData fromLegacyData(int var1) {
        IBlockData var2 = getBlockData().set(VARIANT, BlockPurpurSlabAbstract.EnumPurpurSlabType.DEFAULT);
        if (!l()) {
            var2 = var2.set(HALF, (var1 & 8) == 0 ? BlockStepAbstract.EnumSlabHalf.BOTTOM : BlockStepAbstract.EnumSlabHalf.TOP);
        }

        return var2;
    }

    @Override
    public int toLegacyData(IBlockData var1) {
        int var2 = 0;
        if (!l() && (var1.get(HALF) == BlockStepAbstract.EnumSlabHalf.TOP)) {
            var2 |= 8;
        }

        return var2;
    }

    @Override
    protected BlockStateList getStateList() {
        return l() ? new BlockStateList(this, new IBlockState[]{VARIANT}) : new BlockStateList(this, new IBlockState[]{HALF, VARIANT});
    }

    @Override
    public String b(int var1) {
        return super.getName();
    }

    @Override
    public IBlockState<EnumPurpurSlabType> n() {
        return VARIANT;
    }

    @Override
    public Object a(ItemStack var1) {
        return BlockPurpurSlabAbstract.EnumPurpurSlabType.DEFAULT;
    }

    public static enum EnumPurpurSlabType implements INamable {

        DEFAULT;

        @Override
        public String getName() {
            return "default";
        }
    }

    public static class BlockPurpurDoubleSlab extends BlockPurpurSlabAbstract {

        @Override
        public boolean l() {
            return true;
        }
    }

    public static class BlockPuprpurSlab extends BlockPurpurSlabAbstract {

        @Override
        public boolean l() {
            return false;
        }
    }

}
