package org.jukeboxmc.network.protocol.packet;

import lombok.ToString;
import org.jukeboxmc.network.raknet.utils.BinaryStream;
import org.jukeboxmc.network.raknet.utils.Zlib;

import java.util.Arrays;

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
        super.write();
        byte[] buf = Zlib.compress( this.payload ); //Old [120, -38, 99, 101, 98, 0, 2, 0, 0, 46, 0, 8] -> New [99, 101, 98, 0, 2, 0]
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
