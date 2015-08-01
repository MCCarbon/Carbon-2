package com.lastabyss.carbon.blocks.util;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

public class WrappedBlock extends Block {

	public WrappedBlock(Material material) {
		super(material);
	}

	public WrappedBlock(Material material, MaterialMapColor color) {
		super(material, color);
	}

	public void setBlockData(IBlockData blockdata) {
		j(blockdata);
	}

	public void setSizes(float f, float f1, float f2, float f3, float f4, float f5) {
		a(f, f1, f2, f3, f4, f5);
	}

	public WrappedBlock setStrength(float strength) {
		c(strength);
		return this;
	}

	public WrappedBlock setLightLevel(float level) {
		a(level);
		return this;
	}

	public WrappedBlock setStepSound(StepSound sound) {
		a(sound);
		return this;
	}

	public WrappedBlock setName(String name) {
		c(name);
		return this;
	}

	public WrappedBlock disableBreakStat() {
		K();
		return this;
	}

	@Override
	public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
		return getBoundingBox(world, blockposition, iblockdata);
	}

	public AxisAlignedBB getBoundingBox(World world, BlockPosition blockposition, IBlockData iblockdata) {
		return super.a(world, blockposition, iblockdata);
	}

	@Override
	public MovingObjectPosition a(World world, BlockPosition position, Vec3D vec1, Vec3D vec2) {
		return rayTraceCollision(world, position, vec1, vec2);
	}

	public MovingObjectPosition rayTraceCollision(World world, BlockPosition position, Vec3D vec1, Vec3D vec2) {
		return super.a(world, position, vec1, vec2);
	}

	@Override
	public int k() {
		return getPushReaction();
	}

	public int getPushReaction() {
		return super.k();
	}

	@Override
	public boolean c() {
		return isOpaqueCube();
	}

	public boolean isOpaqueCube() {
		return super.c();
	}

	@Override
	public boolean A() {
		return isFullCube();
	}

	public boolean isFullCube() {
		return super.A();
	}

}
