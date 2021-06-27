package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCutCopper extends Block {

    public BlockCutCopper() {
        super( "minecraft:cut_copper" );
    }

    @Override
    public ItemCutCopper toItem() {
        return new ItemCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CUT_COPPER;
    }
}
