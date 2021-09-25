package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.world.Dimension;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SetSpawnPositionPacket extends Packet {

    private SpawnType spawnType;
    private Vector playerPosition;
    private Dimension dimension = Dimension.OVERWORLD;
    private Vector worldSpawn;

    @Override
    public int getPacketId() {
        return Protocol.SET_SPAWN_POSITION_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeSignedVarInt( this.spawnType.ordinal() );

        stream.writeSignedVarInt( this.playerPosition.getBlockX() );
        stream.writeUnsignedVarInt( this.playerPosition.getBlockY() );
        stream.writeSignedVarInt( this.playerPosition.getBlockZ() );

        stream.writeSignedVarInt( this.dimension.ordinal() );

        stream.writeSignedVarInt( this.worldSpawn.getBlockX() );
        stream.writeUnsignedVarInt( this.worldSpawn.getBlockY() );
        stream.writeSignedVarInt( this.worldSpawn.getBlockZ() );
    }

    public enum SpawnType {
        PLAYER,
        WORLD
    }
}
