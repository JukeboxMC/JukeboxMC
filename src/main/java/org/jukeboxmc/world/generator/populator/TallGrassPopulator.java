package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockTallGrass;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TallGrassPopulator implements Populator {

    private int randomAmount;
    private int baseAmount;

    @Override
    public void populate( Random random, World world, Chunk chunk ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 15 );
            int z = random.nextInt( 15 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            if ( y != -1 && this.canTallGrassStay( chunk, x, y, z ) ) {
                BlockTallGrass tallGrass = new BlockTallGrass();
                chunk.setBlock( x, y, z, 0, tallGrass );
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
        return ( block.getType() == BlockType.AIR || block.getType() == BlockType.SNOW_LAYER ) && chunk.getBlock( x, y - 1, z, 0 ).getType() == BlockType.GRASS;
    }

    private int getHighestWorkableBlock( Chunk chunk, int x, int z ) {
        int y = 255;
        for ( ; y >= 0; --y ) {
            Block block = chunk.getBlock( x, y, z, 0 );
            if ( block.getType() != BlockType.AIR &&
                    block.getType() != BlockType.OAK_LEAVES &&
                    block.getType() != BlockType.SPRUCE_LEAVES &&
                    block.getType() != BlockType.BIRCH_LEAVES &&
                    block.getType() != BlockType.JUNGLE_LEAVES &&
                    block.getType() != BlockType.ACACIA_LEAVES &&
                    block.getType() != BlockType.DARK_OAK_LEAVES &&
                    block.getType() != BlockType.SNOW_LAYER ) {
                break;
            }
        }
        return y == 0 ? -1 : ++y;
    }
}
