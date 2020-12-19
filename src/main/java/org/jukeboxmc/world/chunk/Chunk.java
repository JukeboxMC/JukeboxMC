package org.jukeboxmc.world.chunk;

import lombok.Getter;
import lombok.ToString;
import org.jukeboxmc.utils.BinaryStream;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@ToString
public class Chunk {

    public static final int CHUNK_LAYERS = 2;

    private int chunkX;
    private int chunkZ;

    @Getter
    private SubChunk[] subChunks;

    public Chunk( int chunkX, int chunkZ ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.subChunks = new SubChunk[16];
    }

    public void setBlock( int x, int y, int z, int layer, int runtimeId ) {
        int subY = y >> 4;
        this.checkOrCreateSubChunks( subY );
        this.subChunks[subY].setBlock( x, y & 15, z, layer, runtimeId );
    }

    private void checkOrCreateSubChunks( int subY ) {
        for ( int y = 0; y <= subY; y++ ) {
            if ( this.subChunks[y] == null )
                this.subChunks[y] = new SubChunk( y );
        }
    }

    public void writeTo( BinaryStream binaryStream ) {
        for ( SubChunk subChunk : this.subChunks )
            if ( subChunk != null )
                subChunk.writeTo( binaryStream );

        byte[] biomeIds = new byte[256];
        for ( int x = 0; x < 16; x++ ) {
            for ( int z = 0; z < 16; z++ ) {
                biomeIds[( x << 4 ) | z] = 0;
            }
        }

        binaryStream.writeUnsignedVarInt( biomeIds.length );
        binaryStream.writeBytes( biomeIds );
        binaryStream.writeUnsignedVarInt( 0 ); //Extradata
    }

    public int getAvailableSubChunks() {
        return Arrays.stream( this.subChunks ).mapToInt( o -> o == null ? 0 : 1 ).sum();
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }
}
