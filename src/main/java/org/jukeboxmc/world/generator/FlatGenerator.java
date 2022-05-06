package org.jukeboxmc.world.generator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBedrock;
import org.jukeboxmc.block.BlockDirt;
import org.jukeboxmc.block.BlockGrass;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FlatGenerator extends Generator {

    private final Block blockGrass;
    private final Block blockDirt;
    private final Block blockBedrock;

    public FlatGenerator() {
        this.blockGrass = new BlockGrass();
        this.blockDirt = new BlockDirt();
        this.blockBedrock = new BlockBedrock();
    }

    @Override
    public void generate( int chunkX, int chunkZ ) {
        Chunk chunk = this.getChunk( chunkX, chunkZ );
        for ( int blockX = 0; blockX < 16; blockX++ ) {
            for ( int blockZ = 0; blockZ < 16; blockZ++ ) {
                for ( int blockY = 0; blockY < 16; blockY++ ) {
                    chunk.setBiome( blockX, blockY, blockZ, Biome.PLAINS );
                }

                chunk.setBlock( blockX, 0, blockZ, 0, this.blockBedrock );
                chunk.setBlock( blockX, 1, blockZ, 0, this.blockDirt );
                chunk.setBlock( blockX, 2, blockZ, 0, this.blockDirt );
                chunk.setBlock( blockX, 3, blockZ, 0, this.blockGrass );
            }
        }
    }

    @Override
    public void populate( int chunkX, int chunkZ ) {

    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 0, 5, 0 );
    }
}
