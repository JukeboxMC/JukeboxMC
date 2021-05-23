package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedBirchLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedBirchLog extends Item {

    public ItemStrippedBirchLog() {
        super( -6 );
    }

    @Override
    public BlockStrippedBirchLog getBlock() {
        return new BlockStrippedBirchLog();
    }
}
