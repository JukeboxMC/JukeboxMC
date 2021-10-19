package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class ResourcePackChunkDataPacket extends Packet {

    private UUID uuid;
    private int chunkIndex;
    private long progress;
    private byte[] data;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACK_CHUNK_DATA_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeString( this.uuid.toString() );
        stream.writeLInt( this.chunkIndex );
        stream.writeLLong( this.progress );
        stream.writeUnsignedVarInt( this.data.length );
        stream.writeBytes( this.data );
    }
}
