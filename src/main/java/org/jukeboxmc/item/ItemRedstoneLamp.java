package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedstoneLamp;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedstoneLamp extends Item {

    public ItemRedstoneLamp() {
        super( 123 );
    }

    @Override
    public BlockRedstoneLamp getBlock() {
        return new BlockRedstoneLamp();
    }
}
