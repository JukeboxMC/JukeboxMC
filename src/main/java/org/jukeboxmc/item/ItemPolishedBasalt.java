package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBasalt;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBasalt extends Item {

    public ItemPolishedBasalt() {
        super( -235 );
    }

    @Override
    public BlockPolishedBasalt getBlock() {
        return new BlockPolishedBasalt();
    }
}
