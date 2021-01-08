package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWoodenButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenButton extends Item {

    public ItemWoodenButton() {
        super( "minecraft:wooden_button", 143 );
    }

    @Override
    public BlockWoodenButton getBlock() {
        return new BlockWoodenButton();
    }
}
