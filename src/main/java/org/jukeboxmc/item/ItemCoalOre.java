package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoalOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoalOre extends Item {

    public ItemCoalOre() {
        super(  16 );
    }

    @Override
    public BlockCoalOre getBlock() {
        return new BlockCoalOre();
    }
}
