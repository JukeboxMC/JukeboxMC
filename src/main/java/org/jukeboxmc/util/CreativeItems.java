package org.jukeboxmc.util;

import com.google.gson.Gson;
import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import org.jukeboxmc.Bootstrap;
import org.jukeboxmc.item.Item;

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

    private static final List<ItemData> CREATIVE_ITEMS = new ArrayList<>();

    public static void init() {
        Gson GSON = new Gson();
        try ( InputStream inputStream = Objects.requireNonNull( Bootstrap.class.getClassLoader().getResourceAsStream( "creative_items.json" ) ) ) {
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
            Map<String, List<Map<String, Object>>> itemEntries = GSON.fromJson( inputStreamReader, Map.class );

            int netIdCounter = 0;
            for ( Map<String, Object> itemEntry : itemEntries.get( "items" ) ) {
                Item item;
                String identifier = (String) itemEntry.get( "id" );
                if ( itemEntry.containsKey( "blockRuntimeId" ) ) {
                    item = get( identifier, (int) (double) itemEntry.get( "blockRuntimeId" ) );
                } else {
                    item = get( identifier );
                }
                if ( itemEntry.containsKey( "damage" ) ) {
                    item.setMeta( (short) (double) itemEntry.get( "damage" ) );
                }
                String nbtTag = (String) itemEntry.get( "nbt_b64" );
                if ( nbtTag != null )
                    try ( NBTInputStream nbtReader = NbtUtils.createReaderLE( new ByteArrayInputStream( Base64.getDecoder().decode( nbtTag.getBytes() ) ) ) ) {
                        item.setNBT( (NbtMap) nbtReader.readTag() );
                    }
                CREATIVE_ITEMS.add( item.toNetwork( netIdCounter++ ) );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static Item get(String identifier) {
        return Optional.ofNullable(ItemPalette.IDENTIFIER_TO_RUNTIME.get(identifier)).
                map(runtimeId -> new Item(identifier)).
                orElseThrow(() -> new NullPointerException("No item for " + identifier + " was found"));
    }

    public static Item get(String identifier, int blockRuntimeId) {
        return Optional.ofNullable(ItemPalette.IDENTIFIER_TO_RUNTIME.get(identifier)).
                map(runtimeId -> new Item(identifier, 0, blockRuntimeId)).
                orElseThrow(() -> new NullPointerException("No item for " + blockRuntimeId + " was found"));
    }

    public static ItemData[] getCreativeItems() {
        return CREATIVE_ITEMS.toArray( new ItemData[0] );
    }
}
