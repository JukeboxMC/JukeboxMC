package org.jukeboxmc.network.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class ConnectionRequest extends RakNetPacket {

    private long clientGUID;
    private long requestTimestamp;
    private boolean security;

    public ConnectionRequest( ) {
        super( Protocol.CONNECTION_REQUEST );
    }

    @Override
    public void read() {
        super.read();
        this.clientGUID = this.buffer.readLong();
        this.requestTimestamp = this.buffer.readLong();
        this.security = this.buffer.readBoolean(); //Secure
    }

    @Override
    public void write() {
        super.write();
        this.buffer.writeLong( this.clientGUID );
        this.buffer.writeLong( this.requestTimestamp );
        this.buffer.writeBoolean( false );
    }

}
