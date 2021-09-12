package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlockEventPacket extends Packet {

    private Vector position;
    private int data1;
    private int data2;

    @Override
    public int getPacketId() {
        return Protocol.BLOCK_EVENT_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeSignedVarInt( this.position.getBlockX() );
        stream.writeUnsignedVarInt( this.position.getBlockY() );
        stream.writeSignedVarInt( this.position.getBlockZ() );
        stream.writeSignedVarInt( this.data1 );
        stream.writeSignedVarInt( this.data2 );
    }
}
