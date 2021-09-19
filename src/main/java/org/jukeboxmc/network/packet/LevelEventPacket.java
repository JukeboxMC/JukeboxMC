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
public class LevelEventPacket extends Packet {

    private int levelEventId;
    private Vector position;
    private int data;

    @Override
    public int getPacketId() {
        return Protocol.LEVEL_EVENT_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeSignedVarInt( this.levelEventId );
        stream.writeLFloat( this.position.getX() );
        stream.writeLFloat( this.position.getY() );
        stream.writeLFloat( this.position.getZ() );
        stream.writeSignedVarInt( this.data );
    }
}
