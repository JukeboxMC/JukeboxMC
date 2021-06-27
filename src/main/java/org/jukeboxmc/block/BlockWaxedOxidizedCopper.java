package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedOxidizedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedOxidizedCopper extends Block {

    public BlockWaxedOxidizedCopper() {
        super( "minecraft:waxed_oxidized_copper" );
    }

    @Override
    public ItemWaxedOxidizedCopper toItem() {
        return new ItemWaxedOxidizedCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_OXIDIZED_COPPER;
    }
}
