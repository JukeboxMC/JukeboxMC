package org.jukeboxmc.network.protocol.packet;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BatchPacket extends Packet {

    public byte[] payload;

    @Override
    public int getPacketId() {
        return 0xfe;
    }

    @Override
    public void read() {
        this.payload = new byte[this.buffer.readableBytes()];
        this.buffer.readBytes( this.payload );
    }

    @Override
    public void write() {
    }


}
