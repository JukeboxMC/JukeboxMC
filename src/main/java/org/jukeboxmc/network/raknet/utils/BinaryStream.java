package org.jukeboxmc.network.raknet.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BinaryStream {

    public ByteBuf buffer;

    public BinaryStream() {
        this.buffer = Unpooled.buffer( 0 );
    }

    public BinaryStream( ByteBuf buffer ) {
        this.buffer = buffer;
    }

    public BinaryStream( int maxSize ) {
        this.buffer = Unpooled.buffer( 0, maxSize );
    }

    public int readUnsignedVarInt() {
        int value = 0;
        int i = 0;
        int b;

        while ( ( ( b = this.buffer.readByte() ) & 0x80 ) != 0 ) {
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

}
