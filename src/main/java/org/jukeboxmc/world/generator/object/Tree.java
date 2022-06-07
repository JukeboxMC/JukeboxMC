package org.jukeboxmc.world.generator.object;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDirt;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Tree {

    protected int treeHeight;
    protected BlockType blockLog;
    protected BlockType leafBlock;

    public abstract void grow( Chunk chunk, int x, int y, int z, Random random );

    public boolean canPlaceObject( Chunk chunk, int x, int y, int z, Random random ) {
        int radiusToCheck = 0;
        for ( int yy = 0; yy < this.treeHeight + 3; ++yy ) {
            if ( yy == 1 || yy == this.treeHeight ) {
                ++radiusToCheck;
            }
            for ( int xx = -radiusToCheck; xx < ( radiusToCheck + 1 ); ++xx ) {
                for ( int zz = -radiusToCheck; zz < ( radiusToCheck + 1 ); ++zz ) {
                    boolean success = (x + xx >> 4) == chunk.getX() && (z + zz >> 4) == chunk.getZ();
                    if ( !success ) {
                        return false;
                    }
                    if ( !this.overridable( chunk.getBlock( x + xx, y + yy, z + zz, 0 ).getType() ) ) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void placeObject( Chunk chunk, int x, int y, int z, Random random ) {
        this.placeTrunk( chunk, x, y, z, random, this.treeHeight - 1 );
        for ( int yy = y - 3 + this.treeHeight; yy <= y + this.treeHeight; ++yy ) {
            double yOff = yy - ( y + this.treeHeight );
            int mid = (int) ( 1 - yOff / 2 );
            for ( int xx = x - mid; xx <= x + mid; ++xx ) {
                int xOff = Math.abs( xx - x );
                for ( int zz = z - mid; zz <= z + mid; ++zz ) {
                    int zOff = Math.abs( zz - z );
                    if ( xOff == mid && zOff == mid && ( yOff == 0 || random.nextInt( 2 ) == 0 ) ) {
                        continue;
                    }
                    Block block = chunk.getBlock( xx, yy, zz, 0 );
                    if ( !block.isSolid() && chunk.getX() == xx >> 4 && chunk.getZ() == zz >> 4 ) {
                        chunk.setBlock( xx, yy, zz, 0, this.leafBlock.getBlock() );
                    }
                }
            }
        }
    }

    protected void placeTrunk( Chunk chunk, int x, int y, int z, Random random, int trunkHeight ) {
        chunk.setBlock( x, y - 1, z, 0, new BlockDirt() );
        for ( int yy = 0; yy < trunkHeight; ++yy ) {
            Block block = chunk.getBlock( x, y + yy, z, 0 );

            if ( this.overridable( block.getType() ) ) {
                chunk.setBlock( x, y + yy, z, 0, this.blockLog.getBlock() );
            }
        }
    }

    protected boolean overridable( BlockType blockType ) {
        return switch ( blockType ) {
            case AIR,
                    SAPLING,
                    OAK_LEAVES,
                    SPRUCE_LEAVES,
                    BIRCH_LEAVES,
                    ACACIA_LEAVES,
                    DARK_OAK_LEAVES,
                    JUNGLE_LEAVES,
                    OAK_LOG,
                    SPRUCE_LOG,
                    BIRCH_LOG,
                    ACACIA_LOG,
                    DARK_OAK_LOG,
                    JUNGLE_LOG,
                    SNOW_LAYER -> true;
            default -> false;
        };
    }
}
