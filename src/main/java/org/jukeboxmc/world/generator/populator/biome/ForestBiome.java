package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.object.tree.Tree;
import org.jukeboxmc.world.generator.populator.TreePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ForestBiome extends GrassyBiome {

    public ForestBiome() {
        TreePopulator treePopulator = new TreePopulator( Tree.TreeType.BIRCH );
        treePopulator.setBaseAmount( 3 );
        this.addPopulator( treePopulator );

        treePopulator = new TreePopulator( Tree.TreeType.OAK );
        treePopulator.setBaseAmount( 3 );
        this.addPopulator( treePopulator );
    }
}
