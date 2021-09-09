package jukeboxmc.network.packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jukeboxmc.network.packet.type.PlayStatus;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode ( callSuper = true )
public class PlayStatusPacket extends Packet {

    private PlayStatus status;

    @Override
    public int getPacketId() {
        return Protocol.PLAY_STATUS_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeInt( this.status.ordinal() );
    }
}
