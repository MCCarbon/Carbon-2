package com.lastabyss.carbon.network.packets;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

import com.lastabyss.carbon.types.Bossbar;
import com.lastabyss.carbon.types.Bossbar.BossBarColor;
import com.lastabyss.carbon.types.Bossbar.BossBarDivider;
import com.lastabyss.carbon.utils.PacketDataSerializerHelper;

public class CarbonPacketPlayOutBossBar implements Packet<PacketListenerPlayOut> {

	public static final int REAL_ID = 0x49;
	public static final int FAKE_ID = 99;

	private UUID uniqueId;
	private EnumBossBarAction action;
	private IChatBaseComponent message;
	private float health;
	private BossBarColor color;
	private BossBarDivider divider;
	private boolean darkenSky;
	private boolean isDragon;

	public CarbonPacketPlayOutBossBar() {
	}

	public CarbonPacketPlayOutBossBar(EnumBossBarAction action, Bossbar bossBar) {
		this.action = action;
		this.uniqueId = bossBar.getUniqueId();
		this.message = bossBar.getMessage();
		this.health = bossBar.getHealth();
		this.color = bossBar.getColor();
		this.divider = bossBar.getDivider();
		this.darkenSky = bossBar.shouldDarkenSky();
		this.isDragon = bossBar.isDragon();
	}

	public void setAction(EnumBossBarAction action) {

	}

	public void resetValues(Bossbar bossBar) {
		this.uniqueId = bossBar.getUniqueId();
		this.message = bossBar.getMessage();
		this.health = bossBar.getHealth();
		this.color = bossBar.getColor();
		this.divider = bossBar.getDivider();
		this.darkenSky = bossBar.shouldDarkenSky();
		this.isDragon = bossBar.isDragon();
	}

	@Override
	public void a(PacketDataSerializer serializer) throws IOException {
		this.uniqueId = PacketDataSerializerHelper.readUUID(serializer);
		this.action = PacketDataSerializerHelper.readEnum(serializer, EnumBossBarAction.class);
		switch (action) {
			case ADD: {
				this.message = PacketDataSerializerHelper.readChat(serializer);
				this.health = serializer.readFloat();
				this.color = (BossBarColor) PacketDataSerializerHelper.readEnum(serializer, BossBarColor.class);
				this.divider = (BossBarDivider) PacketDataSerializerHelper.readEnum(serializer, BossBarDivider.class);
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
				this.color = PacketDataSerializerHelper.readEnum(serializer, BossBarColor.class);
				this.divider = PacketDataSerializerHelper.readEnum(serializer, BossBarDivider.class);
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
	
	public void destroy() {
		this.action = null;
		this.color = null;
		this.divider = null;
		this.message = null;
		this.uniqueId = null;
	}

	public enum EnumBossBarAction {
		ADD, REMOVE, UPDATE_HEALTH, UPDATE_TITLE, UPDATE_STYLE, UPDATE_FLAGS;
	}

}
