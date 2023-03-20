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
public class EmeraldOrePopulator extends Populator {

    private final Block BLOCK_EMERALD_ORE = Block.create( BlockType.EMERALD_ORE );

    @Override
    public void populate(@NotNull Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int i = 0; i < 11; i++ ) {
            int x = random.nextInt( 16 );
            int z = random.nextInt( 16 );
            int y = random.nextInt( 0, 32 );

            if ( chunk.getBlock( x, y, z, 0 ).getType() != BlockType.STONE ) {
                continue;
            }
            chunk.setBlock( x, y, z, 0, BLOCK_EMERALD_ORE );
        }
    }
}
