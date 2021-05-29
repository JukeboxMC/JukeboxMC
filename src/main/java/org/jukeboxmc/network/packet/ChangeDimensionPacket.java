package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.world.Dimension;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class ChangeDimensionPacket extends Packet {

    private Dimension dimension;
    private Vector vector;
    private boolean respawn;

    @Override
    public int getPacketId() {
        return Protocol.CHANGE_DIMENSION_PACKET;
    }

    @Override
    public void write() {
        super.write();

        this.writeSignedVarInt( this.dimension.getId() );
        this.writeLFloat( this.vector.getFloorX() );
        this.writeLFloat( this.vector.getFloorY() );
        this.writeLFloat( this.vector.getFloorZ() );
        this.writeBoolean( this.respawn );
    }
}
