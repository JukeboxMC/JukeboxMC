package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RespawnPositionPacket extends Packet {

    private long entityId;
    private Vector position;
    private RespawnState respawnState;

    @Override
    public int getPacketId() {
        return Protocol.RESPAWN_POSITION_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.position = new Vector( stream.readLFloat(), stream.readLFloat(), stream.readLFloat() );
        this.respawnState = RespawnState.values()[stream.readByte()];
        this.entityId = stream.readUnsignedVarLong();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeLFloat( this.position.getX() );
        stream.writeLFloat( this.position.getY() );
        stream.writeLFloat( this.position.getZ() );
        stream.writeByte( this.respawnState.ordinal() );
        stream.writeUnsignedVarLong( this.entityId );
    }

    public enum RespawnState {
        SEARCHING_FOR_SPAWN,
        READY_TO_SPAWN,
        CLIENT_READY_TO_SPAWN
    }
}
