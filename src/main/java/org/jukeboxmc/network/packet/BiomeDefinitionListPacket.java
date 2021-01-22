package org.jukeboxmc.network.packet;

import com.google.common.io.ByteStreams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.network.Protocol;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class BiomeDefinitionListPacket extends Packet {

    private byte[] biomeDifination;

    public BiomeDefinitionListPacket() {
        try ( InputStream resourceAsStream = JukeboxMC.class.getClassLoader().getResourceAsStream( "biome_definitions.dat" ) ) {
            this.biomeDifination = ByteStreams.toByteArray( resourceAsStream );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPacketId() {
        return Protocol.BIOME_DEFINITION_LIST_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeBytes( this.biomeDifination );
    }
}
