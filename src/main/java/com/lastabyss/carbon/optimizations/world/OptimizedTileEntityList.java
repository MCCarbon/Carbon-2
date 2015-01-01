package com.lastabyss.carbon.optimizations.world;

import java.util.ArrayList;

import net.minecraft.server.v1_8_R1.TileEntity;
import net.minecraft.server.v1_8_R1.TileEntityChest;
import net.minecraft.server.v1_8_R1.TileEntityCommand;
import net.minecraft.server.v1_8_R1.TileEntityComparator;
import net.minecraft.server.v1_8_R1.TileEntityDispenser;
import net.minecraft.server.v1_8_R1.TileEntityEnchantTable;
import net.minecraft.server.v1_8_R1.TileEntityEnderChest;
import net.minecraft.server.v1_8_R1.TileEntityEnderPortal;
import net.minecraft.server.v1_8_R1.TileEntityFlowerPot;
import net.minecraft.server.v1_8_R1.TileEntityNote;
import net.minecraft.server.v1_8_R1.TileEntityRecordPlayer;
import net.minecraft.server.v1_8_R1.TileEntitySign;
import net.minecraft.server.v1_8_R1.TileEntitySkull;

public class OptimizedTileEntityList extends ArrayList<TileEntity> {

	private static final long serialVersionUID = 3112991409504261787L;

	@Override
	public boolean add(TileEntity tileEntity) {
		if (
			tileEntity instanceof TileEntityChest ||
			tileEntity instanceof TileEntityEnderChest ||
			tileEntity instanceof TileEntityRecordPlayer ||
			tileEntity instanceof TileEntityDispenser ||
			tileEntity instanceof TileEntitySign ||
			tileEntity instanceof TileEntityNote ||
			tileEntity instanceof TileEntityEnderPortal ||
			tileEntity instanceof TileEntityCommand ||
			tileEntity instanceof TileEntitySkull ||
			tileEntity instanceof TileEntityComparator ||
			tileEntity instanceof TileEntityFlowerPot ||
			tileEntity instanceof TileEntityEnchantTable
		) {
			return false;
		} else {
			return super.add(tileEntity);
		}
	}

}
