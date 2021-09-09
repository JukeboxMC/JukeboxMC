package jukeboxmc.item;

import org.jukeboxmc.block.BlockLog;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.LogType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLog extends Item {

    public ItemLog( int blockRuntimeId ) {
        super( "minecraft:log", blockRuntimeId );
    }

    @Override
    public BlockLog getBlock() {
        return (BlockLog) BlockType.getBlock( this.blockRuntimeId );
    }

    public LogType getLogType() {
        return this.getBlock().getLogType();
    }

}
