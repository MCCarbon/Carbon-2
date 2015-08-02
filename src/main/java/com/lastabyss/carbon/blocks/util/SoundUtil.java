package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.Block;

/**
 *
 * @author Navid
 */
public class SoundUtil {

    public static final Block.StepSound STONE = new Block.StepSound("stone", 1.0F, 1.0F);
    public static final Block.StepSound WOOD = new Block.StepSound("wood", 1.0F, 1.0F);
    public static final Block.StepSound GRAVEL = new Block.StepSound("gravel", 1.0F, 1.0F);
    public static final Block.StepSound GRASS = new Block.StepSound("grass", 1.0F, 1.0F);
    public static final Block.StepSound STONE2 = new Block.StepSound("stone", 1.0F, 1.0F);
    public static final Block.StepSound STONE3 = new Block.StepSound("stone", 1.0F, 1.5F);
    public static final Block.StepSound GLASS = new Block.StepSound("stone", 1.0F, 1.0F) {
        public String getBreakSound() {
            return "dig.glass";
        }

        public String getPlaceSound() {
            return "step.stone";
        }
    };
    public static final Block.StepSound CLOTH = new Block.StepSound("cloth", 1.0F, 1.0F);
    public static final Block.StepSound SAND = new Block.StepSound("sand", 1.0F, 1.0F);
    public static final Block.StepSound SNOW = new Block.StepSound("snow", 1.0F, 1.0F);
    public static final Block.StepSound LADDER = new Block.StepSound("ladder", 1.0F, 1.0F) {
        public String getBreakSound() {
            return "dig.wood";
        }
    };
    public static final Block.StepSound ANVIL = new Block.StepSound("anvil", 0.3F, 1.0F) {
        public String getBreakSound() {
            return "dig.stone";
        }

        public String getPlaceSound() {
            return "random.anvil_land";
        }
    };
    public static final Block.StepSound SLIME = new Block.StepSound("slime", 1.0F, 1.0F) {
        public String getBreakSound() {
            return "mob.slime.big";
        }

        public String getPlaceSound() {
            return "mob.slime.big";
        }

        public String getStepSound() {
            return "mob.slime.small";
        }
    };

}
