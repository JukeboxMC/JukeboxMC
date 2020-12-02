package org.jukeboxmc.network.protocol.packet;

import org.jukeboxmc.network.raknet.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Packet extends BinaryStream {

    public abstract int getPacketId();

    public void read() {

    }

    public void write() {
        this.writeUnsignedVarInt( this.getPacketId() );
    }

}
