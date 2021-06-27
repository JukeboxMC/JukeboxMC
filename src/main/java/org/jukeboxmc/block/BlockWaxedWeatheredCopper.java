package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedWeatheredCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedWeatheredCopper extends Block{

    public BlockWaxedWeatheredCopper() {
        super( "minecraft:waxed_weathered_copper" );
    }

    @Override
    public ItemWaxedWeatheredCopper toItem() {
        return new ItemWaxedWeatheredCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_WEATHERED_COPPER;
    }
}
