package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.inventory.WindowTypeId;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
    public void write() {
        super.write();
        this.writeByte( this.windowId.getId() );
        this.writeByte( this.windowTypeId.getId() );
        this.writeSignedVarInt( this.position.getBlockX() );
        this.writeUnsignedVarInt( this.position.getBlockY() );
        this.writeSignedVarInt( this.position.getBlockZ() );
        this.writeSignedVarLong( this.entityId );
    }
}
