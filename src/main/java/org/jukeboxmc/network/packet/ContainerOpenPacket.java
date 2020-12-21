package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContainerOpenPacket extends Packet {

    private byte windowId;
    private byte containerType;
    private Vector position;
    private long entityId;

    @Override
    public int getPacketId() {
        return Protocol.CONTAINER_OPEN_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeByte( this.windowId );
        this.writeByte( this.containerType );
        this.writeSignedVarInt( this.position.getFloorX() );
        this.writeUnsignedVarInt( this.position.getFloorY() );
        this.writeSignedVarInt( this.position.getFloorZ() );
        this.writeSignedVarLong( this.entityId );
    }
}
