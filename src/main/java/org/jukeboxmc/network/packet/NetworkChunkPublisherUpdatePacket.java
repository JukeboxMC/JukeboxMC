package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class NetworkChunkPublisherUpdatePacket extends Packet {

    private int x;
    private int y;
    private int z;
    private int radius;

    @Override
    public int getPacketId() {
        return Protocol.NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeSignedVarInt( this.x );
        stream.writeSignedVarInt( this.y );
        stream.writeSignedVarInt( this.z );
        stream.writeUnsignedVarInt( this.radius );
    }
}
