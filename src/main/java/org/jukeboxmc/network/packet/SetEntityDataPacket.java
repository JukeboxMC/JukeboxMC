package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SetEntityDataPacket extends Packet {

    private long entityId;
    private Metadata metadata;
    private long tick;

    @Override
    public int getPacketId() {
        return Protocol.SET_ENTITY_DATA_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarLong( this.entityId );
        this.writeEntityMetadata( this.metadata.getMetadata() );
        this.writeUnsignedVarLong( this.tick );
    }
}
