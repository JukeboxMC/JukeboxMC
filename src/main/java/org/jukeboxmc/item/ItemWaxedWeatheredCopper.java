package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedWeatheredCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedWeatheredCopper extends Item{

    public ItemWaxedWeatheredCopper() {
        super( "minecraft:waxed_weathered_copper" );
    }

    @Override
    public BlockWaxedWeatheredCopper getBlock() {
        return new BlockWaxedWeatheredCopper();
    }
}
