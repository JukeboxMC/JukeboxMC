package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedstoneOre extends Item {

    public ItemRedstoneOre() {
        super( 73 );
    }

    @Override
    public BlockRedstoneOre getBlock() {
        return new BlockRedstoneOre();
    }
}
