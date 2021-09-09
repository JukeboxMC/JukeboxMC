package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedBirchLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedBirchLog extends Item {

    public ItemStrippedBirchLog() {
        super ( "minecraft:stripped_birch_log" );
    }

    @Override
    public BlockStrippedBirchLog getBlock() {
        return new BlockStrippedBirchLog();
    }
}
