package org.jukeboxmc.network.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.network.Protocol;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
public class OpenConnectionReply1 extends RakNetPacket {

    private long serverGUID;
    private InetSocketAddress address;
    private boolean security;
    private short mtu;

    public OpenConnectionReply1() {
        super( Protocol.OPEN_CONNECTION_REPLY_1 );
    }

    @Override
    public void read() {
        super.read();
        this.serverGUID = this.buffer.readLong();
        this.security = this.buffer.readBoolean();
        this.mtu = this.buffer.readShort();
    }

    @Override
    public void write() {
        super.write();
        this.writeMagic();
        this.buffer.writeLong( this.serverGUID );
        this.buffer.writeBoolean( false );
        this.buffer.writeShort( this.mtu );
    }

}
