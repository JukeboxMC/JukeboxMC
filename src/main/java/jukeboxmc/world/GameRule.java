package jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public final class GameRule<T> {

    public static final GameRule<Boolean> COMMAND_BLOCK_OUTPUT = new GameRule<>( "commandBlockOutput", Boolean.class );
    public static final GameRule<Boolean> DO_DAYLIGHT_CYCLE = new GameRule<>( "doDaylightCycle", Boolean.class );
    public static final GameRule<Boolean> DO_ENTITY_DROPS = new GameRule<>( "doEntityDrops", Boolean.class );
    public static final GameRule<Boolean> DO_FIRE_TICK = new GameRule<>( "doFireTick", Boolean.class );
    public static final GameRule<Boolean> DO_IMMEDIATE_RESPAWN = new GameRule<>( "doImmediateRespawn", Boolean.class );
    public static final GameRule<Boolean> DO_MOB_LOOT = new GameRule<>( "doMobLoot", Boolean.class );
    public static final GameRule<Boolean> DO_MOB_SPAWNING = new GameRule<>( "doMobSpawning", Boolean.class );
    public static final GameRule<Boolean> DO_TILE_DROPS = new GameRule<>( "doTileDrops", Boolean.class );
    public static final GameRule<Boolean> DO_WEATHER_CYCLE = new GameRule<>( "doWeatherCycle", Boolean.class );
    public static final GameRule<Boolean> DROWNING_DAMAGE = new GameRule<>( "drowningDamage", Boolean.class );
    public static final GameRule<Boolean> FALL_DAMAGE = new GameRule<>( "fallDamage", Boolean.class );
    public static final GameRule<Boolean> FIRE_DAMAGE = new GameRule<>( "fireDamage", Boolean.class );
    public static final GameRule<Boolean> KEEP_INVENTORY = new GameRule<>( "keepInventory", Boolean.class );
    public static final GameRule<Boolean> MOB_GRIEFING = new GameRule<>( "mobGriefing", Boolean.class );
    public static final GameRule<Boolean> NATURAL_REGENERATION = new GameRule<>( "naturalRegeneration", Boolean.class );
    public static final GameRule<Boolean> PVP = new GameRule<>( "pvp", Boolean.class );
    public static final GameRule<Integer> RANDOM_TICK_SPEED = new GameRule<>( "randomTickSpeed", Integer.class );
    public static final GameRule<Boolean> SEND_COMMAND_FEEDBACK = new GameRule<>( "sendCommandFeedback", Boolean.class );
    public static final GameRule<Boolean> SHOW_COORDINATES = new GameRule<>( "showCoordinates", Boolean.class );
    public static final GameRule<Boolean> TNT_EXPLODES = new GameRule<>( "tntExplodes", Boolean.class );
    public static final GameRule<Boolean> SHOW_DEATH_MESSAGE = new GameRule<>( "showDeathMessage", Boolean.class );

    private final String name;
    private final Class<T> value;

    public GameRule( String name, Class<T> value ) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Class<T> getValue() {
        return this.value;
    }

    public Object toCompoundValue( Object value ) {
        if ( this.value == Boolean.class ) {
            Boolean val = (Boolean) value;
            return val ? (byte) 1 : (byte) 0;
        }

        return value;
    }

}
