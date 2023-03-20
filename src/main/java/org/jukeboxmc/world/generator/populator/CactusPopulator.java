package org.jukeboxmc.world.generator.populator;

import org.jetbrains.annotations.NotNull;
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
public class CactusPopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private final Block BLOCK_CACTUS = Block.create( BlockType.CACTUS );

    @Override
    public void populate(@NotNull Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 16 );
            int z = random.nextInt( 16 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            int height = 0;
            int range = random.nextInt( 18 );
            if ( range >= 16 ) {
                height = 2;
            } else if ( range >= 11 ) {
                height = 1;
            }
            if ( y > 0 ) {
                for ( int yy = 0; yy < height; yy++ ) {
                    y += yy;
                    if ( this.canStayCactus( chunk, x, y, z ) ) {
                        chunk.setBlock( x, y, z, 0, BLOCK_CACTUS );
                    }
                }
            }
        }
    }

    public boolean canStayCactus(@NotNull Chunk chunk, int x, int y, int z ) {
        Block block = chunk.getBlock( x, y, z, 0 );
        boolean val1 = block.getType().equals( BlockType.AIR ) || block.getType().equals( BlockType.SNOW_LAYER ) || block.getType().equals( BlockType.TALLGRASS );
        boolean val2 = this.blockBelow( chunk, x, y, z, BlockType.SAND );
        boolean val3 = this.blockBelow( chunk, x, y, z, BlockType.CACTUS );
        boolean val4 = this.isAirAround( chunk, x, y, z );
        return (val1 && val2 && val4 )|| val3;
    }

    private boolean isAirAround(@NotNull Chunk chunk, int x, int y, int z ) {
        boolean val1 = chunk.getBlock( x + 1, y, z, 0 ).getType().equals( BlockType.AIR );
        boolean val2 = chunk.getBlock( x - 1, y, z, 0 ).getType().equals( BlockType.AIR );
        boolean val3 = chunk.getBlock( x, y, z + 1, 0 ).getType().equals( BlockType.AIR );
        boolean val4 = chunk.getBlock( x, y, z - 1, 0 ).getType().equals( BlockType.AIR );
        return val1 && val2 && val3 && val4;
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }


}
