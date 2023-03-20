package org.jukeboxmc.world.generator.populator;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;
import org.jukeboxmc.world.generator.object.tree.Tree;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TreePopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private final Tree.TreeType treeType;
    private final boolean mutated;

    public TreePopulator( Tree.TreeType treeType ) {
        this.treeType = treeType;
        this.mutated = false;
    }

    public TreePopulator( Tree.TreeType treeType, boolean mutated ) {
        this.treeType = treeType;
        this.mutated = mutated;
    }

    @Override
    public void populate(@NotNull Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( chunkX << 4, ( chunkX << 4 ) + 15 );
            int z = random.nextInt( chunkZ << 4, ( chunkZ << 4 ) + 15 );
            int y = this.getHighestWorkableBlock( chunk, x, z );
            if ( y == -1 ) {
                continue;
            }
            if ( !this.mutated ) {
                new Tree( random.nextInt( 4 ) + 5 ).create( random, chunkManager, x, y,z, this.treeType );
            } else {
                new Tree( random.nextInt(3) + 10 ).create( random, chunkManager, x, y,z, this.treeType );
            }

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
