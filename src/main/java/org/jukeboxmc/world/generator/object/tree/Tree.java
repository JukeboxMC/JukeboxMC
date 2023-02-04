package org.jukeboxmc.world.generator.object.tree;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves;
import org.jukeboxmc.block.behavior.BlockLeaves2;
import org.jukeboxmc.block.behavior.BlockLog;
import org.jukeboxmc.block.behavior.BlockLog2;
import org.jukeboxmc.block.data.LeafType;
import org.jukeboxmc.block.data.LeafType2;
import org.jukeboxmc.block.data.LogType;
import org.jukeboxmc.block.data.LogType2;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Tree {

    protected int treeHeight;

    public Tree( int treeHeight ) {
        this.treeHeight = treeHeight;
    }

    public void create( Random random, PopulationChunkManager manager, int x, int y, int z, TreeType treeType ) {
        for ( int j = 0; j < this.treeHeight; j++ ) {
            manager.setBlock( x, y + j, z, treeType.blockLog );
        }

        for ( int yy = y - 3 + this.treeHeight; yy <= y + this.treeHeight; ++yy ) {
            double yOff = yy - ( y + this.treeHeight );
            int mid = (int) ( 1 - yOff / 2 );
            for ( int xx = x - mid; xx <= x + mid; ++xx ) {
                int xOff = Math.abs( xx - x );
                for ( int zz = z - mid; zz <= z + mid; ++zz ) {
                    int zOff = Math.abs( zz - z );
                    if ( xOff == mid && zOff == mid && ( yOff == 0 || random.nextInt( 3 ) == 0 ) ) {
                        continue;
                    }
                    Block block = manager.getBlock( xx, yy, zz, 0 );
                    if ( !block.isSolid() ) {
                        manager.setBlock( xx, yy, zz, 0, treeType.blockLeave );
                    }
                }
            }
        }
    }


    public enum TreeType {

        OAK( Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.OAK ), Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.OAK ) ),
        SPRUCE( Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.SPRUCE ), Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.SPRUCE ) ),
        BIRCH( Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.BIRCH ), Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.BIRCH ) ),
        JUNGLE( Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.JUNGLE ), Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.JUNGLE ) ),
        ACACIA( Block.<BlockLog2>create( BlockType.LOG2 ).setLogType( LogType2.ACACIA ), Block.<BlockLeaves2>create( BlockType.LEAVES2 ).setLeafType( LeafType2.ACACIA ) ),
        DARK_OAK( Block.<BlockLog2>create( BlockType.LOG2 ).setLogType( LogType2.DARK_OAK ), Block.<BlockLeaves2>create( BlockType.LEAVES2 ).setLeafType( LeafType2.DARK_OAK ) ),
        MANGROVE( Block.create( BlockType.MANGROVE_LOG ), Block.create( BlockType.MANGROVE_LEAVES ) );

        private final Block blockLog;
        private final Block blockLeave;

        TreeType( Block blockLog, Block blockLeave ) {
            this.blockLog = blockLog;
            this.blockLeave = blockLeave;
        }

        public Block getBlockLog() {
            return this.blockLog;
        }

        public Block getBlockLeave() {
            return this.blockLeave;
        }
    }
}
