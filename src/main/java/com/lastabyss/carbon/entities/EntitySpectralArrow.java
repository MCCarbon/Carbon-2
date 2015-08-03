package com.lastabyss.carbon.entities;

import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;

public class EntitySpectralArrow extends EntityArrow {

	   private int f = 200;

	   public EntitySpectralArrow(World var1) {
	      super(var1);
	   }

	   public EntitySpectralArrow(World var1, EntityLiving var2, float f) {
	      super(var1, var2, f);
	   }

	   public EntitySpectralArrow(World var1, double var2, double var4, double var6) {
	      super(var1, var2, var4, var6);
	   }

	   public void t_() {
	      super.t_();
	      if(this.world.isClientSide && !this.isInGround()) {
	    	  
	         this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
	      }

	   }

	   protected void b(EntityLiving var1) {
	      super.a(var1);
	      MobEffect var2 = new MobEffect(MobEffectList.WATER_BREATHING.getId(), this.f, 0);
	      var1.addEffect(var2);
	   }

	   public void read(NBTTagCompound var1) {
	      super.a(var1);
	      if(var1.hasKey("Duration")) {
	         this.f = var1.getInt("Duration");
	      }

	   }

	   public void write(NBTTagCompound var1) {
	      super.b(var1);
	      var1.setInt("Duration", this.f);
	   }
	
}
