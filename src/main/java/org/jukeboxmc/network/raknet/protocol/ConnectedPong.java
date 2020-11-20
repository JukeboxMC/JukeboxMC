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
public class ConnectedPong extends Packet {

    private long clientTimestamp;
    private long serverTimestamp;

    public ConnectedPong() {
        super( Protocol.CONNECTED_PONG );
    }

    @Override
    public void read() {
        super.read();
        this.clientTimestamp = this.buffer.readLong();
        this.serverTimestamp = this.buffer.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.buffer.writeLong( this.clientTimestamp );
        this.buffer.writeLong( this.serverTimestamp );
    }

}
