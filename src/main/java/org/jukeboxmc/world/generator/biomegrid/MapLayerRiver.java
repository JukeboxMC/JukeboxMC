package org.jukeboxmc.world.generator.biomegrid;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.jukeboxmc.world.Biome;

public class MapLayerRiver extends MapLayer {

    private static final IntSet OCEANS = new IntOpenHashSet();
    private static final Int2IntMap SPECIAL_RIVERS = new Int2IntOpenHashMap();
    private static final int CLEAR_VALUE = 0;
    private static final int RIVER_VALUE = 1;

    static {
        OCEANS.add(Biome.OCEAN.getId());
        OCEANS.add( Biome.DEEP_OCEAN.getId());

        SPECIAL_RIVERS.put(Biome.ICE_PLAINS.getId(), Biome.FROZEN_RIVER.getId());
        SPECIAL_RIVERS.put(Biome.MUSHROOM_ISLAND.getId(), Biome.MUSHROOM_ISLAND_SHORE.getId());
        SPECIAL_RIVERS.put(Biome.MUSHROOM_ISLAND_SHORE.getId(), Biome.MUSHROOM_ISLAND_SHORE.getId());
    }

    private final MapLayer belowLayer;
    private final MapLayer mergeLayer;

    public MapLayerRiver(long seed, MapLayer belowLayer) {
        this(seed, belowLayer, null);
    }

    /**
     * Creates a map layer that generates rivers.
     *
     * @param seed the layer's PRNG seed
     * @param belowLayer the layer to apply before this one
     * @param mergeLayer
     */
    public MapLayerRiver(long seed, MapLayer belowLayer, MapLayer mergeLayer) {
        super(seed);
        this.belowLayer = belowLayer;
        this.mergeLayer = mergeLayer;
    }

    @Override
    public int[] generateValues(int x, int z, int sizeX, int sizeZ) {
        if (this.mergeLayer == null) {
            return generateRivers(x, z, sizeX, sizeZ);
        }
        return mergeRivers(x, z, sizeX, sizeZ);
    }

    private int[] generateRivers(int x, int z, int sizeX, int sizeZ) {
        int gridX = x - 1;
        int gridZ = z - 1;
        int gridSizeX = sizeX + 2;
        int gridSizeZ = sizeZ + 2;

        int[] values = this.belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);
        int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                // This applies rivers using Von Neumann neighborhood
                int centerVal = values[j + 1 + (i + 1) * gridSizeX] & 1;
                int upperVal = values[j + 1 + i * gridSizeX] & 1;
                int lowerVal = values[j + 1 + (i + 2) * gridSizeX] & 1;
                int leftVal = values[j + (i + 1) * gridSizeX] & 1;
                int rightVal = values[j + 2 + (i + 1) * gridSizeX] & 1;
                int val = CLEAR_VALUE;
                if (centerVal != upperVal || centerVal != lowerVal || centerVal != leftVal || centerVal != rightVal) {
                    val = RIVER_VALUE;
                }
                finalValues[j + i * sizeX] = val;
            }
        }
        return finalValues;
    }

    private int[] mergeRivers(int x, int z, int sizeX, int sizeZ) {
        int[] values = this.belowLayer.generateValues(x, z, sizeX, sizeZ);
        int[] mergeValues = this.mergeLayer.generateValues(x, z, sizeX, sizeZ);

        int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeX * sizeZ; i++) {
            int val = mergeValues[i];
            if (OCEANS.contains(mergeValues[i])) {
                val = mergeValues[i];
            } else if (values[i] == RIVER_VALUE) {
                if (SPECIAL_RIVERS.containsKey(mergeValues[i])) {
                    val = SPECIAL_RIVERS.get(mergeValues[i]);
                } else {
                    val = Biome.RIVER.getId();
                }
            }
            finalValues[i] = val;
        }

        return finalValues;
    }
}
