package org.jukeboxmc.world.generator.populator;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class WaterIcePopulator extends Populator {

    private final Block BLOCK_ICE = Block.create( BlockType.ICE );

    @Override
    public void populate(Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int x = 0; x < 16; x++ ) {
            for ( int z = 0; z < 16; z++ ) {
                Biome biome = chunk.getBiome( x, 7, z );
                if ( biome != null && biome.isFreezing() ) {
                    int y = chunk.getHighestBlockY( x, z ) - 1;
                    if ( chunk.getBlock( x, y, z, 0 ).getType().equals( BlockType.WATER ) ) {
                        chunk.setBlock( x, y, z, 0, BLOCK_ICE );
                    }
                }
            }
        }
    }
}
