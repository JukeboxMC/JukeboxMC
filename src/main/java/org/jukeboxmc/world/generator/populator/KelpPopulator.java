package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.behavior.BlockKelp;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;
import org.jukeboxmc.world.generator.NormalGenerator;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class KelpPopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private final Block BLOCK_KELP = Block.create( BlockType.KELP );
    private final Block BLOCK_WATER = Block.create( BlockType.WATER );

    @Override
    public void populate( Random random, World world, PopulationChunkManager chunkManager, int chunkX, int chunkZ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 16 );
            int z = random.nextInt( 16 );
            int y = getHighestWorkableBlock( chunk, x, z );
            if ( y > 0 ) {
                if ( !this.canKelpStay( chunk, x, y + 1, z ) ) {
                    return;
                }

                if ( !chunk.getBlock( x, y - 1, z, 0 ).isSolid() ) {
                    if ( chunk.getBlock( x, y - 1, z, 0 ).getType() != BlockType.KELP ) {
                        return;
                    }
                }

                int height = random.nextInt( 10 ) + 1;
                for ( int h = 0; h <= height; h++ ) {
                    if ( this.canKelpStay( chunk, x, y + h, z ) ) {
                        if ( h == height || !chunk.getBlock( x, y + h + 2, z, 0 ).getType().equals( BlockType.WATER ) ) {
                            chunk.setBlock( x, y + h, z, 0, ( (BlockKelp) BLOCK_KELP.clone() ).setKelpAge( 20 + random.nextInt( 4 ) ) );
                            chunk.setBlock( x, y + h, z, 1, BLOCK_WATER );
                            return;
                        } else {
                            chunk.setBlock( x, y + h, z, 0, ( (BlockKelp) BLOCK_KELP.clone() ).setKelpAge( 25 ) );
                            chunk.setBlock( x, y + h, z, 1, BLOCK_WATER );
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public int getHighestWorkableBlock( Chunk chunk, int x, int z ) {
        int y;
        for ( y = NormalGenerator.WATER_HEIGHT - 1; y >= 0; --y ) {
            Block block = chunk.getBlock( x, y, z, 0 );
            if ( block.getType() != BlockType.AIR &&
                    block.getType() != BlockType.WATER &&
                    block.getType() != BlockType.FLOWING_WATER &&
                    block.getType() != BlockType.ICE &&
                    block.getType() != BlockType.PACKED_ICE &&
                    block.getType() != BlockType.BLUE_ICE ) {
                break;
            }
        }
        return y == 0 ? -1 : ++y;
    }

    public boolean canKelpStay( Chunk chunk, int x, int y, int z ) {
        return chunk.getBlock( x, y, z, 0 ).getType().equals( BlockType.WATER );
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }
}
