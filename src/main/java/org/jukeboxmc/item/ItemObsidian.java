package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockObsidian;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemObsidian extends Item {

    public ItemObsidian() {
        super( 49 );
    }

    @Override
    public BlockObsidian getBlock() {
        return new BlockObsidian();
    }
}
