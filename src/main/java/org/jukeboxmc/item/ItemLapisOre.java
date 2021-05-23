package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLapisOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLapisOre extends Item {

    public ItemLapisOre() {
        super( 21 );
    }

    @Override
    public BlockLapisOre getBlock() {
        return new BlockLapisOre();
    }
}
