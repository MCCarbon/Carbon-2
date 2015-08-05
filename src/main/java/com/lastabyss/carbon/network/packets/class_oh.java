package com.lastabyss.carbon.network.packets;

import java.util.UUID;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;

public abstract class class_oh {
	
	   private final UUID uniqueId;
	   protected IChatBaseComponent message;
	   protected float health;
	   protected class_oh.class_a_in_class_oh c;
	   protected class_oh.class_b_in_class_oh d;
	   protected boolean e;
	   protected boolean f;

	   public class_oh(UUID var1, IChatBaseComponent var2, class_oh.class_a_in_class_oh var3, class_oh.class_b_in_class_oh var4, boolean var5, boolean var6) {
	      this.uniqueId = var1;
	      this.message = var2;
	      this.c = var3;
	      this.d = var4;
	      this.e = var5;
	      this.f = var6;
	      this.health = 1.0F;
	   }

	   public UUID d() {
	      return this.uniqueId;
	   }

	   public IChatBaseComponent e() {
	      return this.message;
	   }

	   public float f() {
	      return this.health;
	   }

	   public void a(float var1) {
	      this.health = var1;
	   }

	   public class_oh.class_a_in_class_oh g() {
	      return this.c;
	   }

	   public class_oh.class_b_in_class_oh h() {
	      return this.d;
	   }

	   public boolean i() {
	      return this.e;
	   }

	   public boolean j() {
	      return this.f;
	   }

	   public static enum class_b_in_class_oh {
	      a,
	      b,
	      c,
	      d,
	      e;
	   }

	   public static enum class_a_in_class_oh {
	      a,
	      b,
	      c,
	      d,
	      e,
	      f,
	      g;
	   }
	}