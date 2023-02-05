package org.jukeboxmc.world.generator.object.tree;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves;
import org.jukeboxmc.block.behavior.BlockLog;
import org.jukeboxmc.block.data.LeafType;
import org.jukeboxmc.block.data.LogType;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SwampTree extends Tree {

    private final Block BLOCK_OAK_LEAVES = Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.OAK );
    private final Block BLOCK_OAK_LOG = Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.OAK );

    public SwampTree( int treeHeight ) {
        super( treeHeight );
    }

    public void create( Random random, PopulationChunkManager manager, int x, int y, int z ) {
        for ( int j = 0; j < this.treeHeight; j++ ) {
            manager.setBlock( x, y + j, z, BLOCK_OAK_LOG );
        }

        // Create the branches of the tree using blocks of leaves
        for ( int yy = y - 3 + this.treeHeight; yy <= y + this.treeHeight; ++yy ) {
            double yOff = yy - ( y + this.treeHeight );
            int mid = (int) ( 1 - yOff / 2 ) + 1;
            for ( int xx = x - mid; xx <= x + mid; ++xx ) {
                int xOff = Math.abs( xx - x );
                for ( int zz = z - mid; zz <= z + mid; ++zz ) {
                    int zOff = Math.abs( zz - z );
                    if ( xOff == mid && zOff == mid && ( yOff == 0 || random.nextInt( 3 ) == 0 ) ) {
                        continue;
                    }
                    Block block = manager.getBlock( xx, yy, zz, 0 );
                    if ( !block.isSolid() ) {
                        manager.setBlock( xx, yy, zz, 0, BLOCK_OAK_LEAVES );
                    }
                }
            }
        }
    }
}
