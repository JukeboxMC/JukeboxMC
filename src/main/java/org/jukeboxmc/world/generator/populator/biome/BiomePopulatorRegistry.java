package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.Biome;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BiomePopulatorRegistry {

    private static final Map<Biome, BiomePopulator> POPULATOR_MAP = new HashMap<>();

    public static void init() {
        register( Biome.RIVER, new RiverBiome() );
        register( Biome.OCEAN, new OceanBiome() );
        register( Biome.DEEP_OCEAN, new DeepOceanBiome() );

        register( Biome.PLAINS, new PlainsBiome() );
        register( Biome.SUNFLOWER_PLAINS, new SunflowerPlainsBiome() );

        //register( Biome.ICE_PLAINS, new IcePlainsBiome() );
        //register( Biome.ICE_PLAINS_SPIKES, new IcePlainsSpikesBiome() );

        register( Biome.JUNGLE, new JungleBiome() );
        register( Biome.JUNGLE_EDGE, new JungleBiome() );
        register( Biome.JUNGLE_HILLS, new JungleBiome() );
        register( Biome.JUNGLE_MUTATED, new JungleBiome() );
        register( Biome.JUNGLE_EDGE_MUTATED, new JungleBiome() );

        register( Biome.SWAMPLAND, new SwamplandBiome() );
        register( Biome.SWAMPLAND_MUTATED, new SwamplandBiome() );

        register( Biome.TAIGA, new TaigaBiome() );
        register( Biome.TAIGA_MUTATED, new TaigaBiome() );
        register( Biome.TAIGA_HILLS, new TaigaBiome() );
        register( Biome.MEGA_TAIGA, new MegaTaigaBiome() );
        register( Biome.COLD_TAIGA, new ColdTaigaBiome() );
        register( Biome.COLD_TAIGA_HILLS, new ColdTaigaBiome() );
        register( Biome.COLD_TAIGA_MUTATED, new ColdTaigaBiome() );

        register( Biome.FLOWER_FOREST, new FlowerForestBiome() );
        register( Biome.FOREST, new ForestBiome() );
        register( Biome.FOREST_HILLS, new ForestBiome() );
        register( Biome.ROOFED_FOREST, new RoofedForestBiome() );
        register( Biome.ROOFED_FOREST_MUTATED, new RoofedForestBiome() );

        register( Biome.BIRCH_FOREST, new BirchForestBiome() );
        register( Biome.BIRCH_FOREST_MUTATED, new BirchForestMutatedBiome() );
        register( Biome.BIRCH_FOREST_HILLS, new BirchForestBiome() );
        register( Biome.BIRCH_FOREST_HILLS_MUTATED, new BirchForestHillsMutatedBiome() );

        register( Biome.EXTREME_HILLS, new ExtremeHillsBiome() );
        register( Biome.EXTREME_HILLS_PLUS_TREES, new ExtremeHillsBiome() );

        register( Biome.SAVANNA, new SavannaBiome() );
        register( Biome.SAVANNA_PLATEAU, new SavannaBiome() );
        register( Biome.SAVANNA_MUTATED, new SavannaBiome() );
        register( Biome.SAVANNA_PLATEAU_MUTATED, new SavannaBiome() );

        register( Biome.BEACH, new BeachBiome() );
        register( Biome.COLD_BEACH, new ColdBeachBiome() );
        register( Biome.DESERT, new DesertBiome() );
        register( Biome.DESERT_HILLS, new DesertBiome() );
        register( Biome.DESERT_MUTATED, new DesertBiome() );
    }

    public static void register( Biome biome, BiomePopulator biomePopulator ) {
        POPULATOR_MAP.put( biome, biomePopulator );
    }

    public static BiomePopulator getBiomePopulator( Biome biome ) {
        return POPULATOR_MAP.get( biome );
    }
}
