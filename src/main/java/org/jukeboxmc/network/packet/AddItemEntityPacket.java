package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;

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
    public void write() {
        super.write();
        this.writeSignedVarLong( this.entityId );
        this.writeUnsignedVarLong( this.runtimeEntityId );
        this.writeItem( this.item );

        this.writeLFloat( this.vector.getX() );
        this.writeLFloat( this.vector.getY() );
        this.writeLFloat( this.vector.getZ() );

        this.writeLFloat( this.velocity.getX() );
        this.writeLFloat( this.velocity.getY() );
        this.writeLFloat( this.velocity.getZ() );

        this.writeEntityMetadata( this.metadata.getMetadata() );
        this.writeBoolean( this.isFromFishing );
    }
}
