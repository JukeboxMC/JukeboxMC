package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.SavannaTreePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SavannaBiome extends GrassyBiome {

    public SavannaBiome() {
        SavannaTreePopulator savannaTreePopulator = new SavannaTreePopulator();
        savannaTreePopulator.setRandomAmount( 2 );
        this.addPopulator( savannaTreePopulator );
    }
}
