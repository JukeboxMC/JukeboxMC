package org.jukeboxmc.util;

import com.google.gson.Gson;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import org.jukeboxmc.Bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPalette {

    public static final Map<String, Integer> IDENTIFIER_TO_RUNTIME = new LinkedHashMap<>();
    public static final Map<Integer, String> RUNTIME_TO_IDENTIFIER = new LinkedHashMap<>();

    public static void init() {
        Gson GSON = new Gson();

        try ( InputStream inputStream = Objects.requireNonNull( Bootstrap.class.getClassLoader().getResourceAsStream( "itempalette.json" ) ) ) {
            List<Map<String, Object>> parseItem = GSON.fromJson( new InputStreamReader( inputStream ), List.class );
            for ( Map<String, Object> map : parseItem ) {
                IDENTIFIER_TO_RUNTIME.put( (String) map.get( "name" ), (int) (double) map.get( "id" ) );
                RUNTIME_TO_IDENTIFIER.put( (int) (double) map.get( "id" ), (String) map.get( "name" ) );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    public static List<StartGamePacket.ItemEntry> getItemEntries() {
        List<StartGamePacket.ItemEntry> entries = new ArrayList<>();
        IDENTIFIER_TO_RUNTIME.forEach( ( identifier, id ) -> {
            entries.add( toEntry( identifier, id ) );
        } );
        return entries;
    }

    public static int getRuntimeId( String identifier ) {
        return IDENTIFIER_TO_RUNTIME.get( identifier );
    }

    public static String getIdentifier( int runtimeId ) {
        return RUNTIME_TO_IDENTIFIER.get( runtimeId );
    }

    private static StartGamePacket.ItemEntry toEntry( String fullName, int id ) {
        return new StartGamePacket.ItemEntry( fullName, (short) id, false );
    }
}
