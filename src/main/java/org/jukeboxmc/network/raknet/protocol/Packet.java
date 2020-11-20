package org.jukeboxmc.network.raknet.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Packet {

    public ByteBuf buffer = Unpooled.buffer( 0 );

    public int packetId;

    public Packet( byte packetId ) {
        this.packetId = packetId;
    }

    public void read() {
        this.buffer.retain();
        this.packetId = this.buffer.readUnsignedByte();
    }

    public void write() {
        this.buffer.retain();
        this.buffer.writeByte( this.packetId );
    }

    private void expectedLong( long expected ) {
        long got = this.buffer.readLong();
        if ( expected != got ) throw new RuntimeException();
    }

    public void readMagic() {
        this.expectedLong( 0x00ffff00fefefefeL );
        this.expectedLong( 0xfdfdfdfd12345678L );
    }

    public void writeMagic() {
        this.buffer.writeLong( 0x00ffff00fefefefeL );
        this.buffer.writeLong( 0xfdfdfdfd12345678L );
    }

    public String readString() {
        short length = this.buffer.readShort();
        byte[] bytes = new byte[length];
        this.buffer.readBytes( buffer );
        return new String( bytes );
    }

    public void writeString( String value ) {
        this.buffer.writeShort( value.length() );
        this.buffer.writeBytes( value.getBytes() );
    }

    @SneakyThrows
    public InetSocketAddress readAddress() {
        int version = this.buffer.readUnsignedByte();
        int size;
        if ( version == 4 ) {
            size = 4;
        } else if ( version == 6 ) {
            size = 16;
        } else {
            throw new RuntimeException("Version: " + version);
        }

        byte[] raw = new byte[size];
        for ( int i = 0; i < size; i++ ) {
            raw[i] = this.buffer.readByte();
        }
        InetAddress addr = InetAddress.getByAddress( raw );

        int port = this.buffer.readUnsignedShort();
        return new InetSocketAddress( addr, port );
    }

    public void writeAddress( InetSocketAddress address ) {
        byte[] bytes = address.getAddress().getAddress();
        this.buffer.writeByte( bytes.length == 4 ? 4 : 6 );
        for ( byte b : bytes ) this.buffer.writeByte( b & 0xFF );
        this.buffer.writeShort( (short) address.getPort() );
    }

    public int consumePadding() {
        int value = this.buffer.readableBytes();
        while ( this.buffer.readableBytes() > 0 ) {
            this.expectByte();
        }
        return value;
    }

    private void expectByte() {
        int readedByte = this.buffer.readByte();
        if ( readedByte != 0 ) throw new RuntimeException();
    }
}
