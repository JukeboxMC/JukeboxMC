package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLava;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLavaBucket extends Item {

    public ItemLavaBucket() {
        super( 363 );
    }

    @Override
    public BlockLava getBlock() {
        return new BlockLava();
    }
}
