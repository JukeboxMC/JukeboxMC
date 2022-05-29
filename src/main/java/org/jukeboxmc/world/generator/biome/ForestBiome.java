package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.world.generator.object.BirchTree;
import org.jukeboxmc.world.generator.object.OakTree;
import org.jukeboxmc.world.generator.populator.Populator;
import org.jukeboxmc.world.generator.populator.TallGrassPopulator;
import org.jukeboxmc.world.generator.populator.TreePopulator;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ForestBiome extends GrassyBiome {

    @Override
    public List<Populator> getPopulators() {
        TreePopulator oakTreePopulator = new TreePopulator( new OakTree() );
        oakTreePopulator.setBaseAmount( 5 );

        TreePopulator birchPopulator = new TreePopulator( new BirchTree() );
        birchPopulator.setBaseAmount( 5 );

        TallGrassPopulator tallGrassPopulator = new TallGrassPopulator();
        tallGrassPopulator.setBaseAmount( 20 );
        return List.of( tallGrassPopulator, oakTreePopulator, birchPopulator );
    }
}
