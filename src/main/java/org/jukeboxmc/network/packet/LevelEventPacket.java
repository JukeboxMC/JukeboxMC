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
public class LevelEventPacket extends Packet {

    private int eventId;
    private Vector position;
    private int data;

    @Override
    public int getPacketId() {
        return Protocol.LEVEL_EVENT_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.eventId );
        this.writeLFloat( this.position.getX() );
        this.writeLFloat( this.position.getY() );
        this.writeLFloat( this.position.getZ() );
        this.writeSignedVarInt( this.data );
    }
}
