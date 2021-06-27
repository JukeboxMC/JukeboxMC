package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedCutCopper extends Block{

    public BlockWaxedCutCopper() {
        super( "minecraft:waxed_cut_copper" );
    }

    @Override
    public ItemWaxedCutCopper toItem() {
        return new ItemWaxedCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_CUT_COPPER;
    }
}
