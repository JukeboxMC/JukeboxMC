package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.object.Tree;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TreePopulator implements Populator {

    private final Tree tree;
    private int randomAmount;
    private int baseAmount;

    public TreePopulator( Tree tree ) {
        this.tree = tree;
    }

    @Override
    public void populate( Random random, World world, Chunk chunk ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 15 );
            int z = random.nextInt( 15 );
            int y = this.highestWorkableBlock( chunk, x, z );

            if ( y != -1 ) {
                this.tree.grow( chunk, chunk.getChunkX() * 16 + x, y, chunk.getChunkZ() * 16 + z, random );
            }
        }

    }

    private int highestWorkableBlock( Chunk chunk, int x, int z ) {
        int y = 255;
        for ( ; y > 0; --y ) {
            Block block = chunk.getBlock( x, y, z, 0 );
            if ( block.getType() == BlockType.DIRT || block.getType() == BlockType.GRASS ) {
                break;
            } else if ( block.getType() != BlockType.AIR && block.getType() != BlockType.SNOW_LAYER ) {
                return -1;
            }
        }
        return y == 0 ? -1 : ++y;
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }

    private int randomRange( Random random, int start, int end ) {
        return start + random.nextInt() % ( end + 1 - start );
    }
}

