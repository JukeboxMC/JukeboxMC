package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.network.Protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class UpdateAttributesPacket extends Packet {

    private long entityId;
    private List<Attribute> attributes = new ArrayList<>();
    private long tick;

    @Override
    public int getPacketId() {
        return Protocol.UPDATE_ATTRIBUTES_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarLong( this.entityId );

        if ( this.attributes.isEmpty() ) {
            this.writeUnsignedVarInt( 0 );
        } else {
            this.writeUnsignedVarInt( this.attributes.size() );

            for ( Attribute attribute : this.attributes ) {
                this.writeLFloat( attribute.getMinValue() );
                this.writeLFloat( attribute.getMaxValue() );
                this.writeLFloat( attribute.getCurrentValue() );
                this.writeLFloat( attribute.getCurrentValue() ); //Default
                this.writeString( attribute.getAttributeType().getName() );
            }
        }
        this.writeUnsignedVarLong( this.tick );
    }
}
