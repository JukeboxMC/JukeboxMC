package org.jukeboxmc.util;

import com.google.gson.Gson;
import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import org.jukeboxmc.Bootstrap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CreativeItems {

    private static final List<ItemData> CREATIVE_ITEMS = new LinkedList<>();
    private static final Map<Integer, Identifier> IDENTIFIER_BY_NETWORK_ID = new LinkedHashMap<>();

    public static void init() {
        Gson GSON = new Gson();
        try ( final InputStream inputStream = Objects.requireNonNull( Bootstrap.class.getClassLoader().getResourceAsStream( "creative_items.json" ) ) ) {
            final InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
            Map<String, List<Map<String, Object>>> itemEntries = GSON.<Map<String, List<Map<String, Object>>>>fromJson( inputStreamReader, Map.class );
            int netIdCounter = 0;
            for ( Map<String, Object> itemEntry : itemEntries.get( "items" ) ) {
                final ItemData.Builder item;
                final Identifier identifier = Identifier.fromString( (String) itemEntry.get( "id" ) );
                if ( itemEntry.containsKey( "blockRuntimeId" ) ) {
                    item = toItemData( identifier, (int) (double) itemEntry.get( "blockRuntimeId" ) );
                } else {
                    item = toItemData( identifier, 0 );
                }
                if ( itemEntry.containsKey( "damage" ) ) {
                    item.damage( (short) (double) itemEntry.get( "damage" ) );
                }
                final String nbtTag = (String) itemEntry.get( "nbt_b64" );
                if ( nbtTag != null ) {
                    try ( final NBTInputStream nbtReader = NbtUtils.createReaderLE( new ByteArrayInputStream( Base64.getDecoder().decode( nbtTag.getBytes() ) ) ) ) {
                        item.tag( (NbtMap) nbtReader.readTag() );
                    }
                }
                netIdCounter++;
                IDENTIFIER_BY_NETWORK_ID.put( netIdCounter, identifier );
                CREATIVE_ITEMS.add( item.netId( netIdCounter ).build() );
            }
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public static List<ItemData> getCreativeItems() {
        return CREATIVE_ITEMS;
    }

    public static Identifier getIdentifier( int networkId ) {
        return IDENTIFIER_BY_NETWORK_ID.get( networkId );
    }

    private static ItemData.Builder toItemData( Identifier identifier, int blockRuntimeId ) {
        return ItemData.builder()
                .id( ItemPalette.getRuntimeId( identifier ) )
                .blockRuntimeId( blockRuntimeId )
                .count( 1 )
                .canPlace( new String[]{} )
                .canBreak( new String[]{} );
    }
}
