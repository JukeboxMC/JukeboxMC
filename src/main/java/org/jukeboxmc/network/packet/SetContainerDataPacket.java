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
public class SetContainerDataPacket extends Packet {

    private byte windowId;
    private int key;
    private int value;

    @Override
    public int getPacketId() {
        return Protocol.SET_CONTAINER_DATA_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeByte( this.windowId );
        stream.writeSignedVarInt( this.key );
        stream.writeSignedVarInt( this.value );
    }
}
