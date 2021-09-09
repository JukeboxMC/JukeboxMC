package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedWeatheredCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedWeatheredCutCopper extends Item{

    public ItemWaxedWeatheredCutCopper() {
        super( "minecraft:waxed_weathered_cut_copper" );
    }

    @Override
    public BlockWaxedWeatheredCutCopper getBlock() {
        return new BlockWaxedWeatheredCutCopper();
    }
}
