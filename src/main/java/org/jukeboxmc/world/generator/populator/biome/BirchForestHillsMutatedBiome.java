package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.object.tree.Tree;
import org.jukeboxmc.world.generator.populator.TreePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BirchForestHillsMutatedBiome extends GrassyBiome {

    public BirchForestHillsMutatedBiome() {
        TreePopulator treePopulator = new TreePopulator( Tree.TreeType.BIRCH, true );
        treePopulator.setBaseAmount( 6 );
        this.addPopulator( treePopulator );
    }
}
