package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWheat;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWheatBlock extends Item {

    public ItemWheatBlock() {
        super( "minecraft:item.wheat", 59 );
    }

    @Override
    public BlockWheat getBlock() {
        return new BlockWheat();
    }
}
