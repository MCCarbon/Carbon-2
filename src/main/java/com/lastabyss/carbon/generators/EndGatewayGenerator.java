package com.lastabyss.carbon.generators;

import java.util.Random;

import com.lastabyss.carbon.blocks.util.BlockPositionUtil;
import com.lastabyss.carbon.staticaccess.BlockList;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockPosition.MutableBlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.World;

public class EndGatewayGenerator extends WrappedWorldGenerator {

    @Override
	public boolean generate(World world, Random rnd, BlockPosition position) {
		for (MutableBlockPosition mpos : BlockPositionUtil.allInCubeM(BlockPositionUtil.add(position, -1, -2, -1), BlockPositionUtil.add(position, 1, 2, 1))) {
			boolean sameX = mpos.getX() == position.getX();
			boolean sameY = mpos.getY() == position.getY();
			boolean sameZ = mpos.getZ() == position.getZ();
			boolean yDist2 = Math.abs(mpos.getY() - position.getY()) == 2;
			if (sameX && sameY && sameZ) {
				this.setTypeAndData(world, new BlockPosition(mpos), BlockList.END_GATEWAY.getBlockData());
			} else if (sameY) {
				this.setTypeAndData(world, mpos, Blocks.AIR.getBlockData());
			} else if (yDist2 && sameX && sameZ) {
				this.setTypeAndData(world, mpos, Blocks.BEDROCK.getBlockData());
			} else if ((sameX || sameZ) && !yDist2) {
				this.setTypeAndData(world, mpos, Blocks.BEDROCK.getBlockData());
			} else {
				this.setTypeAndData(world, mpos, Blocks.AIR.getBlockData());
			}
		}
		return true;
	}

}
