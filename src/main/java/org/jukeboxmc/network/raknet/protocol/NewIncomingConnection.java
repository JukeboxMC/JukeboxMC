package org.jukeboxmc.network.raknet.protocol;

import lombok.Getter;
import org.jukeboxmc.network.protocol.Protocol;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class NewIncomingConnection extends Packet {

    @Getter
    private InetSocketAddress address;

    private InetSocketAddress[] systemAddress = new InetSocketAddress[]{
            new InetSocketAddress( "127.0.0.1", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 ),
            new InetSocketAddress( "0.0.0.0", 0 ), new InetSocketAddress( "0.0.0.0", 0 )
    };

    private long requestTimestamp;
    private long acceptedTimestamp;

    public NewIncomingConnection() {
        super( Protocol.NEW_INCOMING_CONNECTION );
    }

    @Override
    public void read() {
        super.read();
        this.address = this.readAddress();

        this.requestTimestamp = this.buffer.readLong();
        this.acceptedTimestamp = this.buffer.readLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeAddress( this.address );
        for ( int i = 0; i < 10; i++ ) {
            this.writeAddress( this.systemAddress[i] );
        }
        this.buffer.writeLong( this.requestTimestamp );
        this.buffer.writeLong( this.acceptedTimestamp );
    }
}
