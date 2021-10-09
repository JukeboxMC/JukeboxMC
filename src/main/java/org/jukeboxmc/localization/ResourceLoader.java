package org.jukeboxmc.localization;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ResourceLoader {

    List<String> getKeys();

    String get( String key );

    List<String> getFormats();

    void reload() throws ResourceLoadFailedException;

    void cleanup();

}