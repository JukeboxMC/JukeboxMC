package org.jukeboxmc.world.generator.populator;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockSeagrass;
import org.jukeboxmc.block.data.SeaGrassType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;
import org.jukeboxmc.world.generator.NormalGenerator;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SeagrassPopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private final double tallSeagrassProbability;

    private final Block BLOCK_SEAGRASS = Block.create( BlockType.SEAGRASS );
    private final Block BLOCK_WATER = Block.create( BlockType.WATER );

    public SeagrassPopulator( double tallSeagrassProbability ) {
        this.tallSeagrassProbability = tallSeagrassProbability;
    }

    @Override
    public void populate(@NotNull Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 16 );
            int z = random.nextInt( 16 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            if ( y > 0 && this.canSeagrassStay( chunk, x, y, z, false ) ) {
                if ( random.nextDouble() < this.tallSeagrassProbability ) {
                    if ( this.canSeagrassStay( chunk, x, y + 1, z, true ) ) {
                        chunk.setBlock( x, y, z, 0, ( (BlockSeagrass) BLOCK_SEAGRASS.clone() ).setSeaGrassType( SeaGrassType.DOUBLE_BOT ) );
                        chunk.setBlock( x, y, z, 1, BLOCK_WATER );

                        chunk.setBlock( x, y + 1, z, 0, ( (BlockSeagrass) BLOCK_SEAGRASS.clone() ).setSeaGrassType( SeaGrassType.DOUBLE_TOP ) );
                        chunk.setBlock( x, y + 1, z, 1, BLOCK_WATER );
                    }
                } else {
                    chunk.setBlock( x, y, z, 0, ( (BlockSeagrass) BLOCK_SEAGRASS.clone() ).setSeaGrassType( SeaGrassType.DEFAULT ) );
                    chunk.setBlock( x, y, z, 1, BLOCK_WATER );
                }
            }
        }
    }

    @Override
    public int getHighestWorkableBlock(@NotNull Chunk chunk, int x, int z ) {
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

    public boolean canSeagrassStay(@NotNull Chunk chunk, int x, int y, int z, boolean tallSeagrass ) {
        if ( tallSeagrass ) {
            return chunk.getBlock( x, y, z, 0 ).getType().equals( BlockType.WATER );
        } else {
            return chunk.getBlock( x, y, z, 0 ).getType().equals( BlockType.WATER ) && chunk.getBlock( x, y - 1, z, 0 ).isSolid();
        }
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }
}
