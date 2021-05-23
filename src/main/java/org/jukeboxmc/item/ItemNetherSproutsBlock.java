package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherSprouts;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherSproutsBlock extends Item {

    public ItemNetherSproutsBlock() {
        super( 609 );
    }

    @Override
    public BlockNetherSprouts getBlock() {
        return new BlockNetherSprouts();
    }

}
