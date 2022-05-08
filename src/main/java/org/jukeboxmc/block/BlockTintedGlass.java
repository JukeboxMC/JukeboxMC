package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemTintedGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTintedGlass extends Block {

    public BlockTintedGlass() {
        super( "minecraft:tinted_glass" );
    }

    @Override
    public ItemTintedGlass toItem() {
        return new ItemTintedGlass();
    }

    @Override
    public BlockType getType() {
        return BlockType.TINTED_GLASS;
    }
}
