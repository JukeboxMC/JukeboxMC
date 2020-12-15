package org.jukeboxmc.network.packet;

import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CreativeContentPacket extends Packet {

    @Override
    public int getPacketId() {
        return Protocol.CREATIVE_CONTENT_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarInt( 0 );
    }
}
