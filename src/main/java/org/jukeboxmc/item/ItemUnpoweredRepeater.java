package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRepeater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUnpoweredRepeater extends Item {

    public ItemUnpoweredRepeater() {
        super( 93 );
    }

    @Override
    public BlockRepeater getBlock() {
        return new BlockRepeater();
    }
}
