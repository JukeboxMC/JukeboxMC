package org.jukeboxmc.network.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.network.protocol.Protocol;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
public class OpenConnectionReply2 extends Packet {

    private long serverGUID;
    private InetSocketAddress address;
    private short mtu;
    private boolean encryption = false;

    public OpenConnectionReply2() {
        super( Protocol.OPEN_CONNECTION_REPLY_2 );
    }

    @Override
    public void read() {
        super.read();
        this.serverGUID = this.buffer.readLong();
        this.address = this.readAddress();
        this.mtu = this.buffer.readShort();
        this.encryption = this.buffer.readBoolean();
    }

    @Override
    public void write() {
        super.write();
        this.writeMagic();
        this.buffer.writeLong( this.serverGUID );
        this.writeAddress( this.address );
        this.buffer.writeShort( this.mtu );
        this.buffer.writeBoolean( this.encryption );
    }
}
