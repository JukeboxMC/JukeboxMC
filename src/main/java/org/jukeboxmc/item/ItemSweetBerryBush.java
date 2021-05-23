package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSweetBerryBush;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSweetBerryBush extends Item {

    public ItemSweetBerryBush() {
        super( -207 );
    }

    @Override
    public BlockSweetBerryBush getBlock() {
        return new BlockSweetBerryBush();
    }
}
