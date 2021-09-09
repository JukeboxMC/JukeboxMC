package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.inventory.WindowTypeId;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode (callSuper = true)
public class ContainerOpenPacket extends Packet {

    private WindowId windowId;
    private WindowTypeId windowTypeId;
    private Vector position;
    private long entityId;

    @Override
    public int getPacketId() {
        return Protocol.CONTAINER_OPEN_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeByte( this.windowId.getId() );
        stream.writeByte( this.windowTypeId.getId() );
        stream.writeSignedVarInt( this.position.getBlockX() );
        stream.writeUnsignedVarInt( this.position.getBlockY() );
        stream.writeSignedVarInt( this.position.getBlockZ() );
        stream.writeSignedVarLong( this.entityId );
    }
}
