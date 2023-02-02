package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.SugarcanePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BeachBiome extends BiomePopulator {

    public BeachBiome() {
        SugarcanePopulator sugarcanePopulator = new SugarcanePopulator();
        sugarcanePopulator.setBaseAmount( 0 );
        sugarcanePopulator.setRandomAmount( 20 );
        this.addPopulator( sugarcanePopulator );
    }
}
