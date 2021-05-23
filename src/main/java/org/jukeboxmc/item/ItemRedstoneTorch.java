package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedstoneTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedstoneTorch extends Item {

    public ItemRedstoneTorch() {
        super( 76 );
    }

    @Override
    public BlockRedstoneTorch getBlock() {
        return new BlockRedstoneTorch();
    }
}
