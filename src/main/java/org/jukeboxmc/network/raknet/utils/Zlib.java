package org.jukeboxmc.network.raknet.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Zlib {

    private static final ThreadLocal<Inflater> INFLATER_RAW = ThreadLocal.withInitial( () -> new Inflater( true ) );
    private static final ThreadLocal<Deflater> DEFLATER_RAW = ThreadLocal.withInitial( () -> new Deflater( 7, true ) );
    private static final ThreadLocal<byte[]> BUFFER = ThreadLocal.withInitial( () -> new byte[2 * 1024 * 1024] );

    public static byte[] compress( byte[] input ) {
        final Deflater deflater = new Deflater();
        deflater.setInput( input );
        deflater.finish();
        final ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[2 * 1024 * 1024];
        int readCount;

        while ( !deflater.finished() ) {
            readCount = deflater.deflate( readBuffer );
            if ( readCount > 0 ) {
                bao.write( readBuffer, 0, readCount );
            }
        }

        deflater.end();
        return bao.toByteArray();
    }

    public static byte[] infalte( byte[] input ) throws DataFormatException, IOException {
        final Inflater inflater = new Inflater( true );
        inflater.reset();
        inflater.setInput( input );
        inflater.finished();
        final FastByteArrayOutputStream bao = new FastByteArrayOutputStream();
        byte[] readBuffer = new byte[1024];
        int readCount;
        while ( !inflater.finished() ) {
            readCount = inflater.inflate( readBuffer );
            if ( readCount > 0 ) {
                bao.write( readBuffer, 0, readCount );
            }
        }
        inflater.end();
        return bao.toByteArray();
    }

}
