package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode (callSuper = true)
public class SetEntityDataPacket extends Packet {

    private long entityId;
    private Metadata metadata;
    private long tick;

    @Override
    public int getPacketId() {
        return Protocol.SET_ENTITY_DATA_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarLong( this.entityId );
        stream.writeEntityMetadata( this.metadata.getMetadata() );
        stream.writeUnsignedVarLong( this.tick );
    }
}
