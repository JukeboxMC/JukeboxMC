package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.packet.type.CommandOrigin;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author Kaooot
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class CommandRequestPacket extends Packet {

    private String inputCommand;
    private CommandOrigin commandOrigin;

    @Override
    public int getPacketId() {
        return Protocol.COMMAND_REQUEST_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.inputCommand = stream.readString();
        this.commandOrigin = stream.readCommandOrigin();
    }
}