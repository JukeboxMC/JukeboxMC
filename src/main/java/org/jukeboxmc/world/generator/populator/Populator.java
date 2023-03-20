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
public abstract class Populator {

    public abstract void populate( Random random, World world, PopulationChunkManager chunkManager, int chunkX, int chunkZ );

    public boolean blockBelow(@NotNull Chunk chunk, int x, int y, int z, BlockType blockType ) {
        return chunk.getBlock( x, y - 1, z, 0 ).getType().equals( blockType );
    }

    public int getHighestWorkableBlock(@NotNull Chunk chunk, int x, int z ) {
        int y = 255;
        for ( ; y >= 0; --y ) {
            Block block = chunk.getBlock( x, y, z, 0 );
            if ( block.getType() != BlockType.AIR &&
                    block.getType() != BlockType.LEAVES &&
                    block.getType() != BlockType.LEAVES2 &&
                    block.getType() != BlockType.AZALEA_LEAVES &&
                    block.getType() != BlockType.AZALEA_LEAVES_FLOWERED &&
                    block.getType() != BlockType.MANGROVE_LEAVES &&
                    block.getType() != BlockType.SNOW_LAYER ) {
                break;
            }
        }
        return y == 0 ? -1 : ++y;
    }
}
