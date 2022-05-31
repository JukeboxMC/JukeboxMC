package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCactus;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CactusPopulator implements Populator {

    private int randomAmount;
    private int baseAmount;

    @Override
    public void populate( Random random, World world, Chunk chunk ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 15 );
            int z = random.nextInt( 15 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            if ( y != -1 && this.canPlaceCactus( chunk, x, y, z ) ) {
                int stackValue = random.nextInt(0, 4);
                for ( int i1 = 0; i1 < stackValue; i1++ ) {
                    chunk.setBlock( x, y + i1, z, 0, new BlockCactus() );
                }
            }
        }
    }

    private boolean canPlaceCactus( Chunk chunk, int x, int y, int z ) {
        Block block = chunk.getBlock( x, y, z, 0 );
        return block.getType().equals( BlockType.AIR ) && chunk.getBlock( x, y - 1, z, 0 ).getType().equals( BlockType.SAND );
    }

    private int getHighestWorkableBlock( Chunk chunk, int x, int z ) {
        int y = 255;
        for ( ; y >= 0; --y ) {
            Block block = chunk.getBlock( x, y, z, 0 );

            if ( !block.getType().equals( BlockType.AIR ) ) {
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
