package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.TallGrassPopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GrassyBiome extends BiomePopulator{

    public GrassyBiome() {
        TallGrassPopulator tallGrassPopulator = new TallGrassPopulator();
        tallGrassPopulator.setBaseAmount( 30 );
        this.addPopulator( tallGrassPopulator );
    }
}
