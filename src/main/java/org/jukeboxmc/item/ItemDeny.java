package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeny;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeny extends Item {

    public ItemDeny() {
        super( 211 );
    }

    @Override
    public BlockDeny getBlock() {
        return new BlockDeny();
    }
}
