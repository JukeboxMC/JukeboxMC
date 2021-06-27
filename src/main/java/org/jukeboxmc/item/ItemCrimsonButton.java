package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonButton extends Item {

    public ItemCrimsonButton() {
        super ( "minecraft:crimson_button" );
    }

    @Override
    public BlockCrimsonButton getBlock() {
        return new BlockCrimsonButton();
    }
}
