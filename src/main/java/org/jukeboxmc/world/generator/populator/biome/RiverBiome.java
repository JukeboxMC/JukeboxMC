package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.generator.populator.DiskPopulator;
import org.jukeboxmc.world.generator.populator.SeagrassPopulator;
import org.jukeboxmc.world.generator.populator.SugarcanePopulator;

import java.util.Arrays;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RiverBiome extends BiomePopulator {

    public RiverBiome() {
        DiskPopulator populatorDiskSand = new DiskPopulator( 1.0, Block.create( BlockType.SAND ), 2, 4, 2, Arrays.asList( BlockType.GRASS, BlockType.DIRT ) );
        populatorDiskSand.setBaseAmount( 3 );
        this.addPopulator( populatorDiskSand );

        DiskPopulator populatorDiskClay = new DiskPopulator( 1.0, Block.create( BlockType.CLAY ), 1, 2, 1, Arrays.asList( BlockType.DIRT, BlockType.CLAY ) );
        populatorDiskClay.setBaseAmount( 1 );
        this.addPopulator( populatorDiskClay );

        DiskPopulator populatorDiskGravel = new DiskPopulator( 1.0, Block.create( BlockType.GRAVEL ), 2, 3, 2, Arrays.asList( BlockType.GRASS, BlockType.DIRT ) );
        populatorDiskGravel.setBaseAmount( 1 );
        this.addPopulator( populatorDiskGravel );

        SeagrassPopulator seagrassPopulator = new SeagrassPopulator( 0.4 );
        seagrassPopulator.setBaseAmount( 24 );
        seagrassPopulator.setRandomAmount( 24 );
        this.addPopulator( seagrassPopulator );

        SugarcanePopulator sugarcanePopulator = new SugarcanePopulator();
        sugarcanePopulator.setBaseAmount( 0 );
        sugarcanePopulator.setRandomAmount( 20 );
        this.addPopulator( sugarcanePopulator );
    }
}
