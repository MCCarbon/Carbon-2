package com.lastabyss.carbon.generators;

import java.util.Random;

import com.lastabyss.carbon.blocks.util.BlockPositionUtil;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;

public class EndGatewayPlatformGenerator extends WrappedWorldGenerator {

	public boolean generate(World world, Random random, BlockPosition position) {
		float f = random.nextInt(3) + 4;

		for (int y = 0; f > 0.5F; --y) {
			for (int x = MathHelper.floor(-f); x <= MathHelper.f(f); ++x) {
				for (int z = MathHelper.floor(-f); z <= MathHelper.f(f); ++z) {
					if ((float) (x * x + z * z) <= (f + 1.0F) * (f + 1.0F)) {
						this.setTypeAndData(world, BlockPositionUtil.add(position, x, y, z), Blocks.END_STONE.getBlockData());
					}
				}
			}

			f = (float) ((double) f - ((double) random.nextInt(2) + 0.5D));
		}

		return true;
	}

}
