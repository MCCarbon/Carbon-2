package com.lastabyss.carbon.generators.end;

import java.util.List;
import java.util.Random;

import com.lastabyss.carbon.blocks.BlockChorusFlower;
import com.lastabyss.carbon.blocks.util.BlockPositionUtil;
import com.lastabyss.carbon.generators.EndGatewayPlatformGenerator;
import com.lastabyss.carbon.generators.end.unknown.class_ate;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_8_R3.BlockFalling;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.ChunkProviderTheEnd;
import net.minecraft.server.v1_8_R3.ChunkSnapshot;
import net.minecraft.server.v1_8_R3.EnumCreatureType;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChunkProvider;
import net.minecraft.server.v1_8_R3.IProgressUpdate;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NoiseGeneratorOctaves;
import net.minecraft.server.v1_8_R3.World;


public class NewChunkProviderTheEnd extends ChunkProviderTheEnd {

    private Random random;
    private NoiseGeneratorOctaves gen1;
    private NoiseGeneratorOctaves gen2;
    private NoiseGeneratorOctaves gen3;
    public NoiseGeneratorOctaves gen4;
    public NoiseGeneratorOctaves gen5;
    private World world;
    private class_ate whatIsThis;
    private double[] m;
    private BiomeBase[] biomes;
    double[] c;
    double[] d;
    double[] e;
    private WorldGenEndCity endCityGenerator = new WorldGenEndCity(this);
    private EndGatewayPlatformGenerator gatewayPlatformGenerator = new EndGatewayPlatformGenerator();

    public NewChunkProviderTheEnd(World world, long seed) {
        super(world, seed);
        this.world = world;
        this.random = new Random(seed);
        this.gen1 = new NoiseGeneratorOctaves(this.random, 16);
        this.gen2 = new NoiseGeneratorOctaves(this.random, 16);
        this.gen3 = new NoiseGeneratorOctaves(this.random, 8);
        this.gen4 = new NoiseGeneratorOctaves(this.random, 10);
        this.gen5 = new NoiseGeneratorOctaves(this.random, 16);
        this.whatIsThis = new class_ate(this.random);
    }

    public void a(int var1, int var2, ChunkSnapshot var3) {
        byte var4 = 2;
        int var5 = var4 + 1;
        byte var6 = 33;
        int var7 = var4 + 1;
        this.m = this.a(this.m, var1 * var4, 0, var2 * var4, var5, var6, var7);

        for (int var8 = 0; var8 < var4; ++var8) {
            for (int var9 = 0; var9 < var4; ++var9) {
                for (int var10 = 0; var10 < 32; ++var10) {
                    double var11 = 0.25D;
                    double var13 = this.m[((((var8 + 0) * var7) + var9 + 0) * var6) + var10 + 0];
                    double var15 = this.m[((((var8 + 0) * var7) + var9 + 1) * var6) + var10 + 0];
                    double var17 = this.m[((((var8 + 1) * var7) + var9 + 0) * var6) + var10 + 0];
                    double var19 = this.m[((((var8 + 1) * var7) + var9 + 1) * var6) + var10 + 0];
                    double var21 = (this.m[((((var8 + 0) * var7) + var9 + 0) * var6) + var10 + 1] - var13) * var11;
                    double var23 = (this.m[((((var8 + 0) * var7) + var9 + 1) * var6) + var10 + 1] - var15) * var11;
                    double var25 = (this.m[((((var8 + 1) * var7) + var9 + 0) * var6) + var10 + 1] - var17) * var11;
                    double var27 = (this.m[((((var8 + 1) * var7) + var9 + 1) * var6) + var10 + 1] - var19) * var11;

                    for (int var29 = 0; var29 < 4; ++var29) {
                        double var30 = 0.125D;
                        double var32 = var13;
                        double var34 = var15;
                        double var36 = (var17 - var13) * var30;
                        double var38 = (var19 - var15) * var30;

                        for (int var40 = 0; var40 < 8; ++var40) {
                            double var41 = 0.125D;
                            double var43 = var32;
                            double var45 = (var34 - var32) * var41;

                            for (int var47 = 0; var47 < 8; ++var47) {
                                IBlockData var48 = null;
                                if (var43 > 0.0D) {
                                    var48 = Blocks.END_STONE.getBlockData();
                                }

                                int var49 = var40 + (var8 * 8);
                                int var50 = var29 + (var10 * 4);
                                int var51 = var47 + (var9 * 8);
                                var3.a(var49, var50, var51, var48);
                                var43 += var45;
                            }

                            var32 += var36;
                            var34 += var38;
                        }

                        var13 += var21;
                        var15 += var23;
                        var17 += var25;
                        var19 += var27;
                    }
                }
            }
        }

    }

