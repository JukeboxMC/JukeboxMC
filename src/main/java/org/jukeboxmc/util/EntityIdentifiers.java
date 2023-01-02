package org.jukeboxmc.util;

import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import org.jukeboxmc.Bootstrap;

import java.io.InputStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityIdentifiers {

    private static NbtMap IDENTIFIERS;

    public static void init() {
        final InputStream inputStream = Bootstrap.class.getClassLoader().getResourceAsStream( "entity_identifiers.dat" );
        if ( inputStream == null ) {
            throw new AssertionError( "Could not find entity_identifiers.dat" );
        }
        try ( final NBTInputStream nbtInputStream = NbtUtils.createNetworkReader( inputStream ) ) {
            IDENTIFIERS = (NbtMap) nbtInputStream.readTag();
        } catch ( Exception e ) {
            throw new AssertionError( "Error whilst loading entity_identifiers.dat", e );
        }
    }

    public static NbtMap getIdentifiers() {
        return IDENTIFIERS;
    }
}
