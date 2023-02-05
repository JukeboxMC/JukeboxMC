package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.WaterIcePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ColdBeachBiome extends BeachBiome {

    public ColdBeachBiome() {
        this.addPopulator( new WaterIcePopulator() );
    }
}
