package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class PlayerMovePacket extends Packet {

    private long entityRuntimeId;
    private float x;
    private float y;
    private float z;
    private float pitch;
    private float yaw;
    private float headYaw;
    private Mode mode;
    private boolean isOnGround;
    private long ridingEntityId;
    private int teleportCause;
    private int teleportItemId;
    private long tick;

    @Override
    public int getPacketId() {
        return Protocol.PLAYER_MOVE_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.entityRuntimeId = this.readUnsignedVarInt();
        this.x = this.readLFloat();
        this.y = this.readLFloat();
        this.z = this.readLFloat();
        this.pitch = this.readLFloat();
        this.yaw = this.readLFloat();
        this.headYaw = this.readLFloat();
        this.mode = Mode.values()[this.readByte()];
        this.isOnGround = this.readBoolean();
        this.ridingEntityId = this.readUnsignedVarLong();

        if ( this.mode == Mode.TELEPORT ) {
            this.teleportCause = this.readLInt();
            this.teleportItemId = this.readLInt();
        }
        this.tick = this.readUnsignedVarLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarLong( this.entityRuntimeId );
        this.writeLFloat( this.x );
        this.writeLFloat( this.y );
        this.writeLFloat( this.z );
        this.writeLFloat( this.pitch );
        this.writeLFloat( this.yaw );
        this.writeLFloat( this.headYaw );
        this.writeByte( this.mode.ordinal() );
        this.writeBoolean( this.isOnGround );
        this.writeUnsignedVarLong( this.ridingEntityId );

        if ( this.mode == Mode.TELEPORT ) {
            this.writeLInt( this.teleportCause );
            this.writeLInt( this.teleportItemId );
        }
        this.writeUnsignedVarLong( this.tick );
    }

    public enum Mode {
        NORMAL,
        RESET,
        TELEPORT,
        PITCH;
    }
}