    public void a(ChunkSnapshot snapshot) {
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                byte var4 = 1;
                int i = -1;
                IBlockData var6 = Blocks.END_STONE.getBlockData();
                IBlockData var7 = Blocks.END_STONE.getBlockData();

                for (int y = 127; y >= 0; --y) {
                    IBlockData blockdata = snapshot.a(x, y, z);
                    if (blockdata.getBlock().getMaterial() == Material.AIR) {
                        i = -1;
                    } else if (blockdata.getBlock() == Blocks.STONE) {
                        if (i == -1) {
                            if (var4 <= 0) {
                                var6 = Blocks.AIR.getBlockData();
                                var7 = Blocks.END_STONE.getBlockData();
                            }

                            i = var4;
                            if (y >= 0) {
                                snapshot.a(x, y, z, var6);
                            } else {
                                snapshot.a(x, y, z, var7);
                            }
                        } else if (i > 0) {
                            --i;
                            snapshot.a(x, y, z, var7);
                        }
                    }
                }
            }
        }

    }

    @Override
    public Chunk getOrCreateChunk(int chunkX, int chunkZ) {
        this.random.setSeed((chunkX * 341873128712L) + (chunkZ * 132897987541L));
        ChunkSnapshot snapshot = new ChunkSnapshot();
        this.biomes = this.world.getWorldChunkManager().getBiomeBlock(this.biomes, chunkX * 16, chunkZ * 16, 16, 16);
        this.a(chunkX, chunkZ, snapshot);
        this.a(snapshot);
        this.endCityGenerator.a(this, this.world, chunkX, chunkZ, snapshot);
        Chunk chunk = new Chunk(this.world, snapshot, chunkX, chunkZ);
        byte[] biomeIndex = chunk.getBiomeIndex();
        for (int i = 0; i < biomeIndex.length; ++i) {
            biomeIndex[i] = (byte) this.biomes[i].id;
        }
        chunk.initLighting();
        return chunk;
    }

    private float a(int var1, int var2, int var3, int var4) {
        float var5 = (var1 * 2) + var3;
        float var6 = (var2 * 2) + var4;
        float var7 = 100.0F - (MathHelper.sqrt((var5 * var5) + (var6 * var6)) * 8.0F);
        if (var7 > 80.0F) {
            var7 = 80.0F;
        }

        if (var7 < -100.0F) {
            var7 = -100.0F;
        }

        for (int var8 = -12; var8 <= 12; ++var8) {
            for (int var9 = -12; var9 <= 12; ++var9) {
                int var10 = var1 + var8;
                int var11 = var2 + var9;
                if ((((var10 * var10) + (var11 * var11)) > 4096) && (this.whatIsThis.a(var10, var11) < -0.8999999761581421D)) {
                    float var12 = (((MathHelper.a(var10) * 3439) + (MathHelper.a(var11) * 147)) % 13) + 9;
                    var5 = var3 - (var8 * 2);
                    var6 = var4 - (var9 * 2);
                    float var13 = 100.0F - (MathHelper.sqrt((var5 * var5) + (var6 * var6)) * var12);
                    if (var13 > 80.0F) {
                        var13 = 80.0F;
                    }

                    if (var13 < -100.0F) {
                        var13 = -100.0F;
                    }

                    if (var13 > var7) {
                        var7 = var13;
                    }
                }
            }
        }

        return var7;
    }

    public boolean b(int var1, int var2) {
        return (((var1 * var1) + (var2 * var2)) > 4096) && (this.a(var1, var2, 1, 1) >= 0.0F);
    }

    private double[] a(double[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        if (var1 == null) {
            var1 = new double[var5 * var6 * var7];
        }

        double var8 = 684.412D;
        double var10 = 684.412D;
        var8 *= 2.0D;
        this.c = this.gen3.a(this.c, var2, var3, var4, var5, var6, var7, var8 / 80.0D, var10 / 160.0D, var8 / 80.0D);
        this.d = this.gen1.a(this.d, var2, var3, var4, var5, var6, var7, var8, var10, var8);
        this.e = this.gen2.a(this.e, var2, var3, var4, var5, var6, var7, var8, var10, var8);
        int var12 = var2 / 2;
        int var13 = var4 / 2;
        int var14 = 0;

        for (int var15 = 0; var15 < var5; ++var15) {
            for (int var16 = 0; var16 < var7; ++var16) {
                float var17 = this.a(var12, var13, var15, var16);

                for (int var18 = 0; var18 < var6; ++var18) {
                    double var19 = 0.0D;
                    double var21 = this.d[var14] / 512.0D;
                    double var23 = this.e[var14] / 512.0D;
                    double var25 = ((this.c[var14] / 10.0D) + 1.0D) / 2.0D;
                    if (var25 < 0.0D) {
                        var19 = var21;
                    } else if (var25 > 1.0D) {
                        var19 = var23;
                    } else {
                        var19 = var21 + ((var23 - var21) * var25);
                    }

                    var19 -= 8.0D;
                    var19 += var17;
                    byte var27 = 2;
                    double var28;
                    if (var18 > ((var6 / 2) - var27)) {
                        var28 = (var18 - ((var6 / 2) - var27)) / 64.0F;
                        var28 = MathHelper.a(var28, 0.0D, 1.0D);
                        var19 = (var19 * (1.0D - var28)) + (-3000.0D * var28);
                    }

                    var27 = 8;
                    if (var18 < var27) {
                        var28 = (var27 - var18) / (var27 - 1.0F);
                        var19 = (var19 * (1.0D - var28)) + (-30.0D * var28);
                    }

                    var1[var14] = var19;
                    ++var14;
                }
            }
        }

        return var1;
    }

    @Override
    public boolean isChunkLoaded(int var1, int var2) {
        return true;
    }

    @Override
    public void getChunkAt(IChunkProvider provider, int chunkX, int chunkZ) {
        BlockFalling.instaFall = true;
        BlockPosition position = new BlockPosition(chunkX * 16, 0, chunkZ * 16);
        this.endCityGenerator.a(this.world, this.random, new ChunkCoordIntPair(chunkX, chunkZ));
        this.world.getBiome(BlockPositionUtil.add(position, 16, 0, 16)).a(this.world, this.world.random, position);
        if (((chunkX * chunkX) + (chunkZ * chunkZ)) > 4096) {
            if ((this.a(chunkX, chunkZ, 1, 1) < -20.0F) && (this.random.nextInt(14) == 0)) {
                this.gatewayPlatformGenerator.generate(this.world, this.random, BlockPositionUtil.add(position, this.random.nextInt(16) + 8, 55 + this.random.nextInt(16), this.random.nextInt(16) + 8));
                if (this.random.nextInt(4) == 0) {
                    this.gatewayPlatformGenerator.generate(this.world, this.random, BlockPositionUtil.add(position, this.random.nextInt(16) + 8, 55 + this.random.nextInt(16), this.random.nextInt(16) + 8));
                }
            }

            int rndi = this.random.nextInt(5);
            for (int i = 0; i < rndi; ++i) {
                int rndX = this.random.nextInt(16) + 8;
                int rndZ = this.random.nextInt(16) + 8;
                int rndY = this.world.getHighestBlockYAt(BlockPositionUtil.add(position, rndX, 0, rndZ)).getY();
                if (rndY > 0) {
                    int rRndY = rndY - 1;
                    if (this.world.isEmpty(BlockPositionUtil.add(position, rndX, rRndY + 1, rndZ)) && (this.world.getType(BlockPositionUtil.add(position, rndX, rRndY, rndZ)).getBlock() == Blocks.END_STONE)) {
                        BlockChorusFlower.a(this.world, BlockPositionUtil.add(position, rndX, rRndY + 1, rndZ), this.random, 8);
                    }
                }
            }
        }

        BlockFalling.instaFall = false;
    }

    @Override
    public boolean a(IChunkProvider provider, Chunk chunk, int chunkX, int chunkZ) {
        return false;
    }

    @Override
    public boolean saveChunks(boolean f, IProgressUpdate update) {
        return true;
    }

    @Override
    public void c() {
    }

    @Override
    public boolean unloadChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String getName() {
        return "RandomLevelSource";
    }

    @Override
    public List<BiomeMeta> getMobsFor(EnumCreatureType var1, BlockPosition var2) {
        return this.world.getBiome(var2).getMobs(var1);
    }

    @Override
    public BlockPosition findNearestMapFeature(World world, String string, BlockPosition position) {
        return null;
    }

    @Override
    public int getLoadedChunks() {
        return 0;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {
    }

    @Override
    public Chunk getChunkAt(BlockPosition position) {
        return this.getOrCreateChunk(position.getX() >> 4, position.getZ() >> 4);
    }

}
