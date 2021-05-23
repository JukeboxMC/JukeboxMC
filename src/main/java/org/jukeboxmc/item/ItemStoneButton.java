package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneButton extends Item {

    public ItemStoneButton() {
        super( 77 );
    }

    @Override
    public BlockStoneButton getBlock() {
        return new BlockStoneButton();
    }
}
