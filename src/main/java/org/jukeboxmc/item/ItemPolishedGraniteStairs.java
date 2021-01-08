package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedGraniteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedGraniteStairs extends Item {

    public ItemPolishedGraniteStairs() {
        super( "minecraft:polished_granite_stairs", -172 );
    }

    @Override
    public BlockPolishedGraniteStairs getBlock() {
        return new BlockPolishedGraniteStairs();
    }
}
