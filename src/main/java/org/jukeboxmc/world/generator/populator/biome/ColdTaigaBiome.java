package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.populator.WaterIcePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ColdTaigaBiome extends TaigaBiome {

    public ColdTaigaBiome() {
        this.addPopulator( new WaterIcePopulator() );
    }
}
