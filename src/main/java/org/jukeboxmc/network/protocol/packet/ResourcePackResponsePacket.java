package org.jukeboxmc.network.protocol.packet;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class ResourcePackResponsePacket extends Packet {

    private int responseStatus;

    @Override
    public int getPacketId() {
        return 0x08;
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
