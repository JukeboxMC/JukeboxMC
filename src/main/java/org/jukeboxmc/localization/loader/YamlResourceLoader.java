package org.jukeboxmc.localization.loader;

import lombok.NoArgsConstructor;
import org.jukeboxmc.localization.ResourceLoadFailedException;
import org.jukeboxmc.localization.ResourceLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
public class YamlResourceLoader extends FileResourceLoader implements ResourceLoader {

    private Map<String, Object> lookup;
    private String file;

    public YamlResourceLoader( ClassLoader classLoader, String file ) throws ResourceLoadFailedException {
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
            // Get the correct InputStreamReader for this file
            stream = getFileInputStreamReader( file );

            // Read from the InputStreamReader till he is empty
            BufferedReader br = new BufferedReader( stream );
            String line;
            StringBuilder sb = new StringBuilder();
            while ( ( line = br.readLine() ) != null ) {
                sb.append( line );
                sb.append( "\n" );
            }

            //Try to read the YamlConfiguration
            Yaml yaml = new Yaml();
            lookup = (Map<String, Object>) yaml.load( sb.toString() );
        } catch ( IOException e ) {
            lookup = null;
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
        List<String> keys = new ArrayList<>();
        addKeys( keys, "", this.lookup );
        return keys;
    }

    private void addKeys( List<String> keys, String root, Map<String, Object> lookup ) {
        for ( Map.Entry<String, Object> entry : lookup.entrySet() ) {
            if ( entry.getValue() instanceof Map ) {
                this.addKeys( keys, entry.getKey() + ".", (Map<String, Object>) entry.getValue() );
            } else {
                keys.add( root + entry.getKey() );
            }
        }
    }

    @Override
    public String get( String key ) {
        // Fast out when parsing the YML did fail
        if ( lookup == null ) {
            return null;
        }

        Object finalData;

        // Check if we have a dot in the key
        if ( key.contains( "." ) ) {
            String[] keyParts = key.split( "\\." );

            Map<String, Object> current = (Map<String, Object>) lookup.get( keyParts[0] );
            if ( current == null ) {
                return null;
            }

            for ( int i = 1; i < keyParts.length - 1; i++ ) {
                current = (Map<String, Object>) current.get( keyParts[i] );
                if ( current == null ) {
                    return null;
                }
            }

            finalData = current.get( keyParts[keyParts.length - 1] );
        } else {
            finalData = lookup.get( key );
        }

        return finalData instanceof String ? (String) finalData : finalData == null ? null : String.valueOf( finalData );
    }

    @Override
    public List<String> getFormats() {
        return Collections.singletonList( ".yml" );
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
        lookup = null;
        file = null;

        super.cleanup();
    }
}