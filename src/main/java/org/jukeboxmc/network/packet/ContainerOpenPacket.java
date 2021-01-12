package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.inventory.WindowType;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContainerOpenPacket extends Packet {

    private WindowId windowId;
    private WindowType windowType;
    private BlockPosition position;
    private long entityId;

    @Override
    public int getPacketId() {
        return Protocol.CONTAINER_OPEN_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeByte( this.windowId.getId() );
        this.writeByte( this.windowType.getId() );
        this.writeSignedVarInt( this.position.getX() );
        this.writeUnsignedVarInt( this.position.getY() );
        this.writeSignedVarInt( this.position.getZ() );
        this.writeSignedVarLong( this.entityId );
    }
}
