package org.jukeboxmc.network.packet;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeSignedVarInt( this.chunkX );
        stream.writeSignedVarInt( this.chunkZ );
        stream.writeUnsignedVarInt( this.subChunkCount );
        stream.writeBoolean( false );
        stream.writeUnsignedVarInt( this.data.readableBytes() );
        stream.writeBuffer( this.data );
    }
}
