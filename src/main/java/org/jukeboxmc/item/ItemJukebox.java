package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJukebox;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJukebox extends Item {

    public ItemJukebox() {
        super( 84 );
    }

    @Override
    public BlockJukebox getBlock() {
        return new BlockJukebox();
    }
}
