package com.lastabyss.carbon.network.packets;

import io.netty.handler.codec.EncoderException;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;

import com.google.common.base.Charsets;
import com.lastabyss.carbon.network.CarbonPlayerConnection;

public class CarbonPacketPlayInUpdateSign implements Packet<CarbonPlayerConnection>{

	private BlockPosition position;
	private IChatBaseComponent[] lines;

	public void a(PacketDataSerializer serializer) throws IOException {
		this.position = BlockPosition.fromLong(serializer.readLong());
		this.lines = new IChatBaseComponent[4];

		for (int line = 0; line < 4; ++line) {
			this.lines[line] = IChatBaseComponent.ChatSerializer.a(new String(serializer.readBytes(readVarInt(serializer)).array(), Charsets.UTF_8));
		}
	}

	public void b(PacketDataSerializer serializer) throws IOException {
		serializer.writeLong(position.asLong());

		for (int line = 0; line < 4; ++line) {
			String string = IChatBaseComponent.ChatSerializer.a(lines[line]);
			byte[] bytes = string.getBytes(Charsets.UTF_8);
			if (bytes.length > 32767) {
				throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
			} else {
				int i = bytes.length;
				while ((i & 128) != 0) {
					serializer.writeByte((i & 127) | 128);
					i >>>= 7;
				}
				serializer.writeByte(i);
				serializer.writeBytes(bytes);
			}
		}
	}

	public void a(CarbonPlayerConnection var1) {
		var1.handle(this);
	}

	public BlockPosition getPosition() {
		return this.position;
	}

	public IChatBaseComponent[] getLines() {
		return this.lines;
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
