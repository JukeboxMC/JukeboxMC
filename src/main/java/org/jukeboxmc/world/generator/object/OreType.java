package org.jukeboxmc.world.generator.object;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockStone;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class OreType {

    public final int runtimeId;
    public final int clusterCount;
    public final int clusterSize;
    public final int maxHeight;
    public final int minHeight;
    public final int replaceBlockId;

    public OreType( Block material, int clusterCount, int clusterSize, int minHeight, int maxHeight ) {
        this( material, clusterCount, clusterSize, minHeight, maxHeight, new BlockStone().getRuntimeId() );
    }

    public OreType( Block block, int clusterCount, int clusterSize, int minHeight, int maxHeight, int replaceBlockId ) {
        this.runtimeId = block.getRuntimeId();
        this.clusterCount = clusterCount;
        this.clusterSize = clusterSize;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.replaceBlockId = replaceBlockId;
    }

    public boolean spawn( Chunk chunk, Random rand, int x, int y, int z ) {
        float piScaled = rand.nextFloat() * (float) Math.PI;
        double scaleMaxX = (float) ( x + 8 ) + Math.sin( piScaled ) * (float) clusterSize / 8.0F;
        double scaleMinX = (float) ( x + 8 ) - Math.sin( piScaled ) * (float) clusterSize / 8.0F;
        double scaleMaxZ = (float) ( z + 8 ) + Math.cos( piScaled ) * (float) clusterSize / 8.0F;
        double scaleMinZ = (float) ( z + 8 ) - Math.cos( piScaled ) * (float) clusterSize / 8.0F;
        double scaleMaxY = y + rand.nextInt( 3 ) - 2;
        double scaleMinY = y + rand.nextInt( 3 ) - 2;

        for ( int i = 0; i < clusterSize; ++i ) {
            float sizeIncr = (float) i / (float) clusterSize;
            double scaleX = scaleMaxX + ( scaleMinX - scaleMaxX ) * (double) sizeIncr;
            double scaleY = scaleMaxY + ( scaleMinY - scaleMaxY ) * (double) sizeIncr;
            double scaleZ = scaleMaxZ + ( scaleMinZ - scaleMaxZ ) * (double) sizeIncr;
            double randSizeOffset = rand.nextDouble() * (double) clusterSize / 16.0D;
            double randVec1 = (double) ( Math.sin( (float) Math.PI * sizeIncr ) + 1.0F ) * randSizeOffset + 1.0D;
            double randVec2 = (double) ( Math.sin( (float) Math.PI * sizeIncr ) + 1.0F ) * randSizeOffset + 1.0D;
            int minX = (int) Math.floor( scaleX - randVec1 / 2.0D );
            int minY = (int) Math.floor( scaleY - randVec2 / 2.0D );
            int minZ = (int) Math.floor( scaleZ - randVec1 / 2.0D );
            int maxX = (int) Math.floor( scaleX + randVec1 / 2.0D );
            int maxY = (int) Math.floor( scaleY + randVec2 / 2.0D );
            int maxZ = (int) Math.floor( scaleZ + randVec1 / 2.0D );

            for ( int xSeg = minX; xSeg <= maxX; ++xSeg ) {
                double xVal = ( (double) xSeg + 0.5D - scaleX ) / ( randVec1 / 2.0D );

                if ( xVal * xVal < 1.0D ) {
                    for ( int ySeg = minY; ySeg <= maxY; ++ySeg ) {
                        double yVal = ( (double) ySeg + 0.5D - scaleY ) / ( randVec2 / 2.0D );

                        if ( xVal * xVal + yVal * yVal < 1.0D ) {
                            for ( int zSeg = minZ; zSeg <= maxZ; ++zSeg ) {
                                double zVal = ( (double) zSeg + 0.5D - scaleZ ) / ( randVec1 / 2.0D );

                                if ( xVal * xVal + yVal * yVal + zVal * zVal < 1.0D ) {
                                    if ( chunk.getBlock( xSeg, ySeg, zSeg, 0 ).getRuntimeId() == this.replaceBlockId ) {
                                        chunk.setBlock( new Vector( xSeg, ySeg, zSeg ), 0, runtimeId );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

}