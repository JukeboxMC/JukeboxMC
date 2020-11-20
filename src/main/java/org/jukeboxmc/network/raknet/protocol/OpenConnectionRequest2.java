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
public class OpenConnectionRequest2 extends Packet {

    private InetSocketAddress address;
    private short mtu;
    private long clientGUID;

    public OpenConnectionRequest2() {
        super( Protocol.OPEN_CONNECTION_REQUEST_2 );
    }

    @Override
    public void read() {
        super.read();
        this.readMagic();
        this.address = this.readAddress();
        this.mtu = this.buffer.readShort();
        this.clientGUID = this.buffer.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeMagic();
        this.writeAddress( this.address );
        this.buffer.writeShort( this.mtu );
        this.buffer.writeLong( this.clientGUID );
    }

}
