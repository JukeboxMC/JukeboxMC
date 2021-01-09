package org.jukeboxmc.world.chunk;

import lombok.ToString;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.utils.BinaryStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@ToString ( exclude = { "subChunks" } )
public class Chunk {

    public static final int CHUNK_LAYERS = 2;

    private SubChunk[] subChunks;

    private int chunkX;
    private int chunkZ;

    private List<Entity> entitys = new CopyOnWriteArrayList<>();

    public Chunk( int chunkX, int chunkZ ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.subChunks = new SubChunk[16];
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        int subY = y >> 4;
        this.checkOrCreateSubChunks( subY );
        this.subChunks[subY].setBlock( x & 15, y & 15, z & 15, layer, block );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        int subY = y >> 4;
        this.checkOrCreateSubChunks( subY );
        return this.subChunks[subY].getBlock( x & 15, y & 15, z & 15, layer );
    }

    private void checkOrCreateSubChunks( int subY ) {
        for ( int y = 0; y <= subY; y++ ) {
            if ( this.subChunks[y] == null ) {
                this.subChunks[y] = new SubChunk( y );
            }
        }
    }

    public void iterateEntities( Consumer<Entity> consumer ) {
        for ( Entity player : this.entitys ) {
            consumer.accept( player );
        }
    }

    public void writeTo( BinaryStream binaryStream ) {
        for ( SubChunk subChunk : this.subChunks )
            if ( subChunk != null ) {
                subChunk.writeTo( binaryStream );
            }

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

    public SubChunk[] getSubChunks() {
        return this.subChunks;
    }

    public void addEntity( Entity entity ) {
        if ( !this.entitys.contains( entity ) ) {
            this.entitys.add( entity );
        }
    }

    public void removeEntity( Entity entity ) {
        this.entitys.remove( entity );
    }

    public Collection<Entity> getEntitys() {
        return this.entitys;
    }
}
