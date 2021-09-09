package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.type.InteractAction;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class InteractPacket extends Packet {

    private InteractAction action;
    private long target;
    private Vector position;

    @Override
    public int getPacketId() {
        return Protocol.INTERACT_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.action = InteractAction.values()[stream.readByte()];
        this.target = stream.readUnsignedVarLong();

        if ( this.action == InteractAction.MOUSEOVER ) {
            if ( stream.getBuffer().readableBytes() > 0 ) {
                this.position = new Vector( stream.readLFloat(), stream.readLFloat(), stream.readLFloat() );
            }
        }
    }
}
