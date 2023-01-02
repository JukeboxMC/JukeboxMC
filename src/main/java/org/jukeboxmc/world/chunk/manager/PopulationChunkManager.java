package org.jukeboxmc.world.chunk.manager;

import com.google.common.base.Preconditions;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PopulationChunkManager {

    private final int cornerX;
    private final int cornerZ;

    private final Chunk[] chunks = new Chunk[3 * 3];

    public PopulationChunkManager( Chunk chunk, List<Chunk> chunks ) {
        this.cornerX = chunk.getX() - 1;
        this.cornerZ = chunk.getZ() - 1;

        for ( Chunk value : chunks ) {
            this.chunks[this.chunkIndex( value.getX(), value.getZ() )] = value;
        }
    }

    private int chunkIndex( int chunkX, int chunkZ ) {
        int relativeX = chunkX - this.cornerX;
        int relativeZ = chunkZ - this.cornerZ;
        Preconditions.checkArgument( relativeX >= 0 && relativeX < 3 && relativeZ >= 0 && relativeZ < 3, "Chunk position (%s,%s) out of population bounds", chunkX, chunkZ );
        return relativeX * 3 + relativeZ;
    }

    private Chunk chunkFromBlock( int blockX, int blockZ ) {
        int relativeX = ( blockX >> 4 ) - this.cornerX;
        int relativeZ = ( blockZ >> 4 ) - this.cornerZ;
        Preconditions.checkArgument( relativeX >= 0 && relativeX < 3 && relativeZ >= 0 && relativeZ < 3, "Block position (%s,%s) out of population bounds", blockX, blockZ );
        return this.chunks[relativeX * 3 + relativeZ];
    }

    public Chunk getChunk(int chunkX, int chunkZ) {
        return this.chunks[this.chunkIndex(chunkX, chunkZ)];
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        return this.chunkFromBlock( x, z ).getBlock( x, y, z, layer );
    }

    public Block getBlock( int x, int y, int z ) {
        return this.getBlock( x, y, z, 0 );
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.chunkFromBlock( x, z ).setBlock( x, y, z, layer, block );
    }

    public void setBlock( int x, int y, int z, Block block ) {
        this.setBlock( x, y, z, 0, block );
    }
}
