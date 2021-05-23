package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWoodenButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenDoorBlock extends Item {

    public ItemWoodenDoorBlock() {
        super( 64 );
    }

    @Override
    public BlockWoodenButton getBlock() {
        return new BlockWoodenButton();
    }
}
