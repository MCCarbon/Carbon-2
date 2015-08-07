package com.lastabyss.carbon.network.packets;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

import com.lastabyss.carbon.api.Bossbar;
import com.lastabyss.carbon.api.Bossbar.EnumBossbarColor;
import com.lastabyss.carbon.api.Bossbar.EnumBossbarDivider;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;

public class CarbonPacketPlayOutBossBar implements Packet<PacketListenerPlayOut> {

	public static final int REAL_ID = 0x49;
	public static final int FAKE_ID = 99;

	private UUID uniqueId;
	private EnumBossBarAction action;
	private IChatBaseComponent message;
	private float health;
	private EnumBossbarColor color;
	private EnumBossbarDivider divider;
	private boolean darkenSky;
	private boolean isDragon;

	public CarbonPacketPlayOutBossBar() {
	}

	public CarbonPacketPlayOutBossBar(EnumBossBarAction action, Bossbar bossbar) {
		this.action = action;
		this.uniqueId = bossbar.getUniqueId();
		this.message = bossbar.getMessage();
		this.health = bossbar.getHealth();
		this.color = bossbar.getColor();
		this.divider = bossbar.getDivider();
		this.darkenSky = bossbar.shouldDarkenSky();
		this.isDragon = bossbar.isDragon();
	}

	public void setAction(EnumBossBarAction action) {

	}

	public void resetValues(Bossbar bossbar) {
		this.uniqueId = bossbar.getUniqueId();
		this.message = bossbar.getMessage();
		this.health = bossbar.getHealth();
		this.color = bossbar.getColor();
		this.divider = bossbar.getDivider();
		this.darkenSky = bossbar.shouldDarkenSky();
		this.isDragon = bossbar.isDragon();
	}

	@Override
	public void a(PacketDataSerializer serializer) throws IOException {
		this.uniqueId = PacketDataSerializerHelper.readUUID(serializer);
		this.action = PacketDataSerializerHelper.readEnum(serializer, EnumBossBarAction.class);
		switch (action) {
			case ADD: {
				this.message = PacketDataSerializerHelper.readChat(serializer);
				this.health = serializer.readFloat();
				this.color = (Bossbar.EnumBossbarColor) PacketDataSerializerHelper.readEnum(serializer, Bossbar.EnumBossbarColor.class);
				this.divider = (Bossbar.EnumBossbarDivider) PacketDataSerializerHelper.readEnum(serializer, Bossbar.EnumBossbarDivider.class);
				this.decodeFlags(serializer.readUnsignedByte());
			}
			case REMOVE: {
				break;
			}
			case UPDATE_HEALTH: {
				this.health = serializer.readFloat();
				break;
			}
			case UPDATE_TITLE: {
				this.message = PacketDataSerializerHelper.readChat(serializer);
				break;
			}
			case UPDATE_STYLE: {
				this.color = PacketDataSerializerHelper.readEnum(serializer, Bossbar.EnumBossbarColor.class);
				this.divider = PacketDataSerializerHelper.readEnum(serializer, Bossbar.EnumBossbarDivider.class);
				break;
			}
			case UPDATE_FLAGS: {
				this.decodeFlags(serializer.readUnsignedByte());
				break;
			}
			default: {
				break;
			}
		}
	}

	private void decodeFlags(int bitset) {
		this.darkenSky = (bitset & 1) > 0;
		this.isDragon = (bitset & 2) > 0;
	}

	@Override
	public void b(PacketDataSerializer serializer) throws IOException {
		PacketDataSerializerHelper.writeUUID(serializer, this.uniqueId);
		PacketDataSerializerHelper.writeEnum(serializer, this.action);
		switch (action) {
			case ADD:
				PacketDataSerializerHelper.writeChat(serializer, this.message);
				serializer.writeFloat(this.health);
				PacketDataSerializerHelper.writeEnum(serializer, this.color);
				PacketDataSerializerHelper.writeEnum(serializer, this.divider);
				serializer.writeByte(this.encodeFlags());
			case REMOVE: {
				break;
			}
			case UPDATE_HEALTH: {
				serializer.writeFloat(this.health);
				break;
			}
			case UPDATE_TITLE: {
				PacketDataSerializerHelper.writeChat(serializer, this.message);
				break;
			}
			case UPDATE_STYLE: {
				PacketDataSerializerHelper.writeEnum(serializer, this.color);
				PacketDataSerializerHelper.writeEnum(serializer, this.divider);
				break;
			}
			case UPDATE_FLAGS: {
				serializer.writeByte(this.encodeFlags());
				break;
			}
			default: {
				break;
			}
		}
	}

	private int encodeFlags() {
		int bitset = 0;
		if (this.darkenSky) {
			bitset |= 1;
		}
		if (this.isDragon) {
			bitset |= 2;
		}
		return bitset;
	}

	@Override
	public void a(PacketListenerPlayOut listener) {
	}

	public enum EnumBossBarAction {
		ADD, REMOVE, UPDATE_HEALTH, UPDATE_TITLE, UPDATE_STYLE, UPDATE_FLAGS;
	}

}
