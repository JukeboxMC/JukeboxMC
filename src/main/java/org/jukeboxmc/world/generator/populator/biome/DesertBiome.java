package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.CactusPopulator;
import org.jukeboxmc.world.generator.populator.DeadBushPopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class DesertBiome extends BiomePopulator {

    public DesertBiome() {
        CactusPopulator cactusPopulator = new CactusPopulator();
        cactusPopulator.setBaseAmount( 2 );
        this.addPopulator( cactusPopulator );

        DeadBushPopulator deadBushPopulator = new DeadBushPopulator();
        deadBushPopulator.setBaseAmount( 2 );
        this.addPopulator( deadBushPopulator );
    }
}
