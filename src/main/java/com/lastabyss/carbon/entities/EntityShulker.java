package com.lastabyss.carbon.entities;

import net.minecraft.server.v1_8_R3.EntityGolem;
import net.minecraft.server.v1_8_R3.IMonster;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class EntityShulker extends EntityGolem implements IMonster {

    public EntityShulker(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (bukkitEntity == null) {
            bukkitEntity = new CraftShulker(this.world.getServer(), this);
        }
        return bukkitEntity;
    }

}
/**
public class EntityShulker extends EntityGolem implements IMonster {
    protected static final int a = DataWatcher.claimId(EntityShulker.class);
    protected static final int b = DataWatcher.claimId(EntityShulker.class);
    protected static final int c = DataWatcher.claimId(EntityShulker.class);
    public static final BlockPosition bs = new BlockPosition(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private float bt;
    private float bu;
    private BlockPosition bv;
    private int bw;

    public EntityShulker(World var1) {
        super(var1);
        this.setSize(1.0F, 1.0F);
        this.aM = 180.0F;
        this.aL = 180.0F;
        this.fireProof = true;
        this.bv = bs;
        this.i.a(1, new class_rr(this, EntityHuman.class, 8.0F));
        this.i.a(4, new EntityShulker.class_a_in_class_ug());
        this.i.a(7, new EntityShulker.class_e_in_class_ug((EntityShulker.SyntheticClass_1)null));
        this.i.a(8, new class_sh(this));
        this.bn.a(1, new class_sw(this, true, new Class[0]));
        this.bn.a(2, new EntityShulker.class_d_in_class_ug(this));
        this.bn.a(3, new EntityShulker.class_c_in_class_ug(this));
    }

    protected void initDatawatcher() {
        super.initDatawatcher();
        this.datawatcher.add(a, Byte.valueOf((byte) EnumDirection.DOWN.getId()));
        this.datawatcher.add(b, bs);
        this.datawatcher.add(c, Byte.valueOf((byte)0));
    }

    protected void aY() {
        super.aY();
        this.a((class_qk)class_wl.a).a(30.0D);
    }

    protected class_qv p() {
        return new EntityShulker.class_b_in_class_ug(this);
    }

    public void read(NBTTagCompound var1) {
        super.read(var1);
        this.datawatcher.update(a, Byte.valueOf(var1.getByte("AttachFace")));
        this.datawatcher.update(c, Byte.valueOf(var1.getByte("Peek")));
        if(var1.has("APX")) {
            int var2 = var1.getInt("APX");
            int var3 = var1.getInt("APY");
            int var4 = var1.getInt("APZ");
            this.datawatcher.update(b, new BlockPosition(var2, var3, var4));
        } else {
            this.datawatcher.update(b, bs);
        }

    }

    public void t_() {
        super.t_();
        BlockPosition var1 = this.datawatcher.getBlockPosition(b);
        if((var1 == null || bs.equals(var1)) && !this.world.isClientSide) {
            var1 = new BlockPosition(this);
            this.datawatcher.update(b, var1);
        }

        if(!this.world.isClientSide) {
            IBlockData var2 = this.world.getType(var1);
            if(var2.getBlock() != Blocks.AIR) {
                EnumDirection var3;
                if(var2.getBlock() == Blocks.PISTON_EXTENSION) {
                    var3 = (EnumDirection)var2.get(BlockPiston.FACING);
                    var1 = var1.shift(var3);
                    this.datawatcher.update(b, var1);
                } else if(var2.getBlock() == Blocks.PISTON_HEAD) {
                    var3 = (EnumDirection)var2.get(BlockPistonExtension.FACING);
                    var1 = var1.shift(var3);
                    this.datawatcher.update(b, var1);
                } else {
                    this.n();
                }
            }
        }

        if(!this.world.isClientSide) {
            BlockPosition var15 = var1.shift(this.cA());
            if(!this.world.d(var15, false)) {
                boolean var17 = false;
                EnumDirection[] var4 = EnumDirection.values();
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    EnumDirection var7 = var4[var6];
                    var15 = var1.shift(var7);
                    if(this.world.d(var15, false)) {
                        this.datawatcher.update(a, Byte.valueOf((byte)var7.getId()));
                        var17 = true;
                        break;
                    }
                }

                if(!var17) {
                    this.n();
                }
            }
        }

        float var16 = (float)this.cC() * 0.01F;
        this.bt = this.bu;
        if(this.bu > var16) {
            this.bu = MathHelper.clamp(this.bu - 0.05F, var16, 1.0F);
        } else if(this.bu < var16) {
            this.bu = MathHelper.clamp(this.bu + 0.05F, 0.0F, var16);
        }

        if(var1 != null && !bs.equals(var1)) {
            double var18 = (double)(this.bu - this.bt);
            double var19 = 0.0D;
            double var20 = 0.0D;
            double var9 = 0.0D;
            if(this.world.isClientSide) {
                if(this.bw > 0 && !bs.equals(this.bv)) {
                    --this.bw;
                } else {
                    this.bv = var1;
                }
            }

            this.P = this.lastX = this.locX = (double)var1.getX() + 0.5D;
            this.Q = this.lastY = this.locY = (double)var1.getY();
            this.R = this.lastZ = this.locZ = (double)var1.getZ() + 0.5D;
            EnumDirection var11 = this.cA();
            switch(EntityShulker.SyntheticClass_1.a[var11.ordinal()]) {
                case 1:
                default:
                    this.setBoundingBox((AxisAlignedBB)(new AxisAlignedBB(this.locX - 0.5D, this.locY, this.locZ - 0.5D, this.locX + 0.5D, this.locY + 1.0D + (double)this.bu, this.locZ + 0.5D)));
                    var20 = var18;
                    break;
                case 2:
                    this.setBoundingBox((AxisAlignedBB)(new AxisAlignedBB(this.locX - 0.5D, this.locY - (double)this.bu, this.locZ - 0.5D, this.locX + 0.5D, this.locY + 1.0D, this.locZ + 0.5D)));
                    var20 = -var18;
                    break;
                case 3:
                    this.setBoundingBox((AxisAlignedBB)(new AxisAlignedBB(this.locX - 0.5D, this.locY, this.locZ - 0.5D, this.locX + 0.5D, this.locY + 1.0D, this.locZ + 0.5D + (double)this.bu)));
                    var9 = var18;
                    break;
                case 4:
                    this.setBoundingBox((AxisAlignedBB)(new AxisAlignedBB(this.locX - 0.5D, this.locY, this.locZ - 0.5D - (double)this.bu, this.locX + 0.5D, this.locY + 1.0D, this.locZ + 0.5D)));
                    var9 = -var18;
                    break;
                case 5:
                    this.setBoundingBox((AxisAlignedBB)(new AxisAlignedBB(this.locX - 0.5D, this.locY, this.locZ - 0.5D, this.locX + 0.5D + (double)this.bu, this.locY + 1.0D, this.locZ + 0.5D)));
                    var19 = var18;
                    break;
                case 6:
                    this.setBoundingBox((AxisAlignedBB)(new AxisAlignedBB(this.locX - 0.5D - (double)this.bu, this.locY, this.locZ - 0.5D, this.locX + 0.5D, this.locY + 1.0D, this.locZ + 0.5D)));
                    var19 = -var18;
            }

            if(var18 > 0.0D) {
                List var12 = this.world.getEntities((Entity)this, (AxisAlignedBB)this.getBoundingBox());
                if(!var12.isEmpty()) {
                    Iterator var13 = var12.iterator();

                    while(var13.hasNext()) {
                        Entity var14 = (Entity)var13.next();
                        if(!(var14 instanceof EntityShulker) && !var14.noclip) {
                            var14.d(var19, var20, var9);
                        }
                    }
                }
            }
        }

    }

    protected boolean n() {
        BlockPosition var1 = new BlockPosition(this);

        for(int var2 = 0; var2 < 5; ++var2) {
            BlockPosition var3 = var1.add(8 - this.random.nextInt(17), 8 - this.random.nextInt(17), 8 - this.random.nextInt(17));
            if(var3.getY() > 0 && this.world.isEmpty(var3) && this.world.a((class_aoe)this.world.ag(), (Entity)this)) {
                boolean var4 = false;
                EnumDirection[] var5 = EnumDirection.values();
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    EnumDirection var8 = var5[var7];
                    if(this.world.d(var3.shift(var8), false)) {
                        this.datawatcher.update(a, Byte.valueOf((byte)var8.getId()));
                        var4 = true;
                        break;
                    }
                }

                if(var4) {
                    this.makeSound("mob.endermen.portal", 1.0F, 1.0F);
                    this.datawatcher.update(b, var3);
                    this.datawatcher.update(c, Byte.valueOf((byte)0));
                    this.d((EntityLiving)null);
                    return true;
                }
            }
        }

        return false;
    }

    public void m() {
        super.m();
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.aM = 180.0F;
        this.aL = 180.0F;
        this.yaw = 180.0F;
    }

    public void d(int var1) {
        if(var1 == b && this.world.isClientSide) {
            BlockPosition var2 = this.cB();
            if(!bs.equals(var2)) {
                if(bs.equals(this.bv)) {
                    this.bv = var2;
                } else {
                    this.bw = 6;
                }

                this.P = this.lastX = this.locX = (double)var2.getX() + 0.5D;
                this.Q = this.lastY = this.locY = (double)var2.getY();
                this.R = this.lastZ = this.locZ = (double)var2.getZ() + 0.5D;
            }
        }

        super.d(var1);
    }

    public int bs() {
        return this.cC() == 0?20:super.bs();
    }

    public boolean damageEntity(DamageSource var1, float var2) {
        if(this.cC() == 0) {
            Entity var3 = var1.i();
            if(var3 instanceof EntityArrow) {
                return false;
            }
        }

        if(super.damageEntity(var1, var2)) {
            if((double)this.getHealth() < (double)this.getMaxHealth() * 0.5D && this.random.nextInt(4) == 0) {
                this.n();
            }

            return true;
        } else {
            return false;
        }
    }

    public AxisAlignedBB S() {
        return this.isAlive()?this.getBoundingBox():null;
    }

    public EnumDirection cA() {
        return EnumDirection.getById(this.datawatcher.getByte(a));
    }

    public BlockPosition cB() {
        return this.datawatcher.getBlockPosition(b);
    }

    public void f(BlockPosition var1) {
        this.datawatcher.update(b, var1);
    }

    public int cC() {
        return this.datawatcher.getByte(c);
    }

    public void a(int var1) {
        this.datawatcher.update(c, Byte.valueOf((byte)var1));
    }

    public float getHeadHeight() {
        return 0.5F;
    }

    public int cd() {
        return 180;
    }

    public int ce() {
        return 180;
    }

    public void i(Entity var1) {
    }

    // $FF: synthetic class
    static class SyntheticClass_1 {
        // $FF: synthetic field
        static final int[] a = new int[EnumDirection.values().length];

        static {
            try {
                a[EnumDirection.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError var6) {
                ;
            }

            try {
                a[EnumDirection.UP.ordinal()] = 2;
            } catch (NoSuchFieldError var5) {
                ;
            }

            try {
                a[EnumDirection.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError var4) {
                ;
            }

            try {
                a[EnumDirection.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError var3) {
                ;
            }

            try {
                a[EnumDirection.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError var2) {
                ;
            }

            try {
                a[EnumDirection.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError var1) {
                ;
            }

        }
    }

    static class class_c_in_class_ug extends class_sz {
        public class_c_in_class_ug(EntityShulker var1) {
            super(var1, EntityLiving.class, 10, true, false, new Predicate() {
                public boolean a(EntityLiving var1) {
                    return var1 instanceof class_wd;
                }

                // $FF: synthetic method
                public boolean apply(Object var1) {
                    return this.a((EntityLiving)var1);
                }
            });
        }

        public boolean a() {
            return this.e.bP() == null?false:super.a();
        }

        protected AxisAlignedBB a(double var1) {
            EnumDirection var3 = ((EntityShulker)this.e).cA();
            return var3.getAxis() == EnumDirection.EnumAxis.X?this.e.getBoundingBox().grow(4.0D, var1, var1):(var3.getAxis() == EnumDirection.EnumAxis.Z?this.e.getBoundingBox().grow(var1, var1, 4.0D):this.e.getBoundingBox().grow(var1, 4.0D, var1));
        }
    }

    static class class_d_in_class_ug extends class_sz {
        public class_d_in_class_ug(EntityShulker var1) {
            super(var1, EntityHuman.class, true);
        }

        protected AxisAlignedBB a(double var1) {
            EnumDirection var3 = ((EntityShulker)this.e).cA();
            return var3.getAxis() == EnumDirection.EnumAxis.X?this.e.getBoundingBox().grow(4.0D, var1, var1):(var3.getAxis() == EnumDirection.EnumAxis.Z?this.e.getBoundingBox().grow(var1, var1, 4.0D):this.e.getBoundingBox().grow(var1, 4.0D, var1));
        }
    }

    class class_a_in_class_ug extends class_rm {
        private int b;

        public class_a_in_class_ug() {
            this.a(3);
        }

        public boolean a() {
            EntityLiving var1 = EntityShulker.this.w();
            return var1 != null && var1.isAlive();
        }

        public void c() {
            this.b = 20;
            EntityShulker.this.a(100);
        }

        public void d() {
            EntityShulker.this.a(0);
        }

        public void e() {
            --this.b;
            EntityLiving var1 = EntityShulker.this.w();
            EntityShulker.this.q().a(var1, 180.0F, 180.0F);
            double var2 = EntityShulker.this.h(var1);
            if(var2 < 400.0D) {
                if(this.b <= 0) {
                    this.b = 20 + EntityShulker.this.random.nextInt(10) * 20 / 2;
                    net.minecraft.server.EntityShulkerBullet var4 = new net.minecraft.server.EntityShulkerBullet(EntityShulker.this.world, EntityShulker.this, var1, EntityShulker.this.cA().getAxis());
                    EntityShulker.this.world.addEntity((Entity)var4);
                    EntityShulker.this.makeSound("mob.ghast.fireball", 2.0F, (EntityShulker.this.random.nextFloat() - EntityShulker.this.random.nextFloat()) * 0.2F + 1.0F);
                }
            } else {
                EntityShulker.this.d((EntityLiving)null);
            }

            super.e();
        }
    }

    class class_e_in_class_ug extends class_rm {
        private int b;

        private class_e_in_class_ug() {
        }

        public boolean a() {
            return EntityShulker.this.w() == null && EntityShulker.this.random.nextInt(40) == 0;
        }

        public boolean b() {
            return EntityShulker.this.w() == null && this.b > 0;
        }

        public void c() {
            this.b = 20 * (1 + EntityShulker.this.random.nextInt(3));
            EntityShulker.this.a(30);
        }

        public void d() {
            if(EntityShulker.this.w() == null) {
                EntityShulker.this.a(0);
            }

        }

        public void e() {
            --this.b;
        }

        // $FF: synthetic method
        class_e_in_class_ug(EntityShulker.SyntheticClass_1 var2) {
            this();
        }
    }

    class class_b_in_class_ug extends class_qv {
        public class_b_in_class_ug(EntityLiving var2) {
            super(var2);
        }

        public void a() {
        }
    }
}
**/