package org.jukeboxmc.world.generator.object;

import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.math.Vector2;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Ore {

    private final Random random;
    private final OreType type;

    public Ore( Random random, OreType type ) {
        this.type = type;
        this.random = random;
    }

    public boolean canPlaceObject( Chunk chunk, int x, int y, int z ) {
        return chunk.getBlock( x, y, z, 0 ).getType() == BlockType.STONE;
    }

    public void placeObject( Chunk chunk, int x, int y, int z ) {
        int clusterSize = this.type.getClusterSize();
        double angle = this.random.nextFloat() * Math.PI;

        Vector2 offset = Vector2.direction( angle ).multiply( clusterSize / 8f );
        float x1 = x + 8 + offset.getX();
        float x2 = x + 8 - offset.getX();
        float z1 = z + 8 + offset.getZ();
        float z2 = z + 8 - offset.getZ();

        int y1 = y + this.random.nextInt( 3 ) + 2;
        int y2 = y + this.random.nextInt( 3 ) + 2;

        for ( int count = 0; count <= clusterSize; ++count ) {
            double seedX = ( x1 + ( x2 - x1 ) * count / clusterSize );
            double seedY = y1 + ( y2 - y1 ) * count / clusterSize;
            double seedZ = ( z1 + ( z2 - z1 ) * count / clusterSize );

            double size = ( ( ( Math.sin( count * ( Math.PI / clusterSize ) ) + 1 ) * this.random.nextFloat() * clusterSize / 16 + 1 ) / 2 );
            int startX = (int) ( seedX - size );
            int startY = (int) ( seedY - size );
            int startZ = (int) ( seedZ - size );
            int endX = (int) ( seedX + size );
            int endY = (int) ( seedY + size );
            int endZ = (int) ( seedZ + size );

            for ( x = startX; x <= endX; ++x ) {
                double sizeX = ( x + 0.5 - seedX ) / size;
                sizeX *= sizeX;
                if ( sizeX < 1 ) {
                    for ( y = startY; y <= endY; ++y ) {
                        double sizeY = ( y + 0.5 - seedY ) / size;
                        sizeY *= sizeY;
                        if ( y > 0 && ( sizeX + sizeY ) < 1 ) {
                            for ( z = startZ; z <= endZ; ++z ) {
                                double sizeZ = ( z + 0.5 - seedZ ) / size;
                                sizeZ *= sizeZ;
                                if ( ( sizeX + sizeY + sizeZ ) < 1 && chunk.getBlock( x, y, z, 0 ).getType() == BlockType.STONE ) {
                                    chunk.setBlock( x, y, z, 0,this.type.getBlock() );
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
