package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedExposedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedExposedCutCopper extends Block{

    public BlockWaxedExposedCutCopper() {
        super( "minecraft:waxed_exposed_cut_copper" );
    }

    @Override
    public ItemWaxedExposedCutCopper toItem() {
        return new ItemWaxedExposedCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_EXPOSED_CUT_COPPER;
    }
}
