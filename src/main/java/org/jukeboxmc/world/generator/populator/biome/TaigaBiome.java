package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.TaigaTreePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TaigaBiome extends GrassyBiome {

    public TaigaBiome() {
        TaigaTreePopulator treePopulator = new TaigaTreePopulator();
        treePopulator.setBaseAmount( 10 );
        this.addPopulator( treePopulator );
    }
}
