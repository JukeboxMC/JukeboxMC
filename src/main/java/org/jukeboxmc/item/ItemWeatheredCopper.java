package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWeatheredCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWeatheredCopper extends Item{

    public ItemWeatheredCopper() {
        super( "minecraft:weathered_copper" );
    }

    @Override
    public BlockWeatheredCopper getBlock() {
        return new BlockWeatheredCopper();
    }
}
