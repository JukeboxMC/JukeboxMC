package org.jukeboxmc.world.generator.populator.biome;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.generator.populator.*;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SwamplandBiome extends BiomePopulator {

    public SwamplandBiome() {
        DiskPopulator diskPopulator = new DiskPopulator( 1.0, Block.create( BlockType.CLAY ), 1, 2, 1, List.of( BlockType.DIRT, BlockType.CLAY ) );
        diskPopulator.setBaseAmount( 1 );
        this.addPopulator( diskPopulator );

        LilyPadPopulator lilyPadPopulator = new LilyPadPopulator();
        lilyPadPopulator.setBaseAmount( 4 );
        lilyPadPopulator.setRandomAmount( 2 );
        this.addPopulator( lilyPadPopulator );

        SeagrassPopulator seagrassPopulator = new SeagrassPopulator( 0.6 );
        seagrassPopulator.setRandomAmount( 32 );
        seagrassPopulator.setBaseAmount( 32 );
        this.addPopulator( seagrassPopulator );

        SwampTreePopulator swampTreePopulator = new SwampTreePopulator();
        swampTreePopulator.setBaseAmount( 2 );
        this.addPopulator( swampTreePopulator );

        TallGrassPopulator tallGrassPopulator = new TallGrassPopulator();
        tallGrassPopulator.setBaseAmount( 0 );
        tallGrassPopulator.setRandomAmount( 3 );
        this.addPopulator( tallGrassPopulator );

        YellowFlowerPopulator yellowFlowerPopulator = new YellowFlowerPopulator();
        yellowFlowerPopulator.setBaseAmount( 2 );
        this.addPopulator( yellowFlowerPopulator );

        //TODO MushroomPopulator
    }
}
