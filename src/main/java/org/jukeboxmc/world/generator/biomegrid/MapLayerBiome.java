package org.jukeboxmc.world.generator.biomegrid;

import org.jukeboxmc.world.Biome;

public class MapLayerBiome extends MapLayer {

    private static final int[] WARM = new int[]{ Biome.DESERT.getId(), Biome.DESERT.getId(), Biome.DESERT.getId(), Biome.SAVANNA.getId(), Biome.SAVANNA.getId(), Biome.PLAINS.getId() };
    private static final int[] WET = new int[]{ Biome.PLAINS.getId(), Biome.PLAINS.getId(), Biome.FOREST.getId(), Biome.BIRCH_FOREST.getId(), Biome.ROOFED_FOREST.getId(), Biome.EXTREME_HILLS.getId(), Biome.SWAMPLAND.getId() };
    private static final int[] DRY = new int[]{ Biome.PLAINS.getId(), Biome.FOREST.getId(), Biome.TAIGA.getId(), Biome.EXTREME_HILLS.getId() };
    private static final int[] COLD = new int[]{ Biome.ICE_PLAINS.getId(), Biome.ICE_PLAINS.getId(), Biome.COLD_TAIGA.getId() };
    private static final int[] WARM_LARGE = new int[]{ Biome.MESA_PLATEAU_STONE.getId(), Biome.MESA_PLATEAU_STONE.getId(), Biome.MESA_PLATEAU.getId() };
    private static final int[] DRY_LARGE = new int[]{ Biome.MEGA_TAIGA.getId() };
    private static final int[] WET_LARGE = new int[]{ Biome.JUNGLE.getId() };

    private final MapLayer belowLayer;

    public MapLayerBiome( long seed, MapLayer belowLayer ) {
        super( seed );
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues( int x, int z, int sizeX, int sizeZ ) {
        int[] values = this.belowLayer.generateValues( x, z, sizeX, sizeZ );

        int[] finalValues = new int[sizeX * sizeZ];
        for ( int i = 0; i < sizeZ; i++ ) {
            for ( int j = 0; j < sizeX; j++ ) {
                int val = values[j + i * sizeX];
                if ( val != 0 ) {
                    setCoordsSeed( x + j, z + i );
                    switch ( val ) {
                        case 1 -> val = DRY[nextInt( DRY.length )];
                        case 2 -> val = WARM[nextInt( WARM.length )];
                        case 3, 1003 -> val = COLD[nextInt( COLD.length )];
                        case 4 -> val = WET[nextInt( WET.length )];
                        case 1001 -> val = DRY_LARGE[nextInt( DRY_LARGE.length )];
                        case 1002 -> val = WARM_LARGE[nextInt( WARM_LARGE.length )];
                        case 1004 -> val = WET_LARGE[nextInt( WET_LARGE.length )];
                        default -> {
                        }
                    }
                }
                finalValues[j + i * sizeX] = val;
            }
        }
        return finalValues;
    }
}
