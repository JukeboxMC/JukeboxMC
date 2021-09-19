package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.type.PlayerAction;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class PlayerActionPacket extends Packet {

    private long entityId;
    private PlayerAction action;
    private Vector position;
    private BlockFace blockFace;

    @Override
    public int getPacketId() {
        return Protocol.PLAYER_ACTION_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.entityId = stream.readUnsignedVarLong();
        this.action = PlayerAction.values()[stream.readSignedVarInt()];
        this.position = new Vector( stream.readSignedVarInt(), stream.readUnsignedVarInt(), stream.readSignedVarInt() );
        this.blockFace = stream.readBlockFace();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeUnsignedVarLong( this.entityId );
        stream.writeSignedVarInt( this.action.ordinal() );
        stream.writeSignedVarInt( this.position.getBlockX() );
        stream.writeUnsignedVarInt( this.position.getBlockY() );
        stream.writeSignedVarInt( this.position.getBlockZ() );
        stream.writeSignedVarInt( this.blockFace.ordinal() );
    }
}
