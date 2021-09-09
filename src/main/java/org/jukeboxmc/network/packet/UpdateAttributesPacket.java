package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.utils.BinaryStream;

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
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarLong( this.entityId );

        if ( this.attributes.isEmpty() ) {
            stream.writeUnsignedVarInt( 0 );
        } else {
            stream.writeUnsignedVarInt( this.attributes.size() );

            for ( Attribute attribute : this.attributes ) {
                stream.writeLFloat( attribute.getMinValue() );
                stream.writeLFloat( attribute.getMaxValue() );
                stream.writeLFloat( attribute.getCurrentValue() );
                stream.writeLFloat( attribute.getCurrentValue() );
                stream.writeString( attribute.getKey());
            }
        }
        stream.writeUnsignedVarLong( this.tick );
    }

    public void addAttributes( Attribute attribute ) {
        if ( !this.attributes.contains( attribute ) ) {
            this.attributes.add( attribute );
        }
    }
}
