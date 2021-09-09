package jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedDarkOakLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedDarkOakLog extends Item {

    public ItemStrippedDarkOakLog() {
        super ( "minecraft:stripped_dark_oak_log" );
    }

    @Override
    public BlockStrippedDarkOakLog getBlock() {
        return new BlockStrippedDarkOakLog();
    }
}
