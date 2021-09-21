package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.EntityEventType;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class EntityEventPacket extends Packet {

    private long entityId;
    private EntityEventType entityEventType;
    private int data;

    @Override
    public int getPacketId() {
        return Protocol.ENTITY_EVENT_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.entityId = stream.readUnsignedVarLong();
        this.entityEventType = EntityEventType.getEntityEventById( stream.readByte() );
        this.data = stream.readSignedVarInt();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeUnsignedVarLong( this.entityId );
        stream.writeByte( this.entityEventType.getEventId() );
        stream.writeSignedVarInt( this.data );
    }
}
