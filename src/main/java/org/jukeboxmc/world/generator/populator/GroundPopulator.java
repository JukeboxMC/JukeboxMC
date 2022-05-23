package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.BlockWater;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.biome.BiomeGeneration;
import org.jukeboxmc.world.generator.biome.BiomeGenerationRegistry;

import java.util.List;
import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GroundPopulator implements Populator {

    @Override
    public void populate( Random random, World world, Chunk chunk ) {
        for ( int x = 0; x < 16; ++x ) {
            for ( int z = 0; z < 16; ++z ) {
                Biome biome = chunk.getBiome( x, 7, z );
                BiomeGeneration biomeGeneration = BiomeGenerationRegistry.getBiomeGenerator( biome );
                if ( biomeGeneration != null ) {
                    List<Block> ground = biomeGeneration.getGround();
                    if ( !ground.isEmpty() ) {
                        int y = chunk.getMaxY();
                        for ( ; y > 0; --y ) {
                            Block block = chunk.getBlock( x, y, z, 0 );
                            if ( !block.isTransparent() && block.getType() != BlockType.AIR ) {
                                break;
                            }
                        }

                        int startY = Math.min( 127, y );
                        int endY = startY - ground.size();

                        for ( y = startY; y > endY && y >= 0; --y ) {
                            Block block = ground.get( startY - y );
                            Block chunkBlock = chunk.getBlock( x, y + 1, z, 0 );
                            if ( chunkBlock instanceof BlockWater)  {
                                chunk.setBlock( x, y, z, 0, biomeGeneration.getDefaultGround() );
                            } else {
                                chunk.setBlock( x, y, z, 0, block );
                            }

                        }
                    }
                }
            }
        }
    }
}
