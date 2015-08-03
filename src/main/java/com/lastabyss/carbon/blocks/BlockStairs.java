package com.lastabyss.carbon.blocks;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumDirection.EnumDirectionLimit;
import net.minecraft.server.v1_8_R3.Explosion;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

import com.lastabyss.carbon.blocks.util.WrappedBlock;

public class BlockStairs extends WrappedBlock {

    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirectionLimit.HORIZONTAL);
    public static final BlockStateEnum<BlockStairs.EnumHalf> HALF = BlockStateEnum.of("half", BlockStairs.EnumHalf.class);
    public static final BlockStateEnum<BlockStairs.EnumStairShape> SHAPE = BlockStateEnum.of("shape", BlockStairs.EnumStairShape.class);
    private static final int[][] O = new int[][] { { 4, 5 }, { 5, 7 }, { 6, 7 }, { 4, 6 }, { 0, 1 }, { 1, 3 }, { 2, 3 }, { 0, 2 } };
    private final WrappedBlock P;
    private final IBlockData Q;
    private boolean R;
    private int S;

    public BlockStairs(IBlockData iblockdata) {
        super(iblockdata.getBlock().getMaterial());
        this.j(blockStateList.getBlockData().set(BlockStairs.FACING, EnumDirection.NORTH).set(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).set(BlockStairs.SHAPE, BlockStairs.EnumStairShape.STRAIGHT));
        P = (WrappedBlock) iblockdata.getBlock();
        Q = iblockdata;
        this.c(P.getStrength());
        this.b(P.getDurability() / 3.0F);
        this.a(P.stepSound);
        this.e(255);
        this.a(CreativeModeTab.b);
    }

    @Override
    public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
        if (R) {
            this.a(0.5F * (S % 2), 0.5F * ((S / 4) % 2), 0.5F * ((S / 2) % 2), 0.5F + (0.5F * (S % 2)), 0.5F + (0.5F * ((S / 4) % 2)), 0.5F + (0.5F * ((S / 2) % 2)));
        } else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean d() {
        return false;
    }

    public void e(IBlockAccess iblockaccess, BlockPosition blockposition) {
        if (iblockaccess.getType(blockposition).get(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP) {
            this.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public static boolean c(Block block) {
        return block instanceof BlockStairs;
    }

    public static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
        IBlockData iblockdata1 = iblockaccess.getType(blockposition);
        Block block = iblockdata1.getBlock();

        return c(block) && (iblockdata1.get(BlockStairs.HALF) == iblockdata.get(BlockStairs.HALF)) && (iblockdata1.get(BlockStairs.FACING) == iblockdata.get(BlockStairs.FACING));
    }

    public int f(IBlockAccess iblockaccess, BlockPosition blockposition) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        EnumDirection enumdirection = iblockdata.get(BlockStairs.FACING);
        BlockStairs.EnumHalf blockstairs_enumhalf = iblockdata.get(BlockStairs.HALF);
        boolean flag = blockstairs_enumhalf == BlockStairs.EnumHalf.TOP;
        IBlockData iblockdata1;
        Block block;
        EnumDirection enumdirection1;

        if (enumdirection == EnumDirection.EAST) {
            iblockdata1 = iblockaccess.getType(blockposition.east());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.NORTH) && !a(iblockaccess, blockposition.south(), iblockdata)) {
                    return flag ? 1 : 2;
                }

                if ((enumdirection1 == EnumDirection.SOUTH) && !a(iblockaccess, blockposition.north(), iblockdata)) {
                    return flag ? 2 : 1;
                }
            }
        } else if (enumdirection == EnumDirection.WEST) {
            iblockdata1 = iblockaccess.getType(blockposition.west());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.NORTH) && !a(iblockaccess, blockposition.south(), iblockdata)) {
                    return flag ? 2 : 1;
                }

                if ((enumdirection1 == EnumDirection.SOUTH) && !a(iblockaccess, blockposition.north(), iblockdata)) {
                    return flag ? 1 : 2;
                }
            }
        } else if (enumdirection == EnumDirection.SOUTH) {
            iblockdata1 = iblockaccess.getType(blockposition.south());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.WEST) && !a(iblockaccess, blockposition.east(), iblockdata)) {
                    return flag ? 2 : 1;
                }

                if ((enumdirection1 == EnumDirection.EAST) && !a(iblockaccess, blockposition.west(), iblockdata)) {
                    return flag ? 1 : 2;
                }
            }
        } else if (enumdirection == EnumDirection.NORTH) {
            iblockdata1 = iblockaccess.getType(blockposition.north());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.WEST) && !a(iblockaccess, blockposition.east(), iblockdata)) {
                    return flag ? 1 : 2;
                }

                if ((enumdirection1 == EnumDirection.EAST) && !a(iblockaccess, blockposition.west(), iblockdata)) {
                    return flag ? 2 : 1;
                }
            }
        }

        return 0;
    }

    public int g(IBlockAccess iblockaccess, BlockPosition blockposition) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        EnumDirection enumdirection = iblockdata.get(BlockStairs.FACING);
        BlockStairs.EnumHalf blockstairs_enumhalf = iblockdata.get(BlockStairs.HALF);
        boolean flag = blockstairs_enumhalf == BlockStairs.EnumHalf.TOP;
        IBlockData iblockdata1;
        Block block;
        EnumDirection enumdirection1;

        if (enumdirection == EnumDirection.EAST) {
            iblockdata1 = iblockaccess.getType(blockposition.west());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.NORTH) && !a(iblockaccess, blockposition.north(), iblockdata)) {
                    return flag ? 1 : 2;
                }

                if ((enumdirection1 == EnumDirection.SOUTH) && !a(iblockaccess, blockposition.south(), iblockdata)) {
                    return flag ? 2 : 1;
                }
            }
        } else if (enumdirection == EnumDirection.WEST) {
            iblockdata1 = iblockaccess.getType(blockposition.east());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.NORTH) && !a(iblockaccess, blockposition.north(), iblockdata)) {
                    return flag ? 2 : 1;
                }

                if ((enumdirection1 == EnumDirection.SOUTH) && !a(iblockaccess, blockposition.south(), iblockdata)) {
                    return flag ? 1 : 2;
                }
            }
        } else if (enumdirection == EnumDirection.SOUTH) {
            iblockdata1 = iblockaccess.getType(blockposition.north());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.WEST) && !a(iblockaccess, blockposition.west(), iblockdata)) {
                    return flag ? 2 : 1;
                }

                if ((enumdirection1 == EnumDirection.EAST) && !a(iblockaccess, blockposition.east(), iblockdata)) {
                    return flag ? 1 : 2;
                }
            }
        } else if (enumdirection == EnumDirection.NORTH) {
            iblockdata1 = iblockaccess.getType(blockposition.south());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.WEST) && !a(iblockaccess, blockposition.west(), iblockdata)) {
                    return flag ? 1 : 2;
                }

                if ((enumdirection1 == EnumDirection.EAST) && !a(iblockaccess, blockposition.east(), iblockdata)) {
                    return flag ? 2 : 1;
                }
            }
        }

        return 0;
    }

    public boolean h(IBlockAccess iblockaccess, BlockPosition blockposition) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        EnumDirection enumdirection = iblockdata.get(BlockStairs.FACING);
        BlockStairs.EnumHalf blockstairs_enumhalf = iblockdata.get(BlockStairs.HALF);
        boolean flag = blockstairs_enumhalf == BlockStairs.EnumHalf.TOP;
        float f = 0.5F;
        float f1 = 1.0F;

        if (flag) {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.5F;
        boolean flag1 = true;
        IBlockData iblockdata1;
        Block block;
        EnumDirection enumdirection1;

        if (enumdirection == EnumDirection.EAST) {
            f2 = 0.5F;
            f5 = 1.0F;
            iblockdata1 = iblockaccess.getType(blockposition.east());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.NORTH) && !a(iblockaccess, blockposition.south(), iblockdata)) {
                    f5 = 0.5F;
                    flag1 = false;
                } else if ((enumdirection1 == EnumDirection.SOUTH) && !a(iblockaccess, blockposition.north(), iblockdata)) {
                    f4 = 0.5F;
                    flag1 = false;
                }
            }
        } else if (enumdirection == EnumDirection.WEST) {
            f3 = 0.5F;
            f5 = 1.0F;
            iblockdata1 = iblockaccess.getType(blockposition.west());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.NORTH) && !a(iblockaccess, blockposition.south(), iblockdata)) {
                    f5 = 0.5F;
                    flag1 = false;
                } else if ((enumdirection1 == EnumDirection.SOUTH) && !a(iblockaccess, blockposition.north(), iblockdata)) {
                    f4 = 0.5F;
                    flag1 = false;
                }
            }
        } else if (enumdirection == EnumDirection.SOUTH) {
            f4 = 0.5F;
            f5 = 1.0F;
            iblockdata1 = iblockaccess.getType(blockposition.south());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.WEST) && !a(iblockaccess, blockposition.east(), iblockdata)) {
                    f3 = 0.5F;
                    flag1 = false;
                } else if ((enumdirection1 == EnumDirection.EAST) && !a(iblockaccess, blockposition.west(), iblockdata)) {
                    f2 = 0.5F;
                    flag1 = false;
                }
            }
        } else if (enumdirection == EnumDirection.NORTH) {
            iblockdata1 = iblockaccess.getType(blockposition.north());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.WEST) && !a(iblockaccess, blockposition.east(), iblockdata)) {
                    f3 = 0.5F;
                    flag1 = false;
                } else if ((enumdirection1 == EnumDirection.EAST) && !a(iblockaccess, blockposition.west(), iblockdata)) {
                    f2 = 0.5F;
                    flag1 = false;
                }
            }
        }

        this.a(f2, f, f4, f3, f1, f5);
        return flag1;
    }

    public boolean i(IBlockAccess iblockaccess, BlockPosition blockposition) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        EnumDirection enumdirection = iblockdata.get(BlockStairs.FACING);
        BlockStairs.EnumHalf blockstairs_enumhalf = iblockdata.get(BlockStairs.HALF);
        boolean flag = blockstairs_enumhalf == BlockStairs.EnumHalf.TOP;
        float f = 0.5F;
        float f1 = 1.0F;

        if (flag) {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = 0.5F;
        float f5 = 1.0F;
        boolean flag1 = false;
        IBlockData iblockdata1;
        Block block;
        EnumDirection enumdirection1;

        if (enumdirection == EnumDirection.EAST) {
            iblockdata1 = iblockaccess.getType(blockposition.west());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.NORTH) && !a(iblockaccess, blockposition.north(), iblockdata)) {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    flag1 = true;
                } else if ((enumdirection1 == EnumDirection.SOUTH) && !a(iblockaccess, blockposition.south(), iblockdata)) {
                    f4 = 0.5F;
                    f5 = 1.0F;
                    flag1 = true;
                }
            }
        } else if (enumdirection == EnumDirection.WEST) {
            iblockdata1 = iblockaccess.getType(blockposition.east());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                f2 = 0.5F;
                f3 = 1.0F;
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.NORTH) && !a(iblockaccess, blockposition.north(), iblockdata)) {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    flag1 = true;
                } else if ((enumdirection1 == EnumDirection.SOUTH) && !a(iblockaccess, blockposition.south(), iblockdata)) {
                    f4 = 0.5F;
                    f5 = 1.0F;
                    flag1 = true;
                }
            }
        } else if (enumdirection == EnumDirection.SOUTH) {
            iblockdata1 = iblockaccess.getType(blockposition.north());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                f4 = 0.0F;
                f5 = 0.5F;
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.WEST) && !a(iblockaccess, blockposition.west(), iblockdata)) {
                    flag1 = true;
                } else if ((enumdirection1 == EnumDirection.EAST) && !a(iblockaccess, blockposition.east(), iblockdata)) {
                    f2 = 0.5F;
                    f3 = 1.0F;
                    flag1 = true;
                }
            }
        } else if (enumdirection == EnumDirection.NORTH) {
            iblockdata1 = iblockaccess.getType(blockposition.south());
            block = iblockdata1.getBlock();
            if (c(block) && (blockstairs_enumhalf == iblockdata1.get(BlockStairs.HALF))) {
                enumdirection1 = iblockdata1.get(BlockStairs.FACING);
                if ((enumdirection1 == EnumDirection.WEST) && !a(iblockaccess, blockposition.west(), iblockdata)) {
                    flag1 = true;
                } else if ((enumdirection1 == EnumDirection.EAST) && !a(iblockaccess, blockposition.east(), iblockdata)) {
                    f2 = 0.5F;
                    f3 = 1.0F;
                    flag1 = true;
                }
            }
        }

        if (flag1) {
            this.a(f2, f, f4, f3, f1, f5);
        }

        return flag1;
    }

    @Override
    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
        this.e(world, blockposition);
        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
        boolean flag = h(world, blockposition);

        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
        if (flag && this.i(world, blockposition)) {
            super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
        }

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void attack(World world, BlockPosition blockposition, EntityHuman entityhuman) {
        P.attack(world, blockposition, entityhuman);
    }

    @Override
    public void postBreak(World world, BlockPosition blockposition, IBlockData iblockdata) {
        P.postBreak(world, blockposition, iblockdata);
    }

    @Override
    public float a(Entity entity) {
        return P.a(entity);
    }

    @Override
    public int a(World world) {
        return P.a(world);
    }

    @Override
    public Vec3D a(World world, BlockPosition blockposition, Entity entity, Vec3D vec3d) {
        return P.a(world, blockposition, entity, vec3d);
    }

    @Override
    public boolean isFullCube() {
        return P.isFullCube();
    }

    @Override
    public boolean a(IBlockData iblockdata, boolean flag) {
        return P.a(iblockdata, flag);
    }

    @Override
    public boolean canPlace(World world, BlockPosition blockposition) {
        return P.canPlace(world, blockposition);
    }

    @Override
    public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
        doPhysics(world, blockposition, Q, Blocks.AIR);
        P.onPlace(world, blockposition, Q);
    }

    @Override
    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        P.remove(world, blockposition, Q);
    }

    @Override
    public void a(World world, BlockPosition blockposition, Entity entity) {
        P.a(world, blockposition, entity);
    }

    @Override
    public void tick(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        P.tick(world, blockposition, iblockdata, random);
    }

    @Override
    public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
        return P.interact(world, blockposition, Q, entityhuman, EnumDirection.DOWN, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void wasExploded(World world, BlockPosition blockposition, Explosion explosion) {
        P.wasExploded(world, blockposition, explosion);
    }

    @Override
    public MaterialMapColor g(IBlockData iblockdata) {
        return P.g(Q);
    }

    @Override
    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        IBlockData iblockdata = super.getPlacedState(world, blockposition, enumdirection, f, f1, f2, i, entityliving);

        iblockdata = iblockdata.set(BlockStairs.FACING, entityliving.getDirection()).set(BlockStairs.SHAPE, BlockStairs.EnumStairShape.STRAIGHT);
        return (enumdirection != EnumDirection.DOWN) && ((enumdirection == EnumDirection.UP) || (f1 <= 0.5D)) ? iblockdata.set(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM) : iblockdata.set(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
    }

    @Override
    public MovingObjectPosition rayTraceCollision(World world, BlockPosition blockposition, Vec3D vec3d, Vec3D vec3d1) {
        MovingObjectPosition[] amovingobjectposition = new MovingObjectPosition[8];
        IBlockData iblockdata = world.getType(blockposition);
        int i = iblockdata.get(BlockStairs.FACING).b();
        boolean flag = iblockdata.get(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP;
        int[] aint = BlockStairs.O[i + (flag ? 4 : 0)];

        R = true;

        for (int j = 0; j < 8; ++j) {
            S = j;
            if (Arrays.binarySearch(aint, j) < 0) {
                amovingobjectposition[j] = super.rayTraceCollision(world, blockposition, vec3d, vec3d1);
            }
        }

        int k = aint.length;

        for (int i1 : aint) {
            amovingobjectposition[i1] = null;
        }

        MovingObjectPosition movingobjectposition = null;
        double d0 = 0.0D;
        int j1 = amovingobjectposition.length;

        for (MovingObjectPosition movingobjectposition1 : amovingobjectposition) {
            if (movingobjectposition1 != null) {
                double d1 = movingobjectposition1.pos.distanceSquared(vec3d1);

                if (d1 > d0) {
                    movingobjectposition = movingobjectposition1;
                    d0 = d1;
                }
            }
        }

        return movingobjectposition;
    }

    @Override
    public IBlockData fromLegacyData(int i) {
        IBlockData iblockdata = getBlockData().set(BlockStairs.HALF, (i & 4) > 0 ? BlockStairs.EnumHalf.TOP : BlockStairs.EnumHalf.BOTTOM);

        iblockdata = iblockdata.set(BlockStairs.FACING, EnumDirection.fromType1(5 - (i & 3)));
        return iblockdata;
    }

    @Override
    public int toLegacyData(IBlockData iblockdata) {
        int i = 0;

        if (iblockdata.get(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP) {
            i |= 4;
        }

        i |= 5 - iblockdata.get(BlockStairs.FACING).a();
        return i;
    }

    @Override
    public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        if (h(iblockaccess, blockposition)) {
            switch (this.g(iblockaccess, blockposition)) {
            case 0:
                iblockdata = iblockdata.set(BlockStairs.SHAPE, BlockStairs.EnumStairShape.STRAIGHT);
                break;

            case 1:
                iblockdata = iblockdata.set(BlockStairs.SHAPE, BlockStairs.EnumStairShape.INNER_RIGHT);
                break;

            case 2:
                iblockdata = iblockdata.set(BlockStairs.SHAPE, BlockStairs.EnumStairShape.INNER_LEFT);
            }
        } else {
            switch (f(iblockaccess, blockposition)) {
            case 0:
                iblockdata = iblockdata.set(BlockStairs.SHAPE, BlockStairs.EnumStairShape.STRAIGHT);
                break;

            case 1:
                iblockdata = iblockdata.set(BlockStairs.SHAPE, BlockStairs.EnumStairShape.OUTER_RIGHT);
                break;

            case 2:
                iblockdata = iblockdata.set(BlockStairs.SHAPE, BlockStairs.EnumStairShape.OUTER_LEFT);
            }
        }

        return iblockdata;
    }

    @Override
    protected BlockStateList getStateList() {
        return new BlockStateList(this, BlockStairs.FACING, BlockStairs.HALF, BlockStairs.SHAPE);
    }

    public enum EnumStairShape implements INamable {

        STRAIGHT("straight"), INNER_LEFT("inner_left"), INNER_RIGHT("inner_right"), OUTER_LEFT("outer_left"), OUTER_RIGHT("outer_right");

        private final String f;

        EnumStairShape(String s) {
            f = s;
        }

        @Override
        public String toString() {
            return f;
        }

        @Override
        public String getName() {
            return f;
        }
    }

    public enum EnumHalf implements INamable {

        TOP("top"), BOTTOM("bottom");

        private final String c;

        EnumHalf(String s) {
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
