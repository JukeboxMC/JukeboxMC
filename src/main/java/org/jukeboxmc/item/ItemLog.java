package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLog extends Item {

    public ItemLog() {
        super( "minecraft:log", 17 );
    }

    public void setLogType( LogType logType ) {
        this.setMeta( logType.ordinal() );
    }

    public LogType getLogType() {
        return LogType.values()[this.getMeta()];
    }

    public enum LogType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE
    }
}
