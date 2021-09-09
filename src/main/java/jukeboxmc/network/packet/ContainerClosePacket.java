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
public class ContainerClosePacket extends Packet {

    private int windowId;
    private boolean isServerInitiated;

    @Override
    public int getPacketId() {
        return Protocol.CONTAINER_CLOSE_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.windowId = stream.readByte();
        this.isServerInitiated = stream.readBoolean();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeByte( this.windowId );
        stream.writeBoolean( this.isServerInitiated );
    }
}
