package jukeboxmc.utils;

import com.google.common.io.ByteStreams;
import com.google.gson.*;
import org.jukeboxmc.JukeboxMC;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BedrockResourceLoader {

    private static List<Map<String, Object>> itemPalettes = new ArrayList<>();
    private static List<Map<String, Object>> creativeItems = new ArrayList<>();
    private static final Map<String, Integer> itemIdByName = new HashMap<>();

    private static byte[] biomeDefinitions;
    private static byte[] entityIdentifiers;

    public static void init() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        InputStream itemPalette = JukeboxMC.class.getClassLoader().getResourceAsStream( "itempalette.json" );
        if ( itemPalette != null ) {
            JsonElement parseItem = new JsonParser().parse( new InputStreamReader( itemPalette ) );
            BedrockResourceLoader.itemPalettes = gson.fromJson( parseItem, List.class );
        }

        InputStream creativePalette = JukeboxMC.class.getClassLoader().getResourceAsStream( "creative_items.json" );
        if ( creativePalette != null ) {
            JsonArray parseCreative = new JsonParser().parse( new InputStreamReader( creativePalette ) ).getAsJsonObject().getAsJsonArray( "items" );
            BedrockResourceLoader.creativeItems = gson.fromJson( parseCreative, List.class );
        }

        for ( Map<String, Object> objectMap : itemPalettes ) {
            BedrockResourceLoader.itemIdByName.put( (String) objectMap.get( "name" ), (int) (double) objectMap.get( "id" ) );
        }

        try ( InputStream biomeDefinitionsStream = JukeboxMC.class.getClassLoader().getResourceAsStream( "biome_definitions.dat" ) ) {
            if ( biomeDefinitionsStream != null ) {
                BedrockResourceLoader.biomeDefinitions = ByteStreams.toByteArray( biomeDefinitionsStream );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try ( InputStream entityIdentifiersStream = JukeboxMC.class.getClassLoader().getResourceAsStream( "entity_identifiers.dat" ) ) {
            if ( entityIdentifiersStream != null ) {
                BedrockResourceLoader.entityIdentifiers = ByteStreams.toByteArray( entityIdentifiersStream );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> getItemPalettes() {
        return itemPalettes;
    }

    public static List<Map<String, Object>> getCreativeItems() {
        return creativeItems;
    }

    public static Map<String, Integer> getItemIdByName() {
        return itemIdByName;
    }

    public static byte[] getBiomeDefinitions() {
        return biomeDefinitions;
    }

    public static byte[] getEntityIdentifiers() {
        return entityIdentifiers;
    }
}
