package org.jukeboxmc.world.generator.populator;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;
import org.jukeboxmc.world.generator.object.tree.SavannaTree;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SavannaTreePopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    @Override
    public void populate(@NotNull Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( chunkX << 4, ( chunkX << 4 ) + 15 );
            int z = random.nextInt( chunkZ << 4, ( chunkZ << 4 ) + 15 );
            int y = this.getHighestWorkableBlock( chunk, x, z );
            if ( y == -1 ) {
                continue;
            }
            new SavannaTree().create( random, chunkManager, new Vector( x, y, z ) );
        }
    }

    @Override
    public int getHighestWorkableBlock(@NotNull Chunk chunk, int x, int z ) {
        int y;
        for ( y = 127; y > 0; --y ) {
            BlockType blockType = chunk.getBlock( x, y, z, 0 ).getType();
            if ( blockType == BlockType.DIRT || blockType == BlockType.GRASS ) {
                break;
            } else if ( blockType != BlockType.AIR && blockType != BlockType.SNOW_LAYER ) {
                return -1;
            }
        }

        return ++y;
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }
}
