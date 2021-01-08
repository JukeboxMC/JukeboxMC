package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaLog extends Item {

    public ItemAcaciaLog() {
        super( "minecraft:log2", 162 );
    }

    @Override
    public BlockAcaciaLog getBlock() {
        return new BlockAcaciaLog();
    }

    public void setLogType( LogType logType ) {
        this.setMeta( logType.ordinal() );
    }

    public LogType getLogType() {
        return LogType.values()[this.getMeta()];
    }

    public enum LogType {
        ACACIA,
        DARK_OAK
    }
}
