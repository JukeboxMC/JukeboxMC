package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedOxidizedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedOxidizedCopper extends Item{

    public ItemWaxedOxidizedCopper() {
        super( "minecraft:waxed_oxidized_copper" );
    }

    @Override
    public BlockWaxedOxidizedCopper getBlock() {
        return new BlockWaxedOxidizedCopper();
    }
}
