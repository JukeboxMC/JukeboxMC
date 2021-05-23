package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCobblestoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobblestoneStairs extends Item {

    public ItemCobblestoneStairs() {
        super( 67 );
    }

    @Override
    public BlockCobblestoneStairs getBlock() {
        return new BlockCobblestoneStairs();
    }
}
