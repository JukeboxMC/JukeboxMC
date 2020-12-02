package org.jukeboxmc.player;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.jukeboxmc.network.protocol.Protocol;
import org.jukeboxmc.network.protocol.packet.BatchPacket;
import org.jukeboxmc.network.protocol.packet.Packet;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.protocol.EncapsulatedPacket;
import org.jukeboxmc.network.raknet.utils.BinaryStream;
import org.jukeboxmc.network.raknet.utils.Zlib;

import java.nio.ByteBuffer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerConnection {

    private Connection connection;

    public PlayerConnection( Connection connection ) {
        this.connection = connection;
    }

    public static byte[] appendBytes( byte[][] bytes ) {
        int length = 0;
        for ( byte[] b : bytes ) {
            length += b.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate( length );
        for ( byte[] b : bytes ) {
            buffer.put( b );
        }
        return buffer.array();
    }

    public static byte[] appendBytes(byte byte1, byte[]... bytes2) {
        int length = 1;
        for (byte[] bytes : bytes2) {
            length += bytes.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.put(byte1);
        for (byte[] bytes : bytes2) {
            buffer.put(bytes);
        }
        return buffer.array();
    }

    //TEST

    public void sendPacket( Packet packet ) {
        System.out.println( "C" );
        byte[] buffer;
        if ( packet.getPacketId() == Protocol.BATCH_PACKET ) {
            System.out.println( "Sending: " + packet.getClass().getSimpleName() );
            buffer = ( (BatchPacket) packet ).payload;
        } else {
            System.out.println( "Sending: " + packet.getClass().getSimpleName() );
            this.batchPacket( packet );
            return;
        }
        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.buffer = Unpooled.wrappedBuffer( appendBytes( (byte) 0xfe, buffer ) );
        encapsulatedPacket.reliability = 2; //Maybe 0
        this.connection.addEncapsulatedToQueue( encapsulatedPacket, Connection.Priority.NORMAL );
    }

    public void batchPacket( Packet packet ) {
        System.out.println( packet.toString() );
        byte[][] payload = new byte[2][];
        packet.write();
        BinaryStream stream = new BinaryStream(1 );
        byte[] buffer = packet.getBuffer().array();
        stream.writeUnsignedVarInt( buffer.length );
        payload[2] = stream.getBuffer().array();
        payload[3] = buffer;

        byte[] data = appendBytes( payload );
        BatchPacket batchPacket = new BatchPacket();
        batchPacket.payload = Zlib.compress( data );

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.buffer = Unpooled.wrappedBuffer( appendBytes( (byte) 0xfe, batchPacket.payload ) );
        encapsulatedPacket.reliability = 2; //Maybe 0
        this.connection.addEncapsulatedToQueue( encapsulatedPacket, Connection.Priority.NORMAL );
    }

}
