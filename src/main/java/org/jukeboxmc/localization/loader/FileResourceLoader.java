package org.jukeboxmc.localization.loader;

import lombok.NoArgsConstructor;
import org.jukeboxmc.localization.ResourceLoadFailedException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
public abstract class FileResourceLoader {

    protected ClassLoader classLoader;

    public FileResourceLoader( ClassLoader classLoader ) {
        this.classLoader = classLoader;
    }

    protected InputStreamReader getFileInputStreamReader( String path ) throws ResourceLoadFailedException {
        try {
            if ( !path.startsWith( "file://" ) ) {
                URL resourceUrl = classLoader.getResource( path );

                if ( resourceUrl != null ) {
                    //If the file is not on the Disk read it from the JAR
                    return new InputStreamReader( resourceUrl.openStream(), StandardCharsets.UTF_8 );
                }
            } else {
                File file = new File( path.substring( 7 ) );

                if ( file.isFile() ) {
                    return new InputStreamReader( new FileInputStream( file ), StandardCharsets.UTF_8 );
                }
            }
        } catch ( UnsupportedEncodingException | FileNotFoundException e ) {
            throw new ResourceLoadFailedException( e );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        throw new ResourceLoadFailedException( "Resource not found" );
    }

    protected void cleanup() {
        this.classLoader = null;
    }

}