package org.jukeboxmc.network.packet;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class LevelChunkPacket extends Packet {

    private int chunkX;
    private int chunkZ;
    private int subChunkCount;
    private ByteBuf data;

    @Override
    public int getPacketId() {
        return Protocol.LEVEL_CHUNK_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.chunkX );
        this.writeSignedVarInt( this.chunkZ );
        this.writeUnsignedVarInt( this.subChunkCount );
        this.writeBoolean( false ); //Chached
        this.writeUnsignedVarInt( this.data.readableBytes() );
        this.writeBuffer( this.data );
    }
}
