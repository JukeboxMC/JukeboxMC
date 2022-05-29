package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockSugarCane;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.BlockWater;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SugarcanePopulator implements Populator {

    private int randomAmount;
    private int baseAmount;

    @Override
    public void populate( Random random, World world, Chunk chunk ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 15 );
            int z = random.nextInt( 15 );
            int y = this.getHighestWorkableBlock( x, z, chunk ) - 1;

            if ( y > 0 ) {
                Block block = chunk.getBlock( x, y, z, 0 );
                if ( block.getType().equals( BlockType.GRASS ) || block.getType().equals( BlockType.DIRT ) || block.getType().equals( BlockType.SAND ) ) {
                    Block blockNorth = chunk.getBlock( x, y, z - 1, 0 );
                    Block blockEast = chunk.getBlock( x + 1, y, z, 0 );
                    Block blockSouth = chunk.getBlock( x, y, z + 1, 0 );
                    Block blockWest = chunk.getBlock( x - 1, y, z, 0 );
                    if ( ( blockNorth instanceof BlockWater ) || ( blockEast instanceof BlockWater ) || ( blockSouth instanceof BlockWater ) || ( blockWest instanceof BlockWater ) ) {
                        chunk.setBlock( x, y+ 1, z, 0, new BlockSugarCane() );
                    }
                }
            }
        }
    }

    protected int getHighestWorkableBlock( int x, int z, Chunk chunk ) {
        int y = 255;
        for ( ; y > 0; --y ) {
            Block block = chunk.getBlock( x, y, z, 0 );
            if ( block.getType().equals( BlockType.WATER ) ) {
                return -1;
            }
            if ( block.getType().equals( BlockType.SAND ) || block.getType().equals( BlockType.GRASS ) ) {
                break;
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
}
