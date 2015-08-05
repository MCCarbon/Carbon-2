package com.lastabyss.carbon.network.packets;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Packet;

import com.google.common.collect.Sets;

public class class_le extends class_oh {
	   private final Set g = Sets.newHashSet();
	   private final Set h;
	   private boolean i;

	   public class_le(IChatBaseComponent var1, class_oh.class_a_in_class_oh var2, class_oh.class_b_in_class_oh var3, boolean var4, boolean var5) {
	      super(UUID.randomUUID(), var1, var2, var3, var4, var5);
	      this.h = Collections.unmodifiableSet(this.g);
	      this.i = true;
	   }

	   public void a(float var1) {
	      if(var1 != health) {
	         super.a(var1);
	         this.a(CarbonPacketPlayOutBossBar.BossBarType.c);
	      }

	   }

	   private void a(CarbonPacketPlayOutBossBar.BossBarType var1) {
	      if(this.i) {
	    	  CarbonPacketPlayOutBossBar var2 = new CarbonPacketPlayOutBossBar(var1, this);
	         Iterator var3 = this.g.iterator();

	         while(var3.hasNext()) {
	            EntityPlayer var4 = (EntityPlayer)var3.next();
	            var4.playerConnection.sendPacket((Packet)var2);
	         }
	      }

	   }

	   public void a(EntityPlayer var1) {
	      if(this.g.add(var1) && this.i) {
	         var1.playerConnection.sendPacket((Packet)(new CarbonPacketPlayOutBossBar(CarbonPacketPlayOutBossBar.BossBarType.a, this)));
	      }

	   }

	   public void b(EntityPlayer var1) {
	      if(this.g.remove(var1) && this.i) {
	         var1.playerConnection.sendPacket((Packet)(new CarbonPacketPlayOutBossBar(CarbonPacketPlayOutBossBar.BossBarType.b, this)));
	      }

	   }

	   public void c(boolean var1) {
	      if(var1 != this.i) {
	         this.i = var1;
	         Iterator var2 = this.g.iterator();

	         while(var2.hasNext()) {
	            EntityPlayer var3 = (EntityPlayer)var2.next();
	            var3.playerConnection.sendPacket((Packet)(new CarbonPacketPlayOutBossBar(var1? CarbonPacketPlayOutBossBar.BossBarType.a: CarbonPacketPlayOutBossBar.BossBarType.b, this)));
	         }
	      }

	   }

	   public Collection c() {
	      return this.h;
	   }
	}