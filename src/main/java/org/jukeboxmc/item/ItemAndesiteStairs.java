package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAndesiteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAndesiteStairs extends Item {

    public ItemAndesiteStairs() {
        super(  -171 );
    }

    @Override
    public BlockAndesiteStairs getBlock() {
        return new BlockAndesiteStairs();
    }
}
