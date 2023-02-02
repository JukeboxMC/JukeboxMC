package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.DarkOakTreePopulator;


/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RoofedForestBiome extends GrassyBiome {

    public RoofedForestBiome() {
        DarkOakTreePopulator darkOakTreePopulator = new DarkOakTreePopulator();
        darkOakTreePopulator.setBaseAmount( 20 );
        darkOakTreePopulator.setRandomAmount( 10 );
        this.addPopulator( darkOakTreePopulator );
    }
}
