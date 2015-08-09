package com.lastabyss.carbon.entities;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class EntityNewArrow extends EntityArrow {

    @SuppressWarnings("unchecked")
    private static final Predicate<Entity> TARGET_ENTITY_SELECTOR = Predicates.and(IEntitySelector.d, IEntitySelector.a, new Predicate<Entity>() {
        public boolean apply(Entity entity) {
            return entity.ad();
        }
    });

    private int hitBlockX = -1;
    private int hitBlockY = -1;
    private int hitBlockZ = -1;
    private Block hitBlockId;
    private int hitBlockData;
    public boolean inGround;
    public int fromPlayer;
    public int shake;
    public Entity shooter;
    private int life;
    private int ticksInAir;
    private double damage = 2.0;
    public int knockbackStrength;

    @Override
    public void inactiveTick() {
        if (this.inGround) {
            ++this.life;
        }
        super.inactiveTick();
    }

    public EntityNewArrow(final World world) {
        super(world);
        this.j = 10.0;
        this.setSize(0.5f, 0.5f);
    }

    public EntityNewArrow(final World world, final double x, final double y, final double z) {
        this(world);
        this.setPosition(x, y, z);
    }

    public EntityNewArrow(final World world, final EntityLiving entity) {
        this(world, entity.locX, entity.locY + (double) entity.getHeadHeight() - 0.10000000149011612D, entity.locZ);
        this.shooter = entity;
        if (entity instanceof EntityHuman) {
            this.fromPlayer = 1;
        }
    }

    public void startShoot(float aX, float aY, float aZ, float f1, float f2) {
        float mX = -MathHelper.sin(aY * 0.017453292F) * MathHelper.cos(aX * 0.017453292F);
        float mY = -MathHelper.sin(aX * 0.017453292F);
        float mZ = MathHelper.cos(aY * 0.017453292F) * MathHelper.cos(aX * 0.017453292F);
        this.shoot(mX, mY, mZ, f1, f2);
    }

    @Override
    protected void h() {
        this.datawatcher.a(16, (byte) 0);
    }

    @Override
    public void shoot(double mX, double mY, double mZ, final float f1, final float f2) {
        float var9 = MathHelper.sqrt((mX * mX) + (mY * mY) + (mZ * mZ));
        mX /= var9;
        mY /= var9;
        mZ /= var9;
        mX += this.random.nextGaussian() * 0.007499999832361937D * f2;
        mY += this.random.nextGaussian() * 0.007499999832361937D * f2;
        mZ += this.random.nextGaussian() * 0.007499999832361937D * f2;
        mX *= f1;
        mY *= f1;
        mZ *= f1;
        this.motX = mX;
        this.motY = mY;
        this.motZ = mZ;
        float var10 = MathHelper.sqrt((mX * mX) + (mZ * mZ));
        this.lastYaw = this.yaw = (float) ((MathHelper.b(mX, mZ) * 180.0D) / 3.1415927410125732D);
        this.lastPitch = this.pitch = (float) ((MathHelper.b(mY, var10) * 180.0D) / 3.1415927410125732D);
        this.life = 0;
    }

    private void entityTickMethod() {
        this.world.methodProfiler.a("entityBaseTick");
        if (this.vehicle != null && this.vehicle.dead) {
            this.vehicle = null;
        }
        this.L = this.M;
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.lastPitch = this.pitch;
        this.lastYaw = this.yaw;
        if (!this.world.isClientSide && this.world instanceof WorldServer) {
            this.world.methodProfiler.a("portal");
            final int i = this.L();
            if (this.ak) {
                if (this.vehicle == null && this.al++ >= i) {
                    this.al = i;
                    this.portalCooldown = this.aq();
                    byte b0;
                    if (this.world.worldProvider.getDimension() == -1) {
                        b0 = 0;
                    } else {
                        b0 = -1;
                    }
                    this.c(b0);
                }
                this.ak = false;
            } else {
                if (this.al > 0) {
                    this.al -= 4;
                }
                if (this.al < 0) {
                    this.al = 0;
                }
            }
            if (this.portalCooldown > 0) {
                --this.portalCooldown;
            }
            this.world.methodProfiler.b();
        }
        this.Y();
        this.W();
        if (this.world.isClientSide) {
            this.fireTicks = 0;
        } else if (this.fireTicks > 0) {
            if (this.fireProof) {
                this.fireTicks -= 4;
                if (this.fireTicks < 0) {
                    this.fireTicks = 0;
                }
            } else {
                if (this.fireTicks % 20 == 0) {
                    this.damageEntity(DamageSource.BURN, 1.0f);
                }
                --this.fireTicks;
            }
        }
        if (this.ab()) {
            this.burnFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.locY < -64.0) {
            this.O();
        }
        if (!this.world.isClientSide) {
            this.b(0, this.fireTicks > 0);
        }
        this.justCreated = false;
        this.world.methodProfiler.b();
    }

    @Override
    public void t_() {
        entityTickMethod();
        if ((this.lastPitch == 0.0f) && (this.lastYaw == 0.0f)) {
            final float f = MathHelper.sqrt((this.motX * this.motX) + (this.motZ * this.motZ));
            final float n = (float) ((MathHelper.b(this.motX, this.motZ) * 180.0) / 3.1415927410125732);
            this.yaw = n;
            this.lastYaw = n;
            final float n2 = (float) ((MathHelper.b(this.motY, f) * 180.0) / 3.1415927410125732);
            this.pitch = n2;
            this.lastPitch = n2;
        }
        final BlockPosition blockposition = new BlockPosition(this.hitBlockX, this.hitBlockY, this.hitBlockZ);
        final IBlockData iblockdata = this.world.getType(blockposition);
        final Block block = iblockdata.getBlock();
        if (block.getMaterial() != Material.AIR) {
            block.updateShape(this.world, blockposition);
            final AxisAlignedBB axisalignedbb = block.a(this.world, blockposition, iblockdata);
            if ((axisalignedbb != null) && axisalignedbb.a(new Vec3D(this.locX, this.locY, this.locZ))) {
                this.inGround = true;
            }
        }
        if (this.shake > 0) {
            --this.shake;
        }
        if (this.inGround) {
            final int i = block.toLegacyData(iblockdata);
            if ((block == this.hitBlockId) && (i == this.hitBlockData)) {
                ++this.life;
                if (this.life >= this.world.spigotConfig.arrowDespawnRate) {
                    this.die();
                }
            } else {
                this.inGround = false;
                this.motX *= this.random.nextFloat() * 0.2f;
                this.motY *= this.random.nextFloat() * 0.2f;
                this.motZ *= this.random.nextFloat() * 0.2f;
                this.life = 0;
                this.ticksInAir = 0;
            }
        } else {
            ++this.ticksInAir;
            Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
            Vec3D vec3d2 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d2, false, true, false);
            vec3d = new Vec3D(this.locX, this.locY, this.locZ);
            vec3d2 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            if (movingobjectposition != null) {
                vec3d2 = new Vec3D(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
            }
            Entity target = null;
            double d0 = 0.0;
            for (Entity entityInAABB : this.world.a(this, this.getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0, 1.0, 1.0), TARGET_ENTITY_SELECTOR)) {
                if (((entityInAABB != this.shooter) || (this.ticksInAir >= 5))) {
                    final float f2 = 0.3f;
                    final AxisAlignedBB axisalignedbb2 = entityInAABB.getBoundingBox().grow(f2, f2, f2);
                    final MovingObjectPosition movingobjectposition2 = axisalignedbb2.a(vec3d, vec3d2);
                    if (movingobjectposition2 != null) {
                        final double d = vec3d.distanceSquared(movingobjectposition2.pos);
                        if ((d < d0) || (d0 == 0.0)) {
                            target = entityInAABB;
                            d0 = d;
                        }
                    }
                }
            }
            if (target != null) {
                movingobjectposition = new MovingObjectPosition(target);
            }
            if ((movingobjectposition != null) && (movingobjectposition.entity != null) && (movingobjectposition.entity instanceof EntityHuman)) {
                final EntityHuman entityhuman = (EntityHuman) movingobjectposition.entity;
                if (entityhuman.abilities.isInvulnerable || ((this.shooter instanceof EntityHuman) && !((EntityHuman) this.shooter).a(entityhuman))) {
                    movingobjectposition = null;
                }
            }
            if (movingobjectposition != null) {
                CraftEventFactory.callProjectileHitEvent(this);
                if (movingobjectposition.entity != null) {
                    final float f3 = MathHelper.sqrt((this.motX * this.motX) + (this.motY * this.motY) + (this.motZ * this.motZ));
                    int k = MathHelper.f(f3 * this.damage);
                    if (this.isCritical()) {
                        k += this.random.nextInt((k / 2) + 2);
                    }
                    DamageSource damagesource;
                    if (this.shooter == null) {
                        damagesource = DamageSource.arrow(this, this);
                    } else {
                        damagesource = DamageSource.arrow(this, this.shooter);
                    }
                    if (movingobjectposition.entity.damageEntity(damagesource, k)) {
                        if (this.isBurning() && !(movingobjectposition.entity instanceof EntityEnderman) && (!(movingobjectposition.entity instanceof EntityPlayer) || !(this.shooter instanceof EntityPlayer) || this.world.pvpMode)) {
                            final EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), target.getBukkitEntity(), 5);
                            Bukkit.getPluginManager().callEvent(combustEvent);
                            if (!combustEvent.isCancelled()) {
                                movingobjectposition.entity.setOnFire(combustEvent.getDuration());
                            }
                        }
                        if (movingobjectposition.entity instanceof EntityLiving) {
                            final EntityLiving entityliving = (EntityLiving) movingobjectposition.entity;
                            if (!this.world.isClientSide) {
                                entityliving.o(entityliving.bv() + 1);
                            }
                            if (this.knockbackStrength > 0) {
                                final float f4 = MathHelper.sqrt((this.motX * this.motX) + (this.motZ * this.motZ));
                                if (f4 > 0.0f) {
                                    movingobjectposition.entity.g((this.motX * this.knockbackStrength * 0.6000000238418579) / f4, 0.1, (this.motZ * this.knockbackStrength * 0.6000000238418579) / f4);
                                }
                            }
                            if (this.shooter instanceof EntityLiving) {
                                EnchantmentManager.a(entityliving, this.shooter);
                                EnchantmentManager.b((EntityLiving) this.shooter, (Entity) entityliving);
                            }
                            onEntityHit(entityliving);
                            if ((this.shooter != null) && (movingobjectposition.entity != this.shooter) && (movingobjectposition.entity instanceof EntityHuman) && (this.shooter instanceof EntityPlayer)) {
                                ((EntityPlayer) this.shooter).playerConnection.sendPacket(new PacketPlayOutGameStateChange(6, 0.0f));
                            }
                        }
                        this.makeSound("random.bowhit", 1.0f, 1.2f / ((this.random.nextFloat() * 0.2f) + 0.9f));
                        if (!(movingobjectposition.entity instanceof EntityEnderman)) {
                            this.die();
                        }
                    } else {
                        this.motX *= -0.10000000149011612;
                        this.motY *= -0.10000000149011612;
                        this.motZ *= -0.10000000149011612;
                        this.yaw += 180.0f;
                        this.lastYaw += 180.0f;
                        this.ticksInAir = 0;
                    }
                } else {
                    final BlockPosition blockposition2 = movingobjectposition.a();
                    this.hitBlockX = blockposition2.getX();
                    this.hitBlockY = blockposition2.getY();
                    this.hitBlockZ = blockposition2.getZ();
                    final IBlockData iblockdata2 = this.world.getType(blockposition2);
                    this.hitBlockId = iblockdata2.getBlock();
                    this.hitBlockData = this.hitBlockId.toLegacyData(iblockdata2);
                    this.motX = (float) (movingobjectposition.pos.a - this.locX);
                    this.motY = (float) (movingobjectposition.pos.b - this.locY);
                    this.motZ = (float) (movingobjectposition.pos.c - this.locZ);
                    final float f2 = MathHelper.sqrt((this.motX * this.motX) + (this.motY * this.motY) + (this.motZ * this.motZ));
                    this.locX -= (this.motX / f2) * 0.05000000074505806;
                    this.locY -= (this.motY / f2) * 0.05000000074505806;
                    this.locZ -= (this.motZ / f2) * 0.05000000074505806;
                    this.makeSound("random.bowhit", 1.0f, 1.2f / ((this.random.nextFloat() * 0.2f) + 0.9f));
                    this.inGround = true;
                    this.shake = 7;
                    this.setCritical(false);
                    if (this.hitBlockId.getMaterial() != Material.AIR) {
                        this.hitBlockId.a(this.world, blockposition2, iblockdata2, this);
                    }
                }
            }
            if (this.isCritical()) {
                for (int j = 0; j < 4; ++j) {
                    this.world.addParticle(EnumParticle.CRIT, this.locX + ((this.motX * j) / 4.0), this.locY + ((this.motY * j) / 4.0), this.locZ + ((this.motZ * j) / 4.0), -this.motX, -this.motY + 0.2, -this.motZ, new int[0]);
                }
            }
            this.locX += this.motX;
            this.locY += this.motY;
            this.locZ += this.motZ;
            final float f3 = MathHelper.sqrt((this.motX * this.motX) + (this.motZ * this.motZ));
            this.yaw = (float) ((MathHelper.b(this.motX, this.motZ) * 180.0) / 3.1415927410125732);
            this.pitch = (float) ((MathHelper.b(this.motY, f3) * 180.0) / 3.1415927410125732);
            while ((this.pitch - this.lastPitch) < -180.0f) {
                this.lastPitch -= 360.0f;
            }
            while ((this.pitch - this.lastPitch) >= 180.0f) {
                this.lastPitch += 360.0f;
            }
            while ((this.yaw - this.lastYaw) < -180.0f) {
                this.lastYaw -= 360.0f;
            }
            while ((this.yaw - this.lastYaw) >= 180.0f) {
                this.lastYaw += 360.0f;
            }
            this.pitch = this.lastPitch + ((this.pitch - this.lastPitch) * 0.2f);
            this.yaw = this.lastYaw + ((this.yaw - this.lastYaw) * 0.2f);
            float f5 = 0.99f;
            final float f2 = 0.05f;
            if (this.V()) {
                for (int l = 0; l < 4; ++l) {
                    final float f4 = 0.25f;
                    this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - (this.motX * f4), this.locY - (this.motY * f4), this.locZ - (this.motZ * f4), this.motX, this.motY, this.motZ, new int[0]);
                }
                f5 = 0.6f;
            }
            if (this.U()) {
                this.extinguish();
            }
            this.motX *= f5;
            this.motY *= f5;
            this.motZ *= f5;
            this.motY -= f2;
            this.setPosition(this.locX, this.locY, this.locZ);
            this.checkBlockCollisions();
        }
    }

    @Override
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("xTile", (short) this.hitBlockX);
        nbttagcompound.setShort("yTile", (short) this.hitBlockY);
        nbttagcompound.setShort("zTile", (short) this.hitBlockZ);
        nbttagcompound.setShort("life", (short) this.life);
        final MinecraftKey minecraftkey = Block.REGISTRY.c(this.hitBlockId);
        nbttagcompound.setString("inTile", (minecraftkey == null) ? "" : minecraftkey.toString());
        nbttagcompound.setByte("inData", (byte) this.hitBlockData);
        nbttagcompound.setByte("shake", (byte) this.shake);
        nbttagcompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        nbttagcompound.setByte("pickup", (byte) this.fromPlayer);
        nbttagcompound.setDouble("damage", this.damage);
    }

    @Override
    public void a(final NBTTagCompound nbttagcompound) {
        this.hitBlockX = nbttagcompound.getShort("xTile");
        this.hitBlockY = nbttagcompound.getShort("yTile");
        this.hitBlockZ = nbttagcompound.getShort("zTile");
        this.life = nbttagcompound.getShort("life");
        if (nbttagcompound.hasKeyOfType("inTile", 8)) {
            this.hitBlockId = Block.getByName(nbttagcompound.getString("inTile"));
        } else {
            this.hitBlockId = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
        }
        this.hitBlockData = (nbttagcompound.getByte("inData") & 0xFF);
        this.shake = (nbttagcompound.getByte("shake") & 0xFF);
        this.inGround = (nbttagcompound.getByte("inGround") == 1);
        if (nbttagcompound.hasKeyOfType("damage", 99)) {
            this.damage = nbttagcompound.getDouble("damage");
        }
        if (nbttagcompound.hasKeyOfType("pickup", 99)) {
            this.fromPlayer = nbttagcompound.getByte("pickup");
        } else if (nbttagcompound.hasKeyOfType("player", 99)) {
            this.fromPlayer = (nbttagcompound.getBoolean("player") ? 1 : 0);
        }
    }

    @Override
    public void d(final EntityHuman entityhuman) {
        if (!this.world.isClientSide && this.inGround && (this.shake <= 0)) {
            boolean pickup = (this.fromPlayer == 1) || ((this.fromPlayer == 2) && entityhuman.abilities.canInstantlyBuild);

            ItemStack itemstack = this.getItemStack();
            if ((this.fromPlayer == 1) && !entityhuman.inventory.pickup(itemstack)) {
                pickup = false;
            }

            final EntityItem item = new EntityItem(this.world, this.locX, this.locY, this.locZ, itemstack);
            final PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player) entityhuman.getBukkitEntity(), new CraftItem(this.world.getServer(), this, item), 0);
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }

            if (pickup) {
                this.makeSound("random.pop", 0.2F, (((this.random.nextFloat() - this.random.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
                entityhuman.receive(this, 1);
                this.die();
            }
        }
    }

    @Override
    protected boolean s_() {
        return false;
    }

    @Override
    public void b(final double d0) {
        this.damage = d0;
    }

    @Override
    public double j() {
        return this.damage;
    }

    @Override
    public void setKnockbackStrength(final int i) {
        this.knockbackStrength = i;
    }

    @Override
    public boolean aD() {
        return false;
    }

    @Override
    public float getHeadHeight() {
        return 0.0f;
    }

    @Override
    public void setCritical(final boolean flag) {
        final byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            this.datawatcher.watch(16, (Object) (byte) (b0 | 0x1));
        } else {
            this.datawatcher.watch(16, (Object) (byte) (b0 & 0xFFFFFFFE));
        }
    }

    @Override
    public boolean isCritical() {
        final byte b0 = this.datawatcher.getByte(16);
        return (b0 & 0x1) != 0x0;
    }

    @Override
    public boolean isInGround() {
        return this.inGround;
    }

    public void onEntityHit(EntityLiving living) {
    }

    protected ItemStack getItemStack() {
        return new ItemStack(Items.ARROW);
    }

}
