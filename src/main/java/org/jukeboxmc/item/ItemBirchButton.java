package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBirchButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchButton extends Item {

    public ItemBirchButton() {
        super( "minecraft:birch_button", -141 );
    }

    @Override
    public Block getBlock() {
        return new BlockBirchButton();
    }
}
