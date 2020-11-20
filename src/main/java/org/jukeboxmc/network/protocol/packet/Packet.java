package org.jukeboxmc.network.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Packet {

    public ByteBuf buffer;

    public abstract int getPacketId();
    public abstract void read();
    public abstract void write();

    public int readUnsignedVarInt() {
        int value = 0;
        int i = 0;
        int b;

        while ( ( ( b = this.buffer.readUnsignedByte() ) & 0x80 ) != 0 ) {
            value |= ( b & 0x7F ) << i;
            i += 7;
            if ( i > 35 ) {
                throw new RuntimeException( "VarInt too big" );
            }
        }

        return value | ( b << i );
    }

    public void writeUnsignedVarInt( int value ) {
        while ( ( value & 0xFFFFFF80 ) != 0L ) {
            this.buffer.writeByte( (byte) ( ( value & 0x7F ) | 0x80 ) );
            value >>>= 7;
        }

        this.buffer.writeByte( (byte) ( value & 0x7F ) );
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

    public ByteBuf readRemaining() {
        return this.buffer.readSlice( this.buffer.readerIndex() );
    }
}
