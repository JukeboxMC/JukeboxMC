package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EntityFallPacket extends Packet {

    private long entityId;
    private float fallDistance;
    private boolean inVoid;

    @Override
    public int getPacketId() {
        return Protocol.ENTITY_FALL_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.entityId = stream.readUnsignedVarLong();
        this.fallDistance = stream.readLFloat();
        this.inVoid = stream.readBoolean();
    }
}
