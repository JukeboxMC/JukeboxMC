package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BedrockResourceLoader;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class BiomeDefinitionListPacket extends Packet {

    @Override
    public int getPacketId() {
        return Protocol.BIOME_DEFINITION_LIST_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeBytes( BedrockResourceLoader.getBiomeDefinitions() );
    }
}
