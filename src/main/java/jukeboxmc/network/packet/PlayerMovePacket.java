package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.packet.type.PlayerMoveMode;
import org.jukeboxmc.utils.BinaryStream;

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
    private PlayerMoveMode mode;
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
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.entityRuntimeId = stream.readUnsignedVarInt();
        this.x = stream.readLFloat();
        this.y = stream.readLFloat();
        this.z = stream.readLFloat();
        this.pitch = stream.readLFloat();
        this.yaw = stream.readLFloat();
        this.headYaw = stream.readLFloat();
        this.mode = PlayerMoveMode.values()[stream.readByte()];
        this.isOnGround = stream.readBoolean();
        this.ridingEntityId = stream.readUnsignedVarLong();

        if ( this.mode == PlayerMoveMode.TELEPORT ) {
            this.teleportCause = stream.readLInt();
            this.teleportItemId = stream.readLInt();
        }
        this.tick = stream.readUnsignedVarLong();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarLong( this.entityRuntimeId );
        stream.writeLFloat( this.x );
        stream.writeLFloat( this.y );
        stream.writeLFloat( this.z );
        stream.writeLFloat( this.pitch );
        stream.writeLFloat( this.yaw );
        stream.writeLFloat( this.headYaw );
        stream.writeByte( this.mode.ordinal() );
        stream.writeBoolean( this.isOnGround );
        stream.writeUnsignedVarLong( this.ridingEntityId );

        if ( this.mode == PlayerMoveMode.TELEPORT ) {
            stream.writeLInt( this.teleportCause );
            stream.writeLInt( this.teleportItemId );
        }
        stream.writeUnsignedVarLong( this.tick );
    }
}
