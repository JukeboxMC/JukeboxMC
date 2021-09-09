package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SetGamemodePacket extends Packet {

    private GameMode gameMode;

    @Override
    public int getPacketId() {
        return Protocol.SET_GAMEMODE_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeSignedVarInt( this.gameMode.ordinal() );
    }
}
