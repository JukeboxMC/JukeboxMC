package org.jukeboxmc.network.packet;

import lombok.ToString;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class ResourcePackResponsePacket extends Packet {

    private int responseStatus;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACK_RESPONSE_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.responseStatus = this.readByte();
        short entries = this.readLShort();
        for(int i = 0; i < entries; i++) {
            this.readString(); //Ignore
        }
    }
}
