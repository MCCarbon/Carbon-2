package com.lastabyss.carbon.blocks;

import java.util.Iterator;

import com.lastabyss.carbon.blocks.util.BlockStateUtil;
import com.lastabyss.carbon.blocks.util.EnumDirectionUtil;
import com.lastabyss.carbon.staticaccess.BlockList;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

public class BlockEndRod extends BlockDirectional {

    public BlockEndRod() {
        super(Material.ORIENTABLE);
        setBlockData(blockStateList.getBlockData().set(FACING, EnumDirection.UP));
    }

    // Has something to do with worldgen
    /*
     * @Override public IBlockData a(IBlockData blockdata, Block.EnumRotation
     * rotation) { return blockdata.getBlock() != this ? blockdata :
     * blockdata.set(FACING, rotation.a(blockdata.get(FACING))); }
     * 
     * @Override public IBlockData a(IBlockData blockdata,
     * Block.class_a_in_class_agj var2) { return blockdata.getBlock() != this ?
     * blockdata : blockdata.set(FACING, var2.b(blockdata.get(FACING))); }
     */
    @Override
    public AxisAlignedBB getBoundingBox(World var1, BlockPosition var2, IBlockData var3) {
        EnumDirection var4 = var3.get(FACING);
        float var5 = 0.4375F;
        float var6 = 0.5625F;
        return (var4 != EnumDirection.EAST) && (var4 != EnumDirection.WEST) ? ((var4 != EnumDirection.SOUTH) && (var4 != EnumDirection.NORTH) ? new AxisAlignedBB(var2.getX() + var5, var2.getY(), var2.getZ() + var5, var2.getX() + var6, var2.getY() + 1, var2.getZ() + var6) : new AxisAlignedBB(var2.getX() + var5, var2.getY() + var5, var2.getZ(), var2.getX() + var6, var2.getY() + var6, var2.getZ() + 1)) : new AxisAlignedBB(var2.getX(), var2.getY() + var5, var2.getZ() + var5, var2.getX() + 1, var2.getY() + var6, var2.getZ() + var6);
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
    public boolean canPlace(World world, BlockPosition position) {
        Iterator<EnumDirection> iterator = BlockStateUtil.getValues(FACING).iterator();

        if (!iterator.hasNext()) return false;
        // The following code is pretty pointless for now, so I commented it out until it's needed later
        // when we implement the end rod code
//        EnumDirection direction;
//        do {
//            if (!iterator.hasNext()) {
//                return false;
//            }
//            direction = iterator.next();
//        } while (!this.a(world, position, direction)); //Why is this always false?

        return true;
    }

    // WTF??????
    @SuppressWarnings("unused")
    private boolean a(World var1, BlockPosition var2, EnumDirection var3) {
        return true;
    }

    @Override
    public IBlockData getPlacedState(World world, BlockPosition position, EnumDirection face, float var4, float var5, float var6, int var7, EntityLiving var8) {
        IBlockData blockdata = world.getType(position.shift(face.opposite()));
        if (blockdata.getBlock() == BlockList.END_ROD) {
            EnumDirection facing = blockdata.get(FACING);
            if (facing == face) {
                return getBlockData().set(FACING, face.opposite());
            }
        }
        return getBlockData().set(FACING, face);
    }

    @Override
    public void onPlace(World world, BlockPosition position, IBlockData blockdata) {
    }

    @Override
    public void doPhysics(World world, BlockPosition position, IBlockData blockdata, Block block) {
    }

    @Override
    public MovingObjectPosition rayTraceCollision(World world, BlockPosition position, Vec3D vec1, Vec3D vec2) {
        EnumDirection facing = world.getType(position).get(FACING);
        float var6 = 0.375F;
        float var7 = 0.625F;
        if ((facing != EnumDirection.EAST) && (facing != EnumDirection.WEST)) {
            if ((facing != EnumDirection.SOUTH) && (facing != EnumDirection.NORTH)) {
                setSizes(var6, 0.0F, var6, var7, 1.0F, var7);
            } else {
                setSizes(var6, var6, 0.0F, var7, var7, 1.0F);
            }
        } else {
            setSizes(0.0F, var6, var6, 1.0F, var7, var7);
        }

        return super.rayTraceCollision(world, position, vec1, vec2);
    }

    @Override
    public IBlockData fromLegacyData(int data) {
        IBlockData var2 = getBlockData();
        var2 = var2.set(FACING, EnumDirectionUtil.getById(data));
        return var2;
    }

    @Override
    public int toLegacyData(IBlockData blockdata) {
        return EnumDirectionUtil.getId(blockdata.get(FACING));
    }

    @Override
    protected BlockStateList getStateList() {
        return new BlockStateList(this, FACING);
    }

    @Override
    public int getPushReaction() {
        return 0;
    }

}
