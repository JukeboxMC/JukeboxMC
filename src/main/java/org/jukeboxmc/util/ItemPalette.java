package org.jukeboxmc.util;

import com.google.gson.Gson;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition;
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

    private static final Gson GSON = new Gson();
    public static final Map<Identifier, Short> IDENTIFIER_TO_RUNTIME = new LinkedHashMap<>();
    public static final Map<Short, Identifier> RUNTIME_TO_IDENTIFIER = new LinkedHashMap<>();

    public static final Map<Identifier, Identifier> MAPPING_IDENTIEFER = new LinkedHashMap<>();

    public static void init() {
        try ( InputStream inputStream = Objects.requireNonNull( Bootstrap.class.getClassLoader().getResourceAsStream( "runtime_item_states.json" ) ) ) {
            List<Map<String, Object>> parseItem = GSON.<List<Map<String, Object>>>fromJson( new InputStreamReader( inputStream ), List.class );
            for ( Map<String, Object> entry : parseItem ) {
                Identifier name = Identifier.fromString( (String) entry.get( "name" ) );
                short runtimeId = (short) (double) entry.get( "id" );
                IDENTIFIER_TO_RUNTIME.put( name, runtimeId );
                RUNTIME_TO_IDENTIFIER.put( runtimeId, name );
            }

            mappingIdentifier( Identifier.fromString("minecraft:acacia_door"), Identifier.fromString("minecraft:item.acacia_door"));
            mappingIdentifier( Identifier.fromString("minecraft:bed"), Identifier.fromString("minecraft:item.bed"));
            mappingIdentifier( Identifier.fromString("minecraft:beetroot"), Identifier.fromString("minecraft:item.beetroot"));
            mappingIdentifier( Identifier.fromString("minecraft:birch_door"), Identifier.fromString("minecraft:item.birch_door"));
            mappingIdentifier( Identifier.fromString("minecraft:brewing_stand"), Identifier.fromString("minecraft:item.brewing_stand"));
            mappingIdentifier( Identifier.fromString("minecraft:cake"), Identifier.fromString("minecraft:item.cake"));
            mappingIdentifier( Identifier.fromString("minecraft:camera"), Identifier.fromString("minecraft:item.camera"));
            mappingIdentifier( Identifier.fromString("minecraft:campfire"), Identifier.fromString("minecraft:item.campfire"));
            mappingIdentifier( Identifier.fromString("minecraft:cauldron"), Identifier.fromString("minecraft:item.cauldron"));
            mappingIdentifier( Identifier.fromString("minecraft:chain"), Identifier.fromString("minecraft:item.chain"));
            mappingIdentifier( Identifier.fromString("minecraft:crimson_door"), Identifier.fromString("minecraft:item.crimson_door"));
            mappingIdentifier( Identifier.fromString("minecraft:dark_oak_door"), Identifier.fromString("minecraft:item.dark_oak_door"));
            mappingIdentifier( Identifier.fromString("minecraft:flower_pot"), Identifier.fromString("minecraft:item.flower_pot"));
            mappingIdentifier( Identifier.fromString("minecraft:frame"), Identifier.fromString("minecraft:item.frame"));
            mappingIdentifier( Identifier.fromString("minecraft:glow_frame"), Identifier.fromString("minecraft:item.glow_frame"));
            mappingIdentifier( Identifier.fromString("minecraft:hopper"), Identifier.fromString("minecraft:item.hopper"));
            mappingIdentifier( Identifier.fromString("minecraft:iron_door"), Identifier.fromString("minecraft:item.iron_door"));
            mappingIdentifier( Identifier.fromString("minecraft:jungle_door"), Identifier.fromString("minecraft:item.jungle_door"));
            mappingIdentifier( Identifier.fromString("minecraft:kelp"), Identifier.fromString("minecraft:item.kelp"));
            mappingIdentifier( Identifier.fromString("minecraft:mangrove_door"), Identifier.fromString("minecraft:item.mangrove_door"));
            mappingIdentifier( Identifier.fromString("minecraft:nether_sprouts"), Identifier.fromString("minecraft:item.nether_sprouts"));
            mappingIdentifier( Identifier.fromString("minecraft:nether_wart"), Identifier.fromString("minecraft:item.nether_wart"));
            mappingIdentifier( Identifier.fromString("minecraft:reeds"), Identifier.fromString("minecraft:item.reeds"));
            mappingIdentifier( Identifier.fromString("minecraft:skull"), Identifier.fromString("minecraft:item.skull"));
            mappingIdentifier( Identifier.fromString("minecraft:soul_campfire"), Identifier.fromString("minecraft:item.soul_campfire"));
            mappingIdentifier( Identifier.fromString("minecraft:spruce_door"), Identifier.fromString("minecraft:item.spruce_door"));
            mappingIdentifier( Identifier.fromString("minecraft:warped_door"), Identifier.fromString("minecraft:item.warped_door"));
            mappingIdentifier( Identifier.fromString("minecraft:wheat"), Identifier.fromString("minecraft:item.wheat"));
            mappingIdentifier( Identifier.fromString("minecraft:wooden_door"), Identifier.fromString("minecraft:item.wooden_door"));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private static void mappingIdentifier( Identifier blockIdentifier, Identifier itemIdentifier ) {
        MAPPING_IDENTIEFER.put( blockIdentifier, itemIdentifier );
    }

    public static Identifier getItemIdentifier( Identifier blockIdentifier ) {
        return MAPPING_IDENTIEFER.getOrDefault( blockIdentifier, blockIdentifier );
    }

    public static int getRuntimeId( Identifier identifier ) {
        return IDENTIFIER_TO_RUNTIME.get( identifier );
    }

    public static Identifier getIdentifier( short runtimeId ) {
        return RUNTIME_TO_IDENTIFIER.getOrDefault( runtimeId, Identifier.fromString( "minecraft:air" ) );
    }

    public static Set<Identifier> getIdentifiers() {
        return IDENTIFIER_TO_RUNTIME.keySet();
    }

    public static List<ItemDefinition> getEntries() {
        return NonStream.map( IDENTIFIER_TO_RUNTIME.entrySet(), ItemPalette::toEntry, ArrayList::new, Collections::unmodifiableList );
    }

    private static SimpleItemDefinition toEntry(Map.Entry<Identifier, Short> entry ) {
        return new SimpleItemDefinition( entry.getKey().getFullName(), entry.getValue(), false );
    }

}
