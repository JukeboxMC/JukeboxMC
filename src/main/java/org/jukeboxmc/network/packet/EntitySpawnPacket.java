package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class EntitySpawnPacket extends Packet {

    private long entityId;
    private EntityType entityType;
    private Vector position;
    private Vector velocity;
    private float pitch;
    private float headYaw;
    private float yaw;
    private List<Attribute> attributes;
    private Metadata metadata;

    @Override
    public int getPacketId() {
        return Protocol.ENTITY_SPAWN_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );

        stream.writeSignedVarLong( this.entityId );
        stream.writeUnsignedVarLong( this.entityId );
        stream.writeString( this.entityType.getIdentifier() );
        stream.writeLFloat( this.position.getX() );
        stream.writeLFloat( this.position.getY() );
        stream.writeLFloat( this.position.getZ() );
        stream.writeLFloat( this.velocity.getX() );
        stream.writeLFloat( this.velocity.getY() );
        stream.writeLFloat( this.velocity.getZ() );
        stream.writeLFloat( this.pitch );
        stream.writeLFloat( this.yaw );
        stream.writeLFloat( this.headYaw );

        if ( this.attributes == null ) {
            stream.writeUnsignedVarInt( 0 );
        } else {
            stream.writeUnsignedVarInt( this.attributes.size() );
            for ( Attribute attribute : this.attributes ) {
                stream.writeString( attribute.getKey() );
                stream.writeLFloat( attribute.getMinValue() );
                stream.writeLFloat( attribute.getCurrentValue() );
                stream.writeLFloat( attribute.getMaxValue() );
            }
        }
        stream.writeEntityMetadata( this.metadata.getMetadata() );
        stream.writeUnsignedVarInt( 0 );
    }
}
