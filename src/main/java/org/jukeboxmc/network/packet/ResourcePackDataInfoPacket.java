package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.packet.type.ResourcePackDataInfoType;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class ResourcePackDataInfoPacket extends Packet {

    private String resourcePackUuid;
    private int maxChunkSize;
    private int chunkCount;
    private long compressedResourcePackSize;
    private byte[] resourcePackSha256;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACK_DATA_INFO_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeString( this.resourcePackUuid );
        stream.writeLInt( this.maxChunkSize );
        stream.writeLInt( this.chunkCount );
        stream.writeLLong( this.compressedResourcePackSize );
        stream.writeUnsignedVarInt( this.resourcePackSha256.length );
        stream.writeBytes( this.resourcePackSha256 );
        stream.writeBoolean( false ); // premium
        stream.writeByte( (byte) ResourcePackDataInfoType.RESOURCE.ordinal() );
    }
}
