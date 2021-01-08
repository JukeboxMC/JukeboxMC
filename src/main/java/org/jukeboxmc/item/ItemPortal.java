package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPortal;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPortal extends Item {

    public ItemPortal() {
        super( "minecraft:portal", 90 );
    }

    @Override
    public BlockPortal getBlock() {
        return new BlockPortal();
    }
}
