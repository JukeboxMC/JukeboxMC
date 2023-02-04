package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.world.generator.object.tree.Tree;
import org.jukeboxmc.world.generator.populator.EmeraldOrePopulator;
import org.jukeboxmc.world.generator.populator.TreePopulator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ExtremeHillsBiome extends BiomePopulator {

    public ExtremeHillsBiome() {
        this.addPopulator( new EmeraldOrePopulator() );

        TreePopulator treePopulator = new TreePopulator( Tree.TreeType.SPRUCE );
        treePopulator.setBaseAmount( 2 );
        treePopulator.setRandomAmount( 2 );
        this.addPopulator( treePopulator );
    }
}
