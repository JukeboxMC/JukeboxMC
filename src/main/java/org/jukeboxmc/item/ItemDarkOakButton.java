package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDarkOakButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakButton extends Item {

    public ItemDarkOakButton() {
        super( "minecraft:dark_oak_button", -142 );
    }

    @Override
    public Block getBlock() {
        return new BlockDarkOakButton();
    }
}
