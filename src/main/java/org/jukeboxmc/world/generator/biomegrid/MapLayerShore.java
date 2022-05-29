package org.jukeboxmc.world.generator.biomegrid;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.jukeboxmc.world.Biome;

public class MapLayerShore extends MapLayer {

    private static final IntSet OCEANS = new IntOpenHashSet();
    private static final Int2IntMap SPECIAL_SHORES = new Int2IntOpenHashMap();

    static {
        OCEANS.add( Biome.OCEAN.getId() );
        OCEANS.add( Biome.DEEP_OCEAN.getId() );

        SPECIAL_SHORES.put( Biome.EXTREME_HILLS.getId(), Biome.STONE_BEACH.getId() );
        SPECIAL_SHORES.put( Biome.EXTREME_HILLS_PLUS_TREES.getId(), Biome.STONE_BEACH.getId() );
        SPECIAL_SHORES.put( Biome.EXTREME_HILLS_MUTATED.getId(), Biome.STONE_BEACH.getId() );
        SPECIAL_SHORES.put( Biome.EXTREME_HILLS_PLUS_TREES_MUTATED.getId(), Biome.STONE_BEACH.getId() );
        SPECIAL_SHORES.put( Biome.ICE_PLAINS.getId(), Biome.COLD_BEACH.getId() );
        //SPECIAL_SHORES.put(Biome.ICE_MOUNTAINS.id, Biome.COLD_BEACH.id);
        SPECIAL_SHORES.put( Biome.ICE_PLAINS_SPIKES.getId(), Biome.COLD_BEACH.getId() );
        SPECIAL_SHORES.put( Biome.COLD_TAIGA.getId(), Biome.COLD_BEACH.getId() );
        SPECIAL_SHORES.put( Biome.COLD_TAIGA_HILLS.getId(), Biome.COLD_BEACH.getId() );
        SPECIAL_SHORES.put( Biome.COLD_TAIGA_MUTATED.getId(), Biome.COLD_BEACH.getId() );
        SPECIAL_SHORES.put( Biome.MUSHROOM_ISLAND.getId(), Biome.MUSHROOM_ISLAND_SHORE.getId() );
        SPECIAL_SHORES.put( Biome.SWAMPLAND.getId(), Biome.SWAMPLAND.getId() );
        SPECIAL_SHORES.put( Biome.MESA.getId(), Biome.MESA.getId() );
        SPECIAL_SHORES.put( Biome.MESA_PLATEAU_STONE.getId(), Biome.MESA_PLATEAU_STONE.getId() );
        SPECIAL_SHORES.put( Biome.MESA_PLATEAU_STONE_MUTATED.getId(), Biome.MESA_PLATEAU_STONE_MUTATED.getId() );
        SPECIAL_SHORES.put( Biome.MESA_PLATEAU.getId(), Biome.MESA_PLATEAU.getId() );
        SPECIAL_SHORES.put( Biome.MESA_PLATEAU_MUTATED.getId(), Biome.MESA_PLATEAU_MUTATED.getId() );
        SPECIAL_SHORES.put( Biome.MESA_BRYCE.getId(), Biome.MESA_BRYCE.getId() );
    }

    private final MapLayer belowLayer;

    public MapLayerShore( long seed, MapLayer belowLayer ) {
        super( seed );
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues( int x, int z, int sizeX, int sizeZ ) {
        int gridX = x - 1;
        int gridZ = z - 1;
        int gridSizeX = sizeX + 2;
        int gridSizeZ = sizeZ + 2;
        int[] values = this.belowLayer.generateValues( gridX, gridZ, gridSizeX, gridSizeZ );

        int[] finalValues = new int[sizeX * sizeZ];
        for ( int i = 0; i < sizeZ; i++ ) {
            for ( int j = 0; j < sizeX; j++ ) {
                // This applies shores using Von Neumann neighborhood
                // it takes a 3x3 grid with a cross shape and analyzes values as follow
                // OXO
                // XxX
                // OXO
                // the grid center value decides how we are proceeding:
                // - if it's not ocean and it's surrounded by at least 1 ocean cell it turns the center value into beach.
                int upperVal = values[j + 1 + i * gridSizeX];
                int lowerVal = values[j + 1 + ( i + 2 ) * gridSizeX];
                int leftVal = values[j + ( i + 1 ) * gridSizeX];
                int rightVal = values[j + 2 + ( i + 1 ) * gridSizeX];
                int centerVal = values[j + 1 + ( i + 1 ) * gridSizeX];
                if ( !OCEANS.contains( centerVal ) && ( OCEANS.contains( upperVal ) || OCEANS.contains( lowerVal ) || OCEANS.contains( leftVal ) || OCEANS.contains( rightVal ) ) ) {
                    finalValues[j + i * sizeX] = SPECIAL_SHORES.containsKey( centerVal ) ? SPECIAL_SHORES.get( centerVal ) : Biome.BEACH.getId();
                } else {
                    finalValues[j + i * sizeX] = centerVal;
                }
            }
        }
        return finalValues;
    }
}
