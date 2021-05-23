package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCryingObsidian;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCryingObsidian extends Item {

    public ItemCryingObsidian() {
        super( -289 );
    }

    @Override
    public BlockCryingObsidian getBlock() {
        return new BlockCryingObsidian();
    }
}
