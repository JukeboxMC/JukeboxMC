package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.player.info.DeviceInfo;

import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class AddPlayerPacket extends Packet {

    private UUID uuid;
    private String name;
    private long entityId;
    private long runtimeEntityId;
    private String platformChatId;

    private float x;
    private float y;
    private float z;

    private Vector velocity;

    private float pitch;
    private float headYaw;
    private float yaw;

    private Item item;
    private Metadata metadata;

    private DeviceInfo deviceInfo;

    @Override
    public int getPacketId() {
        return Protocol.ADD_PLAYER_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeUUID( this.uuid );
        this.writeString( this.name );
        this.writeSignedVarLong( this.entityId );
        this.writeUnsignedVarLong( this.runtimeEntityId );
        this.writeString( this.platformChatId == null ? "" : this.platformChatId );

        this.writeLFloat( this.x );
        this.writeLFloat( this.y );
        this.writeLFloat( this.z );

        this.writeLFloat( this.velocity.getX() );
        this.writeLFloat( this.velocity.getY() );
        this.writeLFloat( this.velocity.getZ() );

        this.writeLFloat( this.pitch );
        this.writeLFloat( this.headYaw );
        this.writeLFloat( this.yaw );

        this.writeItem( this.item );
        this.writeEntityMetadata( this.metadata.getMetadata() );

        for ( int i = 0; i < 5; i++ ) {
            this.writeUnsignedVarInt( 0 );
        }

        this.writeLLong( this.entityId );
        this.writeUnsignedVarInt( 0 );
        this.writeString( this.deviceInfo.getDeviceId() );
        this.writeLInt( this.deviceInfo.getDevice().getId() );
    }
}
