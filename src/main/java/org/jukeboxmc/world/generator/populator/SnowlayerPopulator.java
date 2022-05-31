package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockSnowLayer;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.biome.BiomeGeneration;
import org.jukeboxmc.world.generator.biome.BiomeGenerationRegistry;
import org.jukeboxmc.world.generator.biome.SnowyBiome;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SnowlayerPopulator implements Populator {

    @Override
    public void populate( Random random, World world, Chunk chunk ) {
        for ( int x = 0; x < 16; ++x ) {
            for ( int z = 0; z < 16; ++z ) {
                int y = this.getHighestWorkableBlock( chunk, x, z );

                Biome biome = chunk.getBiome( x, y, z );

                BiomeGeneration biomeGenerator = BiomeGenerationRegistry.getBiomeGenerator( biome );
                if ( biomeGenerator instanceof SnowyBiome ) {
                    Block block = chunk.getBlock( x, y - 1, z, 0 );
                    if ( block.getType().equals( BlockType.GRASS ) ) {
                        chunk.setBlock( x, y, z, 0, new BlockSnowLayer() );
                    }
                }
            }
        }
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
}
