package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode (callSuper = true)
public class EntityVelocityPacket extends Packet {

    private long entityId;
    private Vector velocity;

    @Override
    public int getPacketId() {
        return Protocol.ENTITY_VELOCITY_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarLong( this.entityId );
        stream.writeLFloat( this.velocity.getX() );
        stream.writeLFloat( this.velocity.getY() );
        stream.writeLFloat( this.velocity.getZ() );
    }
}
