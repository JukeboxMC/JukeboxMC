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
public class ResourcePackChunkRequestPacket extends Packet {

    private UUID uuid;
    private int chunkIndex;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACK_CHUNK_REQUEST_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.uuid = UUID.fromString( stream.readString() );
        this.chunkIndex = stream.readLInt();
    }
}
