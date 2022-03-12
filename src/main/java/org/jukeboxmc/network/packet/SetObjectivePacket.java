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
public class SetObjectivePacket extends Packet {

    private String displaySlot;
    private String objectiveName;
    private String displayName;
    private String criteriaName;
    private int sortOrder;

    @Override
    public int getPacketId() {
        return Protocol.SET_OBJECTIVE_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeString( this.displaySlot );
        stream.writeString( this.objectiveName );
        stream.writeString( this.displayName );
        stream.writeString( this.criteriaName );
        stream.writeSignedVarInt( this.sortOrder );
    }
}
