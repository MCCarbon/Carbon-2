package com.lastabyss.carbon.items;

import com.lastabyss.carbon.blocks.BlockStepAbstract;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

/**
 * @author Navid
 */
public class ItemStep extends ItemBlock {

    private final BlockStepAbstract b;
    private final BlockStepAbstract c;

    public ItemStep(Block block, BlockStepAbstract blockstepabstract, BlockStepAbstract blockstepabstract1) {
        super(block);
        this.b = blockstepabstract;
        this.c = blockstepabstract1;
        this.setMaxDurability(0);
        this.a(true);
    }

    public int filterData(int i) {
        return i;
    }

    public String e_(ItemStack itemstack) {
        return this.b.b(itemstack.getData());
    }

    public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
        if (itemstack.count == 0) {
            return false;
        } else if (!entityhuman.a(blockposition.shift(enumdirection), enumdirection, itemstack)) {
            return false;
        } else {
            Object object = this.b.a(itemstack);
            IBlockData iblockdata = world.getType(blockposition);

            if (iblockdata.getBlock() == this.b) {
                IBlockState iblockstate = this.b.n();
                Comparable comparable = iblockdata.get(iblockstate);
                BlockStepAbstract.EnumSlabHalf blockstepabstract_enumslabhalf = iblockdata.get(BlockStepAbstract.HALF);

                if ((enumdirection == EnumDirection.UP && blockstepabstract_enumslabhalf == BlockStepAbstract.EnumSlabHalf.BOTTOM || enumdirection == EnumDirection.DOWN && blockstepabstract_enumslabhalf == BlockStepAbstract.EnumSlabHalf.TOP) && comparable == object) {
                    IBlockData iblockdata1 = this.c.getBlockData().set(iblockstate, comparable);

                    if (world.b(this.c.a(world, blockposition, iblockdata1)) && world.setTypeAndData(blockposition, iblockdata1, 3)) {
                        world.makeSound((double) ((float) blockposition.getX() + 0.5F), (double) ((float) blockposition.getY() + 0.5F), (double) ((float) blockposition.getZ() + 0.5F), this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
                        --itemstack.count;
                    }

                    return true;
                }
            }
            return this.a(itemstack, world, blockposition.shift(enumdirection), object) || super.interactWith(itemstack, entityhuman, world, blockposition, enumdirection, f, f1, f2);
        }
    }

    private boolean a(ItemStack item, World world, BlockPosition pos, Object obj) {
        IBlockData a4 = world.getType(pos);
        if(a4.getBlock() == this.b) {
            Comparable compare = a4.get(this.b.n());
            if(compare == obj) {
                IBlockState state = this.b.n();
                IBlockData a6 = this.c.getBlockData().set(state, compare);
                if(world.b(this.c.a(world, pos, a6)) && world.setTypeAndData(pos, a6, 3)) {
                    world.makeSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
                    --item.count;
                }
                return true;
            }
        }
        return false;
    }
}
