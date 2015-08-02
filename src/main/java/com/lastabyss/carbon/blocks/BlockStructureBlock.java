package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.blocks.util.MapColorUtil;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

/**
 * Will fix this later.
 *
 * @author Navid
 */
public class BlockStructureBlock extends BlockContainer {

    public BlockStructureBlock() {
        super(Material.ORE, MapColorUtil.COLOR23);
        setBlockData(blockStateList.getBlockData());
    }

    @Override
    public TileEntity a(World world, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//
//    public static final BlockStateEnum<EnumMode> MODE = BlockStateEnum.of("mode", TileEntityStructure.EnumMode.class);
//    private final class_ast b = new class_ast();
//
//    public BlockStructureBlock() {
//        super(Material.ORE, MapColorUtil.COLOR23);
//        setBlockData(blockStateList.getBlockData());
//    }
//
//    @Override
//    public TileEntity createTileEntity(World var1, int var2) {
//        return new TileEntityStructure();
//    }
//
//    @Override
//    public boolean interact(World var1, BlockPosition var2, IBlockData var3, EntityHuman var4, EnumUsedHand var5, ItemStack var6, EnumDirection var7, float var8, float var9, float var10) {
//        return false;
//    }
//
//    @Override
//    public void postPlace(World var1, BlockPosition var2, IBlockData var3, EntityLiving var4, ItemStack var5) {
//    }
//
//    @Override
//    public int getDropCount(Random var1) {
//        return 0;
//    }
//
//    @Override
//    public int getRenderType() {
//        return 3;
//    }
//
//    @Override
//    public IBlockData getPlacedState(World var1, BlockPosition var2, EnumDirection var3, float var4, float var5, float var6, int var7, EntityLiving var8) {
//        return getBlockData().set(MODE, TileEntityStructure.EnumMode.DATA);
//    }
//
//    public class_ast l() {
//        return b;
//    }
//
//    @Override
//    public IBlockData fromLegacyData(int var1) {
//        return getBlockData().set(MODE, TileEntityStructure.EnumMode.getById(var1));
//    }
//
//    @Override
//    public int toLegacyData(IBlockData var1) {
//        return var1.get(MODE).getId();
//    }
//
//    @Override
//    protected BlockStateList getStateList() {
//        return new BlockStateList(this, new IBlockState[]{MODE});
//    }

}
