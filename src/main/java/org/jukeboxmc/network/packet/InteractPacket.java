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
@EqualsAndHashCode ( callSuper = true )
public class InteractPacket extends Packet {

    private Action action;
    private long target;
    private Vector position;

    @Override
    public int getPacketId() {
        return Protocol.INTERACT_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.action = Action.values()[this.readByte()];
        this.target = this.readUnsignedVarLong();

        if ( this.action == Action.MOUSEOVER ) {
            if ( this.getBuffer().readableBytes() > 0 ) {
                this.position = new Vector( this.readLFloat(), this.readLFloat(), this.readLFloat() );
            }
        }
    }

    public enum Action {
        NONE,
        INTERACT,
        ATTACK,
        LEAVE_VEHICLE,
        MOUSEOVER,
        OPEN_NPC,
        OPEN_INVENTORY
    }
}
