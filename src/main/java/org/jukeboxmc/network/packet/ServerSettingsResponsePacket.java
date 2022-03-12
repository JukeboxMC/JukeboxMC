package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class ServerSettingsResponsePacket extends Packet {

    private int formId;
    private String json;

    @Override
    public int getPacketId() {
        return Protocol.SERVER_SETTINGS_RESPONSE_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeSignedVarInt( this.formId );
        stream.writeString( this.json );
    }
}
