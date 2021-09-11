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
public class EntityMovementPacket extends Packet {

    private static final byte FLAG_ON_GROUND = 0x1;
    private static final byte FLAG_TELEPORTED = 0x2;

    private long entityId;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float headYaw;
    private float pitch;
    private boolean onGround;
    private boolean teleported;

    @Override
    public int getPacketId() {
        return Protocol.ENTITY_MOVE_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );

        stream.writeUnsignedVarLong( this.entityId );

        byte flags = this.onGround ? FLAG_ON_GROUND : 0;
        if ( this.teleported ) {
            flags |= FLAG_TELEPORTED;
        }

        stream.writeByte( flags );

        stream.writeLFloat( this.x );
        stream.writeLFloat( this.y );
        stream.writeLFloat( this.z );

        stream.writeByte( (byte) ( this.pitch / 360f / 256f ) );
        stream.writeByte( (byte) ( this.headYaw / 360f / 256f ) );
        stream.writeByte( (byte) ( this.yaw / 360f / 256f ) );
    }
}
