package jukeboxmc.network.packet;

import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Packet {

    public abstract int getPacketId();

    public void read( BinaryStream stream ) {

    }

    public void write( BinaryStream stream ) {
        stream.writeUnsignedVarInt( this.getPacketId() );
    }
}
