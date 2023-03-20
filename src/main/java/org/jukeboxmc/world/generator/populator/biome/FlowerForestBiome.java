package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.block.data.FlowerType;
import org.jukeboxmc.world.generator.populator.FlowerPopulator;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FlowerForestBiome extends ForestBiome {

    public FlowerForestBiome() {
        FlowerPopulator flowerPopulator = new FlowerPopulator();
        flowerPopulator.setFlowerTypes( List.of( FlowerType.values() ));
        flowerPopulator.setBaseAmount( 10 );
        this.addPopulator( flowerPopulator );
    }
}
