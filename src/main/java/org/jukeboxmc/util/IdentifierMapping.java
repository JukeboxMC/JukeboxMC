package org.jukeboxmc.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class IdentifierMapping {

    private static final Map<Identifier, Identifier> identifierMap = new LinkedHashMap<>();

    public static void init() {
        identifierMap.put( Identifier.fromString( "minecraft:acacia_door" ), Identifier.fromString( "minecraft:item.acacia_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:bed" ), Identifier.fromString( "minecraft:item.bed" ) );
        identifierMap.put( Identifier.fromString( "minecraft:beetroot" ), Identifier.fromString( "minecraft:item.beetroot" ) );
        identifierMap.put( Identifier.fromString( "minecraft:birch_door" ), Identifier.fromString( "minecraft:item.birch_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:brewing_stand" ), Identifier.fromString( "minecraft:item.brewing_stand" ) );
        identifierMap.put( Identifier.fromString( "minecraft:cake" ), Identifier.fromString( "minecraft:item.cake" ) );
        identifierMap.put( Identifier.fromString( "minecraft:camera" ), Identifier.fromString( "minecraft:item.camera" ) );
        identifierMap.put( Identifier.fromString( "minecraft:campfire" ), Identifier.fromString( "minecraft:item.campfire" ) );
        identifierMap.put( Identifier.fromString( "minecraft:cauldron" ), Identifier.fromString( "minecraft:item.cauldron" ) );
        identifierMap.put( Identifier.fromString( "minecraft:chain" ), Identifier.fromString( "minecraft:item.chain" ) );
        identifierMap.put( Identifier.fromString( "minecraft:crimson_door" ), Identifier.fromString( "minecraft:item.crimson_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:dark_oak_door" ), Identifier.fromString( "minecraft:item.dark_oak_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:flower_pot" ), Identifier.fromString( "minecraft:item.flower_pot" ) );
        identifierMap.put( Identifier.fromString( "minecraft:frame" ), Identifier.fromString( "minecraft:item.frame" ) );
        identifierMap.put( Identifier.fromString( "minecraft:glow_frame" ), Identifier.fromString( "minecraft:item.glow_frame" ) );
        identifierMap.put( Identifier.fromString( "minecraft:hopper" ), Identifier.fromString( "minecraft:item.hopper" ) );
        identifierMap.put( Identifier.fromString( "minecraft:iron_door" ), Identifier.fromString( "minecraft:item.iron_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:jungle_door" ), Identifier.fromString( "minecraft:item.jungle_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:kelp" ), Identifier.fromString( "minecraft:item.kelp" ) );
        identifierMap.put( Identifier.fromString( "minecraft:mangrove_door" ), Identifier.fromString( "minecraft:item.mangrove_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:nether_sprouts" ), Identifier.fromString( "minecraft:item.nether_sprouts" ) );
        identifierMap.put( Identifier.fromString( "minecraft:nether_wart" ), Identifier.fromString( "minecraft:item.nether_wart" ) );
        identifierMap.put( Identifier.fromString( "minecraft:reeds" ), Identifier.fromString( "minecraft:item.reeds" ) );
        identifierMap.put( Identifier.fromString( "minecraft:skull" ), Identifier.fromString( "minecraft:item.skull" ) );
        identifierMap.put( Identifier.fromString( "minecraft:soul_campfire" ), Identifier.fromString( "minecraft:item.soul_campfire" ) );
        identifierMap.put( Identifier.fromString( "minecraft:spruce_door" ), Identifier.fromString( "minecraft:item.spruce_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:warped_door" ), Identifier.fromString( "minecraft:item.warped_door" ) );
        identifierMap.put( Identifier.fromString( "minecraft:wheat" ), Identifier.fromString( "minecraft:item.wheat" ) );
        identifierMap.put( Identifier.fromString( "minecraft:wooden_door" ), Identifier.fromString( "minecraft:item.wooden_door" ) );
    }

    public static Identifier toItemIdentifier( Identifier blockIdentifier ) {
        return identifierMap.get( blockIdentifier );
    }

    public static boolean mappingExists( Identifier blockIdentifier ) {
        return identifierMap.containsKey( blockIdentifier );
    }

}
