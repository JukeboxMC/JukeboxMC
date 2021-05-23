package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGraniteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGraniteStairs extends Item {

    public ItemGraniteStairs() {
        super( -169 );
    }

    @Override
    public BlockGraniteStairs getBlock() {
        return new BlockGraniteStairs();
    }
}
