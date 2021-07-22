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
public class EntityVelocityPacket extends Packet {

    private long entityId;
    private Vector velocity;

    @Override
    public int getPacketId() {
        return Protocol.ENTITY_VELOCITY_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarLong( this.entityId );
        this.writeLFloat( this.velocity.getX() );
        this.writeLFloat( this.velocity.getY() );
        this.writeLFloat( this.velocity.getZ() );
    }
}
