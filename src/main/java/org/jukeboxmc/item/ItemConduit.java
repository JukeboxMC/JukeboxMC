package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockConduit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemConduit extends Item {

    public ItemConduit() {
        super( "minecraft:conduit", -157 );
    }

    @Override
    public BlockConduit getBlock() {
        return new BlockConduit();
    }
}
