package com.lastabyss.carbon.network.packets;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

import com.lastabyss.carbon.utils.PacketDataSerializerHelper;

public class CarbonPacketPlayOutBossBar implements Packet<PacketListenerPlayOut> {
	
	   private UUID uniqueId;
	   private BossBarType bossBarType;
	   private IChatBaseComponent message;
	   private float health;
	   private class_oh.class_a_in_class_oh e;
	   private class_oh.class_b_in_class_oh f;
	   private boolean g;
	   private boolean h;

	   public CarbonPacketPlayOutBossBar() {
	   }

	   public CarbonPacketPlayOutBossBar(BossBarType var1, class_oh var2) {
	      this.bossBarType = var1;
	      this.uniqueId = var2.d();
	      this.message = var2.e();
	      this.health = var2.f();
	      this.e = var2.g();
	      this.f = var2.h();
	      this.g = var2.i();
	      this.h = var2.j();
	   }

	   public void a(PacketDataSerializer serializer) throws IOException {
	      this.uniqueId = PacketDataSerializerHelper.readUUID(serializer);
	      this.bossBarType = (BossBarType)PacketDataSerializerHelper.readEnum(serializer, BossBarType.class);
	      switch(CarbonPacketPlayOutBossBar.SyntheticClass_1.a[this.bossBarType.ordinal()]) {
	      case 1:
	         this.message = PacketDataSerializerHelper.readChat(serializer);
	         this.health = serializer.readFloat();
	         this.e = (class_oh.class_a_in_class_oh)PacketDataSerializerHelper.readEnum(serializer, class_oh.class_a_in_class_oh.class);
	         this.f = (class_oh.class_b_in_class_oh)PacketDataSerializerHelper.readEnum(serializer, class_oh.class_b_in_class_oh.class);
	         this.a(serializer.readUnsignedByte());
	      case 2:
	      default:
	         break;
	      case 3:
	         this.health = serializer.readFloat();
	         break;
	      case 4:
	         this.message = PacketDataSerializerHelper.readChat(serializer);
	         break;
	      case 5:
	         this.e = (class_oh.class_a_in_class_oh)PacketDataSerializerHelper.readEnum(serializer, class_oh.class_a_in_class_oh.class);
	         this.f = (class_oh.class_b_in_class_oh)PacketDataSerializerHelper.readEnum(serializer, class_oh.class_b_in_class_oh.class);
	         break;
	      case 6:
	         this.a(serializer.readUnsignedByte());
	      }

	   }

	   private void a(int var1) {
	      this.g = (var1 & 1) > 0;
	      this.h = (var1 & 2) > 0;
	   }

	   public void b(PacketDataSerializer var1) throws IOException {
		   PacketDataSerializerHelper.writeUUID(var1, this.uniqueId);
		   PacketDataSerializerHelper.writeEnum(var1, this.bossBarType);
	      switch(CarbonPacketPlayOutBossBar.SyntheticClass_1.a[this.bossBarType.ordinal()]) {
	      case 1:
	    	  PacketDataSerializerHelper.writeChat(var1, this.message);
	         var1.writeFloat(this.health);
	         PacketDataSerializerHelper.writeEnum(var1, this.e);
	         PacketDataSerializerHelper.writeEnum(var1, this.f);
	         var1.writeByte(this.i());
	      case 2:
	      default:
	         break;
	      case 3:
	         var1.writeFloat(this.health);
	         break;
	      case 4:
	    	  PacketDataSerializerHelper.writeChat(var1, this.message);
	         break;
	      case 5:
	    	  PacketDataSerializerHelper.writeEnum(var1, this.e);
	    	  PacketDataSerializerHelper.writeEnum(var1, this.f);
	         break;
	      case 6:
	         var1.writeByte(this.i());
	      }

	   }

	   private int i() {
	      int var1 = 0;
	      if(this.g) {
	         var1 |= 1;
	      }

	      if(this.h) {
	         var1 |= 2;
	      }

	      return var1;
	   }

	   public void a(PacketListenerPlayOut var1) {
	     
	   }

	   // $FF: synthetic method
	   // $FF: bridge method
	   public void handle(PacketListener var1) {
	      this.a((PacketListenerPlayOut) var1);
	   }

	   // $FF: synthetic class
	   static class SyntheticClass_1 {
	      // $FF: synthetic field
	      static final int[] a = new int[BossBarType.values().length];

	      static {
	         try {
	            a[BossBarType.a.ordinal()] = 1;
	         } catch (NoSuchFieldError var6) {
	            ;
	         }

	         try {
	            a[BossBarType.b.ordinal()] = 2;
	         } catch (NoSuchFieldError var5) {
	            ;
	         }

	         try {
	            a[BossBarType.c.ordinal()] = 3;
	         } catch (NoSuchFieldError var4) {
	            ;
	         }

	         try {
	            a[BossBarType.d.ordinal()] = 4;
	         } catch (NoSuchFieldError var3) {
	            ;
	         }

	         try {
	            a[BossBarType.e.ordinal()] = 5;
	         } catch (NoSuchFieldError var2) {
	            ;
	         }

	         try {
	            a[BossBarType.f.ordinal()] = 6;
	         } catch (NoSuchFieldError var1) {
	            ;
	         }

	      }
	   }

	   public enum BossBarType {
	      a,
	      b,
	      c,
	      d,
	      e,
	      f;
	   }
	}
