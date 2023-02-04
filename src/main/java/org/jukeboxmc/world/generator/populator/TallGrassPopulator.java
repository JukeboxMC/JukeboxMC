package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TallGrassPopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private final Block BLOCK_TALLGRASS = Block.create( BlockType.TALLGRASS );

    @Override
    public void populate( Random random, World world, PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 16 );
            int z = random.nextInt( 16 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            if ( y != -1 && this.canTallGrassStay( chunk, x, y, z ) ) {
                chunk.setBlock( x, y, z, 0, BLOCK_TALLGRASS );
            }
        }
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }

    private boolean canTallGrassStay( Chunk chunk, int x, int y, int z ) {
        Block block = chunk.getBlock( x, y, z, 0 );
        return ( block.getType() == BlockType.AIR || block.getType() == BlockType.SNOW_LAYER ) && this.blockBelow( chunk, x, y, z, BlockType.GRASS );
    }
}
