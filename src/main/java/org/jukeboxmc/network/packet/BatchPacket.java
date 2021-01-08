package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.network.raknet.utils.Zlib;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@ToString
@EqualsAndHashCode ( callSuper = true )
public class BatchPacket extends Packet {

    public byte[] payload = new byte[0];

    @Override
    public int getPacketId() {
        return Protocol.BATCH_PACKET;
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
        stream.writeBytes( this.payload );
        stream.writeUnsignedVarInt( packet.getArray().length );
        stream.writeBuffer( packet.getBuffer() );
        this.payload = stream.getArray();
    }

}
