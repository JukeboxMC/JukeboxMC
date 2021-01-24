package org.jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GameRule {

    public static final GameRules<Boolean> COMMAND_BLOCK_OUTPUT = GameRules.booleanValue("commandBlockOutput", true);
    public static final GameRules<Boolean> DO_DAYLIGHT_CYCLE = GameRules.booleanValue("doDaylightCycle", true);
    public static final GameRules<Boolean> DO_ENTITY_DROPS = GameRules.booleanValue("doEntityDrops", true);
    public static final GameRules<Boolean> DO_FIRE_TICK = GameRules.booleanValue("doFireTick", true);
    public static final GameRules<Boolean> DO_IMMEDIATE_RESPAWN = GameRules.booleanValue("doImmediateRespawn", true);
    public static final GameRules<Boolean> DO_MOB_LOOT = GameRules.booleanValue("doMobLoot", true);
    public static final GameRules<Boolean> DO_MOB_SPAWNING = GameRules.booleanValue("doMobSpawning", true);
    public static final GameRules<Boolean> DO_TILE_DROPS = GameRules.booleanValue("doTileDrops", true);
    public static final GameRules<Boolean> DO_WEATHER_CYCLE = GameRules.booleanValue("doWeatherCycle", true);
    public static final GameRules<Boolean> DROWNING_DAMAGE = GameRules.booleanValue("drowningDamage", true);
    public static final GameRules<Boolean> FALL_DAMAGE = GameRules.booleanValue("fallDamage", true);
    public static final GameRules<Boolean> FIRE_DAMAGE = GameRules.booleanValue("fireDamage", true);
    public static final GameRules<Boolean> KEEP_INVENTORY = GameRules.booleanValue("keepInventory", true);
    public static final GameRules<Boolean> MOB_GRIEFING = GameRules.booleanValue("mobGriefing", true);
    public static final GameRules<Boolean> NATURAL_REGENERATION = GameRules.booleanValue("naturalRegeneration", true);
    public static final GameRules<Boolean> PVP = GameRules.booleanValue("pvp", true);
    public static final GameRules<Integer> RANDOM_TICK_SPEED = GameRules.intValue("randomTickSpeed", 0);
    public static final GameRules<Boolean> SEND_COMMAND_FEEDBACK = GameRules.booleanValue("sendCommandFeedback", true);
    public static final GameRules<Boolean> SHOW_COORDINATES = GameRules.booleanValue("showCoordinates", true);
    public static final GameRules<Boolean> TNT_EXPLODES = GameRules.booleanValue("tntExplodes", true);
    public static final GameRules<Boolean> SHOW_DEATH_MESSAGE = GameRules.booleanValue("showDeathMessage", true);

    public static GameRules[] getAll() {
        return new GameRules[] {
                COMMAND_BLOCK_OUTPUT,
                DO_DAYLIGHT_CYCLE,
                DO_ENTITY_DROPS,
                DO_FIRE_TICK,
                DO_IMMEDIATE_RESPAWN,
                DO_MOB_LOOT,
                DO_MOB_SPAWNING,
                DO_TILE_DROPS,
                DO_WEATHER_CYCLE,
                DROWNING_DAMAGE,
                FALL_DAMAGE,
                FIRE_DAMAGE,
                KEEP_INVENTORY,
                MOB_GRIEFING,
                NATURAL_REGENERATION,
                PVP,
                RANDOM_TICK_SPEED,
                SEND_COMMAND_FEEDBACK,
                SHOW_COORDINATES,
                TNT_EXPLODES,
                SHOW_DEATH_MESSAGE
        };
    }

}
