package com.lastabyss.carbon.generators.end;

import java.util.Random;

import com.lastabyss.carbon.nmsutils.NewChunkSnapshot;
import com.lastabyss.carbon.types.EnumRotation;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChunkProviderTheEnd;
import net.minecraft.server.v1_8_R3.StructureGenerator;
import net.minecraft.server.v1_8_R3.StructureStart;
import net.minecraft.server.v1_8_R3.World;

public class WorldGenEndCity extends StructureGenerator {

	private int d = 20;
	private int f = 11;
	private final NewChunkProviderTheEnd endChunkProvider;

	public WorldGenEndCity(NewChunkProviderTheEnd var1) {
		this.endChunkProvider = var1;
	}

	@Override
	public String a() {
		return "EndCity";
	}

	@Override
	protected boolean a(int var1, int var2) {
		int var3 = var1;
		int var4 = var2;
		if (var1 < 0) {
			var1 -= this.d - 1;
		}

		if (var2 < 0) {
			var2 -= this.d - 1;
		}

		int var5 = var1 / this.d;
		int var6 = var2 / this.d;
		Random var7 = this.c.a(var5, var6, 10387313);
		var5 *= this.d;
		var6 *= this.d;
		var5 += (var7.nextInt(this.d - this.f) + var7.nextInt(this.d - this.f)) / 2;
		var6 += (var7.nextInt(this.d - this.f) + var7.nextInt(this.d - this.f)) / 2;
		return (var3 == var5) && (var4 == var6) && this.endChunkProvider.b(var3, var4);
	}

	@Override
	protected StructureStart b(int var1, int var2) {
		return new WorldGenEndCityStart(this.c, this.endChunkProvider, this.b, var1, var2);
	}

	public static class WorldGenEndCityStart extends StructureStart {
		private boolean generateEndCity;

		public WorldGenEndCityStart() {
		}

		public WorldGenEndCityStart(World var1, ChunkProviderTheEnd var2, Random var3, int var4, int var5) {
			super(var4, var5);
			this.a(var1, var2, var3, var4, var5);
		}

		private void a(World world, ChunkProviderTheEnd endProvider, Random rnd, int x, int z) {
			EnumRotation rotation = EnumRotation.values()[rnd.nextInt(EnumRotation.values().length)];
			NewChunkSnapshot snapshot = new NewChunkSnapshot();
			endProvider.a(x, z, snapshot);
			byte var8 = 5;
			byte var9 = 5;
			if (rotation == EnumRotation.CLOCKWISE_90) {
				var8 = -5;
			} else if (rotation == EnumRotation.CLOCKWISE_180) {
				var8 = -5;
				var9 = -5;
			} else if (rotation == EnumRotation.COUNTERCLOCKWISE_90) {
				var9 = -5;
			}

			int y77 = snapshot.getHighestYAt(7, 7);
			int y7z = snapshot.getHighestYAt(7, 7 + var9);
			int yx7 = snapshot.getHighestYAt(7 + var8, 7);
			int yxz = snapshot.getHighestYAt(7 + var8, 7 + var9);
			int minHeight = Math.min(Math.min(y77, y7z), Math.min(yx7, yxz));
            this.c();
			if (minHeight < 60) {
				this.generateEndCity = false;
			} else {
	            BlockPosition startPoint = new BlockPosition((x * 16) + 8, minHeight, (z * 16) + 8);
	            WorldGenEndCityPieces.a(startPoint, rotation, this.a, rnd);
				this.generateEndCity = true;
			}
		}

		@Override
		public boolean d() {
			return this.generateEndCity;
		}

	}

}
