package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockObserver;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemObserver extends Item {

    public ItemObserver() {
        super( "minecraft:observer", 251 );
    }

    @Override
    public BlockObserver getBlock() {
        return new BlockObserver();
    }
}
