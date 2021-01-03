package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonButton extends Item {

    public ItemCrimsonButton() {
        super( "minecraft:crimson_button", -260 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonButton();
    }
}
