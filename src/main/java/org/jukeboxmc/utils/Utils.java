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
        return ( ( (long) x ) << 32 ) | ( z & 0xffffffffL );
    }

    public static int fromHashX( long hash ) {
        return (int) ( hash >> 32 );
    }

    public static int fromHashZ( long hash ) {
        return (int) hash;
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

    public static byte[] getKey( int chunkX, int chunkZ, byte key ) {
        return new byte[]{
                (byte) ( chunkX & 0xff ),
                (byte) ( ( chunkX >>> 8 ) & 0xff ),
                (byte) ( ( chunkX >>> 16 ) & 0xff ),
                (byte) ( ( chunkX >>> 24 ) & 0xff ),
                (byte) ( chunkZ & 0xff ),
                (byte) ( ( chunkZ >>> 8 ) & 0xff ),
                (byte) ( ( chunkZ >>> 16 ) & 0xff ),
                (byte) ( ( chunkZ >>> 24 ) & 0xff ),
                key
        };
    }

    public static byte[] getSubChunkKey( int chunkX, int chunkZ, byte key, byte subChunk ) {
        return new byte[]{
                (byte) ( chunkX & 0xff ),
                (byte) ( ( chunkX >>> 8 ) & 0xff ),
                (byte) ( ( chunkX >>> 16 ) & 0xff ),
                (byte) ( ( chunkX >>> 24 ) & 0xff ),
                (byte) ( chunkZ & 0xff ),
                (byte) ( ( chunkZ >>> 8 ) & 0xff ),
                (byte) ( ( chunkZ >>> 16 ) & 0xff ),
                (byte) ( ( chunkZ >>> 24 ) & 0xff ),
                key,
                subChunk
        };
    }
}
