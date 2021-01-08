package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMycelium;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMycelium extends Item {

    public ItemMycelium() {
        super( "minecraft:mycelium", 110 );
    }

    @Override
    public BlockMycelium getBlock() {
        return new BlockMycelium();
    }
}
