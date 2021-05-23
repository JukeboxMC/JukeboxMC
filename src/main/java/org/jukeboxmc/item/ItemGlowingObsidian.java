package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowingObsidian;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlowingObsidian extends Item {

    public ItemGlowingObsidian() {
        super( 246 );
    }

    @Override
    public BlockGlowingObsidian getBlock() {
        return new BlockGlowingObsidian();
    }
}
