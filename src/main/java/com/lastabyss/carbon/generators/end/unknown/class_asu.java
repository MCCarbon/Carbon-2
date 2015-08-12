package com.lastabyss.carbon.generators.end.unknown;

import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.StructureBoundingBox;

import com.lastabyss.carbon.types.EnumRotation;
import com.lastabyss.carbon.types.class_a_in_class_agj;

public class class_asu {

    private class_a_in_class_agj a;
    private EnumRotation b;
    private boolean c;
    private boolean d;
    private ChunkCoordIntPair e;
    private StructureBoundingBox f;
    private boolean g;

    public class_asu() {
        this(class_a_in_class_agj.NONE, EnumRotation.NONE, false, false, null);
    }

    public class_asu(class_a_in_class_agj var1, EnumRotation var2, boolean var3, boolean var4, StructureBoundingBox var5) {
        this.b = var2;
        this.a = var1;
        this.c = var3;
        this.d = var4;
        this.e = null;
        this.f = var5;
        this.g = true;
    }

    public class_asu a() {
        return (new class_asu(this.a, this.b, this.c, this.d, this.f)).a(this.e).c(this.g);
    }

    public class_asu a(class_a_in_class_agj var1) {
        this.a = var1;
        return this;
    }

    public class_asu a(EnumRotation var1) {
        this.b = var1;
        return this;
    }

    public class_asu a(boolean var1) {
        this.c = var1;
        return this;
    }

    public class_asu b(boolean var1) {
        this.d = var1;
        return this;
    }

    public class_asu a(ChunkCoordIntPair var1) {
        this.e = var1;
        return this;
    }

    public class_asu a(StructureBoundingBox var1) {
        this.f = var1;
        return this;
    }

    public class_a_in_class_agj b() {
        return this.a;
    }

    public class_asu c(boolean var1) {
        this.g = var1;
        return this;
    }

    public EnumRotation c() {
        return this.b;
    }

    public boolean e() {
        return this.c;
    }

    public boolean f() {
        return this.d;
    }

    public StructureBoundingBox g() {
        if (this.f == null && this.e != null) {
            this.i();
        }

        return this.f;
    }

    public boolean h() {
        return this.g;
    }

    public void i() {
        this.f = this.b(this.e);
    }

    private StructureBoundingBox b(ChunkCoordIntPair var1) {
        if (var1 == null) {
            return null;
        } else {
            int var2 = var1.x * 16;
            int var3 = var1.z * 16;
            return new StructureBoundingBox(var2, 0, var3, var2 + 16 - 1, 255, var3 + 16 - 1);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return this.a();
    }

}
