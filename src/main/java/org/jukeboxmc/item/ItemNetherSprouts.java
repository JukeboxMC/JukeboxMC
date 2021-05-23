package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherSprouts;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherSprouts extends Item {

    public ItemNetherSprouts() {
        super( -238 );
    }

    @Override
    public BlockNetherSprouts getBlock() {
        return new BlockNetherSprouts();
    }
}
