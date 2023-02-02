package org.jukeboxmc.world.generator.object.tree;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves;
import org.jukeboxmc.block.behavior.BlockLog;
import org.jukeboxmc.block.data.LeafType;
import org.jukeboxmc.block.data.LogType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmallJungleTree {

    private final Block BLOCK_LOG = Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.JUNGLE );
    private final Block BLOCK_LEAVES = Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.JUNGLE );
    private final Block BLOCK_VINE = Block.create( BlockType.VINE );

    public void create( Random random, PopulationChunkManager manager, Vector position ) {
        int treeHeight = 4 + random.nextInt( 7 );
        for ( int j = 0; j < treeHeight; j++ ) {
            manager.setBlock( position.getBlockX(), position.getBlockY() + j, position.getBlockZ(), BLOCK_LOG );
        }

        for ( int yy = position.getBlockY() - 3 + treeHeight; yy <= position.getBlockY() + treeHeight; ++yy ) {
            double yOff = yy - ( position.getBlockY() + treeHeight );
            int mid = (int) ( 1 - yOff / 2 );
            for ( int xx = position.getBlockX() - mid; xx <= position.getBlockX() + mid; ++xx ) {
                int xOff = Math.abs( xx - position.getBlockX() );
                for ( int zz = position.getBlockZ() - mid; zz <= position.getBlockZ() + mid; ++zz ) {
                    int zOff = Math.abs( zz - position.getBlockZ() );
                    if ( xOff == mid && zOff == mid && ( yOff == 0 || random.nextInt( 3 ) == 0 ) ) {
                        continue;
                    }
                    Block block = manager.getBlock( xx, yy, zz, 0 );
                    if ( !block.isSolid() ) {
                        manager.setBlock( xx, yy, zz, 0, BLOCK_LEAVES );
                    }
                }
            }
        }
    }
}
