package org.jukeboxmc.network.packet;

import com.google.common.io.ByteStreams;
import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.network.Protocol;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AvailableActorIdentifiersPacket extends Packet {

    private static byte[] entityIdentifiers;

    static {
        try( InputStream resourceAsStream = JukeboxMC.class.getClassLoader().getResourceAsStream( "entity_identifiers.dat" ) ) {
            entityIdentifiers = ByteStreams.toByteArray(resourceAsStream);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPacketId() {
        return Protocol.AVAILABLE_ENTITY_IDENTIFIERS_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeBytes( entityIdentifiers );
    }
}
