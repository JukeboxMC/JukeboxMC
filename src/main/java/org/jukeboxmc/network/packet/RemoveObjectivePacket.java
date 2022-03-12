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
public class RemoveObjectivePacket extends Packet {

    private String objectiveName;

    @Override
    public int getPacketId() {
        return Protocol.REMOVE_OBJECTIVE_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeString( this.objectiveName );
    }
}
