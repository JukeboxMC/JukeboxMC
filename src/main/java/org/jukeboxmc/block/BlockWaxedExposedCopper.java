package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedExposedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedExposedCopper extends Block{

    public BlockWaxedExposedCopper() {
        super( "minecraft:waxed_exposed_copper" );
    }

    @Override
    public ItemWaxedExposedCopper toItem() {
        return new ItemWaxedExposedCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_EXPOSED_COPPER;
    }
}
