package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedButton extends Item {

    public ItemWarpedButton() {
        super( "minecraft:warped_button", -261 );
    }

    @Override
    public BlockWarpedButton getBlock() {
        return new BlockWarpedButton();
    }
}
