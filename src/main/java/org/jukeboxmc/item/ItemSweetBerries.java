package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSweetBerryBush;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSweetBerries extends Item {

    public ItemSweetBerries() {
        super( 287 );
    }

    @Override
    public BlockSweetBerryBush getBlock() {
        return new BlockSweetBerryBush();
    }
}
