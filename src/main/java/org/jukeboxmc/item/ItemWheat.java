package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWheat;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWheat extends Item {

    public ItemWheat() {
        super( "minecraft:wheat", 334 );
    }

    @Override
    public BlockWheat getBlock() {
        return new BlockWheat();
    }
}
