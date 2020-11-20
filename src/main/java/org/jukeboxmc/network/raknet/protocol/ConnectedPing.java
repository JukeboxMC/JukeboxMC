package org.jukeboxmc.network.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.network.protocol.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
public class ConnectedPing extends Packet{

    private long clientTimestamp;

    public ConnectedPing() {
        super( Protocol.CONNECTED_PING );
    }

    @Override
    public void read() {
        super.read();
        this.clientTimestamp = this.buffer.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.buffer.writeLong( this.clientTimestamp );
    }

}
