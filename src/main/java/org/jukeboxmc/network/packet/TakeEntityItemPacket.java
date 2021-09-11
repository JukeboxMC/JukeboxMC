package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TakeEntityItemPacket extends Packet {

    private long entityItemId;
    private long entityId;

    @Override
    public int getPacketId() {
        return Protocol.TAKE_ENTITY_ITEM_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeUnsignedVarLong( this.entityItemId );
        stream.writeUnsignedVarLong( this.entityId );
    }
}
