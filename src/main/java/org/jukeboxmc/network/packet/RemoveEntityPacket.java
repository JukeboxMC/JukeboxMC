package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RemoveEntityPacket extends Packet {

    private long entityId;

    @Override
    public int getPacketId() {
        return Protocol.REMOVE_ENTITY_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarLong( this.entityId );
    }
}
