package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class AddItemEntityPacket extends Packet {

    private long entityId;
    private long runtimeEntityId;
    private Item item;
    private Vector vector;
    private Vector velocity;
    private Metadata metadata = new Metadata();
    public boolean isFromFishing = false;

    @Override
    public int getPacketId() {
        return Protocol.ADD_ITEM_ENTITY_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeSignedVarLong( this.entityId );
        stream.writeUnsignedVarLong( this.runtimeEntityId );
        stream.writeItem( this.item );

        stream.writeLFloat( this.vector.getX() );
        stream.writeLFloat( this.vector.getY() );
        stream.writeLFloat( this.vector.getZ() );

        stream.writeLFloat( this.velocity.getX() );
        stream.writeLFloat( this.velocity.getY() );
        stream.writeLFloat( this.velocity.getZ() );

        stream.writeEntityMetadata( this.metadata.getMetadata() );
        stream.writeBoolean( this.isFromFishing );
    }
}
