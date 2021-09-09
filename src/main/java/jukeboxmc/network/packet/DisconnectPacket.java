package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class DisconnectPacket extends Packet {

    private boolean hideDisconnectScreen;
    private String disconnectMessage;

    @Override
    public int getPacketId() {
        return Protocol.DISCONNECT_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeBoolean( this.hideDisconnectScreen );

        if ( !this.hideDisconnectScreen ) {
            stream.writeString( this.disconnectMessage );
        }
    }
}
