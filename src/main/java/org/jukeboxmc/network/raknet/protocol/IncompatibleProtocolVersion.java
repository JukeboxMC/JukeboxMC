package org.jukeboxmc.network.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
public class IncompatibleProtocolVersion extends RakNetPacket {

    private byte protocol;
    private long serverGUID;

    public IncompatibleProtocolVersion() {
        super( Protocol.INCOMPATIBLE_PROTOCOL_VERSION );
    }

    @Override
    public void write() {
        super.write();
        this.buffer.writeByte( this.protocol );
        this.writeMagic();
        this.buffer.writeLong( this.serverGUID );
    }

}
