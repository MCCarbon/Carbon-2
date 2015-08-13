package com.lastabyss.carbon.entities;

import com.google.common.collect.Lists;
import com.lastabyss.carbon.nmsutils.NBTStructSerializer;
import com.lastabyss.carbon.staticaccess.EffectList;
import com.lastabyss.carbon.staticaccess.ParticleList;
import com.lastabyss.carbon.types.class_xj;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntityShulkerBullet extends Entity {

    private EntityLiving a;
    private Entity b;
    private EnumDirection c;
    private int d;
    private double e;
    private double f;
    private double g;
    private UUID h;
    private BlockPosition i;
    private UUID as;
    private BlockPosition at;

    public EntityShulkerBullet(World var1) {
        super(var1);
        this.setSize(0.3125F, 0.3125F);
        this.noclip = true;
    }

    public EntityShulkerBullet(World var1, EntityLiving var2, Entity var3, EnumDirection.EnumAxis var4) {
        this(var1);
        this.a = var2;
        BlockPosition var5 = new BlockPosition(var2);
        double var6 = (double) var5.getX() + 0.5D;
        double var8 = (double) var5.getY() + 0.5D;
        double var10 = (double) var5.getZ() + 0.5D;
        this.setPositionRotation(var6, var8, var10, this.yaw, this.pitch);
        this.b = var3;
        this.c = EnumDirection.UP;
        this.a(var4);
    }

    protected void a(NBTTagCompound var1) {
        BlockPosition var2;
        NBTTagCompound var3;
        if (this.a != null) {
            var2 = new BlockPosition(this.a);
            var3 = NBTStructSerializer.toNBT(this.a.getUniqueID());
            var3.setInt("X", var2.getX());
            var3.setInt("Y", var2.getY());
            var3.setInt("Z", var2.getZ());
            var1.set("Owner", var3);
        }

        if (this.b != null) {
            var2 = new BlockPosition(this.b);
            var3 = NBTStructSerializer.toNBT(this.b.getUniqueID());
            var3.setInt("X", var2.getX());
            var3.setInt("Y", var2.getY());
            var3.setInt("Z", var2.getZ());
            var1.set("Target", var3);
        }

        if (this.c != null) {
            var1.setInt("Dir", this.c.a());
        }

        var1.setInt("Steps", this.d);
        var1.setDouble("TXD", this.e);
        var1.setDouble("TYD", this.f);
        var1.setDouble("TZD", this.g);
    }

    @Override
    protected void h() {
        //init datawatcher
    }

    protected void b(NBTTagCompound var1) {
        this.d = var1.getInt("Steps");
        this.e = var1.getDouble("TXD");
        this.f = var1.getDouble("TYD");
        this.g = var1.getDouble("TZD");
        if (var1.hasKeyOfType("Dir", 99)) {
            this.c = EnumDirection.fromType1(var1.getInt("Dir"));
        }

        NBTTagCompound var2;
        if (var1.hasKeyOfType("Owner", 10)) {
            var2 = var1.getCompound("Owner");
            this.h = NBTStructSerializer.getUUID(var2);
            this.i = new BlockPosition(var2.getInt("X"), var2.getInt("Y"), var2.getInt("Z"));
        }

        if (var1.hasKeyOfType("Target", 10)) {
            var2 = var1.getCompound("Target");
            this.as = NBTStructSerializer.getUUID(var2);
            this.at = new BlockPosition(var2.getInt("X"), var2.getInt("Y"), var2.getInt("Z"));
        }

    }

    protected void initDatawatcher() {
    }

    private void a(EnumDirection var1) {
        this.c = var1;
    }

    private void a(EnumDirection.EnumAxis var1) {
        double var3 = 0.5D;
        BlockPosition var2;
        if (this.b != null) {
            var3 = (double) this.b.length * 0.5D;
            var2 = new BlockPosition(this.b.locX, this.b.locY + var3, this.b.locZ);
        } else {
            var2 = (new BlockPosition(this)).down();
        }

        double var5 = (double) var2.getX() + 0.5D;
        double var7 = (double) var2.getY() + var3;
        double var9 = (double) var2.getZ() + 0.5D;
        if (var2.d(this.locX, this.locY, this.locZ) >= 4.0D) {
            BlockPosition var11 = new BlockPosition(this);
            ArrayList<EnumDirection> var12 = Lists.newArrayList();
            if (var1 != EnumDirection.EnumAxis.X) {
                if (var11.getX() < var2.getX() && this.world.isEmpty(var11.east())) {
                    var12.add(EnumDirection.EAST);
                } else if (var11.getX() > var2.getX() && this.world.isEmpty(var11.west())) {
                    var12.add(EnumDirection.WEST);
                }
            }

            if (var1 != EnumDirection.EnumAxis.Y) {
                if (var11.getY() < var2.getY() && this.world.isEmpty(var11.up())) {
                    var12.add(EnumDirection.UP);
                } else if (var11.getY() > var2.getY() && this.world.isEmpty(var11.down())) {
                    var12.add(EnumDirection.DOWN);
                }
            }

            if (var1 != EnumDirection.EnumAxis.Z) {
                if (var11.getZ() < var2.getZ() && this.world.isEmpty(var11.south())) {
                    var12.add(EnumDirection.SOUTH);
                } else if (var11.getZ() > var2.getZ() && this.world.isEmpty(var11.north())) {
                    var12.add(EnumDirection.NORTH);
                }
            }

            EnumDirection var13 = EnumDirection.a(this.random);
            if (!var12.isEmpty()) {
                var13 = (EnumDirection) var12.get(this.random.nextInt(var12.size()));
            } else {
                for (int var14 = 5; !this.world.isEmpty(var11.shift(var13)) && var14 > 0; --var14) {
                    var13 = EnumDirection.a(this.random);
                }
            }

            var5 = this.locX + (double) var13.getAdjacentX();
            var7 = this.locY + (double) var13.getAdjacentY();
            var9 = this.locZ + (double) var13.getAdjacentZ();
            this.a(var13);
        } else {
            this.a((EnumDirection) null);
        }

        double var19 = var5 - this.locX;
        double var20 = var7 - this.locY;
        double var15 = var9 - this.locZ;
        double var17 = (double) MathHelper.sqrt(var19 * var19 + var20 * var20 + var15 * var15);
        if (var17 == 0.0) {
            this.f = 0.0;
            this.e = 0.0;
            this.g = 0.0;
        } else {
            this.e = var19 / var17 * 0.15;
            this.f = var20 / var17 * 0.15;
            this.g = var15 / var17 * 0.15;
        }
        this.ai = true;
        this.d = 10 + this.random.nextInt(5) * 10;
    }

    public void t_() {
        super.t_();
        if (!this.world.isClientSide) {
            List<EntityLiving> var1;
            Iterator<EntityLiving> var2;
            EntityLiving var3;
            if (this.b == null && this.as != null) {
                var1 = this.world.a(EntityLiving.class, new AxisAlignedBB(this.at.a(-2, -2, -2), this.at.a(2, 2, 2)));
                var2 = var1.iterator();

                while (var2.hasNext()) {
                    var3 = (EntityLiving) var2.next();
                    if (var3.getUniqueID().equals(this.as)) {
                        this.b = var3;
                        break;
                    }
                }

                this.as = null;
            }

            if (this.a == null && this.h != null) {
                var1 = this.world.a(EntityLiving.class, new AxisAlignedBB(this.i.a(-2, -2, -2), this.i.a(2, 2, 2)));
                var2 = var1.iterator();

                while (var2.hasNext()) {
                    var3 = (EntityLiving) var2.next();
                    if (var3.getUniqueID().equals(this.h)) {
                        this.a = var3;
                        break;
                    }
                }

                this.h = null;
            }

            if (this.b != null && !this.b.dead) {
                this.e = MathHelper.a(this.e * 1.025D, -1.0D, 1.0D);
                this.f = MathHelper.a(this.f * 1.025D, -1.0D, 1.0D);
                this.g = MathHelper.a(this.g * 1.025D, -1.0D, 1.0D);
                this.motX += (this.e - this.motX) * 0.2D;
                this.motY += (this.f - this.motY) * 0.2D;
                this.motZ += (this.g - this.motZ) * 0.2D;
            } else {
                this.motY -= 0.04D;
            }

            MovingObjectPosition var4 = class_xj.a(this, true, false, this.a);
            if (var4 != null) {
                this.a(var4);
            }
        }

        this.setPosition(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        class_xj.a(this, 0.5F);
        if (!this.world.isClientSide) {
            if (this.b != null && !this.b.dead) {
                if (this.d > 0) {
                    --this.d;
                    if (this.d == 0) {
                        this.a(this.c != null ? this.c.k() : null);
                    }
                }

                if (this.c != null) {
                    BlockPosition var6 = new BlockPosition(this);
                    EnumDirection.EnumAxis axis = c.k();
                    if (this.world.d(var6.shift(c), false)) {
                        this.a(axis);
                    } else {
                        BlockPosition var7 = new BlockPosition(this.b);
                        if (axis == EnumDirection.EnumAxis.X && var6.getX() == var7.getX() || axis == EnumDirection.EnumAxis.Z && var6.getZ() == var7.getZ() || axis == EnumDirection.EnumAxis.Y && var6.getY() == var7.getY()) {
                            this.a(axis);
                        }
                    }
                }
            }
        } else {
            this.world.addParticle(ParticleList.END_ROD, this.locX - this.motX, this.locY - this.motY + 0.15D, this.locZ - this.motZ, 0.0D, 0.0D, 0.0D);
        }

    }

    public boolean av() {
        return false;
    }

    public float c(float var1) {
        return 1.0F;
    }

    protected void a(MovingObjectPosition var1) {
        if (var1.entity != null) {
            boolean var2 = var1.entity.damageEntity(DamageSource.projectile(this, this.a), 4.0F);
            if (var2) {
                this.a(this.a, var1.entity);
                if (var1.entity instanceof EntityLiving) {
                    ((EntityLiving) var1.entity).addEffect(new MobEffect(EffectList.LEVITATION.getId(), 200));
                }
            }
        } else {
            ((WorldServer) this.world).a(EnumParticle.EXPLOSION_LARGE, this.locX, this.locY, this.locZ, 2, 0.2D, 0.2D, 0.2D, 0.0D);
            this.makeSound("mob.irongolem.hit", 1.0F, 1.0F);
        }

        this.die();
    }

    public boolean ad() {
        return true;
    }

    public boolean damageEntity(DamageSource var1, float var2) {
        if (!this.world.isClientSide) {
            this.makeSound("mob.irongolem.hit", 1.0F, 1.0F);
            ((WorldServer) this.world).a(EnumParticle.CRIT, this.locX, this.locY, this.locZ, 15, 0.2D, 0.2D, 0.2D, 0.0D);
            this.die();
        }

        return true;
    }
}
