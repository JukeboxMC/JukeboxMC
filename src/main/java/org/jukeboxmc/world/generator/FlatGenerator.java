package org.jukeboxmc.world.generator;

import org.jukeboxmc.block.BlockBedrock;
import org.jukeboxmc.block.BlockDirt;
import org.jukeboxmc.block.BlockGrass;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FlatGenerator extends WorldGenerator {

    private BlockGrass blockGrass;
    private BlockDirt blockDirt;
    private BlockBedrock blockBedrock;

    public FlatGenerator() {
        this.blockGrass = new BlockGrass();
        this.blockDirt = new BlockDirt();
        this.blockBedrock = new BlockBedrock();
    }

    @Override
    public void generate( Chunk chunk ) {

        for ( int blockX = 0; blockX < 16; blockX++ ) {
            for ( int blockZ = 0; blockZ < 16; blockZ++ ) {
                chunk.setBiome( blockX, blockZ, Biome.PLAINS );

                chunk.setBlock( blockX, 0, blockZ, 0, this.blockBedrock );
                chunk.setBlock( blockX, 1, blockZ, 0, this.blockDirt );
                chunk.setBlock( blockX, 2, blockZ, 0, this.blockDirt );
                chunk.setBlock( blockX, 3, blockZ, 0, this.blockGrass );
            }
        }
    }
}
