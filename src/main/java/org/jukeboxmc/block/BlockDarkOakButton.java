package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDarkOakButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakButton extends BlockButton {

    public BlockDarkOakButton() {
        super( "minecraft:dark_oak_button" );
    }

    @Override
    public ItemDarkOakButton toItem() {
        return new ItemDarkOakButton();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DARK_OAK_BUTTON;
    }

}
