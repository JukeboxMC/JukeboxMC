package jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedSpruceLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedSpruceLog extends Item {

    public ItemStrippedSpruceLog() {
        super ( "minecraft:stripped_spruce_log" );
    }

    @Override
    public BlockStrippedSpruceLog getBlock() {
        return new BlockStrippedSpruceLog();
    }
}
