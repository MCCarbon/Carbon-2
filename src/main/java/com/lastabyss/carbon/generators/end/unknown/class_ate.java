package com.lastabyss.carbon.generators.end.unknown;

import java.util.Random;

public class class_ate {

	private static int[][] e = new int[][] { { 1, 1, 0 }, { -1, 1, 0 }, { 1, -1, 0 }, { -1, -1, 0 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
	public static final double a = Math.sqrt(3.0D);
	private int[] f;
	public double b;
	public double c;
	public double d;

	private static final double g = 0.5D * (a - 1.0D);
	private static final double h = (3.0D - a) / 6.0D;

	public class_ate() {
		this(new Random());
	}

	public class_ate(Random var1) {
		this.f = new int[512];
		this.b = var1.nextDouble() * 256.0D;
		this.c = var1.nextDouble() * 256.0D;
		this.d = var1.nextDouble() * 256.0D;

		int var2;
		for (var2 = 0; var2 < 256; this.f[var2] = var2++) {
			;
		}

		for (var2 = 0; var2 < 256; ++var2) {
			int var3 = var1.nextInt(256 - var2) + var2;
			int var4 = this.f[var2];
			this.f[var2] = this.f[var3];
			this.f[var3] = var4;
			this.f[var2 + 256] = this.f[var2];
		}

	}

	private static int a(double var0) {
		return var0 > 0.0D ? (int) var0 : (int) var0 - 1;
	}

	private static double a(int[] var0, double var1, double var3) {
		return (double) var0[0] * var1 + (double) var0[1] * var3;
	}

	public double a(double var1, double var3) {
		double var11 = 0.5D * (a - 1.0D);
		double var13 = (var1 + var3) * var11;
		int var15 = a(var1 + var13);
		int var16 = a(var3 + var13);
		double var17 = (3.0D - a) / 6.0D;
		double var19 = (double) (var15 + var16) * var17;
		double var21 = (double) var15 - var19;
		double var23 = (double) var16 - var19;
		double var25 = var1 - var21;
		double var27 = var3 - var23;
		byte var29;
		byte var30;
		if (var25 > var27) {
			var29 = 1;
			var30 = 0;
		} else {
			var29 = 0;
			var30 = 1;
		}

		double var31 = var25 - (double) var29 + var17;
		double var33 = var27 - (double) var30 + var17;
		double var35 = var25 - 1.0D + 2.0D * var17;
		double var37 = var27 - 1.0D + 2.0D * var17;
		int var39 = var15 & 255;
		int var40 = var16 & 255;
		int var41 = this.f[var39 + this.f[var40]] % 12;
		int var42 = this.f[var39 + var29 + this.f[var40 + var30]] % 12;
		int var43 = this.f[var39 + 1 + this.f[var40 + 1]] % 12;
		double var44 = 0.5D - var25 * var25 - var27 * var27;
		double var5;
		if (var44 < 0.0D) {
			var5 = 0.0D;
		} else {
			var44 *= var44;
			var5 = var44 * var44 * a(e[var41], var25, var27);
		}

		double var46 = 0.5D - var31 * var31 - var33 * var33;
		double var7;
		if (var46 < 0.0D) {
			var7 = 0.0D;
		} else {
			var46 *= var46;
			var7 = var46 * var46 * a(e[var42], var31, var33);
		}

		double var48 = 0.5D - var35 * var35 - var37 * var37;
		double var9;
		if (var48 < 0.0D) {
			var9 = 0.0D;
		} else {
			var48 *= var48;
			var9 = var48 * var48 * a(e[var43], var35, var37);
		}

		return 70.0D * (var5 + var7 + var9);
	}

	public void a(double[] var1, double var2, double var4, int var6, int var7, double var8, double var10, double var12) {
		int var14 = 0;

		for (int var15 = 0; var15 < var7; ++var15) {
			double var16 = (var4 + (double) var15) * var10 + this.c;

			for (int var18 = 0; var18 < var6; ++var18) {
				double var19 = (var2 + (double) var18) * var8 + this.b;
				double var27 = (var19 + var16) * g;
				int var29 = a(var19 + var27);
				int var30 = a(var16 + var27);
				double var31 = (double) (var29 + var30) * h;
				double var33 = (double) var29 - var31;
				double var35 = (double) var30 - var31;
				double var37 = var19 - var33;
				double var39 = var16 - var35;
				byte var41;
				byte var42;
				if (var37 > var39) {
					var41 = 1;
					var42 = 0;
				} else {
					var41 = 0;
					var42 = 1;
				}

				double var43 = var37 - (double) var41 + h;
				double var45 = var39 - (double) var42 + h;
				double var47 = var37 - 1.0D + 2.0D * h;
				double var49 = var39 - 1.0D + 2.0D * h;
				int var51 = var29 & 255;
				int var52 = var30 & 255;
				int var53 = this.f[var51 + this.f[var52]] % 12;
				int var54 = this.f[var51 + var41 + this.f[var52 + var42]] % 12;
				int var55 = this.f[var51 + 1 + this.f[var52 + 1]] % 12;
				double var56 = 0.5D - var37 * var37 - var39 * var39;
				double var21;
				if (var56 < 0.0D) {
					var21 = 0.0D;
				} else {
					var56 *= var56;
					var21 = var56 * var56 * a(e[var53], var37, var39);
				}

				double var58 = 0.5D - var43 * var43 - var45 * var45;
				double var23;
				if (var58 < 0.0D) {
					var23 = 0.0D;
				} else {
					var58 *= var58;
					var23 = var58 * var58 * a(e[var54], var43, var45);
				}

				double var60 = 0.5D - var47 * var47 - var49 * var49;
				double var25;
				if (var60 < 0.0D) {
					var25 = 0.0D;
				} else {
					var60 *= var60;
					var25 = var60 * var60 * a(e[var55], var47, var49);
				}

				int var10001 = var14++;
				var1[var10001] += 70.0D * (var21 + var23 + var25) * var12;
			}
		}

	}

}
