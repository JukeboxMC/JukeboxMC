package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.SunflowerPopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SunflowerPlainsBiome extends GrassyBiome {

    public SunflowerPlainsBiome() {
        SunflowerPopulator sunflowerPopulator = new SunflowerPopulator();
        sunflowerPopulator.setBaseAmount( 2 );
        sunflowerPopulator.setRandomAmount( 3 );
        this.addPopulator( sunflowerPopulator );
    }
}
