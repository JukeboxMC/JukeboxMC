package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class DisconnectPacket extends Packet {

    private boolean hideDisconnectScreen;
    private String message;

    @Override
    public int getPacketId() {
        return Protocol.DISCONNECT_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeBoolean( this.hideDisconnectScreen );
        if ( !this.hideDisconnectScreen ) {
            this.writeString( this.message );
        }
    }
}
