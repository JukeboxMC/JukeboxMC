package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedSpruceLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedSpruceLog extends Item {

    public ItemStrippedSpruceLog() {
        super( -5 );
    }

    @Override
    public BlockStrippedSpruceLog getBlock() {
        return new BlockStrippedSpruceLog();
    }
}
