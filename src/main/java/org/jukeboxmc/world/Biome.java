package org.jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Biome {

    OCEAN( 0, "Ocean" ),
    PLAINS( 1, "Plains" ),
    DESERT( 2, "Desert" ),
    EXTREME_HILLS( 3, "Extreme Hills" ),
    FOREST( 4, "Forest" ),
    TAIGA( 5, "Taiga" ),
    SWAMPLAND( 6, "Swampland" ),
    RIVER( 7, "River" ),
    HELL( 8, "Hell" ),
    THE_END( 9, "The End" ),
    LEGACY_FROZEN_OCEAN( 10, "Legacy Frozen Ocean" ),
    FROZEN_RIVER( 11, "Frozen River" ),
    ICE_PLAINS( 12, "Ice Plains" ),
    ICE_MOUNTAINS( 13, "Ice Mountains" ),
    MUSHROOM_ISLAND( 14, "Mushroom Island" ),
    MUSHROOM_ISLAND_SHORE( 15, "Mushroom Island Shore" ),
    BEACH( 16, "Beach" ),
    DESERT_HILLS( 17, "Desert Hills" ),
    FOREST_HILLS( 18, "Forest Hills" ),
    TAIGA_HILLS( 19, "Taiga Hills" ),
    EXTREME_HILLS_EDGE( 20, "Extreme Hills Edge" ),
    JUNGLE( 21, "Jungle" ),
    JUNGLE_HILLS( 22, "Jungle Hills" ),
    JUNGLE_EDGE( 23, "Jungle Edge" ),
    DEEP_OCEAN( 24, "Deep Ocean" ),
    STONE_BEACH( 25, "Stone Beach" ),
    COLD_BEACH( 26, "Cold Beach" ),
    BIRCH_FOREST( 27, "Birch Forest" ),
    BIRCH_FOREST_HILLS( 28, "Birch Forest Hills" ),
    ROOFED_FOREST( 29, "Roofed Forest" ),
    COLD_TAIGA( 30, "Cold Taiga" ),
    COLD_TAIGA_HILLS( 31, "Cold Taiga Hills" ),
    MEGA_TAIGA( 32, "Mega Taiga" ),
    MEGA_TAIGA_HILLS( 33, "Mega Taiga Hills" ),
    EXTREME_HILLS_PLUS_TREES( 34, "Extreme Hills Plus Trees" ),
    SAVANNA( 35, "Savanna" ),
    SAVANNA_PLATEAU( 36, "Savanna Plateau" ),
    MESA( 37, "Mesa" ),
    MESA_PLATEAU_STONE( 38, "Mesa Plateau Stone" ),
    MESA_PLATEAU( 39, "Mesa Plateau" ),
    WARM_OCEAN( 40, "Warm Ocean" ),
    DEEP_WARM_OCEAN( 41, "Deep Warm Ocean" ),
    LUKEWARM_OCEAN( 42, "Lukewarm Ocean" ),
    DEEP_LUKEWARM_OCEAN( 43, "Deep Lukewarm Ocean" ),
    COLD_OCEAN( 44, "Cold Ocean" ),
    DEEP_COLD_OCEAN( 45, "Deep Cold Ocean" ),
    FROZEN_OCEAN( 46, "Frozen Ocean" ),
    DEEP_FROZEN_OCEAN( 47, "Deep Frozen Ocean" ),
    BAMBOO_JUNGLE( 48, "Bamboo Jungle" ),
    BAMBOO_JUNGLE_HILLS( 49, "Bamboo Jungle Hills" ),
    SUNFLOWER_PLAINS( 129, "Sunflower Plains" ),
    DESERT_MUTATED( 130, "Desert Mutated" ),
    EXTREME_HILLS_MUTATED( 131, "Extreme Hills Mutated" ),
    FLOWER_FOREST( 132, "Flower Forest" ),
    TAIGA_MUTATED( 133, "Taiga Mutated" ),
    SWAMPLAND_MUTATED( 134, "Swampland Mutated" ),
    ICE_PLAINS_SPIKES( 140, "Ice Plains Spikes" ),
    JUNGLE_MUTATED( 149, "Jungle Mutated" ),
    JUNGLE_EDGE_MUTATED( 151, "Jungle Edge Mutated" ),
    BIRCH_FOREST_MUTATED( 155, "Birch Forest Mutated" ),
    BIRCH_FOREST_HILLS_MUTATED( 156, "Birch Forest Hills Mutated" ),
    ROOFED_FOREST_MUTATED( 157, "Roofed Forest Mutated" ),
    COLD_TAIGA_MUTATED( 158, "Cold Taiga Mutated" ),
    REDWOOD_TAIGA_MUTATED( 160, "Redwood Taiga Mutated" ),
    REDWOOD_TAIGA_HILLS_MUTATED( 161, "Redwood Taiga Hills Mutated" ),
    EXTREME_HILLS_PLUS_TREES_MUTATED( 162, "Extreme Hills Plus Trees Mutated" ),
    SAVANNA_MUTATED( 163, "Savanna Mutated" ),
    SAVANNA_PLATEAU_MUTATED( 164, "Savanna Plateau Mutated" ),
    MESA_BRYCE( 165, "Mesa Bryce" ),
    MESA_PLATEAU_STONE_MUTATED( 166, "Mesa Plateau Stone Mutated" ),
    MESA_PLATEAU_MUTATED( 167, "Mesa Plateau Mutated" ),
    SOULSAND_VALLEY( 178, "Soulsand Valley" ),
    CRIMSON_FOREST( 179, "Crimson Forest" ),
    WARPED_FOREST( 180, "Warped Forest" ),
    BASALT_DELTAS( 181, "Basalt Deltas" ),
    JAGGED_PEAKS( 182, "Jagged Peaks" ),
    FROZEN_PEAKS( 183, "Frozen Peaks" ),
    SNOWY_SLOPES( 184, "Snowy Slopes" ),
    GROVE( 185, "Grove" ),
    MEADOW( 186, "Meadow" ),
    LUSH_CAVES( 187, "Lush Caves" ),
    DRIPSTONE_CAVES( 188, "Dripstone Caves" ),
    STONY_PEAKS( 189, "Stony Peaks" );

    private int id;
    private String name;

    Biome( int id, String name ) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static Biome findById( int id ) {
        for ( Biome value : Biome.values() ) {
            if ( value.id == id ) {
                return value;
            }
        }
        return null;
    }
}
