package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.object.tree.Tree;
import org.jukeboxmc.world.generator.populator.TreePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BirchForestBiome extends GrassyBiome {

    public BirchForestBiome() {
        TreePopulator treePopulator = new TreePopulator( Tree.TreeType.BIRCH );
        treePopulator.setBaseAmount( 6 );
        this.addPopulator( treePopulator );
    }
}
