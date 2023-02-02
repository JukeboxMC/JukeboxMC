package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.SmallJungleTreePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class JungleBiome extends GrassyBiome {

    public JungleBiome() {
        SmallJungleTreePopulator smallJungleTreePopulator = new SmallJungleTreePopulator();
        smallJungleTreePopulator.setBaseAmount( 10 );
        this.addPopulator( smallJungleTreePopulator );
    }
}
