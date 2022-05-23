package org.jukeboxmc.world.generator.biome;

/**
 * @author LucGamesYT
 * @version 1.0
 */
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.generator.noise.bukkit.SimplexOctaveGenerator;

import java.util.Random;

public class BiomeClimate {

    private static final Int2ObjectMap<Climate> CLIMATE_MAP = new Int2ObjectOpenHashMap<>();
    private static final SimplexOctaveGenerator noiseGen;

    static {
        int[] biomes = new int[Biome.values().length];
        for (int i = 0; i < biomes.length; i++) {
            biomes[i] = Biome.values()[i].getId();
        }
        setBiomeClimate( Climate.DEFAULT, biomes);
        setBiomeClimate( Climate.PLAINS, Biome.PLAINS.getId(), Biome.SUNFLOWER_PLAINS.getId(), Biome.BEACH.getId());
        setBiomeClimate( Climate.DESERT, Biome.DESERT.getId(), Biome.DESERT_HILLS.getId(), Biome.DESERT_MUTATED.getId(), Biome.MESA.getId(), Biome.MESA_BRYCE.getId(), Biome.MESA_PLATEAU.getId(), Biome.MESA_PLATEAU_STONE.getId(), Biome.MESA_PLATEAU_MUTATED.getId(), Biome.MESA_PLATEAU_STONE_MUTATED.getId(), Biome.HELL.getId());
        setBiomeClimate( Climate.EXTREME_HILLS, Biome.EXTREME_HILLS.getId(), Biome.EXTREME_HILLS_PLUS_TREES.getId(), Biome.EXTREME_HILLS_MUTATED.getId(), Biome.EXTREME_HILLS_PLUS_TREES_MUTATED.getId(), Biome.STONE_BEACH.getId(), Biome.EXTREME_HILLS_EDGE.getId());
        setBiomeClimate( Climate.FOREST, Biome.FOREST.getId(), Biome.FOREST_HILLS.getId(), Biome.FLOWER_FOREST.getId(), Biome.ROOFED_FOREST.getId(), Biome.ROOFED_FOREST_MUTATED.getId());
        setBiomeClimate( Climate.BIRCH_FOREST, Biome.BIRCH_FOREST.getId(), Biome.BIRCH_FOREST_HILLS.getId(), Biome.BIRCH_FOREST_MUTATED.getId(), Biome.BIRCH_FOREST_HILLS_MUTATED.getId());
        setBiomeClimate( Climate.TAIGA, Biome.TAIGA.getId(), Biome.TAIGA_HILLS.getId(), Biome.TAIGA_MUTATED.getId(), Biome.REDWOOD_TAIGA_MUTATED.getId());//, Biome.MEGA_SPRUCE_TAIGA_HILLS.id
        setBiomeClimate( Climate.SWAMPLAND, Biome.SWAMPLAND.getId(), Biome.SWAMPLAND_MUTATED.getId());
        setBiomeClimate( Climate.ICE_PLAINS, Biome.ICE_PLAINS.getId(), Biome.ICE_PLAINS_SPIKES.getId(), Biome.FROZEN_RIVER.getId(), Biome.FROZEN_OCEAN.getId());//, Biome.ICE_MOUNTAINS.id
        setBiomeClimate( Climate.MUSHROOM, Biome.MUSHROOM_ISLAND.getId(), Biome.MUSHROOM_ISLAND_SHORE.getId());
        setBiomeClimate( Climate.COLD_BEACH, Biome.COLD_BEACH.getId());
        setBiomeClimate( Climate.JUNGLE, Biome.JUNGLE.getId(), Biome.JUNGLE_HILLS.getId(), Biome.JUNGLE_MUTATED.getId());
        setBiomeClimate( Climate.JUNGLE_EDGE, Biome.JUNGLE_EDGE.getId(), Biome.JUNGLE_EDGE_MUTATED.getId());
        setBiomeClimate( Climate.COLD_TAIGA, Biome.COLD_TAIGA.getId(), Biome.COLD_TAIGA_HILLS.getId(), Biome.COLD_TAIGA_MUTATED.getId());
        setBiomeClimate( Climate.MEGA_TAIGA, Biome.MEGA_TAIGA.getId(), Biome.MEGA_TAIGA_HILLS.getId());
        setBiomeClimate( Climate.SAVANNA, Biome.SAVANNA.getId());
        setBiomeClimate( Climate.SAVANNA_MOUNTAINS, Biome.SAVANNA_MUTATED.getId());
        setBiomeClimate( Climate.SAVANNA_PLATEAU, Biome.SAVANNA_PLATEAU.getId());
        setBiomeClimate( Climate.SAVANNA_PLATEAU_MOUNTAINS, Biome.SAVANNA_PLATEAU_MUTATED.getId());
        setBiomeClimate( Climate.SKY);//, Biome.THE_END.id, Biome.SMALL_END_ISLANDS.id, Biome.END_MIDLANDS.id, Biome.END_HIGHLANDS.id, Biome.END_BARRENS.id

        noiseGen = new SimplexOctaveGenerator(new Random(1234), 1);
        noiseGen.setScale(1 / 8.0D);
    }

