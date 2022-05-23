package org.jukeboxmc.world.generator.biomegrid;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jukeboxmc.world.Biome;

public class MapLayerBiomeVariation extends MapLayer {

    private static final int[] ISLANDS = new int[]{ Biome.PLAINS.getId(), Biome.FOREST.getId() };
    private static final Int2ObjectMap<int[]> VARIATIONS = new Int2ObjectOpenHashMap<>();

    static {
        VARIATIONS.put( Biome.DESERT.getId(), new int[]{ Biome.DESERT_HILLS.getId() } );
        VARIATIONS.put( Biome.FOREST.getId(), new int[]{ Biome.FOREST_HILLS.getId() } );
        VARIATIONS.put( Biome.BIRCH_FOREST.getId(), new int[]{ Biome.BIRCH_FOREST_HILLS.getId() } );
        VARIATIONS.put( Biome.ROOFED_FOREST.getId(), new int[]{ Biome.PLAINS.getId() } );
        VARIATIONS.put( Biome.TAIGA.getId(), new int[]{ Biome.TAIGA_HILLS.getId() } );
        VARIATIONS.put( Biome.MEGA_TAIGA.getId(), new int[]{ Biome.MEGA_TAIGA_HILLS.getId() } );
        VARIATIONS.put( Biome.COLD_TAIGA.getId(), new int[]{ Biome.COLD_TAIGA_HILLS.getId() } );
        VARIATIONS.put( Biome.PLAINS.getId(), new int[]{ Biome.FOREST.getId(), Biome.FOREST.getId(), Biome.FOREST_HILLS.getId() } );
        //VARIATIONS.put(Biome.ICE_PLAINS.id, new int[]{Biome.ICE_MOUNTAINS.id});
        VARIATIONS.put( Biome.JUNGLE.getId(), new int[]{ Biome.JUNGLE_HILLS.getId() } );
        VARIATIONS.put( Biome.OCEAN.getId(), new int[]{ Biome.DEEP_OCEAN.getId() } );
        VARIATIONS.put( Biome.EXTREME_HILLS.getId(), new int[]{ Biome.EXTREME_HILLS_PLUS_TREES.getId() } );
        VARIATIONS.put( Biome.SAVANNA.getId(), new int[]{ Biome.SAVANNA_PLATEAU.getId() } );
        VARIATIONS.put( Biome.MESA_PLATEAU_STONE.getId(), new int[]{ Biome.MESA.getId() } );
        VARIATIONS.put( Biome.MESA_PLATEAU.getId(), new int[]{ Biome.MESA.getId() } );
        VARIATIONS.put( Biome.MESA.getId(), new int[]{ Biome.MESA.getId() } );
    }

    private final MapLayer belowLayer;
    private final MapLayer variationLayer;

    /**
     * Creates an instance with no variation layer.
     *
     * @param seed       the PRNG seed
     * @param belowLayer the layer below this one
     */
    public MapLayerBiomeVariation( long seed, MapLayer belowLayer ) {
        this( seed, belowLayer, null );
    }

    /**
     * Creates an instance with the given variation layer.
     *
     * @param seed           the PRNG seed
     * @param belowLayer     the layer below this one
     * @param variationLayer the variation layer, or null to use no variation layer
     */
    public MapLayerBiomeVariation( long seed, MapLayer belowLayer, MapLayer variationLayer ) {
        super( seed );
        this.belowLayer = belowLayer;
        this.variationLayer = variationLayer;
    }

    @Override
    public int[] generateValues( int x, int z, int sizeX, int sizeZ ) {
        if ( this.variationLayer == null ) {
            return generateRandomValues( x, z, sizeX, sizeZ );
        }
        return mergeValues( x, z, sizeX, sizeZ );
    }

    /**
     * Generates a rectangle, replacing all the positive values in the previous layer with random
     * values from 2 to 31 while leaving zero and negative values unchanged.
     *
     * @param x     the lowest x coordinate
     * @param z     the lowest z coordinate
     * @param sizeX the x coordinate range
     * @param sizeZ the z coordinate range
     * @return a flattened array of generated values
     */
    public int[] generateRandomValues( int x, int z, int sizeX, int sizeZ ) {
        int[] values = this.belowLayer.generateValues( x, z, sizeX, sizeZ );

        int[] finalValues = new int[sizeX * sizeZ];
        for ( int i = 0; i < sizeZ; i++ ) {
            for ( int j = 0; j < sizeX; j++ ) {
                int val = values[j + i * sizeX];
                if ( val > 0 ) {
                    setCoordsSeed( x + j, z + i );
                    val = nextInt( 30 ) + 2;
                }
                finalValues[j + i * sizeX] = val;
            }
        }
        return finalValues;
    }

    /**
     * Generates a rectangle using the previous layer and the variation layer.
     *
     * @param x     the lowest x coordinate
     * @param z     the lowest z coordinate
     * @param sizeX the x coordinate range
     * @param sizeZ the z coordinate range
     * @return a flattened array of generated values
     */
    public int[] mergeValues( int x, int z, int sizeX, int sizeZ ) {
        int gridX = x - 1;
        int gridZ = z - 1;
        int gridSizeX = sizeX + 2;
        int gridSizeZ = sizeZ + 2;

        int[] values = this.belowLayer.generateValues( gridX, gridZ, gridSizeX, gridSizeZ );
        int[] variationValues = this.variationLayer.generateValues( gridX, gridZ, gridSizeX, gridSizeZ );

        int[] finalValues = new int[sizeX * sizeZ];
        for ( int i = 0; i < sizeZ; i++ ) {
            for ( int j = 0; j < sizeX; j++ ) {
                setCoordsSeed( x + j, z + i );
                int centerValue = values[j + 1 + ( i + 1 ) * gridSizeX];
                int variationValue = variationValues[j + 1 + ( i + 1 ) * gridSizeX];
                if ( centerValue != 0 && variationValue == 3 && centerValue < 128 ) {
                    finalValues[j + i * sizeX] = Biome.findById( centerValue + 128 ) != null ? centerValue + 128 : centerValue;
                } else if ( variationValue == 2 || nextInt( 3 ) == 0 ) {
                    int val = centerValue;
                    if ( VARIATIONS.containsKey( centerValue ) ) {
                        val = VARIATIONS.get( centerValue )[nextInt( VARIATIONS.get( centerValue ).length )];
                    } else if ( centerValue == Biome.DEEP_OCEAN.getId() && nextInt( 3 ) == 0 ) {
                        val = ISLANDS[nextInt( ISLANDS.length )];
                    }
                    if ( variationValue == 2 && val != centerValue ) {
                        val = Biome.findById( val + 128 )!= null ? val + 128 : centerValue;
                    }
                    if ( val != centerValue ) {
                        int count = 0;
                        if ( values[j + 1 + i * gridSizeX] == centerValue ) { // upper value
                            count++;
                        }
                        if ( values[j + 1 + ( i + 2 ) * gridSizeX] == centerValue ) { // lower value
                            count++;
                        }
                        if ( values[j + ( i + 1 ) * gridSizeX] == centerValue ) { // left value
                            count++;
                        }
                        if ( values[j + 2 + ( i + 1 ) * gridSizeX] == centerValue ) { // right value
                            count++;
                        }
                        // spread mountains if not too close from an edge
                        finalValues[j + i * sizeX] = count < 3 ? centerValue : val;
                    } else {
                        finalValues[j + i * sizeX] = val;
                    }
                } else {
                    finalValues[j + i * sizeX] = centerValue;
                }
            }
        }
        return finalValues;
    }
}
