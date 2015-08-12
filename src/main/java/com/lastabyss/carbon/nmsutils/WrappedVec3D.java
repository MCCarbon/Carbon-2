package com.lastabyss.carbon.nmsutils;

import net.minecraft.server.v1_8_R3.BaseBlockPosition;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Vec3D;

public class WrappedVec3D extends Vec3D {

    public WrappedVec3D(double x, double y, double z) {
        super(x, y, z);
    }

    public WrappedVec3D(BaseBlockPosition position) {
        super(position);
    }

    public WrappedVec3D normalize() {
        double var1 = (double) MathHelper.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
        return var1 < 1.0E-4D ? new WrappedVec3D(0.0D, 0.0D, 0.0D) : new WrappedVec3D(this.getX() / var1, this.getY() / var1, this.getZ() / var1);
    }

    public double dotProduct(Vec3D other) {
        return this.getX() * other.a + this.getY() * other.b + this.getZ() * other.c;
    }

    public WrappedVec3D substract(double x, double y, double z) {
        return this.add(-x, -y, -z);
    }

    public WrappedVec3D add(Vec3D vec) {
        return this.add(vec.a, vec.b, vec.c);
    }

    public WrappedVec3D add(double x, double y, double z) {
        return new WrappedVec3D(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    public double distanceSquared(Vec3D other) {
        double diffX = other.a - this.getX();
        double diffY = other.b - this.getY();
        double diffZ = other.c - this.getZ();
        return diffX * diffX + diffY * diffY + diffZ * diffZ;
    }

    public WrappedVec3D multiply(double mul) {
        return new WrappedVec3D(this.getX() * mul, this.getY() * mul, this.getZ() * mul);
    }

    public double length() {
        return (double) MathHelper.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
    }

    public WrappedVec3D getIntermediateWithXValue(Vec3D other, double x) {
        double diffX = other.a - this.getX();
        double diffY = other.b - this.getY();
        double diffZ = other.c - this.getZ();
        if (diffX * diffX < 1.0000000116860974E-7D) {
            return null;
        } else {
            double mX = (x - this.getX()) / diffX;
            return mX >= 0.0D && mX <= 1.0D ? new WrappedVec3D(this.getX() + diffX * mX, this.getY() + diffY * mX, this.getZ() + diffZ * mX) : null;
        }
    }

    public WrappedVec3D getIntermediateWithYValue(Vec3D other, double y) {
        double diffX = other.a - this.getX();
        double diffY = other.b - this.getY();
        double diffZ = other.c - this.getZ();
        if (diffY * diffY < 1.0000000116860974E-7D) {
            return null;
        } else {
            double mY = (y - this.getY()) / diffY;
            return mY >= 0.0D && mY <= 1.0D ? new WrappedVec3D(this.getX() + diffX * mY, this.getY() + diffY * mY, this.getZ() + diffZ * mY) : null;
        }
    }

    public WrappedVec3D getIntermediateWithZValue(Vec3D other, double z) {
        double diffX = other.a - this.getX();
        double diffY = other.b - this.getY();
        double diffZ = other.c - this.getZ();
        if (diffZ * diffZ < 1.0000000116860974E-7D) {
            return null;
        } else {
            double mZ = (z - this.getZ()) / diffZ;
            return mZ >= 0.0D && mZ <= 1.0D ? new WrappedVec3D(this.getX() + diffX * mZ, this.getY() + diffY * mZ, this.getZ() + diffZ * mZ) : null;
        }
    }

    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ")";
    }

    public WrappedVec3D rotatePitch(float pitch) {
        float cos = MathHelper.cos(pitch);
        float sin = MathHelper.sin(pitch);
        double x = this.getX();
        double y = this.getY() * (double) cos + this.getZ() * (double) sin;
        double z = this.getZ() * (double) cos - this.getY() * (double) sin;
        return new WrappedVec3D(x, y, z);
    }

    public WrappedVec3D rotateYaw(float yaw) {
        float cos = MathHelper.cos(yaw);
        float sin = MathHelper.sin(yaw);
        double x = this.getX() * (double) cos + this.getZ() * (double) sin;
        double y = this.getY();
        double z = this.getZ() * (double) cos - this.getX() * (double) sin;
        return new WrappedVec3D(x, y, z);
    }

    public double getX() {
        return a;
    }

    public double getY() {
        return b;
    }

    public double getZ() {
        return c;
    }

}
