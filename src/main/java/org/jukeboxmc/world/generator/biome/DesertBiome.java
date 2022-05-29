package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.world.generator.populator.CactusPopulator;
import org.jukeboxmc.world.generator.populator.DeadbushPopulator;
import org.jukeboxmc.world.generator.populator.Populator;

import java.util.Arrays;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class DesertBiome extends SandyBiome {

    @Override
    public List<Populator> getPopulators() {
        CactusPopulator cactusPopulator = new CactusPopulator();
        cactusPopulator.setRandomAmount( 1 );

        DeadbushPopulator deadbushPopulator = new DeadbushPopulator();
        deadbushPopulator.setRandomAmount( 1 );
        return Arrays.asList( cactusPopulator, deadbushPopulator );
    }
}
