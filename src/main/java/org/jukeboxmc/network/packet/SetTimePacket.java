package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SetTimePacket extends Packet {

    private int time;

    @Override
    public int getPacketId() {
        return Protocol.SET_TIME_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.time );
    }
}
