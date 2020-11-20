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
public class UnconnectedPong extends Packet {

    private long time;
    private long serverGUID;
    private String serverID;

    public UnconnectedPong() {
        super( Protocol.UNCONNECTED_PONG );
    }

    @Override
    public void write() {
        super.write();
        this.buffer.writeLong( this.time );
        this.buffer.writeLong( this.serverGUID );
        this.writeMagic();
        this.writeString( this.serverID );
    }

}
