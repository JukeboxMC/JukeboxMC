package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceStairs extends Item {

    public ItemSpruceStairs() {
        super( 134 );
    }

    @Override
    public BlockSpruceStairs getBlock() {
        return new BlockSpruceStairs();
    }
}
