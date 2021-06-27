package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedCopper extends Block {

    public BlockWaxedCopper() {
        super( "minecraft:waxed_copper" );
    }

    @Override
    public ItemWaxedCopper toItem() {
        return new ItemWaxedCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_COPPER;
    }
}
