package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.world.generator.populator.Populator;
import org.jukeboxmc.world.generator.populator.SugarcanePopulator;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BeachBiome extends SandyBiome {

    @Override
    public List<Populator> getPopulators() {
        SugarcanePopulator sugarcanePopulator = new SugarcanePopulator();
        sugarcanePopulator.setBaseAmount( 10 );
        return List.of(sugarcanePopulator);
    }
}
