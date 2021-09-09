package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchButton extends Item {

    public ItemBirchButton() {
        super ( "minecraft:birch_button" );
    }

    @Override
    public BlockBirchButton getBlock() {
        return new BlockBirchButton();
    }
}
