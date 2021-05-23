package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedOakLog;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedOakLog extends Item{

    public ItemStrippedOakLog() {
        super( -10 );
    }

    @Override
    public BlockStrippedOakLog getBlock() {
        return new BlockStrippedOakLog();
    }
}
