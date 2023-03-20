package org.jukeboxmc.world.generator.object.tree;

import org.jetbrains.annotations.NotNull;
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
public class TaigaTree extends Tree {

    private final Block BLOCK_SPRUCE_LOG = Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.SPRUCE );
    private final Block BLOCK_SPRUCE_LEAVES = Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.SPRUCE );

    public TaigaTree( int treeHeight ) {
        super( treeHeight );
    }

    public void create(@NotNull Random random, @NotNull PopulationChunkManager manager, int x, int y, int z ) {
        this.treeHeight = this.treeHeight - random.nextInt(3);
        for ( int i = 0; i < this.treeHeight; i++ ) {
            manager.setBlock( x, y + i, z, BLOCK_SPRUCE_LOG );
        }

        int topSize = this.treeHeight - ( 1 + random.nextInt( 2 ) );
        int lRadius = 2 + random.nextInt( 2 );
        int radius = random.nextInt( 2 );
        int maxR = 1;
        int minR = 0;

        for ( int yy = 0; yy <= topSize; ++yy ) {
            int yyy = y + this.treeHeight - yy;

            for ( int xx = x - radius; xx <= x + radius; ++xx ) {
                int xOff = Math.abs( xx - x );
                for ( int zz = z - radius; zz <= z + radius; ++zz ) {
                    int zOff = Math.abs( zz - z );
                    if ( xOff == radius && zOff == radius && radius > 0 ) {
                        continue;
                    }
                    manager.setBlock( xx, yyy, zz, BLOCK_SPRUCE_LEAVES );
                }
            }

            if ( radius >= maxR ) {
                radius = minR;
                minR = 1;
                if ( ++maxR > lRadius ) {
                    maxR = lRadius;
                }
            } else {
                ++radius;
            }
        }
    }

    /*
       manager.setBlock( x, y + 1, z, BLOCK_SPRUCE_LEAVES );
        for ( int xx = -2; xx <= 2; xx++ ) {
            for ( int yy = -2; yy <= 2; yy++ ) {
                for ( int zz = -2; zz <= 2; zz++ ) {
                    if ( Math.abs( xx ) + Math.abs( yy ) + Math.abs( zz ) > 3 ) {
                        manager.setBlock( xx + x, yy + y + 1, zz + z, BLOCK_SPRUCE_LEAVES );
                    }
                }
            }
        }
     */
}
