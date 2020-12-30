package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.entity.metadata.MetadataValue;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class AddEntityPacket extends Packet {

    private long entityId;
    private String entityType;
    private float x;
    private float y;
    private float z;
    private Vector velocity;
    private float pitch;
    private float headYaw;
    private float yaw;
    private List<Attribute> attributes = new ArrayList<>();
    private Metadata metadata;

    @Override
    public int getPacketId() {
        return Protocol.ADD_ENTITY_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarLong( this.entityId );
        this.writeUnsignedVarLong( this.entityId );

        this.writeString( this.entityType );

        this.writeLFloat( this.x );
        this.writeLFloat( this.y );
        this.writeLFloat( this.z );

        this.writeLFloat( this.velocity.getX() );
        this.writeLFloat( this.velocity.getY() );
        this.writeLFloat( this.velocity.getZ() );

        this.writeLFloat( this.pitch );
        this.writeLFloat( this.headYaw );
        this.writeLFloat( this.yaw );

        //Attribute
        this.writeUnsignedVarInt( this.attributes.size() );
        for ( Attribute attribute : this.attributes ) {
            this.writeString( attribute.getAttributeType().getName() );
            this.writeLFloat( attribute.getMinValue() );
            this.writeLFloat( attribute.getCurrentValue() );
            this.writeLFloat( attribute.getMaxValue() );
        }

        if(this.metadata != null) {
            this.writeEntityMetadata( this.metadata.getMetadata() );
        } else {
            this.writeUnsignedVarInt( 0 );
        }

        this.writeUnsignedVarInt( 0 );
    }
}
