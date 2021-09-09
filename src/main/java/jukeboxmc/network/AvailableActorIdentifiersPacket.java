package jukeboxmc.network;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.Protocol;
import org.jukeboxmc.utils.BedrockResourceLoader;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AvailableActorIdentifiersPacket extends Packet {

    @Override
    public int getPacketId() {
        return Protocol.AVAILABLE_ENTITY_IDENTIFIERS_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeBytes( BedrockResourceLoader.getEntityIdentifiers() );
    }
}
