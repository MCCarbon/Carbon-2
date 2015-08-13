package com.lastabyss.carbon.types;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

import java.util.List;

public final class class_xj {

    public static MovingObjectPosition a(Entity var0, boolean var1, boolean var2, Entity var3) {
        double var4 = var0.locX;
        double var6 = var0.locY;
        double var8 = var0.locZ;
        double var10 = var0.motX;
        double var12 = var0.motY;
        double var14 = var0.motZ;
        World var16 = var0.world;
        Vec3D var17 = new Vec3D(var4, var6, var8);
        Vec3D var18 = new Vec3D(var4 + var10, var6 + var12, var8 + var14);
        MovingObjectPosition var19 = var16.rayTrace(var17, var18, false, true, false);
        if(var1) {
            if(var19 != null) {
                var18 = new Vec3D(var19.pos.a, var19.pos.b, var19.pos.c);
            }

            Entity var20 = null;
            List var21 = var16.getEntities(var0, var0.getBoundingBox().a(var10, var12, var14).grow(1.0D, 1.0D, 1.0D));
            double var22 = 0.0D;

            for(int var24 = 0; var24 < var21.size(); ++var24) {
                Entity var25 = (Entity)var21.get(var24);
                if(var25.ad() && (var2 || !var25.k(var3)) && !var25.noclip) {
                    float var26 = 0.3F;
                    AxisAlignedBB var27 = var25.getBoundingBox().grow((double)var26, (double)var26, (double)var26);
                    MovingObjectPosition var28 = var27.a(var17, var18);
                    if(var28 != null) {
                        double var29 = var17.distanceSquared(var28.pos);
                        if(var29 < var22 || var22 == 0.0D) {
                            var20 = var25;
                            var22 = var29;
                        }
                    }
                }
            }

            if(var20 != null) {
                var19 = new MovingObjectPosition(var20);
            }
        }

        return var19;
    }

    public static final void a(Entity var0, float var1) {
        double var2 = var0.motX;
        double var4 = var0.motY;
        double var6 = var0.motZ;
        float var8 = MathHelper.sqrt(var2 * var2 + var6 * var6);
        var0.yaw = (float)(MathHelper.b(var6, var2) * 180.0D / 3.1415927410125732D) + 90.0F;

        for(var0.pitch = (float)(MathHelper.b((double)var8, var4) * 180.0D / 3.1415927410125732D) - 90.0F; var0.pitch - var0.lastPitch < -180.0F; var0.lastPitch -= 360.0F) {
        }

        while(var0.pitch - var0.lastPitch >= 180.0F) {
            var0.lastPitch += 360.0F;
        }

        while(var0.yaw - var0.lastYaw < -180.0F) {
            var0.lastYaw -= 360.0F;
        }

        while(var0.yaw - var0.lastYaw >= 180.0F) {
            var0.lastYaw += 360.0F;
        }

        var0.pitch = var0.lastPitch + (var0.pitch - var0.lastPitch) * var1;
        var0.yaw = var0.lastYaw + (var0.yaw - var0.lastYaw) * var1;
    }
}
