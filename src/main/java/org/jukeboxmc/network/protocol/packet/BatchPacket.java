package org.jukeboxmc.network.protocol.packet;

import io.netty.buffer.Unpooled;
import lombok.ToString;
import org.jukeboxmc.network.raknet.utils.BinaryStream;
import org.jukeboxmc.network.raknet.utils.Zlib;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class BatchPacket extends Packet {

    public byte[] payload = new byte[0];

    @Override
    public int getPacketId() {
        return 0xfe;
    }

    @Override
    public void read() {
        this.payload = new byte[this.readableBytes()];
        this.readBytes( this.payload );
    }

    @Override
    public void write() {
        this.writeByte( this.getPacketId() & 0xFF );
        byte[] buf = Zlib.compress( this.payload );
        this.writeBytes( buf );
    }

    public void addPacket( Packet packet ) {
        packet.write();
        BinaryStream stream = new BinaryStream();
        stream.writeUnsignedVarInt( packet.getArray().length );
        stream.writeBuffer( packet.getBuffer() );
        this.payload = stream.getArray();
    }

}
