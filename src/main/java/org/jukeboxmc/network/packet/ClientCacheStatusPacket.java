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
public class ClientCacheStatusPacket extends Packet {

    private boolean support;

    @Override
    public int getPacketId() {
        return Protocol.CLIENT_CACHE_STATUS_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.support = stream.readBoolean();
    }
}
