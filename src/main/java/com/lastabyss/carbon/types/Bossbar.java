package com.lastabyss.carbon.types;

import java.util.UUID;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;

public class Bossbar {

    private UUID uniqueId;
    private IChatBaseComponent message;
    private float health;
    private Bossbar.EnumBossbarColor color;
    private Bossbar.EnumBossbarDivider divider;
    private boolean darkenSky;
    private boolean isDragon;

    public Bossbar(UUID uuid, IChatBaseComponent message, EnumBossbarColor color, EnumBossbarDivider divider, boolean darkenSky, boolean isDragon) {
        this.uniqueId = uuid;
        this.message = message;
        this.color = color;
        this.divider = divider;
        this.darkenSky = darkenSky;
        this.isDragon = isDragon;
        this.health = 1.0F;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public IChatBaseComponent getMessage() {
        return this.message;
    }

    public float getHealth() {
        return this.health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public EnumBossbarColor getColor() {
        return this.color;
    }
    
    public void setColor(EnumBossbarColor color) {
    	this.color = color;
    }

    public EnumBossbarDivider getDivider() {
        return this.divider;
    }
    
    public void setDivider(EnumBossbarDivider divider) {
    	this.divider = divider;
    }

    public boolean shouldDarkenSky() {
        return this.darkenSky;
    }

    public boolean isDragon() {
        return this.isDragon;
    }
    
    public void destroy() {
    	this.color = null;
    	this.divider = null;
    	this.message = null;
    	this.uniqueId = null;
    }

    public static enum EnumBossbarDivider {
        PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20;
    }

    public static enum EnumBossbarColor {
        PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE;
    }

}