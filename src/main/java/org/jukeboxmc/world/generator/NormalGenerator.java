package org.jukeboxmc.world.generator;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jukeboxmc.block.*;
import org.jukeboxmc.block.type.StoneType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.biome.BiomeGeneration;
import org.jukeboxmc.world.generator.biome.BiomeGenerationRegistry;
import org.jukeboxmc.world.generator.biomegrid.MapLayer;
import org.jukeboxmc.world.generator.noise.PerlinOctaveGenerator;
import org.jukeboxmc.world.generator.noise.SimplexOctaveGenerator;
import org.jukeboxmc.world.generator.noise.bukkit.OctaveGenerator;
import org.jukeboxmc.world.generator.object.OreType;
import org.jukeboxmc.world.generator.populator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class NormalGenerator extends Generator {

    protected MapLayer[] biomeGrid;

    protected static final double[][] ELEVATION_WEIGHT = new double[5][5];
    protected static final Int2ObjectMap<BiomeHeight> HEIGHT_MAP = new Int2ObjectOpenHashMap<>();

    protected final Map<String, Map<String, OctaveGenerator>> octaveCache = Maps.newHashMap();
    protected final double[][][] density = new double[5][5][33];
    protected final BiomeHeight defaultHeight = BiomeHeight.DEFAULT;

    protected static double coordinateScale = 684.412d;
    protected static double heightScale = 684.412d;
    protected static double heightNoiseScaleX = 200d; // depthNoiseScaleX
    protected static double heightNoiseScaleZ = 200d; // depthNoiseScaleZ
    protected static double detailNoiseScaleX = 80d;  // mainNoiseScaleX
    protected static double detailNoiseScaleY = 160d; // mainNoiseScaleY
    protected static double detailNoiseScaleZ = 80d;  // mainNoiseScaleZ
    protected static double surfaceScale = 0.0625d;
    protected static double baseSize = 8.5d;
    protected static double stretchY = 12d;
    protected static double biomeHeightOffset = 0d;    // biomeDepthOffset
    protected static double biomeHeightWeight = 1d;    // biomeDepthWeight
    protected static double biomeScaleOffset = 0d;
    protected static double biomeScaleWeight = 1d;

    private Random random;
    private final int waterHeight = 64;

    private final BlockStone blockStone;
    private final BlockWater blockWater;

    private final List<Populator> populators = new ArrayList<>();
    private final List<Populator> groundpopulators = new ArrayList<>();

    static {
        setBiomeHeight( BiomeHeight.OCEAN, Biome.OCEAN.getId(), Biome.FROZEN_OCEAN.getId() );
        setBiomeHeight( BiomeHeight.DEEP_OCEAN, Biome.DEEP_OCEAN.getId() );
        setBiomeHeight( BiomeHeight.RIVER, Biome.RIVER.getId(), Biome.FROZEN_RIVER.getId() );
        setBiomeHeight( BiomeHeight.FLAT_SHORE, Biome.BEACH.getId(), Biome.COLD_BEACH.getId(), Biome.MUSHROOM_ISLAND_SHORE.getId() );
        setBiomeHeight( BiomeHeight.ROCKY_SHORE, Biome.STONE_BEACH.getId() );
        setBiomeHeight( BiomeHeight.FLATLANDS, Biome.DESERT.getId(), Biome.ICE_PLAINS.getId(), Biome.SAVANNA.getId(), Biome.PLAINS.getId() );
        setBiomeHeight( BiomeHeight.EXTREME_HILLS, Biome.EXTREME_HILLS.getId(), Biome.EXTREME_HILLS_PLUS_TREES.getId(), Biome.EXTREME_HILLS_MUTATED.getId(), Biome.EXTREME_HILLS_PLUS_TREES_MUTATED.getId() );
        setBiomeHeight( BiomeHeight.MID_PLAINS, Biome.TAIGA.getId(), Biome.COLD_TAIGA.getId(), Biome.MEGA_TAIGA.getId() );
        setBiomeHeight( BiomeHeight.SWAMPLAND, Biome.SWAMPLAND.getId() );
        setBiomeHeight( BiomeHeight.LOW_HILLS, Biome.MUSHROOM_ISLAND.getId() );
        setBiomeHeight( BiomeHeight.HILLS, Biome.DESERT_HILLS.getId(), Biome.FOREST_HILLS.getId(), Biome.TAIGA_HILLS.getId(), Biome.EXTREME_HILLS_EDGE.getId(), Biome.JUNGLE_HILLS.getId(), Biome.BIRCH_FOREST_HILLS.getId(), Biome.COLD_TAIGA_HILLS.getId(), Biome.MEGA_TAIGA_HILLS.getId(), Biome.MESA_PLATEAU_STONE_MUTATED.getId(), Biome.MESA_PLATEAU_MUTATED.getId() );//, Biome.ICE_MOUNTAINS.id
        setBiomeHeight( BiomeHeight.HIGH_PLATEAU, Biome.SAVANNA_PLATEAU.getId(), Biome.MESA_PLATEAU_STONE.getId(), Biome.MESA_PLATEAU.getId() );
        setBiomeHeight( BiomeHeight.FLATLANDS_HILLS, Biome.DESERT_MUTATED.getId() );
        setBiomeHeight( BiomeHeight.BIG_HILLS, Biome.ICE_PLAINS_SPIKES.getId() );
        setBiomeHeight( BiomeHeight.BIG_HILLS2, Biome.BIRCH_FOREST_HILLS_MUTATED.getId() );
        setBiomeHeight( BiomeHeight.SWAMPLAND_HILLS, Biome.SWAMPLAND_MUTATED.getId() );
        setBiomeHeight( BiomeHeight.DEFAULT_HILLS, Biome.JUNGLE_MUTATED.getId(), Biome.JUNGLE_EDGE_MUTATED.getId(), Biome.BIRCH_FOREST_MUTATED.getId(), Biome.ROOFED_FOREST_MUTATED.getId() );
        setBiomeHeight( BiomeHeight.MID_HILLS, Biome.TAIGA_MUTATED.getId(), Biome.COLD_TAIGA_MUTATED.getId(), Biome.REDWOOD_TAIGA_MUTATED.getId() );//, Biome.MEGA_SPRUCE_TAIGA_HILLS.id
        setBiomeHeight( BiomeHeight.MID_HILLS2, Biome.FLOWER_FOREST.getId() );
        setBiomeHeight( BiomeHeight.LOW_SPIKES, Biome.SAVANNA_MUTATED.getId() );
        setBiomeHeight( BiomeHeight.HIGH_SPIKES, Biome.SAVANNA_PLATEAU_MUTATED.getId() );

        // fill a 5x5 array with values that acts as elevation weight on chunk neighboring, this can be viewed as a parabolic field: the center gets the more weight, and the weight decreases as distance increases from the center. This is applied on the lower scale biome grid.
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

    public NormalGenerator( World world, Dimension dimension ) {
        super( world, dimension );
        if ( this.biomeGrid == null ) {
            this.biomeGrid = MapLayer.initialize( world.getSeed(), Dimension.OVERWORLD, 1 );
        }
        if ( this.random == null ) {
            this.random = new Random( world.getSeed() );
        }

        this.blockStone = new BlockStone();
        this.blockWater = new BlockWater();

        this.groundpopulators.add( new GroundPopulator() );
        this.groundpopulators.add( new SnowlayerPopulator() );
        this.populators.add( new CavePopulator() );

        this.populators.add(
                new OrePopulator( new OreType[]{
                        new OreType( new BlockCoalOre(), 20, 17, 0, 128 ),
                        new OreType( new BlockIronOre(), 20, 9, 0, 64 ),
                        new OreType( new BlockRedstoneOre(), 8, 8, 0, 16 ),
                        new OreType( new BlockLapisOre(), 1, 7, 0, 16 ),
                        new OreType( new BlockGoldOre(), 2, 9, 0, 32 ),
                        new OreType( new BlockDiamondOre(), 1, 8, 0, 16 ),
                        new OreType( new BlockDirt(), 10, 33, 0, 128 ),
                        new OreType( new BlockGravel(), 8, 33, 0, 128 ),
                        new OreType( new BlockStone().setStoneType( StoneType.GRANITE ), 10, 33, 0, 80 ),
                        new OreType( new BlockStone().setStoneType( StoneType.DIORITE ), 10, 33, 0, 80 ),
                        new OreType( new BlockStone().setStoneType( StoneType.ANDESITE ), 10, 33, 0, 80 )
                } )
        );

    }

    @Override
    public void generate( int chunkX, int chunkZ ) {
        Chunk chunk = this.getChunk( chunkX, chunkZ );
        int x = chunkX << 2;
        int z = chunkZ << 2;

        int[] biomeGrid = this.biomeGrid[1].generateValues( x - 2, z - 2, 10, 10 );

        Map<String, OctaveGenerator> octaves = getWorldOctaves();
        double[] heightNoise = ( (PerlinOctaveGenerator) octaves.get( "height" ) ).getFractalBrownianMotion( x, z, 0.5d, 2d );
        double[] roughnessNoise = ( (PerlinOctaveGenerator) octaves.get( "roughness" ) ).getFractalBrownianMotion( x, 0, z, 0.5d, 2d );
        double[] roughnessNoise2 = ( (PerlinOctaveGenerator) octaves.get( "roughness2" ) ).getFractalBrownianMotion( x, 0, z, 0.5d, 2d );
        double[] detailNoise = ( (PerlinOctaveGenerator) octaves.get( "detail" ) ).getFractalBrownianMotion( x, 0, z, 0.5d, 2d );

        int index = 0;
        int indexHeight = 0;

        for ( int i = 0; i < 5; i++ ) {
            for ( int j = 0; j < 5; j++ ) {
                double avgHeightScale = 0;
                double avgHeightBase = 0;
                double totalWeight = 0;
                int biome = biomeGrid[i + 2 + ( j + 2 ) * 10];
                BiomeHeight biomeHeight = HEIGHT_MAP.getOrDefault( biome, defaultHeight );
                // Sampling an average height base and scale by visiting the neighborhood of the current biomegrid column.
                for ( int m = 0; m < 5; m++ ) {
                    for ( int n = 0; n < 5; n++ ) {
                        int nearBiome = biomeGrid[i + m + ( j + n ) * 10];
                        BiomeHeight nearBiomeHeight = HEIGHT_MAP.getOrDefault( nearBiome, defaultHeight );
                        double heightBase = biomeHeightOffset + nearBiomeHeight.getHeight() * biomeHeightWeight;
                        double heightScale = biomeScaleOffset + nearBiomeHeight.getScale() * biomeScaleWeight;
                        double weight = ELEVATION_WEIGHT[m][n] / ( heightBase + 2d );
                        if ( nearBiomeHeight.getHeight() > biomeHeight.getHeight() ) {
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

                noiseH = ( noiseH * 0.2d + avgHeightBase ) * baseSize / 8d * 4d + baseSize;
                for ( int k = 0; k < 33; k++ ) {
                    // density should be lower and lower as we climb up, this gets a height value to subtract from the noise.
                    double nh = ( k - noiseH ) * stretchY * 128d / 256d / avgHeightScale;
                    if ( nh < 0 ) {
                        nh *= 4d;
                    }
                    double noiseR = roughnessNoise[index] / 512d;
                    double noiseR2 = roughnessNoise2[index] / 512d;
                    double noiseD = ( detailNoise[index] / 10d + 1d ) / 2d;
                    // linear interpolation
                    double dens = noiseD < 0 ? noiseR
                            : noiseD > 1 ? noiseR2 : noiseR + ( noiseR2 - noiseR ) * noiseD;
                    dens -= nh;
                    index++;
                    if ( k > 29 ) {
                        double lowering = ( k - 29 ) / 3d;
                        // linear interpolation
                        dens = dens * ( 1d - lowering ) + -10d * lowering;
                    }
                    this.density[i][j][k] = dens;
                }
            }
        }

        int fill = 0;
        int afill = Math.abs( fill );
        int seaFill = 0;
        double densityOffset = 0;

        for ( int i = 0; i < 5 - 1; i++ ) {
            for ( int j = 0; j < 5 - 1; j++ ) {
                for ( int k = 0; k < 33 - 1; k++ ) {
                    // 2x2 grid
                    double d1 = this.density[i][j][k];
                    double d2 = this.density[i + 1][j][k];
                    double d3 = this.density[i][j + 1][k];
                    double d4 = this.density[i + 1][j + 1][k];
                    // 2x2 grid (row above)
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
                                // any density higher than density offset is ground, any density lower or equal to the density offset is air (or water if under the sea level).
                                // this can be flipped if the mode is negative, so lower or equal to is ground, and higher is air/water and, then data can be shifted by afill the order is air by default, ground, then water.
                                // they can shift places within each if statement the target is densityOffset + 0, since the default target is 0, so don't get too confused by the naming.
                                if ( afill == 1 || afill == 10 || afill == 13 || afill == 16 ) {
                                    chunk.setBlock( m + ( i << 2 ), l + ( k << 3 ), n + ( j << 2 ), 0, this.blockWater );
                                } else if ( afill == 2 || afill == 9 || afill == 12 || afill == 15 ) {
                                    chunk.setBlock( m + ( i << 2 ), l + ( k << 3 ), n + ( j << 2 ), 0, this.blockStone );
                                }
                                if ( dens > densityOffset && fill > -1 || dens <= densityOffset && fill < 0 ) {
                                    if ( afill == 0 || afill == 3 || afill == 6 || afill == 9 || afill == 12 ) {
                                        chunk.setBlock( m + ( i << 2 ), l + ( k << 3 ), n + ( j << 2 ), 0, this.blockStone );
                                    } else if ( afill == 2 || afill == 7 || afill == 10 || afill == 16 ) {
                                        chunk.setBlock( m + ( i << 2 ), l + ( k << 3 ), n + ( j << 2 ), 0, this.blockWater );
                                    }
                                } else if ( l + ( k << 3 ) < this.waterHeight - 1 && seaFill == 0 || l + ( k << 3 ) >= this.waterHeight - 1 && seaFill == 1 ) {
                                    if ( afill == 0 || afill == 3 || afill == 7 || afill == 10 || afill == 13 ) {
                                        chunk.setBlock( m + ( i << 2 ), l + ( k << 3 ), n + ( j << 2 ), 0, this.blockWater );
                                    } else if ( afill == 1 || afill == 6 || afill == 9 || afill == 15 ) {
                                        chunk.setBlock( m + ( i << 2 ), l + ( k << 3 ), n + ( j << 2 ), 0, this.blockStone );
                                    }
                                }
                                // interpolation along z
                                dens += ( d10 - d9 ) / 4;
                            }
                            // interpolation along x
                            d9 += ( d2 - d1 ) / 4;
                            // interpolate along z
                            d10 += ( d4 - d3 ) / 4;
                        }
                        // interpolation along y
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

        for ( int sx = 0; sx < sizeX; sx++ ) {
            for ( int sz = 0; sz < sizeZ; sz++ ) {
                for ( int y = chunk.getMinY(); y < chunk.getMaxY(); y++ ) {
                    chunk.setBiome( sx, y, sz, Biome.findById( biomes.getBiome( sx, sz ) ) );
                }
            }
        }

        for ( Populator populator : this.groundpopulators ) {
            populator.populate( this.random, this.getWorld(), chunk );
        }
    }

    @Override
    public void populate( int chunkX, int chunkZ ) {
        Chunk chunk = this.getChunk( chunkX, chunkZ );

        this.populators.forEach( populator -> populator.populate( this.random, this.getWorld(), chunk ) );

        Biome biome = chunk.getBiome( 7, 7, 7 );
        BiomeGeneration biomeGenerator = BiomeGenerationRegistry.getBiomeGenerator( biome );
        if ( biomeGenerator != null ) {
            biomeGenerator.getPopulators().forEach( populator -> populator.populate( this.random, this.getWorld(), chunk ) );
        }
    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 0.5f, 128f, 0.5f );
    }

    protected static void setBiomeHeight( BiomeHeight height, int... biomes ) {
        for ( int biome : biomes ) {
            HEIGHT_MAP.put( biome, height );
        }
    }

    protected Map<String, OctaveGenerator> getWorldOctaves() {
        Map<String, OctaveGenerator> octaves = this.octaveCache.get( "normal" );
        if ( octaves == null ) {
            octaves = Maps.newHashMap();
            Random seed = new Random( this.getWorld().getSeed() );

            OctaveGenerator gen = new PerlinOctaveGenerator( seed, 16, 5, 5 );
            gen.setXScale( heightNoiseScaleX );
            gen.setZScale( heightNoiseScaleZ );
            octaves.put( "height", gen );

            gen = new PerlinOctaveGenerator( seed, 16, 5, 33, 5 );
            gen.setXScale( coordinateScale );
            gen.setYScale( heightScale );
            gen.setZScale( coordinateScale );
            octaves.put( "roughness", gen );

            gen = new PerlinOctaveGenerator( seed, 16, 5, 33, 5 );
            gen.setXScale( coordinateScale );
            gen.setYScale( heightScale );
            gen.setZScale( coordinateScale );
            octaves.put( "roughness2", gen );

            gen = new PerlinOctaveGenerator( seed, 8, 5, 33, 5 );
            gen.setXScale( coordinateScale / detailNoiseScaleX );
            gen.setYScale( heightScale / detailNoiseScaleY );
            gen.setZScale( coordinateScale / detailNoiseScaleZ );
            octaves.put( "detail", gen );

            gen = new SimplexOctaveGenerator( seed, 4, 16, 16 );
            gen.setScale( surfaceScale );
            octaves.put( "surface", gen );

            this.octaveCache.put( "normal", octaves );
        }
        return octaves;
    }

    public static class BiomeHeight {

        public static final BiomeHeight DEFAULT = new BiomeHeight( 0.1, 0.2 );
        public static final BiomeHeight FLAT_SHORE = new BiomeHeight( 0, 0.025d );
        public static final BiomeHeight HIGH_PLATEAU = new BiomeHeight( 1.5d, 0.025d );
        public static final BiomeHeight FLATLANDS = new BiomeHeight( 0.125d, 0.05d );
        public static final BiomeHeight SWAMPLAND = new BiomeHeight( -0.2d, 0.1d );
        public static final BiomeHeight MID_PLAINS = new BiomeHeight( 0.2d, 0.2d );
        public static final BiomeHeight FLATLANDS_HILLS = new BiomeHeight( 0.275d, 0.25d );
        public static final BiomeHeight SWAMPLAND_HILLS = new BiomeHeight( -0.1d, 0.3d );
        public static final BiomeHeight LOW_HILLS = new BiomeHeight( 0.2d, 0.3d );
        public static final BiomeHeight HILLS = new BiomeHeight( 0.45d, 0.3d );
        public static final BiomeHeight MID_HILLS2 = new BiomeHeight( 0.1d, 0.4d );
        public static final BiomeHeight DEFAULT_HILLS = new BiomeHeight( 0.2d, 0.4d );
        public static final BiomeHeight MID_HILLS = new BiomeHeight( 0.3d, 0.4d );
        public static final BiomeHeight BIG_HILLS = new BiomeHeight( 0.525d, 0.55d );
        public static final BiomeHeight BIG_HILLS2 = new BiomeHeight( 0.55d, 0.5d );
        public static final BiomeHeight EXTREME_HILLS = new BiomeHeight( 1d, 0.5d );
        public static final BiomeHeight ROCKY_SHORE = new BiomeHeight( 0.1d, 0.8d );
        public static final BiomeHeight LOW_SPIKES = new BiomeHeight( 0.4125d, 1.325d );
        public static final BiomeHeight HIGH_SPIKES = new BiomeHeight( 1.1d, 1.3125d );
        public static final BiomeHeight RIVER = new BiomeHeight( -0.5d, 0d );
        public static final BiomeHeight OCEAN = new BiomeHeight( -1d, 0.1d );
        public static final BiomeHeight DEEP_OCEAN = new BiomeHeight( -1.8d, 0.1d );

        protected final double height;
        protected final double scale;

        BiomeHeight( double height, double scale ) {
            this.height = height;
            this.scale = scale;
        }

        public double getHeight() {
            return this.height;
        }

        public double getScale() {
            return this.scale;
        }
    }

    public static class BiomeGrid {

        public final byte[] biomes = new byte[256];

        public int getBiome( int x, int z ) {
            // upcasting is very important to get extended biomes
            return Biome.findById( biomes[x | z << 4] & 0xff ).getId();
        }

        public void setBiome( int x, int z, Biome biome ) {
            this.biomes[x | z << 4] = (byte) biome.getId();
        }
    }
}
