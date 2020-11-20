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
public class UnconnectedPing extends Packet {

    private long time;
    private long clientGUID;

    public UnconnectedPing() {
        super( Protocol.UNCONNECTED_PING );
    }

    @Override
    public void read() {
        super.read();
        this.time = this.buffer.readLong();
        this.clientGUID = this.buffer.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.buffer.writeLong( this.time );
        this.writeMagic();
        this.buffer.writeLong( this.clientGUID );
    }
}
