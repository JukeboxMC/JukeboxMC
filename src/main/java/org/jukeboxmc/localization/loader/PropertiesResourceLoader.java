package org.jukeboxmc.localization.loader;

import lombok.NoArgsConstructor;
import org.jukeboxmc.localization.ResourceLoadFailedException;
import org.jukeboxmc.localization.ResourceLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
public class PropertiesResourceLoader extends FileResourceLoader implements ResourceLoader {

    private Properties pro;
    private String file;
    private ArrayList<String> keys = new ArrayList<>();

    public PropertiesResourceLoader( ClassLoader classLoader, String file ) throws ResourceLoadFailedException {
        super( classLoader );

        this.file = file;

        try {
            load();
        } catch ( ResourceLoadFailedException e ) {
            throw e;
        }
    }

    private void load() throws ResourceLoadFailedException {
        InputStreamReader stream = null;
        try {
            //Get the correct InputStreamReader for this file
            stream = getFileInputStreamReader( file );

            //Try to parse the properties
            pro = new Properties();
            pro.load( stream );

            //Get the keys
            keys = new ArrayList<>();

            for ( Object o : pro.keySet() ) {
                keys.add( (String) o );
            }
        } catch ( IOException e ) {
            pro = null;
            throw new ResourceLoadFailedException( e );
        } catch ( ResourceLoadFailedException e ) {
            throw e;
        } finally {
            if ( stream != null ) {
                try {
                    stream.close();
                } catch ( IOException e ) {
                    throw new ResourceLoadFailedException( e );
                }
            }
        }
    }

    @Override
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public String get( String key ) {
        return pro != null ? (String) pro.get( key ) : null;
    }

    @Override
    public List<String> getFormats() {
        return Collections.singletonList( ".properties" );
    }

    @Override
    public void reload() throws ResourceLoadFailedException {
        try {
            load();
        } catch ( ResourceLoadFailedException e ) {
            throw e;
        }
    }

    @Override
    public void cleanup() {
        pro = null;
        file = null;
        super.cleanup();
    }

}