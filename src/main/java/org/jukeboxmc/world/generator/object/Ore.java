package org.jukeboxmc.world.generator.object;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Ore {

    private final Random random;
    private final OreType oreType;

    public Ore( Random random, OreType oreType ) {
        this.random = random;
        this.oreType = oreType;
    }

    public boolean canPlace(@NotNull PopulationChunkManager manager, int x, int y, int z ) {
        return manager.getBlock( x, y, z ).getType().equals( BlockType.STONE );
    }

    public void place(@NotNull PopulationChunkManager manager, int x, int y, int z ) {
        float piScaled = this.random.nextFloat() * (float) Math.PI;
        double scaleMaxX = (float) ( x + 8 ) + Math.sin( piScaled ) * (float) this.oreType.getClusterSize() / 8.0F;
        double scaleMinX = (float) ( x + 8 ) - Math.sin( piScaled ) * (float) this.oreType.getClusterSize() / 8.0F;
        double scaleMaxZ = (float) ( z + 8 ) + Math.cos( piScaled ) * (float) this.oreType.getClusterSize() / 8.0F;
        double scaleMinZ = (float) ( z + 8 ) - Math.cos( piScaled ) * (float) this.oreType.getClusterSize() / 8.0F;
        double scaleMaxY = y + this.random.nextInt( 3 ) - 2;
        double scaleMinY = y + this.random.nextInt( 3 ) - 2;

        for ( int i = 0; i < this.oreType.getClusterSize(); ++i ) {
            float sizeIncr = (float) i / (float) this.oreType.getClusterSize();
            double scaleX = scaleMaxX + ( scaleMinX - scaleMaxX ) * (double) sizeIncr;
            double scaleY = scaleMaxY + ( scaleMinY - scaleMaxY ) * (double) sizeIncr;
            double scaleZ = scaleMaxZ + ( scaleMinZ - scaleMaxZ ) * (double) sizeIncr;
            double randSizeOffset = this.random.nextDouble() * (double) this.oreType.getClusterSize() / 16.0D;
            double randVec1 = ( Math.sin( (float) Math.PI * sizeIncr ) + 1.0F ) * randSizeOffset + 1.0D;
            double randVec2 = ( Math.sin( (float) Math.PI * sizeIncr ) + 1.0F ) * randSizeOffset + 1.0D;
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
                                    if ( this.canPlace( manager, xSeg, ySeg, zSeg )) {
                                        manager.setBlock( xSeg, ySeg, zSeg, this.oreType.getBlock() );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
