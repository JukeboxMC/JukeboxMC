package org.jukeboxmc.world;

import org.jukeboxmc.util.Utils;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Biome {

    OCEAN( 0, "ocean", "Ocean", 0.5f, 0.5f, 46, 58 ),
    PLAINS( 1, "plains", "Plains", 0.8f, 0.4f, 63, 68 ),
    DESERT( 2, "desert", "Desert", 2.0f, 0.0f ),
    EXTREME_HILLS( 3, "extreme_hills", "Extreme Hills ", 0.2f, 0.3f ),
    FOREST( 4, "forest", "Forest", 0.7f, 0.8f, 63, 81 ),
    TAIGA( 5, "taiga", "Taiga", 0.25f, 0.8f ),
    SWAMPLAND( 6, "swampland", "Swampland", 0.8f, 0.5f ),
    RIVER( 7, "river", "River", 0.5f, 0.5f, 52, 62 ),
    HELL( 8, "hell", "Hell", 2.0f, 0.0f ),
    THE_END( 9, "the_end", "The End ", 0.5f, 0.5f ),
    LEGACY_FROZEN_OCEAN( 10, "legacy_frozen_ocean", "Legacy Frozen Ocean ", 0.0f, 0.5f ),
    FROZEN_RIVER( 11, "frozen_river", "Frozen River ", 0.0f, 0.5f ),
    ICE_PLAINS( 12, "ice_plains", "Ice Plains ", 0.0f, 0.5f ),
    ICE_MOUNTAINS( 13, "ice_mountains", "Ice Mountains ", 0.0f, 0.5f ),
    MUSHROOM_ISLAND( 14, "mushroom_island", "Mushroom Island ", 0.9f, 1.0f ),
    MUSHROOM_ISLAND_SHORE( 15, "mushroom_island_shore", "Mushroom Island Shore ", 0.9f, 1.0f ),
    BEACH( 16, "beach", "Beach", 0.8f, 0.4f ),
    DESERT_HILLS( 17, "desert_hills", "Desert Hills ", 2.0f, 0.0f ),
    FOREST_HILLS( 18, "forest_hills", "Forest Hills ", 0.7f, 0.8f ),
    TAIGA_HILLS( 19, "taiga_hills", "Taiga Hills ", 0.25f, 0.8f ),
    EXTREME_HILLS_EDGE( 20, "extreme_hills_edge", "Extreme Hills Edge ", 0.2f, 0.3f ),
    JUNGLE( 21, "jungle", "Jungle", 0.95f, 0.9f ),
    JUNGLE_HILLS( 22, "jungle_hills", "Jungle Hills ", 0.95f, 0.9f ),
    JUNGLE_EDGE( 23, "jungle_edge", "Jungle Edge ", 0.95f, 0.8f ),
    DEEP_OCEAN( 24, "deep_ocean", "Deep Ocean ", 0.5f, 0.5f ),
    STONE_BEACH( 25, "stone_beach", "Stone Beach ", 0.2f, 0.3f ),
    COLD_BEACH( 26, "cold_beach", "Cold Beach ", 0.05f, 0.3f ),
    BIRCH_FOREST( 27, "birch_forest", "Birch Forest ", 0.6f, 0.6f, 63, 81 ),
    BIRCH_FOREST_HILLS( 28, "birch_forest_hills", "Birch Forest Hills ", 0.6f, 0.6f ),
    ROOFED_FOREST( 29, "roofed_forest", "Roofed Forest ", 0.7f, 0.8f ),
    COLD_TAIGA( 30, "cold_taiga", "Cold Taiga ", -0.5f, 0.4f ),
    COLD_TAIGA_HILLS( 31, "cold_taiga_hills", "Cold Taiga Hills ", -0.5f, 0.4f ),
    MEGA_TAIGA( 32, "mega_taiga", "Mega Taiga ", 0.3f, 0.8f ),
    MEGA_TAIGA_HILLS( 33, "mega_taiga_hills", "Mega Taiga Hills ", 0.3f, 0.8f ),
    EXTREME_HILLS_PLUS_TREES( 34, "extreme_hills_plus_trees", "Extreme Hills Plus Trees ", 0.2f, 0.3f ),
    SAVANNA( 35, "savanna", "Savanna", 1.2f, 0.0f ),
    SAVANNA_PLATEAU( 36, "savanna_plateau", "Savanna Plateau ", 1.0f, 0.0f ),
    MESA( 37, "mesa", "Mesa", 2.0f, 0.0f ),
    MESA_PLATEAU_STONE( 38, "mesa_plateau_stone", "Mesa Plateau Stone ", 2.0f, 0.0f ),
    MESA_PLATEAU( 39, "mesa_plateau", "Mesa Plateau ", 2.0f, 0.0f ),
    WARM_OCEAN( 40, "warm_ocean", "Warm Ocean ", 0.5f, 0.5f ),
    DEEP_WARM_OCEAN( 41, "deep_warm_ocean", "Deep Warm Ocean ", 0.5f, 0.5f ),
    LUKEWARM_OCEAN( 42, "lukewarm_ocean", "Lukewarm Ocean ", 0.5f, 0.5f ),
    DEEP_LUKEWARM_OCEAN( 43, "deep_lukewarm_ocean", "Deep Lukewarm Ocean ", 0.5f, 0.5f ),
    COLD_OCEAN( 44, "cold_ocean", "Cold Ocean ", 0.5f, 0.5f ),
    DEEP_COLD_OCEAN( 45, "deep_cold_ocean", "Deep Cold Ocean ", 0.5f, 0.5f ),
    FROZEN_OCEAN( 46, "frozen_ocean", "Frozen Ocean ", 0.0f, 0.5f ),
    DEEP_FROZEN_OCEAN( 47, "deep_frozen_ocean", "Deep Frozen Ocean ", 0.0f, 0.5f ),
    BAMBOO_JUNGLE( 48, "bamboo_jungle", "Bamboo Jungle ", 0.95f, 0.9f ),
    BAMBOO_JUNGLE_HILLS( 49, "bamboo_jungle_hills", "Bamboo Jungle Hills ", 0.95f, 0.9f ),
    SUNFLOWER_PLAINS( 129, "sunflower_plains", "Sunflower Plains ", 0.8f, 0.4f ),
    DESERT_MUTATED( 130, "desert_mutated", "Desert Mutated ", 2.0f, 0.0f ),
    EXTREME_HILLS_MUTATED( 131, "extreme_hills_mutated", "Extreme Hills Mutated ", 0.2f, 0.3f ),
    FLOWER_FOREST( 132, "flower_forest", "Flower Forest ", 0.7f, 0.8f ),
    TAIGA_MUTATED( 133, "taiga_mutated", "Taiga Mutated ", 0.25f, 0.8f ),
    SWAMPLAND_MUTATED( 134, "swampland_mutated", "Swampland Mutated ", 0.8f, 0.5f ),
    ICE_PLAINS_SPIKES( 140, "ice_plains_spikes", "Ice Plains Spikes ", 0.0f, 1.0f ),
    JUNGLE_MUTATED( 149, "jungle_mutated", "Jungle Mutated ", 0.95f, 0.9f ),
    JUNGLE_EDGE_MUTATED( 151, "jungle_edge_mutated", "Jungle Edge Mutated ", 0.95f, 0.8f ),
    BIRCH_FOREST_MUTATED( 155, "birch_forest_mutated", "Birch Forest Mutated ", 0.7f, 0.8f ),
    BIRCH_FOREST_HILLS_MUTATED( 156, "birch_forest_hills_mutated", "Birch Forest Hills Mutated ", 0.7f, 0.8f ),
    ROOFED_FOREST_MUTATED( 157, "roofed_forest_mutated", "Roofed Forest Mutated ", 0.7f, 0.8f ),
    COLD_TAIGA_MUTATED( 158, "cold_taiga_mutated", "Cold Taiga Mutated ", -0.5f, 0.4f ),
    REDWOOD_TAIGA_MUTATED( 160, "redwood_taiga_mutated", "Redwood Taiga Mutated ", 0.25f, 0.8f ),
    REDWOOD_TAIGA_HILLS_MUTATED( 161, "redwood_taiga_hills_mutated", "Redwood Taiga Hills Mutated ", 0.3f, 0.8f ),
    EXTREME_HILLS_PLUS_TREES_MUTATED( 162, "extreme_hills_plus_trees_mutated", "Extreme Hills Plus Trees Mutated ", 0.2f, 0.3f ),
    SAVANNA_MUTATED( 163, "savanna_mutated", "Savanna Mutated ", 1.1f, 0.5f ),
    SAVANNA_PLATEAU_MUTATED( 164, "savanna_plateau_mutated", "Savanna Plateau Mutated ", 1.0f, 0.5f ),
    MESA_BRYCE( 165, "mesa_bryce", "Mesa Bryce ", 2.0f, 0.0f ),
    MESA_PLATEAU_STONE_MUTATED( 166, "mesa_plateau_stone_mutated", "Mesa Plateau Stone Mutated ", 2.0f, 0.0f ),
    MESA_PLATEAU_MUTATED( 167, "mesa_plateau_mutated", "Mesa Plateau Mutated ", 2.0f, 0.0f ),
    SOULSAND_VALLEY( 178, "soulsand_valley", "Soulsand Valley ", 2.0f, 0.0f ),
    CRIMSON_FOREST( 179, "crimson_forest", "Crimson Forest ", 2.0f, 0.0f ),
    WARPED_FOREST( 180, "warped_forest", "Warped Forest ", 2.0f, 0.0f ),
    BASALT_DELTAS( 181, "basalt_deltas", "Basalt Deltas ", 2.0f, 0.0f ),
    JAGGED_PEAKS( 182, "jagged_peaks", "Jagged Peaks ", -0.7f, 0.9f ),
    FROZEN_PEAKS( 183, "frozen_peaks", "Frozen Peaks ", -0.7f, 0.9f ),
    SNOWY_SLOPES( 184, "snowy_slopes", "Snowy Slopes ", -0.3f, 0.9f ),
    GROVE( 185, "grove", "Grove", -0.2f, 0.8f ),
    MEADOW( 186, "meadow", "Meadow", 0.3f, 0.8f ),
    LUSH_CAVES( 187, "lush_caves", "Lush Caves ", 0.9f, 0.0f ),
    DRIPSTONE_CAVES( 188, "dripstone_caves", "Dripstone Caves ", 0.2f, 0.0f ),
    STONY_PEAKS( 189, "stony_peaks", "Stony Peaks ", 1.0f, 0.3f );

    private final int id;
    private final String identifier;
    private final String name;
    private final float temperature;
    private final float downfall;

    private final int minElevation;
    private final int maxElevation;

    Biome( int id, String identifier, String name, float temperature, float downfall ) {
        this( id, identifier, name, temperature, downfall, 58, 74 );
    }

    Biome( int id, String identifier, String name, float temperature, float downfall, int minElevation, int maxElevation ) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.temperature = temperature;
        this.downfall = Utils.clamp( downfall, 0.0F, 1.0F );
        this.minElevation = minElevation;
        this.maxElevation = maxElevation;
    }

    public int getId() {
        return this.id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    public float getTemperature() {
        return this.temperature;
    }

    public float getDownfall() {
        return this.downfall;
    }

    public int getMinElevation() {
        return this.minElevation;
    }

    public int getMaxElevation() {
        return this.maxElevation;
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
