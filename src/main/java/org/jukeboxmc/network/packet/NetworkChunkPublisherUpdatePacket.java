package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

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
    public void write() {
        super.write();
        this.writeSignedVarInt( this.x );
        this.writeSignedVarInt( this.y );
        this.writeSignedVarInt( this.z );
        this.writeUnsignedVarInt( this.radius );
    }
}
