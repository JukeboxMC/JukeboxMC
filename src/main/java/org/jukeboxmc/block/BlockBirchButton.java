package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBirchButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchButton extends BlockButton {

    public BlockBirchButton() {
        super( "minecraft:birch_button" );
    }

    @Override
    public ItemBirchButton toItem() {
        return new ItemBirchButton();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BIRCH_BUTTON;
    }

}
