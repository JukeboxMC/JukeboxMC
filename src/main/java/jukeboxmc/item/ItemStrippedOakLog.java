package jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedOakLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedOakLog extends Item{

    public ItemStrippedOakLog() {
        super ( "minecraft:stripped_oak_log" );
    }

    @Override
    public BlockStrippedOakLog getBlock() {
        return new BlockStrippedOakLog();
    }
}
