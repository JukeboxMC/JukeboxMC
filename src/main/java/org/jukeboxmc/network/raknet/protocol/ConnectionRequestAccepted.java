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
public class ConnectionRequestAccepted extends RakNetPacket {

    private InetSocketAddress address;
    private InetSocketAddress[] systemAddress = new InetSocketAddress[]{
            new InetSocketAddress( "127.0.0.1", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 )
    };
    private long requestTimestamp; //Ping
    private long acceptedTimestamp; //Pong

    public ConnectionRequestAccepted() {
        super( Protocol.CONNECTION_REQUEST_ACCEPTED );
    }

    @Override
    public void read() {
        super.read();
        this.address = this.readAddress();
        this.buffer.readShort();
        for ( int i = 0; i < 10; i++ ) {
            this.systemAddress[i] = this.readAddress();
        }
        this.requestTimestamp = this.buffer.readLong();
        this.acceptedTimestamp = this.buffer.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeAddress( this.address );
        this.buffer.writeShort( (short) 0 );
        for ( int i = 0; i < 10; i++ ) {
            this.writeAddress( this.systemAddress[i] );
        }
        this.buffer.writeLong( this.requestTimestamp );
        this.buffer.writeLong( this.acceptedTimestamp );
    }
}
