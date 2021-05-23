package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBasalt;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBasalt extends Item {

    public ItemBasalt() {
        super( -234 );
    }

    @Override
    public BlockBasalt getBlock() {
        return new BlockBasalt();
    }
}
