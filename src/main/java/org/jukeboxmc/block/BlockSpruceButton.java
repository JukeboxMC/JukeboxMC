package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSpruceButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceButton extends BlockButton {

    public BlockSpruceButton() {
        super( "minecraft:spruce_button" );
    }

    @Override
    public ItemSpruceButton toItem() {
        return new ItemSpruceButton();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SPRUCE_BUTTON;
    }

}
