package org.jukeboxmc.world.generator.biome;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jukeboxmc.world.Biome;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BiomeGenerationRegistry {

    private static final Object2ObjectMap<Biome, BiomeGeneration> biomeBiomeGeneration = new Object2ObjectOpenHashMap<>();

    public static void init() {
        biomeBiomeGeneration.put( Biome.RIVER, new RiverBiome() );
        biomeBiomeGeneration.put( Biome.OCEAN, new OceanBiome() );
        biomeBiomeGeneration.put( Biome.DEEP_OCEAN, new DeepOceanBiome() );

        biomeBiomeGeneration.put( Biome.PLAINS, new PlainsBiome() );
        biomeBiomeGeneration.put( Biome.ICE_PLAINS, new IcePlainsBiome() );
        biomeBiomeGeneration.put( Biome.ICE_PLAINS_SPIKES, new IcePlainsSpikesBiome() );

        biomeBiomeGeneration.put( Biome.JUNGLE, new JungleBiome() );
        biomeBiomeGeneration.put( Biome.JUNGLE_EDGE, new JungleEdgeBiome() );
        biomeBiomeGeneration.put( Biome.JUNGLE_HILLS, new JungleHillsBiome() );
        biomeBiomeGeneration.put( Biome.JUNGLE_MUTATED, new JungleMutatedBiome() );
        biomeBiomeGeneration.put( Biome.JUNGLE_EDGE_MUTATED, new JungleEdgeMutatedBiome() );

        biomeBiomeGeneration.put( Biome.SWAMPLAND, new SwamplandBiome() );
        biomeBiomeGeneration.put( Biome.SWAMPLAND_MUTATED, new SwamplandMutatedBiome() );

        biomeBiomeGeneration.put( Biome.TAIGA, new TaigaBiome() );
        biomeBiomeGeneration.put( Biome.MEGA_TAIGA, new MegaTaigaBiome() );
        biomeBiomeGeneration.put( Biome.TAIGA_MUTATED, new TaigaMutatedBiome() );
        biomeBiomeGeneration.put( Biome.TAIGA_HILLS, new TaigaHillsBiome() );
        biomeBiomeGeneration.put( Biome.COLD_TAIGA, new ColdTaigaBiome() );
        biomeBiomeGeneration.put( Biome.COLD_TAIGA_HILLS, new ColdTaigaHillsBiome() );
        biomeBiomeGeneration.put( Biome.COLD_TAIGA_MUTATED, new ColdTaigaMutatedBiome() );

        biomeBiomeGeneration.put( Biome.FOREST, new ForestBiome() );
        biomeBiomeGeneration.put( Biome.FOREST_HILLS, new ForestHillsBiome() );
        biomeBiomeGeneration.put( Biome.ROOFED_FOREST, new RoofedForestBiome() );
        biomeBiomeGeneration.put( Biome.ROOFED_FOREST_MUTATED, new RoofedForestMutatedBiome() );

        biomeBiomeGeneration.put( Biome.BIRCH_FOREST, new BirchForestBiome() );
        biomeBiomeGeneration.put( Biome.BIRCH_FOREST_MUTATED, new BirchForestMutatedBiome() );
        biomeBiomeGeneration.put( Biome.BIRCH_FOREST_HILLS, new BirchForestHillsBiome() );
        biomeBiomeGeneration.put( Biome.BIRCH_FOREST_HILLS_MUTATED, new BirchForestHillsMutatedBiome() );

        biomeBiomeGeneration.put( Biome.EXTREME_HILLS, new ExtremeHillsBiome() );
        biomeBiomeGeneration.put( Biome.EXTREME_HILLS_PLUS_TREES, new ExtremeHillsPlusTreesBiome() );

        biomeBiomeGeneration.put( Biome.SAVANNA, new SavannaBiome() );
        biomeBiomeGeneration.put( Biome.SAVANNA_PLATEAU, new SavannaPlateauBiome() );
        biomeBiomeGeneration.put( Biome.SAVANNA_MUTATED, new SavannaMutatedBiome() );
        biomeBiomeGeneration.put( Biome.SAVANNA_PLATEAU_MUTATED, new SavannaPlateauMutatedBiome() );

        biomeBiomeGeneration.put( Biome.BEACH, new BeachBiome() );
        biomeBiomeGeneration.put( Biome.DESERT, new DesertBiome() );
        biomeBiomeGeneration.put( Biome.DESERT_HILLS, new DesertHillsBiome() );
        biomeBiomeGeneration.put( Biome.DESERT_MUTATED, new DesertMutatedBiome() );

    }

    public static BiomeGeneration getBiomeGenerator(Biome biome) {
        return biomeBiomeGeneration.get( biome );
    }
}
