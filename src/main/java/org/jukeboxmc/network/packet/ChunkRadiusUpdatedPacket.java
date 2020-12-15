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
public class ChunkRadiusUpdatedPacket extends Packet {

    private int chunkRadius;

    @Override
    public int getPacketId() {
        return Protocol.CHUNK_RADIUS_UPDATE_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.chunkRadius );
    }
}
