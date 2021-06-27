package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemLargeAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLargeAmethystBud extends Block {

    public BlockLargeAmethystBud() {
        super( "minecraft:large_amethyst_bud" );
    }

    @Override
    public ItemLargeAmethystBud toItem() {
        return new ItemLargeAmethystBud();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LARGE_AMETHYST_BUD;
    }
}
