package org.jukeboxmc.network.raknet.utils;

import lombok.SneakyThrows;
import org.jukeboxmc.utils.FastByteArrayOutputStream;

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

    @SneakyThrows
    public static byte[] compress( byte[] input ) {
        Deflater deflater = DEFLATER_RAW.get();
        try {
            deflater.setInput( input );
            deflater.finish();
            FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
            bos.reset();
            byte[] buffer = BUFFER.get();
            while ( !deflater.finished() ) {
                int i = deflater.deflate( buffer );
                bos.write( buffer, 0, i );
            }

            return bos.toByteArray();
        } finally {
            deflater.reset();
        }
    }

    public static byte[] infalte( byte[] input ) throws DataFormatException, IOException {
        Inflater inflater = INFLATER_RAW.get();
        try {
            inflater.setInput( input );
            inflater.finished();

            FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
            bos.reset();
            byte[] buf = BUFFER.get();
            while ( !inflater.finished() ) {
                int i = inflater.inflate( buf );
                if ( i == 0 ) {
                    throw new IOException( "Could not decompress the data. Needs input: " + inflater.needsInput() + ", Needs Dictionary: " + inflater.needsDictionary() );
                }
                bos.write( buf, 0, i );
            }
            return bos.toByteArray();
        } finally {
            inflater.reset();
        }
    }

}
