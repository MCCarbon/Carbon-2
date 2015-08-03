package com.lastabyss.carbon.network.packets;

import io.netty.handler.codec.EncoderException;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;

import com.google.common.base.Charsets;
import com.lastabyss.carbon.network.CarbonPlayerConnection;

public class CarbonPacketPlayOutKickDisconnect implements Packet<CarbonPlayerConnection>{
	
	   private IChatBaseComponent a;

	   //register packet
	   public CarbonPacketPlayOutKickDisconnect(IChatBaseComponent var1) {
	      this.a = var1;
	   }

	   public void a(PacketDataSerializer var1) throws IOException {
	      this.a = IChatBaseComponent.ChatSerializer.a(new String(var1.readBytes(readVarInt(var1)).array(), Charsets.UTF_8));
	   }

	   public void b(PacketDataSerializer var1) throws IOException {
		   String string = IChatBaseComponent.ChatSerializer.a(this.a);
		   byte[] bytes = string.getBytes(Charsets.UTF_8);
			if (bytes.length > 32767) {
				throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
			} else {
				int i = bytes.length;
				while ((i & -128) != 0) {
					var1.writeByte((i & 127) | 128);
					i >>>= 7;
				}
				var1.writeByte(i);
				var1.writeBytes(bytes);
			}
	   }

	   public void a(CarbonPlayerConnection listener) {
		   //listener.handle(this);
	   }

	   public void handle(PacketListener var1) {
	      //this.a((PacketListenerPlayOut)var1);
	   }
	   
		public int readVarInt(PacketDataSerializer var1) {
			int i = 0;
			int length = 0;

			byte b;
			do {
				b = var1.readByte();
				i |= (b & 127) << (length++ * 7);
				if (length > 5) {
					throw new RuntimeException("VarInt too big");
				}
			} while ((b & 128) == 128);

			return i;
		}

}
