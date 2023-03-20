package org.jukeboxmc.world.generator.populator;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SugarcanePopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private final Block BLOCK_SUGAR_CANE = Block.create( BlockType.SUGAR_CANE );

    @Override
    public void populate(@NotNull Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 16 );
            int z = random.nextInt( 16 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            if ( y != -1 && this.canSugarCaneStay( chunk, x, y, z ) ) {
                chunk.setBlock( x, y, z, 0, BLOCK_SUGAR_CANE );
            }
        }
    }

    private boolean canSugarCaneStay(@NotNull Chunk chunk, int x, int y, int z ) {
        Block block = chunk.getBlock( x, y, z, 0 );
        boolean val1 = block.getType() == BlockType.AIR || block.getType() == BlockType.SNOW_LAYER;
        boolean val2 = this.blockBelow( chunk, x, y, z, BlockType.GRAVEL ) || this.blockBelow( chunk, x, y, z, BlockType.GRASS );
        boolean val3 = this.findWater( chunk, x, y - 1, z );
        return val1 && val2 && val3;
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }

    private boolean findWater(@NotNull Chunk chunk, int x, int y, int z ) {
        for ( int i = x - 4; i < ( x + 4 ); i++ ) {
            for ( int j = z - 4; j < ( z + 4 ); j++ ) {
                Block block = chunk.getBlock( i, y, j, 0 );
                Block blockNorth = block.getSide( BlockFace.NORTH );
                Block blockEast = block.getSide( BlockFace.EAST );
                Block blockSouth = block.getSide( BlockFace.SOUTH );
                Block blockWest = block.getSide( BlockFace.WEST );
                if ( ( blockNorth.getType().equals( BlockType.WATER ) ) || ( blockEast.getType().equals( BlockType.WATER ) ) || ( blockSouth.getType().equals( BlockType.WATER ) ) || ( blockWest.getType().equals( BlockType.WATER ) ) ) {
                    return true;
                }
            }
        }
        return false;
    }
}
