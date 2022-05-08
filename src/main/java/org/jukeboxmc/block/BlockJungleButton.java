package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemJungleButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleButton extends BlockButton {

    public BlockJungleButton() {
        super( "minecraft:jungle_button" );
    }

    @Override
    public ItemJungleButton toItem() {
        return new ItemJungleButton();
    }

    @Override
    public BlockType getType() {
        return BlockType.JUNGLE_BUTTON;
    }

}
