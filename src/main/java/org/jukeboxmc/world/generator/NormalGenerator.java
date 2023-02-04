package org.jukeboxmc.world.generator;

import com.google.common.collect.Maps;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStone;
import org.jukeboxmc.block.data.StoneType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;
import org.jukeboxmc.world.generator.biome.BiomeGrid;
import org.jukeboxmc.world.generator.biome.BiomeHeight;
import org.jukeboxmc.world.generator.biome.GroundGenerator;
import org.jukeboxmc.world.generator.biome.generation.*;
import org.jukeboxmc.world.generator.biomegrid.MapLayer;
import org.jukeboxmc.world.generator.noise.PerlinOctaveGenerator;
import org.jukeboxmc.world.generator.noise.SimplexOctaveGenerator;
import org.jukeboxmc.world.generator.noise.bukkit.OctaveGenerator;
import org.jukeboxmc.world.generator.object.OreType;
import org.jukeboxmc.world.generator.populator.OrePopulator;
import org.jukeboxmc.world.generator.populator.Populator;
import org.jukeboxmc.world.generator.populator.biome.BiomePopulator;
import org.jukeboxmc.world.generator.populator.biome.BiomePopulatorRegistry;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class NormalGenerator extends Generator {

    private final MapLayer[] biomeGrid;

    private final Random random;

    private final Block blockStone;
    private final Block blockWater;
    private final Block blockBedrock;

    private final int seed = 34465783;
    private final long localSeed1;
    private final long localSeed2;

    public static final int WATER_HEIGHT = 64;

    private final Set<Populator> populators = new HashSet<>();
    private static final Map<Biome, BiomeHeight> HEIGHT_MAP = new HashMap<>();
    private static final Map<Biome, GroundGenerator> GROUND_MAP = new HashMap<>();
    private static final double[][] ELEVATION_WEIGHT = new double[5][5];
    private final double[][][] density = new double[5][5][33];
    private final Map<String, Map<String, OctaveGenerator>> octaveCache = Maps.newHashMap();

    private final GroundGenerator groundGenerator = new GroundGenerator();

    static {
        setBiomeGround( new GroundGeneratorSandy(), Biome.BEACH, Biome.COLD_BEACH, Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_MUTATED );
        setBiomeGround( new GroundGeneratorRocky(), Biome.STONE_BEACH );
        setBiomeGround( new GroundGeneratorSnowy(), Biome.ICE_PLAINS_SPIKES );
        setBiomeGround( new GroundGeneratorMycel(), Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_ISLAND_SHORE );
        setBiomeGround( new GroundGeneratorPatchStone(), Biome.EXTREME_HILLS );
        setBiomeGround( new GroundGeneratorPatchGravel(), Biome.EXTREME_HILLS_MUTATED, Biome.EXTREME_HILLS_PLUS_TREES_MUTATED );
        setBiomeGround( new GroundGeneratorPatchDirtAndStone(), Biome.SAVANNA_MUTATED, Biome.SAVANNA_PLATEAU_MUTATED );
        setBiomeGround( new GroundGeneratorPatchDirt(), Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS, Biome.REDWOOD_TAIGA_MUTATED );
        //setBiomeGround( new GroundGeneratorMesa(), Biome.MESA, Biome.MESA_PLATEAU, Biome.MESA_PLATEAU_STONE );
        //setBiomeGround( new GroundGeneratorMesa( GroundGeneratorMesa.MesaType.BRYCE ), Biome.MESA_BRYCE );
        // setBiomeGround( new GroundGeneratorMesa( GroundGeneratorMesa.MesaType.FOREST ), Biome.MESA_PLATEAU_STONE, Biome.MESA_PLATEAU_STONE_MUTATED );

        setBiomeHeight( BiomeHeight.OCEAN, Biome.OCEAN, Biome.FROZEN_OCEAN );
        setBiomeHeight( BiomeHeight.DEEP_OCEAN, Biome.DEEP_OCEAN );
        setBiomeHeight( BiomeHeight.RIVER, Biome.RIVER, Biome.FROZEN_RIVER );
        setBiomeHeight( BiomeHeight.FLAT_SHORE, Biome.BEACH, Biome.COLD_BEACH, Biome.MUSHROOM_ISLAND_SHORE );
        setBiomeHeight( BiomeHeight.ROCKY_SHORE, Biome.STONE_BEACH );
        setBiomeHeight( BiomeHeight.FLATLANDS, Biome.DESERT, Biome.ICE_PLAINS, Biome.SAVANNA, Biome.PLAINS );
        setBiomeHeight( BiomeHeight.EXTREME_HILLS, Biome.EXTREME_HILLS, Biome.EXTREME_HILLS_PLUS_TREES, Biome.EXTREME_HILLS_MUTATED, Biome.EXTREME_HILLS_PLUS_TREES_MUTATED );
        setBiomeHeight( BiomeHeight.MID_PLAINS, Biome.TAIGA, Biome.COLD_TAIGA, Biome.MEGA_TAIGA );
        setBiomeHeight( BiomeHeight.SWAMPLAND, Biome.SWAMPLAND );
        setBiomeHeight( BiomeHeight.LOW_HILLS, Biome.MUSHROOM_ISLAND );
        setBiomeHeight( BiomeHeight.HILLS, Biome.DESERT_HILLS, Biome.FOREST_HILLS, Biome.TAIGA_HILLS, Biome.EXTREME_HILLS_EDGE, Biome.JUNGLE_HILLS, Biome.BIRCH_FOREST_HILLS, Biome.COLD_TAIGA_HILLS, Biome.MEGA_TAIGA_HILLS, Biome.MESA_PLATEAU_STONE_MUTATED, Biome.MESA_PLATEAU_MUTATED );//, Biome.ICE_MOUNTAINS
        setBiomeHeight( BiomeHeight.HIGH_PLATEAU, Biome.SAVANNA_PLATEAU, Biome.MESA_PLATEAU_STONE, Biome.MESA_PLATEAU );
        setBiomeHeight( BiomeHeight.FLATLANDS_HILLS, Biome.DESERT_MUTATED );
        setBiomeHeight( BiomeHeight.BIG_HILLS, Biome.ICE_PLAINS_SPIKES );
        setBiomeHeight( BiomeHeight.BIG_HILLS2, Biome.BIRCH_FOREST_HILLS_MUTATED );
        setBiomeHeight( BiomeHeight.SWAMPLAND_HILLS, Biome.SWAMPLAND_MUTATED );
        setBiomeHeight( BiomeHeight.DEFAULT_HILLS, Biome.JUNGLE_MUTATED, Biome.JUNGLE_EDGE_MUTATED, Biome.BIRCH_FOREST_MUTATED, Biome.ROOFED_FOREST_MUTATED );
        setBiomeHeight( BiomeHeight.MID_HILLS, Biome.TAIGA_MUTATED, Biome.COLD_TAIGA_MUTATED, Biome.REDWOOD_TAIGA_MUTATED );
        setBiomeHeight( BiomeHeight.MID_HILLS2, Biome.FLOWER_FOREST );
        setBiomeHeight( BiomeHeight.LOW_SPIKES, Biome.SAVANNA_MUTATED );
        setBiomeHeight( BiomeHeight.HIGH_SPIKES, Biome.SAVANNA_PLATEAU_MUTATED );

        for ( int x = 0; x < 5; x++ ) {
            for ( int z = 0; z < 5; z++ ) {
                int sqX = x - 2;
                sqX *= sqX;
                int sqZ = z - 2;
                sqZ *= sqZ;
                ELEVATION_WEIGHT[x][z] = 10d / Math.sqrt( sqX + sqZ + 0.2d );
            }
        }
    }

    public NormalGenerator() {
        this.random = new Random();
        this.random.setSeed( this.seed );

        this.biomeGrid = MapLayer.initialize( this.seed, Dimension.OVERWORLD, 1 );

        this.localSeed1 = ThreadLocalRandom.current().nextLong();
        this.localSeed2 = ThreadLocalRandom.current().nextLong();

        this.blockStone = Block.create( BlockType.STONE );
        this.blockWater = Block.create( BlockType.WATER );
        this.blockBedrock = Block.create( BlockType.BEDROCK );

        this.populators.add( new OrePopulator(
                new OreType[]{
                        new OreType( Block.create( BlockType.COAL_ORE ), 20, 17, 0, 128 ),
                        new OreType( Block.create( BlockType.IRON_ORE ), 20, 9, 0, 64 ),
                        new OreType( Block.create( BlockType.REDSTONE_ORE ), 8, 8, 0, 16 ),
                        new OreType( Block.create( BlockType.LAPIS_ORE ), 1, 7, 0, 16 ),
                        new OreType( Block.create( BlockType.GOLD_ORE ), 2, 9, 0, 32 ),
                        new OreType( Block.create( BlockType.DIAMOND_ORE ), 1, 8, 0, 16 ),
                        new OreType( Block.create( BlockType.DIRT ), 10, 33, 0, 128 ),
                        new OreType( Block.create( BlockType.GRAVEL ), 8, 33, 0, 128 ),
                        new OreType( Block.<BlockStone>create( BlockType.STONE ).setStoneType( StoneType.GRANITE ), 10, 33, 0, 80 ),
                        new OreType( Block.<BlockStone>create( BlockType.STONE ).setStoneType( StoneType.ANDESITE ), 10, 33, 0, 80 ),
                        new OreType( Block.<BlockStone>create( BlockType.STONE ).setStoneType( StoneType.DIORITE ), 10, 33, 0, 80 ),
                }
        ) );
    }

    @Override
    public void generate( Chunk chunk, int chunkX, int chunkZ ) {
        this.random.setSeed( chunkX * this.localSeed1 ^ chunkZ * this.localSeed2 ^ this.seed );
        int x = chunkX << 2;
        int z = chunkZ << 2;

        int[] biomeGrid = this.biomeGrid[1].generateValues( x - 2, z - 2, 10, 10 );

        Map<String, OctaveGenerator> octaves = this.getWorldOctaves();
        double[] heightNoise = ( (PerlinOctaveGenerator) octaves.get( "height" ) ).getFractalBrownianMotion( x, z, 0.5d, 2d );
        double[] roughnessNoise = ( (PerlinOctaveGenerator) octaves.get( "roughness" ) ).getFractalBrownianMotion( x, 0, z, 0.5d, 2d );
        double[] roughnessNoise2 = ( (PerlinOctaveGenerator) octaves.get( "roughness2" ) ).getFractalBrownianMotion( x, 0, z, 0.5d, 2d );
        double[] detailNoise = ( (PerlinOctaveGenerator) octaves.get( "detail" ) ).getFractalBrownianMotion( x, 0, z, 0.5d, 2d );

        int index = 0;
        int indexHeight = 0;

        for ( int xSeg = 0; xSeg < 5; xSeg++ ) {
            for ( int zSeg = 0; zSeg < 5; zSeg++ ) {
                double avgHeightScale = 0;
                double avgHeightBase = 0;
                double totalWeight = 0;
                Biome biome = Biome.findById( biomeGrid[xSeg + 2 + ( zSeg + 2 ) * 10] );
                BiomeHeight biomeHeight = HEIGHT_MAP.getOrDefault( biome, BiomeHeight.DEFAULT );

                for ( int xSmooth = 0; xSmooth < 5; xSmooth++ ) {
                    for ( int zSmooth = 0; zSmooth < 5; zSmooth++ ) {
                        Biome nearBiome = Biome.findById( biomeGrid[xSeg + xSmooth + ( zSeg + zSmooth ) * 10] );
                        BiomeHeight nearBiomeHeight = HEIGHT_MAP.getOrDefault( nearBiome, BiomeHeight.DEFAULT );
                        double heightBase = nearBiomeHeight.height();
                        double heightScale = nearBiomeHeight.scale();
                        double weight = ELEVATION_WEIGHT[xSmooth][zSmooth] / ( heightBase + 2d );
                        if ( nearBiomeHeight.height() > biomeHeight.height() ) {
                            weight *= 0.5d;
                        }
                        avgHeightScale += heightScale * weight;
                        avgHeightBase += heightBase * weight;
                        totalWeight += weight;
                    }
                }
                avgHeightScale /= totalWeight;
                avgHeightBase /= totalWeight;
                avgHeightScale = avgHeightScale * 0.9d + 0.1d;
                avgHeightBase = ( avgHeightBase * 4d - 1d ) / 8d;

                double noiseH = heightNoise[indexHeight++] / 8000d;
                if ( noiseH < 0 ) {
                    noiseH = Math.abs( noiseH ) * 0.3d;
                }
                noiseH = noiseH * 3d - 2d;
                if ( noiseH < 0 ) {
                    noiseH = Math.max( noiseH * 0.5d, -1 ) / 1.4d * 0.5d;
                } else {
                    noiseH = Math.min( noiseH, 1 ) / 8d;
                }

                noiseH = ( noiseH * 0.2d + avgHeightBase ) * 8.5d / 8d * 4d + 8.5d;
                for ( int k = 0; k < 33; k++ ) {
                    double nh = ( k - noiseH ) * 12d * 128d / 256d / avgHeightScale;
                    if ( nh < 0 ) {
                        nh *= 4d;
                    }
                    double noiseR = roughnessNoise[index] / 512d;
                    double noiseR2 = roughnessNoise2[index] / 512d;
                    double noiseD = ( detailNoise[index] / 10d + 1d ) / 2d;
                    double dens = noiseD < 0 ? noiseR
                            : noiseD > 1 ? noiseR2 : noiseR + ( noiseR2 - noiseR ) * noiseD;
                    dens -= nh;
                    index++;
                    if ( k > 29 ) {
                        double lowering = ( k - 29 ) / 3d;
                        dens = dens * ( 1d - lowering ) + -10d * lowering;
                    }
                    this.density[xSeg][zSeg][k] = dens;
                }
            }
        }

        double densityOffset = 0;
        for ( int i = 0; i < 5 - 1; i++ ) {
            for ( int j = 0; j < 5 - 1; j++ ) {
                for ( int k = 0; k < 33 - 1; k++ ) {
                    double d1 = this.density[i][j][k];
                    double d2 = this.density[i + 1][j][k];
                    double d3 = this.density[i][j + 1][k];
                    double d4 = this.density[i + 1][j + 1][k];
                    double d5 = ( this.density[i][j][k + 1] - d1 ) / 8;
                    double d6 = ( this.density[i + 1][j][k + 1] - d2 ) / 8;
                    double d7 = ( this.density[i][j + 1][k + 1] - d3 ) / 8;
                    double d8 = ( this.density[i + 1][j + 1][k + 1] - d4 ) / 8;

                    for ( int l = 0; l < 8; l++ ) {
                        double d9 = d1;
                        double d10 = d3;
                        for ( int m = 0; m < 4; m++ ) {
                            double dens = d9;
                            for ( int n = 0; n < 4; n++ ) {
                                if ( dens > densityOffset ) {
                                    chunk.setBlock( m + ( i << 2 ), l + ( k << 3 ), n + ( j << 2 ), 0, this.blockStone );
                                } else if ( l + ( k << 3 ) < WATER_HEIGHT - 1 ) {
                                    chunk.setBlock( m + ( i << 2 ), l + ( k << 3 ), n + ( j << 2 ), 0, this.blockWater );
                                }
                                dens += ( d10 - d9 ) / 4;
                            }
                            d9 += ( d2 - d1 ) / 4;
                            d10 += ( d4 - d3 ) / 4;
                        }
                        d1 += d5;
                        d3 += d7;
                        d2 += d6;
                        d4 += d8;
                    }
                }
            }
        }

        int cx = chunkX << 4;
        int cz = chunkZ << 4;

        BiomeGrid biomes = new BiomeGrid();
        int[] biomeValues = this.biomeGrid[0].generateValues( cx, cz, 16, 16 );
        for ( int i = 0; i < biomeValues.length; i++ ) {
            biomes.biomes[i] = (byte) biomeValues[i];
        }

        SimplexOctaveGenerator octaveGenerator = ( (SimplexOctaveGenerator) getWorldOctaves().get( "surface" ) );
        int sizeX = octaveGenerator.getSizeX();
        int sizeZ = octaveGenerator.getSizeZ();

        double[] surfaceNoise = octaveGenerator.getFractalBrownianMotion( cx, cz, 0.5d, 0.5d );
        for ( int sx = 0; sx < sizeX; sx++ ) {
            for ( int sz = 0; sz < sizeZ; sz++ ) {
                GROUND_MAP.getOrDefault( biomes.getBiome( sx, sz ), groundGenerator ).generateTerrainColumn( chunk, random, cx + sx, cz + sz, surfaceNoise[sx | sz << 4] );
                for ( int y = chunk.getMinY(); y < chunk.getMaxY(); y++ ) {
                    chunk.setBiome( sx, y, sz, biomes.getBiome( sx, sz ) );
                }
                chunk.setBlock( sx, 0, sz, 0, this.blockBedrock );
            }
        }
    }

    @Override
    public void populate( PopulationChunkManager manager, int chunkX, int chunkZ ) {
        try {
            this.random.setSeed( 0XDEADBEEF ^ ( (long) chunkX << 8 ) ^ chunkZ ^ this.seed );
            Chunk chunk = manager.getChunk( chunkX, chunkZ );

            for ( Populator populator : this.populators ) {
                populator.populate( this.random, chunk.getWorld(), manager, chunkX, chunkZ );
            }

            Biome biome = chunk.getBiome( 7, 7, 7 );
            BiomePopulator biomePopulator = BiomePopulatorRegistry.getBiomePopulator( biome );
            if ( biomePopulator == null ) return;
            for ( Populator populator : biomePopulator.getPopulators() ) {
                populator.populate( this.random, chunk.getWorld(), manager, chunkX, chunkZ );
            }
        } catch ( Throwable e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish( PopulationChunkManager manager, int chunkX, int chunkZ ) {

    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( -160034, 70, -74614 ); //Only for this map (testing)
    }

    private Map<String, OctaveGenerator> getWorldOctaves() {
        Map<String, OctaveGenerator> octaves = this.octaveCache.get( "normal" );
        if ( octaves == null ) {
            octaves = Maps.newHashMap();
            Random seed = new Random( this.seed );

            OctaveGenerator gen = new PerlinOctaveGenerator( seed, 16, 5, 5 );
            gen.setXScale( 200d );
            gen.setZScale( 200d );
            octaves.put( "height", gen );

            gen = new PerlinOctaveGenerator( seed, 16, 5, 33, 5 );
            gen.setXScale( 684.412d );
            gen.setYScale( 684.412d );
            gen.setZScale( 684.412d );
            octaves.put( "roughness", gen );

            gen = new PerlinOctaveGenerator( seed, 16, 5, 33, 5 );
            gen.setXScale( 684.412d );
            gen.setYScale( 684.412d );
            gen.setZScale( 684.412d );
            octaves.put( "roughness2", gen );

            gen = new PerlinOctaveGenerator( seed, 8, 5, 33, 5 );
            gen.setXScale( 684.412d / 80d );
            gen.setYScale( 684.412d / 160d );
            gen.setZScale( 684.412d / 80d );
            octaves.put( "detail", gen );

            gen = new SimplexOctaveGenerator( seed, 4, 16, 16 );
            gen.setScale( 0.0625d );
            octaves.put( "surface", gen );

            this.octaveCache.put( "normal", octaves );
        }
        return octaves;
    }

    private static void setBiomeHeight( BiomeHeight height, Biome... biomes ) {
        for ( Biome biome : biomes ) {
            HEIGHT_MAP.put( biome, height );
        }
    }

    protected static void setBiomeGround( GroundGenerator groundGenerator, Biome... biomes ) {
        for ( Biome biome : biomes ) {
            GROUND_MAP.put( biome, groundGenerator );
        }
    }
}
