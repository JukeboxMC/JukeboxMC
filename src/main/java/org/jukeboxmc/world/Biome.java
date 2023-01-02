package org.jukeboxmc.world;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Biome {

    OCEAN( 0, "ocean", "Ocean" ),
    PLAINS( 1, "plains", "Plains" ),
    DESERT( 2, "desert", "Desert" ),
    EXTREME_HILLS( 3, "extreme_hills", "Extreme Hills" ),
    FOREST( 4, "forest", "Forest" ),
    TAIGA( 5, "taiga", "Taiga" ),
    SWAMPLAND( 6, "swampland", "Swampland" ),
    RIVER( 7, "river", "River" ),
    HELL( 8, "hell", "Hell" ),
    THE_END( 9, "the_end", "The End " ),
    LEGACY_FROZEN_OCEAN( 10, "legacy_frozen_ocean", "Legacy Frozen Ocean " ),
    FROZEN_RIVER( 11, "frozen_river", "Frozen River " ),
    ICE_PLAINS( 12, "ice_plains", "Ice Plains " ),
    ICE_MOUNTAINS( 13, "ice_mountains", "Ice Mountains " ),
    MUSHROOM_ISLAND( 14, "mushroom_island", "Mushroom Island " ),
    MUSHROOM_ISLAND_SHORE( 15, "mushroom_island_shore", "Mushroom Island Shore " ),
    BEACH( 16, "beach", "Beach" ),
    DESERT_HILLS( 17, "desert_hills", "Desert Hills " ),
    FOREST_HILLS( 18, "forest_hills", "Forest Hills " ),
    TAIGA_HILLS( 19, "taiga_hills", "Taiga Hills " ),
    EXTREME_HILLS_EDGE( 20, "extreme_hills_edge", "Extreme Hills Edge " ),
    JUNGLE( 21, "jungle", "Jungle" ),
    JUNGLE_HILLS( 22, "jungle_hills", "Jungle Hills " ),
    JUNGLE_EDGE( 23, "jungle_edge", "Jungle Edge " ),
    DEEP_OCEAN( 24, "deep_ocean", "Deep Ocean " ),
    STONE_BEACH( 25, "stone_beach", "Stone Beach " ),
    COLD_BEACH( 26, "cold_beach", "Cold Beach " ),
    BIRCH_FOREST( 27, "birch_forest", "Birch Forest " ),
    BIRCH_FOREST_HILLS( 28, "birch_forest_hills", "Birch Forest Hills " ),
    ROOFED_FOREST( 29, "roofed_forest", "Roofed Forest " ),
    COLD_TAIGA( 30, "cold_taiga", "Cold Taiga " ),
    COLD_TAIGA_HILLS( 31, "cold_taiga_hills", "Cold Taiga Hills " ),
    MEGA_TAIGA( 32, "mega_taiga", "Mega Taiga " ),
    MEGA_TAIGA_HILLS( 33, "mega_taiga_hills", "Mega Taiga Hills " ),
    EXTREME_HILLS_PLUS_TREES( 34, "extreme_hills_plus_trees", "Extreme Hills Plus Trees " ),
    SAVANNA( 35, "savanna", "Savanna" ),
    SAVANNA_PLATEAU( 36, "savanna_plateau", "Savanna Plateau " ),
    MESA( 37, "mesa", "Mesa" ),
    MESA_PLATEAU_STONE( 38, "mesa_plateau_stone", "Mesa Plateau Stone " ),
    MESA_PLATEAU( 39, "mesa_plateau", "Mesa Plateau " ),
    WARM_OCEAN( 40, "warm_ocean", "Warm Ocean " ),
    DEEP_WARM_OCEAN( 41, "deep_warm_ocean", "Deep Warm Ocean " ),
    LUKEWARM_OCEAN( 42, "lukewarm_ocean", "Lukewarm Ocean " ),
    DEEP_LUKEWARM_OCEAN( 43, "deep_lukewarm_ocean", "Deep Lukewarm Ocean " ),
    COLD_OCEAN( 44, "cold_ocean", "Cold Ocean " ),
    DEEP_COLD_OCEAN( 45, "deep_cold_ocean", "Deep Cold Ocean " ),
    FROZEN_OCEAN( 46, "frozen_ocean", "Frozen Ocean " ),
    DEEP_FROZEN_OCEAN( 47, "deep_frozen_ocean", "Deep Frozen Ocean " ),
    BAMBOO_JUNGLE( 48, "bamboo_jungle", "Bamboo Jungle " ),
    BAMBOO_JUNGLE_HILLS( 49, "bamboo_jungle_hills", "Bamboo Jungle Hills " ),
    SUNFLOWER_PLAINS( 129, "sunflower_plains", "Sunflower Plains " ),
    DESERT_MUTATED( 130, "desert_mutated", "Desert Mutated " ),
    EXTREME_HILLS_MUTATED( 131, "extreme_hills_mutated", "Extreme Hills Mutated " ),
    FLOWER_FOREST( 132, "flower_forest", "Flower Forest " ),
    TAIGA_MUTATED( 133, "taiga_mutated", "Taiga Mutated " ),
    SWAMPLAND_MUTATED( 134, "swampland_mutated", "Swampland Mutated " ),
    ICE_PLAINS_SPIKES( 140, "ice_plains_spikes", "Ice Plains Spikes " ),
    JUNGLE_MUTATED( 149, "jungle_mutated", "Jungle Mutated " ),
    JUNGLE_EDGE_MUTATED( 151, "jungle_edge_mutated", "Jungle Edge Mutated " ),
    BIRCH_FOREST_MUTATED( 155, "birch_forest_mutated", "Birch Forest Mutated " ),
    BIRCH_FOREST_HILLS_MUTATED( 156, "birch_forest_hills_mutated", "Birch Forest Hills Mutated " ),
    ROOFED_FOREST_MUTATED( 157, "roofed_forest_mutated", "Roofed Forest Mutated " ),
    COLD_TAIGA_MUTATED( 158, "cold_taiga_mutated", "Cold Taiga Mutated " ),
    REDWOOD_TAIGA_MUTATED( 160, "redwood_taiga_mutated", "Redwood Taiga Mutated " ),
    REDWOOD_TAIGA_HILLS_MUTATED( 161, "redwood_taiga_hills_mutated", "Redwood Taiga Hills Mutated " ),
    EXTREME_HILLS_PLUS_TREES_MUTATED( 162, "extreme_hills_plus_trees_mutated", "Extreme Hills Plus Trees Mutated " ),
    SAVANNA_MUTATED( 163, "savanna_mutated", "Savanna Mutated " ),
    SAVANNA_PLATEAU_MUTATED( 164, "savanna_plateau_mutated", "Savanna Plateau Mutated " ),
    MESA_BRYCE( 165, "mesa_bryce", "Mesa Bryce " ),
    MESA_PLATEAU_STONE_MUTATED( 166, "mesa_plateau_stone_mutated", "Mesa Plateau Stone Mutated " ),
    MESA_PLATEAU_MUTATED( 167, "mesa_plateau_mutated", "Mesa Plateau Mutated " ),
    SOULSAND_VALLEY( 178, "soulsand_valley", "Soulsand Valley " ),
    CRIMSON_FOREST( 179, "crimson_forest", "Crimson Forest " ),
    WARPED_FOREST( 180, "warped_forest", "Warped Forest " ),
    BASALT_DELTAS( 181, "basalt_deltas", "Basalt Deltas " ),
    JAGGED_PEAKS( 182, "jagged_peaks", "Jagged Peaks " ),
    FROZEN_PEAKS( 183, "frozen_peaks", "Frozen Peaks " ),
    SNOWY_SLOPES( 184, "snowy_slopes", "Snowy Slopes " ),
    GROVE( 185, "grove", "Grove" ),
    MEADOW( 186, "meadow", "Meadow" ),
    LUSH_CAVES( 187, "lush_caves", "Lush Caves " ),
    DRIPSTONE_CAVES( 188, "dripstone_caves", "Dripstone Caves " ),
    STONY_PEAKS( 189, "stony_peaks", "Stony Peaks " );

    private final int id;
    private final String identifier;
    private final String name;

    private final static Int2ObjectMap<Biome> BIOME_FROM_ID = new Int2ObjectOpenHashMap<>();

    Biome( int id, String identifier, String name ) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
    }

    public static void init() {
        for ( Biome biome : Biome.values() ) {
            BIOME_FROM_ID.put( biome.getId(), biome );
        }
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

    public static Biome findById( int id ) {
        return BIOME_FROM_ID.get( id );
    }
}
