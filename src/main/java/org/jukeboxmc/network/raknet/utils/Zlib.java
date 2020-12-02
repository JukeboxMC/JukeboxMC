package org.jukeboxmc.network.raknet.utils;

import lombok.SneakyThrows;

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

    @SneakyThrows
    public static byte[] compress( byte[] input ) {
        Deflater deflater = new Deflater( 7, true );
        try {
            deflater.setInput( input );
            deflater.finish();
            FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
            outputStream.reset();
            byte[] buffer = new byte[2 * 1024 * 1024];
            while ( !deflater.finished() ) {
                int i = deflater.deflate( buffer );
                outputStream.write( buffer, 0, i );
            }
            return outputStream.toByteArray();
        } finally {
            deflater.reset();
        }
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
