package org.jukeboxmc.nbt.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class VarInts {
    private VarInts() {
    }

    public static void writeInt( DataOutput buffer, int integer ) throws IOException {
        encodeUnsigned( buffer, ( integer << 1 ) ^ ( integer >> 31 ) );
    }

    public static int readInt( DataInput buffer ) throws IOException {
        int n = (int) decodeUnsigned( buffer );
        return ( n >>> 1 ) ^ -( n & 1 );
    }

    public static void writeUnsignedInt( DataOutput buffer, long integer ) throws IOException {
        encodeUnsigned( buffer, integer );
    }

    public static int readUnsignedInt( DataInput buffer ) throws IOException {
        return (int) decodeUnsigned( buffer );
    }

    public static void writeLong( DataOutput buffer, long longInteger ) throws IOException {
        encodeUnsigned( buffer, ( longInteger << 1 ) ^ ( longInteger >> 63 ) );
    }

    public static long readLong( DataInput buffer ) throws IOException {
        long n = decodeUnsigned( buffer );
        return ( n >>> 1 ) ^ -( n & 1 );
    }

    public static void writeUnsignedLong( DataOutput buffer, long longInteger ) throws IOException {
        encodeUnsigned( buffer, longInteger );
    }

    public static long readUnsignedLong( DataInput buffer ) throws IOException {
        return decodeUnsigned( buffer );
    }

    private static long decodeUnsigned( DataInput buffer ) throws IOException {
        long result = 0;
        for ( int shift = 0; shift < 64; shift += 7 ) {
            final byte b = buffer.readByte();
            result |= (long) ( b & 0x7F ) << shift;
            if ( ( b & 0x80 ) == 0 ) {
                return result;
            }
        }
        throw new ArithmeticException( "Varint was too large" );
    }

    private static void encodeUnsigned( DataOutput buffer, long value ) throws IOException {
        while ( true ) {
            if ( ( value & ~0x7FL ) == 0 ) {
                buffer.writeByte( (int) value );
                return;
            } else {
                buffer.writeByte( (byte) ( ( (int) value & 0x7F ) | 0x80 ) );
                value >>>= 7;
            }
        }
    }
}
