package org.jukeboxmc.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Utils {

    public static int blockToChunk( int value ) {
        return value >> 4;
    }

    public static long toLong( int x, int z ) {
        return ( (long) x << 32 ) + z - Integer.MIN_VALUE;
    }

    public static int log2( int value ) {
        if ( value <= 0 ) throw new IllegalArgumentException();
        return 31 - Integer.numberOfLeadingZeros( value );
    }

    public static float square( float value ) {
        return value * value;
    }

    public static float sqrt( float value ) {
        final float xhalf = value * 0.5F;
        float y = Float.intBitsToFloat( 0x5f375a86 - ( Float.floatToIntBits( value ) >> 1 ) );
        y = y * ( 1.5F - ( xhalf * y * y ) );
        y = y * ( 1.5F - ( xhalf * y * y ) );
        return value * y;
    }

    public static ByteBuf allocate( byte[] data ) {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer( data.length );
        buf.writeBytes( data );
        return buf;
    }
}
