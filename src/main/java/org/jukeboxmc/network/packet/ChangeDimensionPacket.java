package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;
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
    public void write( BinaryStream stream ) {
        super.write(stream);

        stream.writeSignedVarInt( this.dimension.ordinal() );
        stream.writeLFloat( this.vector.getBlockX() );
        stream.writeLFloat( this.vector.getBlockY() );
        stream.writeLFloat( this.vector.getBlockZ() );
        stream.writeBoolean( this.respawn );
    }
}