    public static double getTemperature(int biome) {
        return CLIMATE_MAP.get(biome).getTemperature();
    }

    public static double getHumidity(int biome) {
        return CLIMATE_MAP.get(biome).getHumidity();
    }

    public static boolean isWet(int biome) {
        return getHumidity(biome) > 0.85D;
    }

    public static boolean isCold(int biome, int x, int y, int z) {
        return getVariatedTemperature(biome, x, y, z) < 0.15D;
    }

    public static boolean isRainy(int biome, int x, int y, int z) {
        boolean rainy = CLIMATE_MAP.get(biome).isRainy();
        return rainy && !isCold(biome, x, y, z);
    }

    public static boolean isSnowy(int biome, int x, int y, int z) {
        boolean rainy = CLIMATE_MAP.get(biome).isRainy();
        return rainy && isCold(biome, x, y, z);
    }

    private static double getVariatedTemperature(int biome, int x, int y, int z) {
        double temp = CLIMATE_MAP.get(biome).getTemperature();
        if (y > 64) {
            double variation = noiseGen.noise(x, z, 0.5D, 2.0D) * 4.0D;
            return temp - (variation + (y - 64)) * 0.05D / 30.0D;
        } else {
            return temp;
        }
    }

    private static void setBiomeClimate(Climate temp, int... biomes) {
        for (int biome : biomes) {
            CLIMATE_MAP.put(biome, temp);
        }
    }

    private static class Climate {

        public static final Climate DEFAULT = new Climate(0.5D, 0.5D, true);
        public static final Climate PLAINS = new Climate(0.8D, 0.4D, true);
        public static final Climate DESERT = new Climate(2.0D, 0.0D, false);
        public static final Climate EXTREME_HILLS = new Climate(0.2D, 0.3D, true);
        public static final Climate FOREST = new Climate(0.7D, 0.8D, true);
        public static final Climate BIRCH_FOREST = new Climate(0.6D, 0.6D, true);
        public static final Climate TAIGA = new Climate(0.25D, 0.8D, true);
        public static final Climate SWAMPLAND = new Climate(0.8D, 0.9D, true);
        public static final Climate ICE_PLAINS = new Climate(0.0D, 0.5D, true);
        public static final Climate MUSHROOM = new Climate(0.9D, 1.0D, true);
        public static final Climate COLD_BEACH = new Climate(0.05D, 0.3D, true);
        public static final Climate JUNGLE = new Climate(0.95D, 0.9D, true);
        public static final Climate JUNGLE_EDGE = new Climate(0.95D, 0.8D, true);
        public static final Climate COLD_TAIGA = new Climate(-0.5D, 0.4D, true);
        public static final Climate MEGA_TAIGA = new Climate(0.3D, 0.8D, true);
        public static final Climate SAVANNA = new Climate(1.2D, 0.0D, false);
        public static final Climate SAVANNA_MOUNTAINS = new Climate(1.1D, 0.0D, false);
        public static final Climate SAVANNA_PLATEAU = new Climate(1.0D, 0.0D, false);
        public static final Climate SAVANNA_PLATEAU_MOUNTAINS = new Climate(0.5D, 0.0D, false);
        public static final Climate SKY = new Climate(0.5D, 0.5D, false);

        private final double temperature;
        private final double humidity;
        private final boolean rainy;

        Climate(double temperature, double humidity, boolean rainy) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.rainy = rainy;
        }

        public double getTemperature() {
            return this.temperature;
        }

        public double getHumidity() {
            return this.humidity;
        }

        public boolean isRainy() {
            return this.rainy;
        }
    }
}
