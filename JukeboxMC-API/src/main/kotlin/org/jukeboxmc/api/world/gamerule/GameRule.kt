package org.jukeboxmc.api.world.gamerule

enum class GameRule(
    val identifier: String,
    val defaultValue: Any,
    val type: Type
) {

    COMMAND_BLOCKS_ENABLED("commandblocksenabled", true, Type.BOOLEAN),
    COMMAND_BLOCK_OUTPUT("commandblockoutput", true, Type.BOOLEAN),
    DO_DAYLIGHT_CYCLE("dodaylightcycle", true, Type.BOOLEAN),
    DO_ENTITY_DROPS("doentitydrops", true, Type.BOOLEAN),
    DO_FIRE_TICK("dofiretick", true, Type.BOOLEAN),
    DO_INSOMNIA("doinsomnia", true, Type.BOOLEAN),
    DO_IMMEDIATE_RESPAWN("doimmediaterespawn", false, Type.BOOLEAN),
    DO_MOB_LOOT("domobloot", true, Type.BOOLEAN),
    DO_MOB_SPAWNING("domobspawning", true, Type.BOOLEAN),
    DO_TILE_DROPS("dotiledrops", true, Type.BOOLEAN),
    DO_WEATHER_CYCLE("doweathercycle", true, Type.BOOLEAN),
    DROWNING_DAMAGE("drowningdamage", true, Type.BOOLEAN),
    FALL_DAMAGE("falldamage", true, Type.BOOLEAN),
    FIRE_DAMAGE("firedamage", true, Type.BOOLEAN),
    KEEP_INVENTORY("keepinventory", false, Type.BOOLEAN),
    MAX_COMMAND_CHAIN_LENGTH("maxcommandchainlength", 65536, Type.INT),
    MOB_GRIEFING("mobgriefing", true, Type.BOOLEAN),
    NATURAL_REGENERATION("naturalregeneration", true, Type.BOOLEAN),
    PVP("pvp", true, Type.BOOLEAN),
    RANDOM_TICK_SPEED("randomtickspeed", 1, Type.INT),
    SEND_COMMAND_FEEDBACK("sendcommandfeedback", true, Type.BOOLEAN),
    SHOW_COORDINATES("showcoordinates", true, Type.BOOLEAN),
    SHOW_DEATH_MESSAGES("showdeathmessages", true, Type.BOOLEAN),
    SPAWN_RADIUS("spawnradius", 5, Type.INT),
    TNT_EXPLODES("tntexplodes", true, Type.BOOLEAN),
    SHOW_TAGS("showtags", true, Type.BOOLEAN),
    FREEZE_DAMAGE("freezedamage", true, Type.BOOLEAN),
    RESPAWN_BLOCKS_EXPLODE("respawnblocksexplode", true, Type.BOOLEAN),
    SHOW_BORDER_EFFECT("showbordereffect", true, Type.BOOLEAN),
    FUNCTION_COMMAND_LIMIT("functioncommandlimit", 10000, Type.INT),
    RECIPESUNLOCK("recipesunlock", true, Type.BOOLEAN),
    DO_LIMITED_CRAFTING("dolimitedcrafting", true, Type.BOOLEAN),
    PLAYERS_SLEEPING_PERCENTAGE("playerssleepingpercentage", 100, Type.INT);

    enum class Type {
        INT,
        BOOLEAN
    }

    companion object {
        fun fromIdentifier(identifier: String): GameRule? {
            return entries.find { it.identifier.equals(identifier, ignoreCase = true) }
        }

        fun parseByteToBoolean(value: Byte): Boolean {
            return value == 1.toByte()
        }

        val gameRuleIdentifier = arrayOf(
                "commandblocksenabled", "commandblockoutput", "dodaylightcycle",
                "doentitydrops", "dofiretick", "doinsomnia", "doimmediaterespawn", "domobloot", "domobspawning",
                "dotiledrops", "doweathercycle", "drowningdamage", "falldamage", "firedamage", "keepinventory",
                "maxcommandchainlength", "mobgriefing", "naturalregeneration", "pvp", "randomtickspeed", "sendcommandfeedback",
                "showcoordinates", "showdeathmessages", "spawnradius", "tntexplodes", "showtags", "freezedamage",
                "respawnblocksexplode", "showbordereffect", "functioncommandlimit", "recipesunlock", "dolimitedcrafting",
                "playerssleepingpercentage",
        )
    }
}
