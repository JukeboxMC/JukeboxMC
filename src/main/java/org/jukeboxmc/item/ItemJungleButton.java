package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleButton extends Item {

    public ItemJungleButton() {
        super( "minecraft:jungle_button", -143 );
    }

    @Override
    public BlockJungleButton getBlock() {
        return new BlockJungleButton();
    }
}
