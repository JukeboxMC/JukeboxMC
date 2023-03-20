package org.jukeboxmc.world.gamerule;

import java.util.Arrays;

public enum GameRule {

    COMMAND_BLOCKS_ENABLED( "commandBlocksEnabled", true ),
    COMMAND_BLOCK_OUTPUT( "commandBlockOutput", true ),
    DO_DAYLIGHT_CYCLE( "doDaylightCycle", true ), //Implemented
    DO_ENTITY_DROPS( "doEntityDrops", true ),
    DO_FIRE_TICK( "doFireTick", true ),
    DO_INSOMNIA( "doInsomnia", true ),
    DO_IMMEDIATE_RESPAWN( "doImmediateRespawn", false ),
    DO_MOB_LOOT( "doMobLoot", true ),
    DO_MOB_SPAWNING( "doMobSpawning", true ),
    DO_TILE_DROPS( "doTileDrops", true ),
    DO_WEATHER_CYCLE( "doWeatherCycle", true ),
    DROWNING_DAMAGE( "drowningDamage", true ),
    FALL_DAMAGE( "fallDamage", true ),
    FIRE_DAMAGE( "fireDamage", true ),
    KEEP_INVENTORY( "keepInventory", false ),
    MAX_COMMAND_CHAIN_LENGTH( "maxCommandChainLength", 65536 ),
    MOB_GRIEFING( "mobGriefing", true ),
    NATURAL_REGENERATION( "naturalRegeneration", true ),
    PVP( "pvp", true ),
    RANDOM_TICK_SPEED( "randomTickSpeed", 1 ),
    SEND_COMMAND_FEEDBACK( "sendCommandFeedback", true ),
    SHOW_COORDINATES( "showCoordinates", true ), //Implemented
    SHOW_DEATH_MESSAGES( "showDeathMessages", true ),
    SPAWN_RADIUS( "spawnRadius", 5 ),
    TNT_EXPLODES( "tntExplodes", true ),
    SHOW_TAGS( "showTags", true ),
    FREEZE_DAMAGE( "freezeDamage", true ),
    RESPAWN_BLOCKS_EXPLODE( "respawnBlocksExplode", true ),
    SHOW_BORDER_EFFECT( "showBorderEffect", true ),
    FUNCTION_COMMAND_LIMIT( "functionCommandLimit", 10000 );

    private final String identifier;
    private final Object defaultValue;

    GameRule( String identifier, Object defaultValue ) {
        this.identifier = identifier;
        this.defaultValue = defaultValue;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }


    public static GameRule fromIdentifier( String identifier ) {
        return Arrays.stream(values()).filter(gameRule -> gameRule.getIdentifier().equalsIgnoreCase(identifier)).findFirst().orElse(null);
    }
}
